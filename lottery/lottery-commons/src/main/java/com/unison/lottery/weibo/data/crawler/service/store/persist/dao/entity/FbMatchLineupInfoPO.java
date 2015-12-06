package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 赛事分析
 * 赛事 事件
 * @author guixiangcui
 *
 */
@Entity
@Table(name="md_qt_match_lineup_base")
public class FbMatchLineupInfoPO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5901918365783196141L;
	@Id
	private long id;
	private Integer bsId;
	private Integer source; //来源：1球探
	private Integer processStatus; //是否已处理
	
	private String zdsf; //主队首发
	private String zdtb; //主队替补
	private String zds; //主队伤员
	private String zdt;  //主队停赛
	
	private String kdsf; //客队首发
	private String kdtb; //客队替补
	private String kds; //客队伤员
	private String kdt;  //客队停赛
	
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
	public String getZdsf() {
		return zdsf;
	}
	public void setZdsf(String zdsf) {
		this.zdsf = zdsf;
	}
	public String getZdtb() {
		return zdtb;
	}
	public void setZdtb(String zdtb) {
		this.zdtb = zdtb;
	}
	public String getZds() {
		return zds;
	}
	public void setZds(String zds) {
		this.zds = zds;
	}
	public String getZdt() {
		return zdt;
	}
	public void setZdt(String zdt) {
		this.zdt = zdt;
	}
	public String getKdsf() {
		return kdsf;
	}
	public void setKdsf(String kdsf) {
		this.kdsf = kdsf;
	}
	public String getKdtb() {
		return kdtb;
	}
	public void setKdtb(String kdtb) {
		this.kdtb = kdtb;
	}
	public String getKds() {
		return kds;
	}
	public void setKds(String kds) {
		this.kds = kds;
	}
	public String getKdt() {
		return kdt;
	}
	public void setKdt(String kdt) {
		this.kdt = kdt;
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
