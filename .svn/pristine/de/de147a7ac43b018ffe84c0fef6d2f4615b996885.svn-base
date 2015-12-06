package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table; 

import org.hibernate.annotations.Type;

/**
 * 篮球比赛 欧赔数据
 * @author guixiangcui
 *
 */
@Entity
@Table(name="md_qt_basket_odds_euro_base")
public class BasketMatchOpOddsInfoPO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1429591405676496992L;
	@Id
	private long id;
	private Integer bsId;
	private Integer source; //来源：1球探
	private Integer processStatus; //是否已处理
	
	private String corpId; //公司id
	private double HomeWinOdds; //主胜赔率
	private double GuestWinOdds;  //客胜赔率
	private String corpName;
	@Type(type = "timestamp")
	private Date timestamp;//时间点
	
	@Type(type = "timestamp")
	private Date createTime;
	@Type(type = "timestamp")
	private Date updateTime;
	
	
	public BasketMatchOpOddsInfoPO(Integer bsId,String corpId,double homeWinOdds,double guestWinOdds,Date timestamp){
		this.bsId = bsId;
		this.corpId = corpId;
		this.HomeWinOdds = homeWinOdds;
		this.GuestWinOdds = guestWinOdds;
		this.timestamp = timestamp;
	}
	
	public BasketMatchOpOddsInfoPO(double homeWinOdds,double guestWinOdds,Date timestamp){
		this.HomeWinOdds = homeWinOdds;
		this.GuestWinOdds = guestWinOdds;
		this.timestamp = timestamp;
	}
	//default constructer
	public BasketMatchOpOddsInfoPO(){
		
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public double getHomeWinOdds() {
		return HomeWinOdds;
	}
	public void setHomeWinOdds(double homeWinOdds) {
		HomeWinOdds = homeWinOdds;
	}
	public double getGuestWinOdds() {
		return GuestWinOdds;
	}
	public void setGuestWinOdds(double guestWinOdds) {
		GuestWinOdds = guestWinOdds;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public Integer getBsId() {
		return bsId;
	}
	public void setBsId(Integer bsId) {
		this.bsId = bsId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
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
