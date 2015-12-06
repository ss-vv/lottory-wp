package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.Date;

public class ColorRingGrant implements Serializable {

	private static final long serialVersionUID = 1234135882511410066L;

	private Long id;

	private String tradeNo;

	private String mobile;

	private int grantAmount;

	private Date orderTime;

	private Date createdTime;

	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(int grantAmount) {
		this.grantAmount = grantAmount;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
