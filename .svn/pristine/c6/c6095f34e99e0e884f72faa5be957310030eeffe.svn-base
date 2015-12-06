package com.xhcms.ucenter.web.action.msg;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.message.IMessageService;
/**
 * 收件箱
 * @author zhuyongli
 *
 */
public class AjaxInboxAction extends BaseAction {

	private static final long serialVersionUID = 3908620708246206209L;

	@Autowired
	private IMessageService messageService;

    private Data data = Data.success("");
    
	@Override
	public String execute() {
		int count = messageService.getUnreadCount(getSelf().getId());
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
