package com.xhcms.lottery.commons.persist.dao;

import java.util.List;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanSchedule;
import com.xhcms.lottery.commons.persist.entity.RepeatIssuePlanPO;
import com.xhcms.lottery.lang.RepeatIssuePlanExecuteStatus;
import com.xhcms.lottery.lang.RepeatIssuePlanIsValid;

public interface RepeatIssuePlanDao extends Dao<RepeatIssuePlanPO> {
	
	long saveIssuePlan(RepeatIssuePlanPO issuePlanPO);
	
	void saveIssuePlanBatch(List<RepeatIssuePlanPO> list);

	List<RepeatIssuePlanPO> queryIssuePlanList(long planId);
	
	RepeatIssuePlanPO findValidUnExecuteIssuePlan(long planId, String issueNumber);
	
	List<RepeatIssuePlanPO> queryIssuePlanList(long planId, 
			RepeatIssuePlanIsValid issuePlanIsValid,
			RepeatIssuePlanExecuteStatus executeStatus);

	void updateIssuePlan(long planId, RepeatIssuePlanIsValid issuePlanIsValid);

	List<RepeatIssuePlanPO> queryIssuePlanByIssueNumber(String issueNumber);

	RepeatPlanSchedule loadPlanScheduleByPlanId(long planId);
}
