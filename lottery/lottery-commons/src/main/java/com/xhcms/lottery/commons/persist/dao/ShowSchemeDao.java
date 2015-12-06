package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.ShowFollowQueryCondition;

/**
 * @author yonglizhu
 */
public interface ShowSchemeDao extends Dao<BetSchemePO> {

	List<BetSchemePO> findOnSaleShowingSchemes(Paging paging,
			DetachedCriteria detachedCriteria);
	
	List<BetSchemePO> findShowSchemes(ShowFollowQueryCondition condition);
	
	List<BetSchemePO> findFollowSchemes(ShowFollowQueryCondition condition);
	
	List findShowWinListFromScheme();
	
	List countFollowDataforShowWinList(long sponsorId, String lotteryId);
	
	List findFollowWinListFromScheme();

	List<BetSchemePO> queryShowSchemes(int recommend, Date startTime,
			Date endTime, Paging paging);
	
	List findFollowByUserId(Long showSchemeId, Long userId);
}
