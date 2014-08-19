package dnt.itsnow.demo.model;

import dnt.itsnow.model.Account;

/**
 * Created by jacky on 2014/7/28.
 */
public class MsuIncident extends Incident {

    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
