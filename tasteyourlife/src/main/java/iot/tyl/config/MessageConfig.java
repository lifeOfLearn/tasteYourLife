package iot.tyl.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "msg")
public class MessageConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String logout;
	private String register;
	private String forget;
}
