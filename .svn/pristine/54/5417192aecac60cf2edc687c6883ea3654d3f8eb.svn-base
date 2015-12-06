package com.unison.lottery.mc.uni.parser;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryIdForZM;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 交易结果查询请求的响应消息解析器。
 * 
 * @author Yang Bo
 */
public class QueryOrderResultParser extends MessageParser {

	private static final long serialVersionUID = -9122275194328086309L;

	private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TicketService ticketService;
    
    public QueryOrderResultParser(){
    	setExpectedTransCode(103);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void parseBody(Element body, ParserStatus status) throws ParseException {
        List<Element> es = body.elements("ticketresult");
        if (es.size()==0){
        	es = body.element("ticketresults").elements("ticketresult");
        }
        QueryOrderResultStatus orderResultStatus = (QueryOrderResultStatus) status;
        if (es != null) {
            Map<Long, Ticket> ticketIdObjMap = orderResultStatus.getTickets();
            for (Element ticketElement : es) {
                int actualStatus = Integer.parseInt(ticketElement.attributeValue("statusCode"), 10);
                int lcStatus = -1;
                String actualOdds = ticketElement.attributeValue("printodd");
                String lcOdds = StringUtils.EMPTY;
                String zmLotteryId = ticketElement.attributeValue("lotteryId");
                //String lcShortPlayId = StringUtils.EMPTY;
                if ( actualStatus == Constants.ZM_TICKET_WAIT ||
                	 actualStatus == Constants.ZM_TICKET_IN_TRANSACTION ) {
                    continue;
                }
                try{
                	Ticket ticket = new Ticket();
                	String ticketIdStr = ticketElement.attributeValue("ticketId");
                	ticket.setId(Long.parseLong(ticketIdStr));
                	log.debug("获取到交易结果ticketId={}", ticketIdStr);
                	
                	lcStatus = Translator.translateOrderResultStatusToLCFormat(actualStatus);
                	
                	ticket.setStatus(lcStatus);
                	ticket.setActualStatus(actualStatus);
                	
                	if (actualStatus == Constants.ZM_TICKET_NOT_EXIST){
                		logger.error("Ticket {} is not exist, answered by ZM! Will not update ticketPO.", ticket.getId());
                		continue; // 票不存在时，很多字段都是空的。
                	}
                	if (StringUtils.isNotBlank(actualOdds)){
                		lcOdds = translateOddsToLCFormat(actualOdds, ticket.getId());
                	}
                	ticket.setOdds(lcOdds);
                	ticket.setActualOdds(actualOdds);
                	
                	ticket.setNumber(ticketElement.attributeValue("printNo"));
                	ticket.setMessage(ticketElement.attributeValue("message"));
                	// 借用 playId 属性，其实是 short play id
               		//lcShortPlayId = Translator.zmLotteryIdToLcShortPlayId(zmLotteryId);
                	//ticket.setPlayId(lcShortPlayId);
                	
                	//对于“猜冠军玩法”的交易结果，不做lotteryID校验：
                	/**
                	 * lotteryId,主动通知出票结果的没有区分，只分冠军和亚冠玩法。
                	 * lotteryId,投注接口就需要区分欧冠和世界杯
                	 */
                	if(!zmLotteryId.equals(PlayType.JCGJ) && 
                			!zmLotteryId.equals(PlayType.JCYJ)) {
                		ticket.setLotteryId(LotteryIdForZM.valueOfName(zmLotteryId).getLcLotteryId());
                	}
                	ticket.setPlatformId(Long.valueOf(ticketElement.attributeValue("palmId")));
                	ticketIdObjMap.put(ticket.getId(), ticket);
                }catch(TranslateException te){
                	throw new ParseException(te);
                }
            }
            if(log.isDebugEnabled()){
                log.debug("Query ticket order result size: " + ticketIdObjMap.size());
            }
        }
    }

	private String translateOddsToLCFormat(String actualOdds, Long ticketId) throws TranslateException {
		log.debug("通过票Id查询票信息：ticketId={}", ticketId);
		Ticket ticket = ticketService.getTicket(ticketId);
		String lcBetContent = ticket.getCode();
		String lcPlayId = ticket.getPlayId();
		return Translator.translateOddsToLCFormat(actualOdds, lcBetContent, lcPlayId);
	}

}
