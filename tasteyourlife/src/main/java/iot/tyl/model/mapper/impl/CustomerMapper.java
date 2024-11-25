package iot.tyl.model.mapper.impl;

import org.springframework.stereotype.Component;

import iot.tyl.model.CustomerDto;
import iot.tyl.model.CustomerEntity;
import iot.tyl.model.mapper.Mapper;

@Component
public class CustomerMapper implements Mapper<CustomerEntity, CustomerDto>{

	@Override
	public CustomerDto toDto(CustomerEntity entity) {
		return CustomerDto
				.builder()
				.user(entity.getName())
				.tokenId(entity.getTokenId())
				.build();
	}

	@Override
	public CustomerEntity toEntity(CustomerDto dto) {
		CustomerEntity entity = new CustomerEntity();
		entity.setName(dto.getUser());
		entity.setTokenId(dto.getTokenId());
		return entity;
	}
	
	
}
