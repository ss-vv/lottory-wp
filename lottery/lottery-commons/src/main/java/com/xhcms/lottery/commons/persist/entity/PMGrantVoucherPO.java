package com.xhcms.lottery.commons.persist.entity;

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
 * @author zhuyongli 活动赠送优惠券表
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "pm_grant_voucher")
public class PMGrantVoucherPO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "voucher_pm_id", nullable = false)
	private Long voucherPmId;

	@Column(name = "granttype_id", nullable = false)
	private int granttypeId;

	@Column(name = "voucher_type", unique = true)
	private String voucherType;

	@Column(name = "voucher_id", nullable = false)
	private String voucherId;

	@Column(name = "pm_week", nullable = false)
	private Long pmWeek;
	
	@Column(name = "created_time", nullable = false)
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

	public Long getVoucherPmId() {
		return voucherPmId;
	}

	public void setVoucherPmId(Long voucherPmId) {
		this.voucherPmId = voucherPmId;
	}

	public int getGranttypeId() {
		return granttypeId;
	}

	public void setGranttypeId(int granttypeId) {
		this.granttypeId = granttypeId;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getPmWeek() {
		return pmWeek;
	}

	public void setPmWeek(Long pmWeek) {
		this.pmWeek = pmWeek;
	}

}
