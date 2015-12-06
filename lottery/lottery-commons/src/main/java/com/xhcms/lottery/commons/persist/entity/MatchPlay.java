package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

public class MatchPlay implements Serializable {
	private static final long serialVersionUID = 5799940033503708816L;
	// 赛事信息
	private Long id;
	private String code;
	private String cnCode;
	private String name;
	private long leagueId;
	private String leagueName;
	private Date playingTime;
	private Date offtime;
	private Date entrustDeadline;
	private long homeTeamId;
	private String homeTeamName;
	private int guestTeamId;
	private String guestTeamName;
	
	// 玩法信息
	private String playId;
	private String options;
	private String odds;
	private String priorOdds;
	private float defaultScore;
	
	private String score;
	private String halfScore;
	private int status;
	private String homeTeamPosition;
	private String guestTeamPosition;
	private boolean stopMatch;//是否为停售的比赛
	private int qtMatchStatus;
	private String matchId;
	
	private String color;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public Date getPlayingTime() {
		return playingTime;
	}

	public void setPlayingTime(Date playingTime) {
		this.playingTime = playingTime;
	}

	public Date getOfftime() {
		return offtime;
	}

	public void setOfftime(Date offtime) {
		this.offtime = offtime;
	}

	public Date getEntrustDeadline() {
		return entrustDeadline;
	}

	public void setEntrustDeadline(Date entrustDeadline) {
		this.entrustDeadline = entrustDeadline;
	}

	public long getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(long homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public int getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(int guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public String getGuestTeamName() {
		return guestTeamName;
	}

	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getOdds() {
		return odds;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

	public String getPriorOdds() {
		return priorOdds;
	}

	public void setPriorOdds(String priorOdds) {
		this.priorOdds = priorOdds;
	}

	public float getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(float defaultScore) {
		this.defaultScore = defaultScore;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getHalfScore() {
		return halfScore;
	}

	public void setHalfScore(String halfScore) {
		this.halfScore = halfScore;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHomeTeamPosition() {
		return homeTeamPosition;
	}

	public void setHomeTeamPosition(String homeTeamPosition) {
		this.homeTeamPosition = homeTeamPosition;
	}

	public String getGuestTeamPosition() {
		return guestTeamPosition;
	}

	public void setGuestTeamPosition(String guestTeamPosition) {
		this.guestTeamPosition = guestTeamPosition;
	}

	public boolean isStopMatch() {
		return stopMatch;
	}

	public void setStopMatch(boolean stopMatch) {
		this.stopMatch = stopMatch;
	}

	public int getQtMatchStatus() {
		return qtMatchStatus;
	}

	public void setQtMatchStatus(int qtMatchStatus) {
		this.qtMatchStatus = qtMatchStatus;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

}
