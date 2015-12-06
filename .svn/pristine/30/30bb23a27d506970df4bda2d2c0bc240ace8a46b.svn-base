package com.xhcms.lottery.admin.web.action.scheme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 手动设置状态为“未出票”的票为“成功”状态，并且正确设置方案的成功出票注数
 * @author li lei
 * @version 1.0.0
 */
public class AjaxSetTicketSuccessAction extends BaseAction {

    private static final long serialVersionUID = 5139362559096601347L;

    @Autowired
    private BetSchemeService betSchemeService;
    @Autowired
    private TicketService ticketService;

    private long ticketId;
    private List<Long> alreadyExpoetTicketIds;
    
    private Data data = Data.success(getText("message.success"));
    
    public String batch() {
    	boolean result = false;
        try {
			result = ticketService.physicalStoreTickeByBatch(alreadyExpoetTicketIds);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
        if(!result) {
        	data = Data.failure("出票失败！");
        }
    	return SUCCESS;
    }
    
    @Override
    public String execute() {
    	boolean result = false;
        try {
			result = ticketService.physicalStoreTicket(ticketId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(!result) {
        	data = Data.failure("出票失败！");
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