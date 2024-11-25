package iot.tyl.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import iot.tyl.util.contant.ErrorKeyType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValueConstraintValidator.class )
public @interface ValueValidator {
	
	ErrorKeyType errKey() default ErrorKeyType.other;
	
	int len() default -1;
	
	String message() default "資料格式不正確";
	
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
	
}
