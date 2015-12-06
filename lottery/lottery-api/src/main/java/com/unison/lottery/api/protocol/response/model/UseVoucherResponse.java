package com.unison.lottery.api.protocol.response.model;

import java.math.BigDecimal;

/**
 * 使用彩金优惠劵
 * @author Wang Lei
 *
 */
public class UseVoucherResponse extends HaveReturnStatusResponse {

	/**
	 * 介绍,消息
	 */
	private String desc;
	
	/**
	 * 可用金额
	 */
	private BigDecimal free;
	
	/**
	 * 现金余额
	 */
	private BigDecimal fund;
	
	/**
	 * 赠款余额
	 */
	private BigDecimal grant;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigDecimal getFree() {
		return free;
	}

	public void setFree(BigDecimal free) {
		this.free = free;
	}

	public BigDecimal getFund() {
		return fund;
	}

	public void setFund(BigDecimal fund) {
		this.fund = fund;
	}

	public BigDecimal getGrant() {
		return grant;
	}

	public void setGrant(BigDecimal grant) {
		this.grant = grant;
	}
}
