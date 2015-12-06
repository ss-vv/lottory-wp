package com.davcai.lottery.platform.client.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.xhcms.lottery.commons.data.Ticket;

public class OrderTicketResponse4OneLoop {
	private int status;
	private String desc;
	private boolean succ;
	private List<Ticket> succTickets;
	private List<Ticket> failTickets;
	private String lotteryPlatformId;
	private String orderId;
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isSucc() {
		return succ;
	}
	public void setSucc(boolean succ) {
		this.succ = succ;
	}
	public List<Ticket> getSuccTickets() {
		return succTickets;
	}
	public void setSuccTickets(List<Ticket> succTickets) {
		this.succTickets = succTickets;
	}
	public List<Ticket> getFailTickets() {
		return failTickets;
	}
	public void setFailTickets(List<Ticket> failTickets) {
		this.failTickets = failTickets;
	}

	public String getLotteryPlatformId() {
		return lotteryPlatformId;
	}

	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}