package com.xhcms.ucenter.service.mail.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.xhcms.ucenter.service.mail.EmailSender;
import com.xhcms.ucenter.service.mail.IEmailService;

public class EmailService implements IEmailService {
	private static Logger logger = Logger.getLogger(EmailService.class);
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	private String defaultTemplateFile;

	private String mailTemplateFile;
	
	private Properties javaMailProperties;
	
	private boolean useVelocity=true;
	
	private String content;//发送邮件的内容

	@Override
	public void sendEmail(Map<String, Object> map, String address, String subject) {

		final EmailSender mailSender = createMailSender();

		mailSender.setFrom(javaMailProperties.getProperty("from"));
		mailSender.setSubject(subject);
		mailSender.setTemplate(mailTemplateFile);
		mailSender.setAddress(address);
		mailSender.setMap(map);
		mailSender.setUseVelocity(useVelocity);
		mailSender.setContent(content);
		Thread sendThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					mailSender.sendEmail();
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error("SendMail Failed!", e);
				}
			}
		});

		sendThread.start();
	}

	@Override
	public void sendBatchEmail(Map<String, Object> map, String[] addresses, String subject) {

		final EmailSender mailSender = createMailSender();

		mailSender.setFrom(javaMailProperties.getProperty("from"));
		mailSender.setSubject(subject);
		mailSender.setTemplate(mailTemplateFile);
		mailSender.setMap(map);
		mailSender.setUseVelocity(useVelocity);
		mailSender.setContent(content);
		final String[] add = addresses;
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

	public String getMailList(List<String> to) {
		StringBuffer toList = new StringBuffer();
		int length = to.size();
		if (to != null && length < 2) {
			toList.append(to.get(0));
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(to.get(i));
				if (i != (length - 1)) {
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}

	/* **** private method **** */

	private EmailSender createMailSender() {
		EmailSender mailSender = new EmailSender();

		JavaMailSender sender = getJMailSender();

		mailSender.setSender(sender);
		mailSender.setVelocityEngine(velocityEngine);

		return mailSender;
	}

	/* 创建 JavaMailSender */

	private JavaMailSender getJMailSender() {

		// 初始化属性

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(javaMailProperties.getProperty("host"));
		sender.setUsername(javaMailProperties.getProperty("username"));
		sender.setPassword(javaMailProperties.getProperty("password"));
		sender.setDefaultEncoding(javaMailProperties.getProperty("encoding"));
		sender.setJavaMailProperties(javaMailProperties);

		return sender;
	}

	public void setDefaultTemplateFile(String defaultTemplateFile) {
		this.defaultTemplateFile = defaultTemplateFile;
	}

	public void setMailTemplateFile(String mailTemplateFile) {
		this.mailTemplateFile = defaultTemplateFile + mailTemplateFile;
	}

	public void setJavaMailProperties(Properties javaMailProperties) {
		this.javaMailProperties = javaMailProperties;
	}

	public boolean isUseVelocity() {
		return useVelocity;
	}

	public void setUseVelocity(boolean useVelocity) {
		this.useVelocity = useVelocity;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public void setSendMailParam(boolean useVelocity,String sendContent){
		this.useVelocity=useVelocity;
		this.content=sendContent;
	}

}
