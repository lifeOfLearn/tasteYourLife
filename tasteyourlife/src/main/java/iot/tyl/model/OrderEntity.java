package iot.tyl.model;

import iot.tyl.util.contant.PaymentType;
import iot.tyl.util.contant.ShippingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;
	
	@Enumerated(EnumType.STRING)
	@Column(name="payment_type", length = 15, nullable = false )
	private PaymentType paymentType;
	
	@Column(name="payment_fee", nullable = false )
	private double paymentFee;
	
	@Column(name="payment_note", length = 200, nullable = false )
	private String paymentNote;
	
	@Enumerated(EnumType.STRING)
	@Column(name="shipping_type", length = 11, nullable = false )
	private ShippingType shippingType;
	
	@Column(name="shipping_fee", length = 11, nullable = false )
	private double shippingFee;
	
	@Column(name="shipping_address", length = 100, nullable = false )
	private String address;
	
	@Column(name="recipient_name", length = 45, nullable = false )
	private String name;
	
	@Column(name="recipient_phone", length = 9, nullable = false )
	private int phone;
	
	@Column(name="recipient_email", length = 50, nullable = false )
	private String email;
	
	@Column(name="status", length = 11, nullable = false)
	private int status;
}
