/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.platform.config.DefaultAppConfig;
import dnt.messaging.MessageBus;
import dnt.redis.RedisCache;
import dnt.redis.RedisConfig;
import dnt.redis.RedisMessageBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * <h1>额外构建一个平台开发的bean</h1>
 */
@Import(DefaultAppConfig.class)
public class GeneralModelAppConfig {

    @Bean
    public RedisCache localCacheService(){
        RedisConfig config = new RedisConfig("redis.local");
        return new RedisCache(config);
    }

    @Bean
    public MessageBus localMessageBus(){
        return new RedisMessageBus(localCacheService());
    }
}
