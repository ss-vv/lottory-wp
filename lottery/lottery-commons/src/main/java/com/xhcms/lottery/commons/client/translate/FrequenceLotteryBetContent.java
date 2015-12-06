package com.xhcms.lottery.commons.client.translate;

import org.springframework.util.StringUtils;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.LotteryPlatformIdEnum;

public class FrequenceLotteryBetContent extends BetContent {
	
	private String betContent;

	public FrequenceLotteryBetContent(String code, String lcPlayId) {
		this.betContent=code;
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
