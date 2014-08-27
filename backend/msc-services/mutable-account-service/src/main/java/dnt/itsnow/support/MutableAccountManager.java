/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.AccountStatus;
import dnt.itsnow.platform.service.AutoNumberService;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.repository.MutableAccountRepository;
import dnt.itsnow.service.MutableAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    AutoNumberService autoNumberService;

    @Override
    public Page<Account> findAll(String type, Pageable pageable) {
        long total = mutableRepository.countByType(type);
        List<Account> accounts = mutableRepository.findAllByType(type, pageable);
        return new DefaultPage<Account>(accounts, pageable, total);
    }

    @Override
    public Account create(Account account) {
        logger.info("Creating {}", account);
        if( account.getSn() == null ){
            String type = account.getType();
            account.setSn(autoNumberService.next(type));
        }
        account.setStatus(AccountStatus.New);
        mutableRepository.create(account);
        Account created = super.findBySn(account.getSn());
        logger.info("Created  {}", created);
        return created;
    }

    @Override
    public Account update(Account account) throws AccountException {
        logger.info("Updating {}", account);
        Account updating = super.findBySn(account.getSn());
        if( updating.isRejected() )
            throw new AccountException("Can't update rejected account: " + updating.getSn());
        updating.apply(account);
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
        mutableRepository.deleteBySn(deleting.getSn());
        logger.info("Deleted  account {}", deleting);
    }

    @Override
    public Account approve(Account account) throws AccountException {
        logger.info("Approving {}", account);
        if( account.isNew()){
            account.setStatus(AccountStatus.Valid);
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
            mutableRepository.reject(account);
            logger.info("Rejected  {}", account);
            return account;
        } else
            throw new AccountException("Can't reject account in " + account.getStatus());
    }
}
