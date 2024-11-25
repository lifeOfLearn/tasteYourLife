package iot.tyl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "product_category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="category_id", length = 11, nullable = false)
	private int categoryId;
	
	@Column(name = "category_name", length = 45, nullable = false)
	private String categoryName;
	
//	@OneToMany(mappedBy = "category")
//	private List<ProductEntity> products = new ArrayList<>();
}
