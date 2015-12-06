package com.xhcms.lottery.alarm.service;

import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;

public abstract class AbstractEmailSender {
	protected String from;
	protected String subject;
	protected JavaMailSender sender;
	protected String address;
	protected List<String> addressList;
	protected String emailContent;
	
	public abstract void sendEmail();
	
	public String getFrom() {
		return from;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<String> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
}
