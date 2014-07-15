/**
 * Developer: Kadvin Date: 14-7-14 下午5:01
 */
package dnt.itsnow.platform.services;

import net.happyonroad.component.container.feature.AbstractFeatureResolver;
import net.happyonroad.component.core.Component;

/**
 * Itsnow平台上的服务模块扩展包
 * 主要扩展内容有：
 *
 * <ol>
 * <li>DB/Migrate脚本扩展</li>
 * <li>DB/Repository,Mapper类扩展</li>
 * <li>Spring Controller/Views等扩展</li>
 * </ol>
 */
public class ServicePackageResolver extends AbstractFeatureResolver {
    public static final String FEATURE           = "service";

    public ServicePackageResolver() {
        super(50, 50);
    }

    @Override
    public String getName() {
        return FEATURE;
    }

    @Override
    public boolean hasFeature(Component component) {
        //暂时仅根据组件的artifact id判断，也不根据内容判断
        return component.getArtifactId().endsWith("_app");
    }

    @Override
    public void resolve(Component component) throws Exception {
        logger.info("The {} is resolved for service feature", component);
    }
}
