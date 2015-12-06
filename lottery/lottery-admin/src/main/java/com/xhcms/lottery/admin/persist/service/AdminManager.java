package com.xhcms.lottery.admin.persist.service;

import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.commons.persist.entity.AdminPO;

public interface AdminManager {
	Admin login(String username, String password);
	
	/**
	 * 重置密码
	 * @param id
	 * @param oldPassword
	 * @param password
	 */
	void resetPassword(int id, String oldPassword, String password);
	
	/**
	 * 创建用户
	 */
	void saveAdmin(Admin admin);

	@Transactional
	AdminPO getAdmin(String username);
}
