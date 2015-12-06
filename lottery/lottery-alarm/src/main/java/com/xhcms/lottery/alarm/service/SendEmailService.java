package com.xhcms.lottery.alarm.service;

import java.util.Set;

public interface SendEmailService {
	void send(String msgContent,Set<String> receptEmails,String subject);
}
