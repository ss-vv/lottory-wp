package com.unison.lottery.mc.uni.parser;

public class TicketOrderResponse{
	private long ticketId;
	private String statusCode;
	private String errorMsg;
	
	public TicketOrderResponse(long ticketId, String statusCode, String errorMsg){
		this.ticketId = ticketId;
		this.statusCode = statusCode;
		this.errorMsg = errorMsg;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}