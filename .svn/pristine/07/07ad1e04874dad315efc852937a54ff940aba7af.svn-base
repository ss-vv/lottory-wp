package com.xhcms.lottery.account.web.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.xhcms.commons.lang.Data;

public class AjaxAccessInterceptor extends AccessInterceptor {
    
    private static final long serialVersionUID = -7244339054819698041L;

    @Override
    protected String handleError(ActionContext ctx, String message){
        ctx.getValueStack().set("data", Data.failure(message));
        return Action.SUCCESS;
    }

}
