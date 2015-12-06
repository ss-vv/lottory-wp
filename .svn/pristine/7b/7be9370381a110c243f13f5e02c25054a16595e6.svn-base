package com.xhcms.lottery.commons.persist.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.CGJIssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.CGJIssueInfoPO;

public class CGJIssueInfoDaoImpl extends DaoImpl<CGJIssueInfoPO> implements CGJIssueInfoDao {

    private static final long serialVersionUID = -8866968344416859253L;

    public CGJIssueInfoDaoImpl() {
        super(CGJIssueInfoPO.class);
    }

	@Override
	public CGJIssueInfoPO find(String playType, String season) {
		if(StringUtils.isNotBlank(playType) &&
				StringUtils.isNotBlank(season)) {
			Criteria c = createCriteria();
			c.add(Restrictions.eq("playType", playType));
			c.add(Restrictions.eq("season", season));
			Object result = c.uniqueResult();
			if(null != result) {
				return (CGJIssueInfoPO) result;
			}
		}
		return null;
	}
}