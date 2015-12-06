package com.unison.lottery.wap.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;

public class YeepayResultAction extends BaseAction {

	private static final long serialVersionUID = -6226026048154404990L;
	@Autowired
    private AccountService accountService;
	
	private Long respOrderNo;
	
	private Recharge recharge;

	@Override
	public String execute() {
		
		User user = (User) request.getSession().getAttribute(Constant.USER_KEY);
		if (user != null) {
			recharge = accountService.getRecharge(respOrderNo);
		} else {
			return LOGIN;
		}
		return SUCCESS;
	}

	public Long getRespOrderNo() {
		return respOrderNo;
	}

	public void setRespOrderNo(Long respOrderNo) {
		this.respOrderNo = respOrderNo;
	}

	public Recharge getRecharge() {
		return recharge;
	}

	public void setRecharge(Recharge recharge) {
		this.recharge = recharge;
	}
}
