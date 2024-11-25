package iot.tyl.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import iot.tyl.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public void saveValue(String key, String value, long sec) {
		redisTemplate.opsForValue().set(key, value, sec, TimeUnit.SECONDS);
	}

	@Override
	public void saveMap(String key, Map<String, Object> map, long sec) {
		redisTemplate.opsForHash().putAll(key, map);
		redisTemplate.expire(key, sec, TimeUnit.SECONDS);
    }
	
	@Override
	public String getValue(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public Map<Object, Object> getMap(String key) {
		return redisTemplate.opsForHash().entries(key);
	}
	
	@Override
	public void deleteValue(String key) {
		redisTemplate.delete(key);
		
	}

	@Override
	public void clearAll() {
		Set<String> keys = redisTemplate.keys("*");
		if (keys != null && !keys.isEmpty())
			redisTemplate.delete(keys);
	}

	@Override
	public boolean keyExists(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}
	
	public void testRedis() {
		redisTemplate.opsForValue().set("mykey", "Hello, Redis!");

        String value = redisTemplate.opsForValue().get("mykey");
        System.out.println("mykey: " + value);
	}
	
//	@Scheduled(cron = "0 0 0 * *")
//	public void scheduledClearAll() {
//		clearAll();
//	}

}
