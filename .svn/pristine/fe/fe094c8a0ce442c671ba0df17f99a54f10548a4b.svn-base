package com.unison.lottery.weibo.lang;

import org.apache.commons.lang3.StringUtils;
import com.xhcms.lottery.lang.LotteryId;

public enum LotteryBall {

	LQ("篮球", "LQ"), 
	ZQ("足球", "ZQ");

	private LotteryBall(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public static LotteryBall ball(String lotteryId) {
		LotteryBall ball = null;
		if(StringUtils.isNotBlank(lotteryId)) {
			if(lotteryId.equals(LotteryId.JCZQ.name()) || 
					lotteryId.equals(LotteryId.CTZC.name()) || 
					lotteryId.equals(LotteryId.BJDC.name())) {
				return ZQ;
			} else if(lotteryId.equals(LotteryId.JCLQ.name())) {
				return LQ;
			}
		}
		return ball;
	}

	private String name;

	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}