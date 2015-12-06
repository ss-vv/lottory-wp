package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 奖池po
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "pm_week_bonus_pool")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PMWeekBonusPoolPO implements Serializable{
	
	private static final long serialVersionUID = 6193976728490168149L;

	@Id
	private Long id;
	
	/**
	 * 奖池金额（单位：元）
	 */
	@Column(name = "bonus_pool")
	private BigDecimal bonusPool;
	
	/**
	 * 累计加入奖池金额（单位：元）
	 */
	@Column(name = "total_recharge")
	private BigDecimal totalRecharge;
	
	/**
	 * 累计派奖金额（单位：元）
	 */
	@Column(name = "total_award")
	private BigDecimal totalAward;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBonusPool() {
		return bonusPool;
	}

	public void setBonusPool(BigDecimal bonusPool) {
		this.bonusPool = bonusPool;
	}

	public BigDecimal getTotalRecharge() {
		return totalRecharge;
	}

	public void setTotalRecharge(BigDecimal totalRecharge) {
		this.totalRecharge = totalRecharge;
	}

	public BigDecimal getTotalAward() {
		return totalAward;
	}

	public void setTotalAward(BigDecimal totalAward) {
		this.totalAward = totalAward;
	}
	
}
