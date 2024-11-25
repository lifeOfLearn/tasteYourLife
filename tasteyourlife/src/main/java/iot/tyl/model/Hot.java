package iot.tyl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Hot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", length = 11, nullable = false)
	private Integer productId;
	
	@Column(name = "product_name", length = 46, nullable = false)
	private String productName = "";
	
	@Column(name = "product_info", length = 100 , nullable = false)
	private String productInfo = "";
	
	@Column(name = "path", length = 100, nullable = false)
	private String path;
}
