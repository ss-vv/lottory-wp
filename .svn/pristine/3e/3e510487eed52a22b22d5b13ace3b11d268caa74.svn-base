package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.Date;

import com.xhcms.lottery.commons.persist.entity.GrantTypePO;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;

/**
 * 优惠卷DO
 * @author Wang Lei
 *
 */
public class VoucherUser implements Serializable {

    private static final long serialVersionUID = -8314644574776562323L;

    public VoucherUser(){
    	
    }
    
    private Long id;
    
    /** 优惠卷详情 */
	private VoucherPO voucher;

    /** 用户id */
    private Long userId;
    
    /** 赠款类型id */
    private GrantTypePO grantType;
    
    /** 充值流水id */
    private Long rechargeId;
    
    /**
     * 生效时间
     */
    private Date effectTime;
    
    /**
     * 失效时间
     */
    private Date deadTime;
    
    /**
     * 使用时间
     */
    private Date serviceTime;
    
    /**
     * 锁定时间
     */
    private Date lockTime;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 状态 0,有效 1,已使用  2,已失效
     */
    private Integer status;
    
    private Integer version;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public VoucherPO getVoucher() {
		return voucher;
	}

	public void setVoucher(VoucherPO voucher) {
		this.voucher = voucher;
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

	public GrantTypePO getGrantType() {
		return grantType;
	}

	public void setGrantType(GrantTypePO grantType) {
		this.grantType = grantType;
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

}
