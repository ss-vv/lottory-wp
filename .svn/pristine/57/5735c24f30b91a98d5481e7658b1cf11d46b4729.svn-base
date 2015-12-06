package com.xhcms.lottery.dc.feed.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.xhcms.lottery.commons.data.BBMatchPlay;

public class BBMatchResult implements Serializable{
    private static final long serialVersionUID = -2758520174886995155L;
    private Long id;
	private String cnCode;
	private long leagueId;
	private String leagueName;
	private Date playingTime;
	private long homeTeamId;
	private String homeTeamName;
	private int guestTeamId;
	private String guestTeamName;
	private String quarter1;
	private String quarter2;
	private String quarter3;
	private String quarter4;
	private String finalScore;
	/**联赛颜色*/
	private String color;
	
	private Map<String, BBMatchPlay> plays;
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

	public String getQuarter1() {
        return quarter1;
    }

    public void setQuarter1(String quarter1) {
        this.quarter1 = quarter1;
    }

    public String getQuarter2() {
        return quarter2;
    }

    public void setQuarter2(String quarter2) {
        this.quarter2 = quarter2;
    }

    public String getQuarter3() {
        return quarter3;
    }

    public void setQuarter3(String quarter3) {
        this.quarter3 = quarter3;
    }

    public String getQuarter4() {
        return quarter4;
    }

    public void setQuarter4(String quarter4) {
        this.quarter4 = quarter4;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}
	
    public Map<String, BBMatchPlay> getPlays() {
        return plays;
    }

    public void addPlay(BBMatchPlay mp) {
        if(plays == null){
            plays = new HashMap<String, BBMatchPlay>();
        }
        plays.put(mp.getPlayId(), mp);
    }

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
    
}
