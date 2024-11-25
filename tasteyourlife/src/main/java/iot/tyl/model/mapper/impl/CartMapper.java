package iot.tyl.model.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.tyl.config.PathConfig;
import iot.tyl.model.CartEntity;
import iot.tyl.model.Category;
import iot.tyl.model.Product;
import iot.tyl.model.ProductAddEntity;
import iot.tyl.model.ProductPrice;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.ProductSaleEntity;
import iot.tyl.model.mapper.Mapper;

@Component
public class CartMapper implements Mapper<List<CartEntity>, List<ProductResponse>>{
	@Autowired
	private PathConfig pathConfig;
	
	@Override
	public List<ProductResponse> toDto(List<CartEntity> entities) {
		return entities.stream()
				.map(this::entityToResponse)
				.collect(Collectors.toList());
	}
	 
	private ProductResponse entityToResponse (CartEntity entity) {
		ProductResponse response = new ProductResponse();
		ProductSaleEntity ps = entity.getSale();
		ProductAddEntity add = ps.getProductAdd();
		Product product = ps.getProduct();
		Category category = product.getCategory();
		response.setPId(product.getProductId());
		response.setPName(product.getProductName());
		response.setPInfo(product.getProductInfo());
		response.setImagePath(pathConfig.getRoot() + "/img/" + product.getPath());
		response.setCId(category.getCategoryId());
		response.setCName(category.getCategoryName());
		response.setCreatTime(product.getCreateTime());
		response.setQty(entity.getQty());
		
		List<ProductPrice> list = new ArrayList<>();
		ProductPrice listElement = new ProductPrice();
		int addPrice = add.getAddPrice();
		double price = ps.getPrice();
		listElement.setAId(add.getAddId());
		listElement.setAName(add.getAddName());
		listElement.setSize(ps.getSize());
		listElement.setAPrice(addPrice);
		listElement.setPrice(price);
		listElement.setSId(ps.getSaleId());
		list.add(listElement);
		response.setProductPrice(list);
		
		return response;
	}

	@Override
	public List<CartEntity> toEntity(List<ProductResponse> dtos) {
		//TODO
		return null;
	}
}
