package com.unison.lottery.itf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.itf.parser.INotifyParser;
import com.unison.lottery.mc.uni.parser.ParseException;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.mc.persist.service.TicketService;

public class AnRuiOrderNotifyProcessor extends NotifyBaseProcessor{

	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected INotifyParser notifyParser;
	
	public INotifyParser getNotifyParser() {
		return notifyParser;
	}

	public void setNotifyParser(INotifyParser notifyParser) {
		this.notifyParser = notifyParser;
	}

	@Autowired
	private TicketService ticketService;
	
	@Override
	public Map<String, ?> process(String content) {
		if (StringUtils.isBlank(content)){
			throw new IllegalArgumentException("msg is empty!");
		}
		Map<String, ?> result = null;
		try {
			result =  notifyParser.parse(content);
			
			if(result.get("tickets")!=null){
				processParserResult(result);
			}
			
			logger.debug("Parse Message success, status is: {}", result);
			
		} catch (ParseException e) {
			logger.error("Parse message failed!", e);
		}
		return result;
	}
	
	protected void processParserResult(Map<String, ?> parseResult) {
		List<Ticket> tickets = (List<Ticket>) parseResult.get("tickets");
		Map<Long, Ticket> ticketIdObjMap = new HashMap<Long,Ticket>();
		for (Ticket ticket : tickets) {
			ticketIdObjMap.put(ticket.getId(), ticket);
		}
		if (ticketIdObjMap.size() > 0) {
			logger.debug("To update order result for tickets: {}", ticketIdObjMap);
            ticketService.check(ticketIdObjMap);
        }
	}	
	
}
