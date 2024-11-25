package iot.tyl.service;

import org.springframework.http.ResponseCookie;

import iot.tyl.model.CookieData;
import jakarta.servlet.http.Cookie;

public interface CookieService {

	public String getCookieValue(Cookie cookie);
	
	public Cookie getCookieByKey(Cookie[] cookies, String key);
	
	public CookieData getCookieData(String url);

	public ResponseCookie getCookie(String url, String token);

	public ResponseCookie cleanCookie(String url);
}
