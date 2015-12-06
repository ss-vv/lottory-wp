package com.xhcms.ucenter.service.message.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.ucenter.service.message.IMessageService;

public class MessageServiceCache implements IMessageService {

	@Autowired
	private IMessageService messageService;
	
	@Override
	@Transactional
	public int getUnreadCount(Long userId) {
		return messageService.getUnreadCount(userId);
	}

}
