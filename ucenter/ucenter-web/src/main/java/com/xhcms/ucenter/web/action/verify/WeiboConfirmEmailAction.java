package com.xhcms.ucenter.web.action.verify;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.commons.lang.Data;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * 
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-5-5 下午5:44:19
 */
public class WeiboConfirmEmailAction extends BaseAction {
	private static final long serialVersionUID = 2060663097781832693L;

	@Autowired
	private IVerifyService bindEmailService;

	private String code;
	
	private Data data;
	
	private String weiboUserId;
	
	@Autowired
	private UserAccountService userAccountService;
	
	public String execute() {
		if (StringUtils.isEmpty(code)) {
			data = Data.failure(getText("user.verify.email.null.code"));
			return SUCCESS;
		}
		bindEmailService.verify(getSelf().getId(), code);
		data = Data.success(getText("user.verify.confirmemail.success"));
		weiboUserId = userAccountService.findWeiboUserByLotteryUid(getSelf().getId() + "").getWeiboUserId() + "";
		return SUCCESS;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getWeiboUserId() {
		return weiboUserId;
	}

	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}
}
