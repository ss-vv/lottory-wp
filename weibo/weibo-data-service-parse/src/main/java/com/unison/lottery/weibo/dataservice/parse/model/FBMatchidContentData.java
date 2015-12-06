package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FBMatchidContentData {

	private String lotteryName;
	private String issueNum;
	private String id;
	private String qiuTanWangMatchId;
	private Date matchTime;
	private Date matchTimeInGMT0;
	private String homeName;
	private String awayName;
	//是否与球探的比赛主客相反
	private String qiuTanWangTurnHomeAndAway;
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getIssueNum() {
		return issueNum;
	}
	public void setIssueNum(String issueNum) {
		this.issueNum = issueNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQiuTanWangMatchId() {
		return qiuTanWangMatchId;
	}
	public void setQiuTanWangMatchId(String qiuTanWangMatchId) {
		this.qiuTanWangMatchId = qiuTanWangMatchId;
	}
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
	public String getHomeName() {
		return homeName;
	}
	public void setHomeName(String homeName) {
		this.homeName = homeName;
	}
	public String getAwayName() {
		return awayName;
	}
	public void setAwayName(String awayName) {
		this.awayName = awayName;
	}
	public String getQiuTanWangTurnHomeAndAway() {
		return qiuTanWangTurnHomeAndAway;
	}
	public void setQiuTanWangTurnHomeAndAway(String qiuTanWangTurnHomeAndAway) {
		this.qiuTanWangTurnHomeAndAway = qiuTanWangTurnHomeAndAway;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	public Date getMatchTimeInGMT0() {
		return matchTimeInGMT0;
	}
	public void setMatchTimeInGMT0(Date matchTimeInGMT0) {
		this.matchTimeInGMT0 = matchTimeInGMT0;
	}
	
	
	
}

