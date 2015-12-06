package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.SsqIssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.SsqInfoPO;

public class SsqIssueInfoDaoImpl extends DaoImpl<SsqInfoPO> implements SsqIssueInfoDao {

	private static final long serialVersionUID = 1L;
	
	public SsqIssueInfoDaoImpl(){
		super(SsqInfoPO.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public SsqInfoPO getSsqInfoPOByIssueNum(String issueNumber) {
		Criteria criteria = createCriteria()
				.add(Restrictions.eq("issueNumber", issueNumber));
		List<SsqInfoPO> list = criteria.list();
		return list.size() > 0 ? list.get(0) : null;
	}

}
