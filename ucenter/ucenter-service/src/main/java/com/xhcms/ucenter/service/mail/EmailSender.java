package com.xhcms.ucenter.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.exception.VelocityException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class EmailSender extends EmailAbstract {

	public void sendEmail() {
		try {
			MimeMessage msg = sender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(msg, true, "UTF-8");
			message.setFrom(from);
			message.setSubject(subject);
			message.setTo(address);
			if(this.useVelocity){
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", map);
				message.setText(text, true); // 如果发的不是html内容去掉true参数
			}else{
				message.setText(content,false);//不使用模版
			}
			sender.send(msg);
		} catch (VelocityException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MailException me) {
			me.printStackTrace();
		}
	}

}
