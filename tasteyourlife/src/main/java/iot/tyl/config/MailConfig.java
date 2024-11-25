package iot.tyl.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties("mail")
public class MailConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String param;
	private Subject subject;
	private Body body;
	
	@Data
	public static class Subject {
		private String register;
		private String passwdErr;
		private String forget;
	}
	
	@Data
	public static class Body {
		private String header;
		private String register;
		private String passwdErr;
		private String forget;
		private String url;
	}
}
