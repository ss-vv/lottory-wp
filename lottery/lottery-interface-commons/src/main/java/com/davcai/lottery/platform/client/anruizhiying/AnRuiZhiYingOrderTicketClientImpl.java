package com.davcai.lottery.platform.client.anruizhiying;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.davcai.lottery.platform.client.AbstractClientSupport;
import com.davcai.lottery.platform.client.AbstractLotteryPlatformOrderTicketClient;
import com.davcai.lottery.platform.client.ILotteryPlatformOrderTicketClient;
import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingLotteryId;
import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingTransCode;
import com.davcai.lottery.platform.client.anruizhiying.constants.ParamNames;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingOrderTicketResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingParams;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingBatchIdUtil;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingBetContentUtil;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingResponseAdapter;
import com.davcai.lottery.platform.client.anruizhiying.util.DavCaiPlayIdToAnRuiZhiYingParamsUtil;
import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryPlatformId;


public class AnRuiZhiYingOrderTicketClientImpl extends AbstractLotteryPlatformOrderTicketClient{
	@Autowired
	@Qualifier("anRuiZhiYingOrderTicketClientSupport")
	private AbstractAnRuiZhiYingClientSupport clientSupport;

	


	private String makeActualCodeByLotteryPlatformId(String platformId,
			Map<String, String> platformBetCode) {
		return null==platformBetCode?null:platformBetCode.get(platformId);
	}

	

	private Map<String, Object> makeParams(List<Ticket> tickets) {
		Map<String, Object> maps=new HashMap<String,Object>();
		
		//经过之前的检查，可以保证tickets列表中所有项的playId是一致且不为空的,并且期id或者均为空，或者均为同一个值
		String playId=tickets.get(0).getPlayId();
		String issue=tickets.get(0).getIssue();
		AnRuiZhiYingParams anRuiZhiYingParams=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(playId);
		if(null!=anRuiZhiYingParams){
			maps.put(ParamNames.LOTTERY_ID, anRuiZhiYingParams.getLotteryId());
			maps.put(ParamNames.WARE_ID, anRuiZhiYingParams.getWareId());
			maps.put(ParamNames.WARE_ISSUE, StringUtils.isBlank(issue)?anRuiZhiYingParams.getWareIssue():issue);
		}
		maps.put(ParamNames.BATCH_ID, makeBatchId());
		maps.put(ParamNames.ADDFLAG, StringUtils.equals(anRuiZhiYingParams.getLotteryId(), AnRuiZhiYingLotteryId.CJDLT)?"1":"0");
		maps.put(ParamNames.BET_AMOUNT, computeTotalNote(tickets));
		maps.put(ParamNames.BET_CONTENT, makeBetContent(tickets));
		maps.put(ParamNames.REAL_NAME, "张三");
		maps.put(ParamNames.ID_CARD, "4321000000");
		maps.put(ParamNames.PHONE, "13312345678");
		return maps;
	}

	private int computeTotalNote(List<Ticket> tickets) {
		int result = 0;
		if(null!=tickets&&!tickets.isEmpty()){
			for(Ticket ticket:tickets){
				result=result+ticket.getNote();
			}
		}
		return result;
	}



	private String makeBetContent(List<Ticket> tickets) {
		
		return AnRuiZhiYingBetContentUtil.makeBetContent(tickets);
	}



	private BigInteger makeBatchId() {
		return AnRuiZhiYingBatchIdUtil.getBatchId(new Date());
	}



	/**
	 * 检查所有票的彩票平台id是否为指定的值，有一个不一致，就返回false;
	 * 检查所有票的playId与期id是否一致,不一致返回false
	 * 检查所有票的playid必须不为空，否则返回false
	 * @param tickets
	 * @return
	 */
	private CheckResult check(List<Ticket> tickets) {
		CheckResult result=new CheckResult();
		boolean lotteryPlatformIdIsRight=false;
		boolean playIdAndIssueIsSame=false;
		boolean playIdNotBlank=false;
		Set<String> playIdAndIssueSet=new HashSet<String>();
		for(Ticket ticket:tickets){
			if(StringUtils.isNotBlank(getTargetLotteryPlatformId())&&StringUtils.equals(ticket.getLotteryPlatformId(), getTargetLotteryPlatformId())){
				lotteryPlatformIdIsRight=true;
				if(StringUtils.isNotBlank(ticket.getPlayId())){
					playIdNotBlank=true;
					playIdAndIssueSet.add(ticket.getPlayId()+"_"+ticket.getIssue());
				}
				else{
					playIdNotBlank=false;
					break;
				}
				
			}
			else{
				lotteryPlatformIdIsRight=false;
				break;
			}
		}
		if(playIdAndIssueSet.size()==1){
			playIdAndIssueIsSame=true;
		}
		result.setLotteryPlatformIdIsRight(lotteryPlatformIdIsRight);
		result.setPlayIdAndIssueIsSame(playIdAndIssueIsSame);
		result.setPlayIdNotBlank(playIdNotBlank);
		return result;
	}

	private String getTargetLotteryPlatformId() {
		return LotteryPlatformId.AN_RUI_ZHI_YING;
	}

	


	@Override
	public OrderTicketResponse4OneLoop orderTicketForOneLoop(
			List<Ticket> tickets) {
		AnRuiZhiYingOrderTicketResponse response=null;
		
		if(null!=tickets&&!tickets.isEmpty()){
			CheckResult checkResult = check(tickets);
			if(!checkResult.isLotteryPlatformIdIsRight()){
				response=new AnRuiZhiYingOrderTicketResponse("-9998");
				return AnRuiZhiYingResponseAdapter.toOrderTicketResponse4OneLoop(response,tickets);
			}
			if(!checkResult.isPlayIdNotBlank()){
				response=new AnRuiZhiYingOrderTicketResponse("-9996");
				return AnRuiZhiYingResponseAdapter.toOrderTicketResponse4OneLoop(response,tickets);
			}
			if(!checkResult.isPlayIdAndIssueIsSame()){
				response=new AnRuiZhiYingOrderTicketResponse("-9997");
				return AnRuiZhiYingResponseAdapter.toOrderTicketResponse4OneLoop(response,tickets);
			}
		
			Map<String,Object> params = makeParams(tickets);
			response=(AnRuiZhiYingOrderTicketResponse)clientSupport.doPostWithRetry(params);
			
		}
		else{
			response=new AnRuiZhiYingOrderTicketResponse("-9999");
		}
		return AnRuiZhiYingResponseAdapter.toOrderTicketResponse4OneLoop(response,tickets);
	}

	

	@Override
	protected int getMaxCountForOneLoop() {
		return 50;
	}

	@Override
	protected void preProcess(Ticket ticket) {
		//设置平台id和真实投注格式
		ticket.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		ticket.setActualCode(makeActualCodeByLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING,ticket.getPlatformBetCodeMap()));
		
	}

	@Override
	protected Map<String, List<Ticket>> groupTickets(List<Ticket> tickets) {
		Map<String, List<Ticket>> groups = new HashMap<String, List<Ticket>>();
		String key=null;
		for (Ticket tk : tickets) {
			String playId = tk.getPlayId();
			String issueId=tk.getIssue();
			key=playId+"_"+issueId;
			if (!groups.containsKey(key)){
				groups.put(key, new LinkedList<Ticket>());
			}
			groups.get(key).add(tk);
		}
		return groups;
	}



	public AbstractAnRuiZhiYingClientSupport getClientSupport() {
		return clientSupport;
	}



	public void setClientSupport(AbstractAnRuiZhiYingClientSupport clientSupport) {
		this.clientSupport = clientSupport;
	}



	


}
