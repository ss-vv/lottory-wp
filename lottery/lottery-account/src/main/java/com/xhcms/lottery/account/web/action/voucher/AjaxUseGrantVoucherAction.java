package com.xhcms.lottery.account.web.action.voucher;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;
/**
 * 使用赠款优惠劵
 * @author Wang Lei
 *
 */
public class AjaxUseGrantVoucherAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private VoucherUserService voucherUserService;
	public Long voucherId;
    private Data data = Data.success("");
    
	@Override
	public String execute() {
		try {
			voucherUserService.useWebGrantVoucher(voucherId, getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
