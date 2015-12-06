package com.xhcms.lottery.commons.client.translate;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.LotteryPlatformIdEnum;

/**
 * 
 * @author yan.chen, Yang Bo
 *
 */
public class JCBetContent extends BetContent {

	@Override
	public String toPlatformBetContent() throws TranslateException {
		StringBuilder builder = new StringBuilder();
		
		for (PlatformBetContent matchBet : matchBetContents) {
			if (builder.length()>0){
				builder.append("/");
			}
			builder.append(matchBet.toPlatformMatchBetContent());
		}
		return builder.toString();
	}

	@Override
	public String toPlatformBetContent(LotteryPlatformIdEnum lotteryPlatformId)
			throws TranslateException {
		switch (lotteryPlatformId) {
		case YUAN_CHENG_CHU_PIAO:
			return toYuanChengChuPiao();
		default:
			return toPlatformBetContent();
		}
	}
	
	/**
	 * 远程出票格式
	 * @return
	 * @throws TranslateException
	 */
	private String toYuanChengChuPiao() throws TranslateException{
		StringBuilder builder = new StringBuilder();
		for (PlatformBetContent matchBet : matchBetContents) {
			if (builder.length()>0){
//				builder.append("|");
				
			}
			builder.append(matchBet.toPlatformMatchBetContent());
		}
		return builder.toString();
	}
}
