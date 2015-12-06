package com.davcai.lottery.platform.client.qiutan;

/**
 * 球探传统足彩比赛
 * @author haoxiang.jiang@davcai.com
 * @date 2015年4月13日 下午5:50:24
 */
public class QiutanCTZCMatch {
	/** 球探自己的id */
	private String scheduleid;
	
	private String matchid;
	
	/** 格式：yyyyMMddhhmmss */
	private String matchtime;
	
	/** 联赛名称 */
	private String sclass;
	
	private String hometeam;
	private String guestteam;
	private String hometeamf;
	private String guestteamf;
	private String color;
	
	public String getScheduleid() {
		return scheduleid;
	}
	public void setScheduleid(String scheduleid) {
		this.scheduleid = scheduleid;
	}
	public String getMatchid() {
		return matchid;
	}
	public void setMatchid(String matchid) {
		this.matchid = matchid;
	}
	public String getMatchtime() {
		return matchtime;
	}
	public void setMatchtime(String matchtime) {
		this.matchtime = matchtime;
	}
	public String getSclass() {
		return sclass;
	}
	public void setSclass(String sclass) {
		this.sclass = sclass;
	}
	public String getHometeam() {
		return hometeam;
	}
	public void setHometeam(String hometeam) {
		this.hometeam = hometeam;
	}
	public String getGuestteam() {
		return guestteam;
	}
	public void setGuestteam(String guestteam) {
		this.guestteam = guestteam;
	}
	public String getHometeamf() {
		return hometeamf;
	}
	public void setHometeamf(String hometeamf) {
		this.hometeamf = hometeamf;
	}
	public String getGuestteamf() {
		return guestteamf;
	}
	public void setGuestteamf(String guestteamf) {
		this.guestteamf = guestteamf;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
