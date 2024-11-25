package iot.tyl.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import iot.tyl.config.ParamConfig;
import iot.tyl.service.CookieService;
import iot.tyl.service.JwtService;
import iot.tyl.util.ExceptionUtil;
import iot.tyl.util.contant.CommunityType;
import iot.tyl.util.contant.TokenKey;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomerInterceptor implements HandlerInterceptor {

	@Autowired
	private CookieService cookieService;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ParamConfig paramConfig;
	
	@Autowired
	private ExceptionUtil exceptionUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie cookie = getCustomerCookie(request);
		String token = getCustomerCookieValue(cookie, request);
		checkToken(token, request);
		String tokenId = getTokenId(token, request);
		setTokenId(tokenId, request);
		return true;
	}
	
	private void setTokenId(String tokenId, HttpServletRequest request) {
		request.setAttribute(CommunityType.TOKENID.name(), tokenId);
	}
	
	private String getTokenId(String token, HttpServletRequest request) {
		Object sub = jwtService.getValue(token, TokenKey.sub.name());
		if (sub != null)
			return sub.toString();
		
		throw exceptionUtil.throwNullSubToken( getUserData(request) );
	}
	
	private boolean checkToken(String token, HttpServletRequest request) {
		try {	
			checkTime(token, request);
			checkIss(token, request);
			return true;
			
		} catch (Exception e) {
			throw exceptionUtil.throwInvalidToken( getUserData(request) );
		}
	}
	
	private boolean checkIss(String token, HttpServletRequest request) {
		Object iss = jwtService.getValue(token, TokenKey.iss.name());
		if (iss != null && paramConfig.getToken().getIss().equals(iss))
			return true;
		
		throw exceptionUtil.throwIssInvalidToken( getUserData(request) );
	}
	
	private boolean checkTime(String token, HttpServletRequest request) {
		boolean isExpired = jwtService.isTokenExpired(token);
		if (!isExpired)
			return true;
		
		throw exceptionUtil.throwExpiredToken( getUserData(request) );
	}
	
	private String getCustomerCookieValue(Cookie cookie, HttpServletRequest request) {
		String value = cookieService.getCookieValue(cookie);
		if (value != null && !value.trim().isEmpty()) {
			return value;
		}
		
		throw exceptionUtil.throwNullLoginValueCookie( getUserData(request) );
	}
	
	private Cookie getCustomerCookie(HttpServletRequest request) {
		Cookie[] cookies = getAllCookies(request);
		String key = paramConfig.getCookie().getKey().getLogin();
		Cookie cookie = cookieService.getCookieByKey(cookies, key);
		if (cookie != null)
			return cookie;
		
		throw exceptionUtil.throwNullLoginKeyCookie( getUserData(request) );
	}
	
	private Cookie[] getAllCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			return cookies;
		
		throw exceptionUtil.throwNullCookie( getUserData(request) );
	}
	
	private String getUserData(HttpServletRequest request) {
		return new StringBuffer()
						.append("客戶端電腦名稱 : ").append(request.getRemoteHost())
						.append(",客戶端位置 : ").append(request.getRemoteAddr())
						.append(",客戶端埠號 : ").append(request.getRemotePort())
						.append(",瀏覽器語言 ： ").append(request.getHeader("Accept-Language"))
						.append(",瀏覽器版本 ： ").append(request.getHeader("User-Agent")).toString();
	}

}
