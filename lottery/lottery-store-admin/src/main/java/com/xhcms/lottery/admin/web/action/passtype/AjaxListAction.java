package com.xhcms.lottery.admin.web.action.passtype;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.PassTypeService;
import com.xhcms.lottery.admin.web.action.BaseAction;

public class AjaxListAction extends BaseAction {

    private static final long serialVersionUID = -8603619659264126691L;

    @Autowired
    private PassTypeService passTypeService;

    private Data data;

    public String execute() {

        data = Data.success(passTypeService.list());

        return SUCCESS;
    }

    public Data getData() {
        return data;
    }

}
