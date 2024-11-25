package iot.tyl.dao.impl;

import org.springframework.stereotype.Repository;

import iot.tyl.dao.CustomerDao;
import iot.tyl.dao.CustomerRepositoryJpa;
import iot.tyl.model.CustomerEntity;
import iot.tyl.util.contant.Status;

@Repository
public class CustomerDaoJpaImpl implements CustomerDao{
	
	private final CustomerRepositoryJpa customerJpaDao;
	
	public CustomerDaoJpaImpl(CustomerRepositoryJpa customerJpaDao) {
		this.customerJpaDao = customerJpaDao;
	}
	
	@Override
	public CustomerEntity getCustomerByPhone(Integer phone) {
		return customerJpaDao.findByPhone(phone);
	}
	
	@Override
	public CustomerEntity getCustomerByEmail(String email) {
		return customerJpaDao.findByEmail(email);
	}
	
	@Override
	public void insertCustomer(CustomerEntity entity) {
		customerJpaDao.save(entity);
	}
	
	@Override
	public void updatePasswd(String clientId, String passwd) {
		CustomerEntity entity = customerJpaDao.findByClientId(clientId);
		entity.setPassword(passwd);
		entity.setStatus(Status.Y);
		customerJpaDao.save(entity);
	}
	
	@Override
	public void updateStatus(String clientId) {
		CustomerEntity entity = customerJpaDao.findByClientId(clientId);
		entity.setStatus(Status.Y);
		customerJpaDao.save(entity);
	}
	
	@Override
	public CustomerEntity getCustomerByClient(String clientId) {
		return customerJpaDao.findByClientId(clientId);
	}
}
