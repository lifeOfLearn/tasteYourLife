package iot.tyl.exception;

import iot.tyl.util.contant.ErrorKeyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerExpired extends TylBaseException{
	private static final long serialVersionUID = 1L;

	public CustomerExpired(ErrorKeyType key, String msg, String log, Throwable cause) {
		super(key, msg, log, cause);
	}
	
	//create throw if you want
	public CustomerExpired(ErrorKeyType key, String msg, String log) {
		super(key, msg, log);
	}
	
	
	public CustomerExpired(String log, Throwable cause) {
		super(log, cause);
	}

	public CustomerExpired(String log) {
		super(log);
	}
	
	public CustomerExpired() {
		super();
	}
}