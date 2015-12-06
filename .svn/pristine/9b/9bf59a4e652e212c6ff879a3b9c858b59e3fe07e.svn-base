package com.davcai.lottery.push.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map; 

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 即时赔率推送信息
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年2月25日上午11:15:37
 */
@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PushLiveOdds extends PushMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5309836288972010134L;

	private String corpId; //公司id
	
	private LotteryType lotteryId; //彩种类型 ("JCLQ"|"JCZQ")
	private OddsType oddsType; //赔率类型 (europe、asia、bigsmall)
	
	private static final String DEFAULT_ODDS_CHANNEL_PREFIX = "/publish/odds/";
	public String matchId;
	private String oddsData;
	@JsonIgnore
	public OddsDataMessage data;

	//标志数据是否为初始化数据
	public String init;
	
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public LotteryType getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(LotteryType lotteryId) {
		this.lotteryId = lotteryId;
	}
	public OddsType getOddsType() {
		return oddsType;
	}
	public void setOddsType(OddsType oddsType) {
		this.oddsType = oddsType;
	}
	
	@Override
	public String makeChannelName(){
		return DEFAULT_ODDS_CHANNEL_PREFIX+lotteryId.toString();
	}

	@Override
	public boolean isSame(PushMessage oldPushMessage){
		return true;
	}
	
	@Override
	protected void makeExtendDataMap(Map<String, Object> data) {
		data.put("matchId", matchId);
		data.put("corpId", corpId);
		data.put("oddsType", oddsType);
		data.put("time", this.data.getTime());
		data.put("data", (this.data.getNowWinOdds()+","+this.data.getNowDrawOdds()+","+this.data.getNowLoseOdds()));
	}
	
	public String getOddsData() {
		String oddsData = data.getData();
		return oddsData;
	}
	public void setOddsData(String oddsData) {
		this.oddsData = oddsData;
	}
	@Override
	public MessageType getType(){
		return MessageType.ODDS;
	}
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	
	
}
