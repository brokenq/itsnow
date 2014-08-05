/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultAppConfig;
import org.springframework.context.annotation.Import;

/**
 * <h1>额外构建一个平台开发的bean</h1>
 */
@Import(DefaultAppConfig.class)
public class GeneralAccountAppConfig  {
}
