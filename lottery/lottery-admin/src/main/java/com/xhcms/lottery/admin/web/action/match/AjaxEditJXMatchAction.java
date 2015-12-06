/**
 * 
 */
package com.xhcms.lottery.admin.web.action.match;

import javax.annotation.Resource;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.IssueService;

/**
 * @desc 接收编辑期次信息请求的Action
 * @createTime 2012-9-21
 * @author lei.li
 * @version 1.0
 */
public class AjaxEditJXMatchAction extends BaseAction {

    private static final long serialVersionUID = 6385058823032919922L;

    @Resource
    private IssueService issueService;

    private Long id;

    private int status;

    private Data data;

    public String execute() {
    	issueService.updateJXIssue(id, status);
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
