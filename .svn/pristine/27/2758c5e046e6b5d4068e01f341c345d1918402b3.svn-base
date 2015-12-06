package com.xhcms.lottery.commons.persist.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "lt_user_validid")
public class ValidIdPO {
	
	@Id
	@Column(name = "user_id")
	private Long UserId;
	
	@Column(name = "valid_id", nullable = false)
	private String validId;
	
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Column(name = "expired_time", nullable = false)
	private Date expiredTime;
	
	public String getValidId() {
		return validId;
	}
	public void setValidId(String validId) {
		this.validId = validId;
	}
	public Long getUserId() {
		return UserId;
	}
	public void setUserId(Long userId) {
		UserId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

}
