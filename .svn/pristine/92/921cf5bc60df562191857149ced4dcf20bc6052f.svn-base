package com.xhcms.lottery.commons.persist.entity;

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
 * 赠款
 * 
 * @author langhsu
 */
@Entity
@Table(name = "lt_grant")
public class GrantPO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_id")
	private long userId; // 用户ID
	
	@Column(name = "order_id")
	private long orderId;

	private BigDecimal amount; // 赠款金额

	@Column(name = "created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime; // 创建时间

	@Column(name = "operator_id")
	private int operatorId; // 操作人

	@Column(name = "audit_id")
	private int auditId; // 审核人

	@Column(name = "audit_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditTime; // 审核时间

	private String note; // 备注

	private int status; // 状态
	
	@Column(name = "granttype_id")
	private long grantTypeId; //赠款类型

	public long getGrantTypeId() {
		return grantTypeId;
	}

	public void setGrantTypeId(long grantTypeId) {
		this.grantTypeId = grantTypeId;
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

	public int getAuditId() {
		return auditId;
	}

	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

}
