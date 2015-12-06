package com.xhcms.lottery.lang;

import org.apache.commons.lang.StringUtils;

/**
 * 大V彩彩种枚举.
 * 
 * @author Yang Bo
 */
public enum LotteryId {
	
	SSQ("双色球"),		// 双色球
	FC3D("福彩3D"),		// 3D
	JCZQ("竞彩足球"),		// 竞彩足球
	JCLQ("竞彩篮球"),		// 竞彩篮球
	JX11("江西11选5"),	// 江西11选5
	CTZC("传统足彩"),		// 传统足球
	CQSS("重庆时时彩"),	// 重庆时时彩
	BJDC("北京单场"),		// 北京单场
	BDSF("北单胜负"),     //北单胜负
	UNKNOWN("未知");
	
	private String cnName;
	
	private LotteryId(String cnName){
		this.cnName = cnName;
	}
	
	public String cnName(){
		return cnName;
	}
	
	public static String getLotteryName(String key) {
		String result = null;
		if(StringUtils.isBlank(key)) {
			return null;
		}
		LotteryId[] values = LotteryId.values();
		for(LotteryId id : values) {
			if(key.equals(id.name())) {
				result = id.cnName();
				break;
			}
		}
		return result;
	}
	
	public static LotteryId get(String lotteryId) {
		LotteryId lottery = LotteryId.UNKNOWN;
		if(StringUtils.isNotBlank(lotteryId)) {
			if(lotteryId.equals(LotteryId.JCZQ.name())) {
				lottery = LotteryId.JCZQ;
			} else if(lotteryId.equals(LotteryId.JCLQ.name())) {
				lottery = LotteryId.JCLQ;
			} else if(lotteryId.equals(LotteryId.CTZC.name())) {
				lottery = LotteryId.CTZC;
			} else if(lotteryId.equals(LotteryId.SSQ.name())) {
				lottery = LotteryId.SSQ;
			} else if(lotteryId.equals(LotteryId.BJDC.name())) {
				lottery = LotteryId.BJDC;
			} else if(lotteryId.equals(LotteryId.FC3D.name())) {
				lottery = LotteryId.FC3D;
			}
		}
		return lottery;
	}
}
