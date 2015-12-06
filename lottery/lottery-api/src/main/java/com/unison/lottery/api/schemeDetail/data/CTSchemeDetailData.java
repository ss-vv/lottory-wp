package com.unison.lottery.api.schemeDetail.data;

import java.util.Date;

/**
 * 用于构建传统足彩方案详情数据，提供给客户端使用的数据格式
 * @desc
 * @createTime 2013-7-12
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class CTSchemeDetailData {

	//赛事编号
	private long matchId;
	
	private String matchName;
	
	private String homeTeamName;
	
	private String guestTeamName;
	
	private String issueNumber;
	
	private Date playingTime;
	
	private String betContent;
	
	//赛果
	private String matchResult;
	
	private String score;
	
	private String defaultScore;
	
	private boolean isPass;//比赛是否过关

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getGuestTeamName() {
		return guestTeamName;
	}

	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public Date getPlayingTime() {
		return playingTime;
	}

	public void setPlayingTime(Date playingTime) {
		this.playingTime = playingTime;
	}

	public String getBetContent() {
		return betContent;
	}

	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}

	public String getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(String matchResult) {
		this.matchResult = matchResult;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(String defaultScore) {
		this.defaultScore = defaultScore;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
}
