package com.xhcms.lottery.dc.task.repeat;

import java.util.Date;
import java.util.List;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.repeat.RepeatPlan;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanIssues;
import com.xhcms.lottery.commons.exception.RepeatPlanException;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.RepeatIssuePlanResult;
import com.xhcms.lottery.service.RepeatService;
import com.xhcms.lottery.utils.DateUtils;
/**
 * @desc
 * @createTime 2013-8-9
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class RepeatTask extends AbstractRepeatPlanTask {
	
	RepeatService repeatService;
	
	private IssueService issueService;
	
	private String supportLottery = LotteryId.SSQ.name();
	
	@Override
	protected List<RepeatPlan> getRepeatPlanListCanExecuted() {
		List<RepeatPlan> planList = repeatService.
				queryExecutedRepeatPlan(supportLottery);
		return planList;
	}

	@Override
	protected void execute() {
		try {
			List<RepeatPlan> planList = getRepeatPlanListCanExecuted();
			logger.info("查询彩种={}，追号计划：{}", new Object[]{supportLottery, planList});
			if(null != planList && planList.size() > 0) {
				Date now = new Date();
				//查询当前在售期信息
				IssueInfo issueInfo = issueService.findCurrentOnSaling(supportLottery, now);
				if(null != issueInfo && null != issueInfo.getId()) {
					String currIssueNumber = issueInfo.getIssueNumber();
					for(RepeatPlan plan : planList) {
						executeIssuePlan(plan, currIssueNumber);
					}
				} else {
					logger.info("彩种={}，当前时间={}， 不存在在售期信息!", 
							new Object[]{supportLottery, DateUtils.format(now)});
				}
				//执行完成期计划之后检查是否需要更新对应的计划状态
				for(RepeatPlan plan : planList) {
					repeatService.isChangePlanStatus(plan.getId());
				}
			}
		} catch (Exception e) {
			logger.error("执行追号计划任务出现异常!", e);
		}
	}
	
	private void executeIssuePlan(RepeatPlan plan, String currIssueNumber) {
		try {
			repeatService.executeIssuePlan(plan, currIssueNumber);
		} catch (RuntimeException e) {
			if(e instanceof XHRuntimeException) {
				RepeatPlanIssues issuePlan = new RepeatPlanIssues();
				int errorCode = ((XHRuntimeException) e).getCode();
				switch (errorCode) {
				case AppCode.INSUFFICIENT_BALANCE:
				case AppCode.ACCOUNT_FROZED:
					//如果当前用户资金不足，已经追号失败；在当前销售期可投情况下，用户再次充值，不可继续追号
					issuePlan.setExecuted(true);
					issuePlan.setResult(RepeatIssuePlanResult.FAIL_NO_MONEY.getType());
					break;
				default:
					issuePlan.setExecuted(true);
					issuePlan.setResult(RepeatIssuePlanResult.FAIL_OTHER.getType());
					break;
				}
			} else {
				logger.info("执行追号计划时出现异常...当前在售期={}，计划信息={}，异常信息={}", 
						new Object[]{currIssueNumber, plan, e.getMessage()});
			}
		} catch (RepeatPlanException e) {
			logger.error("追号计划执行异常！", e);
		}
	}
	
	@Override
	public String toString() {
		return "SSQ追号后台执行任务" + this.getClass().getName();
	}

	public void setRepeatService(RepeatService repeatService) {
		this.repeatService = repeatService;
	}

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}
}
