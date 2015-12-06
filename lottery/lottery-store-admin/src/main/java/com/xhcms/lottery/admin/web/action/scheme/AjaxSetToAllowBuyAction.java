package com.xhcms.lottery.admin.web.action.scheme;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * 重置方案为可出票状态
 * @author Yang Bo
 * @version 1.0.0
 */
public class AjaxSetToAllowBuyAction extends BaseAction {

    private static final long serialVersionUID = 5139362559096601567L;

    @Autowired
    private BetSchemeService betSchemeService;

    private long sid;

    private Data data = Data.success(getText("message.success"));

    @Override
    public String execute() throws Exception {
        betSchemeService.setToAllowBuy(getMyId(), sid);

        return super.execute();
    }

    public Data getData() {
        return data;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

}
