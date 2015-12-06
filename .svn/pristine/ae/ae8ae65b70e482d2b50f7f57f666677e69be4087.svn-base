package com.unison.lottery.weibo.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "md_qt_lc_matchId")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QTLCMatchidPO implements Serializable{

	private static final long serialVersionUID = -1126704949325404886L;

	@Id
	private String lcMatchId;
	private long qiuTanWangMatchId;
	private String id;
	private String lotteryName;
	private String issueNum;
	private Date matchTime;
	private Date matchTimeInGMT0;
	private String homeName;
	private String awayName;
	private String qiuTanWangTurnHomeAndAway;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public long getQiuTanWangMatchId() {
		return qiuTanWangMatchId;
	}
	public void setQiuTanWangMatchId(long qiuTanWangMatchId) {
		this.qiuTanWangMatchId = qiuTanWangMatchId;
	}
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
	public Date getMatchTimeInGMT0() {
		return matchTimeInGMT0;
	}
	public void setMatchTimeInGMT0(Date matchTimeInGMT0) {
		this.matchTimeInGMT0 = matchTimeInGMT0;
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
	public String getLcMatchId() {
		return lcMatchId;
	}
	public void setLcMatchId(String lcMatchId) {
		this.lcMatchId = lcMatchId;
	}
	
}
