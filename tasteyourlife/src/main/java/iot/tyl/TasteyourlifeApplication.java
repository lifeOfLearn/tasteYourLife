package iot.tyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:config.properties", "classpath:message.properties"}, encoding = "UTF-8")
public class TasteyourlifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasteyourlifeApplication.class, args);
	}
}
