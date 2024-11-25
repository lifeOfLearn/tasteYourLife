package iot.tyl.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.tyl.service.CaptchaService;
import iot.tyl.util.contant.CommunityType;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${path.captcha.root}")
public class CaptchaController {

	@Autowired
	private CaptchaService captchaService;
	
	@GetMapping(value = "${path.captcha.image}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] captchaImg(HttpServletRequest request) {
		String guestId = getGuestId(request);
		return captchaService.getImage(guestId);
	}
	
	private String getGuestId(HttpServletRequest request) {
		String guestId = UUID.randomUUID().toString();
		request.setAttribute(CommunityType.GUESTID.name(), guestId);
		return guestId;
	}
}
