package com.xhcms.lottery.account.service.impl;

import java.util.Date;

import org.hibernate.Query;

import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.account.service.QTFBMatchDao;

public class QTFBMatchDaoImpl extends DaoImpl<QTMatchPO> implements QTFBMatchDao{
	private static final long serialVersionUID = 1L;

	public QTFBMatchDaoImpl() {
		 super(QTMatchPO.class);
	}

	@Override
	public QTMatchPO findById(long qtMatchId) {
		if(qtMatchId > 0) {
			Query qs = createQuery("from QTMatchPO as m where m.id = :id");
			qs.setParameter("id", qtMatchId);
			return (QTMatchPO) qs.uniqueResult();
		}
		return null;
	}

	@Override
	public QTMatchPO findByJingCaiIdAndMatchTime(String jingcaiId, Date matchTime) {
		Query qs = createQuery("from QTMatchPO as m where m.jingCaiId = :jingCaiId and m.matchTime = :matchTime");
		qs.setParameter("jingCaiId", jingcaiId);
		qs.setParameter("matchTime", matchTime);
		return (QTMatchPO) qs.uniqueResult();
	}
}
