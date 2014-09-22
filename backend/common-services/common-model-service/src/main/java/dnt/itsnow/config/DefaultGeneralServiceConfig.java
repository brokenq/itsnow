/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import org.springframework.web.client.RestOperations;

/**
 * <h1>
 * 通用服务模块的服务配置
 * </h1>
 * 默认均要引入 RestFacade
 */
public class DefaultGeneralServiceConfig extends DefaultCommonServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        importService(RestOperations.class, "msc", "mscRestTemplate");
    }
}
