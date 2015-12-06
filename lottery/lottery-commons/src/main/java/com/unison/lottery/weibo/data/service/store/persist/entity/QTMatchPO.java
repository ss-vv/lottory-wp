package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * 球探赛事记录表
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "md_qt_match_base")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QTMatchPO implements Serializable {

	private static final long serialVersionUID = -1305325293498995804L;

	@Id
	private long id;
	private Integer seasonId;   //赛季id
	private String source;  //数据来源1 球探客户端
	private int processStatus;  //处理状态 0未处理 1 已转换
	private String season;      //赛季
	private int matchResult;    //全场赛果 310胜平负   -1无结果
	private int halfMatchResult; //半场赛果  310  -1
//	private int homeTeamHalfScore; //主队半场得分
//	private int guestTeamHalfScore;//客队半场得分
    private int levelType;        //重要程度 
    private int neutralType;     //是否是中立场0 否  1 是
    private int czType;         //是否是足彩比分
    private String country;     //国家名称
    private String round;       //轮次/分组  例4/8强
    private String city;       //比赛地点
    private String weatherLogo;  //天气图标
    private String weather;      //天气
    private String temperature;   //温度
    private String groupId;      //小组分组组名
    private String court;       //比赛场地
    private double lordOdds;     //主胜赔率
    private double flatOdds;     //平赔率
    private double handiCap;     //盘口
    private double negativeOdds;  //主负赔率
    private String jingCaiId;     //竞彩id
	private long leagueId;
	private String leagueShortName;
	private String color;
//	private long homeTeamId;
//	private long guestTeamId;
	private String homeTeamName;
	private String guestTeamName;
	// 是否是足彩比分 0：非足彩，1:胜负彩，2：北京单场，3：胜负彩+北京单场,-1:未知
	private Integer fbLotteryScoreType;
	// 是否有阵容: 1为有
	private int  haveBattleArray;  //是否有阵容 1 有
	// 比赛说明
	private String matchMessage;
	private String score;
	private String halfScore;
	@Type(type = "timestamp")
	private Date matchTime;
	@Type(type = "timestamp")
	private Date halfStartTime;
	private Integer homeTeamPosition;
	private Integer guestTeamPosition;
	private int matchStatus;
	private int homeTeamScore;
	@Column(name="homeTeamHalfScore")
	private Integer homeHalfScore;
	private int guestTeamScore;
	@Column(name="guestTeamHalfScore")
	private Integer guestHalfScore;
	private String televison;
	//private Boolean neutral;
//	private BigDecimal importance;
	private int homeTeamRed;
	private int guestTeamRed;
	private int homeTeamYellow;
	private int guestTeamYellow;
	@Type(type = "timestamp")
	private Date createTime;
	@Type(type = "timestamp")
	private Date updateTime;
	//private Long countryID;
	//private int alreadyCrawl;
	
	public Integer getHomeHalfScore() {
		return homeHalfScore;
	}
	public void setHomeHalfScore(Integer homeHalfScore) {
		this.homeHalfScore = homeHalfScore;
	}
	public Integer getGuestHalfScore() {
		return guestHalfScore;
	}
	public void setGuestHalfScore(Integer homeHalfScore) {
		this.guestHalfScore = homeHalfScore;
	}
	public String getMatchMessage() {
		return matchMessage;
	}
	public void setMatchMessage(String matchMessage) {
		this.matchMessage = matchMessage;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(long leagueId) {
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
//	public long getHomeTeamId() {
//		return homeTeamId;
//	}
//	public void setHomeTeamId(long homeTeamId) {
//		this.homeTeamId = homeTeamId;
//	}
//	public long getGuestTeamId() {
//		return guestTeamId;
//	}
//	public void setGuestTeamId(long guestTeamId) {
//		this.guestTeamId = guestTeamId;
//	}
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
	public int getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(int matchStatus) {
		this.matchStatus = matchStatus;
	}
	public int getHomeTeamScore() {
		return homeTeamScore;
	}
	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}
	public int getGuestTeamScore() {
		return guestTeamScore;
	}
	public void setGuestTeamScore(int guestTeamScore) {
		this.guestTeamScore = guestTeamScore;
	}
	public String getTelevison() {
		return televison;
	}
	public void setTelevison(String televison) {
		this.televison = televison;
	}
//	public BigDecimal getImportance() {
//		return importance;
//	}
//	public void setImportance(BigDecimal importance) {
//		this.importance = importance;
//	}
	public int getHomeTeamRed() {
		return homeTeamRed;
	}
	public void setHomeTeamRed(int homeTeamRed) {
		this.homeTeamRed = homeTeamRed;
	}
	public int getGuestTeamRed() {
		return guestTeamRed;
	}
	public void setGuestTeamRed(int guestTeamRed) {
		this.guestTeamRed = guestTeamRed;
	}
	public int getHomeTeamYellow() {
		return homeTeamYellow;
	}
	public void setHomeTeamYellow(int homeTeamYellow) {
		this.homeTeamYellow = homeTeamYellow;
	}
	public int getGuestTeamYellow() {
		return guestTeamYellow;
	}
	public void setGuestTeamYellow(int guestTeamYellow) {
		this.guestTeamYellow = guestTeamYellow;
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
	public Integer getFbLotteryScoreType() {
		return fbLotteryScoreType;
	}
	public void setFbLotteryScoreType(Integer fbLotteryScoreType) {
		this.fbLotteryScoreType = fbLotteryScoreType;
	}
	public int getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public int getMatchResult() {
		return matchResult;
	}
	public void setMatchResult(int matchResult) {
		this.matchResult = matchResult;
	}
	public int getHalfMatchResult() {
		return halfMatchResult;
	}
	public void setHalfMatchResult(int halfMatchResult) {
		this.halfMatchResult = halfMatchResult;
	}
//	public int getHomeTeamHalfScore() {
//		return homeTeamHalfScore;
//	}
//	public void setHomeTeamHalfScore(int homeTeamHalfScore) {
//		this.homeTeamHalfScore = homeTeamHalfScore;
//	}
//	public int getGuestTeamHalfScore() {
//		return guestTeamHalfScore;
//	}
//	public void setGuestTeamHalfScore(int guestTeamHalfScore) {
//		this.guestTeamHalfScore = guestTeamHalfScore;
//	}
	public int getLevelType() {
		return levelType;
	}
	public void setLevelType(int levelType) {
		this.levelType = levelType;
	}
	public int getNeutralType() {
		return neutralType;
	}
	public void setNeutralType(int neutralType) {
		this.neutralType = neutralType;
	}
	public int getCzType() {
		return czType;
	}
	public void setCzType(int czType) {
		this.czType = czType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWeatherLogo() {
		return weatherLogo;
	}
	public void setWeatherLogo(String weatherLogo) {
		this.weatherLogo = weatherLogo;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
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
	public double getLordOdds() {
		return lordOdds;
	}
	public void setLordOdds(double lordOdds) {
		this.lordOdds = lordOdds;
	}
	public double getFlatOdds() {
		return flatOdds;
	}
	public void setFlatOdds(double flatOdds) {
		this.flatOdds = flatOdds;
	}
	public double getHandiCap() {
		return handiCap;
	}
	public void setHandiCap(double handiCap) {
		this.handiCap = handiCap;
	}
	public double getNegativeOdds() {
		return negativeOdds;
	}
	public void setNegativeOdds(double negativeOdds) {
		this.negativeOdds = negativeOdds;
	}
	public String getJingCaiId() {
		return jingCaiId;
	}
	public void setJingCaiId(String jingCaiId) {
		this.jingCaiId = jingCaiId;
	}
	public int getHaveBattleArray() {
		return haveBattleArray;
	}
	public void setHaveBattleArray(int haveBattleArray) {
		this.haveBattleArray = haveBattleArray;
	}

}
