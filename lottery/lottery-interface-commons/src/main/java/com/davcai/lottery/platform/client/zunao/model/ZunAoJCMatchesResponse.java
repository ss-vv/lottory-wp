package com.davcai.lottery.platform.client.zunao.model;

import java.util.List;

import com.xhcms.lottery.dc.data.Match;

public class ZunAoJCMatchesResponse extends ZunAoResponse {
	private List<Match> matches;

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
}
