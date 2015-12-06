package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "lt_user_score")
public class UserScorePO implements Serializable{

	private static final long serialVersionUID = 9013791555851858747L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@Column(name = "user_id")
	private long userId; // 用户ID
	
	private String username;

	@Column(name = "lottery_id")
	private String lotteryId;
	
	@Column(name = "show_score")
	private long showScore;
	
	@Column(name = "group_score")
	private long groupScore;
	
	@Column(name = "group_failure_score")
	private long groupFailureScore;
	
	@Column(name = "total_score")
	private long totalScore;
	
	@Column(name = "win_amount")
	private BigDecimal winAmount;
	
	public UserScorePO() {
		winAmount = new BigDecimal(0);
	}
	
	public BigDecimal getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(BigDecimal winAmount) {
		this.winAmount = winAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public long getShowScore() {
		return showScore;
	}

	public void setShowScore(long showScore) {
		this.showScore = showScore;
	}

	public long getGroupScore() {
		return groupScore;
	}

	public void setGroupScore(long groupScore) {
		this.groupScore = groupScore;
	}

	public long getGroupFailureScore() {
		return groupFailureScore;
	}

	public void setGroupFailureScore(long groupFailureScore) {
		this.groupFailureScore = groupFailureScore;
	}

	public long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
