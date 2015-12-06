package com.xhcms.lottery.admin.web.action.playoption;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.PlayOptionService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.PlayOption;

public class AjaxSaveAction extends BaseAction {

    private static final long serialVersionUID = -4279267675746699171L;

    @Autowired
    private PlayOptionService playOptionService;

    private PlayOption option;

    private Data data;

    public String execute() {

        playOptionService.add(option);
        data = Data.success(null);

        return SUCCESS;
    }

    public PlayOption getOption() {
        return option;
    }

    public void setOption(PlayOption option) {
        this.option = option;
    }

    public Data getData() {
        return data;
    }

}
