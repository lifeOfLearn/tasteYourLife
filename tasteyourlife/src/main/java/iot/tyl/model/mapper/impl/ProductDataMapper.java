package iot.tyl.model.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.tyl.config.PathConfig;
import iot.tyl.model.ProductData;
import iot.tyl.model.ProductPrice;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.mapper.Mapper;

@Component
public class ProductDataMapper implements Mapper<List<ProductData>, ProductResponse>{

	@Autowired
	private PathConfig pathConfig;
	
	@Override
	public ProductResponse toDto(List<ProductData> entities) {
		ProductResponse dto = new ProductResponse();
		if (entities.size() >= 0) {
			ProductData entity = entities.get(0);
			dto.setPId(entity.getProductId());
			dto.setPName(entity.getProductName());
			dto.setPInfo(entity.getProductInfo());
			dto.setImagePath(pathConfig.getRoot() + "/img/" + entity.getPath());
			dto.setCreatTime(entity.getCreateTime());
			dto.setCId(entity.getCategoryId());
			dto.setCName(entity.getCategoryName());
			dto.setProductPrice(new ArrayList<ProductPrice>());
			for (int i = 0; i < entities.size(); i++) {
				ProductPrice pp = new ProductPrice();
				ProductData pd = entities.get(i);
				pp.setSId(pd.getSaleId());
				pp.setAId(pd.getAddId());
				pp.setAName(pd.getAddName());
				pp.setAPrice(pd.getAddPrice());
				pp.setSize(pd.getSize());
				pp.setPrice(pd.getPrice());
				dto.getProductPrice().add(pp);
			}
		}
		return dto;
	}
	
	@Override
	public List<ProductData> toEntity(ProductResponse dto) {
		// TODO Auto-generated method stub
		return null;
	}
}
