package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.unison.lottery.weibo.data.service.store.data.QTMatchVO;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.xhcms.commons.persist.Dao;

public interface QTMatchDao extends Dao<QTMatchPO>{

	List<Object[]> findExistMatchs(Set<Long> ids);

	/**
	 * flush session 并且将 po 从session中去掉，避免"different object with the same identifier value was already associated with the session."问题。 
	 * @param po 要从session去掉的 PO 对象。
	 */
	void flushAndEvict(Object po);
	
	/**
	 * 通过大V彩赛事ID查询球探赛事ID
	 * @param lcMatchId
	 * @return
	 */
	int queryQTMatchId(String lcMatchId);
	
	/**
	 * 根据球探赛事ID查询球探对赛记录
	 * @param qtMatchId
	 * @return
	 */
	QTMatchPO queryQTMatchInfo(long qtMatchId);
	
	/**
	 * 根据球队ID获取对应主客队最近size场已完场数据
	 * @param teamId
	 * @param size	数量
	 * @return
	 */
	List<QTMatchPO> findLastestCompleteQTMatchPO(long teamId, int size);

	void updateQTSiteDataToQTMatch(QTMatchPO qt);
	
	/**
	 * 查找赛事 （默认按照时间降序排列）
	 * @param homeTeamId 不需要该条件时 传入 null
	 * @param guestTeamId 不需要该条件时 传入 null
	 * @param from 开始时间   不需要该条件时 传入 null
	 * @param to 结束时间   不需要该条件时 传入 null
	 * @param size 大小
	 * @return
	 */
	List<QTMatchPO> findQTMatchPO(long homeTeamId,long guestTeamId, Date from,Date to, int size);
	List<QTMatchPO> findQTMatchPO(long teamId, Date from,Date to, int size);

	List<Long> findExistMatchIds(Set<Long> ids);
	
	void updateQTBFDataToQTMatch(QTMatchPO qt) throws Exception;

	List<QTMatchVO> findQTMatchByDate(Date start, Date end);

	List<QTMatchVO> findQTMatchByIssueNum(String issueNum);
	
	String findLCMatchId(long qiuTanMatchId);
	
	/**
	 * 根据大V彩赛事ID，查询主客队的排名
	 * @param lcMatchIdList
	 * @return
	 * 对象数组
	 * 索引0：lcMatchId
	 * 索引1：主队排名
	 * 索引2：客队排名
	 */
	List<Object[]> findQTFBMatchPosition(Collection<String> lcMatchIdList);
	
	/**
	 * 根据大V彩赛事ID，查询主客队的排名
	 * @param lcMatchIdList
	 * @return
	 * 对象数组
	 * 索引0：lcMatchId
	 * 索引1：主队排名
	 * 索引2：客队排名
	 */
	List<Object[]> findQTBBMatchPosition(Collection<String> lcMatchIdList);
	
	List<Object[]> findQTFBMatchResult();
	String getTeamColor(Long id);
	
	
	
}
