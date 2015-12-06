package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MatchProcess {
	
	private String matchId;				// 球探比赛id
	private String leagueId;			// 联赛id
	private Date matchTimeInGMT0;		// 比赛时间的格林威治标准时间表示
	private Date matchTime;				// 比赛时间的北京时间表示
	private String actualBeginTime;		// 开场时间
	private TeamStatus homeTeamStatus;	// 主队相关信息
	private TeamStatus guestTeamStatus;	// 客队相关信息
	private String matchStatus;			// 0:未开,1:上半场,2:中场,3:下半场,-11:待定,-12:腰斩,-13:中断,-14:推迟,-1:完场
	private String televison;			// 直播电视台
	private boolean zlc;				// 是否中立场
	private String level;				// 0 :彩票赛事，1：重要赛事，2：次要赛事
	private String reserve;				// 留用
	
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	
	public String getActualBeginTime() {
		return actualBeginTime;
	}
	public void setActualBeginTime(String actualBeginTime) {
		this.actualBeginTime = actualBeginTime;
	}
	public TeamStatus getHomeTeamStatus() {
		return homeTeamStatus;
	}
	public void setHomeTeamStatus(TeamStatus homeTeamStatus) {
		this.homeTeamStatus = homeTeamStatus;
	}
	public TeamStatus getGuestTeamStatus() {
		return guestTeamStatus;
	}
	public void setGuestTeamStatus(TeamStatus guestTeamStatus) {
		this.guestTeamStatus = guestTeamStatus;
	}
	public String getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}
	public String getTelevison() {
		return televison;
	}
	public void setTelevison(String televison) {
		this.televison = televison;
	}
	public boolean isZlc() {
		return zlc;
	}
	public void setZlc(boolean zlc) {
		this.zlc = zlc;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
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
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
