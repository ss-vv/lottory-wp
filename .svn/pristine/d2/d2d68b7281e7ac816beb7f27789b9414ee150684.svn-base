package com.xhcms.lottery.commons.persist.dao;

import java.util.List;
import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.SysMessagePO;

/**
 * @desc 系统站内信息dao
 * @author yonglizhu
 */
public interface SysMessageDao extends Dao<SysMessagePO>{

	/**
	 * 查询所有的站内信息
	 * @param paging
	 * @param subject
	 * @return
	 */
	List<SysMessagePO> listSysMessage(Paging paging, String subject);
	
	/**
	 * 取得系统信息中该用户未读的信息
	 * @param userId
	 * @return
	 */
	int getUnreadSysMessageCount(long userId);
	
}
