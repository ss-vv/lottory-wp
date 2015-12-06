package com.xhcms.lottery.admin.web.action.scheme;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 强制方案中未出票的为“失败”状态，并且正确设置方案的成功出票注数、失败注数。
 * @author Yang Bo
 * @version 1.0.0
 */
public class AjaxSetTicketFailAction extends BaseAction {

    private static final long serialVersionUID = 5139362559096601347L;

    @Autowired
    private BetSchemeService betSchemeService;
    @Autowired
    private TicketService ticketService;

    private long sid;

    private Data data = Data.success(getText("message.success"));

    @Override
    public String execute() throws Exception {
        BetScheme scheme = betSchemeService.getScheme(sid);
        int status = scheme.getStatus();
        if (status != EntityStatus.TICKET_REQUIRED){
        	data = Data.failure("方案状态不是'已出票'，不能执行本操作。");
        	return super.execute();
        }
        List<Ticket> tickets = betSchemeService.listTicket(sid);
        Map<Long, Ticket> ticketMap = new HashMap<Long,Ticket>();
        List<Ticket> buySuccessTickets = new LinkedList<Ticket>();
        for (Ticket ticket : tickets) {
        	int ticketStatus = ticket.getStatus();
        	if (ticketStatus == EntityStatus.TICKET_INIT) {
        		ticket.setStatus(EntityStatus.TICKET_REQUIRED);
        		ticket.setPlayId(ticket.getPlayId());
        		ticket.setLotteryId(PlayType.valueOfLcPlayId(ticket.getPlayId()).getLotteryId());
        		ticketMap.put(ticket.getId(), ticket);
        	}
        	// 对已经开奖的票，要设置状态为出票成功，重新获取中奖信息
        	if (ticketStatus == EntityStatus.TICKET_NOT_WIN ||
        			ticketStatus == EntityStatus.TICKET_NOT_AWARD) {
        		ticket.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
        		buySuccessTickets.add(ticket);
        	}
        }
        ticketService.saveTicketStatus(ticketMap.values());
        ticketService.saveTicketStatus(buySuccessTickets);
        for (Ticket ticket : ticketMap.values()) {
        	ticket.setStatus(EntityStatus.TICKET_BUY_FAIL);
        }
        ticketService.check(ticketMap);
        return super.execute();
    }

    public Data getData() {
        return data;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

}
