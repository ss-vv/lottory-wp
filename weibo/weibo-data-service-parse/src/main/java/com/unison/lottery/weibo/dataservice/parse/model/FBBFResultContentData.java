package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FBBFResultContentData {
	
	
	//比赛ID
		private String matchId;
		//颜色值
		private String colour;
		//联赛国语名,联赛繁体名,联赛英文名,联赛ID,是否精简版( 0:完全版、1:精简)
		private League league;
		//比赛时间</d>
		private Date matchTime;
		//比赛时间(标准时间)
		private Date matchTimeInGMT0;
		//联赛子类型</e>
		private String leagueSubtype;
		//比赛状态</f>
		private String matchStatus;
		//主队国语名, 主队繁体名, 主队英文名, 主队ID
		private BaseName homeName;
		private String homeTeamId;
		//客队国语名, 客队繁体名, 客队英文名, 客队ID </i>
		private BaseName awayName;
		private String awayTeamId;
		//主队比分</j>
		private String homeScores;
		//客队比分</k>
		private String awayScores;
		//主队半场比分</l>
		private String homeHalfScores;
		//客队半场比分</m>
		private String awayHalfScores;
		//主队红牌</n>
		private String homeRedCard;
		//客队红牌</o>
		private String awayRedCard;
		//主队排名</p>
		private String homeRankings;
		//客队排名</q>
		private String awayRankings;
		//赛事说明
		private String matchMessage;
		//轮次/分组名
		private String groupName;
		//比赛地点
		private String matchAddress;
		//天气图标
		private String weatherImg;
		//天气
		private String weather;
		//温度
		private String temperature;
		//赛季
		private String season;
		//小组分组
		private String teamGroup;
		//是否中立场
		private String isNeutrality;
		public String getMatchId() {
			return matchId;
		}
		public void setMatchId(String matchId) {
			this.matchId = matchId;
		}
		public String getColour() {
			return colour;
		}
		public void setColour(String colour) {
			this.colour = colour;
		}
		public League getLeague() {
			return league;
		}
		public void setLeague(League league) {
			this.league = league;
		}
		public Date getMatchTime() {
			return matchTime;
		}
		public void setMatchTime(Date matchTime) {
			this.matchTime = matchTime;
		}
		public String getLeagueSubtype() {
			return leagueSubtype;
		}
		public void setLeagueSubtype(String leagueSubtype) {
			this.leagueSubtype = leagueSubtype;
		}
		public String getMatchStatus() {
			return matchStatus;
		}
		public void setMatchStatus(String matchStatus) {
			this.matchStatus = matchStatus;
		}
		public BaseName getHomeName() {
			return homeName;
		}
		public void setHomeName(BaseName homeName) {
			this.homeName = homeName;
		}
		public String getHomeTeamId() {
			return homeTeamId;
		}
		public void setHomeTeamId(String homeTeamId) {
			this.homeTeamId = homeTeamId;
		}
		public BaseName getAwayName() {
			return awayName;
		}
		public void setAwayName(BaseName awayName) {
			this.awayName = awayName;
		}
		public String getAwayTeamId() {
			return awayTeamId;
		}
		public void setAwayTeamId(String awayTeamId) {
			this.awayTeamId = awayTeamId;
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
		public String getHomeRedCard() {
			return homeRedCard;
		}
		public void setHomeRedCard(String homeRedCard) {
			this.homeRedCard = homeRedCard;
		}
		public String getAwayRedCard() {
			return awayRedCard;
		}
		public void setAwayRedCard(String awayRedCard) {
			this.awayRedCard = awayRedCard;
		}
		public String getHomeRankings() {
			return homeRankings;
		}
		public void setHomeRankings(String homeRankings) {
			this.homeRankings = homeRankings;
		}
		public String getAwayRankings() {
			return awayRankings;
		}
		public void setAwayRankings(String awayRankings) {
			this.awayRankings = awayRankings;
		}
		public String getMatchMessage() {
			return matchMessage;
		}
		public void setMatchMessage(String matchMessage) {
			this.matchMessage = matchMessage;
		}
		public String getGroupName() {
			return groupName;
		}
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		public String getMatchAddress() {
			return matchAddress;
		}
		public void setMatchAddress(String matchAddress) {
			this.matchAddress = matchAddress;
		}
		public String getWeatherImg() {
			return weatherImg;
		}
		public void setWeatherImg(String weatherImg) {
			this.weatherImg = weatherImg;
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
		public String getSeason() {
			return season;
		}
		public void setSeason(String season) {
			this.season = season;
		}
		public String getTeamGroup() {
			return teamGroup;
		}
		public void setTeamGroup(String teamGroup) {
			this.teamGroup = teamGroup;
		}
		public String getIsNeutrality() {
			return isNeutrality;
		}
		public void setIsNeutrality(String isNeutrality) {
			this.isNeutrality = isNeutrality;
		}
		public String toString(){
			return ReflectionToStringBuilder.toString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}
		public Date getMatchTimeInGMT0() {
			return matchTimeInGMT0;
		}
		public void setMatchTimeInGMT0(Date matchTimeInGMT0) {
			this.matchTimeInGMT0 = matchTimeInGMT0;
		}
		
}
