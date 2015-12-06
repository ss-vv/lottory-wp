package com.xhcms.ucenter.persist.dao;

import com.xhcms.commons.persist.Dao;
import com.xhcms.ucenter.persist.entity.SysMessagePO;

public interface ISysMessageDao extends Dao<SysMessagePO> {
	int getUnreadSysMessageCount(long userId);
}
