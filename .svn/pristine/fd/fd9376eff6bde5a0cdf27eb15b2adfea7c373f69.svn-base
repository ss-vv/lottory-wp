package com.xhcms.lottery.commons.persist.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.RepeatPlanPO;
import com.xhcms.lottery.lang.RepeatPlanStatus;

/**
 * 
 * @desc 追号计划的数据库操作
 * @createTime 2013-8-6
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public interface RepeatDao extends Dao<RepeatPlanPO> {
	
	long savePlan(RepeatPlanPO repeatPlanPO);
	
	List<RepeatPlanPO> queryRepeatPlanList(RepeatPlanStatus planStatus, String lotteryId);
	
	RepeatPlanPO findRepeatPlanById(long planId);

	BigDecimal queryBonusAmountByPlan(long planId);

	int winNoteIssuePlan(long planId);

	List<RepeatPlanPO> findRepeatPlan(String lottery, Long userId, Date from,
			Date to, int status, Paging paging);
}
