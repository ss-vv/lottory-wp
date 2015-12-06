package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.data.service.store.persist.dao.BBOddsBigSmallDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBOddsBigSmallPO;

@Repository
public class BBOddsBigSmallDaoImpl extends BasicDaoImpl<BBOddsBigSmallPO>
		implements BBOddsBigSmallDao {

	private static final long serialVersionUID = 1L;

	public BBOddsBigSmallDaoImpl() {
		super(BBOddsBigSmallPO.class);
	}

	@Override
	public BBOddsBigSmallPO findBy(long matchId, long corpId) {
		return (BBOddsBigSmallPO) createQuery(
				"from BBOddsBigSmallPO where matchId=? and corpId=?")
				.setLong(0, matchId).setLong(1, corpId).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BBOddsBigSmallPO> findByMatchId(long matchId) {
		return createQuery("from BBOddsBigSmallPO where matchId=?").setLong(0,
				matchId).list();
	}

}
