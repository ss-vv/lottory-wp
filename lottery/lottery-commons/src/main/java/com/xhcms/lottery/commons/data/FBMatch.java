package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.Date;

public class FBMatch implements Serializable{
    private static final long serialVersionUID = 5246999736716396329L;
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
	private int status;
	private int concedePoints;
	private String halfScore;
	private String score;
	private String halfScorePreset;
	private String scorePreset;
	private int checkStatus;
	private String note;

	//check score
	private String checkScore;
	private String checkHalfScore;
	public String getHalfScorePreset() {
		return halfScorePreset;
	}

	public void setHalfScorePreset(String halfScorePreset) {
		this.halfScorePreset = halfScorePreset;
	}

	public String getScorePreset() {
		return scorePreset;
	}

	public void setScorePreset(String scorePreset) {
		this.scorePreset = scorePreset;
	}

	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getConcedePoints() {
		return concedePoints;
	}

	public void setConcedePoints(int concedePoints) {
		this.concedePoints = concedePoints;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	public String getCheckScore() {
		return checkScore;
	}

	public void setCheckScore(String checkScore) {
		this.checkScore = checkScore;
	}

	public String getCheckHalfScore() {
		return checkHalfScore;
	}

	public void setCheckHalfScore(String checkHalfScore) {
		this.checkHalfScore = checkHalfScore;
	}
	
}
