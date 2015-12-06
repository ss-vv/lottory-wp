package com.xhcms.lottery.account.web.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xhcms.ucenter.sso.client.session.SSOAuthentication;

public class AccessInterceptor extends AbstractInterceptor {
    
    private static final long serialVersionUID = -8064595243988495218L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
	    ActionContext ctx = invocation.getInvocationContext();
        
        // 用户未登录
	    HttpServletRequest req = (HttpServletRequest)ctx.get(StrutsStatics.HTTP_REQUEST);
	    HttpServletResponse resp = (HttpServletResponse)ctx.get(StrutsStatics.HTTP_RESPONSE);
	    
        if(!SSOAuthentication.check(req, resp)){
            ctx.getValueStack().set("ssoLogin", SSOAuthentication.retriveRedirect(req, resp, true));
            return Action.LOGIN;
        }
        
	    return invocation.invoke();
    }

    /**
     * 
     * @param ctx
     * @param message 错误信息
     * @return
     */
    protected String handleError(ActionContext ctx, String message){
        Object action = ctx.getActionInvocation().getAction();
        if(action instanceof ValidationAware){
            ((ValidationAware)action).addActionError(message);
        }
        
        return Action.ERROR;
    }
    
}
