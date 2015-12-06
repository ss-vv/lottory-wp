package com.xhcms.lottery.admin.web.action.withdraw;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.WithdrawService;
import com.xhcms.lottery.admin.web.action.BaseAction;

public class AuditAction extends BaseAction {
    private static final long serialVersionUID = -3622331045495936508L;

    @Autowired
    private WithdrawService withdrawService;
    
    private long id;
    private String note;
    private String bankOrder;

    /**
     * 通过审核、确认
     */
    @Override
    public String execute() {
        withdrawService.pass(id, getMyId(), bankOrder, note);
        return SUCCESS;
    }
    
    /**
     * 打回重审
     * @return
     */
    public String reject() {
        withdrawService.reject(id, getMyId(), note);
        return SUCCESS;
    }
    
    /**
     * 直接驳回
     * @return
     */
    public String fail() {
        withdrawService.fail(id, getMyId(), note);
        return SUCCESS;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setBankOrder(String bankOrder) {
        this.bankOrder = bankOrder;
    }

}
