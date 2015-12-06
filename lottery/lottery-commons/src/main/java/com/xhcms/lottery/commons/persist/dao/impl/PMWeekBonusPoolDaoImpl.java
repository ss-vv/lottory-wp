package com.xhcms.lottery.commons.persist.dao.impl;

import org.springframework.stereotype.Repository;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PMWeekBonusPoolDao;
import com.xhcms.lottery.commons.persist.entity.PMWeekBonusPoolPO;

@Repository
public class PMWeekBonusPoolDaoImpl extends DaoImpl<PMWeekBonusPoolPO> implements PMWeekBonusPoolDao {

	private static final long serialVersionUID = -8289248084430106038L;

	public PMWeekBonusPoolDaoImpl(){
		super(PMWeekBonusPoolPO.class);
	}

}
