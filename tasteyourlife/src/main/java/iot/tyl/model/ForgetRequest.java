package iot.tyl.model;

import java.io.Serializable;

import iot.tyl.util.contant.ErrorKeyType;
import iot.tyl.validation.ValueValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgetRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ValueValidator(errKey = ErrorKeyType.user)
	private String user;
	
	@ValueValidator(errKey = ErrorKeyType.captcha)
	private String captcha;
}
