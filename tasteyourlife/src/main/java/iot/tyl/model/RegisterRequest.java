package iot.tyl.model;

import java.io.Serializable;

import iot.tyl.util.contant.ErrorKeyType;
import iot.tyl.validation.ValueValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ValueValidator(errKey = ErrorKeyType.name, len = 36)
    private String name;
	
	@ValueValidator(errKey = ErrorKeyType.phone)
    private String phone;
	
	@ValueValidator(errKey = ErrorKeyType.email, len = 50)
    private String email;
	
	@ValueValidator(errKey = ErrorKeyType.date)
    private String birthday;
    
	@ValueValidator(errKey = ErrorKeyType.rocid, len = 10)
    private String rocid;
    
	@ValueValidator(errKey = ErrorKeyType.passwd, len = 20)
    private String password;
    
	@ValueValidator(errKey = ErrorKeyType.passwd2, len = 20)
    private String password2;
	
	private String blood;
    
	private char gender;
    
	private String address;
    
	@ValueValidator(errKey = ErrorKeyType.captcha)
    private String captcha;
}
