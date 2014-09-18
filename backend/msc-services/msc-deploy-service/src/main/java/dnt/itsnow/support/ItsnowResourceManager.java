/**
 * Developer: Kadvin Date: 14-9-18 上午10:54
 */
package dnt.itsnow.support;

import dnt.itsnow.service.SystemInvocationTranslator;
import dnt.itsnow.service.SystemInvokeService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <h1>Default shared code between itsnow host/schema/process manager</h1>
 */
public abstract class ItsnowResourceManager extends Bean {
    @Autowired
    SystemInvocationTranslator translator;
    @Autowired
    SystemInvokeService        invokeService;
}
