package com.xhcms.lottery.commons.data;

import java.util.Date;

import com.xhcms.commons.mq.XHMessage;

public class QtMatchBaseMQModel implements XHMessage{

	private static final long serialVersionUID = -6848247559499713855L;
	
	private String bsId;
	private String leagueId;
	private String color;
	private String homeTeamName;
	private String guestTeamName;
	private Date matchTime;
	private String jingcaiId; //竞彩id 如周四001

	public String getBsId() {
		return bsId;
	}

	public void setBsId(String bsId) {
		this.bsId = bsId;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public String getJingcaiId() {
		return jingcaiId;
	}

	public void setJingcaiId(String jingcaiId) {
		this.jingcaiId = jingcaiId;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		return getClass().getSimpleName();
	}

	@Override
	public void setPriority(int arg0) {
		// TODO Auto-generated method stub
	}
}
