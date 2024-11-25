package iot.tyl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import iot.tyl.config.MailConfig;
import iot.tyl.config.MailConfig.Body;
import iot.tyl.config.ParamConfig;
import iot.tyl.config.PathConfig;
import iot.tyl.service.JwtService;
import iot.tyl.service.MailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async("mailPool")
@Service
public class MailServiceImpl implements MailService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private MailConfig mailConfig;
	
	@Autowired
	private ParamConfig paramConfig;
	
	@Autowired
	private PathConfig pathConfig;
	
	
	
	@Override
	public void sendRegister(String email, String tokenId) {
		log.info("register:{}",email);
		String token = getToken(tokenId, paramConfig.getSec().getRegister());
		Body body = mailConfig.getBody();
		StringBuffer bodyData = new StringBuffer()
									.append(body.getHeader())
									.append(body.getRegister())
									.append(body.getUrl())
									.append(pathConfig.getRoot())
									.append(pathConfig.getCustomer().getRoot())
									.append(pathConfig.getCustomer().getEnable())
									.append("?")
									.append(mailConfig.getParam())
									.append("=")
									.append(token);
		send(email,
			 mailConfig.getSubject().getRegister(),
			 bodyData.toString());
	}

	@Override
	public void sendPasswdErr(String email) {
		log.info("passwdErr:{}",email);
		Body body = mailConfig.getBody();
		StringBuffer bodyData = new StringBuffer()
									.append(body.getHeader())
									.append(body.getPasswdErr());
		send(email,
			 mailConfig.getSubject().getPasswdErr(),
			 bodyData.toString());
			 
	}

	@Override
	public void sendForget(String email, String tokenId) {
		log.info("forget:{}",email);
		String token = getToken(tokenId, paramConfig.getSec().getForget());
		Body body = mailConfig.getBody();
		StringBuffer bodyData = new StringBuffer()
									.append(body.getHeader())
									.append(body.getForget())
									.append(body.getUrl())
									.append(pathConfig.getRoot())
									.append(pathConfig.getForget().getRoot())
									.append("?")
									.append(mailConfig.getParam())
									.append("=")
									.append(token);
		send(email,
			 mailConfig.getSubject().getForget(),
			 bodyData.toString());
	}

	private String getToken(String tokenId, long timeSec) {
		return jwtService.generateToken(tokenId, timeSec);
	}
	
	
	private void send(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
       
        mailSender.send(message);
	}

}
