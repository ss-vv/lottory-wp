package com.xhcms.lottery.admin.web.action.scheme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;

public class ListTicketAction extends BaseAction {

    private static final long serialVersionUID = 128142404801446862L;
    
    @Autowired
    private BetSchemeService betSchemeService;
    
    private List<Ticket> tickets;
    private Long id;
    private String playId;
    private String lotteryId;
    
    @Override
    public String execute(){
    	BetScheme betScheme = betSchemeService.getScheme(id);
    	lotteryId = betScheme.getLotteryId();
        tickets = betSchemeService.listTicket(id);
        return SUCCESS;
    }
    
    public String lsticket(){
    	BetScheme betScheme = betSchemeService.getPresetScheme(id);
    	lotteryId = betScheme.getLotteryId();
        tickets = betSchemeService.listPresetTicket(id);
        return SUCCESS;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
}
