package com.xhcms.lottery.admin.web.action.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.UserService;

public class AjaxStatusAction extends BaseAction {

    private static final long serialVersionUID = 8668189806299243579L;
    
    @Autowired
    private UserService userService;
    
    private List<Long> id;
    
    private Data data = Data.success("");
    
    @Override
    public String execute(){
        userService.open(id);
        return SUCCESS;
    }
    
    public String close(){
        userService.close(id);
        return SUCCESS;
    }

    public void setId(List<Long> id) {
        this.id = id;
    }

    public Data getData() {
        return data;
    }
    
}
