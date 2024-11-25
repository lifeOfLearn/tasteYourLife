package iot.tyl.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.tyl.model.Product;

@Repository
public interface ProductJpa extends JpaRepository<Product, Integer>{
	List<Product> findByOrderByCreateTimeDesc(Pageable page);
	List<Product> findByOrderByProductId(Pageable page);
	List<Product> findByCategory_CategoryId(Integer categoryId, Pageable page);
	//List<Product> findByProductNameContaining(String productName, Pageable page);
	List<Product> findByProductNameContainingOrProductInfoContaining(String productName, String productInfo, Pageable page);
}
