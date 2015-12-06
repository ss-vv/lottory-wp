package com.xhcms.ucenter.web.action.verify;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * Title: 邮箱验证，确认邮箱绑定
 * 
 * @author xulang
 * @version 1.0
 */
public class ConfirmEmailAction extends BaseAction {
	private static final long serialVersionUID = 2060663097781832693L;

	@Autowired
	private IVerifyService bindEmailService;

	private String code;

	public String execute() {
		if (StringUtils.isEmpty(code)) {
			addActionMessage(getText("user.verify.email.null.code"));
			return INPUT;
		}
		bindEmailService.verify(getSelf().getId(), code);
		addActionMessage(getText("user.verify.confirmemail.success"));
		return SUCCESS;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
