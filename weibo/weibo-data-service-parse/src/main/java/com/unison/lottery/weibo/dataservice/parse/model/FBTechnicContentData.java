package com.unison.lottery.weibo.dataservice.parse.model;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FBTechnicContentData {

	//比赛id
	private String matchId;
	//先开球
	private String  homeKickOff;
	private String  awayKickOff;
	//第一个角球
	private String homeFirstCorner;
	private String awayFirstCorner;
	//第一张黄牌
	private String homeFirstYellowCard;
	private String awayFirstYellowCard;
	//射门次数
	private String homeShootNum;
	private String awayShootNum;
	//射正次数
	private String homeShootRightNum;
	private String awayShootRightNum;
	//犯规次数
	private String homeFoulTrouble;
	private String awayFoulTrouble;
	//角球次数
	private String homeCorners;
	private String awayCorners;
	//角球次数(加时)
	private String homeExtraTimeCorners;
	private String awayExtraTimeCorners;
	//任意球次数
	private String homeFreeKickNum;
	private String awayFreeKickNum;
	//越位次数
	private String homeOffsideNum;
	private String awayOffsideNum;
	//乌龙球数
	private String homeOwnGoalNum;
	private String awayOwnGoalNum;
	//黄牌数
	private String homeYellowCardNum;
	private String awayYellowCardNum;
	//黄牌数(加时)
	private String homeExtraTimeYellowCardNum;
	private String awayExtraTimeYellowCardNum;
	//红牌数
	private String homeRedCardNum;
	private String awayRedCardNum;
	//控球时间
	private String homeBallControlTime;
	private String awayBallControlTime;
	//头球
	private String homeHeadingNum;
	private String awayHeadingNum;
	//救球
	private String homeFollowTheBallNum;
	private String awayFollowTheBallNum;
	//守门员出击
	private String homeGoalkeeperPouncedNum;
	private String awayGoalkeeperPouncedNum;
	//丟球
	private String homeBallverlustNum;
	private String awayBallverlustNum;
	//成功抢断
	private String homeStealSuccess;
	private String awayStealSuccess;
	//阻截
	private String homeInterceptNum;
	private String awayInterceptNum;
	//长传
	private String homeLongPassNum;
	private String awayLongPassNum;
	//短传
	private String homeShortPassNum;
	private String awayShortPassNum;
	//助攻
	private String homeSecondaryAttackNum;
	private String awaySecondaryAttackNum;
	//成功传中
	private String homeSuccessfulCrossNum;
	private String awaySuccessfulCrossNum;
	//第一个换人
	private String homeFirstSubstitution;
	private String awayFirstSubstitution;
	//最后换人
	private String homeLastSubstitution;
	private String awayLastSubstitution;
	//第一个越位
	private String homeFirstOffside;
	private String awayFirstOffside;
	//最后越位
	private String homeLastOffside;
	private String awayLastOffside;
	//换人数
	private String homeSubstitutionNum;
	private String awaySubstitutionNum;
	//最后角球
	private String homeLastCorner;
	private String awayLastCorner;
	//最后黄牌
	private String homeYellowCard;
	private String awayYellowCard;
	//换人数(加时)
	private String homeExtraTimeSubstitutionNum;
	private String awayExtraTimeSubstitutionNum;
	//越位次数(加时)
	private String homeExtraTimeOffsideNum;
	private String awayExtraTimeOffsideNum;
	//红牌数(加时)
	private String homeExtraTimeRedCardNum;
	private String awayExtraTimeRedCardNum;
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getHomeKickOff() {
		return homeKickOff;
	}
	public void setHomeKickOff(String homeKickOff) {
		this.homeKickOff = homeKickOff;
	}
	public String getAwayKickOff() {
		return awayKickOff;
	}
	public void setAwayKickOff(String awayKickOff) {
		this.awayKickOff = awayKickOff;
	}
	public String getHomeFirstCorner() {
		return homeFirstCorner;
	}
	public void setHomeFirstCorner(String homeFirstCorner) {
		this.homeFirstCorner = homeFirstCorner;
	}
	public String getAwayFirstCorner() {
		return awayFirstCorner;
	}
	public void setAwayFirstCorner(String awayFirstCorner) {
		this.awayFirstCorner = awayFirstCorner;
	}
	public String getHomeFirstYellowCard() {
		return homeFirstYellowCard;
	}
	public void setHomeFirstYellowCard(String homeFirstYellowCard) {
		this.homeFirstYellowCard = homeFirstYellowCard;
	}
	public String getAwayFirstYellowCard() {
		return awayFirstYellowCard;
	}
	public void setAwayFirstYellowCard(String awayFirstYellowCard) {
		this.awayFirstYellowCard = awayFirstYellowCard;
	}
	public String getHomeShootNum() {
		return homeShootNum;
	}
	public void setHomeShootNum(String homeShootNum) {
		this.homeShootNum = homeShootNum;
	}
	public String getAwayShootNum() {
		return awayShootNum;
	}
	public void setAwayShootNum(String awayShootNum) {
		this.awayShootNum = awayShootNum;
	}
	public String getHomeShootRightNum() {
		return homeShootRightNum;
	}
	public void setHomeShootRightNum(String homeShootRightNum) {
		this.homeShootRightNum = homeShootRightNum;
	}
	public String getAwayShootRightNum() {
		return awayShootRightNum;
	}
	public void setAwayShootRightNum(String awayShootRightNum) {
		this.awayShootRightNum = awayShootRightNum;
	}
	public String getHomeFoulTrouble() {
		return homeFoulTrouble;
	}
	public void setHomeFoulTrouble(String homeFoulTrouble) {
		this.homeFoulTrouble = homeFoulTrouble;
	}
	public String getAwayFoulTrouble() {
		return awayFoulTrouble;
	}
	public void setAwayFoulTrouble(String awayFoulTrouble) {
		this.awayFoulTrouble = awayFoulTrouble;
	}
	public String getHomeCorners() {
		return homeCorners;
	}
	public void setHomeCorners(String homeCorners) {
		this.homeCorners = homeCorners;
	}
	public String getAwayCorners() {
		return awayCorners;
	}
	public void setAwayCorners(String awayCorners) {
		this.awayCorners = awayCorners;
	}
	public String getHomeExtraTimeCorners() {
		return homeExtraTimeCorners;
	}
	public void setHomeExtraTimeCorners(String homeExtraTimeCorners) {
		this.homeExtraTimeCorners = homeExtraTimeCorners;
	}
	public String getAwayExtraTimeCorners() {
		return awayExtraTimeCorners;
	}
	public void setAwayExtraTimeCorners(String awayExtraTimeCorners) {
		this.awayExtraTimeCorners = awayExtraTimeCorners;
	}
	public String getHomeFreeKickNum() {
		return homeFreeKickNum;
	}
	public void setHomeFreeKickNum(String homeFreeKickNum) {
		this.homeFreeKickNum = homeFreeKickNum;
	}
	public String getAwayFreeKickNum() {
		return awayFreeKickNum;
	}
	public void setAwayFreeKickNum(String awayFreeKickNum) {
		this.awayFreeKickNum = awayFreeKickNum;
	}
	public String getHomeOffsideNum() {
		return homeOffsideNum;
	}
	public void setHomeOffsideNum(String homeOffsideNum) {
		this.homeOffsideNum = homeOffsideNum;
	}
	public String getAwayOffsideNum() {
		return awayOffsideNum;
	}
	public void setAwayOffsideNum(String awayOffsideNum) {
		this.awayOffsideNum = awayOffsideNum;
	}
	public String getHomeOwnGoalNum() {
		return homeOwnGoalNum;
	}
	public void setHomeOwnGoalNum(String homeOwnGoalNum) {
		this.homeOwnGoalNum = homeOwnGoalNum;
	}
	public String getAwayOwnGoalNum() {
		return awayOwnGoalNum;
	}
	public void setAwayOwnGoalNum(String awayOwnGoalNum) {
		this.awayOwnGoalNum = awayOwnGoalNum;
	}
	public String getHomeYellowCardNum() {
		return homeYellowCardNum;
	}
	public void setHomeYellowCardNum(String homeYellowCardNum) {
		this.homeYellowCardNum = homeYellowCardNum;
	}
	public String getAwayYellowCardNum() {
		return awayYellowCardNum;
	}
	public void setAwayYellowCardNum(String awayYellowCardNum) {
		this.awayYellowCardNum = awayYellowCardNum;
	}
	public String getHomeExtraTimeYellowCardNum() {
		return homeExtraTimeYellowCardNum;
	}
	public void setHomeExtraTimeYellowCardNum(String homeExtraTimeYellowCardNum) {
		this.homeExtraTimeYellowCardNum = homeExtraTimeYellowCardNum;
	}
	public String getAwayExtraTimeYellowCardNum() {
		return awayExtraTimeYellowCardNum;
	}
	public void setAwayExtraTimeYellowCardNum(String awayExtraTimeYellowCardNum) {
		this.awayExtraTimeYellowCardNum = awayExtraTimeYellowCardNum;
	}
	public String getHomeRedCardNum() {
		return homeRedCardNum;
	}
	public void setHomeRedCardNum(String homeRedCardNum) {
		this.homeRedCardNum = homeRedCardNum;
	}
	public String getAwayRedCardNum() {
		return awayRedCardNum;
	}
	public void setAwayRedCardNum(String awayRedCardNum) {
		this.awayRedCardNum = awayRedCardNum;
	}
	public String getHomeBallControlTime() {
		return homeBallControlTime;
	}
	public void setHomeBallControlTime(String homeBallControlTime) {
		this.homeBallControlTime = homeBallControlTime;
	}
	public String getAwayBallControlTime() {
		return awayBallControlTime;
	}
	public void setAwayBallControlTime(String awayBallControlTime) {
		this.awayBallControlTime = awayBallControlTime;
	}
	public String getHomeHeadingNum() {
		return homeHeadingNum;
	}
	public void setHomeHeadingNum(String homeHeadingNum) {
		this.homeHeadingNum = homeHeadingNum;
	}
	public String getAwayHeadingNum() {
		return awayHeadingNum;
	}
	public void setAwayHeadingNum(String awayHeadingNum) {
		this.awayHeadingNum = awayHeadingNum;
	}
	public String getHomeFollowTheBallNum() {
		return homeFollowTheBallNum;
	}
	public void setHomeFollowTheBallNum(String homeFollowTheBallNum) {
		this.homeFollowTheBallNum = homeFollowTheBallNum;
	}
	public String getAwayFollowTheBallNum() {
		return awayFollowTheBallNum;
	}
	public void setAwayFollowTheBallNum(String awayFollowTheBallNum) {
		this.awayFollowTheBallNum = awayFollowTheBallNum;
	}
	public String getHomeGoalkeeperPouncedNum() {
		return homeGoalkeeperPouncedNum;
	}
	public void setHomeGoalkeeperPouncedNum(String homeGoalkeeperPouncedNum) {
		this.homeGoalkeeperPouncedNum = homeGoalkeeperPouncedNum;
	}
	public String getAwayGoalkeeperPouncedNum() {
		return awayGoalkeeperPouncedNum;
	}
	public void setAwayGoalkeeperPouncedNum(String awayGoalkeeperPouncedNum) {
		this.awayGoalkeeperPouncedNum = awayGoalkeeperPouncedNum;
	}
	public String getHomeBallverlustNum() {
		return homeBallverlustNum;
	}
	public void setHomeBallverlustNum(String homeBallverlustNum) {
		this.homeBallverlustNum = homeBallverlustNum;
	}
	public String getAwayBallverlustNum() {
		return awayBallverlustNum;
	}
	public void setAwayBallverlustNum(String awayBallverlustNum) {
		this.awayBallverlustNum = awayBallverlustNum;
	}
	public String getHomeStealSuccess() {
		return homeStealSuccess;
	}
	public void setHomeStealSuccess(String homeStealSuccess) {
		this.homeStealSuccess = homeStealSuccess;
	}
	public String getAwayStealSuccess() {
		return awayStealSuccess;
	}
	public void setAwayStealSuccess(String awayStealSuccess) {
		this.awayStealSuccess = awayStealSuccess;
	}
	public String getHomeInterceptNum() {
		return homeInterceptNum;
	}
	public void setHomeInterceptNum(String homeInterceptNum) {
		this.homeInterceptNum = homeInterceptNum;
	}
	public String getAwayInterceptNum() {
		return awayInterceptNum;
	}
	public void setAwayInterceptNum(String awayInterceptNum) {
		this.awayInterceptNum = awayInterceptNum;
	}
	public String getHomeLongPassNum() {
		return homeLongPassNum;
	}
	public void setHomeLongPassNum(String homeLongPassNum) {
		this.homeLongPassNum = homeLongPassNum;
	}
	public String getAwayLongPassNum() {
		return awayLongPassNum;
	}
	public void setAwayLongPassNum(String awayLongPassNum) {
		this.awayLongPassNum = awayLongPassNum;
	}
	public String getHomeShortPassNum() {
		return homeShortPassNum;
	}
	public void setHomeShortPassNum(String homeShortPassNum) {
		this.homeShortPassNum = homeShortPassNum;
	}
	public String getAwayShortPassNum() {
		return awayShortPassNum;
	}
	public void setAwayShortPassNum(String awayShortPassNum) {
		this.awayShortPassNum = awayShortPassNum;
	}
	public String getHomeSecondaryAttackNum() {
		return homeSecondaryAttackNum;
	}
	public void setHomeSecondaryAttackNum(String homeSecondaryAttackNum) {
		this.homeSecondaryAttackNum = homeSecondaryAttackNum;
	}
	public String getAwaySecondaryAttackNum() {
		return awaySecondaryAttackNum;
	}
	public void setAwaySecondaryAttackNum(String awaySecondaryAttackNum) {
		this.awaySecondaryAttackNum = awaySecondaryAttackNum;
	}
	public String getHomeSuccessfulCrossNum() {
		return homeSuccessfulCrossNum;
	}
	public void setHomeSuccessfulCrossNum(String homeSuccessfulCrossNum) {
		this.homeSuccessfulCrossNum = homeSuccessfulCrossNum;
	}
	public String getAwaySuccessfulCrossNum() {
		return awaySuccessfulCrossNum;
	}
	public void setAwaySuccessfulCrossNum(String awaySuccessfulCrossNum) {
		this.awaySuccessfulCrossNum = awaySuccessfulCrossNum;
	}
	public String getHomeFirstSubstitution() {
		return homeFirstSubstitution;
	}
	public void setHomeFirstSubstitution(String homeFirstSubstitution) {
		this.homeFirstSubstitution = homeFirstSubstitution;
	}
	public String getAwayFirstSubstitution() {
		return awayFirstSubstitution;
	}
	public void setAwayFirstSubstitution(String awayFirstSubstitution) {
		this.awayFirstSubstitution = awayFirstSubstitution;
	}
	public String getHomeLastSubstitution() {
		return homeLastSubstitution;
	}
	public void setHomeLastSubstitution(String homeLastSubstitution) {
		this.homeLastSubstitution = homeLastSubstitution;
	}
	public String getAwayLastSubstitution() {
		return awayLastSubstitution;
	}
	public void setAwayLastSubstitution(String awayLastSubstitution) {
		this.awayLastSubstitution = awayLastSubstitution;
	}
	public String getHomeFirstOffside() {
		return homeFirstOffside;
	}
	public void setHomeFirstOffside(String homeFirstOffside) {
		this.homeFirstOffside = homeFirstOffside;
	}
	public String getAwayFirstOffside() {
		return awayFirstOffside;
	}
	public void setAwayFirstOffside(String awayFirstOffside) {
		this.awayFirstOffside = awayFirstOffside;
	}
	public String getHomeLastOffside() {
		return homeLastOffside;
	}
	public void setHomeLastOffside(String homeLastOffside) {
		this.homeLastOffside = homeLastOffside;
	}
	public String getAwayLastOffside() {
		return awayLastOffside;
	}
	public void setAwayLastOffside(String awayLastOffside) {
		this.awayLastOffside = awayLastOffside;
	}
	public String getHomeSubstitutionNum() {
		return homeSubstitutionNum;
	}
	public void setHomeSubstitutionNum(String homeSubstitutionNum) {
		this.homeSubstitutionNum = homeSubstitutionNum;
	}
	public String getAwaySubstitutionNum() {
		return awaySubstitutionNum;
	}
	public void setAwaySubstitutionNum(String awaySubstitutionNum) {
		this.awaySubstitutionNum = awaySubstitutionNum;
	}
	public String getHomeLastCorner() {
		return homeLastCorner;
	}
	public void setHomeLastCorner(String homeLastCorner) {
		this.homeLastCorner = homeLastCorner;
	}
	public String getAwayLastCorner() {
		return awayLastCorner;
	}
	public void setAwayLastCorner(String awayLastCorner) {
		this.awayLastCorner = awayLastCorner;
	}
	public String getHomeYellowCard() {
		return homeYellowCard;
	}
	public void setHomeYellowCard(String homeYellowCard) {
		this.homeYellowCard = homeYellowCard;
	}
	public String getAwayYellowCard() {
		return awayYellowCard;
	}
	public void setAwayYellowCard(String awayYellowCard) {
		this.awayYellowCard = awayYellowCard;
	}
	public String getHomeExtraTimeSubstitutionNum() {
		return homeExtraTimeSubstitutionNum;
	}
	public void setHomeExtraTimeSubstitutionNum(String homeExtraTimeSubstitutionNum) {
		this.homeExtraTimeSubstitutionNum = homeExtraTimeSubstitutionNum;
	}
	public String getAwayExtraTimeSubstitutionNum() {
		return awayExtraTimeSubstitutionNum;
	}
	public void setAwayExtraTimeSubstitutionNum(String awayExtraTimeSubstitutionNum) {
		this.awayExtraTimeSubstitutionNum = awayExtraTimeSubstitutionNum;
	}
	public String getHomeExtraTimeOffsideNum() {
		return homeExtraTimeOffsideNum;
	}
	public void setHomeExtraTimeOffsideNum(String homeExtraTimeOffsideNum) {
		this.homeExtraTimeOffsideNum = homeExtraTimeOffsideNum;
	}
	public String getAwayExtraTimeOffsideNum() {
		return awayExtraTimeOffsideNum;
	}
	public void setAwayExtraTimeOffsideNum(String awayExtraTimeOffsideNum) {
		this.awayExtraTimeOffsideNum = awayExtraTimeOffsideNum;
	}
	public String getHomeExtraTimeRedCardNum() {
		return homeExtraTimeRedCardNum;
	}
	public void setHomeExtraTimeRedCardNum(String homeExtraTimeRedCardNum) {
		this.homeExtraTimeRedCardNum = homeExtraTimeRedCardNum;
	}
	public String getAwayExtraTimeRedCardNum() {
		return awayExtraTimeRedCardNum;
	}
	public void setAwayExtraTimeRedCardNum(String awayExtraTimeRedCardNum) {
		this.awayExtraTimeRedCardNum = awayExtraTimeRedCardNum;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

