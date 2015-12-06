package com.unison.lottery.pm.entity;

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

/**
 * 晒单跟单中奖排行榜
 * @author yongli zhu
 *
 */
@Entity
@Table(name = "lt_bet_scheme")
public class ShowFollowPO implements Serializable{

	private static final long serialVersionUID = 6489517143868339358L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sponsor_id")
	private Long userId;//用户id
	
	@Column(name = "sponsor")
	private String username;
	
	@Column(name = "after_tax_bonus")
	private BigDecimal afterTaxBonus;
	
	@Column(nullable = false, name = "created_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@Column(name = "is_show_scheme")
	private int showScheme;
	
	@Column(name = "type", updatable = false)
	private int type;
	
	private int status;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getAfterTaxBonus() {
		return afterTaxBonus;
	}

	public void setAfterTaxBonus(BigDecimal afterTaxBonus) {
		this.afterTaxBonus = afterTaxBonus;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getShowScheme() {
		return showScheme;
	}

	public void setShowScheme(int showScheme) {
		this.showScheme = showScheme;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
