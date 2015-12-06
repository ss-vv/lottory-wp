package com.xhcms.lottery.admin.web.action.play;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.PlayService;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * <p>Title: 过关方式维护，查询指定玩法的过关方式</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */ 
public class AjaxGetAction extends BaseAction {

    private static final long serialVersionUID = -107356706209760852L;

    @Autowired
    private PlayService playService;

    private String playId;

    private Data data;

    public String execute() {

        data = Data.success(playService.getWithPassType(playId));

        return SUCCESS;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public Data getData() {
        return data;
    }

}
