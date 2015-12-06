package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.List;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBEuroOddsPO;
import com.xhcms.commons.persist.Dao;

public interface FBEuroOddsDao extends Dao<FBEuroOddsPO> {

	/**
	 * 
	 * @param matchId
	 * @param corpId
	 * @return
	 */
	FBEuroOddsPO findBy(long matchId, long corpId);

	/**
	 * flush session 并且将 po 从session中去掉，避免"different object with the same identifier value was already associated with the session."问题。 
	 * @param po 要从session去掉的 PO 对象。
	 */
	void flushAndEvict(Object po);

	/**
	 * 根据球探大V彩赛事ID查询欧赔列表
	 * @param qtMatchId
	 * @return
	 */
	List<FBEuroOddsPO> findEuropeOddsList(long qtMatchId);
}
