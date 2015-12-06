package com.xhcms.lottery.admin.web.action.play;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.PlayService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Play;

public class AjaxSaveAction extends BaseAction {

    private static final long serialVersionUID = -107356706209760852L;

    @Autowired
    private PlayService playService;

    private Play play;

    private Data data;

    public String execute() {

        playService.add(play);
        data = Data.success(null);

        return SUCCESS;
    }

    public void setPlay(Play play) {
        this.play = play;
    }

    public Play getPlay() {
        return play;
    }

    public Data getData() {
        return data;
    }

}
