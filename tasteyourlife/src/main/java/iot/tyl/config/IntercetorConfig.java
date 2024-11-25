package iot.tyl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import iot.tyl.config.PathConfig.Customer;
import iot.tyl.config.PathConfig.Order;
import iot.tyl.interceptor.CustomerInterceptor;
import iot.tyl.interceptor.EnableCustomerInterceptor;
import iot.tyl.interceptor.ForgetPathInterceptor;
import iot.tyl.interceptor.ForgetInterceptor;
import iot.tyl.interceptor.GuestInterceptor;

@Configuration
public class IntercetorConfig implements WebMvcConfigurer {

	@Autowired
	private GuestInterceptor guestInterceptor;
	
	@Autowired
	private ForgetInterceptor forgetInterceptor;
	
	@Autowired
	private ForgetPathInterceptor forgetPathInterceptor;
	
	@Autowired
	private EnableCustomerInterceptor enableCustomerInterceptor;
	
	@Autowired
	private CustomerInterceptor customerInterceptor;
	
	@Autowired
	private PathConfig pathConfig;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		Customer customer = pathConfig.getCustomer();
		Order order = pathConfig.getOrder();
		String customerPath = customer.getRoot();
		String memberPath = pathConfig.getMember().getRoot();
		String orderPath = order.getRoot();

		registry.addInterceptor(guestInterceptor)
				.order(10)
				.addPathPatterns(customerPath + customer.getLogin(),
								 customerPath + customer.getRegister(),
								 customerPath + customer.getForget(),
								 customerPath + customer.getRenew());
		
		
		registry.addInterceptor(enableCustomerInterceptor)
				.order(10)
				.addPathPatterns(customerPath + customer.getEnable());
		
		registry.addInterceptor(customerInterceptor)
				.order(10)
				.addPathPatterns(customerPath,
								 customerPath + customer.getInfo(),
								 customerPath + customer.getLogout(),
								 orderPath + "/**",
								 memberPath + "/**",
								 pathConfig.getOrder().getRoot() + "/**");
		
		
		registry.addInterceptor(forgetInterceptor)
				.order(10)
				.addPathPatterns(pathConfig.getForget().getRoot());

		registry.addInterceptor(forgetPathInterceptor)
				.order(9)
				.addPathPatterns(customerPath + customer.getRenew());
	}
}
