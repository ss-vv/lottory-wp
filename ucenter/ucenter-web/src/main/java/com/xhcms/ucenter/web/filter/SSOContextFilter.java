package com.xhcms.ucenter.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.xhcms.ucenter.sso.client.GuestUserProfile;
import com.xhcms.ucenter.sso.client.UserProfile;
import com.xhcms.ucenter.sso.client.UserProfileThreadContextHolder;
import com.xhcms.ucenter.sso.client.session.SSOAuthentication;
import com.xhcms.ucenter.sso.client.session.SSOConstant;

/**
 * @author bean
 * 
 */
public class SSOContextFilter implements Filter {
	
    private String ignore;
    
	@Override
	public void init(FilterConfig config) throws ServletException {
	    ignore = config.getInitParameter("ignore").trim();
		SSOAuthentication.init(config.getInitParameter("profileUrl"), config.getInitParameter("ssoLoginUrl"), config.getInitParameter("appLoginUrl"));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		if (ignore != null && !req.getServletPath().startsWith(ignore)) {
	        //XXX:当上传文件时，使用过滤器（调用了getParameter()方法）会导致文件对象无法包装
	        String contentType = req.getContentType();
	        if(contentType == null || !contentType.startsWith("multipart/form-data")){
	            // 检查SSO状态
	            SSOAuthentication.check(request, response);
	        }
		}
		
        UserProfile profile = (UserProfile)req.getSession().getAttribute(SSOConstant.SSO_USER_PROFILE);
        if(profile == null) {
            profile = new GuestUserProfile();
        }
        UserProfileThreadContextHolder.setUserProfile(profile);
        chain.doFilter(request, response);
        UserProfileThreadContextHolder.setUserProfile(null);
	}

	@Override
	public void destroy() {
	}
}
