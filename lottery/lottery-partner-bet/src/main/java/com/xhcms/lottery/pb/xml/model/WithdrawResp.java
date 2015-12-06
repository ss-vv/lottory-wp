package com.xhcms.lottery.pb.xml.model;

import javax.xml.bind.annotation.XmlAttribute;

public class WithdrawResp {
	private String transactionId;
	private int status;
	
	public String getTransactionId() {
		return transactionId;
	}
	@XmlAttribute
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public int getStatus() {
		return status;
	}
	@XmlAttribute
	public void setStatus(int status) {
		this.status = status;
	}
}
