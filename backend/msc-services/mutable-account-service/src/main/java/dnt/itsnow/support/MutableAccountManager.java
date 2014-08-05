/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.AccountStatus;
import dnt.itsnow.platform.service.DefaultPage;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.repository.MutableAccountRepository;
import dnt.itsnow.service.MutableAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>可操作的账户服务</h1>
 */
@Service
public class MutableAccountManager extends CommonAccountManager implements MutableAccountService {
    @Autowired
    MutableAccountRepository mutableRepository;

    @Override
    public Page<Account> findAll(String type, Pageable pageable) {
        long total = mutableRepository.countByType(type);
        List<Account> accounts = mutableRepository.findAllByType(type, pageable);
        return new DefaultPage<Account>(accounts, pageable, total);
    }

    @Override
    public Account create(Account account) {
        logger.info("Creating {}", account);
        //TODO Generate SN
        account.setStatus(AccountStatus.New);
        long id = mutableRepository.create(account);
        Account created = mutableRepository.findById(id);
        logger.info("Created  {}", created);
        return created;
    }

    @Override
    public Account update(Account account) throws AccountException {
        logger.info("Updating {}", account);
        Account updating = mutableRepository.findBySn(account.getSn());
        if( updating.isExpired() )
            throw new AccountException("Can't update expired account: " + updating.getSn());
        updating.apply(account);
        mutableRepository.update(updating);
        logger.info("Updated  {}", updating);
        return updating;
    }

    @Override
    public void delete(Account deleting) throws AccountException {
        logger.info("Deleting account {}", deleting);
        if( deleting.isValid() )
            throw new AccountException("Can't delete valid account: " + deleting);
        mutableRepository.deleteBySn(deleting.getSn());
        logger.info("Deleted  account {}", deleting);
    }
}
