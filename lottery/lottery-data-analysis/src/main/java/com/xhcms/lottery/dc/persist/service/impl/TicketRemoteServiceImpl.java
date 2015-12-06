package com.xhcms.lottery.dc.persist.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.TicketRemoteDao;
import com.xhcms.lottery.dc.persist.service.TicketRemoteService;
import com.xhcms.lottery.lang.EntityStatus;

public class TicketRemoteServiceImpl implements TicketRemoteService{
	@Autowired
	private TicketRemoteDao ticketRemoteDao;
	Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	@Transactional
	public List<String> listRequiredTicket() {
		//查询   状态为：成功回应、等待出票    的票
		String status[]={EntityStatus.REMOTE_SUCCESS,EntityStatus.REMOTE_WAIT};
		return ticketRemoteDao.findRemoteTicketByStatus(status);
	}
	@Override
	@Transactional
	public List<String> listSuccessTicket() {
		//查询   状态为：成功出票    的票
		String status[]={EntityStatus.REMOTE_TICKET_SUCCESS};
		return ticketRemoteDao.findRemoteTicketByStatus(status);
	}

}
