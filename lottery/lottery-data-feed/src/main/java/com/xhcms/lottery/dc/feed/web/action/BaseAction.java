package com.xhcms.lottery.dc.feed.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 544694119132788744L;
	protected HttpServletRequest request;
    protected HttpServletResponse response;
    
    @Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public ActionContext getActionContext() {
		return ActionContext.getContext();
	}
	
	public ValueStack getValueStack() {
		return getActionContext().getValueStack();
	}
}
