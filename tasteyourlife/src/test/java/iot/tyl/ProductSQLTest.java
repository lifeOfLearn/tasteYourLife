package iot.tyl;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import iot.tyl.dao.HotJpa;
import iot.tyl.dao.ProductDao;
import iot.tyl.dao.ProductJpa;
import iot.tyl.model.Category;
import iot.tyl.model.Hot;
import iot.tyl.model.Product;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.ProductSaleEntity;
import iot.tyl.model.mapper.Mapper;

@SpringBootTest
public class ProductSQLTest {
	
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductJpa productJpa;
	
	@Autowired
	private HotJpa hotJpa;
	
	
	@Autowired
	private Mapper<List<Category>, List<ProductResponse>> mapperCategory;
	
	@Test
	public void test() {
//		testDao();
		
	
	}
	
	private void testDao() {
//		productDao.getHot(0, 3).forEach(hot -> System.out.println(hot));
//		productDao.getNew(0, 3).forEach(neww -> System.out.println(neww));
//		productDao.getAllCategory().forEach(c -> System.out.println(c));
//		 
//		List<Category> entity = productDao.getAllCategory();
//		List<ProductResponse> list = mapperCategory.toDto(entity);
//		System.out.println(list);
		int id = 1;
		Pageable pageable = PageRequest.of(0, 6);
		List<Product> list = productJpa.findByCategory_CategoryId(id, pageable);
		list.forEach(p -> System.out.println(p));
	}
	
	private void loop(List<Object> list, String name) {
		System.out.println(name);
		for (Object obj : list) {
			System.out.println(obj);
		}
	}
	
//	public List<ProductSaleEntity> getHotProducts(int page, int size) {
//		Pageable pageable = PageRequest.of(page, size);
//		return productSaleRepositoryJpa.findByOrderBySalesDesc(pageable).getContent();
//	}
	

//	public List<ProductSaleEntity> getNewProducts(int page, int size) {
//		Pageable pageable = PageRequest.of(page, size);
//		List<ProductSaleEntity> saleList = productSaleRepositoryJpa.findByProduct_CreateTimeDesc(pageable).getContent();
//		List<Integer> idList = new ArrayList<>();
//		saleList.forEach(sale -> {
//			ProductEntity p = sale.getProduct();
//			if (idList.get(p.getProductId()) == null) {
//				idList.add(p.getProductId());
//				System.out.println("in");
//				//TODO sale to list
//			} else {
//				//TODO new productEntity 
//			}
//		});
//		return saleList;
//	}
	
	
	 public List<Hot> getAllHots(int page, int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        return hotJpa.findAll(pageable).getContent();
	    }
	
}
