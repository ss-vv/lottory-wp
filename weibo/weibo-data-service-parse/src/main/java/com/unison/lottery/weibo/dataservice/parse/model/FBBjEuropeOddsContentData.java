package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FBBjEuropeOddsContentData {
	
	private String matchId;
	private String matchTime;
	private BaseName leagueName;
	private BaseName homeName;
	private BaseName awayName;
	private List<FBMatchEuropeOddDetail> fbMatchEuropeOddDetailList; 
	
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	
	public List<FBMatchEuropeOddDetail> getFbMatchEuropeOddDetailList() {
		return fbMatchEuropeOddDetailList;
	}
	public void setFbMatchEuropeOddDetailList(
			List<FBMatchEuropeOddDetail> fbMatchEuropeOddDetailList) {
		this.fbMatchEuropeOddDetailList = fbMatchEuropeOddDetailList;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	public BaseName getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(BaseName leagueName) {
		this.leagueName = leagueName;
	}
	public BaseName getHomeName() {
		return homeName;
	}
	public void setHomeName(BaseName homeName) {
		this.homeName = homeName;
	}
	public BaseName getAwayName() {
		return awayName;
	}
	public void setAwayName(BaseName awayName) {
		this.awayName = awayName;
	}
	

}
