package iot.tyl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iot.tyl.model.ProductData;

@Repository
public interface ProductDataJpa extends JpaRepository<ProductData, Integer>{
	List<ProductData> findByProductId(Integer id);
	ProductData findBySaleId(Integer id);
}
