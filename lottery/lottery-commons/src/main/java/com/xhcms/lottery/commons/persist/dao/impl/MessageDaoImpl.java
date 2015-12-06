package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.MessageDao;
import com.xhcms.lottery.commons.persist.entity.MessagePO;
import com.xhcms.lottery.lang.EnumMessageType;
import com.xhcms.lottery.lang.UCMessageStatus;

public class MessageDaoImpl extends DaoImpl<MessagePO> implements
		MessageDao {
	
	private static final long serialVersionUID = 7741183428189019508L;

	public MessageDaoImpl() {
		super(MessagePO.class);
	}
	
	@Override
	public void syncSysMessage(long userId, String username) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into uc_message(sys_message_id,user_id,username,type, ")
		.append(" isread,subject,note,authorid,author,created_time,del_status) ")
		.append(" select s.id,"+userId+",'"+username+"',")
		.append(EnumMessageType.SYSTEM_MESSAGE.getValue()+","+UCMessageStatus.UNREAD+",s.subject,s.note,s.authorid, ")
		.append(" s.author,now(),"+UCMessageStatus.NORMAL+" from uc_sysmessage s where s.id not in ")
		.append(" ( select m.sys_message_id from uc_message m where m.user_id=")
		.append(userId+" and m.sys_message_id<>"+UCMessageStatus.PERSONAL_MESSAGE+" ) ")
		.append(" and s.`status`="+UCMessageStatus.VALID );
		
		this.createSQLQuery(sql.toString()).executeUpdate();
	}
	
	@Override
	public List<MessagePO> find(Paging p, long userId, int delStatus) {
		PagingQuery<MessagePO> q = pagingQuery(p);
		q.add(Restrictions.eq("userId", userId))
		.add(Restrictions.eq("delStatus", delStatus))
		.asc("read").desc("id");
		
		return q.list();
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
