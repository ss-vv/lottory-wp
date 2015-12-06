package com.xhcms.lottery.admin.web.action.scheme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 对状态为“未出票”的票进行撤单操作，并且正确设置方案的失败出票注数
 * @author li lei
 * @version 1.0.0
 */
public class AjaxSetTicketCancelAction extends BaseAction {

    private static final long serialVersionUID = 5139362559096601347L;

    @Autowired
    private BetSchemeService betSchemeService;
    @Autowired
    private TicketService ticketService;

    private long ticketId;
    private Data data = Data.success(getText("message.success"));
    
    private List<Long> alreadyExpoetTicketIds;
    
    public String batch() throws Exception {
    	ticketService.cancelStoreTicket(alreadyExpoetTicketIds);
    	return SUCCESS;
    }
    
    @Override
    public String execute() throws Exception {
    	try {
			Ticket t = ticketService.getTicket(ticketId);
			if(null == t || t.getId() <= 0) {
				return SUCCESS;
			} else {
				if (t.getStatus() != EntityStatus.TICKET_READY_FOR_HANDWORK && t.getStatus() != EntityStatus.TICKET_EXPORTED){
			    	data = Data.failure("方案状态不是'等待人工处理出票'或'已导出文件'状态，不能执行本操作。");
			    	return SUCCESS;
			    }
			}
			ticketService.cancelStoreTicket(ticketId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return SUCCESS;
    }

    public Data getData() {
        return data;
    }

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public void setAlreadyExpoetTicketIds(List<Long> alreadyExpoetTicketIds) {
		this.alreadyExpoetTicketIds = alreadyExpoetTicketIds;
	}
}