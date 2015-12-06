package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBConcedeScoreOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBConcedeScoreOddsPO;

@Repository
@SuppressWarnings("unchecked")
public class BBOddsConcedeDaoImpl extends BasicDaoImpl<BBConcedeScoreOddsPO> implements BBConcedeScoreOddsDao {

	public BBOddsConcedeDaoImpl(){
		super(BBConcedeScoreOddsPO.class);
	}
	
	private static final long serialVersionUID = -6878032846236242641L;

	@Override
	public BBConcedeScoreOddsPO findBy(long matchId, long corpId) {
		return (BBConcedeScoreOddsPO) createQuery("from BBConcedeScoreOddsPO where matchId=? and corpId=?")
		.setLong(0, matchId)
		.setLong(1, corpId).uniqueResult();
	}

	@Override
	public List<BBConcedeScoreOddsPO> findByMatchId(long matchId) {
		return createQuery("from BBConcedeScoreOddsPO where matchId=?")
				.setLong(0, matchId).list();
	}
}