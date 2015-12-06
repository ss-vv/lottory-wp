package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ctfb_match")

public class CTFBMatchPO implements Serializable {

    private static final long serialVersionUID = -8830057769185889080L;

    @Id
	private String id;

	@Column(name = "issue_number",nullable = false)
	private String issueNumber;
	
    @Column(nullable = false, name = "play_id")
    private String playId;

    @Column(nullable = false, name = "match_id")
    private Long matchId;

	@Column(nullable = false, name = "league_name")
	private String leagueName;

	@Column(name = "playing_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date playingTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date offtime;

	@Column(name = "entrust_deadline")
	@Temporal(TemporalType.TIMESTAMP)
	private Date entrustDeadline;

	@Column(nullable = false, name = "home_team_name")
	private String homeTeamName;
	
	@Column(nullable = false, name = "guest_team_name")
	private String guestTeamName;

	private int status;

	@Column(name = "half_score")
	private String halfScore;

	private String score;
	
	@Column(name = "win_option")
	private String winOption;

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

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

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

	
}
