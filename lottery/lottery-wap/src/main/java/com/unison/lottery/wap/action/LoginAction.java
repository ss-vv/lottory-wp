/**
 * 
 */
package com.unison.lottery.wap.action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.wap.utils.PasswordCookieGenerator;
import com.unison.lottery.wap.utils.UserNameCookieGenerator;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.utils.CryptoUitls;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.lang.EnumLoginStatus;
import com.xhcms.ucenter.lang.EnumLoginType;
import com.xhcms.ucenter.persistent.service.IUserService;

/**
 * @author peng.qi
 *
 */
public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 607496267302123641L;
	private static final Logger log = LoggerFactory.getLogger(LoginAction.class);
	
	public static final String USERNAME_COOKIE_NAME = "_usrname_davcai_";
	public static final String PASSWORD_COOKIE_NAME = "_pwd_davcai_";
	@Autowired
	private IUserService userServiceCache;
	
	@Autowired
	private UserNameCookieGenerator userNameCookieGenerator;
	
	@Autowired
	private PasswordCookieGenerator pwdCookieGenerator;
	
	
	private String username;
	private String password;

	private Integer loginType;
	private String referer;
	
	@Override
	public String execute() {
		if (isPost()) {
			
			if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
				
				addActionError("用户名或者密码错误！");
				return LOGIN;
			}

			// if user is not exists
			User user = userServiceCache.getUserByUsername(username);
			if (null == user) {
				addActionError("用户不存在");
				return LOGIN;
			}
			// 默认类型为用户名登录
			if (null == loginType) {
				loginType = EnumLoginType.USERNAME.getValue();
			}

			String pwd = Text.MD5Encode(password);
			// 判断用户密码 和 登录状态
			if (pwd.equals(user.getPassword())
					&& user.getStatus() != EnumLoginStatus.STATUS_DISABLE
							.getValue()) {
				
				request.getSession().setAttribute(Constant.USER_KEY, user);
				request.getSession().setAttribute(Constant.USER_LASTLOGINTIME,
						user.getLastLoginTime());
				
				String usernameCookieValue;
				String pwdCookieValue;
				try {
					usernameCookieValue = CryptoUitls.encrypt(user.getUsername(), CryptoUitls.USERNAME_SIGNATURE);
					userNameCookieGenerator.addCookie(request, response, usernameCookieValue);
					pwdCookieValue = CryptoUitls.encrypt(password, CryptoUitls.PASSWORD_SIGNATURE);
					pwdCookieGenerator.addCookie(request, response, pwdCookieValue);
					
					
				} catch (Exception e) {
					log.error(e.getMessage());
					return LOGIN;
				}
				
				
				if(StringUtils.isNotEmpty(referer) && referer.startsWith("http://")) {
					return "referer";
				}
				return SUCCESS;
			}else{
				addActionError("用户名或密码错误！");
			}
		}else{
			
			String encryptUserName = userNameCookieGenerator.getCookieValue(request);
			String encryptPassword = pwdCookieGenerator.getCookieValue(request);
			if(StringUtils.isNotBlank(encryptUserName)&&StringUtils.isNotBlank(encryptPassword)){
				try {
					username = CryptoUitls.decrypt(encryptUserName, CryptoUitls.USERNAME_SIGNATURE);
					password = CryptoUitls.decrypt(encryptPassword, CryptoUitls.PASSWORD_SIGNATURE);
				} catch (Exception e) {
					//do nothing   如果有异常，就不自动填充用户名和密码
				}
				
			}
		}
		if (request.getSession().getAttribute(Constant.USER_KEY) != null) {
			// 如果已登录，则跳转到index.do
			return SUCCESS;
		}
		return LOGIN;
}
	

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public String getUsername() {
		return username;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
}
