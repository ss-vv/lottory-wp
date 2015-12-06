package com.xhcms.lottery.commons.data.repeat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.utils.internal.IssueNumberStrategy;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.RepeatIssuePlanResult;
import com.xhcms.lottery.lang.RepeatPlanStopType;
import com.xhcms.lottery.lang.RepeatType;
import com.xhcms.lottery.lang.SuiteType;

public class SSQRepeatRequestParse extends RepeatRequestParse {
	
	private RepeatPlan repeatPlan;
	
	private List<RepeatPlanIssues> repeatPlanIssues;
	
	private List<RepeatBetContent> repeatBetContents;
	
	private IssueService issueService;
	
	private IssueNumberStrategy issueNumberStrategy;
	
	@Override
	public List<RepeatPlanIssues> parseToRepeatIssuePlan(RepeatRequest repeatRequest) {
		String betNoteList = repeatRequest.getBetNoteList();
		String issueNumber = repeatRequest.getIssueNumber();
		int repeatType = repeatRequest.getRepeatType();
		int suite = repeatRequest.getSuite();
		IssueInfo issueInfo = issueService.findByIssue(LotteryId.SSQ.name(), issueNumber);
		
		if(StringUtils.isNotBlank(betNoteList)) {
			String[] betNoteArray = new String[]{};
			int size = 0;
			if(repeatType == RepeatType.REPEAT.getType()) {
				betNoteArray = betNoteList.split(ISSUE_SEP);
				size = betNoteArray.length;
			} else if(repeatType == RepeatType.REPEAT_SUITE.getType()) {
				SuiteType suiteType = SuiteType.get(suite);
				if(null != suiteType) {
					size = suiteType.getIssues();
					String[] multipleArr = new String[size];
					for(int i = 0; i < size; i++) {
						multipleArr[i] = betNoteList;
					}
					betNoteArray = multipleArr;
				}
			}
			if(size > 0) {
				List<String> issueNumberList = issueNumberStrategy.moreIssueNumbers(issueNumber, size, issueInfo.getStartTime());
				
				repeatPlanIssues = new ArrayList<RepeatPlanIssues>();
				for(int i = 0; i < size; i++) {
					String betNoteStr = betNoteArray[i];
					RepeatPlanIssues issuePlan = new RepeatPlanIssues();
					int betNote = Integer.parseInt(betNoteStr);
					issuePlan.setIssueNumber(issueNumberList.get(i));
					issuePlan.setMultiple(betNote);
					issuePlan.setValid(true);
					issuePlan.setExecuted(false);
					issuePlan.setResult(RepeatIssuePlanResult.SUCCESS.getType());
					issuePlan.setExecutedTime(null);
					issuePlan.setCreatedTime(new Date());
					
					repeatPlanIssues.add(issuePlan);
				}
			}
		}
		return repeatPlanIssues;
	}
	
	@Override
	public RepeatPlan parseToRepeatPlan(BetScheme scheme, RepeatRequest repeatRequest) {
		int repeatType = repeatRequest.getRepeatType();
		if(RepeatType.isRepeatPlan(repeatType)) {
        	if(repeatType == RepeatType.REPEAT.getType()) {//追号
        		if(StringUtils.isNotBlank(repeatRequest.getBetNoteList())) {
        			String stopType = repeatRequest.getStopType();
        			int profitStandardStop = repeatRequest.getBonusForStop();
        			if(StringUtils.isBlank(stopType)) {
        				//默认计划停止类型为"持续执行"
        				repeatRequest.setPlanStopType(RepeatPlanStopType.CONTINUE.getType());
        			} else {
        				if(profitStandardStop > 0) {
        					repeatRequest.setPlanStopType(RepeatPlanStopType.BONUS_FOR_STOP.getType());
        					repeatRequest.setBonusForStop(profitStandardStop);
        				} else {
        					repeatRequest.setPlanStopType(RepeatPlanStopType.PRIZED_STOP.getType());
        				}
        			}
        		}
        	} else if(repeatType == RepeatType.REPEAT_SUITE.getType()) {//追号套餐
        		if(StringUtils.isNotBlank(repeatRequest.getBetNoteList())) {
        			String stopTypeMeal = repeatRequest.getStopTypeMeal();
        			int profitStandardStopMeal = repeatRequest.getBonusForStopMeal();
        			if(StringUtils.isBlank(stopTypeMeal)) {
        				repeatRequest.setPlanStopType(RepeatPlanStopType.CONTINUE.getType());
        			} else {
        				if(profitStandardStopMeal > 0) {
        					repeatRequest.setPlanStopType(RepeatPlanStopType.BONUS_FOR_STOP.getType());
        					repeatRequest.setBonusForStopMeal(profitStandardStopMeal);
        				} else {
        					repeatRequest.setPlanStopType(RepeatPlanStopType.PRIZED_STOP.getType());
        				}
        			}
        		}
        	}
        	repeatPlan = bulidRepeatPlan(repeatRequest);
        }
		return repeatPlan;
	}
	
	private RepeatPlan bulidRepeatPlan(RepeatRequest repeatRequest) {
		RepeatPlan repeatPlan = new RepeatPlan();
		repeatPlan.setLotteryId(repeatRequest.getLotteryId());
		repeatPlan.setSponsorId(repeatRequest.getSponsorId());
		repeatPlan.setStopped(false);
		repeatPlan.setDone(false);
		repeatPlan.setFinishTime(null);
		repeatPlan.setCreatedTime(new Date());
		repeatPlan.setStoppedReason(repeatRequest.getStopedReason());
		repeatPlan.setStopType(repeatRequest.getPlanStopType());
		repeatPlan.setStatus(repeatRequest.getStatus());
		
		if(repeatRequest.getRepeatType() == RepeatType.REPEAT.getType()) {
			repeatPlan.setBonusForStop(repeatRequest.getBonusForStop());
			repeatPlan.setPrivacy(repeatRequest.getPrivacy());
		} else {
			repeatPlan.setSuiteType(repeatRequest.getSuite());
			repeatPlan.setBonusForStop(repeatRequest.getBonusForStopMeal());
			repeatPlan.setPrivacy(repeatRequest.getMealPrivacy());
		}
		return repeatPlan;
	}

	@Override
	public List<RepeatBetContent> parseToOriginalBetContent(BetScheme scheme) {
		repeatBetContents = new ArrayList<RepeatBetContent>();
		List<String> betContentList = scheme.getDigitalBetRequest().getBetContents();
		if(null != betContentList) {
			Date now = new Date();
			for(String betCont : betContentList) {
				RepeatBetContent betContent = new RepeatBetContent();
				betContent.setLotteryId(scheme.getLotteryId());
				betContent.setPlayId(scheme.getPlayId());
				betContent.setChooseType(scheme.getDigitalBetRequest().getChooseType().getIndex());
				betContent.setCreatedTime(now);
				betContent.setCode(betCont);
				repeatBetContents.add(betContent);
			}
		}
		return repeatBetContents;
	}
	
	@Override
	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}

	public void setIssueNumberStrategy(IssueNumberStrategy issueNumberStrategy) {
		this.issueNumberStrategy = issueNumberStrategy;
	}
}