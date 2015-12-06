package com.xhcms.lottery.admin.web.action.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.lang.AdminConstant;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.UserService;

public class UpdateUserRealnameAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private UserService userService;
	
	private long id;
	
	private String realname;
	
	@Override
    public String execute() {
		String result = null;
		Admin user = (Admin)request.getSession().getAttribute(AdminConstant.USER);
		
		if(null != user && "root".equals(user.getUsername())) {
			try {
				userService.updateRealname(id, realname);
				result = SUCCESS;
			} catch (Exception e) {
				result = ERROR;
				logger.error("update User realname error:" + e.getMessage());
			}
		}
		
        return result;
    }
	
	public void setId(long id) {
		this.id = id;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
}
