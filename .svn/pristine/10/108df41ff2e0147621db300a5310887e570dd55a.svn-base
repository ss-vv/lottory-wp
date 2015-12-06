package com.xhcms.lottery.admin.web.action.play;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.PlayService;
import com.xhcms.lottery.admin.web.action.BaseAction;

public class AjaxRemoveAction extends BaseAction {

    private static final long serialVersionUID = 3040792579205381457L;

    @Autowired
    private PlayService playService;

    private String pid;

    private Data data;

    public String execute() {

        playService.remove(pid);
        data = Data.success(null);

        return SUCCESS;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Data getData() {
        return data;
    }

}
