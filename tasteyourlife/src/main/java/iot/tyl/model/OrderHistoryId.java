package iot.tyl.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderHistoryId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "cart_id")
	private int cartId;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderHistoryId other = (OrderHistoryId) obj;
		return cartId == other.cartId && orderId == other.orderId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cartId, orderId);
	}
	
}
