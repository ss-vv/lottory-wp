package com.xhcms.lottery.mc.jc.parser;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.jc.JCParser;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 测试用解析器。总是返回出票成功状态。
 * @author Yang Bo
 *
 */
public class MockJCTicketParser extends JCParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2979935892407294809L;
	
	@Autowired
    private TicketService ticketService;
	
	public int parse(String resp) {
		parseBody(null);
        return 704;
    }
	
	/**
	 * 把数据库中“已请求出票状态”的ticket取出，组合为出票响应。
	 */
	@Override
	public void parseBody(Element body) {
		List<Ticket> tickets = ticketService.listRequiredTicket();
        HashMap<Long, Ticket> ts = new HashMap<Long, Ticket>(tickets.size());
        for(Ticket t: tickets){
            t.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
            t.setNumber(""+RandomUtils.nextInt(9999999));
            t.setMessage("模拟已出票");
            ts.put(t.getId(), t);
        }
		ticketService.check(ts);
	}

}
