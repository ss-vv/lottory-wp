package com.xhcms.lottery.account.web.action.voucher;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

/**
 * 查询无效优惠劵
 * @author Wang Lei
 *
 */
public class ListInvalidVoucherAction  extends BaseListAction{
private static final long serialVersionUID = 1L;
	
	@Autowired
	private VoucherUserService voucherUserService;
	
	@Override
	public String execute() {
		init();
		voucherUserService.listExpireByUserid(paging, getUserId());
		return SUCCESS;
	}
}
