package com.xhcms.lottery.mc.persist.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.davcai.lottery.persist.OrderTicketService;
import com.unison.lottery.mc.uni.parser.OrderTicketResponseParserStatus;
import com.unison.lottery.mc.uni.parser.TicketOrderResponse;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.persist.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/spring-*.xml")
public class OrderTicketServiceImplTest {
	@Autowired
	private OrderTicketService orderTicketService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TicketServiceForTest ticketServiceForTest;

	private long failTicketId;				// 未成功接收的 ticket
	private long successTicketId;			// 成功接收的 ticket
	private long noResponseTicketId;		// 无接收确认消息的 ticket 
	private List<Ticket> allTickets = new LinkedList<Ticket>();

	private List<TicketOrderResponse> responses;
	
	@Before
	public void createTicketsAndResponses() {
		Ticket ticket = generateTicket();
		ticketServiceForTest.createTicket(ticket);
		successTicketId = ticket.getId();
		allTickets.add(ticket);
		
		ticket = generateTicket();
		ticketServiceForTest.createTicket(ticket);
		failTicketId = ticket.getId();
		allTickets.add(ticket);
		
		ticket = generateTicket();
		ticketServiceForTest.createTicket(ticket);
		noResponseTicketId = ticket.getId();
		allTickets.add(ticket);
		
		setupResponses();
	}

	private Ticket generateTicket() {
		Ticket ticket = new Ticket();
		ticket.setActualCode("");
		ticket.setCode("");
		ticket.setSchemeId(0L);
		ticket.setPlayId(Constants.PLAY_01_ZC_1);
		ticket.setCreatedTime(new Date());
		ticket.setPassTypeId("");
		ticket.setPlatformId(0L);
		ticket.setStatus(EntityStatus.TICKET_REQUIRED);
		return ticket;
	}
	
	private void setupResponses() {
		OrderTicketResponseParserStatus status = new OrderTicketResponseParserStatus();
		status.addResponse(successTicketId, "000", "成功接收");
		status.addResponse(failTicketId, "999", "系统故障");
		responses = status.getResponses();
	}

	@After
	public void clearTickets() {
		for (Ticket ticket : allTickets) {
			ticketServiceForTest.deleteTicket(ticket.getId());
		}
	}

	@Test
	public void testUpdateSuccessfullySendedTickets() {
		orderTicketService.updateSuccessfullySendedTickets(responses);
		
		Ticket successTicket = ticketService.getTicket(successTicketId);
		assertEquals(EntityStatus.TICKET_REQUIRED, successTicket.getStatus());
		assertEquals(0, successTicket.getActualStatus());
		
		Ticket failTicket = ticketService.getTicket(failTicketId);
		assertEquals(EntityStatus.TICKET_INIT, failTicket.getStatus());
		assertNotSame(0, failTicket.getActualStatus());
	}
	
	@Test
	public void testUpdateFailSendedTickets() {
		orderTicketService.updateFailSendedTickets(allTickets, responses);
		
		Ticket noResponseTicket = ticketService.getTicket(noResponseTicketId);
		assertEquals(EntityStatus.TICKET_INIT, noResponseTicket.getStatus());
		assertEquals(EntityStatus.TICKET_ACTUAL_STATUS_ORDER_NO_RESPONSE, noResponseTicket.getActualStatus());
		
		Ticket successTicket = ticketService.getTicket(successTicketId);
		assertEquals(EntityStatus.TICKET_REQUIRED, successTicket.getStatus());
		
		Ticket failTicket = ticketService.getTicket(failTicketId);
		assertEquals(EntityStatus.TICKET_REQUIRED, failTicket.getStatus());
	}

}
