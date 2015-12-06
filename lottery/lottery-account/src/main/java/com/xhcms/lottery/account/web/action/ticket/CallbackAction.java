package com.xhcms.lottery.account.web.action.ticket;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.account.service.TicketService;
import com.xhcms.lottery.account.web.WebContext;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.EntityStatus;

public class CallbackAction implements Action, ServletRequestAware {

    private static final Logger log = LoggerFactory.getLogger(CallbackAction.class);

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private WebContext webContext;

    private HttpServletRequest request;
    private Set<Long> tickets;
    private String time;
    
    @Override
    public String execute() {
        String xml = readXml();
        if(xml != null){
            String verifyCode = xml.substring(0, 32);
            String text = xml.substring(32);
            if(verifyCode.equalsIgnoreCase(Text.MD5Encode(text + webContext.getTicketKey()))){
                HashMap<Long, Ticket> ts = parseXml(text);
                if (ts != null && ts.size() > 0) {
                    ticketService.check(ts);
                    
                    tickets = ts.keySet();
                    time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    return Action.SUCCESS;
                }
            }else{
                log.warn("invalid xml");
            }
        }
        
        return Action.NONE;
    }
    
    
    private String readXml(){
        StringBuilder buf = new StringBuilder(256);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            char[] ch = new char[128];
            int len = 0;
            while((len = reader.read(ch)) != -1){
                buf.append(ch, 0, len);
            }
            return buf.toString();
        } catch (IOException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
        }
        
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private HashMap<Long, Ticket> parseXml(String xml){
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");

        try {
            Document doc = reader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
            Element root = doc.getRootElement();

            List<Element> es = root.element("body").elements("ticket");
            if (es != null) {
                HashMap<Long, Ticket> ts = new HashMap<Long, Ticket>(es.size());
                for (Element e : es) {
                    int status = Integer.parseInt(e.attributeValue("status"));
                    if(status == EntityStatus.TICKET_BUYING){
                        continue;
                    }

                    Ticket t = new Ticket();
                    t.setId(Long.parseLong(e.attributeValue("id")));
                    t.setStatus(status);
                    t.setNumber(e.attributeValue("ticketnum"));
                    t.setMessage(e.attributeValue("message"));
                    t.setMoney(Integer.parseInt(e.attributeValue("money")));
                    t.setMultiple(Integer.parseInt(e.attributeValue("multiple")));
                    t.setPlayId(e.attributeValue("play"));
                    t.setCode(e.getText());
                    t.setOdds(e.attributeValue("odds"));
                    
                    ts.put(t.getId(), t);
                }
                return ts;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        
        return null;
    }

    public Set<Long> getTickets() {
        return tickets;
    }

    public String getTime() {
        return time;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

}
