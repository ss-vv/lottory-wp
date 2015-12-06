package com.unison.lottery.weibo.data.vo;

import com.unison.lottery.weibo.data.MatchData;

public class RecentMatchVO extends MatchData {
	
	private String matchNameColor;
	private int hostScore;
	private int guestScore;
	private String hostScoreColor;
	private String guestScoreColor;
	
	public String getMatchNameColor() {
		return matchNameColor;
	}

	public void setMatchNameColor(String matchNameColor) {
		this.matchNameColor = matchNameColor;
	}

	public int getHostScore() {
		return hostScore;
	}

	public void setHostScore(int hostScore) {
		this.hostScore = hostScore;
	}

	public int getGuestScore() {
		return guestScore;
	}

	public void setGuestScore(int guestScore) {
		this.guestScore = guestScore;
	}

	public String getHostScoreColor() {
		return hostScoreColor;
	}

	public void setHostScoreColor(String hostScoreColor) {
		this.hostScoreColor = hostScoreColor;
	}

	public String getGuestScoreColor() {
		return guestScoreColor;
	}

	public void setGuestScoreColor(String guestScoreColor) {
		this.guestScoreColor = guestScoreColor;
	}
}
