package com.xhcms.lottery.pb.xml.model;

import javax.xml.bind.annotation.XmlAttribute;

public class TicketResp {
	private long id;
	private int status;
	public long getId() {
		return id;
	}
	@XmlAttribute(name="id")
	public void setId(long id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	@XmlAttribute(name="status")
	public void setStatus(int status) {
		this.status = status;
	}
}
