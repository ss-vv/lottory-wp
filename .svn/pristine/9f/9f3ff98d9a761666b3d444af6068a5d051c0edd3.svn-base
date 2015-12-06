package com.xhcms.lottery.admin.web.action.withdraw;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.WithdrawService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Withdraw;

public class ViewAction extends BaseAction {
    private static final long serialVersionUID = -3622331045495936508L;

    @Autowired
    private WithdrawService withdrawService;
    private Withdraw w;
    private long id;

    @Override
    public String execute() {
        w = withdrawService.getWithdraw(id);
        return SUCCESS;
    }

    public Withdraw getW() {
        return w;
    }

    public void setId(long id) {
        this.id = id;
    }

}
