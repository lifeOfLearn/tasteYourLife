package iot.tyl.validation;

import iot.tyl.exception.TylBaseException;
import iot.tyl.util.contant.ErrorKeyType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValueConstraintValidator implements ConstraintValidator<ValueValidator, String>{
	
	private ErrorKeyType errKey;
	
	private int length;
	
	@Override
	public void initialize(ValueValidator constraintAnnotation) {
		this.errKey = constraintAnnotation.errKey();
		this.length = constraintAnnotation.len();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		isEmpty(value);
		lengthCheck(value);
		return true;
	}
	
	private boolean isEmpty(String value) {
		if (value != null && (value = value.trim()).length() != 0)
			return true;
		
		String msg = errKey.value() + "不可為空";
		throw new TylBaseException(errKey, msg, msg);
	}
	
	private boolean lengthCheck(String value) {
		if (length == -1 || value.length() <= length)
			return true;
		
		String msg = errKey.value() + "長度最多:" + length;
		throw new TylBaseException(errKey, msg, msg);
	}
}
