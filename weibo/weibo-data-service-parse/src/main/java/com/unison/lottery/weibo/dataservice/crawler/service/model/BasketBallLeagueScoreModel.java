package com.unison.lottery.weibo.dataservice.crawler.service.model;

import java.io.Serializable;
import java.util.Date;

public class BasketBallLeagueScoreModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3147779601444518466L;
	private Integer id;
	private String leagueId;
	private Integer source;
	private Integer seasonId;
	private Integer processStatus;
	private String subLeagueId;
	private String leagueName;
	private Integer leagueType;
	private String teamId;
	private Integer scoreType;
	private String teamName;
	private Integer rank;
	private Integer totalMatches;
	private Integer winMatches;
	private Integer loseMatches;
	private Double winNet;
	private Double winPercent;
	private Double losePercent;
	private Double averageGoal;
	private Double averageLose;
	private String season;
	private String eastWinLose;
	private String westWinLose;
	private String sameWinLose;
	private String pastTen;
	private String leagueRecord;
	private Integer winContinuous;
	private Date createTime;
	private String groupName;
	private Date updateTime;
	
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	
	public String getLeagueRecord() {
		return leagueRecord;
	}
	public void setLeagueRecord(String leagueRecord) {
		this.leagueRecord = leagueRecord;
	}
	public Integer getId() {
		return id;
	}
	
	public String getSubLeagueId() {
		return subLeagueId;
	}
	public void setSubLeagueId(String subLeagueId) {
		this.subLeagueId = subLeagueId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public Integer getLeagueType() {
		return leagueType;
	}
	public void setLeagueType(Integer leagueType) {
		this.leagueType = leagueType;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public Integer getScoreType() {
		return scoreType;
	}
	public void setScoreType(Integer scoreType) {
		this.scoreType = scoreType;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getTotalMatches() {
		return totalMatches;
	}
	public void setTotalMatches(Integer totalMatches) {
		this.totalMatches = totalMatches;
	}
	public Integer getWinMatches() {
		return winMatches;
	}
	public void setWinMatches(Integer winMatches) {
		this.winMatches = winMatches;
	}
	public Integer getLoseMatches() {
		return loseMatches;
	}
	public void setLoseMatches(Integer loseMatches) {
		this.loseMatches = loseMatches;
	}
	public Double getWinNet() {
		return winNet;
	}
	public void setWinNet(Double winNet) {
		this.winNet = winNet;
	}
	public Double getWinPercent() {
		return winPercent;
	}
	public void setWinPercent(Double winPercent) {
		this.winPercent = winPercent;
	}
	public Double getLosePercent() {
		return losePercent;
	}
	public void setLosePercent(Double losePercent) {
		this.losePercent = losePercent;
	}
	public Double getAverageGoal() {
		return averageGoal;
	}
	public void setAverageGoal(Double averageGoal) {
		this.averageGoal = averageGoal;
	}
	public Double getAverageLose() {
		return averageLose;
	}
	public void setAverageLose(Double averageLose) {
		this.averageLose = averageLose;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getEastWinLose() {
		return eastWinLose;
	}
	public void setEastWinLose(String eastWinLose) {
		this.eastWinLose = eastWinLose;
	}
	public String getWestWinLose() {
		return westWinLose;
	}
	public void setWestWinLose(String westWinLose) {
		this.westWinLose = westWinLose;
	}
	public String getSameWinLose() {
		return sameWinLose;
	}
	public void setSameWinLose(String sameWinLose) {
		this.sameWinLose = sameWinLose;
	}
	public String getPastTen() {
		return pastTen;
	}
	public void setPastTen(String pastTen) {
		this.pastTen = pastTen;
	}
	public Integer getWinContinuous() {
		return winContinuous;
	}
	public void setWinContinuous(Integer winContinuous) {
		this.winContinuous = winContinuous;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
