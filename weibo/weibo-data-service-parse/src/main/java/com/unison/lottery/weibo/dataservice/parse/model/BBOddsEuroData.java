package com.unison.lottery.weibo.dataservice.parse.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 篮球欧赔
 * 
 * @author Yang Bo
 */
public class BBOddsEuroData {
	private int matchId;
	private int corpId;
	private BigDecimal initHomeWinOdds = BigDecimal.ZERO;
	private BigDecimal initGuestWinOdds = BigDecimal.ZERO;
	private BigDecimal realtimeHomeWinOdds = BigDecimal.ZERO;
	private BigDecimal realtimeGuestWinOdds = BigDecimal.ZERO;
	private Date createTime;
	private Date updateTime;
	
	public int getMatchId() {
		return matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
	public int getCorpId() {
		return corpId;
	}
	public void setCorpId(int corpId) {
		this.corpId = corpId;
	}
	public BigDecimal getInitHomeWinOdds() {
		return initHomeWinOdds;
	}
	public void setInitHomeWinOdds(BigDecimal initHomeWinOdds) {
		this.initHomeWinOdds = initHomeWinOdds;
	}
	public BigDecimal getInitGuestWinOdds() {
		return initGuestWinOdds;
	}
	public void setInitGuestWinOdds(BigDecimal initGuestWinOdds) {
		this.initGuestWinOdds = initGuestWinOdds;
	}
	public BigDecimal getRealtimeHomeWinOdds() {
		return realtimeHomeWinOdds;
	}
	public void setRealtimeHomeWinOdds(BigDecimal realtimeHomeWinOdds) {
		this.realtimeHomeWinOdds = realtimeHomeWinOdds;
	}
	public BigDecimal getRealtimeGuestWinOdds() {
		return realtimeGuestWinOdds;
	}
	public void setRealtimeGuestWinOdds(BigDecimal realtimeGuestWinOdds) {
		this.realtimeGuestWinOdds = realtimeGuestWinOdds;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
