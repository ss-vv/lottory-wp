package com.xhcms.lottery.admin.web.action.scheme;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * @desc 重置方案为出票成功状态
 * @createTime 2012-12-17
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class AjaxSetSchemeStatusToSuccessAction extends BaseAction {

    private static final long serialVersionUID = 5139362559096601567L;

    @Autowired
    private BetSchemeService betSchemeService;

    private long sid;

    private Data data = Data.success(getText("message.success"));

    @Override
    public String execute() throws Exception {
    	try {
    		betSchemeService.setToSchemeBuySuccess(getMyId(), sid);
		} catch (Exception e) {
			data = Data.failure(getText("message.fail"));
		}
        return super.execute();
    }

    public Data getData() {
        return data;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}