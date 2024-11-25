package iot.tyl.service;

public interface EncryptService {
	
	public String encrypt(String sourcePasswd);
	
	public boolean checkPassword(String sourcePasswd, String encryptPasswd); 
}
