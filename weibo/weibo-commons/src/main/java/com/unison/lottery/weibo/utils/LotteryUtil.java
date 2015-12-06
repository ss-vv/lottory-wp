package com.unison.lottery.weibo.utils;

/**
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2013-11-26
 * @version 1.0
 */
public class LotteryUtil {

	/**
	 *	根据彩种ID返回彩种名称 
	 * @param lotteryId
	 * @return
	 */
	public static String getName(String lotteryId) {
		String name = "未知";
		switch (lotteryId) {
		case "JCZQ":
			name = "竞彩足球";
			break;
		case "JCLQ":
			name = "竞彩篮球";
			break;
		case "JX11":
			name = "江西十一选五";
			break;
		case "CTZC":
			name = "传统足彩";
			break;
		case "CQSS":
			name = "重庆时时彩";
			break;
		case "SSQ":
			name = "双色球";
		case "BJDC":
		case "BDSF":
			name = "北京单场";
			break;
		}
		return name;
	}
	
}
