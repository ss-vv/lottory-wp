package com.xhcms.lottery.dc.feed.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.xhcms.lottery.commons.data.FBMatchPlay;

/**
 * 赛果
 * @author langhsu
 */
public class FBMatchResult implements Serializable {
	private static final long serialVersionUID = 7240192054282298858L;
	private Long id;
	private String cnCode;
	private long leagueId;
	private String leagueName;
	private Date playingTime;
	private long homeTeamId;
	private String homeTeamName;
	private int guestTeamId;
	private String guestTeamName;
	private int concedePoints;
	private String halfScore;
	private String score;
	/**联赛颜色*/
	private String color;
	
	private Map<String, FBMatchPlay> plays;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	
	@JSON(format="MM-dd HH:mm")
	public Date getPlayingTime() {
		return playingTime;
	}

	public void setPlayingTime(Date playingTime) {
		this.playingTime = playingTime;
	}

	public long getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(long homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public int getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(int guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public String getGuestTeamName() {
		return guestTeamName;
	}

	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}

	public int getConcedePoints() {
		return concedePoints;
	}

	public void setConcedePoints(int concedePoints) {
		this.concedePoints = concedePoints;
	}

	public String getHalfScore() {
		return halfScore;
	}

	public void setHalfScore(String halfScore) {
		this.halfScore = halfScore;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Map<String, FBMatchPlay> getPlays() {
        return plays;
    }

    public void addPlay(FBMatchPlay mp) {
		if(plays == null){
		    plays = new HashMap<String, FBMatchPlay>();
		}
		plays.put(mp.getPlayId(), mp);
	}

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
