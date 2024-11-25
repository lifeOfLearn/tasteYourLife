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

import iot.tyl.model.CustomerDto;
import iot.tyl.model.ResponseDto;
import iot.tyl.service.CookieService;
import iot.tyl.service.JwtService;

@RestControllerAdvice
public class LoginAdvice implements ResponseBodyAdvice<Object>{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private ParamConfig paramConfig;
	
	@Autowired
	private PathConfig pathConfig;
	
	@Autowired
	private MessageConfig messageConfig;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return CustomerDto.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public ResponseDto beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		
		CustomerDto dto = (CustomerDto) body;
		ResponseDto reDto = new ResponseDto();
		reDto.setCode(HttpStatus.OK.value());
		reDto.setUser(dto.getUser());
		reDto.setMsg(messageConfig.getLogin());
		
		String token = getToken(dto);
		ResponseCookie loginCookie = getLoginCookie(token, request);
		ResponseCookie captchaCookie = cleanCaptchaCookie();
		
		setHeader(response, captchaCookie);
		setHeader(response, loginCookie);

		body = null;
		return reDto;		
	}
	
	private void setHeader(ServerHttpResponse response, ResponseCookie cookie) {
		response.getHeaders().add(HttpHeaders.SET_COOKIE, cookie.toString());
	}
	
	private ResponseCookie cleanCaptchaCookie() {
		String url = pathConfig.getRoot() + pathConfig.getCaptcha().getRoot() + pathConfig.getCaptcha().getImage();
System.out.println("clean url : " + url);
		return cookieService.cleanCookie(url);
	}
	
	private ResponseCookie getLoginCookie(String token, ServerHttpRequest request) {
		String uri = request.getURI().getPath();
System.out.println("Advice : uri :" + uri);
		return cookieService.getCookie(uri, token);		
	}
	
	private String getToken(CustomerDto dto) {
		String tokenId = dto.getTokenId().toString();
		long timeSec = paramConfig.getSec().getLogin();
		return jwtService.generateToken(tokenId, timeSec);
	}

}
