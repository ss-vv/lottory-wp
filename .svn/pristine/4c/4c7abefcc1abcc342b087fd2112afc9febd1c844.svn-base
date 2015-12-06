package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table; 

import org.hibernate.annotations.Type;

/**
 * 比赛 欧赔数据
 * @author guixiangcui
 *
 */
@Entity
@Table(name="md_qt_odds_euro_base")
public class FbMatchOpOddsInfoPO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1429591405676496992L;
	@Id
	private long id;
	private Long bsId;
	private Integer source; //来源：1球探
	private Integer processStatus; //是否已处理
	
	private String corpId; //公司id
	private double HomeWinOdds; //主胜赔率
	private double DrawOdds; //平赔率
	private double GuestWinOdds;  //客胜赔率
	
	private String euroOdds;
	private String kellyIndex;
	private String changeTime; //变化时间点
	
	@Type(type = "timestamp")
	private Date timestamp;//时间点
	
	@Type(type = "timestamp")
	private Date createTime;
	@Type(type = "timestamp")
	private Date updateTime;
	
	
	public FbMatchOpOddsInfoPO(){
		
	}
	public FbMatchOpOddsInfoPO(Long bsId,String corpId,String euroOdds,String changeTime){
		this.bsId = bsId;
		this.corpId = corpId;
		this.euroOdds = euroOdds;
		this.changeTime = changeTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getEuroOdds() {
		return euroOdds;
	}
	public void setEuroOdds(String euroOdds) {
		this.euroOdds = euroOdds;
	}
	public String getKellyIndex() {
		return kellyIndex;
	}
	public void setKellyIndex(String kellyIndex) {
		this.kellyIndex = kellyIndex;
	}
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	public Long getBsId() {
		return bsId;
	}
	public void setBsId(Long bsId) {
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
	public double getDrawOdds() {
		return DrawOdds;
	}
	public void setDrawOdds(double drawOdds) {
		DrawOdds = drawOdds;
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
