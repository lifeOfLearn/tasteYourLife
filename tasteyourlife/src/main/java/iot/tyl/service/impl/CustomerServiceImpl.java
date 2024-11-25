package iot.tyl.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iot.tyl.config.ParamConfig;
import iot.tyl.dao.CustomerDao;
import iot.tyl.exception.TylBaseException;
import iot.tyl.model.CustomerData;
import iot.tyl.model.CustomerDto;
import iot.tyl.model.CustomerEntity;
import iot.tyl.model.ForgetRequest;
import iot.tyl.model.LoginRequest;
import iot.tyl.model.ReNewPasswdRequest;
import iot.tyl.model.RegisterRequest;
import iot.tyl.model.UpdateRequest;
import iot.tyl.model.mapper.Mapper;
import iot.tyl.service.CaptchaService;
import iot.tyl.service.CustomerService;
import iot.tyl.service.EncryptService;
import iot.tyl.service.MailService;
import iot.tyl.service.RedisService;
import iot.tyl.util.CustomerUtil;
import iot.tyl.util.ExceptionUtil;
import iot.tyl.util.contant.RedisMapKey;
import iot.tyl.util.contant.Status;
import iot.tyl.util.contant.UserMode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CaptchaService captchaService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private EncryptService encryptService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private Mapper<CustomerEntity, CustomerDto> customerMapper;

	@Autowired
	private Mapper<CustomerEntity, RegisterRequest> registerMapper;
	
	@Autowired
	private Mapper<CustomerEntity, CustomerData> dataMapper;
	
	@Autowired
	private CustomerUtil customerUtil;
	
	@Autowired
	private ExceptionUtil exceptionUtil;
	
	@Autowired
	private ParamConfig paramConfig;
	

	@Override
	public String getUser(String tokenId) {
		try {
			String clientId = redisService.getValue(tokenId);
			return (String)redisService.getMap(clientId).get(RedisMapKey.name.name());
		} catch (Exception e) {
			throw exceptionUtil.throwInvalidToken(tokenId);
		}
	}
	
	@Override
	public CustomerDto login(LoginRequest request, String guestId) {
		String captcha = request.getCaptcha();
		captchaOk(guestId, captcha);
		String user = request.getUser();
		String passwd = request.getPasswd();
		UserMode userMode = getUserMode(user, guestId);
		passwdPattern(passwd, guestId);
		
		CustomerEntity entity = getUserData(user, userMode, guestId);
		boolean passwdEq = encryptService.checkPassword(passwd, entity.getPassword());
		Status status = entity.getStatus();
//		String tokenId = entity.getTokenId().toString();
		String clientId = entity.getClientId().toString();
		checkStatus(status, clientId);
		if (passwdEq) {
//			saveLogin(tokenId, clientId, entity.getName());
			saveLoginMap(entity);
			redisService.deleteValue(guestId);
			return customerMapper.toDto(entity);
		}
		
		mailService.sendPasswdErr(entity.getEmail());
		throw exceptionUtil.throwNotEqPasswd(guestId, entity.getClientId().toString());
	}

	@Override
	public void register(RegisterRequest request, String guestId) {
		String captcha = request.getCaptcha();
		captchaOk(guestId, captcha);
		checkAllData(request, guestId);
		alreadyRegisterCheck(request, guestId);
		CustomerEntity entity = registerMapper.toEntity(request);
		setPassswd(entity, request.getPassword());
		setEntity(entity);
		insert(entity, guestId);
		saveRegister(entity.getTokenId().toString(), entity.getClientId().toString());
		sendRegister(entity);
		redisService.deleteValue(guestId);
	}


	@Override
	public void enable(String tokenId) {
		String clientId = getClientId(tokenId);
		customerDao.updateStatus(clientId);
		redisService.deleteValue(tokenId);
	}
	
	@Override
	public void forget(ForgetRequest request, String guestId) {
		String user = request.getUser();
		String captcha = request.getCaptcha();
		captchaOk(guestId, captcha);
		UserMode userMode = getUserMode(user, guestId);
		CustomerEntity entity;
		try {
			entity = getUserData(user, userMode, guestId);
			saveForget(entity.getTokenId().toString(), entity.getClientId().toString());
			sendForget(entity);
		} catch (TylBaseException ex) {
			//不動作,不要回傳帳號不存在給人
			log.error("忘記密碼輸入的帳號不存在",ex);
		}
		redisService.deleteValue(guestId);
	}
	
	@Override
	public void reNewPasswd(String guestId, String tokenId, ReNewPasswdRequest request) {
		captchaOk(guestId, request.getCaptcha());
		String passwd1 = request.getPassword();
		String passwd2 = request.getPassword2();
		if (passwd1.equals(passwd2)) {
			passwdPattern(passwd1, tokenId);
			String passwd = encryptService.encrypt(passwd1);
			String clientId = getRenewClientId(tokenId);
			customerDao.updatePasswd(clientId, passwd);
			redisService.deleteValue(guestId);
			redisService.deleteValue(tokenId);
			return;
		}
		throw exceptionUtil.throwRenewDataPasswdNotEq(tokenId);
	}
	
	@Override
	public void logout(String tokenId) {
			String clientId = get(tokenId);
			redisService.deleteValue(clientId);
			redisService.deleteValue(tokenId);
	}
	
	
	@Override
	public void update(UpdateRequest request, String tokenId) {
		checkPasswdEq(request);
		//TODO
	}
	
	@Override
	public CustomerData getUserData(String tokenId) {
		String clientId = get(tokenId);
		CustomerEntity entity = customerDao.getCustomerByClient(clientId);
		return dataMapper.toDto(entity);
	}
	
	private void setPassswd(CustomerEntity entity, String password) {
		String passwd = encryptService.encrypt(password);
		entity.setPassword(passwd);
	}
	private void setEntity(CustomerEntity entity) {
		entity.setId(UUID.randomUUID().toString());
		entity.setClientId(UUID.randomUUID().toString());
		entity.setTokenId(UUID.randomUUID().toString());
		entity.setStatus(Status.N);
	}

	private boolean checkPasswdEq(UpdateRequest request) {
		String passwd = request.getPassword();
		String passwd2 = request.getPassword2();
		if (passwd.equals(passwd2))
			return true;
		
		return false;//TODO throw
	}
	
	private void sendRegister(CustomerEntity entity) {
		String email = entity.getEmail();
		String tokenId = entity.getTokenId().toString();
		mailService.sendRegister(email, tokenId);
	}
	
	private void sendForget(CustomerEntity entity) {
		String email = entity.getEmail();
		String tokeId = entity.getTokenId().toString();
		mailService.sendForget(email, tokeId);
	}
	
	private void insert(CustomerEntity entity, String guestId) {
		try {
			customerDao.insertCustomer(entity);
			
		} catch (Exception e) {
			exceptionUtil.throwRegisterInsert(entity, guestId);
		}
	}
	
	private boolean alreadyRegisterCheck(RegisterRequest request, String guestId) {
		String phone = request.getPhone().substring(1);
		if (customerDao.getCustomerByPhone(
				Integer.valueOf(phone) )  != null) {
			throw exceptionUtil.throwAlreadyRegisterByPhone(phone);	
		}
		String email = request.getEmail();
		if (customerDao.getCustomerByEmail(email) != null) {
			throw exceptionUtil.throwAlreadyRegisterByEmail(email);
		}
		return true;
	}

	private boolean checkAllData(RegisterRequest request, String guestId) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		checkPhone(request.getPhone(), guestId);
		checkEmail(request.getEmail(), guestId);
		checkRocId(request.getRocid(), guestId);
		checkPassword(request.getPassword(), request.getPassword2(), guestId);
		try {
			checkBirthday(formatter.parse(request.getBirthday()), guestId);
		} catch (ParseException e) {
			throw exceptionUtil.throwDateFormat(request.getBirthday());
		}
		return true;
	}

	private boolean checkRocId(String rocId, String guestId) {
		if (customerUtil.checkRocId(rocId))
			return true;
		
		throw exceptionUtil.throwPatterRocId(guestId);
	}
	
	private boolean checkPassword(String passwd1, String passwd2, String guestId) {
		if(passwd1.equals(passwd2)) {
			if(customerUtil.checkPasswd(passwd1))
				return true;
		
			throw exceptionUtil.throwPatternPasswd(guestId);
		}
		throw exceptionUtil.throwNotComparePasswd(guestId);
	}
	
	private boolean checkBirthday(Date birthday, String guestId) {
		if (customerUtil.checkDate(birthday))
			return true;
		
		throw exceptionUtil.throwDayAfterNow(guestId, birthday);
	}
	
	private boolean checkEmail(String mail, String guestId) {
		if (customerUtil.checkMail(mail))
			return true;
		
		throw exceptionUtil.throwPatternEmail(guestId, mail);
	}
	
	private boolean checkPhone(String phone, String guestId) {
		if (customerUtil.checkPhone(phone))
			return true;
		
		throw exceptionUtil.throwPatternPhone(guestId, phone);
	}
	
	private boolean checkStatus(Status status, String clientId) {
		if (status.equals(Status.Y))
			return true;
		
		throw exceptionUtil.throwUserStatus(clientId);
	}
	
	private CustomerEntity getUserData(String user, UserMode mode, String guestId) {
		switch (mode) {
		case PHONE:
			return getByPhone(Integer.valueOf(user.substring(1)), guestId);
		case EMAIL: 
			return getByEmail(user, guestId);
		}
		
		throw exceptionUtil.throwUserMode(guestId, user);
	}
	
	private CustomerEntity getByPhone(Integer phone, String guestId) {
		CustomerEntity entity = customerDao.getCustomerByPhone(phone);
		if (entity != null)
			return entity;
		
		throw exceptionUtil.throwUserDataEmpty(guestId, phone.toString());
	}
	
	private CustomerEntity getByEmail(String email, String guestId) {
		CustomerEntity entity = customerDao.getCustomerByEmail(email);
		if (entity != null)
			return entity;
		
		throw exceptionUtil.throwUserDataEmpty(guestId, email);
	}
	
	private boolean passwdPattern(String passwd, String id) {
		boolean isOk = customerUtil.checkPasswd(passwd);
		if (isOk != false)
			return true;
		
		throw exceptionUtil.throwPasswdPattern(id);
	}
	
	private UserMode getUserMode(String user, String guestId) {
		UserMode userMode = customerUtil.getUserMode(user);
		if (userMode != null)
			return userMode;
		
		throw exceptionUtil.throwUserMode(guestId, user);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws
	 */
	private boolean captchaOk(String id, String captcha) {
		return captchaService.checkCaptcha(id, captcha);
	}
	
	
	private String getRenewClientId(String tokenId) {
		try {
			String key = get(tokenId);
			if (key != null)
				return key;
		
		
		} catch (Exception e) {
		}
		throw exceptionUtil.throwRenewDataNullClientId(tokenId);
	}
	
	private String getClientId(String tokenId) {
		try {
			String key = get(tokenId);
			return key;
			
		} catch (Exception e) {
			throw exceptionUtil.throwNullEnable(tokenId);
		}
	}
	
	private String get(String key) {
		return redisService.getValue(key);
	}
	
	private void saveForget(String tokenId, String clientId) {
		long sec = paramConfig.getSec().getForget();
		save(tokenId, clientId, sec);
	}
	
	private void saveRegister(String tokenId, String clientId) {
		long sec = paramConfig.getSec().getRegister();
		save(tokenId, clientId, sec);
	}
	
//	private void saveLogin(String tokenId, String clientId, String name) {
//		long sec = paramConfig.getSec().getLogin();
//		save(tokenId, clientId, sec);
//		save(clientId, name, sec);
//	}
	
	private void saveLoginMap(CustomerEntity entity) {
		long sec = paramConfig.getSec().getLogin();
		String key = entity.getTokenId();
		String keyValue = entity.getClientId();
		Map<String,Object> valueMap = new HashMap<>();
		valueMap.put(RedisMapKey.id.name(), entity.getId());
		valueMap.put(RedisMapKey.name.name(), entity.getName());
		save(key, keyValue, sec);
		save(keyValue, valueMap, sec);
	}
	
	private void save(String key, Map<String, Object> map, long sec) {
		redisService.saveMap(key, map, sec);
	}
	
	private void save(String key, String value, long sec) {
		redisService.saveValue(key, value, sec);
	}
}
