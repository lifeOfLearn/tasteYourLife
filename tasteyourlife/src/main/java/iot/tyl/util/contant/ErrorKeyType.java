package iot.tyl.util.contant;

public enum ErrorKeyType {
	user("帳號"),
	name("使用者"),
	passwd("密碼"),
	passwd2("密碼"),
	phone("手機"),
	email("信箱"),
	date("日期"),
	rocid("身分證"),
	captcha("驗證碼"),
	reCaptcha("新驗證碼"),
	other("其他"),
	proudct("商品"),
	customerExpired("使用者登入時間過期"),
	cart("購物車"),
	raddress("收件地址"),
	rname("收件人"),
	payment("支付方式"),
	shipping("運送方式"),
	order("訂單");
	
	private String value;
	
	private ErrorKeyType(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
