package com.davcai.lottery.persist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.unison.lottery.mc.uni.parser.TicketOrderResponse;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.TicketRemoteDao;
import com.xhcms.lottery.commons.persist.entity.TicketRemotePO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * @author yangbo
 */
public class OrderTicketServiceImpl implements OrderTicketService {

	@Autowired
	private TicketService ticketService;
	private List<String> shitidianLotteryPlatformIds=new ArrayList<String>();
	@Autowired
	private TicketRemoteDao ticketRemoteDao;
	@Override
	public void updateSuccessfullySendedTickets(
			List<TicketOrderResponse> responses) {
		for (TicketOrderResponse response : responses) {
			ticketService.updateTicketStatusForOrder(response.getTicketId(), response.getStatusCode(), response.getErrorMsg());    
		}
	}
	
	@Override
	public void updateFailSendedTickets(List<Ticket> tickets,
			List<TicketOrderResponse> responses) {
		Collection<Long> failSendedTickets = pickupFailSendedTickets(tickets, responses);
		if (failSendedTickets.size()>0){
			ticketService.updateTicketsStatusTo(EntityStatus.TICKET_INIT, 
					EntityStatus.TICKET_ACTUAL_STATUS_ORDER_NO_RESPONSE, failSendedTickets);
		}
	}

	@SuppressWarnings("unchecked")
	private Collection<Long> pickupFailSendedTickets(List<Ticket> tickets,
			List<TicketOrderResponse> responses) {

		Collection<Long> successfullySendedTickets = CollectionUtils.collect(responses, new Transformer() {
			@Override
			public Object transform(Object element) {
				TicketOrderResponse response = (TicketOrderResponse) element;
				return response.getTicketId();
			}
		});
		
		Collection<Long> toBeSendedTickets = CollectionUtils.collect(tickets, new Transformer() {
			@Override
			public Object transform(Object element) {
				Ticket ticket = (Ticket) element;
				return ticket.getId();
			}
		});
		
		Collection<Long> failSendedTickets = CollectionUtils.subtract(toBeSendedTickets, successfullySendedTickets);
		return failSendedTickets;
	}

	@Override
	@Transactional
	public void updateTicketsByResponse(OrderTicketResponse result) {
		if(null!=result&&null!=result.getResponse4Loops()&&!result.getResponse4Loops().isEmpty()){
			for( OrderTicketResponse4OneLoop orderTicketResponse4OneLoop:result.getResponse4Loops()){
				handleResponse(orderTicketResponse4OneLoop);
			}
		}
		
	}

	private void handleResponse(
			OrderTicketResponse4OneLoop orderTicketResponse4OneLoop) {
		// 分别更新成功和失败的票状态
		if (null != orderTicketResponse4OneLoop) {
			if (null != orderTicketResponse4OneLoop.getFailTickets()
					&& !orderTicketResponse4OneLoop.getFailTickets().isEmpty()) {// 更新失败的票的状态和lotteryPlatformId、actualcode
				for (Ticket ticket : orderTicketResponse4OneLoop.getFailTickets()) {
					ticketService.updateTicketStatusAndLotteryPlatformIdAndActualCode(
									ticket.getId(), EntityStatus.TICKET_INIT,
									ticket.getActualStatus(),
									ticket.getMessage(),
									ticket.getLotteryPlatformId(),
									ticket.getActualCode(),
									ticket.getNumber(),
									ticket.getOdds());
				}
			}
			if (null != orderTicketResponse4OneLoop.getSuccTickets()
					&& !orderTicketResponse4OneLoop.getSuccTickets().isEmpty()) {
				for(Ticket ticket:orderTicketResponse4OneLoop.getSuccTickets()){
					int status=makeStatus(ticket);
					ticketService.updateTicketStatusAndLotteryPlatformIdAndActualCode(
							ticket.getId(), status,
							ticket.getActualStatus(),
							ticket.getMessage(),
							ticket.getLotteryPlatformId(),
							ticket.getActualCode(),
							ticket.getNumber(),
							ticket.getOdds());
					TicketRemotePO ticketRemotePO = new TicketRemotePO();
					ticketRemotePO.setCreatedTime(new Date());
					ticketRemotePO.setMessage(ticket.getMessage());
					ticketRemotePO.setOrderId(orderTicketResponse4OneLoop.getOrderId());
					ticketRemotePO.setStatus(orderTicketResponse4OneLoop.getStatus()+"");
					ticketRemotePO.setTicketId(ticket.getId());
					ticketRemoteDao.save(ticketRemotePO);
				}
			}
		}
//		this.updateSuccessfullySendedTickets(responses);
//		this.updateFailSendedTickets(tickets, responses);
	}

	/**
	 * 默认返回已请求出票，否则，返回EntityStatus.TICKET_INIT
	 * @param ticket
	 * @return
	 */
	private int makeStatus(Ticket ticket) {
		if(StringUtils.equals(ticket.getLotteryPlatformId(), LotteryPlatformId.AN_RUI_ZHI_YING)){//安瑞至赢如果是成功的话，不应改变状态
			return EntityStatus.TICKET_REQUIRED;
		} else if(StringUtils.equals(ticket.getLotteryPlatformId(), LotteryPlatformId.ZUN_AO)){//尊奥如果是成功的话，如果不是真实成功状态，设置为TICKET_INIT状态
			if(!ticketService.isZMSendTicketOKStatus(Integer.toString(ticket.getActualStatus()))){
				return EntityStatus.TICKET_INIT;
			}
		} else if(shitidianLotteryPlatformIds.contains(ticket.getLotteryPlatformId())){
			return EntityStatus.TICKET_READY_FOR_HANDWORK;
		} else if(LotteryPlatformId.YUAN_CHENG_CHU_PIAO.equals(ticket.getLotteryPlatformId())){
			return EntityStatus.TICKET_REQUIRED;//远程出票成功的返回  已请求出票
		}
		return EntityStatus.TICKET_REQUIRED;
	}

	public List<String> getShitidianLotteryPlatformIds() {
		return shitidianLotteryPlatformIds;
	}

	public void setShitidianLotteryPlatformIds(
			List<String> shitidianLotteryPlatformIds) {
		this.shitidianLotteryPlatformIds = shitidianLotteryPlatformIds;
	}
}
