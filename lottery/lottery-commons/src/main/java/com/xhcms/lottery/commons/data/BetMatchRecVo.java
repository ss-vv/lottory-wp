package com.xhcms.lottery.commons.data;

import java.util.Date;

public class BetMatchRecVo {

	private Long id;
	private Long schemeId;
	private String sponsor;
	private String code;
	private String playId;
	private String annotation;
	private String cnCode;
	private String homeTeamName;
	private String guestTeamName;
	private String leagueName;
	private Date playingTime;
	private Date entrustDeadline;
	private String concedePoints;
	private Integer isOn;//是否已在推荐表中
	private Long matchId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPlayId() {
		return playId;
	}
	public void setPlayId(String playId) {
		this.playId = playId;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public String getCnCode() {
		return cnCode;
	}
	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
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
	public Date getEntrustDeadline() {
		return entrustDeadline;
	}
	public void setEntrustDeadline(Date entrustDeadline) {
		this.entrustDeadline = entrustDeadline;
	}
	public String getConcedePoints() {
		return concedePoints;
	}
	public void setConcedePoints(String concedePoints) {
		this.concedePoints = concedePoints;
	}
	public Integer getIsOn() {
		return isOn;
	}
	public void setIsOn(Integer isOn) {
		this.isOn = isOn;
	}
	public Long getMatchId() {
		return matchId;
	}
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	
	
}
