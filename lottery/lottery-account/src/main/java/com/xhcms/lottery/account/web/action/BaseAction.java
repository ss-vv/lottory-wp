package com.xhcms.lottery.account.web.action;

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
	protected HttpServletRequest request;
    
	private static final long serialVersionUID = 7124812597981738142L;

	@Autowired
	private TicketService ticketService;
	
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
    
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getErrorText(int code){
	    return getText("error." + code);
	}
	
	/**
	 * 用出票中心给出的odds更新ticket对象关联的比赛的赔率、预设分值属性。支持混合过关方式。
	 * odds of lt_ticket 形如：
	 * 1.700-1.700-1.750-1.750@173.5B152.5B190.5B174.5 <br/>
	 * 1.68-3.4-120@-5.5
	 * @param tickets
	 * @throws TranslateException 如果解析赔率格式有错
	 */
	protected void parseResultOdds(List<Ticket> tickets,Map<String, PlayMatch> matches) {
		ticketService.updatePlayMatchByPlatformOdds(tickets, matches);
	}
}
