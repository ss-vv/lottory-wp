package com.xhcms.lottery.admin.web.action.playoption;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.PlayOptionService;
import com.xhcms.lottery.admin.web.action.BaseAction;

public class AjaxRemoveAction extends BaseAction {

    private static final long serialVersionUID = 3863971638910139620L;

    @Autowired
    private PlayOptionService playOptionService;

    private Long oid;

    private Data data;

    public String execute() {

        playOptionService.remove(oid);
        data = Data.success(null);

        return SUCCESS;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Data getData() {
        return data;
    }

}
