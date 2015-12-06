package com.unison.lottery.weibo.web.action.pc;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.Result;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.uc.service.BankService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;

/**
 * 账户相关action
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-4-30 上午11:07:15
 */
public class AccountAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Account account;
	private Result data = new Result();
	private String newIdNumber;
	private String newRealname;
	private Account newAccount;
	
	private String newPassword;
	private String oldPassword;
	
	private String reload;
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AccountService accountService;
	@Autowired
	BankService bankService;
	public String execute(){
		user = getUser(); 
		account = accountService.getAccount(user.getId());
		return SUCCESS;
	}
	
	public String editBankInfo(){
		user = getUser(); 
		account = accountService.getAccount(user.getId());
		request.setAttribute("editBankInfo", "editBankInfo");
		return SUCCESS;
	}
	public String editTxPass(){
		user = getUser(); 
		account = accountService.getAccount(user.getId());
		if(StringUtils.isNotBlank(reload)){
			context.getSession().put(Constant.User.USER, userAccountService.findWeiboUserByLotteryUid(getUserId() + ""));
			return "reload";
		}
		return SUCCESS;
	}
	public String bankInfo(){
		user = getUser(); 
		account = accountService.getAccount(user.getId());
		context.getSession().put(Constant.User.USER, userAccountService.findWeiboUserByLotteryUid(getUserId() + ""));
		if(StringUtils.isNotBlank(reload)){
			return "reload";
		}
		return SUCCESS;
	}
	
	public String ajCheckIdNumber(){
		if(StringUtils.isBlank(newIdNumber)){
			data.fail("身份证号码为空");
		} else {
			if(userAccountService.isIdCardUnique(newIdNumber)){
				data.setSuccess(true);
			} else {
				data.fail("身份证号码重复");
			}
		}
		return SUCCESS;
	}
	
	public String saveBankInfo(){
		data = bankService.saveBankInfo(getUser(), newIdNumber, newAccount,newRealname);
		context.getSession().put(Constant.User.USER, userAccountService.findWeiboUserByLotteryUid(getUserId() + ""));
		return SUCCESS;
	}
	
	public String modifyAccPwd(){
		if(StringUtils.isBlank(newPassword) || StringUtils.isBlank(oldPassword)){
			data.fail("非法提交,新密码或旧密码为空");
			logger.info("非法提交,新密码或旧密码为空");
			return SUCCESS;
		}
		if(accountService.checkPasswd(getUserId(), oldPassword)){
			accountService.updatePasswd(getUserId(), newPassword);
			data.setDesc("修改成功");
			data.setSuccess(true);
			return SUCCESS;
		} else {
			data.fail("提现密码不正确");
			return SUCCESS;
		}
	}
	
	public String toEditEmail(){
		if(StringUtils.isNotBlank(reload)){
			context.getSession().put(Constant.User.USER, userAccountService.findWeiboUserByLotteryUid(getUserId() + ""));
			return "reload";
		}
		return SUCCESS;
	}
	
	public String toEditPhone(){
		if(StringUtils.isNotBlank(reload)){
			context.getSession().put(Constant.User.USER, userAccountService.findWeiboUserByLotteryUid(getUserId() + ""));
			return "reload";
		}
		return SUCCESS;
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getNewIdNumber() {
		return newIdNumber;
	}

	public void setNewIdNumber(String newIdNumber) {
		this.newIdNumber = newIdNumber;
	}

	public Account getNewAccount() {
		return newAccount;
	}

	public void setNewAccount(Account newAccount) {
		this.newAccount = newAccount;
	}

	public Result getData() {
		return data;
	}

	public void setData(Result data) {
		this.data = data;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getReload() {
		return reload;
	}

	public void setReload(String reload) {
		this.reload = reload;
	}

	public String getNewRealname() {
		return newRealname;
	}

	public void setNewRealname(String newRealname) {
		this.newRealname = newRealname;
	}
}
