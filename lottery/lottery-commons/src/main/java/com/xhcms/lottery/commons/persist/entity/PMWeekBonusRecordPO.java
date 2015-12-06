package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 奖池操作记录po
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "pm_week_bonus_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PMWeekBonusRecordPO implements Serializable{
	
	private static final long serialVersionUID = -7902022977694532638L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 操作金额（单位：元）
	 */
	private BigDecimal amount;
	
	/**
	 * 操作类型 :
	 * 0 派奖 1 注入
	 */
	private int type;
	
	@Column(name="created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	
}
