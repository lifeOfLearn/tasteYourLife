package iot.tyl.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import iot.tyl.model.CustomerEntity;

public interface CustomerRepositoryJpa extends JpaRepository<CustomerEntity, String> {
	CustomerEntity findByPhone(Integer phone);
	CustomerEntity findByEmail(String email);
	CustomerEntity findByClientId(String clientId);
}
