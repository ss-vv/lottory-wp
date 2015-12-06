/**
 * 
 */
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

@Entity
@Table(name = "bb_match")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class BBMatchPO implements Serializable {
	private static final long serialVersionUID = -8830057769185889080L;

	@Id
	private Long id;

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

	@Column(nullable = false, name = "long_league_name")
	private String longLeagueName;
	
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

	@Column(name = "quarter1")
	private String quarter1;

	@Column(name = "quarter2")
	private String quarter2;

	@Column(name = "quarter3")
	private String quarter3;

	@Column(name = "quarter4")
	private String quarter4;

	@Column(name = "final_score")
	private String finalScore;

	private String note;
	
	/**
	 * 预设全场比分
	 */
	@Column(name = "final_score_preset")
	private String finalScorePreset;
	
	/**
	 * 赛果核对状态
	 * 0 - 未做预兑奖
	 * 1 - 做了预兑奖，但未核对
	 * 2 - 已核对，一致
	 * 3 - 已核对，不一致
	 */
	@Column(name = "check_status")
	private int checkStatus;
	
	public String getFinalScorePreset() {
		return finalScorePreset;
	}

	public void setFinalScorePreset(String finalScorePreset) {
		this.finalScorePreset = finalScorePreset;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getQuarter1() {
		return quarter1;
	}

	public void setQuarter1(String quarter1) {
		this.quarter1 = quarter1;
	}

	public String getQuarter2() {
		return quarter2;
	}

	public void setQuarter2(String quarter2) {
		this.quarter2 = quarter2;
	}

	public String getQuarter3() {
		return quarter3;
	}

	public void setQuarter3(String quarter3) {
		this.quarter3 = quarter3;
	}

	public String getQuarter4() {
		return quarter4;
	}

	public void setQuarter4(String quarter4) {
		this.quarter4 = quarter4;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLongLeagueName() {
		return longLeagueName;
	}

	public void setLongLeagueName(String longLeagueName) {
		this.longLeagueName = longLeagueName;
	}
}
