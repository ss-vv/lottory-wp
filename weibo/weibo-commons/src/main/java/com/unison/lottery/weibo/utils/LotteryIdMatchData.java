package com.unison.lottery.weibo.utils;

import com.xhcms.lottery.lang.LotteryId;

public final class LotteryIdMatchData {

	/**
	 * 判断当前彩种类型是否是足彩
	 * @param lotteryType
	 * @return
	 */
	public static boolean isZC(String lotteryType) {
		return (LotteryId.JCZQ.name().equals(lotteryType) || 
				LotteryId.CTZC.name().equals(lotteryType) ||
				LotteryId.BJDC.name().equals(lotteryType) );
	}

	/**
	 * 判断当前彩种类型是否是竞篮
	 * @param lotteryType
	 * @return
	 */
	public static boolean isLC(String lotteryType) {
		return LotteryId.JCLQ.name().equals(lotteryType);
	}
}
