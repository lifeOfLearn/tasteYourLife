package iot.tyl.dao;

import java.util.List;

import iot.tyl.model.Category;
import iot.tyl.model.Hot;
import iot.tyl.model.Product;
import iot.tyl.model.ProductData;

public interface ProductDao {
	List<Hot> getHot(int page, int size);
	List<Product> getNew(int page, int size);
	List<Category> getAllCategory();
	List<Product> getACategoryById(int id, int page, int size);
	List<Product> getProducts(int page, int size);
	List<ProductData> getProductById(int id);
	ProductData getProductBySId(int id);
	List<Product> like(String key, int page, int size);
	
}
