package com.xhcms.lottery.admin.web.action.voucher;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.GrantType;
import com.xhcms.lottery.commons.data.Voucher;
import com.xhcms.lottery.commons.persist.entity.GrantTypePO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;
import com.xhcms.lottery.commons.persist.entity.VoucherTypePO;
import com.xhcms.lottery.commons.persist.entity.VoucherUserExtendPO;
import com.xhcms.lottery.commons.persist.service.GrantTypeService;
import com.xhcms.lottery.commons.persist.service.VoucherService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

/**
 * 优惠劵列表
 * @author Wang Lei
 *
 */
public class ListVoucherAction extends BaseListAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private VoucherUserService voucherUserService;
    @Autowired
    private GrantTypeService grantTypeService;
    @Autowired
    private VoucherService voucherService;
	private VoucherUserExtendPO vuePO;
	private List<GrantType> grantTypes;
	private List<Voucher> vouchers;
	private List<VoucherTypePO> voucherTypes;
	public String state; 				// 优惠劵状态
	public String username; 		// 用户帐户名
	public Long granttypeId; 		// 赠款类型id
	public String voucherType; 	// 优惠劵类型
	public String voucherId; 		// 优惠劵属性id
	public Long voucherUserId; 	// 优惠劵id
	
	@Override
    public String execute(){
		init();
		VoucherUserExtendPO voucherUser = new VoucherUserExtendPO();
		if(voucherUserId != null){
			voucherUser.setId(voucherUserId);
		}
		if(granttypeId != null){
			GrantTypePO grantTypePO = new GrantTypePO();
			grantTypePO.setId(granttypeId);
			voucherUser.setGrantType(grantTypePO);
		}
		if(StringUtils.isNotBlank(voucherId) || StringUtils.isNotBlank(voucherType)){
			VoucherPO voucherPO = new VoucherPO();
			if(StringUtils.isNotBlank(voucherId)){
				voucherPO.setId(voucherId);
			}
			if(StringUtils.isNotBlank(voucherType)){
				voucherPO.setType(voucherType);
			}
			voucherUser.setVoucher(voucherPO);
		}
		if(StringUtils.isNotBlank(username)){
			UserPO user = new UserPO();
			user.setUsername(username);
			voucherUser.setUser(user);
		}
		voucherUserService.list(paging, state,  voucherUser);
		grantTypes = grantTypeService.list();
		voucherTypes = voucherUserService.listAllVoucherType();
		vouchers = voucherService.loadAll();
		return SUCCESS;
	}
	
	public VoucherUserExtendPO getVuePO() {
		return vuePO;
	}
	public void setVuePO(VoucherUserExtendPO vuePO) {
		this.vuePO = vuePO;
	}

	public List<GrantType> getGrantTypes() {
		return grantTypes;
	}

	public void setGrantTypes(List<GrantType> grantTypes) {
		this.grantTypes = grantTypes;
	}

	public List<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	public List<VoucherTypePO> getVoucherTypes() {
		return voucherTypes;
	}

	public void setVoucherTypes(List<VoucherTypePO> voucherTypes) {
		this.voucherTypes = voucherTypes;
	}
}
