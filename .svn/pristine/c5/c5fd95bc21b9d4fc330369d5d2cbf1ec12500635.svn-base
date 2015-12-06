package com.xhcms.lottery.dc.data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 北单赔率
 */
public class BDOdds extends OddsBase {
	
	private Date matchtime; // 开赛时间
	private String issueNumber;
	private String options;
	private String finalOdds;
	private BigDecimal condedePoints;
	private String winOption;
	private Date createdTime;
	private Date updateTime;
	
	public BDOdds() {
		
	}
	
	public BDOdds(String issueNumber, String playId, String options) {
		this.issueNumber = issueNumber;
		this.playId = playId;
		this.options = options;
	}
	
	@Override
	public long getMatchId() {
		if (matchId <= 0) {
			matchId = Long.parseLong(issueNumber)*1000 + Long.parseLong(code);
		}
		return matchId;
	}
	
	public Date getMatchtime() {
		return matchtime;
	}
	
	public void setMatchtime(Date matchtime) {
		this.matchtime = matchtime;
	}
	
	public String getIssueNumber() {
		return issueNumber;
	}
	
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getFinalOdds() {
		return finalOdds;
	}

	public void setFinalOdds(String finalOdds) {
		this.finalOdds = finalOdds;
	}

	public BigDecimal getCondedePoints() {
		return condedePoints;
	}

	public void setCondedePoints(BigDecimal condedePoints) {
		this.condedePoints = condedePoints;
	}

	public String getWinOption() {
		return winOption;
	}

	public void setWinOption(String winOption) {
		this.winOption = winOption;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}