package iot.tyl.util.contant;

public enum ShippingType {
	SHOP("店面取貨",0),
	HOME("送貨到府",100),
	STORE("超商取貨",65);
	
	private final String description;
	private final double fee;
	
	private ShippingType(String description, double fee) {
		this.description = description;
		this.fee = fee;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getFee() {
		return fee;
	}
	
	public static ShippingType getkey(String key) {
		key = key.toUpperCase();
		for (ShippingType type : ShippingType.values()) {
			if(type.name().equals(key))
				return type;
		}
		return null;
	}
}
