package com.unison.lottery.weibo.data.service.store.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 竞彩足球比分直播VO
 * @author Wang Lei
 *
 */
public class QTMatchVO implements Serializable {

	private static final long serialVersionUID = 4254533949741152916L;

	public QTMatchVO(){}

	
	public QTMatchVO(long id, String cnCode, String color,
			String leagueShortName, Date matchTime, int matchStatus,
			Integer homeTeamPosition, Integer guestTeamPosition,
			String homeTeamName, String guestTeamName, String score,
			String halfScore) {
		this.id = id;
		this.cnCode = cnCode;
		this.color = color;
		this.leagueShortName = leagueShortName;
		this.matchTime = matchTime;
		this.matchStatus = matchStatus;
		this.homeTeamPosition = homeTeamPosition;
		this.guestTeamPosition = guestTeamPosition;
		this.homeTeamName = homeTeamName;
		this.guestTeamName = guestTeamName;
		this.score = score;
		this.halfScore = halfScore;
	}


	private long id;
	private String cnCode;
	private String color;
	private String leagueShortName;
	private Date matchTime;
	private int matchStatus;
	private Integer homeTeamPosition;
	private Integer guestTeamPosition;
	private String homeTeamName;
	private String guestTeamName;
	private String score;
	private String halfScore;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLeagueShortName() {
		return leagueShortName;
	}

	public void setLeagueShortName(String leagueShortName) {
		this.leagueShortName = leagueShortName;
	}
	
	@JSON(format="MM-dd HH:mm")
	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public int getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(int matchStatus) {
		this.matchStatus = matchStatus;
	}

	public Integer getHomeTeamPosition() {
		return homeTeamPosition;
	}

	public void setHomeTeamPosition(Integer homeTeamPosition) {
		this.homeTeamPosition = homeTeamPosition;
	}

	public Integer getGuestTeamPosition() {
		return guestTeamPosition;
	}

	public void setGuestTeamPosition(Integer guestTeamPosition) {
		this.guestTeamPosition = guestTeamPosition;
	}

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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getHalfScore() {
		return halfScore;
	}

	public void setHalfScore(String halfScore) {
		this.halfScore = halfScore;
	}

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	
}
