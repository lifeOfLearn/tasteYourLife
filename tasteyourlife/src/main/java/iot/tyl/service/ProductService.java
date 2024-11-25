package iot.tyl.service;

import java.util.List;

import iot.tyl.model.ProductResponse;

public interface ProductService {
	public List<ProductResponse> getHot(int page, int size);
	public List<ProductResponse> getNew(int page, int size);
	public List<ProductResponse> getAllCategory();
	public List<ProductResponse> getCategoryId(int id, int page, int size);
	public List<ProductResponse> getProducts(int page, int size);
	public ProductResponse getProductId(int id);
	public List<ProductResponse> search(String key, int page, int size);
	
}
