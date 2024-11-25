package iot.tyl.util.contant;

public enum Pattern {
	
	EMPTY(""),
	PASSWD("((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\x20-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7B-\\x7E])).{6,20}"),
	EMAIL("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}"),
	PHONE("09[0-9]{8}"),
	ROCID("[A-Z][1289][0-9]{8}");
	
	private final String value;
	
	private Pattern(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
