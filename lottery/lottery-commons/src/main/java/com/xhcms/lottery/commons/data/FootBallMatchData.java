package com.xhcms.lottery.commons.data;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;

public class FootBallMatchData {

	private FbMatchBaseInfoPO foBaseInfoPO;
	
	private String leagueShortName;

	private String color;
	
	private String matchId;
	
	private String formation;
	
	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public FbMatchBaseInfoPO getFoBaseInfoPO() {
		return foBaseInfoPO;
	}

	public void setFoBaseInfoPO(FbMatchBaseInfoPO foBaseInfoPO) {
		this.foBaseInfoPO = foBaseInfoPO;
	}

	public String getLeagueShortName() {
		return leagueShortName;
	}

	public void setLeagueShortName(String leagueShortName) {
		this.leagueShortName = leagueShortName;
	}
}
