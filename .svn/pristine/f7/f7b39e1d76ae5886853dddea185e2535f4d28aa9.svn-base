package com.xhcms.lottery.admin.persist.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.persist.dao.AdminDao;
import com.xhcms.lottery.admin.persist.entity.AdminPO;
import com.xhcms.lottery.admin.persist.service.AdminManager;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.EntityStatus;

public class AdminManagerImpl implements AdminManager {
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	@Transactional
	public Admin login(String username, String password) {
		AdminPO po = adminDao.getAdmin(username);
		if(po != null) {
			String pwd = Text.MD5Encode(password); 
			// 判断用户密码 和 登录状态
			if (pwd.equals(po.getPassword()) && po.getStatus() != EntityStatus.LOCKED) {
				Admin a = new Admin();
				BeanUtils.copyProperties(po, a);
	            return a;
	        }
		}
		return null;
	}

	@Override
	@Transactional
	public void resetPassword(int id, String oldPassword, String password) {
		AdminPO po = adminDao.get(id);
		if(po != null) {
			Assert.eq(Text.MD5Encode(oldPassword), po.getPassword(), AppCode.PASSWD_LOGIN_WRONG);
			po.setPassword(Text.MD5Encode(password));
		}
	}
	
	@Override
	@Transactional
	public void saveAdmin(Admin admin) {
		AdminPO po = new AdminPO();
		BeanUtils.copyProperties(admin, po);
		po.setPassword(Text.MD5Encode(admin.getPassword()));
		adminDao.saveAdmin(po);
	}

}
