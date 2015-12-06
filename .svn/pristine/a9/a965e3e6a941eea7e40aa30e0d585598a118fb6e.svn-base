package com.unison.lottery.pm.data;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 用户赠款
 * @author yonglizhu
 *
 */
public class CountResult implements Serializable{

	private static final long serialVersionUID = -6367642633089734658L;
	
	private Long userId;

	private String mobile;
	
	private Long num;
	
	private BigDecimal amount;

	private Long registNum;
	
	private Long rechargeNum;
	
	private BigDecimal rechargeAmount;
	
	private Long betNum;
	
	private BigDecimal betAmount;
	
	public CountResult() {
		
	}
	public CountResult(String mobile, Long registNum) {
		super();
		this.mobile = mobile;
		this.registNum = registNum;
	}
	
	public CountResult(Long userId, Long num, BigDecimal amount) {
		super();
		this.userId = userId;
		this.num = num;
		this.amount = amount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getRegistNum() {
		return registNum;
	}

	public void setRegistNum(Long registNum) {
		this.registNum = registNum;
	}

	public Long getRechargeNum() {
		return rechargeNum;
	}

	public void setRechargeNum(Long rechargeNum) {
		this.rechargeNum = rechargeNum;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Long getBetNum() {
		return betNum;
	}

	public void setBetNum(Long betNum) {
		this.betNum = betNum;
	}

	public BigDecimal getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(BigDecimal betAmount) {
		this.betAmount = betAmount;
	}
	
	public Long getNum() {
		return num;
	}
	
	public void setNum(Long num) {
		this.num = num;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
