package iot.tyl.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import iot.tyl.config.ParamConfig;
import iot.tyl.config.PathConfig;
import iot.tyl.model.CookieData;
import iot.tyl.service.CookieService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;

@Service
public class CookieServiceImpl implements CookieService{

	@Autowired
	private ParamConfig paramConfig;
	
	@Autowired
	private PathConfig pathConfig;
	
	private Map<String, CookieData> cookie;
	
	@PostConstruct
	public void init() {
		cookie = new HashMap<>();
		setCaptcha();
		setLogin();
		setForget();
	}
	
	@Override
	public String getCookieValue(Cookie cookie) {
		return cookie.getValue();
	}
	
	@Override
	public Cookie getCookieByKey(Cookie[] cookies, String key) {
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			if (key.equals(name))
				return cookie;
		}
		return null;
	}
	
	@Override
	public ResponseCookie cleanCookie(String url) {
		CookieData data = getCookieData(url);
		return getCookie(data.getKey(), data.getPath(), 0L, "");
	}

	@Override
	public ResponseCookie getCookie(String url, String token) {
		CookieData data = getCookieData(url);
		return getCookie(data.getKey(), data.getPath(), data.getTimeSec(), token);
	}
	
	@Override
	public CookieData getCookieData(String url) {
		return cookie.get(url);
	}
	
	
	private ResponseCookie getCookie(String key, String path, long timeSec, String vale) {
		return ResponseCookie
				.from(key, vale)
				.httpOnly(true)
				.secure(true)
				.sameSite(SameSite.STRICT.attributeValue())
				.path(path)
				.maxAge(timeSec)
				.build();
	}
	
	
	private void setLogin() {
		String url = pathConfig.getRoot() +
					 pathConfig.getCustomer().getRoot() +
					 pathConfig.getCustomer().getLogin();
		String key = paramConfig.getCookie().getKey().getLogin();
		String path = paramConfig.getCookie().getPath().getLogin();
		long timeSec = paramConfig.getSec().getLogin();
		setMap(url, key, path, timeSec);
	}
	
	private void setCaptcha() {
		String url = pathConfig.getRoot() +
					 pathConfig.getCaptcha().getRoot() +
					 pathConfig.getCaptcha().getImage();
		String key = paramConfig.getCookie().getKey().getCaptcha();
		String path = paramConfig.getCookie().getPath().getCaptcha();
		long timeSec = paramConfig.getSec().getCaptcha();
		setMap(url, key, path, timeSec);
	}
	
	private void setForget() {
		String url = pathConfig.getRoot() +
				 pathConfig.getCustomer().getForget();
		String key = paramConfig.getCookie().getKey().getForget();
		String path = paramConfig.getCookie().getPath().getForget();
		long timeSec = paramConfig.getSec().getForget();
		setMap(url, key, path, timeSec);
	}
	
	private void setMap(String url, String key, String path, long timeSec) {
		CookieData data = CookieData.builder()
									.key(key)
									.path(path)
									.timeSec(timeSec)
									.build();
		
		cookie.put(url, data);
	}
}
