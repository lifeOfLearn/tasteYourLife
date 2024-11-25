package iot.tyl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.tyl.model.Category;

@Repository
public interface CategoryJpa extends JpaRepository<Category, Integer>{

	List<Category> findByOrderByCategoryId();
	
}
