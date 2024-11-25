package iot.tyl;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import iot.tyl.dao.ProductDao;
import iot.tyl.model.Product;
import iot.tyl.model.ProductResponse;
import iot.tyl.service.ProductService;

@SpringBootTest
public class ProductControllerTest {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductDao productDao;
	
	@Test
	public void test() {
//		List<Product> list = productDao.getNewProducts(0, 6);
//		System.out.println(list);
//		System.out.println("Hot");
//		loop(1);
//		System.out.println("New");
//		loop2(1);
	}
	
	private void loop(int num) {
		int page = 0;
		int size = 6;
		for (int i = 0; i < num; i++) {
			System.out.println(i);
			List<ProductResponse> responses = productService.getHot(page, size);
			for (ProductResponse response : responses) {
				System.out.println(response);
			}
		}
	}
	private void loop2(int num) {
		int page = 0;
		int size = 6;
		for (int i = 0; i < num; i++) {
			System.out.println(i);
			List<ProductResponse> responses = productService.getNew(page, size);
			for (ProductResponse response : responses) {
				System.out.println(response);
			}
		}
	}
}
