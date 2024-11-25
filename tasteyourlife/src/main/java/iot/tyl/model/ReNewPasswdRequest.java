package iot.tyl.model;

import java.io.Serializable;

import iot.tyl.util.contant.ErrorKeyType;
import iot.tyl.validation.ValueValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReNewPasswdRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ValueValidator(errKey = ErrorKeyType.passwd)
	private String password;
	
	@ValueValidator(errKey = ErrorKeyType.passwd2)
	private String password2;
	
	@ValueValidator(errKey = ErrorKeyType.captcha)
	private String captcha;

}
