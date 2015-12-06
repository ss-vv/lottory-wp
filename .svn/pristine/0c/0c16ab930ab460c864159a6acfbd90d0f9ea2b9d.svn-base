package com.xhcms.lottery.account.web.action.my;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;

/**
 * 
 * <p>Title: 用户银行信息</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public class BankInfoAction extends BaseAction {
	private static final long serialVersionUID = -8042083765469165618L;
	@Autowired
	private AccountService accountService;
	private Account acc;
	
	@Override
	public String execute() {
		acc = accountService.getAccount(getUser().getId());
		// 如果没有填写过, 跳至添加页
		if(StringUtils.isEmpty(acc.getAccountNumber()) || StringUtils.isEmpty(acc.getBank())) {
			return "add";
		}
		return SUCCESS;
	}
	
	public String toAdd(){
	    this.execute();
	    return SUCCESS;
	}
	
	public Account getAcc() {
		return acc;
	}

}
