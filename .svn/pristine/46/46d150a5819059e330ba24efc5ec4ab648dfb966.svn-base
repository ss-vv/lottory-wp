package com.xhcms.lottery.admin.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.lang.AdminConstant;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 2664251239218199347L;

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
	
//	public boolean isPostRequest() {
//		if("post".equalsIgnoreCase(request.getMethod())) {
//			return true;
//		}
//		return false;
//	}
	
	public Admin getAdmin(){
		return (Admin) request.getSession().getAttribute(AdminConstant.USER);
	}
	
	public int getMyId() {
		Admin a = (Admin) request.getSession().getAttribute(AdminConstant.USER);
		return a.getId();
	}
	
    public String getErrorText(int code){
        return getText("error." + code);
    }

}
