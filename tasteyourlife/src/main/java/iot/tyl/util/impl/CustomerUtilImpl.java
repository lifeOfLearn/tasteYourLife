package iot.tyl.util.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import iot.tyl.util.CustomerUtil;
import iot.tyl.util.contant.Pattern;
import iot.tyl.util.contant.UserMode;

@Component
public class CustomerUtilImpl implements CustomerUtil{
	
	private static final String ROC_FIRST_CHAR_SEQ = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
	
	@Override
	public UserMode getUserMode(String user) {
		if (user.matches(Pattern.PHONE.value()))
			return UserMode.PHONE;
		else if (user.matches(Pattern.EMAIL.value()))
			return UserMode.EMAIL;
		return null;
	}
	
	@Override
	public boolean checkPasswd(String passwd) {
		return passwd.matches(Pattern.PASSWD.value());
	}

	@Override
	public boolean checkRocId(String rocId) {
		if (!rocId.matches(Pattern.ROCID.value()))
			return false;
		
		int sum = ROC_FIRST_CHAR_SEQ.indexOf(rocId.charAt(0)) + 10;
		sum = sum / 10 + sum % 10 * 9;
		for (int i = 1; i < 9; i++) {
			sum += (rocId.charAt(i) - '0') * (9 - i);
		}
		sum += rocId.charAt(9) - '0';
		return sum % 10 == 0;
	}
	
	@Override
	public boolean checkDate(Date date) {
		return date.before(new Date());
	}
	
	@Override
	public boolean checkMail(String mail) {
		return mail.matches(Pattern.EMAIL.value());
	}
	
	@Override
	public boolean checkPhone(String phone) {
		return phone.matches(Pattern.PHONE.value());
	}
	
}
