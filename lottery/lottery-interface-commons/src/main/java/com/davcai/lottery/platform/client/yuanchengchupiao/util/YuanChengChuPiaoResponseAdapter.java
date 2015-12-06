package com.davcai.lottery.platform.client.yuanchengchupiao.util;

import java.util.List;

import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.constants.YuanChengChuPiaoStatus;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class YuanChengChuPiaoResponseAdapter {
	public static OrderTicketResponse4OneLoop toOrderTicketResponse4OneLoop(
			YuanChengChuPiaoResponse response, List<Ticket> tickets, String orderId) {
		if(null==response){
			return null;
		}
		OrderTicketResponse4OneLoop response4OneLoop=new OrderTicketResponse4OneLoop();
		response4OneLoop.setLotteryPlatformId(LotteryPlatformId.YUAN_CHENG_CHU_PIAO);
		response4OneLoop.setDesc(response.getMessage());
		response4OneLoop.setStatus(Integer.valueOf(response.getError()));
		response4OneLoop.setOrderId(orderId);
		if(null!=tickets&&!tickets.isEmpty()){
			for(Ticket ticket:tickets){
				ticket.setActualStatus(0);
				ticket.setMessage(response4OneLoop.getDesc());
			}
		}
		
		if(response4OneLoop.getStatus()==YuanChengChuPiaoStatus.Wait
				||response4OneLoop.getStatus()==YuanChengChuPiaoStatus.Success){//等待
			response4OneLoop.setSucc(true);
			response4OneLoop.setSuccTickets(tickets);
		} else {
			response4OneLoop.setSucc(false);
			response4OneLoop.setFailTickets(tickets);
		}
		return response4OneLoop;
	}
}
