package com.xhcms.lottery.mc.jc.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.mc.jc.JCParser;
import com.xhcms.lottery.mc.persist.service.TicketService;

public class JCBuyParser extends JCParser {

    private static final long serialVersionUID = -6208429970686779119L;

    @Autowired
    private TicketService ticketService;
    
    @SuppressWarnings("unchecked")
    @Override
    public void parseBody(Element body) {
        List<Element> tickets = body.element("ticketorder").element("tickets").elements("ticket");
        Map<Integer, List<Long>> map = new HashMap<Integer, List<Long>>();
        
        for(Element t: tickets){
            try{
                Integer status = new Integer(t.attributeValue("statuscode"));
                List<Long> list = map.get(status);
                if(list == null){
                    list = new ArrayList<Long>();
                    map.put(status, list);
                }
                list.add(new Long(t.attributeValue("id")));
            }catch(NumberFormatException e){
            }
        }
        
        ticketService.updateTicketStatus(map);
    }

}
