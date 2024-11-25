package iot.tyl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import iot.tyl.model.ResponseDto;
import iot.tyl.service.CookieService;

@RestControllerAdvice
public class LogoutAdvice implements ResponseBodyAdvice<ResponseDto> {
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private PathConfig pathConfig;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		String methodName = returnType.getMethod().getName();
		return "logout".equals(methodName);
//		Class<?> customerControllerClazz = returnType.getContainingClass();
//		return customerControllerClazz == CustomerController.class &&
//				String.class.isAssignableFrom(returnType.getParameterType());
				//returnType.getParameterType() == String.class;
	}
	
	@Override
	public ResponseDto beforeBodyWrite(ResponseDto body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		
		body.setRedirect(pathConfig.getRoot());
		body.setCode(HttpStatus.OK.value());
		
		ResponseCookie loginCookie = cleanLoginCookie();
		ResponseCookie captchaCookie = cleanCaptchaCookie();
		
		setHeader(response, captchaCookie);
		setHeader(response, loginCookie);
		return body;
	}
	
	private void setHeader(ServerHttpResponse response, ResponseCookie cookie) {
		response.getHeaders().add(HttpHeaders.SET_COOKIE, cookie.toString());
	}
	
	private ResponseCookie cleanLoginCookie() {
		String url = pathConfig.getRoot() +
					 pathConfig.getCustomer().getRoot() +
					 pathConfig.getCustomer().getLogin();
		return cleanCookie(url);
	}
	
	private ResponseCookie cleanCaptchaCookie() {
		String url = pathConfig.getRoot() + 
					 pathConfig.getCaptcha().getRoot() + 
					 pathConfig.getCaptcha().getImage();
		return cleanCookie(url);
	}
	
	private ResponseCookie cleanCookie(String url) {
		return cookieService.cleanCookie(url);
	}
}
