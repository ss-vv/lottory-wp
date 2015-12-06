package com.xhcms.lottery.commons.persist.entity;

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

@Entity
@Table(name = "lt_user_advice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvicePO {
	
	@Column(nullable= true, name = "user_id")
	private String userId;
	
	@Column(nullable= true, name = "client_version")
	private String clientVersion;
	
	@Column(nullable= true, name = "channel")
	private String channel;
	
	@Column(nullable= true, name = "device_id")
	private String deviceId;
	
	@Column(nullable= true, name = "model_name")
	private String modelName;
	
	@Column(nullable= false, name = "advice")
	private String advice;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getClientVersion() {
		return clientVersion;
	}


	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}


	public String getChannel() {
		return channel;
	}


	public void setChannel(String channel) {
		this.channel = channel;
	}


	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public String getModelName() {
		return modelName;
	}


	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


	public String getAdvice() {
		return advice;
	}


	public void setAdvice(String advice) {
		this.advice = advice;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
}
