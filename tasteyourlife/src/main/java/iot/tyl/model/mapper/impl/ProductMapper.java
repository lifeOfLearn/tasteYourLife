package iot.tyl.model.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.tyl.config.PathConfig;
import iot.tyl.model.Product;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.mapper.Mapper;

@Component
public class ProductMapper implements Mapper<List<Product>, List<ProductResponse>>{

	@Autowired
	private PathConfig pathConfig;
	
	@Override
	public List<ProductResponse> toDto(List<Product> entities) {
		List<ProductResponse> list = new ArrayList<>();
		entities.forEach(entity -> {
			ProductResponse p = new ProductResponse();
			p.setPId(entity.getProductId());
			p.setPName(entity.getProductName());
			p.setPInfo(entity.getProductInfo());
			p.setImagePath(pathConfig.getRoot() + "/img/" + entity.getPath());
			p.setCId(entity.getCategory().getCategoryId());
			p.setCName(entity.getCategory().getCategoryName());
			p.setCreatTime(entity.getCreateTime());
			list.add(p);
		});
		return list;
//		return entities.stream()
//				.flatMap(entity -> entityStream(entity).stream())
//				.toList();
	}
	 
//	private List<ProductResponse> entityStream (Product entity) {
//		List<ProductResponse> responses = new ArrayList<>();
////		List<ProductAddEntity> adds = entity.getProductAdd();
////		List<ProductSaleEntity> sales = entity.getProductSale();
//		ProductResponse response = new ProductResponse();
//		ProductCategoryEntity category = entity.getCategory();
//		 
//		response.setPId(entity.getProductId());
//		response.setPName(entity.getProductName());
//		response.setPInfo(entity.getProductInfo());
//		response.setImagePath(entity.getPath());
//		response.setCreatTime(entity.getCreateTime());
//		response.setCId(category.getCategoryId());
//		response.setCName(category.getCategoryName());
//		
//		for (int i = 0; i < entity.getProductAdd().size(); i++) {
//			ProductAddEntity add = adds.get(i);
//			ProductSaleEntity sale = sales.get(i);
//			
//			int addPrice = add.getAddPrice();
//			double price = sale.getPrice();
//			
//			response.setAId(add.getAddId());
//			response.setAName(add.getAddName());
//			response.setAPrice(addPrice);
//			response.setPrice(price);
//			response.setTotal(addPrice + price);
//			
//			responses.add(response);
//		}
//		return responses;
//	}

	@Override
	public List<Product> toEntity(List<ProductResponse> dtos) {
		//TODO
		return null;
	}
}
