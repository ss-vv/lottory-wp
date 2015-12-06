package com.unison.lottery.api.lotteryInfo.util;

import org.apache.commons.lang.StringUtils;


/**
 * @desc 开奖号码转换
 * @createTime 2013-1-9
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public final class LotteryNumberConvertor {
	
	public static String convert(String lotteryId, String lotteryNumber) {
		if(null != lotteryId && StringUtils.isNotBlank(lotteryNumber)) {
			return lotteryNumber.replaceAll(" ", ",");
		}
		return null;
	}
}