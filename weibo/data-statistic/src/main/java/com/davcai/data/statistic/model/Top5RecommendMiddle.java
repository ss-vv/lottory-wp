package com.davcai.data.statistic.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Top5RecommendMiddle {
	private long id;
	private Date recommendCreateTime;
	private long userId;
	private String lotteryId;
	private String playId;
	private long matchId;
	private String code;
	private String winOption;
	private int isWin;//是否赢，0为输，1为赢
	private float revenueRatio;
	private String odds;
	private String options;
	private String finalOdds;//北单赛果最终赔率
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getRecommendCreateTime() {
		return recommendCreateTime;
	}
	public void setRecommendCreateTime(Date recommendCreateTime) {
		this.recommendCreateTime = recommendCreateTime;
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
	public String getPlayId() {
		return playId;
	}
	public void setPlayId(String playId) {
		this.playId = playId;
	}
	public long getMatchId() {
		return matchId;
	}
	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getWinOption() {
		return winOption;
	}
	public void setWinOption(String winOption) {
		this.winOption = winOption;
	}
	public int getIsWin() {
		return isWin;
	}
	public void setIsWin(int isWin) {
		this.isWin = isWin;
	}
	public float getRevenueRatio() {
		return revenueRatio;
	}
	public void setRevenueRatio(float revenueRatio) {
		this.revenueRatio = revenueRatio;
	}
	public String getOdds() {
		return odds;
	}
	public void setOdds(String odds) {
		this.odds = odds;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	public String getFinalOdds() {
		return finalOdds;
	}
	public void setFinalOdds(String finalOdds) {
		this.finalOdds = finalOdds;
	}
	

}
