package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserScore implements Serializable {

	private static final long serialVersionUID = -6718195350719763626L;
	
	//id
	private long id;
	// 用户id
	private long userId;
	// 用户名
	private String username;
	//
	private String lotteryId;	
	// 发起人晒单战绩
	private long showScore;
	// 发起人合买战绩
	private long groupScore;
	// 发起人合买流标战绩
	private long groupFailureScore;
	//总战绩
	private long totalScore;
	//晒单战绩图片
	private String showPic;	
	//合买战绩图片
	private String groupPic;
	//合买流标战绩图片
	private String groupFailurePic;
	// 中奖金额
	private BigDecimal winAmount;

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public UserScore() {

	}

	public UserScore(long id,long userId, String username, long showScore,
			long groupScore, long groupFailureScore,long totalScore,String showPic,String groupPic,String groupFailurePic,BigDecimal winAmount) {
		this.id = id;
		this.userId = userId;
		this.username = username;
		this.showScore = showScore;
		this.groupScore = groupScore;
		this.groupFailureScore = groupFailureScore;
		this.totalScore = totalScore;
		this.showPic = showPic;
		this.groupPic = groupPic;
		this.groupFailurePic = groupFailurePic;
		this.winAmount = winAmount;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getShowPic() {
		return showPic;
	}

	public void setShowPic(String showPic) {
		this.showPic = showPic;
	}

	public String getGroupPic() {
		return groupPic;
	}

	public void setGroupPic(String groupPic) {
		this.groupPic = groupPic;
	}

	public String getGroupFailurePic() {
		return groupFailurePic;
	}

	public void setGroupFailurePic(String groupFailurePic) {
		this.groupFailurePic = groupFailurePic;
	}
	
}
