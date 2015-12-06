package com.xhcms.lottery.admin.web.action.recharge;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.RechargeService;
import com.xhcms.lottery.admin.web.action.BaseAction;

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
public class AuditAction extends BaseAction {
	private static final long serialVersionUID = -5380657763399711385L;
	@Autowired
	private RechargeService rechargeService;
	
	private long id;
	private String note;
	
	/**
     * 通过审核、确认
     */
	@Override
	public String execute() {
		rechargeService.pass(id, getMyId(), note);
		return SUCCESS;
	}
	
	/**
     * 驳回重审
     * @return
     */
	public String reject() {
	    rechargeService.reject(id, getMyId(), note);
	    return SUCCESS;
	}
	
	/**
	 * 直接驳回
	 * @return
	 */
	public String fail() {
	    rechargeService.fail(id, getMyId(), note);
	    return SUCCESS;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
