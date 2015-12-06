package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BFChangeContentData {

	private String matchId;
	
	private String matchStatus;
	
	private String homeScores;
	
	private String awayScores;
	
	private String homeHalfScores;
	
	private String awayHalfScores;
	
	private String homeRedCards;
	
	private String awayRedCards;
	
	private String matchTimeWithHourAndMin;
	
	private Date matchBeginTime;
	
	private Date matchBeginTimeInGMT0;
	
	private String matchMessage;
	
	private String isHaveBattleArray;
	
	private String homeYellowCards;
	
	private String awayYellowCards;
	
	private String matchTimeWithMonthAndDay;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}

	public String getHomeScores() {
		return homeScores;
	}

	public void setHomeScores(String homeScores) {
		this.homeScores = homeScores;
	}

	public String getAwayScores() {
		return awayScores;
	}

	public void setAwayScores(String awayScores) {
		this.awayScores = awayScores;
	}

	public String getHomeHalfScores() {
		return homeHalfScores;
	}

	public void setHomeHalfScores(String homeHalfScores) {
		this.homeHalfScores = homeHalfScores;
	}

	public String getAwayHalfScores() {
		return awayHalfScores;
	}

	public void setAwayHalfScores(String awayHalfScores) {
		this.awayHalfScores = awayHalfScores;
	}

	public String getHomeRedCards() {
		return homeRedCards;
	}

	public void setHomeRedCards(String homeRedCards) {
		this.homeRedCards = homeRedCards;
	}

	public String getAwayRedCards() {
		return awayRedCards;
	}

	public void setAwayRedCards(String awayRedCards) {
		this.awayRedCards = awayRedCards;
	}

	public Date getMatchBeginTime() {
		return matchBeginTime;
	}

	public void setMatchBeginTime(Date matchBeginTime) {
		this.matchBeginTime = matchBeginTime;
	}

	public String getMatchMessage() {
		return matchMessage;
	}

	public void setMatchMessage(String matchMessage) {
		this.matchMessage = matchMessage;
	}

	public String getIsHaveBattleArray() {
		return isHaveBattleArray;
	}

	public void setIsHaveBattleArray(String isHaveBattleArray) {
		this.isHaveBattleArray = isHaveBattleArray;
	}

	public String getHomeYellowCards() {
		return homeYellowCards;
	}

	public void setHomeYellowCards(String homeYellowCards) {
		this.homeYellowCards = homeYellowCards;
	}

	public String getAwayYellowCards() {
		return awayYellowCards;
	}

	public void setAwayYellowCards(String awayYellowCards) {
		this.awayYellowCards = awayYellowCards;
	}

	public String getMatchTimeWithHourAndMin() {
		return matchTimeWithHourAndMin;
	}

	public void setMatchTimeWithHourAndMin(String matchTimeWithHourAndMin) {
		this.matchTimeWithHourAndMin = matchTimeWithHourAndMin;
	}

	public String getMatchTimeWithMonthAndDay() {
		return matchTimeWithMonthAndDay;
	}

	public void setMatchTimeWithMonthAndDay(String matchTimeWithMonthAndDay) {
		this.matchTimeWithMonthAndDay = matchTimeWithMonthAndDay;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public Date getMatchBeginTimeInGMT0() {
		return matchBeginTimeInGMT0;
	}

	public void setMatchBeginTimeInGMT0(Date matchBeginTimeInGMT0) {
		this.matchBeginTimeInGMT0 = matchBeginTimeInGMT0;
	}
	
	
	
}
