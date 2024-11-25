package iot.tyl.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import iot.tyl.config.ErrLogMsgConfig.Forget.Token;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "err")
public class ErrMsgConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String userPasswd;
	private Enable enable;
	private Register register;
	private User user;
	private Passwd passwd;
	private Captcha captcha;
	private Cookie cookie;
	private Token token;
	private Http http;
	private Forget forget;
	private Customer customer;
	private Cart cart;
	private Order order;
	private Checkout checkout;
	@Data
	public static class Enable {
		private String token;
	}
	
	@Data
	public static class Register {
		private String repeatE;
		private String repeatP;
		private String fail;
		private String passwdDouble;
		private String passwd;
		private String birthday;
		private String email;
		private String phone;
		private String rocId;
	}
	
	@Data
	public static class User {
		private String empty;
		private String pattern;
		private String status;
	}
	
	@Data
	public static class Passwd {
		private String empty;
		private String pattern;
	}
	
	@Data
	public static class Captcha {
		private String empty;
		private String error;
		private String io;
	}
	
	@Data
	public static class Cookie {
		private String invalid;
		private Captcha captcha;
		private Login login;
		private String Logout;
		
		@Data
		public static class Captcha {
			private String key;
			private String value;
		}
		
		@Data
		public static class Login {
			private String key;
			private String value;
		}
	}
	
	@Data
	public static class Token {
		private String invalid;
	}
	
	@Data
	public static class Http {
		private String notSupport;
		private String notReadable;
	}
	@Data
	public static class Forget {
		private Token token;
		private Path path;
		@Data 
		public static class Token {
			private String empty;
			private String expired;
		}
		@Data
		public static class Path {
			private String cookie;
			private String token;
			private String key;
			private String value;
			private String passwdEq;
			private String clientEmpty;
		}
		
	}
	@Data
	public static class Customer {
		private String dateFormat;
	}
	@Data
	public static class Cart {
		private String redisEmpty;
		private String sidEmpty;
	}
	@Data
	public static class Order {
		private Recipient recipient;
		private String paymentType;
		private String shippingType;
		private String typeParse;
		private String invalid;
		@Data
		public static class Recipient {
			private String phone;
			private String email;
		}
	}
	@Data
	public static class Checkout {
		private String userEmpty;
		private String unknow;
	}
}
