package com.xhcms.lottery.admin.web.action.recharge;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.RechargeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Recharge;

public class ViewAction extends BaseAction {
    private static final long serialVersionUID = -3622331045495936508L;

    @Autowired
    private RechargeService rechargeService;
    private Recharge r;
    private long id;

    @Override
    public String execute() {
        r = rechargeService.getRecharge(id);
        return SUCCESS;
    }

    public Recharge getR() {
        return r;
    }

    public void setId(long id) {
        this.id = id;
    }

}
