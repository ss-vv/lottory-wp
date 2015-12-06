package com.unison.lottery.weibo.data;

import com.xhcms.lottery.lang.LotteryId;

/**
 * 比赛串中的彩种类型字母。
 * 
 * @author Yang Bo
 */
public enum LotteryLetter {
	UNKNOWN(LotteryId.UNKNOWN),	// 未知字母定义
	JZ(LotteryId.JCZQ),
	JL(LotteryId.JCLQ),
	CZ(LotteryId.CTZC)
	;
	
	private LotteryId lotteryId;
	
	private LotteryLetter(LotteryId lotteryId){
		this.lotteryId = lotteryId;
	}
	
	public LotteryId getLotteryId(){
		return this.lotteryId;
	}
	
	/**
	 * 从 letters 字符串构造一个 LotteryLetter 枚举对象。
	 * @param letters
	 * @return UNKNOWN，如果不识别字母串；否则返回枚举对象。
	 */
	public static LotteryLetter fromString(String letters){
		if (letters == null || letters.length()==0) {
			return UNKNOWN;
		}
		try{
			return LotteryLetter.valueOf(letters.toUpperCase());
		}catch(Exception e){
			return UNKNOWN;
		}
	}
}
