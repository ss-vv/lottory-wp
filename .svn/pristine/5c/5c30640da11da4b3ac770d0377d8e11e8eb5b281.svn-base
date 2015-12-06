package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FBEventContentData {
	//赛程ID
	private String matchId;
	
	//主客队标志 1：主队，0：客队事件
	private String homeAwayTeamFlag;
	//事件类型  1、入球 2、红牌  3、黄牌   7、点球  8、乌龙  9、两黄变红
	private String eventType;
	//事件发生时间(第多少分钟)
	private String timeInMin;
	//球员名字
	private String fbPlayerName;
	//球名id
	private String ballId;
	//简体球员名
	private String fbplayerChineseName;
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getHomeAwayTeamFlag() {
		return homeAwayTeamFlag;
	}
	public void setHomeAwayTeamFlag(String homeAwayTeamFlag) {
		this.homeAwayTeamFlag = homeAwayTeamFlag;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getFbPlayerName() {
		return fbPlayerName;
	}
	public void setFbPlayerName(String fbPlayerName) {
		this.fbPlayerName = fbPlayerName;
	}
	public String getBallId() {
		return ballId;
	}
	public void setBallId(String ballId) {
		this.ballId = ballId;
	}
	public String getFbplayerChineseName() {
		return fbplayerChineseName;
	}
	public void setFbplayerChineseName(String fbplayerChineseName) {
		this.fbplayerChineseName = fbplayerChineseName;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	public String getTimeInMin() {
		return timeInMin;
	}
	public void setTimeInMin(String timeInMin) {
		this.timeInMin = timeInMin;
	}
	
	
}
