package com.xhcms.lottery.admin.web.action.play;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.PlayService;
import com.xhcms.lottery.admin.web.action.BaseListAction;

public class AjaxListAction extends BaseListAction {

    private static final long serialVersionUID = 7002611053786429980L;

    private String lid;

    private Data data;

    @Autowired
    private PlayService playService;

    public String execute() {

        data = Data.success(playService.list(lid));

        return SUCCESS;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getLid() {
        return lid;
    }

    public Data getData() {
        return data;
    }

}
