package com.xhcms.lottery.alarm.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.xhcms.lottery.alarm.service.AbstractEmailSender;

public class EmailSender extends AbstractEmailSender{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void sendEmail() {
		try {
			MimeMessage msg = sender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(msg, true, "UTF-8");
			message.setFrom(from);
			message.setSubject(subject);
			message.setTo(address);
			message.setText(emailContent);
			sender.send(msg);
		} catch (VelocityException e) {
			logger.error("velocity 模板出错！",e);
		} catch (MessagingException e) {
			logger.error("发送email信息异常",e);
		} catch (MailException me) {
			logger.error("发送email信息异常",me);
		}
	}
}
