package com.davcai.lottery.platform.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import org.apache.commons.lang3.StringUtils;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

public class TicketGroupUtil {

	public static Map<LotteryId, List<Ticket>> groupByLotteryId(
			List<Ticket> tickets) {
		if(null==tickets||tickets.isEmpty()){
			return null;
		}
		Map<LotteryId, List<Ticket>> result=new HashMap<LotteryId, List<Ticket>>();
		for(Ticket ticket:tickets){
			PlayType playType=PlayType.valueOfLcPlayId(ticket.getPlayId());
			if(null==playType){
				break;
				
			}
			LotteryId lotteryId=playType.getLotteryId();
			if(null!=lotteryId){
				ticket.setLotteryId(lotteryId);
				if(result.containsKey(lotteryId)){
					result.get(lotteryId).add(ticket);
				}
				else{
					List<Ticket> ticketList=new ArrayList<Ticket>();
					ticketList.add(ticket);
					result.put(lotteryId, ticketList);
				}
			}
		}
		return result;
	}

}
