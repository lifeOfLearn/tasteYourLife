package iot.tyl.service.impl;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import iot.tyl.service.EncryptService;


@Service
public class EncryptServiceImpl implements EncryptService {
	
	private final BCryptPasswordEncoder passwdEncoder;
	
	public EncryptServiceImpl() {
        this.passwdEncoder = new BCryptPasswordEncoder();
    }
	
	@Override
	public String encrypt(String sourcePasswd) {
		return passwdEncoder.encode(sourcePasswd);
	}

	@Override
	public boolean checkPassword(String sourcePasswd, String encryptPasswd) {
		return passwdEncoder.matches(sourcePasswd, encryptPasswd);
	}

}
