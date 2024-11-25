package iot.tyl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import iot.tyl.dao.ProductDao;
import iot.tyl.model.Category;
import iot.tyl.model.Hot;
import iot.tyl.model.Product;
import iot.tyl.model.ProductData;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.mapper.Mapper;
import iot.tyl.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private Mapper<List<Product>, List<ProductResponse>> mapper;
	
	@Autowired
	private Mapper<List<ProductData>, ProductResponse> mapperData;
	
	@Autowired
	private Mapper<List<Hot>, List<ProductResponse>> mapperHot;
	
	@Autowired
	private Mapper<List<Category>, List<ProductResponse>> mapperCategory;
	
	@Override
	@Cacheable(value = "hotProducts", key = "#page + '_' + #size", cacheManager = "twelveHour")
	public List<ProductResponse> getHot(int page, int size) {
		List<Hot> entities = productDao.getHot(page, size);
		return mapperHot.toDto(entities);
	}
	
	@Override
	@Cacheable(value = "newProducts", key = "#page + '_' + #size", cacheManager = "twelveHour")
	public List<ProductResponse> getNew(int page, int size) {
		List<Product> entities = productDao.getNew(page, size);
		return mapper.toDto(entities);
	}
	
	@Override
	@Cacheable(value = "products", key = "'category'", cacheManager = "oneDay")
	public List<ProductResponse> getAllCategory() {
		List<Category> entities = productDao.getAllCategory();
		return mapperCategory.toDto(entities);
	}
	
	@Override
	@Cacheable(value = "products", key = "'category' + '_' + #id + '_' + #page + '_' + #size", cacheManager = "eightHour")
	public List<ProductResponse> getCategoryId(int id, int page, int size) {
		List<Product> entities = productDao.getACategoryById(id, page, size);
		return mapper.toDto(entities);
	}
	
	@Override
	@Cacheable(value = "products", key = "#page + '_' + #size", cacheManager = "twelveHour")
	public List<ProductResponse> getProducts(int page, int size) {
		List<Product> entities = productDao.getProducts(page, size);
		return mapper.toDto(entities);
	}
	
	@Override
	@Cacheable(value = "products", key = "#id", cacheManager = "twelveHour")
	public ProductResponse getProductId(int id) {
		List<ProductData> entities = productDao.getProductById(id);
		return mapperData.toDto(entities);
	}
	
	@Override
	@Cacheable(value = "search", key = "#key + '_' + #page + '_' + #size", cacheManager = "oneHour")
	public List<ProductResponse> search(String key, int page, int size) {
		List<Product> entities = productDao.like(key, page, size);
		return mapper.toDto(entities);
	}
}
