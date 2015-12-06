package com.xhcms.lottery.commons.persist.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @desc 竞猜世界杯冠军队伍信息
 * @author lei.li@unison.net.cn
 * @createTime 2014-5-21
 * @version 1.0
 */

@Table(name = "cgj_teams")
@Entity
public class CGJTeamPO {

	@Id
	private long id;

	@Column(name = "team_id")
	private long teamId;

	@Column(name = "match_name")
	private String name;

	@Column(name = "team_name")
	private String teamName;

	@Column(name = "team_logo")
	private String teamLogo;

	@Column(name = "group_name")
	private String groupName;

	private BigDecimal odds;

	@Column(name = "sale_status")
	private int status;

	private int rank;

	private String season;

	@Column(name = "current_season")
	private int currentSeason;

	@Column(name = "play_type")
	private String playType;

	private Date offtime;

	@Column(name = "playing_time")
	private Date playingTime;

	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "update_time")
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamLogo() {
		return teamLogo;
	}

	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public BigDecimal getOdds() {
		return odds;
	}

	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public int getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(int currentSeason) {
		this.currentSeason = currentSeason;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public Date getOfftime() {
		return offtime;
	}

	public void setOfftime(Date offtime) {
		this.offtime = offtime;
	}

	public Date getPlayingTime() {
		return playingTime;
	}

	public void setPlayingTime(Date playingTime) {
		this.playingTime = playingTime;
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

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
