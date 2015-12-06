package com.unison.lottery.mc.uni.parser.util;

import com.xhcms.lottery.lang.PlayType;

/**
 * 接口平台、大V彩之间的 lotteryId, playId 转换。
 * @author Wang Lei
 *
 */
public interface IDMapper {
	/**
	 *  从中民lotteryId得到大V彩玩法枚举对象。
	 *  @return UNKOWN 如果没有对应的玩法id。
	 */
	public PlayType getLCPlayTypeFromPlatformLotteryId(String platformLotteryId) throws IDMapperException;
	
	/**
	 * 从中民lotteryId转换为大V彩的lotteryId。
	 */
	public String getLCLotteryIdFromPlatformLotteryId(String platformLotteryId);
}
