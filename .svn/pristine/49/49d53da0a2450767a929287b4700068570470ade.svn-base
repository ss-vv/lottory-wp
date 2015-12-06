package com.xhcms.ucenter.web.action;

import com.xhcms.commons.lang.Data;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.sso.client.UserProfileThreadContextHolder;

public class LoadMsgAction extends BaseAction {
    private static final long serialVersionUID = 7967059888393173714L;
    
    private Data data;
    
    @Override
    public String execute() {
        data = Data.success(UserProfileThreadContextHolder.getUserProfile());

        return SUCCESS;
    }

    public Data getData() {
        return data;
    }

}
