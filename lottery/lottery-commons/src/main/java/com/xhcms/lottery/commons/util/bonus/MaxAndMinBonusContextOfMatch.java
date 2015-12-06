package com.xhcms.lottery.commons.util.bonus;

import java.util.List;

public class MaxAndMinBonusContextOfMatch {
	private long matchId;//比赛id
	private List<List<BetContent>> coexistenceSet;//根据用户投注项生成的可共存投注项集合
	private List<BetContent> maxOddsBetContentList;//最大赔率投注项集合
	private List<BetContent> minOddsBetContentList;//最小赔率投注项集合
	
	private boolean seed;//是否设胆
	
	public long getMatchId() {
		return matchId;
	}
	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}
	
	public List<List<BetContent>> getCoexistenceSet() {
		return coexistenceSet;
	}
	public void setCoexistenceSet(List<List<BetContent>> coexistenceSet) {
		this.coexistenceSet = coexistenceSet;
	}
	public List<BetContent> getMaxOddsBetContentList() {
		return maxOddsBetContentList;
	}
	public void setMaxOddsBetContentList(List<BetContent> maxOddsBetContentList) {
		this.maxOddsBetContentList = maxOddsBetContentList;
	}
	public List<BetContent> getMinOddsBetContentList() {
		return minOddsBetContentList;
	}
	public void setMinOddsBetContentList(List<BetContent> minOddsBetContentList) {
		this.minOddsBetContentList = minOddsBetContentList;
	}
	public boolean isSeed() {
		return seed;
	}
	public void setSeed(boolean seed) {
		this.seed = seed;
	}
}
