package com.davcai.lottery.push.common.model;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;



public  class MatchMessage extends PushMessage{
	
	public MatchMessage() {
		super();
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 4478825206902185034L;


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
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	public String getState() {
		if(StringUtils.isBlank(state)){
			state=getDefaultState();
		}
		return state;
	}
	protected String getDefaultState() {
		return null;
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
	
	@Override
	protected void makeExtendDataMap(Map<String, Object> data) {
		
			data.put("homeTeamName", homeTeamName);
			data.put("guestTeamName", guestTeamName);
			data.put("homeScore", homeScore);
			data.put("guestScore", guestScore);
			data.put("matchId", matchId);
			data.put("result",result);
			data.put("state", state);
			data.put("homeTeamHalfScore", homeTeamHalfScore);
			data.put("homeTeamPosition", homeTeamPosition);
			data.put("guestTeamPosition", guestTeamPosition);
			data.put("scheduleMatchTime", scheduleMatchTime.toString());
		
		
	}
	
	@Override
	public String makeChannelName() {
		
		return matchChannelPrefix+this.getType()+"/"+this.matchId;
		
	}
	public String getMatchChannelPrefix() {
		return matchChannelPrefix;
	}
	public void setMatchChannelPrefix(String matchChannelPrefix) {
		this.matchChannelPrefix = matchChannelPrefix;
	}
	
	@Override
	public String getId() {
		String result=null;
		if(StringUtils.isNotBlank(this.matchId)&&null!=getType()){
			result=getType().name()+":"+this.matchId;
		}
		return result;
	}

	@Override
	public boolean isSame(PushMessage oldPushMessage) {
		if (null == oldPushMessage) {
			return false;
		}
		if (oldPushMessage instanceof MatchMessage) {
			MatchMessage matchMessage = (MatchMessage) oldPushMessage;
			return StringUtils.equals(matchMessage.guestTeamName,
					this.guestTeamName)
					&& StringUtils.equals(matchMessage.guestTeamPosition,
							this.guestTeamPosition)
					&& StringUtils.equals(matchMessage.homeTeamName,
							this.homeTeamName)
					&& StringUtils.equals(matchMessage.homeTeamPosition,
							this.homeTeamPosition)
					&& StringUtils.equals(matchMessage.matchId, this.matchId)
					&& StringUtils.equals(matchMessage.state, this.state)
					&& matchMessage.homeScore == this.homeScore
					&& matchMessage.guestScore == this.guestScore
					&& matchMessage.guestTeamHalfScore == this.guestTeamHalfScore
					&& matchMessage.homeTeamHalfScore == this.homeTeamHalfScore
					&& (
							(
									null != matchMessage.scheduleMatchTime
									&& null != this.scheduleMatchTime 
									&& matchMessage.scheduleMatchTime.equals(this.scheduleMatchTime)	
							)||
							(
									null==matchMessage.scheduleMatchTime
									&&
									null==this.scheduleMatchTime
							)
							
							
					);

		}
		return false;
	}
	@Override
	public boolean isSameForHuanxin(PushMessage oldPushMessage){
		if (null == oldPushMessage) {
			return false;
		}
		if (oldPushMessage instanceof MatchMessage) {
			MatchMessage matchMessage = (MatchMessage) oldPushMessage;
			return StringUtils.equals(matchMessage.guestTeamName,
					this.guestTeamName)
					&& StringUtils.equals(matchMessage.guestTeamPosition,
							this.guestTeamPosition)
					&& StringUtils.equals(matchMessage.homeTeamName,
							this.homeTeamName)
					&& StringUtils.equals(matchMessage.homeTeamPosition,
							this.homeTeamPosition)
					&& StringUtils.equals(matchMessage.matchId, this.matchId)
					&& StringUtils.equals(matchMessage.state, this.state)
					&& (
							(
									null != matchMessage.scheduleMatchTime
									&& null != this.scheduleMatchTime 
									&& matchMessage.scheduleMatchTime.equals(this.scheduleMatchTime)	
							)||
							(
									null==matchMessage.scheduleMatchTime
									&&
									null==this.scheduleMatchTime
							)
							
							
					);
		}
		return false;
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
	public Date getScheduleMatchTime() {
		return scheduleMatchTime;
	}
	public void setScheduleMatchTime(Date scheduleMatchTime) {
		this.scheduleMatchTime = scheduleMatchTime;
	}
	public  String getStateDesc(){
		return null;
	}
	
	
	
	

}
