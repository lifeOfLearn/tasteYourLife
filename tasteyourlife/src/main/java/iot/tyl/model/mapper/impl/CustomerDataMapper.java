package iot.tyl.model.mapper.impl;

import org.springframework.stereotype.Component;

import iot.tyl.model.CustomerData;
import iot.tyl.model.CustomerEntity;
import iot.tyl.model.mapper.Mapper;

@Component
public class CustomerDataMapper implements Mapper<CustomerEntity, CustomerData>{

	@Override
	public CustomerData toDto(CustomerEntity entity) {
		CustomerData data = new CustomerData();
		String name = entity.getName();
		String phone = "0" + entity.getPhone();
		String email = entity.getEmail();
		if (name.length() == 2) {
			name = name.charAt(0) + "*";
		} else {
			name = name.replaceAll("(?<=^.).(?=.$)","*");
		}
		phone = phone.replaceAll("(?<=^.{4}).(?=.{3}$)", "*");
		email = email.replaceAll("(?<=^.{2}).(?=@.*)", "*");
		data.setName(name);
		data.setPhone(phone);
		data.setMail(email);
		data.setAddress(entity.getAddress());
		return data;
	}

	@Override
	public CustomerEntity toEntity(CustomerData dto) {
		return null;
		//TODO
	}
	
	
}
