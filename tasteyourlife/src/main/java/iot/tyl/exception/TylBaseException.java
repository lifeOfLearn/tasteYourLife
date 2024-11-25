package iot.tyl.exception;

import iot.tyl.util.contant.ErrorKeyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TylBaseException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private ErrorKeyType key;
	private String msg;
	private String log;
	
	//auto throw error
	public TylBaseException(ErrorKeyType key, String msg, String log, Throwable cause) {
		super(log, cause);
		setAttr(key, msg, log);
	}
	
	//create throw if you want
	public TylBaseException(ErrorKeyType key, String msg, String log) {
		super(log);
		setAttr(key, msg, log);
	}
	
	
	public TylBaseException(String log, Throwable cause) {
		super(log, cause);
		setErrorLog(log);
	}

	public TylBaseException(String log) {
		super(log);
		setErrorLog(log);
	}
	
	public TylBaseException() {
		super();
	}
	
	
	private void setAttr(ErrorKeyType key, String msg, String log) {
		this.key = key;
		this.msg = msg;
		setErrorLog(log);
	}
	
	private void setErrorLog(String log) {
		this.log = log;
	}
}
