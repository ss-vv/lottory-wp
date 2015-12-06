package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 每次注入奖金po
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "pm_week_bonus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PMWeekBonusPO implements Serializable{
	
	private static final long serialVersionUID = 981898419090165762L;

	@Id
	private Long id;
	
	/**
	 * 每次注入奖池的金额（单位：元）
	 */
	private int amount;
	
	/**
	 * 状态：
	 * 0，有效
	 * 1，无效
	 */
	private int status;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
