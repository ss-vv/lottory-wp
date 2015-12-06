package com.xhcms.lottery.pb.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

public class PartnerBetModel {
	private String uuid;
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartnerBetModel other = (PartnerBetModel) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	private String partnerUserId;
	
	private Long partnerId;
	
	private Long schemeId;
	
	private Integer status;
	
	private Date createTime;
	
	private String winNoticeTime;

	private int schemeStatus;
	
	private List<PartnerTicket> tickets;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPartnerUserId() {
		return partnerUserId;
	}

	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWinNoticeTime() {
		return winNoticeTime;
	}

	public void setWinNoticeTime(String winNoticeTime) {
		this.winNoticeTime = winNoticeTime;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	public int getSchemeStatus() {
		return schemeStatus;
	}
	public void setSchemeStatus(int schemeStatus) {
		this.schemeStatus = schemeStatus;
	}
	public List<PartnerTicket> getTickets() {
		return tickets;
	}
	public void setTickets(List<PartnerTicket> tickets) {
		this.tickets = tickets;
	}
}
