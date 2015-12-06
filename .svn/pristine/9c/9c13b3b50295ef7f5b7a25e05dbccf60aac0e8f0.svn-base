package com.xhcms.lottery.dc.feed.web.action.matchplay;

import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.Action;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.persist.service.MatchColorService;

public class AjaxColorListAction implements Action{
	@Autowired
	private MatchColorService matchColorService;
	private Data data;
	private String playId;
	
	@Override
	public String execute() {
		data = Data.success(matchColorService.findMatchColorOnSale(playId));
		return SUCCESS;
	}
	
	public Data getData() {
        return data;
    }

    public void setPlayId(String playId) {
		this.playId = playId;
	}
	
}
