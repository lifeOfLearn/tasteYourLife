package iot.tyl.model.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import iot.tyl.model.Category;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.mapper.Mapper;

@Component
public class CategoryMapper implements Mapper<List<Category>, List<ProductResponse>>{
	
	
	@Override
	public List<ProductResponse> toDto(List<Category> entities) {
		List<ProductResponse> list = new ArrayList<>();
		entities.forEach(entity -> {
			ProductResponse p = new ProductResponse();
			p.setCId(entity.getCategoryId());
			p.setCName(entity.getCategoryName());
			list.add(p);
		});
		
		return list;
	}
	
	@Override
	public List<Category> toEntity(List<ProductResponse> dto) {
		return null;
	}

}
