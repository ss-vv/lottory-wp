package com.xhcms.lottery.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.repeat.RepeatBetContent;
import com.xhcms.lottery.commons.data.repeat.RepeatPlan;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanIssues;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanSchedule;
import com.xhcms.lottery.commons.data.repeat.RepeatRecord;
import com.xhcms.lottery.commons.exception.RepeatPlanException;
import com.xhcms.lottery.commons.persist.dao.RepeatBetContentDao;
import com.xhcms.lottery.commons.persist.dao.RepeatDao;
import com.xhcms.lottery.commons.persist.dao.RepeatIssuePlanDao;
import com.xhcms.lottery.commons.persist.entity.RepeatPlanPO;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.RepeatPlanStatus;
import com.xhcms.lottery.lang.RepeatPlanStopReasonType;
import com.xhcms.lottery.lang.RepeatPlanStopType;
import com.xhcms.lottery.lang.RepeatResp;
import com.xhcms.lottery.service.RepeatBetContentService;
import com.xhcms.lottery.service.RepeatIssuePlanService;
import com.xhcms.lottery.service.RepeatSchemeService;
import com.xhcms.lottery.service.RepeatService;
import com.xhcms.lottery.utils.DateUtils;

/**
 * 追号服务实现。
 * 
 * @author Yang Bo, lei.li
 */
public class RepeatServiceImpl implements RepeatService {

	@Autowired
	private RepeatDao repeatDao;
	
	@Autowired
	private RepeatIssuePlanService issuePlanService;
	
	@Autowired
	private RepeatBetContentService betContentService;
	
	@Autowired
	private RepeatIssuePlanDao issuePlanDao; 
	
	@Autowired
	private RepeatBetContentDao betContentDao;
	
	@Autowired
	private IssueService issueService;
	
	@Autowired
	private RepeatSchemeService repeatSchemeService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	@Transactional
	public void createRepeatPlans(BetScheme originalBetScheme,
			List<RepeatPlan> plans) {
	}
	
	@Override
	@Transactional
	public void updatePlan(RepeatPlan plan) {
		if(null != plan) {
			RepeatPlanPO planPO = repeatDao.findRepeatPlanById(plan.getId());
			if(null != planPO) {
				if(null != plan.getStatus()) {
					planPO.setStatus(plan.getStatus());
				}
				if(null != plan.getFinishTime()) {
					planPO.setFinishTime(plan.getFinishTime());
				}
				planPO.setStoppedReason(plan.getStoppedReason());
			}
		}
	}

	public BetScheme executePlansOnIssue(RepeatPlanIssues issueOnSale, Long sponsorId)
			throws RepeatPlanException {
		List<RepeatBetContent> betContentList = betContentService.
				queryBetContentOfPlanId(issueOnSale.getPlanId());
		BetScheme betScheme = repeatSchemeService.makeBetRequestAndBet(issueOnSale, betContentList, sponsorId);
		
		RepeatPlanIssues issuePlan = new RepeatPlanIssues();
		issuePlan.setExecutedTime(new Date());
		issuePlanService.updateIssuePlan(issueOnSale.getPlanId(), 
				issueOnSale.getIssueNumber(), issuePlan);
		return betScheme;
	}

	@Override
	@Transactional
	public BetScheme executeIssuePlan(RepeatPlan plan, String currIssueNumber) 
			throws RepeatPlanException {
		long planId = plan.getId();
		Long sponsorId = plan.getSponsorId();
		BetScheme betScheme = null;
		Date now = new Date();
		
		//根据追号计划和期号查询有效且未执行的期计划
		RepeatPlanIssues issuePlanOnSaling = issuePlanService.findValidUnExecuteIssuePlan(planId, currIssueNumber);
		logger.info("当前时间={}，通过当前在售期号={}，计划ID={}，查询的期计划信息={}", 
				new Object[]{DateUtils.format(now), currIssueNumber, plan.getId(), issuePlanOnSaling});
		if(null != issuePlanOnSaling) {
			int stopType = plan.getStopType();
			int bonusStop = plan.getBonusForStop();
			//是否停止追号
			boolean isStopRepeat = false;
			//判断追号计划是否为达标停止类型
			if(stopType == RepeatPlanStopType.PRIZED_STOP.getType()) {//中奖即停
				isStopRepeat = isContainBonusIssuePlan(planId);
				if(isStopRepeat) {//修改追号计划状态停止继续追号
					plan.setStatus(RepeatPlanStatus.COMPLETE.getType());
					plan.setFinishTime(new Date());
					plan.setStoppedReason(RepeatPlanStopReasonType.PRIZED_STOP.getType());
					updatePlan(plan);
				}
			} else if(stopType == RepeatPlanStopType.BONUS_FOR_STOP.getType()) {
				BigDecimal bonusAmount = queryBonusAmountByPlan(planId);
				if(bonusAmount.intValue() >= bonusStop) {//盈利达标停止追号
					isStopRepeat = true;
					plan.setStatus(RepeatPlanStatus.COMPLETE.getType());
					plan.setFinishTime(new Date());
					plan.setStoppedReason(RepeatPlanStopReasonType.BONUS_FOR_STOP.getType());
					updatePlan(plan);
				}
			}
			if(!isStopRepeat) {
				betScheme = executePlansOnIssue(issuePlanOnSaling, sponsorId);
				logger.info("追号计划执行成功，期号={}，期计划={}，返回方案详情={}", 
						new Object[]{currIssueNumber, plan, betScheme});
			} else {
				logger.info("针对期号={}，计划ID={}，追号发起人ID={}，停止追号；停止追号原因={}", 
						new Object[]{currIssueNumber, planId, sponsorId, 
						RepeatPlanStopReasonType.getName(plan.getStoppedReason())});
			}
		}
		return betScheme;
	}
	
	@Override
	@Transactional
	public boolean repeatCode(RepeatPlan repeatPlan,
			List<RepeatPlanIssues> repeatIssuePlanList,
			List<RepeatBetContent> repeatBetContentList) {
		long planId = createRepeatPlans(repeatPlan);
		int issuePlanSize = repeatIssuePlanList.size();
		int betContentSize = repeatBetContentList.size();
		for(int i = 0; i < issuePlanSize; i++) {
			repeatIssuePlanList.get(i).setPlanId(planId);
		}
		for(int j = 0; j < betContentSize; j++) {
			repeatBetContentList.get(j).setPlanId(planId);
		}
		issuePlanService.createRepeatIssuePlan(repeatIssuePlanList);
		betContentService.saveRepeatBetContent(repeatBetContentList);
		return true;
	}

	@Override
	@Transactional
	public long createRepeatPlans(RepeatPlan plan) {
		RepeatPlanPO repeatPlanPO = new RepeatPlanPO();
		BeanUtils.copyProperties(plan, repeatPlanPO);
		long id = repeatDao.savePlan(repeatPlanPO);
		return id;
	}

	@Override
	@Transactional
	public List<RepeatPlan> queryExecutedRepeatPlan(String lotteryId) {
		return queryRepeatPlanList(RepeatPlanStatus.EXECUTE, lotteryId);
	}

	@Override
	@Transactional
	public List<RepeatPlan> queryRepeatPlanList(RepeatPlanStatus planStatus, String lotteryId) {
		List<RepeatPlanPO> list = repeatDao.queryRepeatPlanList(planStatus, lotteryId);
		List<RepeatPlan> repeatPlanList = new ArrayList<RepeatPlan>();
		if(null != list) {
			for(RepeatPlanPO planPO : list) {
				RepeatPlan plan = new RepeatPlan();
				BeanUtils.copyProperties(planPO, plan);
				repeatPlanList.add(plan);
			}
		}
		return repeatPlanList;
	}

	@Override
	@Transactional
	public void isChangePlanStatus(long planId) {
		boolean isContain = issuePlanService.isContainValidAndUncomplete(planId);
		if(!isContain) {
			RepeatPlan plan = new RepeatPlan();
			plan.setId(planId);
			plan.setStatus(RepeatPlanStatus.COMPLETE.getType());
			plan.setFinishTime(new Date());
			plan.setStoppedReason(RepeatPlanStopReasonType.NO_STOP.getType());
			updatePlan(plan);
		}
	}

	@Override
	@Transactional
	public RepeatResp updateIssuePlanStatus(String lotteryId, long planId, String issueNumber) {
		IssueInfo issueInfo = issueService.findByIssue(lotteryId, issueNumber);
		RepeatResp repeatResp = new RepeatResp();
		if(null != issueInfo && null != issueInfo.getId()) {
			Date stopTimeForUser = issueInfo.getStopTimeForUser();
			Date startTime = issueInfo.getStartTime();
			int lcStatus = issueInfo.getLCStatus();
			Date now = new Date();
			
			if(now.after(stopTimeForUser)) {
				repeatResp.setDesc("计划ID=" + planId + ",期信息=" + issueNumber + "，当前时间=" + now + 
						"，在用户截止投注时间之后,搜索到的期信息=" + issueInfo);
				//将当前期计划置为无效
				RepeatPlanIssues issuePlan = new RepeatPlanIssues();
				issuePlan.setValid(false);
				issuePlanService.updateIssuePlan(planId, issueNumber, issuePlan);
			} else if(now.before(startTime)) {
				repeatResp.setDesc("计划ID=" + planId + ",期信息=" + issueNumber + ",未开售；搜索到的期信息=" + issueInfo);
			} else if(Constants.ISSUE_STATUS_SALE == lcStatus){
				repeatResp.setDesc("计划ID=" + planId + ",期信息=" + issueNumber + ",在售中；搜索到的期信息=" + issueInfo);
				repeatResp.setIssueInfo(issueInfo);
			}
		} else {
			repeatResp.setDesc("计划ID=" + planId + "通过期号:" + issueNumber + ",未找到期信息!");
		}
		return repeatResp;
	}

	@Override
	@Transactional
	public RepeatPlan findById(long planId) {
		RepeatPlanPO planPO = repeatDao.findRepeatPlanById(planId);
		RepeatPlan plan = null;
		if(null != planPO) {
			plan = new RepeatPlan();
			BeanUtils.copyProperties(planPO, plan);
		}
		return plan;
	}

	@Override
	@Transactional
	public boolean isContainBonusIssuePlan(long planId) {
		int winNote = repeatDao.winNoteIssuePlan(planId);
		return winNote > 0 ? true : false;
	}

	@Override
	@Transactional
	public BigDecimal queryBonusAmountByPlan(long planId) {
		return repeatDao.queryBonusAmountByPlan(planId);
	}

	@Override
	@Transactional
	public List<RepeatRecord> findRepeatPlan(String lottery, Long userId,
			Date from, Date to, int status, Paging paging) {
		List<RepeatPlanPO> planList = repeatDao.findRepeatPlan(lottery, userId,
				from, to, status, paging);
		
		List<RepeatRecord> repeatResultList = new ArrayList<RepeatRecord>();
		if(null != planList && planList.size() > 0) {
			for(RepeatPlanPO PO : planList) {
				RepeatRecord repeatRecord = new RepeatRecord();
				BeanUtils.copyProperties(PO, repeatRecord);
				
				long start = System.currentTimeMillis();
				
				List<String> betCodeList = betContentDao.queryBetListOfPlanId(PO.getId());
				if(null != betCodeList && betCodeList.size() > 0) {
					repeatRecord.setBetContentList(betCodeList);
				}
				RepeatPlanSchedule planSchedule = issuePlanDao.loadPlanScheduleByPlanId(PO.getId());
				repeatRecord.setRepeatTotal(planSchedule.getTotalIssue());
				repeatRecord.setCompleteRepeatTotal(planSchedule.getCompleteIssue());
				
				long end = System.currentTimeMillis();
				logger.debug("用户：{},查询 追号计划内容和完成投注期数，耗时：{}", new Object[]{userId, (end - start)});
				
				repeatResultList.add(repeatRecord);
			}
		}
		
		return repeatResultList;
	}

	@Transactional
	@Override
	public boolean stopRepeatPlan(long planId, long userId) {
		boolean stopResult = false;
		RepeatPlanPO planPO = repeatDao.findRepeatPlanById(planId);
		if(null != planPO && planPO.getStatus() == RepeatPlanStatus.EXECUTE.getType()) {
			if(planPO.getSponsorId() == userId) {
				planPO.setStatus(RepeatPlanStatus.STOPED.getType());
				planPO.setStoppedReason(RepeatPlanStopReasonType.USER_STOP.getType());
				planPO.setFinishTime(new Date());
				stopResult = true;
			}
		}
		return stopResult;
	}
}