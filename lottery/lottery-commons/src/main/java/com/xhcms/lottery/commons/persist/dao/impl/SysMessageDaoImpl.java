package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.SysMessageDao;
import com.xhcms.lottery.commons.persist.entity.SysMessagePO;
import com.xhcms.lottery.lang.UCMessageStatus;

public class SysMessageDaoImpl extends  DaoImpl<SysMessagePO> implements SysMessageDao {

	private static final long serialVersionUID = -8080084709954155023L;

	public SysMessageDaoImpl() {
		super(SysMessagePO.class);
	}

	@Override
	public List<SysMessagePO> listSysMessage(Paging paging, String subject) {
		PagingQuery<SysMessagePO> pq = pagingQuery(paging);
        if(StringUtils.isNotBlank(subject)){
            pq.add(Restrictions.like("subject", subject, MatchMode.ANYWHERE));
        }
        return pq.desc("createdTime").list();
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