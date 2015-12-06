package com.unison.lottery.weibo.dataservice.parse.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 篮球赛事信息
 * 
 * @author Yang Bo
 */
public class BBMatchInfoData {
	public static final String IS_NOT_MIDDLE_MATCH = "0";

	public static final String IS_MIDDLE_MATCH="1";
	
	private long id;			// 赛事ID
	private String name;		// 简体联赛名
	private String nameF;		// 繁体联赛名
	private int sclassType;		// 分几节进行，2:上下半场，4：分4小节
	private String color;		// 颜色值
	private Date matchTime;		// 开赛时间, 03月04日<br>09:00，年份在后面字段获取
	private String matchTimeStr;// 开赛时间串
	private String year;		// 年份
	private int matchState;		// 状态:0:未开赛,1:一节,2:二节,5:1'OT，以此类推，-1:完场, -2:待定,-3:中断,-4:取消,-5:推迟,50中场
	private int remainTime;		// 小节剩余时间,秒为单位
	private long homeTeamId;	// 主队ID
	private String homeTeam;	// 主队名，简体
	private String homeTeamF;	// 主队名，繁体
	private String homeTeamE;	// 主队名，英文
	private String homeTeamRank;//主队排名
	private long guestTeamId;	// 客队ID
	private String guestTeam;	// 客队名，简体
	private String guestTeamF;	// 客队名，繁体
	private String guestTeamE;	// 客队名，英文
	private String guestTeamRank;//客队排名
	private int homeScore;		// 主队得分
	private int guestScore;		// 客队得分
	private int homeOne;		// 主队一节得分(上半场)
	private int guestOne;		// 客队一节得分（上半场）
	private int homeTwo;		// 主队二节得分
	private int guestTwo;		// 客队二节得分
	private int homeThree;		// 主队三节得分(下半场）
	private int guestThree;		// 客队三节得分(下半场）
	private int homeFour;		// 主队四节得分
	private int guestFour;		// 客队四节得分
	private int addTime;		// 加时数 ，即几个加时
	private int homeAddTime1;	// 主队1'ot得分
	private int guestAddTime1;	// 客队1'ot得分
	private int homeAddTime2;	// 主队2'ot得分
	private int guestAddTime2;	// 客队2'ot得分
	private int homeAddTime3;	// 主队3'ot得分
	private int guestAddTime3;	// 客队3'ot得分
	private boolean addTechnic;	// 是否有技术统计
	private String tv;			// 电视直播
	private String explainContent;		// 备注，直播内容
	private String explain2;	// 赔率信息,可不理
	private int cupLeague;		// 1联赛，2杯赛
	private BigDecimal homeWinOdds;		// 主胜赔率
	private BigDecimal guestWinOdds;	// 客胜赔率
	private long cupLeagueId;			// 赛事类型ID（联赛/杯赛ID）
	private String season = "";				// 赛季
	private String matchType = "";			// 类型，如季前赛，常规赛，季后赛
	private String matchClass = "";			// 比赛分类，例如第一圈，只有杯赛或季后赛才有数据
	private String matchSubClass = "";		// 比赛子分类，例如A组，只有杯赛才有数据
	private Boolean isNeutral;			// 是否中立场
	private String middleMatchState;//中立场状态：1表示中立场，0表示非中立场
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSclassType() {
		return sclassType;
	}
	public void setSclassType(int sclassType) {
		this.sclassType = sclassType;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getMatchState() {
		return matchState;
	}
	public void setMatchState(int matchState) {
		this.matchState = matchState;
	}
	public int getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
	public long getHomeTeamId() {
		return homeTeamId;
	}
	public void setHomeTeamId(long homeTeamId) {
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
	public long getGuestTeamId() {
		return guestTeamId;
	}
	public void setGuestTeamId(long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}
	public String getGuestTeam() {
		return guestTeam;
	}
	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
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
	public int getHomeScore() {
		return homeScore;
	}
	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}
	public int getGuestScore() {
		return guestScore;
	}
	public void setGuestScore(int guestScore) {
		this.guestScore = guestScore;
	}
	public int getHomeOne() {
		return homeOne;
	}
	public void setHomeOne(int homeOne) {
		this.homeOne = homeOne;
	}
	public int getGuestOne() {
		return guestOne;
	}
	public void setGuestOne(int guestOne) {
		this.guestOne = guestOne;
	}
	public int getHomeTwo() {
		return homeTwo;
	}
	public void setHomeTwo(int homeTwo) {
		this.homeTwo = homeTwo;
	}
	public int getGuestTwo() {
		return guestTwo;
	}
	public void setGuestTwo(int guestTwo) {
		this.guestTwo = guestTwo;
	}
	public int getHomeThree() {
		return homeThree;
	}
	public void setHomeThree(int homeThree) {
		this.homeThree = homeThree;
	}
	public int getGuestThree() {
		return guestThree;
	}
	public void setGuestThree(int guestThree) {
		this.guestThree = guestThree;
	}
	public int getHomeFour() {
		return homeFour;
	}
	public void setHomeFour(int homeFour) {
		this.homeFour = homeFour;
	}
	public int getGuestFour() {
		return guestFour;
	}
	public void setGuestFour(int guestFour) {
		this.guestFour = guestFour;
	}
	public int getAddTime() {
		return addTime;
	}
	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}
	public int getHomeAddTime1() {
		return homeAddTime1;
	}
	public void setHomeAddTime1(int homeAddTime1) {
		this.homeAddTime1 = homeAddTime1;
	}
	public int getGuestAddTime1() {
		return guestAddTime1;
	}
	public void setGuestAddTime1(int guestAddTime1) {
		this.guestAddTime1 = guestAddTime1;
	}
	public int getHomeAddTime2() {
		return homeAddTime2;
	}
	public void setHomeAddTime2(int homeAddTime2) {
		this.homeAddTime2 = homeAddTime2;
	}
	public int getGuestAddTime2() {
		return guestAddTime2;
	}
	public void setGuestAddTime2(int guestAddTime2) {
		this.guestAddTime2 = guestAddTime2;
	}
	public int getHomeAddTime3() {
		return homeAddTime3;
	}
	public void setHomeAddTime3(int homeAddTime3) {
		this.homeAddTime3 = homeAddTime3;
	}
	public int getGuestAddTime3() {
		return guestAddTime3;
	}
	public void setGuestAddTime3(int guestAddTime3) {
		this.guestAddTime3 = guestAddTime3;
	}
	public boolean isAddTechnic() {
		return addTechnic;
	}
	public void setAddTechnic(boolean addTechnic) {
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
	public void setExplainContent(String explain) {
		this.explainContent = explain;
	}
	public String getExplain2() {
		return explain2;
	}
	public void setExplain2(String explain2) {
		this.explain2 = explain2;
	}
	public int getCupLeague() {
		return cupLeague;
	}
	public void setCupLeague(int cupLeague) {
		this.cupLeague = cupLeague;
	}
	public BigDecimal getHomeWinOdds() {
		return homeWinOdds;
	}
	public void setHomeWinOdds(BigDecimal homeWinOdds) {
		this.homeWinOdds = homeWinOdds;
	}
	public BigDecimal getGuestWinOdds() {
		return guestWinOdds;
	}
	public void setGuestWinOdds(BigDecimal guestWinOdds) {
		this.guestWinOdds = guestWinOdds;
	}
	public long getCupLeagueId() {
		return cupLeagueId;
	}
	public void setCupLeagueId(long cupLeagueId) {
		this.cupLeagueId = cupLeagueId;
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
	public Boolean getIsNeutral() {
		return isNeutral;
	}
	public void setIsNeutral(Boolean isNeutral) {
		this.isNeutral = isNeutral;
	}
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
	public String getMatchTimeStr() {
		return matchTimeStr;
	}
	public void setMatchTimeStr(String matchTimeStr) {
		this.matchTimeStr = matchTimeStr;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	public String getNameF() {
		return nameF;
	}
	public void setNameF(String nameF) {
		this.nameF = nameF;
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
	public String getMiddleMatchState() {
		return middleMatchState;
	}
	public void setMiddleMatchState(String middleMatchState) {
		this.middleMatchState = middleMatchState;
	}
}
