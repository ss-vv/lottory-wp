package com.xhcms.lottery.dc.feed.web.action.result;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.dc.feed.web.action.BaseListAction;

public class AjaxListAction extends BaseListAction {
	private static final long serialVersionUID = -6089381470488108274L;
	@Autowired
	private MatchService matchService;
	private Date from;
	private Date to;
	
	@Override
	public String execute() {

		wrapPaging();
		
		if (to != null) {
			to = DateUtils.addDays(to, 1);
		}
		
		matchService.listFBMatch(paging, from, to);
		return SUCCESS;
	}
	
	public String basketball() {
	    wrapPaging();
	    if (to != null) {
			to = DateUtils.addDays(to, 1);
		}
	    matchService.listBBMatch(paging, from, to);
	    return SUCCESS;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Paging getPaging() {
		return paging;
	}
	
}
