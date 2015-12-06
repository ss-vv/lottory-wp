package com.xhcms.lottery.admin.web.action.voucher;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.Voucher;
import com.xhcms.lottery.commons.persist.service.VoucherService;

/**
 * 优惠劵种类列表
 * @author Wang Lei
 *
 */
public class ListSourceVoucherAction extends BaseListAction {
	private static final long serialVersionUID = 1L;
    @Autowired
    private VoucherService voucherService;
	private Voucher voucher;
	
	@Override
    public String execute(){
		init();
		voucherService.list(paging, voucher);
		return SUCCESS;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
}
