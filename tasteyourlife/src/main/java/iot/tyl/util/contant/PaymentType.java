package iot.tyl.util.contant;

public enum PaymentType {
	
	SHOP("店面付款",ShippingType.SHOP),
	HOME("貨到付款",50,ShippingType.HOME),
	STORE("超商付款",ShippingType.STORE),
	CARD("信用卡付款",ShippingType.HOME,ShippingType.STORE),
	PAY("電子支付",ShippingType.HOME,ShippingType.STORE);
	
	private final String description;
	private final double fee;
	private final ShippingType[] shippingTypes;
	
	private PaymentType(String description, ShippingType... shippingTypes) {
		this(description, 0, shippingTypes);
	}
	
	private PaymentType(String description, double fee, ShippingType... shippingTypes) {
		this.description = description;
		this.fee = fee;
		this.shippingTypes = shippingTypes;
	}
	
	public static PaymentType getKey(String key) {
		key = key.toUpperCase();
		for (PaymentType type : PaymentType.values()) {
			if (type.name().equals(key))
				return type;
		}
		return null;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getFee() {
		return fee;
	}
	
	public ShippingType[] getShippingTypes() {
		return shippingTypes;
	}
	
//	public String[] getShippingTypes() {
//		int length = shippingTypes.length;
//		String[] typeNames = new String[length];
//		for (int i = 0; i < length; i ++) {
//			typeNames[i] = shippingTypes[i].getDescription();
//		}
//		return typeNames;
//	}
	
	public boolean hasShippingType(ShippingType shippingType) {
		for (ShippingType type : shippingTypes) {
			if (type.equals(shippingType))
				return true;
		}
		return false;
	}
	
}
