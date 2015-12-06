package com.xhcms.ucenter.persist.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.lang.UCMessageStatus;
import com.xhcms.ucenter.persist.dao.IMessageDao;
import com.xhcms.ucenter.persist.entity.MessagePO;

public class MessageDaoImpl extends DaoImpl<MessagePO> implements
		IMessageDao {
	private static final long serialVersionUID = 5820196598337515886L;

	public MessageDaoImpl() {
		super(MessagePO.class);
	}
	
	@Override
	public int getUnreadMessageCount(long userId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("userId", userId))
		.add(Restrictions.eq("read", UCMessageStatus.UNREAD))
		.add(Restrictions.eq("delStatus", UCMessageStatus.NORMAL));
		
		int count = ((Long)(c.setProjection(Projections.rowCount()).uniqueResult())).intValue();
		return count;
	}

}
