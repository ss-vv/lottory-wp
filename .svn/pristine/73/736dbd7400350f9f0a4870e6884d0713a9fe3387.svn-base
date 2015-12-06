package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.HfBetContentDao;
import com.xhcms.lottery.commons.persist.entity.HfBetContentPO;

public class HfBetContentDaoImpl extends DaoImpl<HfBetContentPO> implements
		HfBetContentDao {

	private static final long serialVersionUID = 1L;

	public HfBetContentDaoImpl() {
		super(HfBetContentPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HfBetContentPO> findHfBetContent(Long schemeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from HfBetContentPO ").append("where schemeId = :schemeId");
		Query query = createQuery(String.valueOf(sql)).setLong("schemeId",
				schemeId);
		return query.list();
	}

}
