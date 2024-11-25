package iot.tyl.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "param")
public class ParamConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Keystore keystore;
	private Sec sec;
	private Token token;
	private Cookie cookie;
	
	@Data
	public static class Keystore {
		private String type;
		private String path;
		private String passwd;
		private String alias;
	}
	
	@Data
	public static class Sec {
		private long login;
		private long forget;
		private long register;
		private long captcha;
	}
	
	@Data
	public static class Token {
		private String aud;
		private String jti;
		private String iss;
		private Claim claim;
		
		@Data
		public static class Claim {
			private String captcha;
		}
		
	}
	
	@Data
	public static class Cookie {
		private Path path;
		private Key key;
		
		@Data
		public static class Path {
			private String forget;
			private String captcha;
			private String login;
		}
		
		@Data
		public static class Key {
			private String forget;
			private String captcha;
			private String login;
		}
		
	}
	
		
}
