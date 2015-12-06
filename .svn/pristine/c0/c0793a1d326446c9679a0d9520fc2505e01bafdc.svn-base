package com.unison.lottery.wap.action.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.persistent.service.IUserManager;

/**
 * 更新用户密码的Action
 * @author lei.li
 * @version 1.0
 */
public class UpdatePasswordAction extends BaseAction {

	private static final long serialVersionUID = -595818439204237794L;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserManager userManager;

	@Autowired
	private AccountService accountService;
	
	//@Autowired
//	private PasswordCookieGenerator pwdCookieGenerator;

	private String oldPassword;

	private String newPassword;

	private String newConfirmPassword;

	private String statusCode;

	// 更新登录密码
	public String updateLoginPassword() {
		Object obj = request.getSession().getAttribute(Constant.USER_KEY);
		if (null != obj) {
			User user = (User) obj;
			Long id = user.getId();
			String currPassword = user.getPassword();
			
			boolean flag = validatePasswordUpdateInput(oldPassword, newPassword, newConfirmPassword, currPassword);
			if(flag) {
				userManager.updatePasswd(id, oldPassword, newPassword);
				user.setPassword(Text.MD5Encode(newPassword));
				request.getSession().setAttribute(Constant.USER_KEY, user);
				/*
				pwdCookieGenerator.removeCookie(response);
				String pwdCookieValue = null;
				try {
					pwdCookieValue = CryptoUitls.encrypt(newPassword, CryptoUitls.PASSWORD_SIGNATURE);
					pwdCookieGenerator.addCookie(request, response, pwdCookieValue);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				*/
				statusCode = "密码更新成功";
			}
		} else {
			statusCode = "无效用户,请重新登录";
		}
		return SUCCESS;
	}

	// 更新用户提现密码
	public String updateWithdrawPassword() {
		Object obj = request.getSession().getAttribute(Constant.USER_KEY);
		if (null != obj) {
			User user = (User) obj;
			Long id = user.getId();
			Account account = accountService.getAccount(id);
			String currPassword = account.getPassword();

			boolean flag = validatePasswordUpdateInput(oldPassword, newPassword, newConfirmPassword, currPassword);
			if(flag) {
				accountService.updatePasswd(id, newPassword);
				statusCode = "密码更新成功";
			}
		} else {
			statusCode = "无效用户,请重新登录";
		}
		return SUCCESS;
	}

	/**
	 * 验证用户密码更新输入的有效性
	 * @param oldPassword	用户输入的当前密码
	 * @param newPassword	新密码
	 * @param newConfirmPassword	确认新密码
	 * @param currPassword	当前登录密码
	 * @return	有效返回true，否则返回false
	 */
	private boolean validatePasswordUpdateInput(String oldPassword,
			String newPassword, String newConfirmPassword, String currPassword) {
		if (null == oldPassword || null == newPassword
				|| null == newConfirmPassword) {
			statusCode = "输入密码不能为空";
			return false;
		}
		if(-1 != newPassword.indexOf(" ") && -1 != newConfirmPassword.indexOf(" ")) {
			statusCode = "密码中包含非法字符,推荐使用数字加字母组合";
			return false;
		}
		if (!newPassword.equals(newConfirmPassword)) {
			statusCode = "两次输入的新密码不一致";
		} else if (Text.MD5Encode(newPassword).equals(currPassword)) {
			statusCode = "请不要使用当前用户密码为新密码";
		} else if(!Text.MD5Encode(oldPassword).equals(currPassword)) {
			statusCode = "输入的旧密码错误";
		} else if(newPassword.length() < 6 || newPassword.length() > 15
				|| newConfirmPassword.length() < 6 || newConfirmPassword.length() > 15) {
			statusCode = "密码长度错误：6-15个字符";
		} else {
			return true;
		}
		return false;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewConfirmPassword() {
		return newConfirmPassword;
	}

	public void setNewConfirmPassword(String newConfirmPassword) {
		this.newConfirmPassword = newConfirmPassword;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
