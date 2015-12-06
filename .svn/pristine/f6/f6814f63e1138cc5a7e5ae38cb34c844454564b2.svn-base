package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.RegistCode;
import com.xhcms.lottery.lang.RegistCodeStatus;

public interface RegistrationCodeService {

	/**
	 * 查询邀请码状态
	 * 		-会计算邀请码当前状态并更新邀请码状态
	 * @param code
	 * @return
	 */
	public RegistCodeStatus findAndUpdateRegistrationCodeStatus(String code);
	
	public void updateRegistrationCodeStatus(String code, RegistCodeStatus status, long userId);

	public List<RegistCode> listByPagger(Paging paging, int status, int vid);

	public void genereateCodeAndSave(long genvid, int codeMaxSize, int expiryDay);
}
