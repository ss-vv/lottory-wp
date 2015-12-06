package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;

public interface BJDCMatchDao extends Dao<BJDCMatchPO>{

	List<BJDCMatchPO> find(Set<Long> idSet);

	/**
	 * 根据时间范围分页查询
	 * @param paging
	 * @param from
	 * @param to
	 * @return
	 */
	List<BJDCMatchPO> find(Paging paging, Date from, Date to);
	
	List<Object[]> findMatchById(Long id);
	//查询在售赛事数量
	Integer findMatchCount();
	BJDCMatchPO findMatch(Long matchid);
	
	List<BJDCMatchPO> findBJDCMatchByPlayTime(Date beginTime);
}
