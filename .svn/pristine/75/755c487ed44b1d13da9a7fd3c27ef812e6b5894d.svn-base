package com.xhcms.lottery.service;

import java.util.List;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.repeat.RepeatBetContent;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanIssues;

/**
 * @desc 追号方案管理服务
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public interface RepeatSchemeService {

	/**
	 * 构建投注请求并投注方案，入库、扣费
	 * @param selectIssue
	 * @param betContentList
	 * @param sponsorId
	 * @return
	 */
	public BetScheme makeBetRequestAndBet(RepeatPlanIssues selectIssue, 
			List<RepeatBetContent> betContentList, 
			Long sponsorId);
}
