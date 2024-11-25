package iot.tyl.exception;

import iot.tyl.util.contant.ErrorKeyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductException extends TylBaseException{
	private static final long serialVersionUID = 1L;

	public ProductException(ErrorKeyType key, String msg, String log, Throwable cause) {
		super(key, msg, log, cause);
	}
	
	//create throw if you want
	public ProductException(ErrorKeyType key, String msg, String log) {
		super(key, msg, log);
	}
	
	
	public ProductException(String log, Throwable cause) {
		super(log, cause);
	}

	public ProductException(String log) {
		super(log);
	}
	
	public ProductException() {
		super();
	}
}
