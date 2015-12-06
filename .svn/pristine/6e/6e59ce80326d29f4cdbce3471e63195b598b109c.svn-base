package com.xhcms.ucenter.service.message.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.ucenter.persist.dao.IMessageDao;
import com.xhcms.ucenter.persist.dao.ISysMessageDao;
import com.xhcms.ucenter.service.message.IMessageService;

public class MessageServiceImpl implements IMessageService {
	@Autowired
	private IMessageDao messageDao;
	
	@Autowired
	private ISysMessageDao sysMessageDao;
	
	@Override
	@Transactional
	public int getUnreadCount(Long userId) {
		int countm = messageDao.getUnreadMessageCount(userId);
		int counts = sysMessageDao.getUnreadSysMessageCount(userId);
		
		return countm + counts;
	}

}
