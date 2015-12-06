package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Value;

/**
 * 足球比赛信息
 * 
 * @author 彭保星
 *
 * @since 2014年11月4日下午3:37:36
 */
@Entity
@Table(name = "md_qt_match_base")
public class FbMatchBaseInfoPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -269421580850978410L;
	@Id
	private long id;
	private Integer seasonId;
	private Integer source;
	private Integer processStatus;
	private String bsId;
	private String leagueId;
	private String leagueShortName;
	private String color;
	private String homeTeamId;
	private String guestTeamId;
	private String homeTeamName;
	private String guestTeamName;
	// /// 是否是足彩比分 0：非足彩，1:胜负彩，2：北京单场，3：胜负彩+北京单场,-1:未知
	private Integer fbLotteryScoreType;
	// // 是否有阵容: 1为有
	private Boolean haveBattleArray;
	// // 比赛说明
	private String matchMessage;
	private String score;
	private String halfScore;
	private Integer matchResult;
	private Integer halfMatchResult;
	@Type(type = "timestamp")
	private Date matchTime;
	@Type(type = "timestamp")
	private Date halfStartTime;
	private Integer homeTeamPosition;
	private Integer guestTeamPosition;
	private Integer matchStatus;
	private Integer homeTeamScore;
	private Integer homeTeamHalfScore;
	private Integer guestTeamScore;
	private Integer guestTeamHalfScore;
	private String televison;
	private Integer neutralType;
	private Integer levelType;
	private Integer homeTeamRed;
	private Integer guestTeamRed;
	private Integer homeTeamYellow;
	private Integer guestTeamYellow;
	private String temperature;
	private String season;
	private String groupId; // 小组分组组名（无）
	private String court; // 球场
	private String streamLine; // 是否是精简比赛（0:完全版、1:精简）
	@Type(type = "timestamp")
	private Date createTime;
	@Type(type = "timestamp")
	private Date updateTime;
	private Double lordOdds; // 主胜赔率
	private Double flatOdds; // 平赔率
	private Double negativeOdds; // 主负赔率
	private String jingcaiId;
	@Type(type = "timestamp")
	private Date halfEndTime;
	private String isNow;
	private String liveContent;

	public FbMatchBaseInfoPO() {

	}

	public FbMatchBaseInfoPO(String leagueId,String jingcaiId,String homeTeamId, String guestTeamId,
			Integer homeTeamScore, Integer guestTeamScore,
			Integer homeTeamHalfScore, Integer guestTeamHalfScore,
			Date matchTime, String colour, String chineseName) {
		this.leagueId = leagueId;
		this.jingcaiId = jingcaiId;
		this.homeTeamId = homeTeamId;
		this.guestTeamId = guestTeamId;
		this.homeTeamScore = homeTeamScore;
		this.guestTeamScore = guestTeamScore;
		this.homeTeamHalfScore = homeTeamHalfScore;
		this.guestTeamHalfScore = guestTeamHalfScore;
		this.matchTime = matchTime;
		this.leagueShortName = chineseName;
		this.color = colour;
	}
	
	public FbMatchBaseInfoPO(String bsId,long id){
		this.bsId = bsId;
		this.id = id;
	}
	public String getGuestTeamName() {
		return guestTeamName;
	}

	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}
	
	public String getLiveContent() {
		return liveContent;
	}

	public void setLiveContent(String liveContent) {
		this.liveContent = liveContent;
	}

	public String getJingcaiId() {
		return jingcaiId;
	}

	public void setJingcaiId(String jingcaiId) {
		this.jingcaiId = jingcaiId;
	}
	
	public String getIsNow() {
		return isNow;
	}

	public void setIsNow(String isNow) {
		this.isNow = isNow;
	}

	public Double getLordOdds() {
		return lordOdds;
	}

	public void setLordOdds(Double lordOdds) {
		this.lordOdds = lordOdds;
	}

	public Double getFlatOdds() {
		return flatOdds;
	}

	public void setFlatOdds(Double flatOdds) {
		this.flatOdds = flatOdds;
	}

	public Date getHalfEndTime() {
		return halfEndTime;
	}

	public void setHalfEndTime(Date halfEndTime) {
		this.halfEndTime = halfEndTime;
	}

	public Double getNegativeOdds() {
		return negativeOdds;
	}

	public void setNegativeOdds(Double negativeOdds) {
		this.negativeOdds = negativeOdds;
	}

	public FbMatchBaseInfoPO(long id, String bsId) {
		this.id = id;
		this.bsId = bsId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
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

	public String getBsId() {
		return bsId;
	}

	public void setBsId(String bsId) {
		this.bsId = bsId;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getLeagueShortName() {
		return leagueShortName;
	}

	public void setLeagueShortName(String leagueShortName) {
		this.leagueShortName = leagueShortName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(String guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public Boolean getHaveBattleArray() {
		return haveBattleArray;
	}

	public void setHaveBattleArray(Boolean haveBattleArray) {
		this.haveBattleArray = haveBattleArray;
	}

	public String getMatchMessage() {
		return matchMessage;
	}

	public void setMatchMessage(String matchMessage) {
		this.matchMessage = matchMessage;
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

	public Integer getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(Integer matchResult) {
		this.matchResult = matchResult;
	}

	public Integer getHalfMatchResult() {
		return halfMatchResult;
	}

	public void setHalfMatchResult(Integer halfMatchResult) {
		this.halfMatchResult = halfMatchResult;
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public Date getHalfStartTime() {
		return halfStartTime;
	}

	public void setHalfStartTime(Date halfStartTime) {
		this.halfStartTime = halfStartTime;
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

	public Integer getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(Integer matchStatus) {
		this.matchStatus = matchStatus;
	}

	public Integer getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(Integer homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public Integer getHomeTeamHalfScore() {
		return homeTeamHalfScore;
	}

	public void setHomeTeamHalfScore(Integer homeTeamHalfScore) {
		this.homeTeamHalfScore = homeTeamHalfScore;
	}

	public Integer getGuestTeamHalfScore() {
		return guestTeamHalfScore;
	}

	public void setGuestTeamHalfScore(Integer guestTeamHalfScore) {
		this.guestTeamHalfScore = guestTeamHalfScore;
	}

	public Integer getGuestTeamScore() {
		return guestTeamScore;
	}

	public void setGuestTeamScore(Integer guestTeamScore) {
		this.guestTeamScore = guestTeamScore;
	}

	public String getTelevison() {
		return televison;
	}

	public void setTelevison(String televison) {
		this.televison = televison;
	}

	public Integer getNeutralType() {
		return neutralType;
	}

	public void setNeutralType(Integer neutralType) {
		this.neutralType = neutralType;
	}

	public Integer getLevelType() {
		return levelType;
	}

	public void setLevelType(Integer levelType) {
		this.levelType = levelType;
	}

	public Integer getFbLotteryScoreType() {
		return fbLotteryScoreType;
	}

	public void setFbLotteryScoreType(Integer fbLotteryScoreType) {
		this.fbLotteryScoreType = fbLotteryScoreType;
	}

	public Integer getHomeTeamRed() {
		return homeTeamRed;
	}

	public void setHomeTeamRed(Integer homeTeamRed) {
		this.homeTeamRed = homeTeamRed;
	}

	public Integer getGuestTeamRed() {
		return guestTeamRed;
	}

	public void setGuestTeamRed(Integer guestTeamRed) {
		this.guestTeamRed = guestTeamRed;
	}

	public Integer getHomeTeamYellow() {
		return homeTeamYellow;
	}

	public void setHomeTeamYellow(Integer homeTeamYellow) {
		this.homeTeamYellow = homeTeamYellow;
	}

	public Integer getGuestTeamYellow() {
		return guestTeamYellow;
	}

	public void setGuestTeamYellow(Integer guestTeamYellow) {
		this.guestTeamYellow = guestTeamYellow;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public String getStreamLine() {
		return streamLine;
	}

	public void setStreamLine(String streamLine) {
		this.streamLine = streamLine;
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
