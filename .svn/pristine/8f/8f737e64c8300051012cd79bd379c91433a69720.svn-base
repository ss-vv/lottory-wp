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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
@Entity
@Table(name = "lt_recharge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RechargePO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_id", updatable = false)
	private long userId;
	
	@Column(nullable = false, name = "username", updatable = false)
    private String username;

	@Column(name = "channel_id")
	private int channelId;

	@Column(name = "real_name")
	private String realName;

	@Column(name = "ip")
	private String ip;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "total_fee")
	private BigDecimal totalFee;

	@Column(name = "channel_fee")
	private BigDecimal channelFee;

	@Column(name = "pay_amount")
	private BigDecimal payAmount;

	@Column(name = "bank_order")
	private String bankOrder;

	@Column(name = "channel_code")
	private String channelCode;

	@Column(name = "pay_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date payTime;

	@Column(name = "created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(name = "audit_id")
	private int auditId;

	@Column(name = "audit_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditTime;

	@Column(name = "confirm_id")
	private int confirmId;

	@Column(name = "confirm_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date confirmTime;

	@Column(name = "status")
	private int status;
	
	private String note;

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

    public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getChannelFee() {
        return channelFee;
    }

    public void setChannelFee(BigDecimal channelFee) {
        this.channelFee = channelFee;
    }

    public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getBankOrder() {
		return bankOrder;
	}

	public void setBankOrder(String bankOrder) {
		this.bankOrder = bankOrder;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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

	public int getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(int confirmId) {
		this.confirmId = confirmId;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
	
}
