package iot.tyl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iot.tyl.model.ProductResponse;
import iot.tyl.service.ProductService;

@RestController
@RequestMapping("${path.product.root}")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("${path.product.hots}")
	public List<ProductResponse> hot(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size
			) {
		return productService.getHot(page, size);
	}
//	
	@GetMapping("${path.product.news}")
	public List<ProductResponse> newProudct(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size
			) {
		return productService.getNew(page, size);
	}
	
	@GetMapping("${path.product.category}")
	public List<ProductResponse> category() {
		return productService.getAllCategory();
	}
	
	@GetMapping("${path.product.category}/{id}")
	public List<ProductResponse> categoryId(
			@PathVariable int id,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size
			) {
		return productService.getCategoryId(id, page, size);
	}
	
	@GetMapping("${path.product.page}/{page}") // 0 ~ n
	public List<ProductResponse> allProducts(
			@PathVariable int page,
			@RequestParam(defaultValue = "6") int size
			) {
		return productService.getProducts(page, size);
	}
	
	@GetMapping("${path.product.id}/{id}")
	public ProductResponse getProductId(@PathVariable int id){
		return productService.getProductId(id);
	}
	
	@GetMapping("${path.product.search}/{search}")
	public List<ProductResponse> search(
			@PathVariable String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size
			) {
		if (search != null && (search = search.trim()).length() != 0) {
			return productService.search(search, page, size);
		}
		return null;
	}
}
