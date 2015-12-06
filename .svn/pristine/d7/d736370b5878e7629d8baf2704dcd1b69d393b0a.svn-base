package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 中奖记录po
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "pm_week_winners_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PMWeekWinnersRecordPO implements Serializable{
	
	private static final long serialVersionUID = 7002536778869132640L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "username")
	private String userName;
	
	@OneToOne
	private BetSchemePO scheme;
	
	@Column(name = "granttype_id")
	private Long granttypeId;
	
	/**
	 * 最大奖金（单位：元）
	 */
	@Column(name = "max_amount")
	private BigDecimal maxAmount;
	
	/**
	 * 实际奖金（单位：元）
	 */
	@Column(name = "real_amount")
	private BigDecimal realAmount;
	
	/**
	 * 状态：0，未处理  1，已领奖 2，已过期
	 */
	private int status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;
	
	/**
	 * 操作时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="operator_time")
	private Date operatorTime;

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

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getGranttypeId() {
		return granttypeId;
	}

	public void setGranttypeId(Long granttypeId) {
		this.granttypeId = granttypeId;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	public BetSchemePO getScheme() {
		return scheme;
	}

	public void setScheme(BetSchemePO scheme) {
		this.scheme = scheme;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	
}
