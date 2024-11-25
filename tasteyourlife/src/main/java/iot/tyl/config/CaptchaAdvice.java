package iot.tyl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import iot.tyl.controller.CaptchaController;
import iot.tyl.service.CookieService;
import iot.tyl.service.JwtService;
import iot.tyl.util.contant.CommunityType;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CaptchaAdvice implements ResponseBodyAdvice<byte[]> {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private ParamConfig paramConfig;
	
	@Autowired
	private PathConfig pathConfig;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		 return returnType.getContainingClass() == CaptchaController.class;
	}

	@Override
	public byte[] beforeBodyWrite(byte[] body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		
		String guestId = getId(request);
		String token = getToken(guestId);
		ResponseCookie cookie = getCookie(token);
		setHeader(response, cookie);
		
		return body;
	}
	
	private void setHeader(ServerHttpResponse response, ResponseCookie cookie) {
		response.getHeaders().add(HttpHeaders.SET_COOKIE, cookie.toString());
	}
	
	private ResponseCookie getCookie(String token) {
		String url = pathConfig.getRoot() + pathConfig.getCaptcha().getRoot() + pathConfig.getCaptcha().getImage();
		return cookieService.getCookie(url, token);
	}
	
	private String getToken(String guestId) {
		long timeSec = paramConfig.getSec().getCaptcha();
		return jwtService.generateToken(guestId, timeSec);
	}
	
	private String getId (ServerHttpRequest request) {
		HttpServletRequest servletRequest = ((ServletServerHttpRequest)request).getServletRequest();
		Object guestId = servletRequest.getAttribute(CommunityType.GUESTID.name());
		return guestId.toString();
	}

}
