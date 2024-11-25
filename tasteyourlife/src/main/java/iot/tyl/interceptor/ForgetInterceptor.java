package iot.tyl.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import iot.tyl.config.PathConfig;
import iot.tyl.service.CookieService;
import iot.tyl.service.JwtService;
import iot.tyl.util.ExceptionUtil;
import iot.tyl.util.contant.CommunityType;
import iot.tyl.util.contant.TokenKey;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ForgetInterceptor implements HandlerInterceptor  {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private PathConfig pathConfig;
	
	@Autowired
	private ExceptionUtil exceptionUtil;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String token = (String)request.getAttribute(CommunityType.TOKEN.name());
		checkValid(token);
		getTokenId(token, request);
		ResponseCookie cookie = getCookie(token);
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
response.getHeaderNames().forEach(name -> System.out.println(response.getHeader(name)));
		
	}
	
	private ResponseCookie getCookie(String token) {
		String url = pathConfig.getRoot() +
					 pathConfig.getCustomer().getForget();
		return cookieService.getCookie(url, token);
	}
	
	private String getTokenId(String token, HttpServletRequest request) {
		Object sub = jwtService.getValue(token, TokenKey.sub.name());
		if (sub != null)
			return sub.toString();
		
		throw exceptionUtil.throwForgetTokenIdNull( getUserData(request) );
	}
	
	private boolean checkValid (String token) {
		boolean notOk = jwtService.isTokenExpired(token);
		if (notOk == false)
			return true;
		
		throw exceptionUtil.throwForgetTokenIsExpired(token);
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
