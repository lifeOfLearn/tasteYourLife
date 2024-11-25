package iot.tyl.exception;

import iot.tyl.util.contant.ErrorKeyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReNewException extends TylBaseException{
	private static final long serialVersionUID = 1L;

	public ReNewException(ErrorKeyType key, String msg, String log, Throwable cause) {
		super(key, msg, log, cause);
	}
	
	//create throw if you want
	public ReNewException(ErrorKeyType key, String msg, String log) {
		super(key, msg, log);
	}
	
	
	public ReNewException(String log, Throwable cause) {
		super(log, cause);
	}

	public ReNewException(String log) {
		super(log);
	}
	
	public ReNewException() {
		super();
	}
}
