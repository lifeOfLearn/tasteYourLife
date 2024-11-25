package iot.tyl.model;

import java.sql.Timestamp;

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
@Table(name = "product")
public class Product {
	
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
	
	@Column(name = "create_time", nullable = false)
	private Timestamp createTime;
	
	@Column(name = "update_time", nullable = false)
	private Timestamp updateTime;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
//	@OneToMany(mappedBy = "product")
//	private List<ProductSaleEntity> sale;
	
//	@Transient
//	private List<ProductSaleEntity> sale;
//	
//	public List<ProductSaleEntity> newSale(){
//		return new ArrayList<>();
//	}
//	
//	public void hasSaleToSet(ProductSaleEntity ps) {
//		sale.add(ps);
//	}
//	
//	public void noSaleToNew(ProductSaleEntity ps) {
//		ProductEntity p = ps.getProduct();
//		p.newSale();
//		p.hasSaleToSet(ps);
//	}
	
	
}
