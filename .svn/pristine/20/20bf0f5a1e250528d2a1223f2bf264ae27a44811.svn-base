package com.davcai.lottery.platform.client.zunao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.AbstractQueryPrizeClient;
import com.davcai.lottery.platform.client.ILotteryPlatformQueryPrizeClient;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.unison.lottery.mc.uni.client.QueryPrizeClient;
import com.unison.lottery.mc.uni.parser.QueryPrizeParserStatus;
import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.commons.data.Ticket;

public class ZunAoQueryPrizeClientImpl extends AbstractQueryPrizeClient {
	@Autowired
	private QueryPrizeClient client;

	@Override
	protected int getMaxCountForOneLoop() {
		return 1;
	}

	@Override
	public QueryPrizeResponse4OneLoop queryPrizeForOneLoop(List<Ticket> tickets) {
		QueryPrizeResponse4OneLoop result=null;
		if(null!=tickets&&!tickets.isEmpty()&&tickets.size()==1){//目前每一轮只处理一张票
			result=new QueryPrizeResponse4OneLoop();
			Ticket ticket=tickets.get(0);
			String zmLotteryId;
			QueryPrizeParserStatus parserStatuses = new QueryPrizeParserStatus();
			try {
				zmLotteryId = Translator.lcPlayIdToZMLotteryId(ticket.getPlayId());
				String issue = getIssueDate(ticket);
				boolean postSucc=client.postWithStatus(zmLotteryId, issue, parserStatuses);
				if(postSucc){
					Map<Long, Ticket> ticketsMap=makeTicketsMap(parserStatuses);
					result.setTicketsMap(ticketsMap);
				}
			} catch (TranslateException e) {
				logger.error("Can not translate lcPlayId to zmLotteryId. Continue to query next issue.", e);
			}
		}
		return result;
	}
	
	private Map<Long, Ticket> makeTicketsMap(
			QueryPrizeParserStatus parserStatuses) {
		return parserStatuses.getTicketIdTickets();
	}

	/**
	 * 获取正确的“期号”。对于竞彩是ticket的创建日期。
	 * @param ticket 投注的彩票
	 * @return 期号
	 */
	public String getIssueDate(Ticket ticket) {
		SimpleDateFormat issueDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isEmpty(ticket.getIssue())) {
			return issueDateFormatter.format(ticket.getCreatedTime());
		}
		return ticket.getIssue();
	}

	public QueryPrizeClient getClient() {
		return client;
	}

	public void setClient(QueryPrizeClient client) {
		this.client = client;
	}


}
