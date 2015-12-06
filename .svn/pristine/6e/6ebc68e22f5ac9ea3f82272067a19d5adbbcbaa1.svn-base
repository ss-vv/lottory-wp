package com.unison.lottery.grant.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.mc.persist.service.TicketService;
import com.xhcms.ucenter.sso.client.UserProfile;
import com.xhcms.ucenter.sso.client.UserProfileThreadContextHolder;

public class BaseAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 4683513973185668361L;
	protected HttpServletRequest request;
    
	
    public UserProfile getUser(){
        return UserProfileThreadContextHolder.getUserProfile();
    }
    
    public long getUserId(){
        return getUser().getId();
    }
    
    public String getUsername(){
    	return getUser().getUsername();
    }
    
    public boolean isGet() {
    	return "GET".equalsIgnoreCase(request.getMethod());
    }
    
    public boolean isPost() {
    	return "POST".equalsIgnoreCase(request.getMethod());
    }
    
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getErrorText(int code){
	    return getText("error." + code);
	}
	
}
