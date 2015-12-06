package com.xhcms.lottery.commons.data;

import java.util.Date;

public class LtLotteryHomeRecommendVo extends BetMatchRecVo{

	private long reId;
	private String reLotteryId;
	private Long reBetMatchId;
	private Integer reStatus;
	private Date reDeadlineTime;
	private Date reCreatedTime;
	private Date reUpdateTime;
	public long getReId() {
		return reId;
	}
	public void setReId(long reId) {
		this.reId = reId;
	}
	public String getReLotteryId() {
		return reLotteryId;
	}
	public void setReLotteryId(String reLotteryId) {
		this.reLotteryId = reLotteryId;
	}
	public Long getReBetMatchId() {
		return reBetMatchId;
	}
	public void setReBetMatchId(Long reBetMatchId) {
		this.reBetMatchId = reBetMatchId;
	}
	public Integer getReStatus() {
		return reStatus;
	}
	public void setReStatus(Integer reStatus) {
		this.reStatus = reStatus;
	}
	public Date getReDeadlineTime() {
		return reDeadlineTime;
	}
	public void setReDeadlineTime(Date reDeadlineTime) {
		this.reDeadlineTime = reDeadlineTime;
	}
	public Date getReCreatedTime() {
		return reCreatedTime;
	}
	public void setReCreatedTime(Date reCreatedTime) {
		this.reCreatedTime = reCreatedTime;
	}
	public Date getReUpdateTime() {
		return reUpdateTime;
	}
	public void setReUpdateTime(Date reUpdateTime) {
		this.reUpdateTime = reUpdateTime;
	}
	
	
}
