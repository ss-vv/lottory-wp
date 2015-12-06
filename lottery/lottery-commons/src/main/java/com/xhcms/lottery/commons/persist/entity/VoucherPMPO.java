package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 优惠卷活动信息PO
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "lt_voucher_pm")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VoucherPMPO implements Serializable {

    private static final long serialVersionUID = -8314644574776562323L;

    public VoucherPMPO(){
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    /** 赠款类型 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "grant_type_id", updatable=false)
    private GrantTypePO grantType;
    
    /**
     * 有效时间类型 limit,范围有效   fromCreate,从创建开始有效
     */
    @Column(nullable = false, name="valid_time_type", updatable=false)
    private String validTimeType;
    
    /** 从创建日期开始多少天内 */
    @Column(name="from_create_day", updatable=false)
    private Integer fromCreateday;
    
    /**
     * 生效时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effect_time", updatable=false)
    private Date effectTime;
    
    /**
     * 失效时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dead_time", updatable=false)
    private Date deadTime;
    
    /**
     * 活动开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pm_begin_time", updatable=false)
    private Date pmBeginTime;
    
    /**
     * 活动结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pm_end_time", updatable=false)
    private Date pmEndTime;
    
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "create_time", updatable=false)
    private Date createTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValidTimeType() {
		return validTimeType;
	}

	public void setValidTimeType(String validTimeType) {
		this.validTimeType = validTimeType;
	}

	public Integer getFromCreateday() {
		return fromCreateday;
	}

	public void setFromCreateday(Integer fromCreateday) {
		this.fromCreateday = fromCreateday;
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public Date getDeadTime() {
		return deadTime;
	}

	public void setDeadTime(Date deadTime) {
		this.deadTime = deadTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GrantTypePO getGrantType() {
		return grantType;
	}

	public void setGrantType(GrantTypePO grantType) {
		this.grantType = grantType;
	}

	public Date getPmBeginTime() {
		return pmBeginTime;
	}

	public void setPmBeginTime(Date pmBeginTime) {
		this.pmBeginTime = pmBeginTime;
	}

	public Date getPmEndTime() {
		return pmEndTime;
	}

	public void setPmEndTime(Date pmEndTime) {
		this.pmEndTime = pmEndTime;
	}
}
