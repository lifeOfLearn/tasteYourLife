package iot.tyl.config;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableCaching
public class RedisCacheConfig {

	private RedisCacheConfiguration createCache(int time) {
		Duration ttl = Duration.ofHours(time);
		return RedisCacheConfiguration.defaultCacheConfig()
					.entryTtl(ttl)
					.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
					.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}
	
	@Bean(name = "oneHour")
	@Primary
	public RedisCacheManager oneHourCacheManager (RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheManager.builder(redisConnectionFactory)
								.cacheDefaults(createCache(1))
								.build();
	}
	
	@Bean(name = "eightHour")
	public RedisCacheManager eightHourCacheManager (RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheManager.builder(redisConnectionFactory)
								.cacheDefaults(createCache(8))
								.build();
	}
	
	@Bean(name = "twelveHour")
	public RedisCacheManager twelveHourCacheManager (RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheManager.builder(redisConnectionFactory)
								.cacheDefaults(createCache(12))
								.build();
	}
	
	@Bean(name = "oneDay")
	public RedisCacheManager oneDayCacheManager (RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheManager.builder(redisConnectionFactory)
								.cacheDefaults(createCache(24))
								.build();
	}
}
