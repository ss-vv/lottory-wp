/**
 * 
 */
package com.xhcms.ucenter.web.action;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.lang.EnumLoginType;
import com.xhcms.ucenter.service.user.IUserManager;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.discuz.DiscuzSynchronizer;
import com.xhcms.ucenter.sso.principal.RemeberMeUsernamePasswordCredentials;
import com.xhcms.ucenter.sso.service.IAuthenticationManager;
import com.xhcms.ucenter.sso.service.UserProfile;
import com.xhcms.ucenter.sso.ticket.IService;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.ticket.impl.ServiceTicket;
import com.xhcms.ucenter.sso.web.util.GrantingTicketCookieGenerator;

/**
 * @author bean
 *
 */
public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 607496267302123641L;
	
	@Autowired
	private IUserService userServiceCache;
	@Autowired
	private IUserManager userManager;
	@Autowired
	private GrantingTicketCookieGenerator cookieGenerator;
	@Autowired
	private DiscuzSynchronizer discuzSynchronizer;
	
	@Autowired
	private IAuthenticationManager authenticationManager;
	
	private String username;
	private String password;
	
	private String rememberMe;
	private Integer time;
	private Integer loginType;
	
	private String referer;
	private String failReturnURL;
	private String errorMessage = "";
	
	@Override
	public String execute() throws Exception {
		if(StringUtils.isBlank(username) && 
			StringUtils.isBlank(password) ){// 如果是第三方注册后的登录，用户名和密码会从request传递
			password = (String)ActionContext.getContext().getValueStack().findValue("password1");
			username = (String)ActionContext.getContext().getValueStack().findValue("username1");
		}
		String result =  execute0();
		if (LOGIN.equals(result) && StringUtils.isNotBlank(failReturnURL)){
			if(StringUtils.isNotBlank(errorMessage)){
				failReturnURL += "?errorMessage=" + URLEncoder.encode(errorMessage,"UTF-8");
			}
			return "toFailURL";
		} else {
			return result;
		}
	}
	
	private String execute0() throws Exception {
		if(isPost()) {
			//if user is not exists
			User u = userServiceCache.getUserByUsername(username);
			if(null == u) {
				u = userServiceCache.getUserByVerifyedMobile(username);//如果用户名输入手机号码
				if(null == u) {
					addActionError("用户不存在");
					errorMessage = "用户不存在";
					return LOGIN;
				} else { 
					username = u.getUsername();
				}
			}
			// 默认类型为用户名登录
			if (null == loginType) {
				loginType = EnumLoginType.USERNAME.getValue();
			}
			
			RemeberMeUsernamePasswordCredentials credentials = new RemeberMeUsernamePasswordCredentials(loginType, username, password);
			if(StringUtils.isBlank(rememberMe)){
			    credentials.setRememberMe(false);
			    credentials.setMaxAge(-1);
			} else {
			    credentials.setRememberMe(true);
			    // time值为正数时取设置过期时间为该值，否则设置为三个月过期
	            credentials.setMaxAge((time != null && time > 0) ? time : 7889231);
			}
			
			Properties props = new Properties();
			props.load(this.getClass().getResourceAsStream("/config.properties"));
			
			UserProfile profile = authenticationManager.authenticate(credentials);
			if(null == profile) {//login failure
				//判断是否达到最大的登录次数
				int loginFailureNumber = u.getLoginFailureNumber();
				loginFailureNumber = userServiceCache.recordUserLoginFailureCount(username);
				int max = 0;
				try {
					max = Integer.parseInt((String)props.get("max_login_failure"));
				} catch(NumberFormatException e) {
					max = 5;//default max failure login count
				}
				
				if(max == loginFailureNumber) {//locked user account
					u.setLoginFailureNumber(loginFailureNumber);
					userServiceCache.lockUserAccount(u);
					long auto_unlock_interval = Long.parseLong(props.getProperty("auto_unlock_interval"));
					addActionError(props.getProperty("account_lock_tip") + auto_unlock_interval + props.getProperty("account_unlock_tip"));
					errorMessage = props.getProperty("account_lock_tip") + auto_unlock_interval + props.getProperty("account_unlock_tip");
					return LOGIN;
				} else {
					addActionError("密码错误");
					errorMessage = "密码错误";
				}
			} else {
				if(Constants.ISLOCKED == u.getIsLocked()) {
					long auto_unlock_interval = Long.parseLong(props.getProperty("auto_unlock_interval"));
					long interval = userServiceCache.autoUnlockUserAccount(u, auto_unlock_interval);
					if(-1 != interval) {
						addActionError(props.getProperty("account_lock_tip") + interval + props.getProperty("account_unlock_tip"));
						errorMessage = props.getProperty("account_lock_tip") + interval + props.getProperty("account_unlock_tip");
						return LOGIN;
					}
				} else {
					userServiceCache.unlockUserAccount(profile.getId());
				}
				
			    request.getSession().setAttribute(Constant.USER_KEY, profile);
				request.getSession().setAttribute(Constant.USER_LASTLOGINTIME, profile.getLastLoginTime());
				//更新用户登录信息
				userManager.updateUserLoginStatus(profile.getId(), request.getHeader("X-Real-IP"), new Date());
				
				User user = userServiceCache.getUser(profile.getId());
				
				//记录cookie
				GrantingTicket grantingTicket = authenticationManager.createGrantingTicket(profile);
				cookieGenerator.addCookie(request, response, grantingTicket.getId());
				
				discuzSynchronizer.syncLoginStatus(request, response, user);
				
				
				//登录成功后台的处理流程
				IService service = (IService) request.getSession().getAttribute(Constant.SERVICE_KEY_IN_SESSION);
				if (service != null) {
					grantingTicket.addSerice(service);
					ServiceTicket serviceTicket = authenticationManager.createServiceTicket(grantingTicket, service);
					response.sendRedirect(constructServiceUrl(service, serviceTicket.getId()));

					return NONE;
				}
				
				if(StringUtils.isNotEmpty(referer) && referer.startsWith("http://")) {
					return "referer";
				}
				
				return SUCCESS;
			} 
			
			//addActionError(getText("user.login.failure"));
			
			return LOGIN;
		} 
		
		if(request.getSession().getAttribute(Constant.USER_KEY) != null){
			//如果已登录，则跳转到index.do
			return SUCCESS;
		}
		return LOGIN;
	}
	
	private String constructServiceUrl(IService service, String ticket) {
	    String backUrl = service.getId();
		return new StringBuilder(256)
		    .append(backUrl)
		    .append(backUrl.indexOf('?') > 0 ? '&' : '?')
		    .append("ticket=")
		    .append(ticket)
		    .toString();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRememberMe(String rememberMe){
        this.rememberMe = rememberMe;
    }

    public void setTime(Integer time) {
		this.time = time;
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

	public String getFailReturnURL() {
		return failReturnURL;
	}

	public void setFailReturnURL(String failReturnURL) {
		this.failReturnURL = failReturnURL;
	}
}
