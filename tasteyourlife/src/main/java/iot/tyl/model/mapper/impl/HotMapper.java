package iot.tyl.model.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.tyl.config.PathConfig;
import iot.tyl.model.Hot;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.mapper.Mapper;

@Component
public class HotMapper implements Mapper<List<Hot>, List<ProductResponse>>{
	
	@Autowired
	private PathConfig pathConfig;
	
	@Override
	public List<ProductResponse> toDto(List<Hot> entities) {
		List<ProductResponse> list = new ArrayList<>();
		entities.forEach(entity -> {
			ProductResponse p = new ProductResponse();
			p.setPId(entity.getProductId());
			p.setPName(entity.getProductName());
			p.setPInfo(entity.getProductInfo());
			p.setImagePath(pathConfig.getRoot() + "/img/" + entity.getPath());
			list.add(p);
		});
		
		return list;
	}
	
	@Override
	public List<Hot> toEntity(List<ProductResponse> dto) {
		return null;
	}

}
