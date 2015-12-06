package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 用户优惠卷PO
 * @author Wang Lei
 *
 */
@MappedSuperclass
@Table(name = "lt_voucher_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VoucherUserPO implements Serializable {

    private static final long serialVersionUID = -8314644574776562323L;

    public VoucherUserPO(){
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 优惠卷信息 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "voucher_id", updatable=false)
	private VoucherPO voucher;

    /** 用户id */
    @Column(nullable = false, name = "user_id", updatable=false)
    private Long userId;
    
    /** 赠款类型 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "granttype_id", updatable=false)
    private GrantTypePO grantType;
    
    /** 充值流水id */
    @Column(name = "recharge_id")
    private Long rechargeId;
    
    /**
     * 生效时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "effect_time", updatable=false)
    private Date effectTime;
    
    /**
     * 失效时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "dead_time", updatable=false)
    private Date deadTime;
    
    /**
     * 锁定时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lock_time")
    private Date lockTime;
    
    /**
     * 使用时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "service_time")
    private Date serviceTime;
    
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "create_time", updatable=false)
    private Date createTime;
    
    /**
     * 状态  0,有效  1,锁定 2,已使用  3,已失效
     */
    @Column(nullable = false)
    private Integer status;
    
    @Version
    private Integer version;
    
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

	public Date getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public VoucherPO getVoucher() {
		return voucher;
	}

	public void setVoucher(VoucherPO voucher) {
		this.voucher = voucher;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Long getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(Long rechargeId) {
		this.rechargeId = rechargeId;
	}

	public GrantTypePO getGrantType() {
		return grantType;
	}

	public void setGrantType(GrantTypePO grantType) {
		this.grantType = grantType;
	}

}
