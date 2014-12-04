/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.AccountStatus;
import dnt.itsnow.model.User;
import net.happyonroad.platform.service.AutoNumberService;
import net.happyonroad.platform.util.DefaultPage;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.service.Pageable;
import dnt.itsnow.repository.MutableAccountRepository;
import dnt.itsnow.service.MutableAccountService;
import dnt.itsnow.service.MutableUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>可操作的账户服务</h1>
 *
 * TODO    同样，还有 Mutable Service 是不是应该继承 Common Service，也值得探讨
 */
@Service
public class MutableAccountManager extends CommonAccountManager implements MutableAccountService {
    @Autowired
    MutableAccountRepository mutableRepository;
    @Autowired
    AutoNumberService        autoNumberService;
    @Autowired
    MutableUserService       userService;

    @Override
    public Page<Account> findAll(String type, Pageable pageable) {
        long total = mutableRepository.countByType(type);
        List<Account> accounts = mutableRepository.findAllByType(type, pageable);
        return new DefaultPage<Account>(accounts, pageable, total);
    }

    @Override
    public Account create(Account account) {
        logger.info("Creating {}", account);
        if (account.getSn() == null) {
            String type = account.getType();
            account.setSn(autoNumberService.next(type));
        }
        account.setStatus(AccountStatus.New);
        account.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        account.setUpdatedAt(account.getCreatedAt());
        mutableRepository.create(account);
        logger.info("Created  {}", account);
        return account;
    }

    @Override
    public Account update(Account account) throws AccountException {
        logger.info("Updating {}", account);
        Account updating = super.findBySn(account.getSn());
        if( updating.isRejected() )
            throw new AccountException("Can't update rejected account: " + updating.getSn());
        updating.apply(account);
        updating.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        mutableRepository.update(updating);
        logger.info("Updated  {}", updating);
        return updating;
    }

    @Override
    public void delete(Account deleting) throws AccountException {
        logger.info("Deleting account {}", deleting);
        if( deleting.isNew() )
            throw new AccountException("Can't delete new account: " + deleting);
        if( deleting.isValid() )
            throw new AccountException("Can't delete valid account: " + deleting);
        //TODO 对于已经开通服务的帐户，其删除流程暂时没考虑清楚，先用拒绝删除的方案
        if( deleting.getProcess() != null )
            throw new AccountException("Can't delete account with deployed process: " + deleting);
        //删除该帐户下所有用户, 照理来说，此时该帐户下不应该有其他数据，类似于合同什么的
        userService.deleteByAccountId(deleting.getId());
        mutableRepository.deleteBySn(deleting.getSn());
        logger.info("Deleted  account {}", deleting);
    }

    @Override
    public Account approve(Account account) throws AccountException {
        logger.info("Approving {}", account);
        if( account.isNew()){
            account.setStatus(AccountStatus.Valid);
            account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            mutableRepository.approve(account);
            logger.info("Approved  {}", account);
            return account;
        } else
            throw new AccountException("Can't approve account in " + account.getStatus());
    }

    @Override
    public Account reject(Account account) throws AccountException {
        logger.info("Rejecting {}", account);
        if( account.isNew()){
            account.setStatus(AccountStatus.Rejected);
            account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            mutableRepository.reject(account);
            logger.info("Rejected  {}", account);
            return account;
        } else
            throw new AccountException("Can't reject account in " + account.getStatus());
    }

    @Override
    public Account register(Account account, User user) throws AccountException {
        // TODO 根据测试情况发现，此地没有事务控制
        //   user 有问题，account没有问题，account created，user fail， account没有被回退
        // TODO 这句代码可能抛出 FK Violation exception(org.springframework.dao.DuplicateKeyException)
        // 需要将其转化为合适的Validation异常
        Account created = this.create(account);
        user.setAccount(created);
        User admin = userService.create(user);
        created.setUser(admin);
        return this.update(created);
    }

    @Override
    public List<Account> findAllForNoProcess() {
        logger.debug("Finding all accounts that haven't been assign process");
        List<Account> accounts = mutableRepository.findAllForNoProcess();
        logger.debug("Found size of all accounts that haven't been assign process: {}", accounts.size());
        return accounts;
    }

    @Override
    public void resetNew(String sn) throws AccountException {
        mutableRepository.resetNew(sn);
    }
}
