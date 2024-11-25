package iot.tyl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class BeanAsyncFactory {

	@Bean(name = "logPool")
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("Log-");
		executor.initialize();
		return executor;
	}
	
	@Bean(name = "mailPool")
	public TaskExecutor threadPool2TaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("Mail-");
		executor.initialize();
		return executor;
	}
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
