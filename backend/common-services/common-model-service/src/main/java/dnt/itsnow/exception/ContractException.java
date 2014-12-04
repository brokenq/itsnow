/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * <h1>The contract exception</h1>
 */
public class ContractException extends ServiceException {
    public ContractException(String message) {
        super(message);
    }
}
