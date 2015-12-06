package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 优惠卷信息PO
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "lt_voucher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VoucherPO implements Serializable {

    private static final long serialVersionUID = -8314644574776562323L;

    public VoucherPO(){
    }
    
    @Id
    private String id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String imgUrl;
    
    /**
     * 基础金额
     */
    @Column(nullable = false, updatable=false)
    private BigDecimal price = new BigDecimal(0);
    
    /**
     * 面值
     */
    @Column(nullable = false, updatable=false)
    private BigDecimal grant = new BigDecimal(0);
    
    /** 类型 recharge,充值类*/
    @Column(nullable = false, updatable=false)
    private String type;

    /** 使用范围 */
    @Column(name = "limit")
    private String limit;
    
    /** 使用说明 */
    @Column(nullable = false)
    private String instructions;
    
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "create_time", updatable=false)
    private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getGrant() {
		return grant;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setGrant(BigDecimal grant) {
		this.grant = grant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
