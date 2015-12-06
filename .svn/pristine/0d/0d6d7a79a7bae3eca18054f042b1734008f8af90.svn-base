package com.xhcms.lottery.commons.data;

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
public class ShowWinList implements Serializable{

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
	private BigDecimal afterTaxBonus;
	
	@Column(name = "follow_count")
	private int followCount;
	
	@Column(name = "follow_total_amount")
	private int followTotalAmount;
	
	@Column(name = "follow_total_bonus")
	private BigDecimal followTotalBonus;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "last_count_time")
	private Date lastCountTime;
	
	private UserScore userScore;
	
	//回报率
	private int returnRate;
	
	//平均跟单
	private int avgFollowCount;
	
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
		if(afterTaxBonus != null) {
			return afterTaxBonus.setScale(0, BigDecimal.ROUND_HALF_UP);
		} else {
			return new BigDecimal(0);
		}
		
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
		if(followTotalBonus != null) {
			return followTotalBonus.setScale(0, BigDecimal.ROUND_HALF_UP);
		} else {
			return new BigDecimal(0);
		}
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

	public int getAvgFollowCount() {
		return getAvgFollowCount(followCount, showSchemeCount);
	}

	public void setAvgFollowCount(int avgFollowCount) {
		this.avgFollowCount = avgFollowCount;
	}

	public UserScore getUserScore() {
		return userScore;
	}

	public void setUserScore(UserScore userScore) {
		this.userScore = userScore;
	}

	public int getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(int returnRate) {
		this.returnRate = returnRate;
	}
	
	private int getAvgFollowCount(int followCount, int showSchemeCount) {
		if(followCount == 0) {
			return 0;
		}
		if(followCount > 0 && followCount < showSchemeCount) {
			return 1;
		}
		int avg = followCount/showSchemeCount;
		int avgm = followCount%showSchemeCount;
		if(avgm*2 >= showSchemeCount) {
			avg += 1;
		}
		return avg;
	}
}
