package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.MessagePO;

public interface MessageDao extends Dao<MessagePO>{
	
	/**
	 * 同步系统信息到个人信息
	 * @param user
	 */
	void syncSysMessage(long userId, String username);
	
	/**
	 * 查询个人站内信息
	 * @param p
	 * @param userId
	 * @param delStatus
	 * @return
	 */
	List<MessagePO> find(Paging p, long userId, int delStatus);
	
	/**
	 * 取得个人未读的信息
	 * @param userId
	 * @return
	 */
	int getUnreadMessageCount(long userId);

}
