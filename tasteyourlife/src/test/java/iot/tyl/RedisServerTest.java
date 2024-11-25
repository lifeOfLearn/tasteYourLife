package iot.tyl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import iot.tyl.service.RedisService;
import iot.tyl.util.contant.RedisMapKey;
import iot.tyl.util.contant.TokenKey;

@SpringBootTest
public class RedisServerTest {

	@Autowired
	private RedisService redisService;
	
	private String id;
	@Test
	public void test() {
		
		testmap();
//		set();
//		get();
//		deleteValue();
//		clearAll();
//		keyIsExists();
	}
	
	private void testmap() {
		String test = "test";
		Map<String, Object> map = new HashMap<>();
		map.put("id", UUID.randomUUID().toString());
		map.put("name", "testName");
		redisService.saveMap(test, map, 100);
		Map<Object, Object> mapRes = redisService.getMap(test);
		mapRes.forEach((k,v)->{
			System.out.println("k=" + k + ",v=" + v);
		});
	}
	
	private void set() {
		id = UUID.randomUUID().toString();
		String[] keys = {	id,
							TokenKey.aud.name()};
		int i = 0;
		for (String key : keys) {
			System.out.println("Set key : " + key);
			redisService.saveValue(key, String.valueOf(i++), 100);
		}
	}
	private void get() {
		String[] keys = {	id,
				TokenKey.aud.name()};
		for (String key : keys) {
			System.out.println("Get key : " + key);
			System.out.println(redisService.getValue(key));
		}
	}
	
	private void deleteValue() {
		redisService.deleteValue(id);
	}
	
	private void clearAll() {
		redisService.clearAll();
	}
	
	private void keyIsExists() {
		System.out.println(redisService.keyExists(TokenKey.aud.name()));
	}
}
