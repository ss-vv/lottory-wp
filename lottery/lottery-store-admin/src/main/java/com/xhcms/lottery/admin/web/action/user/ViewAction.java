package com.xhcms.lottery.admin.web.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.UserService;

public class ViewAction extends BaseAction {

    private static final long serialVersionUID = -595818439204217794L;

    @Autowired
    private UserService userService;
    private long id;
    private User u;
    
    @Override
    public String execute(){
        u = userService.getUser(id);
        return SUCCESS;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getU() {
        return u;
    }
    
}
