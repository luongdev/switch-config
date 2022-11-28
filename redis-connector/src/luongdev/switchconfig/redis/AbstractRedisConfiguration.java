package luongdev.switchconfig.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@PropertySource("classpath:redis.properties")
public abstract class AbstractRedisConfiguration {

    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        var template = new RedisTemplate<String, String>();
        template.setConnectionFactory(connectionFactory);

        template.afterPropertiesSet();

        return template;
    }

    //    @Bean
//    @ConditionalOnProperty(value = "spring.cache.type", havingValue = "redis")
//    public CacheManager cacheManager(
//            RedisConnectionFactory connectionFactory,
//            @Value("${app.pbx.cache.ttl:60}") int ttl) {
//
//        var stringSerializer = RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
//        var config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMillis(ttl))
//                .disableCachingNullValues()
//                .serializeValuesWith(stringSerializer)
//                .serializeKeysWith(stringSerializer);
//
//        return RedisCacheManager.builder(connectionFactory).enableStatistics().cacheDefaults(config).build();
//    }
}
