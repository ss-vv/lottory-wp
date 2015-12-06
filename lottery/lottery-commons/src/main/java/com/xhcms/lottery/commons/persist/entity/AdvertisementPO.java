package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
 * 新首页广告类
 * @author haohao
 *
 */
@Entity
@Table(name="advertisement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvertisementPO implements Serializable{

	private static final long serialVersionUID = -3848690022805127306L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="pic_path")
	private String picPath;
	
	@Column(name="href_link")
	private String hrefLink;
	
	@Column(name="created_time")
	@Temporal (TemporalType.TIMESTAMP) 
	private Date createdTime;
	
	@Column(name="update_time")
	@Temporal (TemporalType.TIMESTAMP) 
	private Date updateTime;
	
	@Column(name="status")
	private Integer status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getHrefLink() {
		return hrefLink;
	}
	public void setHrefLink(String hrefLink) {
		this.hrefLink = hrefLink;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
