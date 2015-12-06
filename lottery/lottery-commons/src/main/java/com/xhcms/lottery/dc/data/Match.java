/**
 * 
 */
package com.xhcms.lottery.dc.data;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 赛事信息
 * 
 * @author langhsu
 * 
 */
public class Match {

	protected long matchId;

	private String cnCode;

	protected String code; // 赛事编号

	private String league; // 联赛名称 //短联赛

	private String name; // 赛事名称

	private String longLeagueName; // 联赛名称
	
	private String homeTeam; // 主队名称

	private String guestTeam; // 客队名称

	private long homeTeamId; // 主队ID

	private long guestTeamId; // 客队ID

	private Date offtime; // 预计截止停售时间

	private Date playingTime; // 比赛时间

	private Date entrustDeadline; // 委托截止时间

	private int concedePoints; // 让球

	/** 篮球 */
	private float concedeScore; // 胜负让分

	private float concedeScorePass; // 过关胜负让分

	private float guestScore; // 大小分预设总分

	private float guestScorePass; // 过关大小分预设总分
	
	private List<String> playIds;	//赛事支持的玩法
	
	private Long anruiMatchId;
	
	public long getMatchId() {
		if (matchId <= 0) {
			matchId = Long.parseLong(DateFormatUtils
					.format(offtime, "yyyyMMdd"))
					* 10000
					+ Long.parseLong(code);
		}
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getOfftime() {
		return offtime;
	}

	public void setOfftime(Date offtime) {
		this.offtime = offtime;
	}

	public Date getPlayingTime() {
		return playingTime;
	}

	public void setPlayingTime(Date playingTime) {
		this.playingTime = playingTime;
	}

	public Date getEntrustDeadline() {
		return entrustDeadline;
	}

	public void setEntrustDeadline(Date entrustDeadline) {
		this.entrustDeadline = entrustDeadline;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public long getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(long homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public long getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getGuestTeam() {
		return guestTeam;
	}

	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
	}

	public int getConcedePoints() {
		return concedePoints;
	}

	public void setConcedePoints(int concedePoints) {
		this.concedePoints = concedePoints;
	}

	public float getConcedeScore() {
		return concedeScore;
	}

	public void setConcedeScore(float concedeScore) {
		this.concedeScore = concedeScore;
	}

	public float getConcedeScorePass() {
		return concedeScorePass;
	}

	public void setConcedeScorePass(float concedeScorePass) {
		this.concedeScorePass = concedeScorePass;
	}

	public float getGuestScore() {
		return guestScore;
	}

	public void setGuestScore(float guestScore) {
		this.guestScore = guestScore;
	}

	public float getGuestScorePass() {
		return guestScorePass;
	}

	public void setGuestScorePass(float guestScorePass) {
		this.guestScorePass = guestScorePass;
	}
	
	public List<String> getPlayIds() {
		return playIds;
	}

	public void setPlayIds(List<String> playIds) {
		this.playIds = playIds;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getLongLeagueName() {
		return longLeagueName;
	}

	public void setLongLeagueName(String longLeagueName) {
		this.longLeagueName = longLeagueName;
	}

	public Long getAnruiMatchId() {
		return anruiMatchId;
	}

	public void setAnruiMatchId(Long anruiMatchId) {
		this.anruiMatchId = anruiMatchId;
	}
}
