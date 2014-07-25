/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.platform.services;

/**
 *  the service package load exception
 */
public class ServicePackageException extends Exception {
    public ServicePackageException(String message, Exception cause) {
        super(message, cause);
    }
}
