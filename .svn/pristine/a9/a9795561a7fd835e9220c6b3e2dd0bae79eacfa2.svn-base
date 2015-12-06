package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户优惠卷扩展PO
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "lt_voucher_user")
public class VoucherUserExtendPO extends VoucherUserPO implements Serializable {

    private static final long serialVersionUID = -8314644574776562323L;
    /** 用户信息 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id" , updatable=false, insertable=false)
	private UserPO user;

	public UserPO getUser() {
		return user;
	}

	public void setUser(UserPO user) {
		this.user = user;
	}
}
