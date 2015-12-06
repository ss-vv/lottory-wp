package com.xhcms.lottery.commons.util;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PresetAward;

/**
 * 预兑奖方案工具类
 * @author Wang Lei
 *
 */
public final class PresetSchemeTool {
	
	/**
	 * 判断方案是否是有效的预兑奖方案
	 * @param sourceSpo
	 * @return
	 */
	 public static boolean isPresetScheme(BetSchemePO sourceSpo){
		 return check(sourceSpo.getLotteryId(), sourceSpo.getIsPresetAward());
	 }
	 
	 public static boolean isPresetScheme(BetScheme sourceSdo){
		 return check(sourceSdo.getLotteryId(), sourceSdo.getIsPresetAward());
	 }
	 
	 private static boolean check(String lotteryId, int isPresetAward){
		 return (Constants.JCLQ.equals(lotteryId) || Constants.JCZQ.equals(lotteryId)) 
				 && PresetAward.Is_PresetAward.getValue() == isPresetAward;
	 }
}
