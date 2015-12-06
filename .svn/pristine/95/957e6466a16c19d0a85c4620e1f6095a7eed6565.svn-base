package com.xhcms.lottery.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.repeat.RepeatBetContent;
import com.xhcms.lottery.commons.data.repeat.RepeatPlan;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanIssues;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.lang.RepeatIssuePlanExecuteStatus;
import com.xhcms.lottery.lang.RepeatIssuePlanResult;
import com.xhcms.lottery.service.RepeatIssuePlanService;
import com.xhcms.lottery.service.RepeatSchemeService;
import com.xhcms.lottery.service.RepeatService;

/**
 * @desc 数字彩追号投注方案管理
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class RepeatDigitalSchemeServiceImpl implements RepeatSchemeService {
	
	@Autowired
	private DigitalBetService digitalBetService;
	
	@Autowired
	private RepeatIssuePlanService issuePlanService;
	
	@Autowired
	private RepeatService repeatService;
	
	/**
	 * 根据期信息和投注内容组装投注请求对象
	 * @param issueInfo
	 * @param betContentList
	 * @return
	 */
	private DigitalBetRequest makeBetRequest(RepeatPlanIssues issueInfo, 
			List<RepeatBetContent> betContentList) {
		DigitalBetRequest orderBetRequest = new DigitalBetRequest();
		orderBetRequest.setMultiple(issueInfo.getMultiple());
		orderBetRequest.setLotteryId(LotteryId.SSQ.name());
		orderBetRequest.setPlayType(PlayType.valueOfLcPlayId(betContentList.get(0).getPlayId()));
		LinkedList<String> contents = new LinkedList<String>();
		List<PlayType> playTypes = new LinkedList<PlayType>();
		for (RepeatBetContent betContent : betContentList){
			contents.add(betContent.getCode());
			playTypes.add(digitalBetService.deduceSSQPlayType(betContent.getCode()));
		}
		orderBetRequest.setBetContents(contents);
		orderBetRequest.setPlayTypeList(playTypes);
		orderBetRequest.setIssue(issueInfo.getIssueNumber());
		orderBetRequest.setChooseType(ChooseType.valueOfIndex(betContentList.get(0).getChooseType()));
		return orderBetRequest;
	}
	
	@Override
	@Transactional
	public BetScheme makeBetRequestAndBet(RepeatPlanIssues selectIssue, List<RepeatBetContent> betContentList, Long sponsorId) {
		RepeatPlan plan = repeatService.findById(selectIssue.getPlanId());
		
		//生成方案，扣费
		DigitalBetRequest digitBetRequest = makeBetRequest(selectIssue, betContentList);
		BetScheme schemeView = digitalBetService.prepareBet(digitBetRequest);
		BetScheme betScheme = null;
		if(null != schemeView && null != sponsorId) {
			schemeView.setSponsorId(sponsorId);
			if(null != plan) {
				schemeView.setFollowSchemePrivacy(plan.getPrivacy());
			}
			betScheme = digitalBetService.betConfirm(schemeView);
			//更新期计划和追号计划
			Long schemeId = betScheme.getId();
			if(null != schemeId) {
				selectIssue.setExecutedTime(new Date());
				selectIssue.setExecuted(RepeatIssuePlanExecuteStatus.EXECUTED.isType());
				selectIssue.setSchemeId(schemeId);
				selectIssue.setResult(RepeatIssuePlanResult.SUCCESS.getType());
				issuePlanService.updateIssuePlan(selectIssue.getPlanId(), selectIssue.getIssueNumber(), selectIssue);
			}
		}
		return betScheme;
	}
}
