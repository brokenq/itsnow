/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;

public interface CommonAccountService {
    Account findByName(String name);

    Account findBySn(String sn);

    Account findById(Long id);
}
