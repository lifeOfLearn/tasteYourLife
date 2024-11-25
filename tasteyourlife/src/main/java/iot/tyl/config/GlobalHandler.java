package iot.tyl.config;

import java.time.DateTimeException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import iot.tyl.exception.CustomerExpired;
import iot.tyl.exception.ForgetException;
import iot.tyl.exception.OrderException;
import iot.tyl.exception.ProductException;
import iot.tyl.exception.ReNewException;
import iot.tyl.exception.RenewDataException;
import iot.tyl.exception.TylBaseException;
import iot.tyl.model.ResponseDto;
import iot.tyl.service.CaptchaService;
import iot.tyl.service.CookieService;
import iot.tyl.service.JwtService;
import iot.tyl.service.RedisService;
import iot.tyl.util.contant.CommunityType;
import iot.tyl.util.contant.ErrorKeyType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalHandler{

	@Autowired
	private CaptchaService captchaService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private ParamConfig paramConfig;
	
	@Autowired
	private PathConfig pathConfig;
	
	
//	@ExceptionHandler(Exception.class)
//	public ResponseDto handler(Exception e, HttpServletResponse response) {
//		ResponseDto dto = new ResponseDto();
//		dto.setCode(500);
//		dto.setMsg("未知錯誤");
//		return dto;
//	}
	
	@ExceptionHandler(DateTimeException.class)
	public ResponseDto orderHistoryHandler(DateTimeException e) {
		return baseDto(400, "日期格式不正確，需要符合ISO-8061");
	}
	
	@ExceptionHandler(OrderException.class)
	public ResponseDto orderExceptionHandler(OrderException ex) {
		return baseDto(400, ex);
	}
	
	
	@ExceptionHandler(ProductException.class)
	public ResponseDto productExceptionHandler(ProductException ex) {
		return baseDto(400, ex);
	}
	
	@ExceptionHandler(CustomerExpired.class)
	public ResponseDto customerExpiredHandler(CustomerExpired ex) {
		return baseDto(303, ex);
	}
	
	@ExceptionHandler(RenewDataException.class)
	public ResponseDto renewDataHandler(RenewDataException ex, HttpServletRequest request, HttpServletResponse response) {
		return baseDto(400, ex);
	}
	
	@ExceptionHandler(ReNewException.class)
	public ResponseDto reNewHandler(ReNewException ex, HttpServletRequest request, HttpServletResponse response) {
		return dtoRedirectRoot(ex);
	}
	
	@ExceptionHandler(ForgetException.class)
	public ResponseDto forgetHandler(ForgetException ex, HttpServletRequest request, HttpServletResponse response) {
		return dtoRedirectRoot(ex);
	}
	

	
	@ExceptionHandler(TylBaseException.class)
	public ResponseDto customersizeHandler(TylBaseException ex, HttpServletRequest request, HttpServletResponse response) {
		String contextPath = pathConfig.getRoot(); 
		String customer = contextPath + pathConfig.getCustomer().getRoot();
		String uri = request.getRequestURI();
		if (customer.equals(uri)) {
			ResponseDto dto = new ResponseDto();
			dto.setCode(400);
			return dto;
		}
		
		
		removeOldCaptchaCookie(request);
		String guestId = getId();
		ResponseCookie cookie = getNewCookie(guestId);
		setHeader(response, cookie);
		String captchaBase64 = getNewCaptchaBase64(guestId);
		ResponseDto dto = getDto(captchaBase64, 400, ex);
		
		return dto;
	}
	
	
	private ResponseDto dtoRedirectRoot(TylBaseException ex) {
		ResponseDto dto = new ResponseDto();
		Map<ErrorKeyType, String> map = new HashMap<>();
		map.put(ex.getKey(), ex.getMsg());
		dto.setRedirect(pathConfig.getRoot());
		dto.setCode(400);
		dto.setError(map);
		return dto;
	}
	
	private void setHeader(HttpServletResponse response, ResponseCookie cookie) {
		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}
	
	private void removeOldCaptchaCookie(HttpServletRequest request) {
		Object guestId = request.getAttribute(CommunityType.GUESTID.name());
		if (guestId != null)
			remove(guestId.toString());
	}
	
	
	private String getNewCaptchaBase64(String guestId) {
		return captchaService.getBase64(guestId);
	}
	
	private void remove(String guestId) {
		try {
			redisService.deleteValue(guestId);
			
		} catch (Exception e) {
			
		}
	}
	
	private ResponseCookie getNewCookie(String guestId) {
		String token = getToken(guestId);
		return getCookie(token);
	}
	
	private ResponseCookie getCookie(String token) {
		String url = pathConfig.getRoot() + pathConfig.getCaptcha().getRoot() + pathConfig.getCaptcha().getImage();
		return cookieService.getCookie(url, token);
	}
	
	private String getToken(String guestId) {
		long timeSec = paramConfig.getSec().getCaptcha();
		return jwtService.generateToken(guestId, timeSec);
	}
	
	private String getId() {
		return UUID.randomUUID().toString();
	}
	
	private ResponseDto baseDto(int errCode, String msg) {
		ResponseDto dto = new ResponseDto();
		Map<ErrorKeyType, String> map = new HashMap<>();
		map.put(ErrorKeyType.other, msg);
		dto.setCode(errCode);
		dto.setError(map);
		return dto;
	}
	
	private ResponseDto baseDto(int errCode, TylBaseException ex) {
		ResponseDto dto = new ResponseDto();
		Map<ErrorKeyType, String> map = new HashMap<>();
		map.put(ex.getKey(), ex.getMsg());
		dto.setCode(errCode);
		dto.setError(map);
		return dto;
	}
	
	private ResponseDto getDto(String captcha, int code, TylBaseException ex) {
		ResponseDto dto = new ResponseDto();
		dto.setCode(code);
		dto.setError(getMap(ex, captcha));
		return dto;
	}
	
	private Map<ErrorKeyType, String> getMap(TylBaseException ex, String captcha){
		Map<ErrorKeyType, String> map = new HashMap<>();
		map.put(ex.getKey(), ex.getMsg());
		map.put(ErrorKeyType.reCaptcha, captcha);
		return map;
	}
}
