package com.xhcms.ucenter.service.mail;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;

public abstract class EmailAbstract {
	protected String from;
	protected String subject;
	protected JavaMailSender sender;
	protected VelocityEngine velocityEngine;
	protected Map<String, Object> map;
	protected String address;
	protected String template;
	protected List<String> addressList;
	protected boolean useVelocity;//是否使用模版 
	protected String content;//发送内容

	public List<String> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public String getFrom() {
		return from;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public JavaMailSender getSender() {
		return sender;
	}

	public void setSender(JavaMailSender sender) {
		this.sender = sender;
	}

	public String getTemplate() {
		return template;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setTemplate(String template) {
		this.template = template;
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

	/**
	 * 发送单个html格式邮件
	 * 
	 * @throws MessagingException
	 */
	public abstract void sendEmail();
}
