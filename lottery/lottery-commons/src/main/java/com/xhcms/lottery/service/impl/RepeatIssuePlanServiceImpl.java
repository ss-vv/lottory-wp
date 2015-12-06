package com.xhcms.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanIssues;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanSchedule;
import com.xhcms.lottery.commons.persist.dao.RepeatIssuePlanDao;
import com.xhcms.lottery.commons.persist.entity.RepeatIssuePlanPO;
import com.xhcms.lottery.lang.RepeatIssuePlanExecuteStatus;
import com.xhcms.lottery.lang.RepeatIssuePlanIsValid;
import com.xhcms.lottery.service.RepeatIssuePlanService;

public class RepeatIssuePlanServiceImpl implements RepeatIssuePlanService {

	@Autowired
	private RepeatIssuePlanDao issuePlanDao;
	
	@Override
	@Transactional
	public void updateIssuePlan(long planId, RepeatIssuePlanIsValid issuePlanIsValid) {
		issuePlanDao.updateIssuePlan(planId, issuePlanIsValid);
	}

	@Override
	@Transactional
	public void updateIssuePlan(long planId, String issueNumber,
			RepeatPlanIssues issuePlan) {
		RepeatIssuePlanPO issuePlanPO = issuePlanDao.findValidUnExecuteIssuePlan(planId, issueNumber);
		if(null != issuePlan && null != issuePlanPO) {
			if(null != issuePlan.getExecutedTime()) {
				issuePlanPO.setExecutedTime(issuePlan.getExecutedTime());
			}
			if(null != issuePlan.getSchemeId()) {
				issuePlanPO.setSchemeId(issuePlan.getSchemeId());
			}
			if(null != issuePlan.getResult()) {
				issuePlanPO.setResult(issuePlan.getResult());
			}
			if(null != issuePlan.getExecuted()) {
				issuePlanPO.setExecuted(issuePlan.getExecuted());
			}
			if(null != issuePlan.getValid()) {
				issuePlanPO.setValid(issuePlan.getValid());
			}
		}
	}

	@Override
	public void createRepeatIssuePlan(List<RepeatPlanIssues> repeatIssuePlanList) {
		List<RepeatIssuePlanPO> list = new ArrayList<RepeatIssuePlanPO>();
		for(RepeatPlanIssues issuePlan : repeatIssuePlanList) {
			RepeatIssuePlanPO issuePlanPO = new RepeatIssuePlanPO();
			BeanUtils.copyProperties(issuePlan, issuePlanPO);
			list.add(issuePlanPO);
		}
		issuePlanDao.saveIssuePlanBatch(list);
	}
	
	@Override
	@Transactional
	public List<RepeatPlanIssues> queryIssuePlanList(long planId) {
		List<RepeatIssuePlanPO> list = issuePlanDao.queryIssuePlanList(planId);
		return convert(list);
	}
	
	public List<RepeatPlanIssues> convert(List<RepeatIssuePlanPO> list) {
		List<RepeatPlanIssues> repeatIssuesList = new ArrayList<RepeatPlanIssues>();
		if(null != list) {
			for(RepeatIssuePlanPO issuePlanPO : list) {
				RepeatPlanIssues issuePlan = new RepeatPlanIssues();
				BeanUtils.copyProperties(issuePlanPO, issuePlan);
				repeatIssuesList.add(issuePlan);
			}
		}
		return repeatIssuesList;
	}
	
	@Override
	@Transactional
	public List<RepeatPlanIssues> queryIssuePlanList(long planId,
			RepeatIssuePlanIsValid issuePlanIsValid,
			RepeatIssuePlanExecuteStatus executeStatus) {
		List<RepeatIssuePlanPO> list = issuePlanDao.queryIssuePlanList(planId, 
				issuePlanIsValid, executeStatus);
		return convert(list);
	}
	
	@Override
	@Transactional
	public List<RepeatPlanIssues> queryIssuePlanListValidAndExecuted(long planId) {
		return queryIssuePlanList(planId, RepeatIssuePlanIsValid.VALID, 
				RepeatIssuePlanExecuteStatus.NOT_EXECUTED);
	}
	
	@Override
	@Transactional
	public boolean isContainValidAndUncomplete(long planId) {
		List<RepeatIssuePlanPO> issuePlanList = issuePlanDao.queryIssuePlanList(planId, 
				RepeatIssuePlanIsValid.VALID, 
				RepeatIssuePlanExecuteStatus.NOT_EXECUTED);
		if(null != issuePlanList && issuePlanList.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public List<RepeatPlanIssues> queryIssuePlanByIssueNumber(String issueNumber) {
		List<RepeatIssuePlanPO> issuePlanList = issuePlanDao.queryIssuePlanByIssueNumber(issueNumber);
		List<RepeatPlanIssues> issueList = null;
		if(null != issuePlanList) {
			issueList = new ArrayList<RepeatPlanIssues>();
			for(RepeatIssuePlanPO issuePlanPO : issuePlanList) {
				RepeatPlanIssues issuePlan = new RepeatPlanIssues();
				BeanUtils.copyProperties(issuePlanPO, issuePlan);
				issueList.add(issuePlan);
			}
		}
		return issueList;
	}

	@Override
	@Transactional
	public RepeatPlanIssues findValidUnExecuteIssuePlan(long planId, String issueNumber) {
		RepeatIssuePlanPO issuePlanPO = issuePlanDao.findValidUnExecuteIssuePlan(planId, issueNumber);
		RepeatPlanIssues issuePlan = null;
		if(null != issuePlanPO) {
			issuePlan = new RepeatPlanIssues();
			BeanUtils.copyProperties(issuePlanPO, issuePlan);
		}
		return issuePlan;
	}

	@Override
	@Transactional
	public RepeatPlanSchedule loadPlanScheduleByPlanId(long planId) {
		return issuePlanDao.loadPlanScheduleByPlanId(planId);
	}
}
