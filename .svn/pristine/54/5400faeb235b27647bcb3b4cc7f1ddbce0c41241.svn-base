package com.xhcms.lottery.admin.web.action.passtype;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.PassTypeService;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * <p>Title: 玩法与过关方式关联维护</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class AjaxSaveAction extends BaseAction {

    private static final long serialVersionUID = 3330389654667757618L;

    @Autowired
    private PassTypeService passTypeService;

    private String playId;

    private String passtype;

    private Data data;

    public String execute() {
        Pattern p = Pattern.compile(",");
        passTypeService.modify(playId, p.split(passtype));
        data = Data.success(null);

        return SUCCESS;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public void setPasstype(String passtype) {
        this.passtype = passtype;
    }

    public Data getData() {
        return data;
    }

}
