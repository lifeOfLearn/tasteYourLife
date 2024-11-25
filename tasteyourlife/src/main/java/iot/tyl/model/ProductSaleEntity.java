package iot.tyl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "product_sale")
public class ProductSaleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sale_id", length = 11, nullable = false)
	private int saleId;
	
	@Column(name = "size", length = 10, nullable = true)
	private String size;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "sales", length = 11, nullable = false)
	private int sales;
	
//	@Column(name = "product_id", nullable = false)
//	private int productId;
	
	@ManyToOne
	@JoinColumn(name = "add_id", nullable = false)
	private ProductAddEntity productAdd;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

}
