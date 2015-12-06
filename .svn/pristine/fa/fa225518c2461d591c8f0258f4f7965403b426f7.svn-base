package com.xhcms.lottery.admin.persist.service;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Grant;
import com.xhcms.lottery.commons.data.User;

public interface GrantService {
	/**
	 * 查询赠款
	 * @param paging
	 * @param userId
	 * @param status
	 * @param beginTime
	 * @param endTime
	 */
	void listGrant(Paging paging, long userId, int status, Date beginTime, Date endTime);
	
	/**
	 * 发起赠款
	 * @param users  用户列表
	 * @param grant  赠款记录
	 * @return 执行成功的记录ID
	 */
	List<Long> sponsorGrant(List<User> users, Grant grant, int userId);
	
	/**
	 * 批量审核通过
	 * @param ids
	 * @param auditId
	 */
	void batchPass(List<Long> ids, int auditId);
	
	void batchReject(List<Long> ids, int auditId);
	
}
