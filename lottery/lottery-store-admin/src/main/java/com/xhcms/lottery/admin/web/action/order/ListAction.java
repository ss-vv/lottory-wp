package com.xhcms.lottery.admin.web.action.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.OrderService;

public class ListAction extends BaseListAction {

    private static final long serialVersionUID = 7745142796518100377L;

    @Autowired
    private OrderService orderService;
    private String username;
    private int type = -1;
    
    @Override
    public String execute(){
        init();
        orderService.listOrder(paging, username, type, from, to);
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
