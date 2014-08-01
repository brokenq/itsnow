/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.web.security.DelegateSecurityConfigurer;

/**
 * 缺省的服务配置
 */
public class CommonAclServiceConfig extends DefaultCommonServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(DelegateSecurityConfigurer.class);
    }
}
