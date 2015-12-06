package com.unison.lottery.weibo.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;

/**
 * 基础action
 * @author Wang Lei
 * @param <E>
 *
 */
public class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = -406742020362981131L;
	protected Logger log = LoggerFactory.getLogger(getClass());
    protected PageResult<?> pageResult;
    protected PageRequest pageRequest;
    protected ActionContext context = ActionContext.getContext();  
    protected HttpServletRequest request;  
    protected HttpServletResponse response;
    protected SessionMap<?, ?> session;
    protected WeiboUser user;
    
    public BaseAction (){
    	super();
    	pageResult = new PageResult<>();
    	pageRequest = new PageRequest();
    }

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;  
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;  
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;  
	}
	
	public WeiboUser getUser(){
		if(request == null){
			return null;
		}
		Object obj = request.getSession().getAttribute(Constant.User.USER);
		return obj == null ? null : (WeiboUser) obj;
	}
	
	public long getUserLaicaiWeiboId(){
		WeiboUser user = getUser();
		return user == null ? 0 : user.getWeiboUserId();
	}
	
	public String getUserSinaWeiboId(){
		WeiboUser user = getUser();
		return user == null ? "" : user.getSinaWeiboUid();
	}
	
	/**
	 * @return 大V彩用户id
	 */
	public long getUserId(){
		WeiboUser user = getUser();
		return user == null ? 0 : user.getId();
	}

	public PageResult<?> getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult<?> pageResult) {
		this.pageResult = pageResult;
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}
	
}
