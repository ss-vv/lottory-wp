package com.xhcms.lottery.admin.web.action.ticket;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.service.AwardService;

public class FbListAction extends BaseListAction {

	private static final long serialVersionUID = -1153041565319927804L;
	
	@Autowired
	private AwardService awardService;

	private String playId;
	private Date createdTime;
	
	private List<Ticket> tickets;
	
	public String notawardlist() {
		if(null == playId) {
			playId = "05_ZC_2";
		}
		if(null == createdTime) {
			createdTime = new Date();
		}
		
		tickets = awardService.findByCreatTime(playId, createdTime, false);
		
		return SUCCESS;
	}

	public String awardedlist() {
		if(null == playId) {
			playId = "05_ZC_2";
		}
		if(null == createdTime) {
			createdTime = new Date();
		}
		
		tickets = awardService.findByCreatTime(playId, createdTime, true);
		
		return SUCCESS;
	}


	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	
}
