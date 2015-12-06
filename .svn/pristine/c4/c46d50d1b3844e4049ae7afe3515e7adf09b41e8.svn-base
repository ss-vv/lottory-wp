package com.unison.lottery.mc.uni.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * OrderTicketResponseParser 使用的 ParserStatus 类。
 * @author Yang Bo
 *
 */
public class OrderTicketResponseParserStatus extends ParserStatus {

	private List<TicketOrderResponse> responses = new LinkedList<TicketOrderResponse>();
	
	public void addResponse(long ticketId, String statusCode, String errorMsg){
		this.responses.add(new TicketOrderResponse(ticketId, statusCode, errorMsg));
	}
	
	public List<TicketOrderResponse> getResponses() {
		return responses;
	}
}
