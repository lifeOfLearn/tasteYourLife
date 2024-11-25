package iot.tyl.util;

import java.io.IOException;

public interface CaptchaUtil {
	public String generateCaptcha();
	public byte[] generatePng(String captcha) throws IOException;
	public String getImageBase64(String captcha) throws IOException;
}
