package iot.tyl.service;

import iot.tyl.model.CustomerData;
import iot.tyl.model.CustomerDto;
import iot.tyl.model.ReNewPasswdRequest;
import iot.tyl.model.LoginRequest;
import iot.tyl.model.ForgetRequest;
import iot.tyl.model.RegisterRequest;
import iot.tyl.model.UpdateRequest;

public interface CustomerService {
	public CustomerDto login(LoginRequest request, String guestId);
	public void register(RegisterRequest request, String guestId);
	public void logout(String tokenId);
	public void forget(ForgetRequest request, String guestId);
	public void enable(String tokenId);
	public void update(UpdateRequest request, String tokenId);
	public String getUser(String tokenId);
	public void reNewPasswd(String guestId, String tokenId, ReNewPasswdRequest request);
	public CustomerData getUserData(String tokenId);
}
