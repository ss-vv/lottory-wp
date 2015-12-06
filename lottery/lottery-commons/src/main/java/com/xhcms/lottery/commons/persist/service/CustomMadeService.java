/**
 * 
 */
package com.xhcms.lottery.commons.persist.service;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.CustomMade;

/**
 * @author Bean.Long
 *
 */
public interface CustomMadeService {

	CustomMade getCustomMade(long userId, long fuid);

	void updateCustomMade(long userId, long fuid, CustomMade customMade);
	
	void deleteCustomMade(long id);
	
	/**
	 * 获取定制列表
	 * @param type 0 表示我的定制, 1 表示定制我的人
	 * @param userId type=0,表示当前用户ID type=1,表示被定制人的用户ID
	 * @param paging 结果页
	 */
	void listCustomMades(int type, long userId, Paging paging);
	
	
	/**
	 * 查询定制产生的明细
	 * @param type 0 表示我的定制, 1 表示定制我的人
	 * @param userId type=0,表示当前用户ID type=1,表示被定制人的用户ID
	 * @param followedUserId 表示被follow的用户ID
	 * @param paging 结果页
	 */
	void listCustomMadeDetails(long userId, long followedUserId, Paging paging);
	
	/**
	 * 统计某个用户的被定制数
	 * @param fuid
	 * @return
	 */
	int countCustomMades(Long fuid);
}
