package com.xhcms.lottery.account.web.action.inbox;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.MessageService;
/**
 * 收件箱
 * @author zhuyongli
 *
 */
public class AjaxInboxAction extends BaseAction {

	private static final long serialVersionUID = -3321001650666781909L;
	
	@Autowired
	private MessageService messageService;

    private Data data = Data.success("");
    
	@Override
	public String execute() {
		int count = messageService.getUnreadCount(getUserId());
		if(count > 0) {
			data = Data.success(count);
		} else {
			data = null;
		}
		return SUCCESS;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
