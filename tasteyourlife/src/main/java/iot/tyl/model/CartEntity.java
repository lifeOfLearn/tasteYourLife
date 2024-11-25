package iot.tyl.model;

import iot.tyl.util.contant.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class CartEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_id", length = 11, nullable = false )
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;
	
	@ManyToOne
	@JoinColumn(name = "sale_id")
	private ProductSaleEntity sale;
	
	@Column(name="quantity", length = 11, nullable = false )
	private int qty;
	
	@Enumerated(EnumType.STRING)
	@Column(name="choose", length = 1, nullable = false )
	private Status choose = Status.Y;
	
	@Enumerated(EnumType.STRING)
	@Column(name="checkout", length = 1, nullable = false )
	private Status checkout = Status.N;
}
