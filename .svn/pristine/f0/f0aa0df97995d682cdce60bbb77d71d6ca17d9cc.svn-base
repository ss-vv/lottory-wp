package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 北单赛事信息
 */
@Entity
@Table(name = "bjdc_match")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class BJDCMatchPO implements Serializable {

	private static final long serialVersionUID = -8830057769185889080L;

	@Id
	private Long id;

	@Column(nullable = false, name = "issue_number")
	private String issueNumber;

	@Column(nullable = false)
	private String code;

	@Column(name = "cn_code", nullable = false)
	private String cnCode;

	@Column(nullable = false, name = "name")
	private String name;

	@Column(nullable = false, name = "league_id")
	private long leagueId;

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

	@Column(name = "home_team_id")
	private long homeTeamId;

	@Column(nullable = false, name = "home_team_name")
	private String homeTeamName;

	@Column(name = "guest_team_id")
	private int guestTeamId;

	@Column(nullable = false, name = "guest_team_name")
	private String guestTeamName;

	private int status;

	@Column(name = "half_score")
	private String halfScore;

	@Column(name = "score")
	private String score;

	private String note;

	@Column(name = "created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

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

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
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