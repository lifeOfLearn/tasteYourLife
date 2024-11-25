package iot.tyl.service;

import java.util.Map;

public interface RedisService {
    void saveValue(String key, String value, long sec);
    void saveMap(String key, Map<String, Object> map, long sec);
    String getValue(String key);
    Map<Object, Object> getMap(String key);
    void deleteValue(String key);
    void clearAll();
    boolean keyExists(String key);
	
}
