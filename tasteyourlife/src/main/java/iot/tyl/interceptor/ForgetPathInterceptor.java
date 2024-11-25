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
public class ForgetPathInterceptor implements HandlerInterceptor  {
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ParamConfig paramConfig;
	
	@Autowired
	private ExceptionUtil exceptionUtil;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			//0:captcha, 1:forget
			Cookie[] cookies = getForgetCookie(request);
			String guest = getForgetCookieValue(cookies[0], request);
			String token = getForgetCookieValue(cookies[1], request);
			checkToken(guest, request);
			checkToken(token, request);
			String guestId = getSub(guest, request);
			String tokenId = getSub(token, request);
			setId(guestId, CommunityType.GUESTID, request);
			setId(tokenId, CommunityType.TOKENID, request);
			return true;
			
		}catch (Exception e) {
			throw exceptionUtil.throwForgetPathDestUnknow( getUserData(request) );
		}
	}
	private void setId(String id, CommunityType type, HttpServletRequest request) {
		request.setAttribute(type.name(), id);
	}
	
	private String getSub(String token, HttpServletRequest request) {
		Object sub = jwtService.getValue(token, TokenKey.sub.name());
		if (sub != null)
			return sub.toString();
		
		throw exceptionUtil.throwForgetPathNullSubToken( getUserData(request) );
	}
	
	private boolean checkToken(String token, HttpServletRequest request) {
		try {	
			checkTime(token, request);
			checkIss(token, request);
			return true;
			
		} catch (Exception e) {
			throw exceptionUtil.throwForgetPathInvalidToken(token, getUserData(request),e.getCause() );
		}
	}
	
	private boolean checkIss(String token, HttpServletRequest request) {
		Object iss = jwtService.getValue(token, TokenKey.iss.name());
		if (iss != null && paramConfig.getToken().getIss().equals(iss))
			return true;
		
		throw exceptionUtil.throwForgetPathIssInvalidToken(token, getUserData(request) );
	}
	
	private boolean checkTime(String token, HttpServletRequest request) {
		boolean isExpired = jwtService.isTokenExpired(token);
		if (!isExpired)
			return true;
		
		throw exceptionUtil.throwForgetPathExpiredToken(token, getUserData(request) );
	}
	
	private String getForgetCookieValue(Cookie cookie, HttpServletRequest request) {
		String value = cookieService.getCookieValue(cookie);
		if (value != null && !value.trim().isEmpty()) {
			return value;
		}
		
		throw exceptionUtil.throwForgetPathNullValue( getUserData(request) );
	}
	
	private Cookie[] getForgetCookie(HttpServletRequest request) {
		Cookie[] cookies = getAllCookies(request);
		String captchaKey = paramConfig.getCookie().getKey().getCaptcha();
		String forgetKey = paramConfig.getCookie().getKey().getForget();
		Cookie captchaCookie = cookieService.getCookieByKey(cookies, captchaKey);
		Cookie forgetCookie = cookieService.getCookieByKey(cookies, forgetKey);
		if (captchaCookie != null && forgetCookie != null) {
			return new Cookie[]{captchaCookie, forgetCookie};
		}
			
		throw exceptionUtil.throwForgetPathNullKey(forgetKey, getUserData(request) );
	}
	
	private Cookie[] getAllCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			return cookies;
		
		throw exceptionUtil.throwForgetPathNullCookie( getUserData(request) );
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
