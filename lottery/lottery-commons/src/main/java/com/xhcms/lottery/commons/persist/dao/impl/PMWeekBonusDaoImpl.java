package com.xhcms.lottery.commons.persist.dao.impl;

import org.springframework.stereotype.Repository;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PMWeekBonusDao;
import com.xhcms.lottery.commons.persist.entity.PMWeekBonusPO;

@Repository
public class PMWeekBonusDaoImpl extends DaoImpl<PMWeekBonusPO> implements PMWeekBonusDao {

	private static final long serialVersionUID = -1237829893431275138L;

	public PMWeekBonusDaoImpl(){
		super(PMWeekBonusPO.class);
	}

}
