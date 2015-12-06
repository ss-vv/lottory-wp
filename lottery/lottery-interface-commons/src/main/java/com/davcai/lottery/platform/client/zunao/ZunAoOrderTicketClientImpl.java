package com.davcai.lottery.platform.client.zunao;

import java.util.ArrayList;
import java.util.HashMap; 
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.davcai.lottery.platform.client.AbstractLotteryPlatformOrderTicketClient;
import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.unison.lottery.mc.uni.client.OrderTicketClient;
import com.unison.lottery.mc.uni.parser.OrderTicketResponseParserStatus;
import com.unison.lottery.mc.uni.parser.TicketOrderResponse;
import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.PlayType;

public class ZunAoOrderTicketClientImpl extends AbstractLotteryPlatformOrderTicketClient  {

	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("zunAoOrderTicketOldClient")
	private OrderTicketClient client;

	private String makeActualCodeByLotteryPlatformId(String platformId,
			Map<String, String> platformBetCode) {
		return null==platformBetCode?null:platformBetCode.get(platformId);
	}


	@Override
	public OrderTicketResponse4OneLoop orderTicketForOneLoop(
			List<Ticket> tickets) {
		
		if(null==tickets||tickets.isEmpty()){
			return null;
		}
		OrderTicketResponse4OneLoop result=null;
		String zmLotteryId;
		try {
			zmLotteryId = Translator.lcPlayIdToZMLotteryId(tickets.get(0).getPlayId());
			OrderTicketResponseParserStatus status = new OrderTicketResponseParserStatus();
			int totalMoney=0;
			for(Ticket ticket:tickets){
				totalMoney=totalMoney+ticket.getMoney();
			}
			boolean ret = client.post(zmLotteryId, totalMoney, tickets, status);
			result=parse(ret,status,tickets);
			
		} catch (TranslateException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private OrderTicketResponse4OneLoop parse(boolean ret,
			OrderTicketResponseParserStatus status, List<Ticket> tickets) {
		OrderTicketResponse4OneLoop result=new OrderTicketResponse4OneLoop();
		result.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		if(null!=status&&null!=status.getResponses()&&!status.getResponses().isEmpty()){
			result.setSuccTickets(transferToSuccTickets(status.getResponses(),tickets));
			result.setFailTickets(transferToFailTickets(status.getResponses(),tickets));
		}
		return result;
	}


	private List<Ticket> transferToFailTickets(
			List<TicketOrderResponse> responses, List<Ticket> tickets) {
		List<Ticket> result=new ArrayList<Ticket>();
		Map<Long,TicketOrderResponse> map=toResponseMap(responses);
		for( Ticket ticket:tickets){
			if(!map.containsKey(ticket.getId())){
				result.add(ticket);
			}
			
		}
		return result;
	}


	private Map<Long, TicketOrderResponse> toResponseMap(List<TicketOrderResponse> responses) {
		Map<Long, TicketOrderResponse> map=new HashMap<Long, TicketOrderResponse>();
		for(TicketOrderResponse response:responses){
			map.put(response.getTicketId(), response);
		}
		return map;
	}


	private Map<Long, Ticket> toMap(List<Ticket> tickets) {
		Map<Long, Ticket> map=new HashMap<Long, Ticket>();
		for(Ticket ticket:tickets){
			map.put(ticket.getId(), ticket);
		}
		return map;
	}


	private List<Ticket> transferToSuccTickets(List<TicketOrderResponse> responses,
			List<Ticket> tickets) {
		List<Ticket> result=new ArrayList<Ticket>();
		Map<Long,Ticket> map=toMap(tickets);
		for(TicketOrderResponse response:responses){
			try{
				Ticket ticket = map.get(response.getTicketId());
				if(null!=ticket){
					ticket.setActualStatus(Integer.parseInt(response.getStatusCode()));
					ticket.setMessage(response.getErrorMsg());
					result.add(ticket);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return result;
	}


	public OrderTicketClient getClient() {
		return client;
	}

	public void setClient(OrderTicketClient client) {
		this.client = client;
	}

	@Override
	protected int getMaxCountForOneLoop() {
		return 50;
	}

	@Override
	protected void preProcess(Ticket ticket) {
		Translator.translateBetType(ticket);
		makeLotteryPlatformId(ticket);
		makeActualCode(ticket);
		
	}

	private void makeLotteryPlatformId(Ticket ticket) {
		ticket.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
	}

	private void makeActualCode(Ticket ticket) {
		String actualCode = makeActualCodeByLotteryPlatformId(LotteryPlatformId.ZUN_AO,ticket.getPlatformBetCodeMap());
		if(StringUtils.isNotBlank(actualCode)){//在上多平台之后生成的票
			ticket.setActualCode(actualCode);
		}
		else{//是上多平台之前生成的票，按尊奥平台的要求处理
			try {
				PlayType playType=PlayType.valueOfLcPlayId(ticket.getPlayId());
				if(null!=playType){
					if(playType.getLotteryId()==LotteryId.JCLQ||playType.getLotteryId()==LotteryId.JCZQ){//如果是竞彩足球和篮球，才强制转换
						ticket.setActualCode(Translator.translateTicketCodeToPFormat(ticket));
					}
				}
				
			} catch (TranslateException e) {
				logger.error("Can not translate bet content! {}", e.getMessage());
			}
		}
		
	}

	@Override
	protected Map<String, List<Ticket>> groupTickets(List<Ticket> tickets) {
		Map<String, List<Ticket>> groups = new HashMap<String, List<Ticket>>();
		for (Ticket tk : tickets) {
			String playId = tk.getPlayId();
			if (!groups.containsKey(playId)){
				groups.put(playId, new LinkedList<Ticket>());
			}
			groups.get(playId).add(tk);
		}
		return groups;
	}

}
