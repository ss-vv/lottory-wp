package com.unison.lottery.mc.uni.client;

import java.util.HashMap;
import java.util.List;

import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.OrderTicketResponseParserStatus;
import com.xhcms.lottery.commons.data.Ticket;

/**
 * 投注接口客户端（002）。
 * @author Yang Bo
 * @version 1.0.0
 */
public class OrderTicketClient extends ZMClient{
    
	public OrderTicketClient(){
		setTranscode("002");
	}
	
    public boolean post(String lotteryId, int totalMoney, List<Ticket> tickets, 
    		OrderTicketResponseParserStatus status){
        HashMap<String, Object> values = new HashMap<String, Object>();
        
        values.put("lotteryId", lotteryId);
        values.put("totalMoney", totalMoney);
        values.put("tickets", tickets);
        values.put("ticketNum", tickets.size());
        
        return postWithStatus(values, status);
    }

}
