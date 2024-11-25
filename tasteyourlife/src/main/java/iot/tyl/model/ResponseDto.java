package iot.tyl.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import iot.tyl.util.contant.ErrorKeyType;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ResponseDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String redirect;
	private int code;
	private String msg;
	private String user;
	private Map<ErrorKeyType, String> error;
	
	@PostConstruct
	private void setMap() {
		error = new HashMap<>();
	}
}
