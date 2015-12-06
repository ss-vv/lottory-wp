package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 欧赔
 * 
 * @author Yang Bo
 */
@Entity
@Table(name = "md_fb_odds_euro")
public class FBEuroOddsPO implements Serializable {
	private static final long serialVersionUID = -1121628376803029698L;
	
	@Id
	private long id;						// 主键
	private long matchId;					// 球探网比赛id
	private long corpId;					// 博彩公司id
	
	private BigDecimal initHomeWinOdds;		// 初盘主胜赔率
	private BigDecimal initDrawOdds;		// 初盘和局赔率
	private BigDecimal initGuestWinOdds;	// 初盘客胜赔率
	
	private BigDecimal curHomeWinOdds;		// 即时盘主胜赔率
	private BigDecimal curDrawOdds;			// 即时盘和局赔率
	private BigDecimal curGuestWinOdds;		// 即时盘客胜赔率
	
	private Date createTime;				// 创建时间
	private Date updateTime;				// 更新时间

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public BigDecimal getInitHomeWinOdds() {
		return initHomeWinOdds;
	}
	public void setInitHomeWinOdds(BigDecimal initHomeWinOdds) {
		this.initHomeWinOdds = initHomeWinOdds;
	}
	public BigDecimal getInitDrawOdds() {
		return initDrawOdds;
	}
	public void setInitDrawOdds(BigDecimal initDrawOdds) {
		this.initDrawOdds = initDrawOdds;
	}
	public BigDecimal getInitGuestWinOdds() {
		return initGuestWinOdds;
	}
	public void setInitGuestWinOdds(BigDecimal initGuestWinOdds) {
		this.initGuestWinOdds = initGuestWinOdds;
	}
	public BigDecimal getCurHomeWinOdds() {
		return curHomeWinOdds;
	}
	public void setCurHomeWinOdds(BigDecimal curHomeWinOdds) {
		this.curHomeWinOdds = curHomeWinOdds;
	}
	public BigDecimal getCurDrawOdds() {
		return curDrawOdds;
	}
	public void setCurDrawOdds(BigDecimal curDrawOdds) {
		this.curDrawOdds = curDrawOdds;
	}
	public BigDecimal getCurGuestWinOdds() {
		return curGuestWinOdds;
	}
	public void setCurGuestWinOdds(BigDecimal curGuestWinOdds) {
		this.curGuestWinOdds = curGuestWinOdds;
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
	
}
