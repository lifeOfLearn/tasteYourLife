package iot.tyl.model.mapper.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.tyl.model.CustomerEntity;
import iot.tyl.model.RegisterRequest;
import iot.tyl.model.mapper.Mapper;
import iot.tyl.util.ExceptionUtil;
import iot.tyl.util.contant.BloodType;
import iot.tyl.util.contant.GenderType;

@Component
public class RegisterMapper implements Mapper<CustomerEntity, RegisterRequest>{
	
	@Autowired
	private ExceptionUtil exceptionUtil;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public RegisterRequest toDto(CustomerEntity entity) {
		RegisterRequest dto = new RegisterRequest();
		dto.setName(entity.getName());
		dto.setPhone("0" + entity.getPhone());
		dto.setEmail(entity.getEmail());
		
		dto.setBirthday(formatter.format(entity.getBirthday()));
		dto.setRocid(entity.getRocid());
		dto.setPassword(entity.getPassword());
		dto.setBlood(entity.getBloodtype().name());
		dto.setGender(entity.getGenderChar());
		dto.setAddress(entity.getAddress());
		return dto;
	}
	
	@Override
	public CustomerEntity toEntity(RegisterRequest dto) {
		CustomerEntity entity = new CustomerEntity();
		entity.setName(dto.getName());
		entity.setPhone(Integer.valueOf(dto.getPhone().substring(1)));
		entity.setEmail(dto.getEmail());
		entity.setRocid(dto.getRocid());
		entity.setPassword(dto.getPassword());
		entity.setBloodtype(BloodType.getBloodType(dto.getBlood()));
		entity.setGender(GenderType.getGenderTypeFromCode(dto.getGender()));
		entity.setGenderChar(entity.getGender().getCode());
		String address = dto.getAddress();
		if (address == null)
			address = "";
		entity.setAddress(address);
		try {
			entity.setBirthday(formatter.parse(dto.getBirthday()));
		} catch (ParseException e) {
			throw exceptionUtil.throwDateFormat(dto.getBirthday());
		}
		return entity;
	}

}
