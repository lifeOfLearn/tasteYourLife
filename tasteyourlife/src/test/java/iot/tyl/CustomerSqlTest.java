package iot.tyl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import iot.tyl.dao.CustomerDao;
import iot.tyl.dao.CustomerRepositoryJpa;
import iot.tyl.model.CustomerEntity;

@SpringBootTest
public class CustomerSqlTest {

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private CustomerRepositoryJpa customerRepositoryJpa;
	
	@Test
	public void test() {
		String clientId = "9a336116-8abd-11ef-a5d7-e45f01c03bca";
		CustomerEntity entity = customerRepositoryJpa.findByClientId(clientId);
		System.out.println(entity);
	}
}
