package com.xhcms.lottery.commons.data.repeat;

import java.util.List;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.utils.internal.IssueNumberStrategy;

/**
 * @desc 追号请求内容的解析器
 * @createTime 2013-8-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public abstract class RepeatRequestParse {

	public static final String ISSUE_SEP = ",";
	
	/**
	 * 解析用户的追号期计划
	 * @return
	 */
	public abstract List<RepeatPlanIssues> parseToRepeatIssuePlan(RepeatRequest repeatRequest);
	
	/**
	 * 解析用户投注内容
	 * @param scheme
	 * @return
	 */
	public abstract List<RepeatBetContent> parseToOriginalBetContent(BetScheme scheme);
	
	/**
	 * 解析追号计划
	 * @param scheme
	 * @param repeatRequest
	 */
	public abstract RepeatPlan parseToRepeatPlan(BetScheme scheme, RepeatRequest repeatRequest);
	
	public abstract void setIssueService(IssueService issueService);
	
	public abstract void setIssueNumberStrategy(IssueNumberStrategy issueNumberStrategy);
}
