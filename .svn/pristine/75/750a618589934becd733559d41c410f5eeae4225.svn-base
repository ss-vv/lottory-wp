package com.xhcms.ucenter.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.lang.Constant;

/**
 * 
 * <p>Title: 基础Action类</p>
 * <p>Description: 封装日志输出、Ajax请求判断等</p>
 * <p>Copyright：Copyright (c) 2010</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * @author wanged
 * @version 1.0
 */
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private static final long serialVersionUID = -890811607317529195L;

    /** 
     * 日志记录器
     */
    protected Logger log = LoggerFactory.getLogger(getClass());

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    /**
     * 判断请求方法是否为POST
     * @return
     */
    public boolean isPost() {
        return "post".equalsIgnoreCase(request.getMethod());
    }

    /**
     * 判断请求是否为Ajax请求
     * @return
     */
    public boolean isAjax() {
        return (!StringUtils.isEmpty(request.getParameter("_ajax")));
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    public void setValue(String name, Object value) {
    	ActionContext.getContext().put(name, value);
    }

	public User getSelf() {
		return (User) request.getSession().getAttribute(Constant.USER_KEY);
	}
	
    public int getIntParameter(String name, int defaultValue) {
    	String value = request.getParameter(name);
    	
    	if(value == null) {
    		return defaultValue;
    	}
		
    	try {
    		return Integer.parseInt(value);
    	}catch(NumberFormatException ex){
    		return defaultValue;
    	}
    }
    
    public String getErrorText(int code){
        return getText("error." + code);
    }
}
