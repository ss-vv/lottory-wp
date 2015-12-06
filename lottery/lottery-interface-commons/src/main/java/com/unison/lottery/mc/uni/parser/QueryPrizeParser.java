package com.unison.lottery.mc.uni.parser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.Constants;

/**
 * 查询ticket中奖情况的响应parser。是线程安全的。
 * @author Yang Bo
 */
public class QueryPrizeParser extends MessageParser {

	private static final long serialVersionUID = 5261952627314711854L;

	public QueryPrizeParser(){
    	setExpectedTransCode(104);
    }
	
	/**
	 * 解析“中奖查询响应消息”，会判断并设置是否最后一页。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void parseBody(Element body, ParserStatus status) throws ParseException {
		List<Element> wontickets = body.element("wontickets").elements("wonticket");
		QueryPrizeParserStatus prizeParserStatus = (QueryPrizeParserStatus) status;
		if (wontickets == null || wontickets.size() == 0) {
			prizeParserStatus.setReachLastPage(true);
			return;
		}
        Map<Long, Ticket> ts = prizeParserStatus.getTicketIdTickets();
        for (Element ticket : wontickets) {
        	String actualStatus = ticket.attributeValue("state");
            int statusCode = Integer.parseInt(actualStatus);
            int lcStatusCode = -1;
            prizeParserStatus.setPrevPlatformTicketId(ticket.attributeValue("palmId"));
            try {
				lcStatusCode = Translator.translatePrizeStatusCode(statusCode);
			} catch (TranslateException e) {
				throw new ParseException(e);
			}
            // 忽略未开奖的
            if(Constants.ZM_PRIZE_NOT_YET != statusCode){
                Ticket t = new Ticket();
                t.setId(Long.parseLong(ticket.attributeValue("ticketId")));
                t.setStatus(lcStatusCode);
                t.setActualCode(actualStatus);
                t.setPreTaxBonus(new BigDecimal(ticket.attributeValue("pretaxPrice")).setScale(2));
                t.setAfterTaxBonus(new BigDecimal(ticket.attributeValue("prize")).setScale(2));
                String isCancelGame = ticket.attributeValue("IsCancelGame");
                StringBuilder msg = new StringBuilder(Translator.getMessageOfLcPrizeStatus(lcStatusCode));
                if (isCancelGame.equals("1")){
                	msg.append("；是取消场次。");
                }
                t.setMessage(msg.toString());
                ts.put(t.getId(), t);
            }
        }
	}
}
