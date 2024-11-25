package iot.tyl.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinePayRequest {
	private double amount;
	private String currency;
	private String orderId;
	private List<Packages> packages;
	private Redirecturls redirecturls;
	private Display display;
	
	@Data
	public static class Packages {
		private String id;
		//private String name;
		private double amount;
		private double userFee;
		private List<Products> products;
		
		@Data
		public static class Products {
			private String id;
			private String name;
			//private String imageUrl;
			private int quantity;
			private double price;
			//private double originalPrice;
		}
	}
	
	@Data
	public static class Redirecturls {
		private String comfirmUrl;
		private String cancelUrl;
		//private boolean capture; //default true
	}
	
	@Data
	public static class Display {
		private String locale;
	}
}
