package com.xhcms.lottery.account.web.action.my;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.AppCode;

public class ModifyBankAction extends BaseAction {
	private static final long serialVersionUID = 2045171743498426653L;
	@Autowired
	private AccountService accountService;
	private Account acc;
	
	private String passwd;
	private String province;
	private String city;
	private String bank;
	private String accountNumber;
	
	@Override
	public String execute() {
		acc = accountService.getAccount(getUser().getId());
		return SUCCESS;
	}
	
	/**
	 * 修改银行信息
	 * 
	 * @return
	 */
	public String modify() {
		if(checkBankInfo()) {
			accountService.addBankInfo(getUserId(), province, city, bank, accountNumber, passwd);
			addActionMessage(getText("message.success"));
		} else {
			if (!accountService.checkPasswd(getUserId(), passwd)) {
				addFieldError("passwd", getErrorText(AppCode.PASSWD_WH_WRONG));
				return INPUT;
			}
			accountService.modifyBankInfo(getUserId(), province, city, bank, accountNumber);
			addActionMessage(getText("message.success"));
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return true:没有填写, false:已经填写
	 */
	private boolean checkBankInfo() {
		acc = accountService.getAccount(getUser().getId());
		// 如果没有填写过, 跳至添加页
		if(StringUtils.isEmpty(acc.getAccountNumber()) || StringUtils.isEmpty(acc.getBank())) {
			return true;
		}
		return false;
	}

	public Account getAcc() {
		return acc;
	}

	public String getProvince() {
		return province;
	}

	@RequiredStringValidator
	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	@RequiredStringValidator
	public void setCity(String city) {
		this.city = city;
	}

	public String getBank() {
		return bank;
	}

	@RequiredStringValidator
	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	@RequiredStringValidator
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@RequiredStringValidator
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

    public String getPasswd() {
        return passwd;
    }
	
}
