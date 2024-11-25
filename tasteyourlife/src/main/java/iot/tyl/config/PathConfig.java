package iot.tyl.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "path")
public class PathConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Value("${server.servlet.context-path}")
	private String root;
	
	private Captcha captcha;
	private Customer customer;
	private Forget forget;
	private Member member;
	private Product product;
	private Order order;
	private Payment payment;
	
	@Data
	public static class Captcha {
		private String root;
		private String image;
	}
	
	@Data
	public static class Customer {
		private String root;
		private String info;
		private String login;
		private String register;
		private String enable;
		private String update;
		private String forget;
		private String renew;
		private String logout;
	}
	
	@Data
	public static class Forget {
		private String root;
		private String des;
	}
	
	@Data
	public static class Member {
		private String root;
	}
	
	@Data
	public static class Order {
		private String root;
		private String getCart;
		private String addCart;
		private String smallCart;
		private String checkout;
		private String history;
	}
	
	@Data
	public static class Payment {
		private String root;
		private String line;
		private String type;
	}
	
	@Data
	public static class Product {
		private String root;
		private String hots;
		private String news;
		private String category;
		private String page;
		private String search;
		private String id;
	}
}
