package com.xhcms.lottery.admin.web.action.ticket;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.service.AwardService;

public class FbAwardAction extends BaseListAction {

	private static final long serialVersionUID = -1153041565319927804L;
	
	@Autowired
	private AwardService awardService;

	private List<Long> ids;
	
	public String award() {		
		
		
		return SUCCESS;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	
}
