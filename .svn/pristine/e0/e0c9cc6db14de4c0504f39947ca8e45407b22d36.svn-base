package com.xhcms.lottery.account.web.action.my;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.lottery.account.service.UserService;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.data.VoucherUser;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;
import com.xhcms.ucenter.sso.client.UserProfile;
import com.xhcms.ucenter.sso.client.UserProfileThreadContextHolder;

/**
 * <p>Title: 查看账户明细</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public class AccountAction extends BaseAction {

    private static final long serialVersionUID = 953476628724632211L;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private VoucherUserService voucherUserService;
    
    @Autowired
    private UserAccountService userAccountService;
    
    private Account account;
    
    private BigDecimal amount;
    
    private boolean partake;
    
    @Autowired
    private UserService userService;
    
    private List<VoucherUser> vouchers;
    public Long voucherId; // 优惠卷id
    public boolean isUseVoucher; // 是否使用优惠劵 
    
    private WeiboUser weiboUser;
    
	/**
     * 账户明细
     */
    @Override
    public String execute() {
        Long uid = getUserId();
        account = accountService.getAccount(uid);
        
        return SUCCESS;
    }

	/**
     * 申请充值
     * @return
     */
    public String recharge() {
    	weiboUser = userAccountService.findWeiboUserByLotteryUid(getUserId() + "");
    	vouchers = voucherUserService.listValidRechargeByUserid(getUserId());
        return this.execute();
    }

    public String yeepayRecharge(){
    	return this.recharge();
    }

	/**
     * 申请提款
     * @return
     */
    public String withdraw() {
    	account = accountService.getAccount(getUser().getId());
    	// 如果没有填写过, 跳至添加页
		if(StringUtils.isEmpty(account.getAccountNumber()) || StringUtils.isEmpty(account.getBank())) {
			addActionMessage(getText("wh.error.settingBank"));
			request.setAttribute("noBankAccount", "noBankAccount");
			return Action.ERROR;
		}
		
		//查找最新的用户信息，并放入Context
		long uid = getUser().getId();
		updateUserInThreadContext(userService.getUser(uid));
        return SUCCESS;
    }
    
    private void updateUserInThreadContext(User user){
    	if(null == user) {
    		return ;
    	}
    	UserProfile userProfile = new UserProfile();
		userProfile.setTruename(user.getRealname());
		userProfile.setUsername(user.getUsername());
		userProfile.setId(user.getId());
		userProfile.setLastLoginIp(user.getLastLoginIp());
		userProfile.setLastLoginTime(user.getLastLoginTime());
		UserProfileThreadContextHolder.setUserProfile(userProfile);
    }
    
    public Account getAccount() {
        return account;
    }

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
    public boolean isPartake() {
		return partake;
	}

	public void setPartake(boolean partake) {
		this.partake = partake;
	}
	
	public List<VoucherUser> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<VoucherUser> vouchers) {
		this.vouchers = vouchers;
	}

	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
	}
}
