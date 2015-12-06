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
@Table(name = "lt_withdraw")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WithdrawPO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, name = "user_id", updatable = false)
	private long userId;
	
	@Column(nullable = false, name = "username", updatable = false)
	private String username;

	@Column(name = "channel_id")
	private int channelId; // 提现渠道ID

	@Column(name = "real_name")
	private String realName; // 用户真实姓名

	@Column(name = "bank")
	private String bank; // 提现开户银行

	@Column(name = "bank_account")
	private String bankAccount; // 提现帐号

	@Column(name = "ip")
	private String ip; // 申请提现IP

	@Column(name = "regist_ip")
	private String registIp;   //用户注册IP
	
	@Column(name = "province") 
	private String province;   //提现省市
	
	@Column(name = "city")
	private String city;   // 提现地区
	
	@Column(name = "amount")
	private BigDecimal amount; // 提现金额

	@Column(name = "total_fee")
	private BigDecimal totalFee; // 提现总手续费

	@Column(name = "bank_fee")
	private BigDecimal bankFee; // 银行手续费

	@Column(name = "pay_amount")
	private BigDecimal payAmount; // 支付金额

	@Column(name = "bank_order")
	private String bankOrder; // 银行订单编号

	@Column(name = "created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(name = "audit_id")
	private int auditId; // 审核人ID

	@Column(name = "audit_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditTime;

	@Column(name = "confirm_id")
	private int confirmId; // 最后确认人ID

	@Column(name = "confirm_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date confirmTime;
	
	private String note;

	@Column(name = "status", length = 5)
	private int status;

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

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBankOrder() {
		return bankOrder;
	}

	public void setBankOrder(String bankOrder) {
		this.bankOrder = bankOrder;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getBankFee() {
		return bankFee;
	}

	public void setBankFee(BigDecimal bankFee) {
		this.bankFee = bankFee;
	}

    public String getRegistIp() {
        return registIp;
    }

    public void setRegistIp(String registIp) {
        this.registIp = registIp;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
	
}
