package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "lt_follow_win_list")
public class FollowWinListPO implements Serializable{
	
	private static final long serialVersionUID = 2150638984457223279L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@Column(name = "follower_id")
	private long followerId; // 发起人ID
	
	private String follower;

	@Column(name = "lottery_id")
	private String lotteryId;
	
	@Column(name = "follow_scheme_count")
	private int followSchemeCount;
	
	@Column(name = "total_amount")
	private int totalAmount;
	
	@Column(nullable = false, name = "after_tax_bonus", updatable = false)
	private BigDecimal afterTaxBonus = new BigDecimal(0);;
		
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "last_count_time")
	private Date lastCountTime;
	
	@Formula(value="after_tax_bonus/total_amount")
	private int returnRate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFollowerId() {
		return followerId;
	}

	public void setFollowerId(long followerId) {
		this.followerId = followerId;
	}

	public String getFollower() {
		return follower;
	}

	public void setFollower(String follower) {
		this.follower = follower;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public int getFollowSchemeCount() {
		return followSchemeCount;
	}

	public void setFollowSchemeCount(int followSchemeCount) {
		this.followSchemeCount = followSchemeCount;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getAfterTaxBonus() {
		return afterTaxBonus;
	}

	public void setAfterTaxBonus(BigDecimal afterTaxBonus) {
		this.afterTaxBonus = afterTaxBonus;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getLastCountTime() {
		return lastCountTime;
	}

	public void setLastCountTime(Date lastCountTime) {
		this.lastCountTime = lastCountTime;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(int returnRate) {
		this.returnRate = returnRate;
	}
}
