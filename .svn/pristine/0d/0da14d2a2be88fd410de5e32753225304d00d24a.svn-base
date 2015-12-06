package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 优惠卷类型PO
 * @author Wang Lei
 */
@Entity
@Table(name = "lt_voucher_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VoucherTypePO implements Serializable {

	private static final long serialVersionUID = -706322443550696686L;

	public VoucherTypePO(){
    }
    
    @Id
    private String type;

    @Column(nullable = false)
    private String name;
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
