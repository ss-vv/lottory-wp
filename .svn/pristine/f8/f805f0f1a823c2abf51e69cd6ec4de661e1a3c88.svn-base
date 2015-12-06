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

/**
 * 充值返还彩金PO
 * 
 * @author yongli zhu
 */
@Entity
@Table(name = "pm_recharge_redeemed")
public class PMRechargeRedeemedPO implements Serializable{

	private static final long serialVersionUID = 6817134046649382796L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId; // 用户ID
	
	private String username;

	@Column(name = "promotion_id")
	private Long promotionId;
	
	@Column(name = "granttype_id")
	private Long grantTypeId;
	
	@Column(name = "pm_recharge_id")
	private Long pmRechargeId;

	private BigDecimal amount; // 赠款金额

	@Column(name = "created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime; // 赠款时间

	@Column(name = "operator_id")
	private int operatorId; // 操作人

	@Column(name = "operator_name")
	private String operatorName; // 审核人

	@Column(name = "operate_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operateTime; // 审核时间
	
	private int status;

	@Column(name = "id_number")
	private String idNumber;//身份证
		
	private String mobile;//手机号
	
	private String email;//邮箱

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
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public Long getGrantTypeId() {
		return grantTypeId;
	}

	public void setGrantTypeId(Long grantTypeId) {
		this.grantTypeId = grantTypeId;
	}

	public Long getPmRechargeId() {
		return pmRechargeId;
	}

	public void setPmRechargeId(Long pmRechargeId) {
		this.pmRechargeId = pmRechargeId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
