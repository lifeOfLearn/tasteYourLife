package iot.tyl.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderHistoryResponse {
	private int id;
	private int qty;
	private double totalPrice;
	private String payment;
	private double paymentFee;
	private String shipping;
	private double shippingFee;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String orderStatus;
	private int paymentStatus;
	private List<ProductResponse> productInfo;
	private Timestamp time;
}
