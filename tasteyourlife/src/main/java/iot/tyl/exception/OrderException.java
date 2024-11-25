package iot.tyl.exception;

import iot.tyl.util.contant.ErrorKeyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderException extends TylBaseException{
	private static final long serialVersionUID = 1L;

	public OrderException(ErrorKeyType key, String msg, String log, Throwable cause) {
		super(key, msg, log, cause);
	}
	
	//create throw if you want
	public OrderException(ErrorKeyType key, String msg, String log) {
		super(key, msg, log);
	}
	
	
	public OrderException(String log, Throwable cause) {
		super(log, cause);
	}

	public OrderException(String log) {
		super(log);
	}
	
	public OrderException() {
		super();
	}
}
