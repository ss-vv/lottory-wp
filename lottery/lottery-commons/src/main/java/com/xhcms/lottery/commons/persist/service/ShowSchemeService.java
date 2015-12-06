package com.xhcms.lottery.commons.persist.service;

import java.util.Date;
import java.util.HashMap;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

/**
 * 晒单服务
 * @author yonglizhu
 */
public interface ShowSchemeService {
	
	/**
	 * 查找在售的晒单方案。
	 * 排序规则: (全部推荐彩种) 人气、截止时间、投注金额、战绩;
	 * <br/>
	 * 排序: (足球、篮球) 是否推荐、人气、截止时间、投注金额、战绩;
	 */
	void findOnSaleShowingSchemes(Paging paging, boolean isRecommend,
			String lotteryId, String username, String playId, int followRatio,
			String orderBy, boolean asc);
	
	/**
	 * 查询某个人的晒单方案
	 * @param userId
	 * @return
	 */
	void findShowSchemesByCondition(Paging paging, ShowFollowQueryCondition condition);
	
	/**
	 * 查询某个人的跟单方案
	 * @param userId
	 * @return
	 */
	void findFollowSchemesByCondition(Paging paging, ShowFollowQueryCondition condition);
	
	/**
	 * 查询晒单推荐方案
	 * @param paging
	 */
	void pagingRecommendFollowShcemes(Date startTime, Date endTime, Paging paging);
	
	/**
	 * 中奖晒单跟单奖励活动
	 * @param operator
	 * @param pos
	 */
	void awardedShowFollowGrant(Integer operator, BetSchemePO betSchemePo);
	
	/**
	 * 未中奖晒单跟单奖励活动
	 * @param operator
	 * @param sMap
	 */
	void notAwardShowFollowGrant(Integer operator, HashMap<Long, BetSchemePO> sMap);
}
