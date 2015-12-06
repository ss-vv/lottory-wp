package com.xhcms.ucenter.service.mail;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;

public interface IEmailService {
	/**
	 * 发送单封邮件
	 * 
	 * @param map
	 *            模板内容
	 * @param address
	 *            地址
	 * @param subject
	 *            主题
	 * @throws MessagingException
	 */
	public void sendEmail(Map<String, Object> map, String address, String subject) throws Exception;

	/**
	 * 批量发送带附件的html格式邮件
	 * 
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */

	public void sendBatchEmail(Map<String, Object> map, String[] addresses, String subject);

	// public void sendBatchEmail(Map<String, Object> map, List<?> employees,
	// String subject) throws MessagingException,
	// UnsupportedEncodingException;

	public void setMailTemplateFile(String defaultTemplateFile);
	/**
	 * 设置是否使用模版引擎   和发送的内容
	 * @param useVelocity
	 * @param sendContent
	 */
	public void setSendMailParam(boolean useVelocity,String sendContent);

}
