package com.project.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender jms;
	
	@Transactional
	public void sendEmail(String toEmail, String subject,String body) {
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setSubject(subject);
		message.setTo(toEmail);
		message.setText(body);
		
		jms.send(message);
	}

	
}