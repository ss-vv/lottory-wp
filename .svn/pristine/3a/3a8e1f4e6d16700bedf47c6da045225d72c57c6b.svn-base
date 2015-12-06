package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 篮球比赛信息
 * 
 * @author Yang Bo
 */
@Entity
@Table(name = "md_bb_match_base")
public class BBMatchInfoPO {
	@Id
	private long id;			// 赛事ID
	private String bsId;        //球探网比赛id
	private int source;         //数据来源
	private int processStatus;  //处理状态
	private String leagueId;    //联赛id
	private Integer seasonId;       //赛季轮数
	private String jingCaiId;    //竞猜id
	private String name;		// 简体联赛名
	private String nameF;		// 繁体联赛名
	private int sclassType;		// 分几节进行，2:上下半场，4：分4小节
	private String color;		// 颜色值
	private Date matchTime;		// 开赛时间, 03月04日<br>09:00，年份在后面字段获取
	@Transient
	private String matchTimeStr;// 开赛时间串
	@Transient
	private String year;		// 年份
	private Integer matchState;		// 状态:0:未开赛,1:一节,2:二节,5:1'OT，以此类推，-1:完场, -2:待定,-3:中断,-4:取消,-5:推迟,50中场
	private Integer remainTime;		// 小节剩余时间
//	private long homeTeamId;	// 主队ID
	private String homeTeam;	// 主队名，简体
	private String homeTeamF;	// 主队名，繁体
	private String homeTeamE;	// 主队名，英文
//	private long guestTeamId;	// 客队ID
	private String guestTeam;	// 客队名，简体
	private String guestTeamF;	// 客队名，繁体
	private String guestTeamE;	// 客队名，英文
	private Integer homeScore;		// 主队得分
	private Integer guestScore;		// 客队得分
	private Integer homeOne;		// 主队一节得分(上半场)
	private Integer guestOne;		// 客队一节得分（上半场）
	private Integer homeTwo;		// 主队二节得分
	private Integer guestTwo;		// 客队二节得分
	private Integer homeThree;		// 主队三节得分(下半场）
	private Integer guestThree;		// 客队三节得分(下半场）
	private Integer homeFour;		// 主队四节得分
	private Integer guestFour;		// 客队四节得分
	private Integer addTime;		// 加时数 ，即几个加时
	private Integer homeAddTime1;	// 主队1'ot得分
	private Integer guestAddTime1;	// 客队1'ot得分
	private Integer homeAddTime2;	// 主队2'ot得分
	private Integer guestAddTime2;	// 客队2'ot得分
	private Integer homeAddTime3;	// 主队3'ot得分
	private Integer guestAddTime3;	// 客队3'ot得分
	private boolean addTechnic;	// 是否有技术统计
	private String tv;			// 电视直播
	private String explainContent;		// 备注，直播内容
	private String explain2;	// 赔率信息,可不理
	private int cupLeague;		// 1联赛，2杯赛
	private BigDecimal homeWinOdds;		// 主胜赔率
	private BigDecimal guestWinOdds;	// 客胜赔率
//	private long cupLeagueId;			// 赛事类型ID（联赛/杯赛ID）
	private String season;				// 赛季
	private String matchType;			// 类型，如季前赛，常规赛，季后赛
	private String matchClass;			// 比赛分类，例如第一圈，只有杯赛或季后赛才有数据
	private String matchSubClass;		// 比赛子分类，例如A组，只有杯赛才有数据
	private Boolean isNeutral;			// 是否中立场
	
	private String homeTeamRank;//主队排名
	private String guestTeamRank;//客队排名
	
	private Date createTime;
	private Date updateTime;
	private Integer letBallNum;      //让球数
	private String kind;         //比赛类型 1常规赛 2 季后赛 3 季前赛
	private String subLeagueId;  //子联赛id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBsId() {
		return bsId;
	}
	public void setBsId(String bsId) {
		this.bsId = bsId;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}
	public String getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	public String getJingCaiId() {
		return jingCaiId;
	}
	public void setJingCaiId(String jingCaiId) {
		this.jingCaiId = jingCaiId;
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
	public Integer getMatchState() {
		return matchState;
	}
	public void setMatchState(Integer matchState) {
		this.matchState = matchState;
	}
	public Integer getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(Integer remainTime) {
		this.remainTime = remainTime;
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
	public Integer getGuestAddTime1() {
		return guestAddTime1;
	}
	public void setGuestAddTime1(Integer guestAddTime1) {
		this.guestAddTime1 = guestAddTime1;
	}
	public Integer getHomeAddTime2() {
		return homeAddTime2;
	}
	public void setHomeAddTime2(Integer homeAddTime2) {
		this.homeAddTime2 = homeAddTime2;
	}
	public Integer getGuestAddTime2() {
		return guestAddTime2;
	}
	public void setGuestAddTime2(Integer guestAddTime2) {
		this.guestAddTime2 = guestAddTime2;
	}
	public Integer getHomeAddTime3() {
		return homeAddTime3;
	}
	public void setHomeAddTime3(Integer homeAddTime3) {
		this.homeAddTime3 = homeAddTime3;
	}
	public Integer getGuestAddTime3() {
		return guestAddTime3;
	}
	public void setGuestAddTime3(Integer guestAddTime3) {
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
	public void setExplainContent(String explainContent) {
		this.explainContent = explainContent;
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
	public Integer getLetBallNum() {
		return letBallNum;
	}
	public void setLetBallNum(Integer letBallNum) {
		this.letBallNum = letBallNum;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getSubLeagueId() {
		return subLeagueId;
	}
	public void setSubLeagueId(String subLeagueId) {
		this.subLeagueId = subLeagueId;
	}
}
