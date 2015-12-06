package com.xhcms.lottery.mc.persist.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.TicketPO;

/**
 * 与票相关的测试用服务。
 *
 * @author yangbo
 */
@Transactional
public class TicketServiceForTest {

    @Autowired
    private TicketDao ticketDao;

	public void createTicket(Ticket ticket) {
		TicketPO po = new TicketPO();
		BeanUtils.copyProperties(ticket, po);
		ticketDao.save(po);
		ticket.setId(po.getId());
	}

	public void deleteTicket(long ticketId) {
		TicketPO ticket = ticketDao.get(ticketId);
		ticketDao.delete(ticket);
	}
}
