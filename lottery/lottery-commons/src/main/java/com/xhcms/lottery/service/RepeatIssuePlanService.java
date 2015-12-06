package com.xhcms.lottery.service;

import java.util.List;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanIssues;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanSchedule;
import com.xhcms.lottery.lang.RepeatIssuePlanExecuteStatus;
import com.xhcms.lottery.lang.RepeatIssuePlanIsValid;

/**
 * 追号期计划服务
 * 
 * @author lei.li@unison.net.cn
 */
public interface RepeatIssuePlanService {
	
	/**
	 * 更新期计划的状态
	 * @param issuePlanIsValid
	 */
	void updateIssuePlan(long planId, RepeatIssuePlanIsValid issuePlanIsValid);
	
	void updateIssuePlan(long planId, String issueNumber, RepeatPlanIssues issuePlan);
	
	/**
	 * 查询指定计划下面是否包含有效且未完成的期计划
	 * @param planId
	 * @return
	 */
	boolean isContainValidAndUncomplete(long planId);
	
	/**
	 * 根据当前计划ID，选出对应的期计划集合
	 * @param planId	追号计划ID
	 * @return
	 */
	List<RepeatPlanIssues> queryIssuePlanList(long planId);
	
	/**
	 * 创建追号方案的期计划
	 * @param repeatPlanId
	 * @param repeatPlan
	 * @return
	 */
	void createRepeatIssuePlan(List<RepeatPlanIssues> repeatIssuePlanList);
	
	/**
	 * 选出指定追号计划下面的期计划
	 * @param planId
	 * @param issuePlanIsValid	是否有效
	 * @param executeStatus		是否未被执行
	 * @return
	 */
	List<RepeatPlanIssues> queryIssuePlanList(long planId,
			RepeatIssuePlanIsValid issuePlanIsValid,
			RepeatIssuePlanExecuteStatus executeStatus);
	
	/**
	 * 选出指定追号计划下面有效且未被执行的期计划
	 * @param planId
	 * @return
	 */
	List<RepeatPlanIssues> queryIssuePlanListValidAndExecuted(long planId);
	
	/**
	 * 根据追号计划和期号查询有效且未执行的期计划
	 * @param planId
	 * @param issueNumber
	 * @return
	 */
	RepeatPlanIssues findValidUnExecuteIssuePlan(long planId, String issueNumber);
	
	/**
	 * 根据期号查询期计划
	 * @param issueNumber
	 * @return
	 */
	List<RepeatPlanIssues> queryIssuePlanByIssueNumber(String issueNumber);
	
	/**
	 * 返回计划进度
	 * @return
	 */
	RepeatPlanSchedule loadPlanScheduleByPlanId(long planId);
}
