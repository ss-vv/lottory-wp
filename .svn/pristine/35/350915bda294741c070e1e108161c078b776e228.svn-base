package com.xhcms.lottery.mc.jc.parser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.jc.JCParser;
import com.xhcms.lottery.mc.persist.service.TicketService;

public class JCBonusParser extends JCParser {

    private static final long serialVersionUID = -6387160854625380912L;

    @Autowired
    private TicketService ticketService;
    
    @SuppressWarnings("unchecked")
    @Override
    public void parseBody(Element body) {
        List<Element> es = body.elements("ticket");
        if (es != null) {
            HashMap<Long, Ticket> ts = new HashMap<Long, Ticket>(es.size());
            for (Element e : es) {
                int status = Integer.parseInt(e.attributeValue("code"));
                // 忽略未开奖的
                if(EntityStatus.TICKET_NOT_PRIZE != status){
                    Ticket t = new Ticket();
                    t.setId(Long.parseLong(e.attributeValue("id")));
                    t.setStatus(status);
                    t.setPreTaxBonus(new BigDecimal(e.attributeValue("pretaxmoney")).setScale(2));
                    t.setAfterTaxBonus(new BigDecimal(e.attributeValue("awardmoney")).setScale(2));
                    t.setCode(e.attributeValue("ticketcode"));
                    t.setMessage(e.attributeValue("message"));
                    
                    ts.put(t.getId(), t);
                }
            }
            if(ts.size() > 0){
                ticketService.prize(ts);
            }
        }
    }

}
