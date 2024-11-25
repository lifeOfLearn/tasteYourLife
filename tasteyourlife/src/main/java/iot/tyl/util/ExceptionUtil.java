package iot.tyl.util;

import java.util.Date;

import iot.tyl.exception.CustomerExpired;
import iot.tyl.exception.ForgetException;
import iot.tyl.exception.OrderException;
import iot.tyl.exception.ProductException;
import iot.tyl.exception.ReNewException;
import iot.tyl.exception.TylBaseException;
import iot.tyl.model.CustomerEntity;

public interface ExceptionUtil {

	//order
	public OrderException throwOrderCheckoutUnknow(String clientId, Throwable e);
	public OrderException throwCheckoutGetUserNull(String clientId);
	public OrderException throwOrderDataInvalid(String userId, int sId, int qty);
	public OrderException throwOrderPaymentNoHasTheShipping(String clientId, String payment,  String shipping);
	public OrderException throwOrderShppingType(String clientId, String shipping);
	public OrderException throwOrderPaymentType(String clientId, String payment);
	public OrderException throwOderRecipientPhone(String clientId, String phone);
	public OrderException throwOrderRecipientEmail(String clientId, String email);
	
	
	//cart
	public ProductException throwCartGetSIdNull(String tokeId,int sId);
	public CustomerExpired throwCartGetRedisIdNull(String tokenId);
	
	//RenewData
	public TylBaseException throwRenewDataNullClientId(String tokenId);
	public TylBaseException throwRenewDataPasswdNotEq(String tokenId); 
	
	//Forget
	public ReNewException throwForgetPathDestUnknow(String data);
	public ReNewException throwForgetPathNullSubToken(String data);
	public ReNewException throwForgetPathInvalidToken(String token, String data, Throwable cause);
	public ReNewException throwForgetPathIssInvalidToken(String token, String data);
	public ReNewException throwForgetPathExpiredToken(String token, String data);
	public ReNewException throwForgetPathNullValue(String data);
	public ReNewException throwForgetPathNullKey(String key, String data);
	public ReNewException throwForgetPathNullCookie(String data);
	public ForgetException throwForgetTokenIsExpired(String token);
	public ForgetException throwForgetTokenIdNull(String data);
	
	
	//Customer
	public TylBaseException throwNullEnable(String tokenId);
	public TylBaseException throwDateFormat(String date);
	//Register
	public TylBaseException throwPatterRocId(String guestId);
	public TylBaseException throwNotComparePasswd(String guestId);
	public TylBaseException throwPatternPasswd(String guestId);
	public TylBaseException throwDayAfterNow(String guestId, Date date);
	public TylBaseException throwPatternEmail(String guestId, String email);
	public TylBaseException throwPatternPhone(String guestId, String phone);
	public TylBaseException throwAlreadyRegisterByEmail(String data);
	public TylBaseException throwAlreadyRegisterByPhone(String data);
	public TylBaseException throwRegisterInsert(CustomerEntity entity, String guestId);
	public TylBaseException throwUserStatus(String clientId);
	
	//Token
	public TylBaseException throwNullSubToken(String log);
	public TylBaseException throwIssInvalidToken(String log);
	public TylBaseException throwExpiredToken(String log);
	public TylBaseException throwInvalidToken(String log);
	
	//Cookie
	public TylBaseException throwNullLoginValueCookie(String log);
	public TylBaseException throwNullLoginKeyCookie(String log);
	public TylBaseException throwNullLoginCookie(String log);
	public TylBaseException throwNullCaptchaValueCookie(String log);
	public TylBaseException throwNullCaptchaKeyCookie(String log);
	public TylBaseException throwNullCookie(String log);
	
	//Passwd
	public TylBaseException throwPasswdPattern(String guestId);
	public TylBaseException throwNotEqPasswd(String guestId, String clientId);
	
	//User
	public TylBaseException throwUserDataEmpty(String guestId, String user);
	public TylBaseException throwUserMode(String guestId, String user);	
	
	//Captcha
	public TylBaseException throwIoGetCaptchaImage(String guestId, Throwable cause);
	public TylBaseException throwNotEqualCaptcha(String guestId);
	public TylBaseException throwNullCpathcaByServer(String guestId);
	public TylBaseException throwNullCpathcaByServer(String guestId, Throwable cause);
	

}
