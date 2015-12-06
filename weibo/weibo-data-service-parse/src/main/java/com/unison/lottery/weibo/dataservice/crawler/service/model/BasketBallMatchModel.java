package com.unison.lottery.weibo.dataservice.crawler.service.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 篮球比赛
 * @author 彭保星
 *
 * @since 2014年11月19日上午11:40:43
 */
public class BasketBallMatchModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1080819823461333590L;
	private Integer id;
	private String bsId;
	private Integer source;
	private Integer processStatus;
	private String name;
	private String nameF;
	private Integer sclassType;
	private String leagueId;
	private String color;
	private Date matchTime;
	private Integer matchState;
	private String remainTime;
	private String homeTeamId;
	private String homeTeam;
	private String homeTeamF;
	private String homeTeamE;
	private String guestTeam;
	private String guestTeamId;
	private String guestTeamF;
	private String guestTeamE;
	private Integer homeScore;
	private Integer guestScore;
	private Integer homeOne;
	private Integer guestOne;
	private Integer homeTwo;
	private Integer guestTwo;
	private Integer homeThree;
	private Integer guestThree;
	private Integer homeFour;
	private Integer guestFour;
	private Integer addTime; //加时次数
	private Integer homeAddTime1;
	private Integer homeAddTime2;
	private Integer homeAddTime3;
	private Integer guestAddTime1;
	private Integer guestAddTime2;
	private Integer guestAddTime3;
	private Integer addTechnic; //是否有技术统计
	private String tv;
	private String explainContent;
	private String explain2;
	private String cupLeague; //比赛类型：2杯赛 1联赛
	private Double homeWindOdds;
	private Double guestWindOdds;
	private String season;
	private Integer seasonId;
	private String matchType;
	private String matchClass;
	private String matchSubClass;
	private Integer isNeutral;
	private String homeTeamRank;
	private String guestTeamRank;
	private Integer letBallNum;
	private Date createTime;
	private Date updateTime;
	private String jingcaiId;
	
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	public String getJingcaiId() {
		return jingcaiId;
	}
	public void setJingcaiId(String jingcaiId) {
		this.jingcaiId = jingcaiId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getLetBallNum() {
		return letBallNum;
	}
	public void setLetBallNum(Integer letBallNum) {
		this.letBallNum = letBallNum;
	}
	public String getBsId() {
		return bsId;
	}
	public void setBsId(String bsId) {
		this.bsId = bsId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameF() {
		return nameF;
	}
	public void setNameF(String nameF) {
		this.nameF = nameF;
	}
	public Integer getSclassType() {
		return sclassType;
	}
	public void setSclassType(Integer sclassType) {
		this.sclassType = sclassType;
	}
	public String getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
	public Integer getMatchState() {
		return matchState;
	}
	public void setMatchState(Integer matchState) {
		this.matchState = matchState;
	}
	public String getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}
	public String getHomeTeamId() {
		return homeTeamId;
	}
	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getHomeTeamF() {
		return homeTeamF;
	}
	public void setHomeTeamF(String homeTeamF) {
		this.homeTeamF = homeTeamF;
	}
	public String getHomeTeamE() {
		return homeTeamE;
	}
	public void setHomeTeamE(String homeTeamE) {
		this.homeTeamE = homeTeamE;
	}
	public String getGuestTeam() {
		return guestTeam;
	}
	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
	}
	public String getGuestTeamId() {
		return guestTeamId;
	}
	public void setGuestTeamId(String guestTeamId) {
		this.guestTeamId = guestTeamId;
	}
	public String getGuestTeamF() {
		return guestTeamF;
	}
	public void setGuestTeamF(String guestTeamF) {
		this.guestTeamF = guestTeamF;
	}
	public String getGuestTeamE() {
		return guestTeamE;
	}
	public void setGuestTeamE(String guestTeamE) {
		this.guestTeamE = guestTeamE;
	}
	public Integer getHomeScore() {
		return homeScore;
	}
	public void setHomeScore(Integer homeScore) {
		this.homeScore = homeScore;
	}
	public Integer getGuestScore() {
		return guestScore;
	}
	public void setGuestScore(Integer guestScore) {
		this.guestScore = guestScore;
	}
	public Integer getHomeOne() {
		return homeOne;
	}
	public void setHomeOne(Integer homeOne) {
		this.homeOne = homeOne;
	}
	public Integer getGuestOne() {
		return guestOne;
	}
	public void setGuestOne(Integer guestOne) {
		this.guestOne = guestOne;
	}
	public Integer getHomeTwo() {
		return homeTwo;
	}
	public void setHomeTwo(Integer homeTwo) {
		this.homeTwo = homeTwo;
	}
	public Integer getGuestTwo() {
		return guestTwo;
	}
	public void setGuestTwo(Integer guestTwo) {
		this.guestTwo = guestTwo;
	}
	public Integer getHomeThree() {
		return homeThree;
	}
	public void setHomeThree(Integer homeThree) {
		this.homeThree = homeThree;
	}
	public Integer getGuestThree() {
		return guestThree;
	}
	public void setGuestThree(Integer guestThree) {
		this.guestThree = guestThree;
	}
	public Integer getHomeFour() {
		return homeFour;
	}
	public void setHomeFour(Integer homeFour) {
		this.homeFour = homeFour;
	}
	public Integer getGuestFour() {
		return guestFour;
	}
	public void setGuestFour(Integer guestFour) {
		this.guestFour = guestFour;
	}
	public Integer getAddTime() {
		return addTime;
	}
	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}
	public Integer getHomeAddTime1() {
		return homeAddTime1;
	}
	public void setHomeAddTime1(Integer homeAddTime1) {
		this.homeAddTime1 = homeAddTime1;
	}
	public Integer getHomeAddTime2() {
		return homeAddTime2;
	}
	public void setHomeAddTime2(Integer homeAddTime2) {
		this.homeAddTime2 = homeAddTime2;
	}
	public Integer getHomeAddTime3() {
		return homeAddTime3;
	}
	public void setHomeAddTime3(Integer homeAddTime3) {
		this.homeAddTime3 = homeAddTime3;
	}
	public Integer getGuestAddTime1() {
		return guestAddTime1;
	}
	public void setGuestAddTime1(Integer guestAddTime1) {
		this.guestAddTime1 = guestAddTime1;
	}
	public Integer getGuestAddTime2() {
		return guestAddTime2;
	}
	public void setGuestAddTime2(Integer guestAddTime2) {
		this.guestAddTime2 = guestAddTime2;
	}
	public Integer getGuestAddTime3() {
		return guestAddTime3;
	}
	public void setGuestAddTime3(Integer guestAddTime3) {
		this.guestAddTime3 = guestAddTime3;
	}
	public Integer getAddTechnic() {
		return addTechnic;
	}
	public void setAddTechnic(Integer addTechnic) {
		this.addTechnic = addTechnic;
	}
	public String getTv() {
		return tv;
	}
	public void setTv(String tv) {
		this.tv = tv;
	}
	public String getExplainContent() {
		return explainContent;
	}
	public void setExplainContent(String explainContent) {
		this.explainContent = explainContent;
	}
	public String getExplain2() {
		return explain2;
	}
	public void setExplain2(String explain2) {
		this.explain2 = explain2;
	}
	public String getCupLeague() {
		return cupLeague;
	}
	public void setCupLeague(String cupLeague) {
		this.cupLeague = cupLeague;
	}
	public Double getHomeWindOdds() {
		return homeWindOdds;
	}
	public void setHomeWindOdds(Double homeWindOdds) {
		this.homeWindOdds = homeWindOdds;
	}
	public Double getGuestWindOdds() {
		return guestWindOdds;
	}
	public void setGuestWindOdds(Double guestWindOdds) {
		this.guestWindOdds = guestWindOdds;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getMatchClass() {
		return matchClass;
	}
	public void setMatchClass(String matchClass) {
		this.matchClass = matchClass;
	}
	public String getMatchSubClass() {
		return matchSubClass;
	}
	public void setMatchSubClass(String matchSubClass) {
		this.matchSubClass = matchSubClass;
	}
	public Integer getIsNeutral() {
		return isNeutral;
	}
	public void setIsNeutral(Integer isNeutral) {
		this.isNeutral = isNeutral;
	}
	public String getHomeTeamRank() {
		return homeTeamRank;
	}
	public void setHomeTeamRank(String homeTeamRank) {
		this.homeTeamRank = homeTeamRank;
	}
	public String getGuestTeamRank() {
		return guestTeamRank;
	}
	public void setGuestTeamRank(String guestTeamRank) {
		this.guestTeamRank = guestTeamRank;
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
