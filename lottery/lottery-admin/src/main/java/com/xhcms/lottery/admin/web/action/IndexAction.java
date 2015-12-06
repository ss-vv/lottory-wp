package com.xhcms.lottery.admin.web.action;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.lang.AdminConstant;
import com.xhcms.lottery.admin.persist.service.AdminManager;
import com.xhcms.lottery.admin.shiro.service.ShiroService;
import com.xhcms.lottery.commons.persist.entity.AdminPO;

public class IndexAction extends BaseAction {
	private static final long serialVersionUID = -3955035997869421877L;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AdminManager adminMgr;
	private Admin admin = new Admin();
	
	
	@Override
	public String execute() {
		Subject subject = ShiroService.subject();
		log.info("login succ. isAuthed={}", subject.isAuthenticated());
		
		AdminPO po = adminMgr.getAdmin(subject.getPrincipal().toString());
		if(null != po) {
			BeanUtils.copyProperties(po, admin);
			request.getSession().setAttribute(AdminConstant.USER, admin);
		}
		
		return SUCCESS;
	}
	
	@Override
	public Admin getAdmin(){
		return admin;
	}
}
