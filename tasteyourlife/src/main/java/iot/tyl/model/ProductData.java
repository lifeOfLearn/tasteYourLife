package iot.tyl.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sale_id", length = 11, nullable = false)
	private int saleId;
	
	@Column(name = "product_id", length = 11, nullable = false)
	private Integer productId;
	
	@Column(name = "product_name", length = 46, nullable = false)
	private String productName = "";
	
	@Column(name = "product_info", length = 100 , nullable = false)
	private String productInfo = "";
	
	@Column(name = "path", length = 100, nullable = false)
	private String path;
	
	@Column(name = "create_time", nullable = false)
	private Timestamp createTime;
	
	@Column(name="category_id", length = 11, nullable = false)
	private int categoryId;
	
	@Column(name = "category_name", length = 45, nullable = false)
	private String categoryName;
	
	@Column(name = "size", length = 10, nullable = true)
	private String size;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "add_id", length = 11, nullable = false)
	private int addId;
	
	@Column(name = "add_name", length = 100, nullable = false)
	private String addName;
	
	@Column(name = "add_price", length = 11, nullable = false)
	private int addPrice;
}
