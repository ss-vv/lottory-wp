package com.unison.lottery.api.lotteryInfo.util;

import org.apache.commons.lang.StringUtils;

public class BonusResultConvert {

	public static String lotteryBonusCodeConvert(String bonusCode) {
		if(StringUtils.isNotBlank(bonusCode)) {
			return bonusCode.replace(" ", ",");
		}
		return null;
	}
	
	public static String lotteryBonusCodeConvertBySplit(String bonusCode, String split) {
		if(StringUtils.isNotBlank(bonusCode)) {
			bonusCode = bonusCode.replace(split, ",");
			bonusCode = bonusCode.substring(1, bonusCode.length() - 1);
			return bonusCode;
		}
		return null;
	}
}