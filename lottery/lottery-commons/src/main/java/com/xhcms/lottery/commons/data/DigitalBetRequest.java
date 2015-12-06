package com.xhcms.lottery.commons.data;

import java.util.List;

import com.xhcms.lottery.commons.lang.Channel;
import com.xhcms.lottery.commons.lang.Partner;
import com.xhcms.lottery.lang.PlayType;

/**
 * 数字彩投注请求。
 * 
 * @author Yang Bo
 */
public class DigitalBetRequest extends BetRequest {

	// 玩法，顺序同 betContents.
	private List<PlayType> playTypeList;

	// 投注的生成方式
	private ChooseType chooseType;
	
	// 胆码
	private List<Integer> mustDigital;
	
	// 托码
	private List<Integer> appendDigital;
	
	// 渠道，对应 lt_bet_scheme 中的字段 channel, @see com.xhcms.lottery.commons.lang.Channel
	private String channel = Channel.UNKNOWN;
	
	// 合作方，对应 lt_bet_scheme 中的字段 partner, @see com.xhcms.lottery.commons.lang.Partner
	private String partner = Partner.UNKOWN;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public ChooseType getChooseType() {
		return chooseType;
	}

	public void setChooseType(ChooseType chooseType) {
		this.chooseType = chooseType;
	}

	public List<Integer> getMustDigital() {
		return mustDigital;
	}

	public void setMustDigital(List<Integer> mustDigital) {
		this.mustDigital = mustDigital;
	}

	public List<Integer> getAppendDigital() {
		return appendDigital;
	}

	public void setAppendDigital(List<Integer> appendDigital) {
		this.appendDigital = appendDigital;
	}
	
	public List<PlayType> getPlayTypeList() {
		return playTypeList;
	}

	public void setPlayTypeList(List<PlayType> playTypeList) {
		this.playTypeList = playTypeList;
	}
}
