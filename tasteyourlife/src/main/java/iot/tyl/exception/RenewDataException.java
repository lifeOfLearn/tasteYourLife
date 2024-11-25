package iot.tyl.exception;

import iot.tyl.util.contant.ErrorKeyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewDataException extends TylBaseException{
	private static final long serialVersionUID = 1L;

	public RenewDataException(ErrorKeyType key, String msg, String log, Throwable cause) {
		super(key, msg, log, cause);
	}
	
	//create throw if you want
	public RenewDataException(ErrorKeyType key, String msg, String log) {
		super(key, msg, log);
	}
	
	
	public RenewDataException(String log, Throwable cause) {
		super(log, cause);
	}

	public RenewDataException(String log) {
		super(log);
	}
	
	public RenewDataException() {
		super();
	}
}
