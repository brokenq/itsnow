/**
 * Developer: Kadvin Date: 14-7-10 下午9:54
 */
package dnt.itsnow.exception;

import net.happyonroad.platform.service.ServiceException;

/**
 * Session Service thrown exception
 */
public class ServiceCatalogException extends ServiceException {
    public ServiceCatalogException(String message) {
        super(message);
    }
}
