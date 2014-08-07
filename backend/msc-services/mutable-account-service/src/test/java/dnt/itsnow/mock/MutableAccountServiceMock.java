/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.mock;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.model.Account;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.service.MutableAccountService;

/**
 * <h1>Class Usage</h1>
 */
public class MutableAccountServiceMock implements MutableAccountService {
    @Override
    public Page<Account> findAll(String type, Pageable pageable) {
        return null;
    }

    @Override
    public Account create(Account account) {
        return null;
    }

    @Override
    public Account update(Account account) throws AccountException {
        return null;
    }

    @Override
    public void delete(Account account) throws AccountException {

    }

    @Override
    public Account findByName(String name) {
        return null;
    }

    @Override
    public Account findBySn(String sn) {
        return null;
    }

    @Override
    public Account findById(Long id) {
        return null;
    }
}
