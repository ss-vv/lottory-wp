package com.xhcms.ucenter.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xhcms.ucenter.sso.client.UserProfile;
import com.xhcms.ucenter.sso.client.UserProfileThreadContextHolder;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.ticket.impl.TicketRegistryDefault;
import com.xhcms.ucenter.sso.web.util.GrantingTicketCookieGenerator;

public final class ProfileRetriveInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 8427881386596881854L;
	
	@Autowired
	private GrantingTicketCookieGenerator cookieGenerator;
	
	@Autowired
	private TicketRegistryDefault ticketRegistry;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
	    ActionContext ctx = invocation.getInvocationContext();
	    HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
	    
	    UserProfile profile = UserProfileThreadContextHolder.getUserProfile();
	    if(profile.getId() == 0L){
	        GrantingTicket ticket = loadServiceTicketFromCookie(request);
	        if(ticket != null){
	            com.xhcms.ucenter.sso.service.UserProfile up = ticket.getUserProfile();
	            if(up != null){
	                profile = new UserProfile();
	                profile.setId(up.getId());
	                profile.setUsername(up.getUsername());
	                profile.setTruename(up.getTruename());
	                profile.setNickname(up.getNickname());
	                profile.setLastLoginIp(up.getLastLoginIp());
	                profile.setLastLoginTime(up.getLastLoginTime());
	                UserProfileThreadContextHolder.setUserProfile(profile);
	            }
	        }
	    }
		
		return invocation.invoke();
	}



	private GrantingTicket loadServiceTicketFromCookie(HttpServletRequest request) {
		GrantingTicket grantingTicket = (GrantingTicket) ticketRegistry.getTicket(
				cookieGenerator.getCookieValue(request), GrantingTicket.class);
		return grantingTicket;
	}
	
}
