package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "lottery_platform_priority")
public class LotteryPlatformPriorityPO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3022102992735430685L;
	@Id
	private Long id;
	@Column(name="lottery_platform_id")
	private String lotteryPlatformId;
	@Column(name="interface_name")
	private String interfaceName;
	@Column(name="lottery_id")
	private String lotteryId;
	private Integer priority;
	@Column(name="priority_of_big_ticket")
	private Integer priorityOfBigTicket;
	@Column(name="lottery_platform_alias_name",nullable=true)
	private String lotteryPlatformAliasName;
	public String getLotteryPlatformAliasName() {
		return lotteryPlatformAliasName;
	}
	public void setLotteryPlatformAliasName(String lotteryPlatformAliasName) {
		this.lotteryPlatformAliasName = lotteryPlatformAliasName;
	}
	public Integer getPriorityOfBigTicket() {
		return priorityOfBigTicket;
	}
	public void setPriorityOfBigTicket(Integer priorityOfBigTicket) {
		this.priorityOfBigTicket = priorityOfBigTicket;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
