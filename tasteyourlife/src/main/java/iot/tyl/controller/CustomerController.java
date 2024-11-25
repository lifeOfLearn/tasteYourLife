package iot.tyl.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.tyl.config.MessageConfig;
import iot.tyl.config.PathConfig;
import iot.tyl.model.CustomerData;
import iot.tyl.model.CustomerDto;
import iot.tyl.model.ReNewPasswdRequest;
import iot.tyl.model.LoginRequest;
import iot.tyl.model.ForgetRequest;
import iot.tyl.model.RegisterRequest;
import iot.tyl.model.ResponseDto;
import iot.tyl.service.CustomerService;
import iot.tyl.util.contant.CommunityType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${path.customer.root}")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PathConfig pathConfig;
	
	@Autowired
	private MessageConfig messageConfig;
	
	@GetMapping
	public ResponseDto navbar(HttpServletRequest request) {
		Object tokenId = getTokenId(request);
		//TODO tokenId time < 5 min => service todo time=60min
		String name = customerService.getUser(tokenId.toString());
		ResponseDto dto = new ResponseDto();
		dto.setUser(name);
		dto.setCode(200);
		return dto;
	}
	
	
	@PostMapping("${path.customer.login}")
	public CustomerDto login(@RequestBody  @Valid LoginRequest loginRequest, HttpServletRequest request) {
		//advice CustomerDto -> ResponseDto
		String guestId = getGuestId(request);
		return customerService.login(loginRequest, guestId);
	}
	
	@PostMapping("${path.customer.register}")
	public ResponseDto register(@RequestBody  @Valid RegisterRequest registerRequest, HttpServletRequest request) {
		String guestId = getGuestId(request);
		customerService.register(registerRequest, guestId);
		return getBody(HttpStatus.OK.value(), messageConfig.getRegister());
	}
	
	@GetMapping("${path.customer.enable}")
	public void enable(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String tokenId = getTokenId(request);
		customerService.enable(tokenId);
		response.sendRedirect(pathConfig.getRoot());
	}
	
	@GetMapping("${path.customer.info}")
	public CustomerData info(HttpServletRequest request) {
		String tokenId = getTokenId(request);
		return customerService.getUserData(tokenId);
	}
	
	//update => 密碼正確才回應 使用者資料
	//updatePasswd 要增加此 => 輸入舊資料比對 正確才更新
//	@PostMapping("${path.customer.update}")
//	public ResponseDto update(@RequestBody UpdateRequest updateRequest, HttpServletRequest request) {
//		//TODO:check inteceptor
//		String tokenId = getTokenId(request);
//		customerService.update(updateRequest, tokenId);
//		
//	}
	
	@GetMapping("${path.customer.logout}")
	public ResponseDto logout(HttpServletRequest request) {
		//TODO:check inteceptor
		String tokenId = getTokenId(request);
		customerService.logout(tokenId);
		ResponseDto dto = new ResponseDto();
		dto.setMsg(messageConfig.getLogout());
		return dto;
		
	}
	
	@PostMapping("${path.customer.forget}")
	public ResponseDto forget(@RequestBody @Valid ForgetRequest forgetRequest, HttpServletRequest request) {
		String guestId = getGuestId(request);
		customerService.forget(forgetRequest, guestId);
		return getBody(HttpStatus.OK.value(), messageConfig.getForget());
	}

	@PostMapping("${path.customer.renew}")
	public ResponseDto reNewPasswd(@RequestBody @Valid ReNewPasswdRequest reNewRequest, HttpServletRequest request) {
		String guestId = getGuestId(request);
		String tokenId = getTokenId(request);
		customerService.reNewPasswd(guestId, tokenId, reNewRequest);
		ResponseDto body = new ResponseDto();
		body.setRedirect(pathConfig.getRoot());
		body.setCode(HttpStatus.OK.value());
		body.setMsg("密碼成功更新");
		return body;
		
	}
	
	
	
	
	private ResponseDto getBody (int code, String msg) {
		ResponseDto body = new ResponseDto();
		body.setCode(code);
		body.setMsg(msg);
		return body;
	}
	
	private String getGuestId(HttpServletRequest request) {
		return getId(request, CommunityType.GUESTID);
	}
	
	private String getTokenId(HttpServletRequest request) {
		return getId(request, CommunityType.TOKENID);
	}
	
	private String getId(HttpServletRequest request, CommunityType type) {
		return (String) request.getAttribute(type.name());
	}
}
