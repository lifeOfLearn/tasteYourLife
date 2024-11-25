package iot.tyl.service;

import java.util.Map;

public interface JwtService {
	public String generateToken(String subject, long expireTimeSec);
	public String generateToken(Map<String, Object> claims, String subject, long expireTimeSec);
	public Object getValue(String token, String key);
	public long remainTime(String token);
	public boolean checkToken(String token, String subject);
	public boolean isTokenExpired(String token);
}
