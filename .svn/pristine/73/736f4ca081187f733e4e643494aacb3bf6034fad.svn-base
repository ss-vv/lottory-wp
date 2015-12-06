package com.unison.lottery.wap.interceptor;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.unison.lottery.wap.utils.PasswordCookieGenerator;
import com.unison.lottery.wap.utils.UserNameCookieGenerator;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.utils.CryptoUitls;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.persistent.service.IUserService;

public class AutoLoginInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = -5677242442415461586L;
	
	@Autowired
    private UserNameCookieGenerator usernameCookieGenerator;

	@Autowired
	private PasswordCookieGenerator passwordCookieGenerator;
	
	@Autowired
	private IUserService userServiceCache;
	
	public String intercept(ActionInvocation invocation) throws Exception {
		
		
		
		
		System.out.println("AutoLoginInterceptor");
		ActionContext ctx = invocation.getInvocationContext();
		Map map = ctx.getSession();
		HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
		User user = (User) map.get(Constant.USER_KEY);
		if (null != user) {
			if (null != user.getUsername() && null != user.getPassword()) {
				return invocation.invoke();
			}
		}
		String exit = request.getParameter("exit");
		
		System.out.println("exit is "+ exit);
		//如果退出，不读取cookie
		if(exit == null){
			
			String encryptUserName = usernameCookieGenerator.getCookieValue(request);
			String encryptPassword = passwordCookieGenerator.getCookieValue(request);
			if(StringUtils.isNotBlank(encryptUserName)&&StringUtils.isNotBlank(encryptPassword)){
				String userName = CryptoUitls.decrypt(encryptUserName, CryptoUitls.USERNAME_SIGNATURE);
				String password = CryptoUitls.decrypt(encryptPassword, CryptoUitls.PASSWORD_SIGNATURE);
				
				user = userServiceCache.getUserByUsername(userName);
				if(user!=null && user.getPassword().equals(Text.MD5Encode(password))){
					request.getSession().setAttribute(Constant.USER_KEY, user);
					request.getSession().setAttribute(Constant.USER_LASTLOGINTIME,
							user.getLastLoginTime());
				}
			}
		}
		
		return invocation.invoke();
	}

}

