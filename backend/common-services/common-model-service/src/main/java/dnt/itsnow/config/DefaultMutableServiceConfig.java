/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.remote.service.RestFacade;

/**
 * <h1>
 * 默认Mutable服务模块的服务配置
 * </h1>
 * 默认均要引入 User Service 去鉴别会话
 */
public class DefaultMutableServiceConfig extends DefaultCommonServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        importService(RestFacade.class);
    }
}