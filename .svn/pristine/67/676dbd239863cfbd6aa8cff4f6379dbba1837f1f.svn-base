package com.xhcms.lottery.mc.jc.parser;

import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.jc.JCParser;
import com.xhcms.lottery.mc.persist.service.TicketService;

public class JCTicketParser extends JCParser {
    private static final long serialVersionUID = -5320184216864814215L;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TicketService ticketService;

    @SuppressWarnings("unchecked")
    @Override
    public void parseBody(Element body) {
        if(log.isDebugEnabled()){
            log.debug("start parse ticket status");
        }
        
        List<Element> es = body.elements("ticket");
        if (es != null) {
            if(log.isDebugEnabled()){
                log.debug(body.asXML());
            }
            HashMap<Long, Ticket> ts = new HashMap<Long, Ticket>(es.size());
            for (Element e : es) {
                int status = Integer.parseInt(e.attributeValue("status"));
                if(status == EntityStatus.TICKET_BUYING || status == EntityStatus.TICKET_NOT_EXIST){
                    continue;
                }

                Ticket t = new Ticket();
                t.setId(Long.parseLong(e.attributeValue("id")));
                t.setStatus(status);
                t.setNumber(e.attributeValue("ticketnum"));
                t.setOdds(e.attributeValue("odds"));
                t.setMessage(e.attributeValue("message"));
                t.setMoney(Integer.parseInt(e.attributeValue("money")));
                t.setMultiple(Integer.parseInt(e.attributeValue("multiple")));
                t.setPlayId(e.attributeValue("play"));
                
                ts.put(t.getId(), t);
            }
            
            if(log.isDebugEnabled()){
                log.debug("checks " + ts.size());
            }
            
            if (ts.size() > 0) {
                ticketService.check(ts);
            }
        }
    }

}
