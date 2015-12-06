package com.xhcms.lottery.commons.client.translate;



import org.springframework.util.StringUtils;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.LotteryPlatformIdEnum;

/**
 * 高频彩投注内容。
 * 
 * @author Yang Bo
 */
public class HFLotteryBetContent extends BetContent {
	
	private String betContent;

	public HFLotteryBetContent(String betContent) {
		this.betContent=betContent;
		if(StringUtils.countOccurrencesOf(betContent, ";")==1){//如果只有一个;,就把这个分号去掉
			this.betContent.replace(";", "");
		}
	}

	@Override
	public String toPlatformBetContent() throws TranslateException {
		return betContent;
	}

	@Override
	public String toPlatformBetContent(LotteryPlatformIdEnum lotteryPlatformId)
			throws TranslateException {
		// TODO Auto-generated method stub
		return null;
	}
}
