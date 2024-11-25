package iot.tyl.dao;

import iot.tyl.model.CustomerEntity;

public interface CustomerDao {
	public CustomerEntity getCustomerByPhone(Integer phone);
	public CustomerEntity getCustomerByEmail(String email);
	public void insertCustomer(CustomerEntity entity);
	public void updatePasswd(String clientId, String passwd);
	public void updateStatus(String clientId);
	public CustomerEntity getCustomerByClient(String clientId);
}
