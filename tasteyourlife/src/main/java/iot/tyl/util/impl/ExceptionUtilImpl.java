package iot.tyl.util.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.tyl.config.ErrLogMsgConfig;
import iot.tyl.config.ErrMsgConfig;
import iot.tyl.exception.CustomerExpired;
import iot.tyl.exception.ForgetException;
import iot.tyl.exception.OrderException;
import iot.tyl.exception.ProductException;
import iot.tyl.exception.ReNewException;
import iot.tyl.exception.TylBaseException;
import iot.tyl.model.CustomerEntity;
import iot.tyl.service.JwtService;
import iot.tyl.util.ExceptionUtil;
import iot.tyl.util.contant.ErrorKeyType;
import iot.tyl.util.contant.TokenKey;

@Component
public class ExceptionUtilImpl implements ExceptionUtil{
	
	@Autowired
	private ErrMsgConfig errMsgConfig;
	
	@Autowired
	private ErrLogMsgConfig errLogMsgConfig;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public OrderException throwOrderCheckoutUnknow(String clientId, Throwable e) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getCheckout().getUnknow())
								.append(",clientId:").append(clientId)
								.append(",Cause:\n").append(e);
		return new OrderException(ErrorKeyType.order, errMsgConfig.getCheckout().getUnknow(), log.toString());
	}
	
	@Override
	public OrderException throwCheckoutGetUserNull(String clientId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getCheckout().getUserEmpty())
								.append(",client:").append(clientId);
		return new OrderException(ErrorKeyType.order, errMsgConfig.getCheckout().getUserEmpty(), log.toString());
	}
	
	@Override
	public OrderException throwOrderDataInvalid(String userId, int sId, int qty) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getOrder().getInvalid())
								.append(",userId:").append(userId)
								.append(",sId:").append(sId)
								.append(",qty:").append(qty);
		return new OrderException(ErrorKeyType.cart, errMsgConfig.getOrder().getInvalid(), log.toString());
	}
	
	@Override
	public OrderException throwOrderPaymentNoHasTheShipping(String clientId, String payment,  String shipping) {
		StringBuffer log = new StringBuffer()
									.append(errLogMsgConfig.getOrder().getTypeParse())
									.append(",clientId:").append(clientId)
									.append(",payment type:").append(payment)
									.append(",shipping type:").append(shipping);
		return new OrderException(ErrorKeyType.shipping, errMsgConfig.getOrder().getTypeParse(), log.toString());
	}
	
	@Override
	public OrderException throwOrderShppingType(String clientId, String shipping) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getOrder().getShippingType())
								.append(",clientId:").append(clientId)
								.append(",shipping type:").append(shipping);
		return new OrderException(ErrorKeyType.shipping, errMsgConfig.getOrder().getShippingType(), log.toString());
	}
	
	@Override
	public OrderException throwOrderPaymentType(String clientId, String payment) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getOrder().getPaymentType())
								.append(",clientId:").append(clientId)
								.append(",payment type:").append(payment);
		return new OrderException(ErrorKeyType.payment, errMsgConfig.getOrder().getPaymentType(), log.toString());
	}
	
	@Override
	public OrderException throwOrderRecipientEmail(String clientId, String email) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getOrder().getRecipient().getEmail())
								.append(",clientId:").append(clientId)
								.append(",email:").append(email);
		return new OrderException(ErrorKeyType.phone, errMsgConfig.getOrder().getRecipient().getEmail(), log.toString());
	}
	
	@Override
	public OrderException throwOderRecipientPhone(String clientId, String phone) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getOrder().getRecipient().getPhone())
								.append(",clientId:").append(clientId)
								.append(",phone:").append(phone);
		return new OrderException(ErrorKeyType.phone, errMsgConfig.getOrder().getRecipient().getPhone(), log.toString());
	}
	
	@Override
	public ProductException throwCartGetSIdNull(String tokeId, int sId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getCart().getSidEmpty())
								.append(",tokenId:").append(tokeId)
								.append(",sId=").append(sId);
		return new ProductException(ErrorKeyType.proudct, errMsgConfig.getCart().getSidEmpty(),	log.toString());
	}
	
	@Override
	public CustomerExpired throwCartGetRedisIdNull(String tokenId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getCart().getRedisEmpty())
								.append(",tokenId:").append(tokenId);
		
		return new CustomerExpired(ErrorKeyType.customerExpired, errMsgConfig.getCart().getRedisEmpty(), log.toString());
	}
	
	@Override
	public TylBaseException throwDateFormat(String date) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getCustomer().getDateFormat())
								.append(",data:").append(date);
		return new TylBaseException(ErrorKeyType.date, errMsgConfig.getCustomer().getDateFormat(), log.toString());
	}

	@Override
	public TylBaseException throwRenewDataNullClientId(String tokenId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getClientEmpty())
								.append(",token id:").append(tokenId);
		return new TylBaseException(ErrorKeyType.other, errMsgConfig.getForget().getPath().getClientEmpty(), log.toString());
	}
	
	@Override
	public TylBaseException throwRenewDataPasswdNotEq(String tokenId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getPasswdEq())
								.append(",token id:").append(tokenId);
		return new TylBaseException(ErrorKeyType.passwd2, errMsgConfig.getForget().getPath().getPasswdEq(), log.toString());
	}
	
	@Override
	public ReNewException throwForgetPathDestUnknow(String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getUnknow())
								.append(data);
		return new ReNewException(ErrorKeyType.other, errMsgConfig.getForget().getPath().getToken(), log.toString());
	}
	
	@Override
	public ReNewException throwForgetPathNullSubToken(String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getTokenSub())
								.append(data);
		return new ReNewException(ErrorKeyType.other, errMsgConfig.getForget().getPath().getToken(), log.toString());
	}
	
	@Override
	public ReNewException throwForgetPathInvalidToken(String token, String data, Throwable cause) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getTokenInvalid())
								.append(data);
		return new ReNewException(ErrorKeyType.other, errMsgConfig.getForget().getPath().getToken(), log.toString(), cause);
	}
	
	@Override
	public ReNewException throwForgetPathIssInvalidToken(String token, String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getTokenIss())
								.append(",iss:").append(jwtService.getValue(token, TokenKey.iss.name()))
								.append(data);
		return new ReNewException(ErrorKeyType.other, errMsgConfig.getForget().getPath().getToken(), log.toString());
	}
	
	@Override
	public ReNewException throwForgetPathExpiredToken(String token, String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getTokenExp())
								.append(",iat:").append(jwtService.getValue(token, TokenKey.iat.name()))
								.append(",exp:").append(jwtService.getValue(token, TokenKey.exp.name()))
								.append(data);
		return new ReNewException(ErrorKeyType.other, errMsgConfig.getForget().getPath().getToken(), log.toString());
	}

	@Override
	public ReNewException throwForgetPathNullValue(String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getValue())
								.append(data);
		return new ReNewException(ErrorKeyType.other, errMsgConfig.getForget().getPath().getValue(), log.toString());
	}
	
	@Override
	public ReNewException throwForgetPathNullKey(String key, String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getKey()).append(key)
								.append(data);
		return new ReNewException(ErrorKeyType.other, errMsgConfig.getForget().getPath().getKey(), log.toString());
	}
	
	@Override
	public ReNewException throwForgetPathNullCookie(String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getPath().getCookie())
								.append(data);
		return new ReNewException(ErrorKeyType.other, errMsgConfig.getForget().getPath().getCookie(), log.toString());
	}
	
	@Override
	public ForgetException throwForgetTokenIsExpired(String token) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getToken().getExpired())
								.append(TokenKey.sub.name()).append(":").append(jwtService.getValue(token, TokenKey.sub.name()))
								.append(TokenKey.iat.name()).append(":").append(jwtService.getValue(token, TokenKey.iat.name()))
								.append(TokenKey.exp.name()).append(":").append(jwtService.getValue(token, TokenKey.exp.name()));
		return new ForgetException(ErrorKeyType.other,  errMsgConfig.getForget().getToken().getExpired(), log.toString() );
	}
	
	@Override
	public ForgetException throwForgetTokenIdNull(String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getForget().getToken().getEmpty())
								.append(",")
								.append(data);
		return new ForgetException(ErrorKeyType.other, errMsgConfig.getForget().getToken().getEmpty(), log.toString());
	}
	
	@Override
	public TylBaseException throwNullEnable(String tokenId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getEnable().getServerEmpty())
								.append(tokenId);
		return baseOther(errMsgConfig.getEnable().getToken(), log.toString());
	}
	
	@Override
	public TylBaseException throwPatterRocId(String guestId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getRegister().getRocId())
								.append(guestId);
		return baseRocId(errMsgConfig.getRegister().getRocId(), log.toString());
	}
	
	@Override
	public TylBaseException throwNotComparePasswd(String guestId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getRegister().getPasswdDobule())
								.append(guestId);
		return basePasswd(errMsgConfig.getRegister().getPasswdDouble(), log.toString());
	}
	
	@Override
	public TylBaseException throwPatternPasswd(String guestId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getRegister().getPasswd())
								.append(guestId);
		return basePasswd(errMsgConfig.getRegister().getPasswd(), log.toString());
	}
	
	@Override
	public TylBaseException throwDayAfterNow(String guestId, Date date) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getRegister().getBirthday())
								.append(guestId)
								.append(",date=")
								.append(date);
		return baseDate(errMsgConfig.getRegister().getBirthday(), log.toString());
	}
	
	@Override
	public TylBaseException throwPatternEmail(String guestId, String email) {
		StringBuffer log = new StringBuffer()
							.append(errLogMsgConfig.getRegister().getEmail())
							.append(guestId)
							.append(",email=")
							.append(email);
		return baseEmail(errMsgConfig.getRegister().getEmail(), log.toString());
	}
	
	@Override
	public TylBaseException throwPatternPhone(String guestId, String phone) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getRegister().getPhone())
								.append(guestId)
								.append(",phone=")
								.append(phone);
		return basePhone(errMsgConfig.getRegister().getPhone(), log.toString());
	}
	
	@Override
	public TylBaseException throwAlreadyRegisterByEmail(String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getRegister().getRepeatEmail())
								.append(data);
		return basePhone(errMsgConfig.getRegister().getRepeatE(),
				 log.toString());
	}
	
	@Override
	public TylBaseException throwAlreadyRegisterByPhone(String data) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getRegister().getRepeatPhone())
								.append(data);
		return basePhone(errMsgConfig.getRegister().getRepeatP(),
						 log.toString());
	}
	
	@Override
	public TylBaseException throwRegisterInsert(CustomerEntity entity, String guestId) {
		StringBuffer log = new StringBuffer()
								.append(errLogMsgConfig.getRegister().getInsert())
								.append(guestId)
								.append(entity.errLog());
		return baseOther(errMsgConfig.getRegister().getFail(),
						 log.toString());
	}
	
	
	@Override
	public TylBaseException throwUserStatus(String clientId) {
		StringBuffer log = new StringBuffer()
									.append(errLogMsgConfig.getUser().getStatus())
									.append(clientId);
		return baseOther(errMsgConfig.getUser().getStatus(),
						log.toString());
	}
	
	@Override
	public TylBaseException throwNullSubToken(String log) {
		return baseOther(errMsgConfig.getToken().getInvalid(),
						 errLogMsgConfig.getToken().getSub() + log);
	}
	
	@Override
	public TylBaseException throwIssInvalidToken(String log) {
		return baseOther(errMsgConfig.getToken().getInvalid(),
						 errLogMsgConfig.getToken().getIss() + log);
	}
	
	@Override
	public TylBaseException throwExpiredToken(String log) {
		return baseOther(errMsgConfig.getToken().getInvalid(),
						 errLogMsgConfig.getToken().getExpired() + log);
	}
	
	@Override
	public TylBaseException throwInvalidToken(String log) {
		return baseOther(errMsgConfig.getToken().getInvalid(),
						 errLogMsgConfig.getToken().getInvalid() + log);
	}
	
	@Override
	public TylBaseException throwNullLoginValueCookie(String log) {
		return baseOther(errMsgConfig.getCookie().getLogin().getValue(),
						  errLogMsgConfig.getCookie().getLogin().getValue() + log);
	}
	
	@Override
	public TylBaseException throwNullLoginKeyCookie(String log) {
		return baseOther(errMsgConfig.getCookie().getLogin().getValue(),
						 errLogMsgConfig.getCookie().getLogin().getKey());
	}
	
	@Override
	public TylBaseException throwNullLoginCookie(String log) {
		return baseOther(errMsgConfig.getCookie().getInvalid(),
						  errLogMsgConfig.getCookie().getLogin().getEmpty() + log);
	}
	
	@Override
	public TylBaseException throwNullCaptchaValueCookie(String log) {
		return baseOther(errMsgConfig.getCookie().getCaptcha().getValue(),
						  errLogMsgConfig.getCookie().getLogin().getValue() + log);
	}
	
	@Override //2
	public TylBaseException throwNullCaptchaKeyCookie(String log) {
		return baseOther(errMsgConfig.getCookie().getCaptcha().getKey(),
						  errLogMsgConfig.getCookie().getCaptcha().getKey() + log);
	}
	
	@Override //1
	public TylBaseException throwNullCookie(String log) {
		return baseOther(errMsgConfig.getCookie().getInvalid(),
						  errLogMsgConfig.getCookie().getEmpty() + log);
	}
	
	@Override
	public TylBaseException throwNotEqPasswd(String guestId, String clientId) {
		StringBuffer log = new StringBuffer()
							.append(errLogMsgConfig.getPasswd().getInvalid())
							.append(guestId)
							.append(", client id : ")
							.append(clientId);
		return baseUser(errMsgConfig.getUserPasswd(),
						log.toString());
	}
	
	@Override
	public TylBaseException throwPasswdPattern(String guestId) {
		return basePasswd(errMsgConfig.getPasswd().getPattern(),
						errLogMsgConfig.getPasswd().getPattern()+ guestId);
	}
	
	@Override
	public TylBaseException throwUserDataEmpty(String guestId, String user) {
		StringBuffer log = new StringBuffer()
							.append(errLogMsgConfig.getUser().getDataEmpty())
							.append(guestId)
							.append(", user : ")
							.append(user);
		return baseOther(errMsgConfig.getUserPasswd(),
						log.toString());
	}
	
	@Override
	public TylBaseException throwUserMode(String guestId, String user) {
		StringBuffer log = new StringBuffer()
									.append(errLogMsgConfig.getUser().getPattern())
									.append(guestId)
									.append(", user : ")
									.append(user);
		return baseUser(errMsgConfig.getUser().getPattern(),
						log.toString() );
	}
	
	@Override
	public TylBaseException throwIoGetCaptchaImage(String guestId, Throwable cause) {
		return baseCaptcha(errMsgConfig.getCaptcha().getIo(),
							errLogMsgConfig.getCaptcha().getIo() + guestId,
							cause);
	}
	
	public TylBaseException throwNotEqualCaptcha(String guestId) {
		return baseCaptcha(errMsgConfig.getCaptcha().getError(),
							errLogMsgConfig.getCaptcha().getUnequal() + guestId);
	}
	
	public TylBaseException throwNullCpathcaByServer(String guestId) {
		return baseCaptcha(errMsgConfig.getCaptcha().getError(),
							errLogMsgConfig.getCaptcha().getServerEmpty() + guestId);
	}
	
	@Override
	public TylBaseException throwNullCpathcaByServer(String guestId, Throwable cause) {
		return baseCaptcha(errMsgConfig.getCaptcha().getError(),
							errLogMsgConfig.getCaptcha().getServerEmpty() + guestId,
							cause);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	private TylBaseException baseOther(String value, String log, Throwable cause) {
		return base(ErrorKeyType.other, value, log, cause);
	}
	
	private TylBaseException baseOther(String value, String log) {
		return base(ErrorKeyType.other, value, log);
	}
	
	private TylBaseException baseUser(String value, String log , Throwable cause) {
		return base(ErrorKeyType.user, value, log, cause);
	}
	
	private TylBaseException baseUser(String value, String log) {
		return base(ErrorKeyType.user, value, log);
	}
	
	private TylBaseException basePhone(String value, String log, Throwable cause) {
		return base(ErrorKeyType.phone, value, log, cause);
	}
	
	private TylBaseException basePhone(String value, String log) {
		return base(ErrorKeyType.phone, value, log);
		
	}
	
	private TylBaseException baseDate(String value, String log, Throwable cause) {
		return base(ErrorKeyType.date, value, log, cause);
	}
	
	private TylBaseException baseDate(String value, String log) {
		return base(ErrorKeyType.date, value, log);
		
	}
	
	private TylBaseException basePasswd(String value, String log, Throwable cause) {
		return base(ErrorKeyType.passwd, value, log, cause);
	}
	
	private TylBaseException basePasswd(String value, String log) {
		return base(ErrorKeyType.passwd, value, log);
		
	}

	private TylBaseException basePasswd2(String value, String log, Throwable cause) {
		return base(ErrorKeyType.passwd, value, log, cause);
	}
	
	private TylBaseException basePasswd2(String value, String log) {
		return base(ErrorKeyType.passwd, value, log);
		
	}
	
	private TylBaseException baseEmail(String value, String log, Throwable cause) {
		return base(ErrorKeyType.email, value, log, cause);
	}
	
	private TylBaseException baseEmail(String value, String log) {
		return base(ErrorKeyType.email, value, log);
		
	}
	
	private TylBaseException baseRocId(String value, String log, Throwable cause) {
		return base(ErrorKeyType.rocid, value, log, cause);
	}
	
	private TylBaseException baseRocId(String value, String log) {
		return base(ErrorKeyType.rocid, value, log);
		
	}
	
	private TylBaseException baseCaptcha(String value, String log, Throwable cause) {
		return base(ErrorKeyType.captcha, value, log, cause);
	}
	
	private TylBaseException baseCaptcha(String value, String log) {
		return base(ErrorKeyType.captcha, value, log);
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	private TylBaseException base() {
		return new TylBaseException();
	}
	
	private TylBaseException base(String log) {
		return new TylBaseException(log);
	}
	
	private TylBaseException base(String log, Throwable cause) {
		return new TylBaseException(log, cause);
	}
	
	private TylBaseException base(ErrorKeyType key, String value, String log) {
		return new TylBaseException(key, value, log);
	}
	
	private TylBaseException base(ErrorKeyType key ,String value, String log, Throwable cause) {
		return new TylBaseException(key, value, log, cause);
	}
	
	
}
