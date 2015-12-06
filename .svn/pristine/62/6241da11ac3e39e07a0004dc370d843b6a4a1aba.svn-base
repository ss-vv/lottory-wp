package com.unison.lottery.api.callBack.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FootballMatchMessage extends MathMessage implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int guestTeamRed;
	private int homeTeamRed;
	private int homeTeamYellow;
	private int guestTeamYellow;
	private Date halfStartTime;//上半场为上半场开场时间、下半场为下半场开场时间
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
	private boolean shouldUnsubscribe;
	private Date scheduleMatchTime;//预定比赛时间
	private String matchCode;//赛事编号
	private String matchTime;
	private String liveContent;
	
	public String getLiveContent() {
		return liveContent;
	}
	public void setLiveContent(String liveContent) {
		this.liveContent = liveContent;
	}
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
	public int getGuestTeamRed() {
		return guestTeamRed;
	}
	public void setGuestTeamRed(int guestTeamRed) {
		this.guestTeamRed = guestTeamRed;
	}
	public int getHomeTeamRed() {
		return homeTeamRed;
	}
	public void setHomeTeamRed(int homeTeamRed) {
		this.homeTeamRed = homeTeamRed;
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
	public Date getHalfStartTime() {
		return halfStartTime;
	}
	public void setHalfStartTime(Date halfStartTime) {
		this.halfStartTime = halfStartTime;
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
	public boolean isShouldUnsubscribe() {
		return shouldUnsubscribe;
	}
	public void setShouldUnsubscribe(boolean shouldUnsubscribe) {
		this.shouldUnsubscribe = shouldUnsubscribe;
	}
	public Date getScheduleMatchTime() {
		return scheduleMatchTime;
	}
	public void setScheduleMatchTime(Date scheduleMatchTime) {
		this.scheduleMatchTime = scheduleMatchTime;
	}
	
}
