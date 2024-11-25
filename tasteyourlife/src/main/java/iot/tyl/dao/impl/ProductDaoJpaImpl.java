package iot.tyl.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import iot.tyl.dao.CategoryJpa;
import iot.tyl.dao.HotJpa;
import iot.tyl.dao.ProductDao;
import iot.tyl.dao.ProductDataJpa;
import iot.tyl.dao.ProductJpa;
import iot.tyl.model.Category;
import iot.tyl.model.Hot;
import iot.tyl.model.Product;
import iot.tyl.model.ProductData;

@Repository
public class ProductDaoJpaImpl implements ProductDao{
	
	@Autowired
	private HotJpa hotJpa;
	
	@Autowired
	private ProductJpa productJpa;
	
	@Autowired
	private CategoryJpa categoryJpa;
	
	@Autowired
	private ProductDataJpa productDataJpa;
	
	@Override
	public List<Hot> getHot(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return hotJpa.findAll(pageable).getContent();
	}
	
	@Override
	public List<Product> getNew(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productJpa.findByOrderByCreateTimeDesc(pageable);
	}
	
	@Override
	public List<Category> getAllCategory() {
		return categoryJpa.findByOrderByCategoryId();
	}
	
	@Override
	public List<Product> getACategoryById(int id, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productJpa.findByCategory_CategoryId(id, pageable);
	}
	
	@Override
	public List<Product> getProducts(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productJpa.findByOrderByProductId(pageable);
	}
	
	@Override
	public List<ProductData> getProductById(int id) {
		return productDataJpa.findByProductId(id);
	}
	
	@Override
	public List<Product> like(String key, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		//return productJpa.findByProductNameContaining(key, pageable);
		return productJpa.findByProductNameContainingOrProductInfoContaining(key, key, pageable);
	}

	@Override
	public ProductData getProductBySId(int id) {
		return productDataJpa.findBySaleId(id);
	}
}
