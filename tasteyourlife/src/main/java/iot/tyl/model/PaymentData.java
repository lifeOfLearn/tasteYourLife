package iot.tyl.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentData {
	private String value;
	private String type;
	private double fee;
	private List<ShippingData> shippingTypes;
	
	@Data
	public static class ShippingData {
		private String value;
		private String type;
		private double fee;
	}
}
