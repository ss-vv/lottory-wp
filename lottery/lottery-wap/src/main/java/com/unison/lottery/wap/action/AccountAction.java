package com.unison.lottery.wap.action;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.persistent.service.IUserService;
/**
 * <p>Title: 查看账户明细</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * 
 * @author peng.qi
 * 
 */
public class AccountAction extends BaseAction {

    private static final long serialVersionUID = 953476628724632211L;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private IUserService userService;

    private Account account;
    
    private BigDecimal amount;
    
    private boolean isNewUser;
    
    private long id;
    
    private String password;

    public boolean isNewUser() {
		return isNewUser;
	}

	public void setNewUser(boolean isNewUser) {
		this.isNewUser = isNewUser;
	}

	/**
     * 账户明细
     */
    @Override
    public String execute() {
        User user  = (User)request.getSession().getAttribute(Constant.USER_KEY);
        if(user != null){
        	user = userService.getUser(user.getId());
        	//每次访问我的账户页面，同步数据库中User相关数据，放入session
        	request.getSession().setAttribute(Constant.USER_KEY, user);
        	account = accountService.getAccount(user.getId());
        	return SUCCESS;
        }
        else{
        	return LOGIN;
        }
    }

    /**
     * 申请充值
     * @return
     */
    public String recharge() {
        return this.execute();
    }

    public String yeepayRecharge(){
    	return this.execute();
    }
    
    /**
     * 申请提款
     * @return
     */
    public String withdraw() {
//    	account = accountService.getAccount(getUser().getId());
//		// 如果没有填写过, 跳至添加页
//		if(StringUtils.isEmpty(account.getAccountNumber()) || StringUtils.isEmpty(account.getBank())) {
//			addActionMessage(getText("wh.error.settingBank"));
//			return Action.ERROR;
//		}
        return SUCCESS;
    }

    /**
     * 修改指定用户的提现密码
     * @param userId
     * @param passwd
     */
    public String updatePasswd(long userId, String password) {
    	accountService.updatePasswd(userId, password);
    	return SUCCESS;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
}
