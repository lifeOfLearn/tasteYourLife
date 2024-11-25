package iot.tyl.util;

import java.util.Date;

import iot.tyl.util.contant.UserMode;

public interface CustomerUtil {
	public UserMode getUserMode (String user);
	public boolean checkPasswd(String passwd);
	public boolean checkRocId(String rocId);
	public boolean checkDate(Date date);
	public boolean checkMail(String mail);
	public boolean checkPhone(String phone);
}
