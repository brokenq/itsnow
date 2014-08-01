/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.exception;

import dnt.itsnow.platform.service.ServiceException;

/**
 * The account exception
 */
public class AccountException extends ServiceException {
    public AccountException(String message) {
        super(message);
    }
}
