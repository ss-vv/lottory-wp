package com.unison.lottery.weibo.mq;

import java.io.Serializable;

public class OddsData implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -3055712030117343635L;

	private String corpId; //公司id
	
	private String lotteryId; //彩种类型 ("JCLQ"|"JCZQ")
	private String oddsType; //赔率类型 (europe、asia、bigsmall)
	
	private String matchId;
	private String data;
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getOddsType() {
		return oddsType;
	}
	public void setOddsType(String oddsType) {
		this.oddsType = oddsType;
	}
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
