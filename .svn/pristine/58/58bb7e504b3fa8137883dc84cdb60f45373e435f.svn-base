package com.davcai.lottery.platform.client.shitidian;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.ILotteryPlatformOrderTicketClient;
import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.utils.ShiTiDianBetContentUpgradeUtil;

public abstract class AbstractShiTiDianLotteryPlatformOrderTicketClient implements
		ILotteryPlatformOrderTicketClient {

	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private ShiTiDianBetContentUpgradeUtil shiTiDianBetContentUpgradeUtil;
	@Override
	public OrderTicketResponse orderTicketByLoops(List<Ticket> tickets) {
		OrderTicketResponse response=null;
		
		if(null!=tickets&&!tickets.isEmpty()){
			response=new OrderTicketResponse();
			List<OrderTicketResponse4OneLoop> orderTicketResponse4OneLoopList=new ArrayList<OrderTicketResponse4OneLoop>();
			OrderTicketResponse4OneLoop orderTicketResponse4OneLoop=new OrderTicketResponse4OneLoop();
			for(Ticket ticket:tickets){
				ticket.setActualStatus(ShiTiDianActualStatus.READY_TO_PROCESS);
				ticket.setLotteryPlatformId(getLotteryPlatformId());
				ticket.setMessage(ShiTiDianMessage.READY_TO_PROCESS);
				ticket.setOdds(ticket.getDavcaiOdds());
				String number=makeNumber();
				ticket.setNumber(number);
				
			}
			Set<Long> ticketIds=makeTicketIds(tickets);
			shiTiDianBetContentUpgradeUtil.tryUpgradeOrDowngradeBetContent(getLotteryPlatformId(),ticketIds);
			orderTicketResponse4OneLoop.setSuccTickets(tickets);//按照出票成功处理
			orderTicketResponse4OneLoopList.add(orderTicketResponse4OneLoop);
			response.setResponse4Loops(orderTicketResponse4OneLoopList);
		}
		
		return response;
	}

	private Set<Long> makeTicketIds(List<Ticket> tickets) {
		Set<Long> result=new HashSet<Long>();
		for(Ticket ticket:tickets){
			result.add(ticket.getId());
		}
		return result;
	}

	private String makeNumber() {
		return UUID.randomUUID().toString();
	}

	protected abstract String getLotteryPlatformId();
}
