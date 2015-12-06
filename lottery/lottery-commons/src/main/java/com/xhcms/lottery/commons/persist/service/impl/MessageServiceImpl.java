package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Message;
import com.xhcms.lottery.commons.data.SysMessage;
import com.xhcms.lottery.commons.persist.dao.MessageDao;
import com.xhcms.lottery.commons.persist.dao.SysMessageDao;
import com.xhcms.lottery.commons.persist.entity.MessagePO;
import com.xhcms.lottery.commons.persist.entity.SysMessagePO;
import com.xhcms.lottery.commons.persist.service.MessageService;
import com.xhcms.lottery.lang.UCMessageStatus;

/**
 * @desc 系统站内信息服务
 * @author yonglizhu
 */
public class MessageServiceImpl implements MessageService {

	@Autowired
	private SysMessageDao sysMessageDao;
	
	@Autowired
	private MessageDao messageDao;
		
	@Override
	@Transactional
	public void listSysMessage(Paging paging, String subject) {
		if(StringUtils.isNotBlank(subject)) {
			subject = subject.trim();
		}
		List<SysMessagePO> sysMessagePOs = sysMessageDao.listSysMessage(paging, subject);
		List<SysMessage> results = new ArrayList<SysMessage>(sysMessagePOs.size());
		if(null != sysMessagePOs) {
			for(SysMessagePO po : sysMessagePOs) {
				SysMessage sm = new SysMessage();
				BeanUtils.copyProperties(po, sm);
	            results.add(sm);
			}
			paging.setResults(results);
		}
	}
	
	@Override
	@Transactional
	public void add(SysMessagePO sysMessagePO) {
		sysMessageDao.save(sysMessagePO);
	}
	
	@Override
	@Transactional
	public void modify(SysMessage sysMessage) {
		Long id = sysMessage.getId();
		SysMessagePO sysMessagePO = sysMessageDao.get(id);
		
		sysMessagePO.setSubject(sysMessage.getSubject());
		sysMessagePO.setNote(sysMessage.getNote());
		sysMessagePO.setAuthorid(sysMessage.getAuthorid());
		sysMessagePO.setAuthor(sysMessage.getAuthor());
		sysMessagePO.setStatus(sysMessage.getStatus());
	}
	
	@Override
	@Transactional
	public void remove(Long sysMessageId) {
		sysMessageDao.deleteById(sysMessageId);
	}
	
	@Override
	@Transactional
	public void syncAndListMyMessages(Paging paging, Long userId, String username) {
		messageDao.syncSysMessage(userId, username);

		List<MessagePO> messagePOs = messageDao.find(paging, userId, UCMessageStatus.NORMAL);
		
		List<Message> list = new ArrayList<Message>();
		if(null != messagePOs && !messagePOs.isEmpty()) {
			for (MessagePO messagePO : messagePOs) {
				Message message = new Message();
				BeanUtils.copyProperties(messagePO, message);
				list.add(message);
			}
		}
		paging.setResults(list);
	}
	
	@Override
	@Transactional
	public void listMyMessages(Paging paging, Long userId) {
		List<MessagePO> messagePOs = messageDao.find(paging, userId, UCMessageStatus.NORMAL);
		
		List<Message> list = new ArrayList<Message>();
		if(null != messagePOs && !messagePOs.isEmpty()) {
			for (MessagePO messagePO : messagePOs) {
				Message message = new Message();
				BeanUtils.copyProperties(messagePO, message);
				list.add(message);
			}
		}
		paging.setResults(list);
	}
	
	@Override
	@Transactional
	public Message read(Long messageId) {
		Message message = null;
		MessagePO messagePO = messageDao.get(messageId);
		if(null != messagePO) {
			message = new Message();
			messagePO.setRead(UCMessageStatus.READ);
			BeanUtils.copyProperties(messagePO, message);
		}
		
		return message;
		
	}
	
	@Override
	@Transactional
	public void removeMyMessage(List<Long> ids) {
		if(null != ids && !ids.isEmpty()) {
			for(Long id : ids) {
				MessagePO messagePO = messageDao.get(id);
				if(messagePO.getSysMessageId() == 0) {
					messageDao.deleteById(id);
				} else {
					messagePO.setDelStatus(UCMessageStatus.DELETED);
				}
			}
		}
	}
	
	@Override
	@Transactional
	public int getUnreadCount(Long userId) {
		int countm = messageDao.getUnreadMessageCount(userId);
		int counts = sysMessageDao.getUnreadSysMessageCount(userId);
		
		return countm + counts;
	}
}