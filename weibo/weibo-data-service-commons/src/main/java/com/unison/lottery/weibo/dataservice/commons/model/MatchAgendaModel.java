package com.unison.lottery.weibo.dataservice.commons.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.unison.lottery.weibo.dataservice.commons.util.XmlDateAdapter;

public class MatchAgendaModel {

	// 比赛ID
	@XmlElement(name="ID")
	public String matchId;
	// 颜色值
	@XmlElement(name="color")
	public String colour;
	// 赛事类型 简体名
	// 赛事类型 繁体名
	// 赛事类型 英文名
	@XmlElement(name="league")
	public String league;
	private BaseNameModel matchTypeName;
	//联赛id
	@XmlElement(name="leagueID")
	public String leagueID;
	// 类型：1联赛，2杯赛
	@XmlElement(name="kind")
	public String kind;
	// 主队 简体名
	// 主队 繁体名
	// 主队 英文名
	@XmlElement(name="home")
	public String home;
	private BaseNameModel homeName;
	// 客队 简体名
	// 客队 繁体名
	// 客队 英文名
	@XmlElement(name="away")
	public String away;
	private BaseNameModel awayName;
	// 比赛时间 只有小时和分数 20:30 格式
	@XmlElement(name="time")
	public String matchTimeWithHourAndMin;
	// 开场时间 (上半场时为开上半场的时间,下半场时为开下半场的时间）js日期时间格式
	@XmlElement(name="time2")
	@XmlJavaTypeAdapter(value=XmlDateAdapter.class)
	public Date matchBeginTime;
	private Date matchBeginTimeInGMT0;
	// 比赛状态 0:未开,1:上半场,2:中场,3:下半场,4,加时，-11:待定,-12:腰斩,-13:中断,-14:推迟,-1:完场，-10取消
	@XmlElement(name="state")
	public String matchStatus;
	// 主队比分
	@XmlElement(name="homeScore")
	public String homeScores;
	// 客队比分
	@XmlElement(name="awayScore")
	public String awayScores;
	// 主队上半场比分
	@XmlElement(name="bc1")
	public String homeHalfScores;
	// 客队上半场比分
	@XmlElement(name="bc2")
	public String awayHalfScores;
	// 主队红牌
	@XmlElement(name="red1")
	public String homeRedCards;
	// 客队红牌
	@XmlElement(name="red2")
	public String awayRedCards;
	// 主队黄牌
	@XmlElement(name="yellow1")
	public String homeYellowCards;
	// 客队黄牌
	@XmlElement(name="yellow2")
	public String awayYellowCards;
	// 主队排名
	@XmlElement(name="order1")
	public String homeRanking;
	// 客队排名
	@XmlElement(name="order2")
	public String awayRanking;
	// 是否是精简版比分 0 /1表示，1表示精简版（即重要赛事）
	private String isAllOrSimple;
	// 是否是足彩比分 0：非足彩，1:胜负彩，2：北京单场，3：胜负彩+北京单场
	private String isFootballLotteryScore;
	// 电视直播频道: CCTV5 广东体育
	@XmlElement(name="tv")
	public String TVLive;
	// 是否有阵容: 1为有
	@XmlElement(name="lineup")
	public String isHaveBattleArray;
	// 是否是走地比赛 True or False
	private boolean zouDi;
	// 皇冠初盘
	private String crownPrimaryDisc;
	// 比赛说明
	@XmlElement(name="explain")
	public String matchMessage;
	//是否中立场true or false
	@XmlElement(name="zl")
	public String isCenter;
	// 联赛资料库超链接 (可不理会)
	private String leagueDataUrl;
	// 空(有变动)
	private String customOne;
	// 空(有变动)
	private String customTwo;
	// 空(有变动)
	private String customThree;
	// 空(有变动)
	private String customFour;
	// 比赛日期 m-d， 月-日
	private String matchTimeWithMonthAndDay;
	// 主队ID
	private String homeTeamId;
	// 客队ID
	private String awayTeamId;
	// 空(有变动)
	private String  customFive;
	// 国家ID (有变动)
	private String countryId;

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getIsFootballLotteryScore() {
		return isFootballLotteryScore;
	}
	public String getIsAllOrSimple() {
		return isAllOrSimple;
	}
	public void setIsAllOrSimple(String isAllOrSimple) {
		this.isAllOrSimple = isAllOrSimple;
	}
	public void setIsFootballLotteryScore(String isFootballLotteryScore) {
		this.isFootballLotteryScore = isFootballLotteryScore;
	}


	public BaseNameModel getMatchTypeName() {
		return matchTypeName;
	}

	public void setMatchTypeName(BaseNameModel matchTypeName) {
		this.matchTypeName = matchTypeName;
	}

	public BaseNameModel getHomeName() {
		return homeName;
	}

	public void setHomeName(BaseNameModel homeName) {
		this.homeName = homeName;
	}

	public BaseNameModel getAwayName() {
		return awayName;
	}

	public void setAwayName(BaseNameModel awayName) {
		this.awayName = awayName;
	}

	public Date getMatchBeginTimeInGMT0() {
		return matchBeginTimeInGMT0;
	}

	public void setMatchBeginTimeInGMT0(Date matchBeginTimeInGMT0) {
		this.matchBeginTimeInGMT0 = matchBeginTimeInGMT0;
	}

	public boolean isZouDi() {
		return zouDi;
	}

	public void setZouDi(boolean zouDi) {
		this.zouDi = zouDi;
	}

	public String getCrownPrimaryDisc() {
		return crownPrimaryDisc;
	}

	public void setCrownPrimaryDisc(String crownPrimaryDisc) {
		this.crownPrimaryDisc = crownPrimaryDisc;
	}

	public String getLeagueDataUrl() {
		return leagueDataUrl;
	}

	public void setLeagueDataUrl(String leagueDataUrl) {
		this.leagueDataUrl = leagueDataUrl;
	}

	public String getCustomOne() {
		return customOne;
	}

	public void setCustomOne(String customOne) {
		this.customOne = customOne;
	}

	public String getCustomTwo() {
		return customTwo;
	}

	public void setCustomTwo(String customTwo) {
		this.customTwo = customTwo;
	}

	public String getCustomThree() {
		return customThree;
	}

	public void setCustomThree(String customThree) {
		this.customThree = customThree;
	}

	public String getCustomFour() {
		return customFour;
	}

	public void setCustomFour(String customFour) {
		this.customFour = customFour;
	}

	public String getMatchTimeWithMonthAndDay() {
		return matchTimeWithMonthAndDay;
	}

	public void setMatchTimeWithMonthAndDay(String matchTimeWithMonthAndDay) {
		this.matchTimeWithMonthAndDay = matchTimeWithMonthAndDay;
	}

	public String getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getAwayTeamId() {
		return awayTeamId;
	}

	public void setAwayTeamId(String awayTeamId) {
		this.awayTeamId = awayTeamId;
	}

	public String getCustomFive() {
		return customFive;
	}

	public void setCustomFive(String customFive) {
		this.customFive = customFive;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	
	
	
}
