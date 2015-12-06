package com.xhcms.lottery.commons.client.translate;

import java.util.LinkedList;
import java.util.List;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.LotteryPlatformIdEnum;

public abstract class BetContent {
	protected String lcPlayId;	// 大V彩playId
	protected List<PlatformBetContent> matchBetContents = new LinkedList<PlatformBetContent>();
	
	public String getLcPlayId() {
		return lcPlayId;
	}

	public void setLcPlayId(String lcPlayId) {
		this.lcPlayId = lcPlayId;
	}

	public List<PlatformBetContent> getMatchBetContents() {
		return matchBetContents;
	}

	public void setMatchBetContents(List<PlatformBetContent> matchBetContents) {
		this.matchBetContents = matchBetContents;
	}
	
	/**
	 * 转换为中民投注内容。
	 * @throws TranslateException 
	 */
	public  abstract String toPlatformBetContent() throws TranslateException ;
	
	public  abstract String toPlatformBetContent(LotteryPlatformIdEnum lotteryPlatformId) throws TranslateException ;
}
