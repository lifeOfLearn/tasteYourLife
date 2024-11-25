package iot.tyl.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iot.tyl.config.ParamConfig;
import iot.tyl.service.CaptchaService;
import iot.tyl.service.RedisService;
import iot.tyl.util.CaptchaUtil;
import iot.tyl.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService{

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CaptchaUtil captchaUtil;
	
	@Autowired
	private ExceptionUtil exceptionUtil;
	
	@Autowired
	private ParamConfig paramConfig;

	@Override
	public byte[] getImage(String guestId) {
		try {
			String captcha = captchaUtil.generateCaptcha();
			save(guestId, captcha);
			return captchaUtil.generatePng(captcha);
			
		} catch (IOException e) {
			throw exceptionUtil.throwIoGetCaptchaImage(guestId, e);
		}
	}

	@Override
	public String getBase64(String guestId) {
		try {
			String captcha = captchaUtil.generateCaptcha();
			save(guestId, captcha);
			return captchaUtil.getImageBase64(captcha);
			
		} catch (IOException e) {
			throw exceptionUtil.throwIoGetCaptchaImage(guestId, e);
		}
	}

	@Override
	public boolean checkCaptcha(String id, String captcha) {
		String serverCaptcha = getServerCaptcha(id);
		boolean isEq = serverCaptcha.equals(captcha);
		if (isEq)
			return true;
		
		throw exceptionUtil.throwNotEqualCaptcha(id);
	}
	
	private String getServerCaptcha(String guestId) {
		try {
			String value = get(guestId);
			return value;
		} catch (Exception e) {
			throw exceptionUtil.throwNullCpathcaByServer(guestId, e);
		}
	}
	
	private String get(String key) {
		return redisService.getValue(key);
	}
	
	private void save(String guestId, String captcha) {
		redisService.saveValue(guestId, captcha, paramConfig.getSec().getCaptcha());
	}
	
}
