package iot.tyl.service;

public interface MailService {

	public void sendRegister(String email, String tokenId);
	
	public void sendPasswdErr(String email);
	
	public void sendForget(String email, String tokenId);
}
