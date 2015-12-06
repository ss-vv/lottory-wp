package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.Date;

/**
 * 传统足球赛程
 * @author Wang Lei
 *
 */
public class CTFBMatch implements Serializable {

    private static final long serialVersionUID = -8830057769185889080L;

	private String id;
	private String issueNumber;
//    private String lotteryId;
    private String playId;
    private Long matchId;
	private String leagueName;
	private Date playingTime;
	private Date offtime;
	private Date entrustDeadline;
	private String homeTeamName;
	private String guestTeamName;
	private int status;
	private String halfScore;
	private String score;
	private String winOption;
	private String options;
	private String odds;
	private String name;
	private String color;
	private String code;
	/** 让球 */
	private float defaultScore;
	
	public CTFBMatch(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

//	public String getLotteryId() {
//		return lotteryId;
//	}
//
//	public void setLotteryId(String lotteryId) {
//		this.lotteryId = lotteryId;
//	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getWinOption() {
		return winOption;
	}

	public void setWinOption(String winOption) {
		this.winOption = winOption;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOdds() {
		return odds;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public float getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(float defaultScore) {
		this.defaultScore = defaultScore;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}
	
}
