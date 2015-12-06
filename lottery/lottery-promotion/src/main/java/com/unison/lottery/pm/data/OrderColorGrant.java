package com.unison.lottery.pm.data;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class OrderColorGrant {

	private String mobile;

	private Long userId;

	private int grantAmount;

	private String tradeNo;

	private String pid;
	
	private Date orderTime;

	public OrderColorGrant() {

	}

	public OrderColorGrant(String mobile, Long userId, String tradeNo,
			int grantAmount,
			String pid, Date orderTime) {
		this.mobile = mobile;
		this.userId = userId;
		this.grantAmount = grantAmount;
		this.tradeNo = tradeNo;
		this.pid = pid;
		this.orderTime = orderTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(int grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
