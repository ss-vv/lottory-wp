package com.xhcms.lottery.admin.web.action.withdraw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.WithdrawService;
import com.xhcms.lottery.admin.web.action.BaseAction;

public class AjaxPassAction extends BaseAction {
    private static final long serialVersionUID = -3622331045495936508L;

    @Autowired
    private WithdrawService withdrawService;
    
    private List<Long> id;
    private Data data = Data.success(getText("message.success"));

    /**
     * 通过审核、确认
     */
    @Override
    public String execute() {
        withdrawService.batchConfirm(id, getMyId());
        return SUCCESS;
    }

    public void setId(List<Long> id) {
        this.id = id;
    }

    public Data getData() {
        return data;
    }

}
