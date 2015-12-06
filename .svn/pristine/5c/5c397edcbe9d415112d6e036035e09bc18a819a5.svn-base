/**
 * 
 */
package com.xhcms.lottery.admin.web.action.match;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.MatchService;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class AjaxEditFBMatchAction extends BaseAction {

    private static final long serialVersionUID = 6385058823032919922L;

    @Autowired
    private MatchService matchService;

    private Long id;

    private int status;

    private Data data;

    public String execute() {

        matchService.updateFBMatch(id, status);
        data = Data.success(null);

        return SUCCESS;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public Data getData() {
        return data;
    }

}
