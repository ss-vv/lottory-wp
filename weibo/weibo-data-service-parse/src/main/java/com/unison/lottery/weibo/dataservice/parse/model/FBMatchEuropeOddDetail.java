package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FBMatchEuropeOddDetail {
	
	private String corpId;
	private String corpName;
	private String initHomeWin;
	private String initDraw;
	private String initGuestWin; 
	private String homeWin;
	private String draw;
	private String guestWin;
	private Date changeTime;
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getInitHomeWin() {
		return initHomeWin;
	}
	public void setInitHomeWin(String initHomeWin) {
		this.initHomeWin = initHomeWin;
	}
	public String getInitDraw() {
		return initDraw;
	}
	public void setInitDraw(String initDraw) {
		this.initDraw = initDraw;
	}
	public String getInitGuestWin() {
		return initGuestWin;
	}
	public void setInitGuestWin(String initGuestWin) {
		this.initGuestWin = initGuestWin;
	}
	public String getHomeWin() {
		return homeWin;
	}
	public void setHomeWin(String homeWin) {
		this.homeWin = homeWin;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public String getGuestWin() {
		return guestWin;
	}
	public void setGuestWin(String guestWin) {
		this.guestWin = guestWin;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	} 
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
