package com.xhcms.lottery.admin.web.action.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.Order;
import com.xhcms.lottery.commons.persist.service.OrderService;

public class ViewAction extends BaseListAction {

    private static final long serialVersionUID = 7745142796518100377L;

    @Autowired
    private OrderService orderService;
    
    private Long id;
    private Order order;
    
    @Override
    public String execute(){
        order = orderService.getOrder(id);
        return SUCCESS;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
