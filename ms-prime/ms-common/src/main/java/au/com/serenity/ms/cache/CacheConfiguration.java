package au.com.serenity.ms.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@Profile({ "prod", "dev-cache" })
public class CacheConfiguration {

	private @Value("${spring.redis.host:localhost}") String redisHostName;
	private @Value("${spring.redis.port:6379}") int redisPort;
	private @Value("${spring.redis.defaultExpiration:60}") int redisExpirationTime;
	
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHostName);
        factory.setPort(redisPort);
        factory.setUsePool(true);
        
        return factory;
    }

    @Bean
    RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        
        return redisTemplate;
    }

    @Bean
    CacheManager cacheManager() {
    	RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
    	redisCacheManager.setDefaultExpiration(redisExpirationTime);
    	
        return redisCacheManager;
    }
}
