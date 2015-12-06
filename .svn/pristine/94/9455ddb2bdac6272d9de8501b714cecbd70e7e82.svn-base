package com.xhcms.ucenter.persist.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.lang.UCMessageStatus;
import com.xhcms.ucenter.persist.dao.ISysMessageDao;
import com.xhcms.ucenter.persist.entity.SysMessagePO;

public class SysMessageDaoImpl extends DaoImpl<SysMessagePO> implements ISysMessageDao {
	private static final long serialVersionUID = 3163582839996063610L;
	
	public SysMessageDaoImpl() {
		super(SysMessagePO.class);
	}
	
	@Override
	public int getUnreadSysMessageCount(long userId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("status", UCMessageStatus.VALID))
		.add(Restrictions.sqlRestriction(" id not in (select m.sys_message_id from uc_message m where m.user_id="+
				userId+" and m.sys_message_id<>"+UCMessageStatus.PERSONAL_MESSAGE+") "));
		
		int count = ((Long)(c.setProjection(Projections.rowCount()).uniqueResult())).intValue();
		return count;
	}
}
