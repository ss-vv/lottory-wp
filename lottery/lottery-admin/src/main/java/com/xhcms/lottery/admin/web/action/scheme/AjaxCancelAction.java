package com.xhcms.lottery.admin.web.action.scheme;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * <p>Title: 撤单</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class AjaxCancelAction extends BaseAction {

    private static final long serialVersionUID = 5139362559096601567L;

    @Autowired
    private BetSchemeService betSchemeService;

    private long sid;

    private Data data = Data.success(getText("message.success"));

    @Override
    public String execute() throws Exception {
        betSchemeService.cancel(getMyId(), sid);

        return super.execute();
    }

    public Data getData() {
        return data;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

}
