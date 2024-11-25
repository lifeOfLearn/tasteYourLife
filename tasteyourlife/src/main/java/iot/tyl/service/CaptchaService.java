package iot.tyl.service;

public interface CaptchaService {
	/**
	 * @throws TylEx, if create image IoEx
	 */
	public byte[] getImage(String guestId);
	
	/**
	 * @throws TylEx, if create image IoEx
	 */
	public String getBase64(String guestId);
	
	/**
	 * @throws TylEx, if server captcha is null ,or not equal
	 */
	public boolean checkCaptcha(String id, String captcha);
}
