package com.xhcms.lottery.admin.web.action.grant;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.xhcms.lottery.admin.persist.service.GrantService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Grant;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.UserService;

public class SponsorGrantAction extends BaseAction {
	private static final long serialVersionUID = 4116701466978019841L;
	@Autowired
	private GrantService grantService;
	@Autowired
	private UserService userService;
	
	private String username;
	private String realname;
	private BigDecimal amount;
	private String note;
	
	@Override
	public String execute() {
		return SUCCESS;
	}
	
	public String post() {
		User user  = userService.getUser(username, realname);
		if(user != null) {
			Grant g = new Grant();
			g.setAmount(amount);
			g.setNote(note);
			List<Long> ids = grantService.sponsorGrant(Collections.singletonList(user), g, getMyId());
			if (ids.size() < 1) {
				addActionMessage("没有找到" + username + "的账户信息");
			} else {
				addActionMessage(getText("message.success"));
			}
		} else {
			addFieldError("username", getText("grant.userNotExist"));
		}
		return SUCCESS;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	@RequiredFieldValidator(key="grant.amount.mustInput")
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	public String getUsername() {
		return username;
	}

	@RequiredFieldValidator(key="grant.username.mustInput")
	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}
	
	@RequiredFieldValidator(key="grant.realname.mustInput")
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
}
