package com.unison.lottery.api.odds.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.xhcms.lottery.dc.data.OddsBase;

/**
 * @author baoxing.peng@davcai.com
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JCOdds{
	private Date matchTime;
	private String homeTeamName;
	private String guestTeamName;
	private String leagueShortName;
	private String code;
	private Long matchId;
	
	private List<Map> itemList;

	public String getMatchTime() {
		return DateFormatUtils.format(matchTime,"yyyyMMddHHmmss");
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
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

	public String getLeagueShortName() {
		return leagueShortName;
	}

	public void setLeagueShortName(String leagueShortName) {
		this.leagueShortName = leagueShortName;
	}

	public List<Map> getItemList() {
		return itemList;
	}

	public void setItemList(List<Map> oddsData) {
		this.itemList = oddsData;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	
}
