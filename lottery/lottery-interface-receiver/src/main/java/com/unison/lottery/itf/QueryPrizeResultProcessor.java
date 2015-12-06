package com.unison.lottery.itf;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.mc.uni.parser.ParseException;
import com.unison.lottery.mc.uni.parser.ParserStatus;

import com.unison.lottery.mc.uni.parser.QueryPrizeParserStatus;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 中奖结果处理器。<br/>
 * 需要用spring的method inject方法配置 object factory method 'generateProcessResult'.
 * @author Yang Bo
 */
public  class QueryPrizeResultProcessor extends NotifyWithoutResult4ZMProcesser {
	
	
	@Autowired
	private TicketService ticketService;

	@Override
	protected ParserStatus generateParserStatus() {
		return new QueryPrizeParserStatus() ;
	}

	/**
	 * 解析异常时，生成一个处理结果对象，表示处理失败。
	 */
	@Override
	protected QueryPrizeProcessResult generateErrorResult(ParseException error) {
		return new QueryPrizeProcessResult();
	}

	@Override
	protected QueryPrizeProcessResult process(int parseResult, ParserStatus status) {
		QueryPrizeProcessResult returnResults = (QueryPrizeProcessResult) generateProcessResult();
		QueryPrizeParserStatus orderStatus = (QueryPrizeParserStatus) status;
		Map<Long, Ticket> ticketIdObjMap = orderStatus.getTicketIdTickets();

		if (ticketIdObjMap.size() > 0) {
			logger.debug("To update prize status for tickets: {}", ticketIdObjMap);
            ticketService.prize(ticketIdObjMap);
            
        }
		return returnResults;
	}

	@Override
	protected ProcessResult<?> generateProcessResult() {
		return new QueryPrizeProcessResult();
	}

}
