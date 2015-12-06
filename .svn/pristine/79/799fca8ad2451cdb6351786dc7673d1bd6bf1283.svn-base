package com.unison.lottery.mc.uni.parser;

import java.util.List;

import org.dom4j.Element;

/**
 * 投注请求的响应消息解析器。
 * 
 * @author Yang Bo
 */
public class OrderTicketResponseParser extends MessageParser {

    private static final long serialVersionUID = -6208429970681239119L;

    public OrderTicketResponseParser(){
    	setExpectedTransCode(102);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void parseBody(Element body, ParserStatus status) {
        List<Element> tickets = body.element("tickets").elements("ticket");
        OrderTicketResponseParserStatus responseStatus = (OrderTicketResponseParserStatus) status;
        for(Element ticket: tickets){
        	saveTicketResponse(ticket, responseStatus);
        }
    }

	private void saveTicketResponse(Element ticket, OrderTicketResponseParserStatus responseStatus) {
		String statusCode = ticket.attributeValue("statusCode");
		String message = ticket.attributeValue("message");
        String detailMessage = ticket.attributeValue("detailmessage");
        String errorMsg = new StringBuffer("statusCode=").append(statusCode).append("；")
            	.append(message).append("；")
            	.append(detailMessage).toString();
        long ticketId = Long.parseLong(ticket.attributeValue("ticketId"));
        responseStatus.addResponse(ticketId, statusCode, errorMsg);
	}
}
