package com.xhcms.lottery.admin.web.action.voucher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.GrantType;
import com.xhcms.lottery.commons.data.Voucher;
import com.xhcms.lottery.commons.data.VoucherUser;
import com.xhcms.lottery.commons.persist.service.GrantTypeService;
import com.xhcms.lottery.commons.persist.service.VoucherService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

/**
 * 赠送优惠劵
 * @author Wang Lei
 *
 */
public class AddVoucherAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	public String username;
	public String voucherId;
	public Integer validDay;
	public Long granttypeId;
	private List<Voucher> vouchers;
	private List<GrantType> grantTypes;
	private VoucherUser voucherUser;
	private Data data = Data.success(getText("message.success"));
	@Autowired
    private VoucherService voucherService;
	@Autowired
	private VoucherUserService voucherUserService;
    @Autowired
    private GrantTypeService grantTypeService;
	
	@Override
    public String execute(){
		vouchers = voucherService.loadAll();
		grantTypes = grantTypeService.list();
		return SUCCESS;
	}
	
	/**
	 * 添加优惠劵
	 * @return
	 */
	public String add(){
		try {
			if(!"root".equals(getAdmin().getUsername()) && !"handongyang".equals(getAdmin().getUsername())){
				data.setSuccess(false);
				data.setData("用户没有权限！");
				return SUCCESS;
			}
			voucherUserService.createVoucher(username, validDay.longValue(), voucherId, granttypeId);
		} catch (Exception e) {
			e.printStackTrace();
			data.setSuccess(false);
			data.setData("数据输入错误！");
		}
		return SUCCESS;
	}

	public List<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	public List<GrantType> getGrantTypes() {
		return grantTypes;
	}

	public void setGrantTypes(List<GrantType> grantTypes) {
		this.grantTypes = grantTypes;
	}

	public VoucherUser getVoucherUser() {
		return voucherUser;
	}

	public void setVoucherUser(VoucherUser voucherUser) {
		this.voucherUser = voucherUser;
	}
	
    public Data getData() {
        return data;
    }
}
