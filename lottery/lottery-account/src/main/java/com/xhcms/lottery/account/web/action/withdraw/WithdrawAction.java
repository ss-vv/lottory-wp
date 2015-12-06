package com.xhcms.lottery.account.web.action.withdraw;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.service.UserService;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.service.WithdrawService;

public class WithdrawAction extends BaseAction {
	private static final long serialVersionUID = -363001265523920770L;
	private static final BigDecimal min = new BigDecimal(5);
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WithdrawService withdrawService;
	
	private BigDecimal amount;
	private String passwd;
	private Account account;
	
	@Override
	public String execute() {
	    User user = userService.getUser(getUserId());
	    account = accountService.getAccount(user.getId());
		if (amount == null || amount.compareTo(min) < 0) {
		    addFieldError("amount", getErrorText(AppCode.INVALID_AMOUNT));
			return INPUT;
		}
		
		if (!accountService.checkPasswd(getUserId(), passwd)) {
			addFieldError("passwd", getErrorText(AppCode.INVALID_PASSWORD));
			return INPUT;
		}

		Withdraw w = new Withdraw();
		w.setUserId(user.getId());
		w.setUsername(user.getUsername());
		w.setRealName(user.getRealname());
		w.setIp(requestIP(request));
		w.setRegistIp(user.getIp());
		
		// 银行信息
		w.setBank(account.getBank());
		w.setBankAccount(account.getAccountNumber());
		w.setAmount(amount);
		w.setProvince(account.getProvince());
		w.setCity(account.getCity());
		
		withdrawService.applyWithdraw(w);
		addActionMessage(getText("message.success"));
		
		return SUCCESS;
	}
	
	private String requestIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

    public Account getAccount() {
        return account;
    }
}
