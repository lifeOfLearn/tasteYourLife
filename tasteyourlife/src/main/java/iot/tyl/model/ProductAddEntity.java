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
@Table(name = "product_add")
public class ProductAddEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "add_id", length = 11, nullable = false)
	private int addId;
	
	@Column(name = "add_name", length = 100, nullable = false)
	private String addName;
	
	@Column(name = "add_price", length = 11, nullable = false)
	private int addPrice;
	
}
