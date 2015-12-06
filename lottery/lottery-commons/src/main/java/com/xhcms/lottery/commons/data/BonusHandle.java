package com.xhcms.lottery.commons.data;

import java.math.BigDecimal;

import com.xhcms.commons.mq.XHMessage;

public class BonusHandle implements XHMessage{
	
	private static final long serialVersionUID = 822438179325785110L;

	private int priority;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/** 操作金额 */
	private BigDecimal amount;
	
	/** 0 派奖 1注入 */
	private int amountType;
	
	/** 中奖纪录id */
	private Long winnersRecordId;
	
	public BonusHandle(){
		
	}
	
	public BonusHandle(BigDecimal amount , int amountType){
		this.amount = amount;
		this.amountType = amountType;
	}
	
	public BonusHandle(Long winnersRecordId, int amountType){
		this.winnersRecordId = winnersRecordId;
		this.amountType = amountType;
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


	public Long getWinnersRecordId() {
		return winnersRecordId;
	}

	public void setWinnersRecordId(Long winnersRecordId) {
		this.winnersRecordId = winnersRecordId;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public String getType() {
		 return getClass().getSimpleName();
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getAmountType() {
		return amountType;
	}

	public void setAmountType(int amountType) {
		this.amountType = amountType;
	}

}
