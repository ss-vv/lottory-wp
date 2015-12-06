package com.xhcms.lottery.alarm.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * email 连接信息
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-4-21 下午2:40:54
 */
@Component
public class EmailConfig {
	private String host;
	private boolean smtpAuth;
	private String smtpTimeout;
	private String username;
	private String password;
	private String from;
	private String encoding;
	
	public String getHost() {
		return host;
	}
	@Value("${mail.host}")
	public void setHost(String host) {
		this.host = host;
	}
	public boolean isSmtpAuth() {
		return smtpAuth;
	}
	@Value("${mail.smtp.auth}")
	public void setSmtpAuth(boolean smtpAuth) {
		this.smtpAuth = smtpAuth;
	}
	public String getSmtpTimeout() {
		return smtpTimeout;
	}
	@Value("${mail.smtp.timeout}")
	public void setSmtpTimeout(String smtpTimeout) {
		this.smtpTimeout = smtpTimeout;
	}
	public String getUsername() {
		return username;
	}
	@Value("${mail.username}")
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	@Value("${mail.password}")
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFrom() {
		return from;
	}
	@Value("${mail.from}")
	public void setFrom(String from) {
		this.from = from;
	}
	public String getEncoding() {
		return encoding;
	}
	@Value("${mail.encoding}")
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
