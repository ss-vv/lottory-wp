package com.xhcms.lottery.alarm.service.impl;

import java.util.Set;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.xhcms.lottery.alarm.data.EmailConfig;
import com.xhcms.lottery.alarm.service.SendEmailService;

@Service
public class SendEmailServiceImpl implements SendEmailService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EmailConfig emailConfig;
	
	@Override
	public void send(String msgContent, Set<String> receptEmails,String subject) {
		final EmailSender mailSender = createMailSender();
		mailSender.setSubject(subject);
		mailSender.setEmailContent(msgContent);
		final Set<String> add = receptEmails;
		Thread sendThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (final String address : add) {
						mailSender.setAddress(address);
						mailSender.sendEmail();
					}
				} catch (Throwable e) {
					logger.error("SendMail Failed!", e);
				}
			}
		});
		sendThread.start();
	}
	
	private EmailSender createMailSender() {
		EmailSender mailSender = new EmailSender();
		mailSender.setSender(getJMailSender());
		mailSender.setFrom(emailConfig.getFrom());
		return mailSender;
	}
	
	private JavaMailSender getJMailSender() {
		// 初始化属性
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(emailConfig.getHost());
		sender.setUsername(emailConfig.getUsername());
		sender.setPassword(emailConfig.getPassword());
		sender.setDefaultEncoding(emailConfig.getEncoding());
		return sender;
	}
}
