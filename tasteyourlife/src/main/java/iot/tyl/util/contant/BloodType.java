package iot.tyl.util.contant;

public enum BloodType {
	O,A,B,AB,N;
	
//	/**
//	 * this is enum toString <br>
//	 * enum is public static final
//	 * @return bloodType + "型"
//	 */
//	@Override
//	public String toString() {
//		return name() + "型" ;
//	}
	
	public static BloodType getBloodType(String c) {
		if(c != null && c.length() > 0) {
			String cValue = c.toUpperCase();
			for(BloodType type : BloodType.values()) {
				if(type.name().equals(cValue))
					return type;
			}
		}
		return N;
	}
}
