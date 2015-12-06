package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


/**
 * 赛事分析
 * 赛事 事件 技术统计
 * 
 * @author guixiangcui
 *
 */
@Entity
@Table(name="md_qt_match_statistic_base")
public class FbMatchStatisticInfoPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -14574404745580505L;
	@Id
	private long id;
	private Integer bsId;
	private Integer source; //来源：1球探
	private Integer processStatus; //是否已处理
	public String getZd() {
		return zd;
	}
	public void setZd(String zd) {
		this.zd = zd;
	}
	public String getKd() {
		return kd;
	}
	public void setKd(String kd) {
		this.kd = kd;
	}
	private String zd; //主队
	private String kd; //客队
	private Integer eventType; //子联赛简体名称
	@Type(type = "timestamp")
	private Date createTime;
	@Type(type = "timestamp")
	private Date updateTime;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public Integer getEventType() {
		return eventType;
	}
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
