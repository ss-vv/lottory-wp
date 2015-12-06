package com.davcai.lottery.platform.client.anruizhiying.model;

import java.math.BigDecimal;

public class WinInfo {
	
	private int lotteryId;
	private long channelTicketId;
	private BigDecimal winamt;
	private int ticketStatus;
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public long getChannelTicketId() {
		return channelTicketId;
	}
	public void setChannelTicketId(long channelTicketId) {
		this.channelTicketId = channelTicketId;
	}
	public BigDecimal getWinamt() {
		return winamt;
	}
	public void setWinamt(BigDecimal winamt) {
		this.winamt = winamt;
	}
	public int getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(int ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

}
