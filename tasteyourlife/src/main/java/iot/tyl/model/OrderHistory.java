package iot.tyl.model;

import java.sql.Timestamp;

import iot.tyl.util.contant.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@IdClass(OrderHistoryId.class)
@Table(name = "order_history")
public class OrderHistory {
	
	@Id
	private int orderId;
	
	@Id
	private int cartId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "pay", nullable = false)
	private Status pay = Status.N;
	
	@ManyToOne(cascade = CascadeType.ALL)
//	@MapsId("orderId")
	@JoinColumn(name = "order_id", referencedColumnName = "id" , nullable = false, insertable = false, updatable = false)
	private OrderEntity order;
	
	@OneToOne
//	@MapsId("cartId")
	@JoinColumn(name = "cart_id", nullable = false, insertable = false, updatable = false)
	private CartEntity cart;
	
	@Column(name = "create_time", nullable = false, insertable = false, updatable = false)
	private Timestamp createTime;
}
