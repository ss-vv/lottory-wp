package com.xhcms.lottery.commons.util.bonus;

import java.util.List;

public class MaxAndMinBonusContext {
	
	private String passType = "";//格式:“2@1,3@4”
	
	private List<MaxAndMinBonusContextOfMatch> matchContexts;

	public List<MaxAndMinBonusContextOfMatch> getMatchContexts() {
		return matchContexts;
	}

	public void setMatchContexts(List<MaxAndMinBonusContextOfMatch> matchContexts) {
		this.matchContexts = matchContexts;
	}
	
	private List<BetContent> maxOddsBetContentListOfAll;//所有比赛的最大赔率投注项集合
	private List<BetContent> minOddsBetContentListOfAll;//所有比赛的最小赔率投注项集合
	
	private List<Integer[]> excludeIndexOfMaxOdds;//最大赔率的排除位置集合
	private List<Integer[]> excludeIndexOfMinOdds;//最小赔率的排除位置集合
	
	private List<Integer[]> seedIndexOfMaxOdds;//最大赔率的设胆位置集合
	private List<Integer[]> seedIndexOfMinOdds;//最小赔率的设胆位置集合

	

	public List<Integer[]> getExcludeIndexOfMaxOdds() {
		return excludeIndexOfMaxOdds;
	}

	public void setExcludeIndexOfMaxOdds(List<Integer[]> excludeIndexOfMaxOdds) {
		this.excludeIndexOfMaxOdds = excludeIndexOfMaxOdds;
	}

	public List<Integer[]> getExcludeIndexOfMinOdds() {
		return excludeIndexOfMinOdds;
	}

	public void setExcludeIndexOfMinOdds(List<Integer[]> excludeIndexOfMinOdds) {
		this.excludeIndexOfMinOdds = excludeIndexOfMinOdds;
	}

	public List<Integer[]> getSeedIndexOfMaxOdds() {
		return seedIndexOfMaxOdds;
	}

	public void setSeedIndexOfMaxOdds(List<Integer[]> seedIndexOfMaxOdds) {
		this.seedIndexOfMaxOdds = seedIndexOfMaxOdds;
	}

	public List<Integer[]> getSeedIndexOfMinOdds() {
		return seedIndexOfMinOdds;
	}

	public void setSeedIndexOfMinOdds(List<Integer[]> seedIndexOfMinOdds) {
		this.seedIndexOfMinOdds = seedIndexOfMinOdds;
	}

	public List<BetContent> getMaxOddsBetContentListOfAll() {
		return maxOddsBetContentListOfAll;
	}

	public void setMaxOddsBetContentListOfAll(
			List<BetContent> maxOddsBetContentListOfAll) {
		this.maxOddsBetContentListOfAll = maxOddsBetContentListOfAll;
	}

	public List<BetContent> getMinOddsBetContentListOfAll() {
		return minOddsBetContentListOfAll;
	}

	public void setMinOddsBetContentListOfAll(
			List<BetContent> minOddsBetContentListOfAll) {
		this.minOddsBetContentListOfAll = minOddsBetContentListOfAll;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}
}
