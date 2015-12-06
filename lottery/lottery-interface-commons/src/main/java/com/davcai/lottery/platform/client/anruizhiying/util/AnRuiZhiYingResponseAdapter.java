package com.davcai.lottery.platform.client.anruizhiying.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;





import java.util.Map;

import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingOrderTicketResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingQueryPrizeResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.WinInfo;
import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class AnRuiZhiYingResponseAdapter {

	public static OrderTicketResponse toOrderTicketResponse(
			AnRuiZhiYingOrderTicketResponse response, List<Ticket> tickets) {
		if(null==response){
			return null;
		}
		OrderTicketResponse orderTicketResponse=new OrderTicketResponse();
		OrderTicketResponse4OneLoop response4OneLoop=new OrderTicketResponse4OneLoop();
		response4OneLoop.setDesc(response.getDesc());
		response4OneLoop.setStatus(response.getStatus());
		if(response.getStatus()==0){
			response4OneLoop.setSucc(true);
			response4OneLoop.setSuccTickets(tickets);
		}
		else{
			response4OneLoop.setSucc(false);
			response4OneLoop.setFailTickets(tickets);
		}
		return orderTicketResponse;
	}

	public static OrderTicketResponse4OneLoop toOrderTicketResponse4OneLoop(
			AnRuiZhiYingOrderTicketResponse response, List<Ticket> tickets) {
		if(null==response){
			return null;
		}
		OrderTicketResponse4OneLoop response4OneLoop=new OrderTicketResponse4OneLoop();
		response4OneLoop.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		response4OneLoop.setDesc(response.getDesc());
		response4OneLoop.setStatus(response.getStatus());
		if(null!=tickets&&!tickets.isEmpty()){
			for(Ticket ticket:tickets){
				ticket.setActualStatus(response.getStatus());
				ticket.setMessage(response.getDesc());
			}
		}
		
		if(response.getStatus()==0){
			response4OneLoop.setSucc(true);
			response4OneLoop.setSuccTickets(tickets);
		}
		else{
			response4OneLoop.setSucc(false);
			response4OneLoop.setFailTickets(tickets);
		}
		return response4OneLoop;
	}

	public static QueryPrizeResponse4OneLoop toQueryPrizeResponse(
			AnRuiZhiYingQueryPrizeResponse response) {
		if(null!=response){
			QueryPrizeResponse4OneLoop queryPrizeResponse4OneLoop=new QueryPrizeResponse4OneLoop();
			Map<Long, Ticket> ticketsMap=null;
			if(null!=response.getWinTickets()&&null!=response.getWinTickets().getWinInfos()&&!response.getWinTickets().getWinInfos().isEmpty()){
				ticketsMap=new HashMap<Long,Ticket>();
				for(WinInfo winInfo:response.getWinTickets().getWinInfos()){
					Ticket ticket=new Ticket();
					ticket.setId(winInfo.getChannelTicketId());
					ticket.setAfterTaxBonus(winInfo.getWinamt());
					ticket.setStatus(makeStatus(winInfo));
					ticketsMap.put(ticket.getId(), ticket);
				}
			}
			queryPrizeResponse4OneLoop.setTicketsMap(ticketsMap);
			return queryPrizeResponse4OneLoop;
		}
		return null;
	}

	private static int makeStatus(WinInfo winInfo) {
		if(winInfo.getTicketStatus()==4){
			if(winInfo.getWinamt().compareTo(BigDecimal.ZERO)>0){//已中奖
				return EntityStatus.TICKET_NOT_AWARD;
			}
			else if(winInfo.getWinamt().compareTo(BigDecimal.ZERO)==0){//未中奖
				return EntityStatus.TICKET_NOT_WIN;
			}
		}
		return -1;
	}

}
