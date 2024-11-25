package iot.tyl.util.contant;

import java.util.HashMap;
import java.util.Map;

public enum GenderType {
	MALE('M'),
	FEMALE('F'),
	OTHER('O'),
	NOSAY('N');
	

	private final char code; 
	private static final Map<Character, GenderType> VALUE = new HashMap<>();
	
    static {
        for (GenderType gt : GenderType.values()) {
            VALUE.put(gt.getCode(), gt);
        }
    }
	
	private GenderType(char code){
		this.code = code;
	}
	
	public char getCode() {
		return code;
	}
	
	public static boolean hasValue(char c) {
		return VALUE.containsKey(c);
	}
	
	public static GenderType getGenderTypeFromCode(char c) {
		if (hasValue(c)) {
			return VALUE.get(c);
		}
		return NOSAY;
	}
	
	@Override
	public String toString() {
		return name() ;
	}
}
