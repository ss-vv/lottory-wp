package com.unison.lottery.wap.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
/**
 * <p>Title: 查看账户明细</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * 
 * @author peng.qi
 * 
 */
public class AccountDetailAction extends BaseAction {


	private static final long serialVersionUID = 8460728659218780939L;

	@Autowired
    private AccountService accountService;

    private Account account;
    


	/**
     * 账户明细
     */
    @Override
    public String execute() {
    	User user  = (User)request.getSession().getAttribute(Constant.USER_KEY);
        if(user!=null){
        	account = accountService.getAccount(user.getId());
        	return SUCCESS;
        }else{
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
    

    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
		this.account = account;
	}

}
