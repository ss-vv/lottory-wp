package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Table(name="lt_red_envalope_grab_record")
public class RedEnvalopeGrabRecordPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8581525499066615261L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private Long envalopeId;
	private Long ltUserId;
	private Long envalopeAmount;
	private Date createTime;
	
	@Transient
	private String imageUrl;
	@Transient
	private String nickName;
	
	public RedEnvalopeGrabRecordPO(){
		//default constructer
	}
	public RedEnvalopeGrabRecordPO(Long id,String headImageURL,String nickName,Date createTime,Long envalopeAmount,Long ltUserId){
		this.id = id;
		this.imageUrl = headImageURL;
		this.nickName = nickName;
		this.createTime = createTime;
		this.envalopeAmount = envalopeAmount;
		this.ltUserId = ltUserId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getEnvalopeId() {
		return envalopeId;
	}
	public void setEnvalopeId(Long envalopeId) {
		this.envalopeId = envalopeId;
	}
	public Long getLtUserId() {
		return ltUserId;
	}
	public void setLtUserId(Long ltUserId) {
		this.ltUserId = ltUserId;
	}
	public Long getEnvalopeAmount() {
		return envalopeAmount;
	}
	public void setEnvalopeAmount(Long envalopeAmount) {
		this.envalopeAmount = envalopeAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
