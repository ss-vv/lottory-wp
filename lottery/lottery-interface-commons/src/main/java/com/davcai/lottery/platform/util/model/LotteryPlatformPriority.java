package com.davcai.lottery.platform.util.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LotteryPlatformPriority {
	
	private String lotteryPlatformId;
	private String interfaceName;
	private String lotteryId;
	private int priority;
	
	private int priorityOfBigTicket;
	
	private Long id;
	
	private String lotteryPlatformAliasName;
	
	public String getLotteryPlatformAliasName() {
		return lotteryPlatformAliasName;
	}

	public void setLotteryPlatformAliasName(String lotteryPlatformAliasName) {
		this.lotteryPlatformAliasName = lotteryPlatformAliasName;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getLotteryPlatformId() {
		return lotteryPlatformId;
	}
	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriorityOfBigTicket() {
		return priorityOfBigTicket;
	}

	public void setPriorityOfBigTicket(int priorityOfBigTicket) {
		this.priorityOfBigTicket = priorityOfBigTicket;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
