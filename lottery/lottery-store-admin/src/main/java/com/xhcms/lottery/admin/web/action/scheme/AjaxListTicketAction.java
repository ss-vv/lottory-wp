package com.xhcms.lottery.admin.web.action.scheme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.Ticket;

public class AjaxListTicketAction extends BaseListAction {
	private static final long serialVersionUID = -316401962307986984L;
	@Autowired
	private BetSchemeService betSchemeService;
	private List<Ticket> tickets;
	private long schemeId;
	
	@Override
	public String execute() {
		tickets = betSchemeService.listTicket(schemeId);
		return SUCCESS;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}
	
}
