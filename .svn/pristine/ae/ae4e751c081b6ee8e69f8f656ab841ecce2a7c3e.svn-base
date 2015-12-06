package com.unison.lottery.weibo.dataservice.parse.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @desc 篮球让分赔率
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-12
 * @version 1.0
 */
public class BBOddsConcedeData {
	
//	private long id;					// 主键
	private long matchId;				// 球探网比赛id
	private long corpId;				// 博彩公司id, 精选的公司id
	private BigDecimal initHandicap = BigDecimal.ZERO;	// 初盘盘口
	private BigDecimal initHomeOdds = BigDecimal.ZERO;	// 初盘主队赔率
	private BigDecimal initGuestOdds = BigDecimal.ZERO;	// 初盘客队赔率
	private BigDecimal curHandicap = BigDecimal.ZERO;		// 即时盘盘口
	private BigDecimal curHomeOdds = BigDecimal.ZERO;		// 即时盘主队赔率
	private BigDecimal curGuestOdds = BigDecimal.ZERO;		// 即时盘客队赔率
	private BigDecimal groundHandicap = BigDecimal.ZERO;	//走地盘口
	private BigDecimal homeGroundOdds = BigDecimal.ZERO;	//主队走地盘口
	private BigDecimal guestGroundOdds = BigDecimal.ZERO;	//客队走地盘口
	private Date createTime;			// 创建时间
	private Date updateTime;			// 更新时间
	
	
	public long getMatchId() {
		return matchId;
	}
	
	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}
	
	public long getCorpId() {
		return corpId;
	}
	
	public void setCorpId(long corpId) {
		this.corpId = corpId;
	}
	
	public BigDecimal getInitHandicap() {
		return initHandicap;
	}
	
	public void setInitHandicap(BigDecimal initHandicap) {
		this.initHandicap = initHandicap;
	}
	
	public BigDecimal getInitHomeOdds() {
		return initHomeOdds;
	}
	
	public void setInitHomeOdds(BigDecimal initHomeOdds) {
		this.initHomeOdds = initHomeOdds;
	}
	
	public BigDecimal getInitGuestOdds() {
		return initGuestOdds;
	}
	
	public void setInitGuestOdds(BigDecimal initGuestOdds) {
		this.initGuestOdds = initGuestOdds;
	}
	
	public BigDecimal getCurHandicap() {
		return curHandicap;
	}
	
	public void setCurHandicap(BigDecimal curHandicap) {
		this.curHandicap = curHandicap;
	}
	
	public BigDecimal getCurHomeOdds() {
		return curHomeOdds;
	}
	
	public void setCurHomeOdds(BigDecimal curHomeOdds) {
		this.curHomeOdds = curHomeOdds;
	}
	
	public BigDecimal getCurGuestOdds() {
		return curGuestOdds;
	}
	
	public void setCurGuestOdds(BigDecimal curGuestOdds) {
		this.curGuestOdds = curGuestOdds;
	}
	
	public BigDecimal getGroundHandicap() {
		return groundHandicap;
	}
	
	public void setGroundHandicap(BigDecimal groundHandicap) {
		this.groundHandicap = groundHandicap;
	}
	
	public BigDecimal getHomeGroundOdds() {
		return homeGroundOdds;
	}
	
	public void setHomeGroundOdds(BigDecimal homeGroundOdds) {
		this.homeGroundOdds = homeGroundOdds;
	}
	
	public BigDecimal getGuestGroundOdds() {
		return guestGroundOdds;
	}
	
	public void setGuestGroundOdds(BigDecimal guestGroundOdds) {
		this.guestGroundOdds = guestGroundOdds;
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