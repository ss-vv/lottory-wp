package com.unison.lottery.pm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 用户赠款
 * @author yonglizhu
 *
 */
public class RechargeResult implements Serializable{

	private static final long serialVersionUID = -4094070272452559060L;
	
	private Long userId;

	private BigDecimal amount;
	
	private int grantAmount;
	
	private String tradeNo;

	private Date payTime;

	public RechargeResult(){
		
	}
	
	public RechargeResult(Long userId, String tradeNo, int grantAmount) {
		super();
		this.userId = userId;
		this.tradeNo = tradeNo;
		this.grantAmount = grantAmount;
	}
	
	public RechargeResult(Long userId, BigDecimal amount, Date payTime) {
		super();
		this.userId = userId;
		this.amount = amount;
		this.payTime = payTime;
	}
	
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public int getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(int grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
