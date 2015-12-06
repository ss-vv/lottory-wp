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
 * wap支付宝充值赠款
 * 
 * @author yongli zhu
 */
@Entity
@Table(name = "pm_wap_recharge_grant")
public class WapRechargeGrantPO implements Serializable{

	private static final long serialVersionUID = -222782463838526262L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_id")
	private long userId; // 用户ID
	
	private String username;
	
	@Column(name = "promotion_id")
	private long promotionId;
	
	@Column(name = "granttype_id")
	private long grantTypeId;

	private BigDecimal amount; // 赠款金额

	@Column(name = "grant_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date grantTime; // 赠款时间

	@Column(name = "operator_id")
	private int operatorId; // 操作人

	@Column(name = "operator_name")
	private String operatorName; // 审核人

	@Column(name = "operator_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatorTime; // 审核时间
		
	private String mobile;//手机号

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(long promotionId) {
		this.promotionId = promotionId;
	}

	public long getGrantTypeId() {
		return grantTypeId;
	}

	public void setGrantTypeId(long grantTypeId) {
		this.grantTypeId = grantTypeId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getGrantTime() {
		return grantTime;
	}

	public void setGrantTime(Date grantTime) {
		this.grantTime = grantTime;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
}
