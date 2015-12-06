package com.xhcms.lottery.pb.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "lt_partner_ticket")
public class PartnerTicketPO {
	@Column(name = "scheme_id")
	private long schemeId;
	
	@Id
	@GeneratedValue(generator = "partnerTicketTableGenerator")
	@GenericGenerator(name = "partnerTicketTableGenerator", strategy = "assigned")
	@Column(name = "ticket_id")
	private long ticketId;
	
	@Column(name = "status")
	private int status;
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "win_notice_time")
	private Date winNoticeTime;
	
	public long getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}
	public long getTicketId() {
		return ticketId;
	}
	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getWinNoticeTime() {
		return winNoticeTime;
	}
	public void setWinNoticeTime(Date winNoticeTime) {
		this.winNoticeTime = winNoticeTime;
	}
}
