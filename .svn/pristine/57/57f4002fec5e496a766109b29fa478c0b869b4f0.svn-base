package com.xhcms.lottery.dc.feed.web.action.passtype;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.persist.service.PlayService;
import com.xhcms.lottery.dc.feed.web.action.BaseAction;

/**
 * 
 * <p>Title: 查询过关类型</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class AjaxListAction extends BaseAction {
    private static final long serialVersionUID = 6569629946401612198L;
    @Autowired
    private PlayService playService;
    private String playId;
    private Data data;

    @Override
    public String execute() throws Exception {
        data = Data.success(playService.listPassType(playId));
        return SUCCESS;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public Data getData() {
        return data;
    }

}
