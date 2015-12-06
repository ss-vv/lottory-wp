package com.unison.lottery.mc.uni.parser;

import java.util.HashMap;
import java.util.Map;

import com.xhcms.lottery.commons.data.Ticket;

/**
 * 封装一论QueryPrizeParser解析过程的状态。<br/>
 * 一个 QueryPrizeParserStatus 会被用于一期彩票中奖查询的所有页面解析。
 * 
 * 目的是让 QueryPrizeParser 变为线程安全类。
 * 
 * @author Yang Bo
 */
public class QueryPrizeParserStatus extends ParserStatus {

	private String prevPlatformTicketId = null;
	private boolean isReachLastPage = false;
	private Map<Long, Ticket> ticketIdTickets = new HashMap<Long, Ticket>();
	
	public void clearTickets(){
		this.ticketIdTickets.clear();
	}
	
	/**
	 * 重置解析器到初始状态，可以用来解析新一轮查询（即新的一期分页查询）。
	 */
	public void resetParser() {
		prevPlatformTicketId = null;
		isReachLastPage = false;
	}
	
	public String getPrevPlatformTicketId() {
		return prevPlatformTicketId;
	}
	
	public void setPrevPlatformTicketId(String prevPlatformTicketId) {
		this.prevPlatformTicketId = prevPlatformTicketId;
	}

	public void setReachLastPage(boolean isReachLastPage) {
		this.isReachLastPage = isReachLastPage;
	}

	public boolean isReachLastPage() {
		return isReachLastPage;
	}

	public Map<Long, Ticket> getTicketIdTickets() {
		return ticketIdTickets;
	}
}
