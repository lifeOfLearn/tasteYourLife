package iot.tyl.model;

import iot.tyl.util.contant.ErrorKeyType;
import iot.tyl.validation.ValueValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckoutRequest {
	
	@ValueValidator(errKey = ErrorKeyType.name, len = 36)
	private String recipientName;
	
	@ValueValidator(errKey = ErrorKeyType.phone)
	private String recipientPhone;
	
	@ValueValidator(errKey = ErrorKeyType.email, len = 50)
	private String recipientEmail;
	
	@ValueValidator(errKey = ErrorKeyType.raddress, len = 100)
	private String recipientAddress;
	
	@ValueValidator(errKey = ErrorKeyType.payment)
	private String paymentType;
	
	private String paymentNote = "";
	
	@ValueValidator(errKey = ErrorKeyType.shipping)
	private String shippingType;
	
}
