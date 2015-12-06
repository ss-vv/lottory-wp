package com.unison.lottery.api.callBack.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BasketballMatchMessage extends MathMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String remainTime;//小节剩余时间
	
	private int homeOne,guestOne;//主客队一节得分
	
	private int homeTwo,guestTwo;//主客队二节得分
	
	private int homeThree,guestThree;//主客队三节得分
	
	private int homeFour,guestFour;//主客队三节得分
	
	private String explainContent;//直播内容
	
	private int homeAddTimeScore,guestAddTimeScore;//主客队加时得分数
	
	private String homeTeamName;
	
	
	private String guestTeamName;
	
	
	private int homeScore;
	
	
	private int guestScore;
	
	private int homeTeamHalfScore;//主队半场得分
	private int guestTeamHalfScore;//客队半场得分
	
	private String homeTeamPosition;//主队排名
	private String guestTeamPosition;//客队排名
	
	
	private String result;
	
	private String state="0";//比赛状态
	
	
	private String matchId;//比赛id

	private static final String DEFAULT_MATCH_CHANNEL_PREFIX = "/public/match/";

	private String matchChannelPrefix=DEFAULT_MATCH_CHANNEL_PREFIX;
	
	private Date scheduleMatchTime;//预定比赛时间
	
	private boolean shouldUnsubscribe;
	
	private String matchCode;//赛事编号
	
	private String matchTime;
	
	private String leagueShortName;
	
	private String leagueColor;
	
	public String getLeagueColor() {
		return leagueColor;
	}
	public void setLeagueColor(String leagueColor) {
		this.leagueColor = leagueColor;
	}
	public String getLeagueShortName() {
		return leagueShortName;
	}
	public void setLeagueShortName(String leagueShortName) {
		this.leagueShortName = leagueShortName;
	}
	public String getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
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

	public String getExplainContent() {
		return explainContent;
	}

	public void setExplainContent(String explainContent) {
		this.explainContent = explainContent;
	}

	public int getHomeAddTimeScore() {
		return homeAddTimeScore;
	}

	public void setHomeAddTimeScore(int homeAddTimeScore) {
		this.homeAddTimeScore = homeAddTimeScore;
	}

	public int getGuestAddTimeScore() {
		return guestAddTimeScore;
	}

	public void setGuestAddTimeScore(int guestAddTimeScore) {
		this.guestAddTimeScore = guestAddTimeScore;
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

	public int getHomeTeamHalfScore() {
		return homeTeamHalfScore;
	}

	public void setHomeTeamHalfScore(int homeTeamHalfScore) {
		this.homeTeamHalfScore = homeTeamHalfScore;
	}

	public int getGuestTeamHalfScore() {
		return guestTeamHalfScore;
	}

	public void setGuestTeamHalfScore(int guestTeamHalfScore) {
		this.guestTeamHalfScore = guestTeamHalfScore;
	}

	public String getHomeTeamPosition() {
		return homeTeamPosition;
	}

	public void setHomeTeamPosition(String homeTeamPosition) {
		this.homeTeamPosition = homeTeamPosition;
	}

	public String getGuestTeamPosition() {
		return guestTeamPosition;
	}

	public void setGuestTeamPosition(String guestTeamPosition) {
		this.guestTeamPosition = guestTeamPosition;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getMatchChannelPrefix() {
		return matchChannelPrefix;
	}

	public void setMatchChannelPrefix(String matchChannelPrefix) {
		this.matchChannelPrefix = matchChannelPrefix;
	}

	public Date getScheduleMatchTime() {
		return scheduleMatchTime;
	}

	public void setScheduleMatchTime(Date scheduleMatchTime) {
		this.scheduleMatchTime = scheduleMatchTime;
	}

	public boolean isShouldUnsubscribe() {
		return shouldUnsubscribe;
	}

	public void setShouldUnsubscribe(boolean shouldUnsubscribe) {
		this.shouldUnsubscribe = shouldUnsubscribe;
	}
}
