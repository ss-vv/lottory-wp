package com.xhcms.lottery.mc.jc.parser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.jc.JCParser;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 测试用解析器。总是返回出票成功状态。
 * @author Yang Bo
 *
 */
public class MockJCBonusParser extends JCParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2979935892407294809L;
	
	@Autowired
    private TicketService ticketService;
	
	public int parse(String resp) {
		parseBody(null);
        return 706;
    }
	
	/**
	 * 把数据库中“出票成功状态”的ticket取出，组合为中奖。
	 */
	@Override
	public void parseBody(Element body) {
		List<Ticket> tickets = ticketService.listBuySuccessTickets();
        HashMap<Long, Ticket> ts = new HashMap<Long, Ticket>(tickets.size());
        for(Ticket t: tickets){
            t.setStatus(EntityStatus.TICKET_NOT_AWARD);
            BigDecimal bonus = expectMaxBonusOfTicket(t.getId());
            t.setPreTaxBonus(bonus);
            t.setAfterTaxBonus(bonus);
            t.setMessage("模拟中奖未派奖");

            ts.put(t.getId(), t);
        }
        if(ts.size() > 0){
            ticketService.prize(ts);
        }
	}

	private BigDecimal expectMaxBonusOfTicket(Long id) {
		BetScheme scheme = ticketService.getBetSchemeOfTicket(id);
		BigDecimal ticketBonus = scheme.getMaxBonus();
		if (ticketBonus == null){
			return BigDecimal.ZERO;
		}
		ticketBonus = ticketBonus.divide(new BigDecimal(scheme.getTicketCount()), BigDecimal.ROUND_HALF_EVEN);
		return ticketBonus;
	}

}
