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

@Entity
@Table(name = "lt_show_win_list")
public class ShowWinListPO implements Serializable{

	private static final long serialVersionUID = -1002026003995146853L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@Column(name = "sponsor_id")
	private long sponsorId; // 发起人ID
	
	private String sponsor;

	@Column(name = "lottery_id")
	private String lotteryId;
	
	@Column(name = "show_scheme_count")
	private int showSchemeCount;
	
	@Column(name = "total_amount")
	private int totalAmount;
	
	@Column(name = "after_tax_bonus")
	private BigDecimal afterTaxBonus = new BigDecimal(0);
	
	@Column(name = "follow_count")
	private int followCount;
	
	@Column(name = "follow_total_amount")
	private int followTotalAmount;
	
	@Column(name = "follow_total_bonus")
	private BigDecimal followTotalBonus = new BigDecimal(0);
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "last_count_time")
	private Date lastCountTime;
	
	@Column(name = "return_rate")
	private int returnRate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public int getShowSchemeCount() {
		return showSchemeCount;
	}

	public void setShowSchemeCount(int showSchemeCount) {
		this.showSchemeCount = showSchemeCount;
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

	public int getFollowCount() {
		return followCount;
	}

	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}

	public int getFollowTotalAmount() {
		return followTotalAmount;
	}

	public void setFollowTotalAmount(int followTotalAmount) {
		this.followTotalAmount = followTotalAmount;
	}

	public BigDecimal getFollowTotalBonus() {
		return followTotalBonus;
	}

	public void setFollowTotalBonus(BigDecimal followTotalBonus) {
		this.followTotalBonus = followTotalBonus;
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
