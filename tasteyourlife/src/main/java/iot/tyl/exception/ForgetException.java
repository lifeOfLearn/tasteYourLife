package iot.tyl.exception;

import iot.tyl.util.contant.ErrorKeyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetException extends TylBaseException{
	private static final long serialVersionUID = 1L;

	public ForgetException(ErrorKeyType key, String msg, String log, Throwable cause) {
		super(key, msg, log, cause);
	}
	
	//create throw if you want
	public ForgetException(ErrorKeyType key, String msg, String log) {
		super(key, msg, log);
	}
	
	
	public ForgetException(String log, Throwable cause) {
		super(log, cause);
	}

	public ForgetException(String log) {
		super(log);
	}
	
	public ForgetException() {
		super();
	}
}
