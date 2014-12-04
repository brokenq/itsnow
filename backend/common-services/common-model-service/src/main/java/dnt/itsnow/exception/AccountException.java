/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * The account exception
 */
public class AccountException extends ServiceException {
    public AccountException(String message) {
        super(message);
    }
}
