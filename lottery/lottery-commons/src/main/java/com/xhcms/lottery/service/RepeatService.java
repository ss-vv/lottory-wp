package com.xhcms.lottery.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.repeat.RepeatBetContent;
import com.xhcms.lottery.commons.data.repeat.RepeatPlan;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanIssues;
import com.xhcms.lottery.commons.data.repeat.RepeatRecord;
import com.xhcms.lottery.commons.exception.RepeatPlanException;
import com.xhcms.lottery.lang.RepeatPlanStatus;
import com.xhcms.lottery.lang.RepeatResp;

/**
 * 追号服务。
 * 
 * @author Yang Bo, lei.li@unison.net.cn
 */
public interface RepeatService {

	/**
	 * 创建一组追号计划。
	 * @param originalBetScheme 源投注方案，包含有对应的投注内容。
	 * @param plans 指定了状态的计划
	 */
	void createRepeatPlans(BetScheme originalBetScheme, List<RepeatPlan> plans);
	
	/**
	 * 创建追号计划
	 * @param plan
	 * @return 追号计划ID
	 */
	long createRepeatPlans(RepeatPlan plan);
	
	/**
	 * 生成追号计划，期计划，追号内容一系列计划数据
	 * @param repeatPlan
	 * @param repeatIssuePlanList
	 * @param repeatBetContentList
	 * @return
	 */
	boolean repeatCode(RepeatPlan repeatPlan, List<RepeatPlanIssues> repeatIssuePlanList, 
			List<RepeatBetContent> repeatBetContentList);
	
	/**
	 * 更新计划内容:完成时间、计划状态、停止原因
	 * @param plan 需要更新的追号计划
	 */
	void updatePlan(RepeatPlan plan);
	
	/**
	 * 判断是否需要更新追号计划状态：
	 * 1.如果计划下面所有期计划中不包含“未执行且有效的期计划”则更新计划状态为“执行完成”且更新完成时间
	 * @param planId
	 */
	void isChangePlanStatus(long planId);
	
	/**
	 * 对指定彩种和当前在售期信息对应期计划执行追号计划：满足条件
	 * 1.期计划有效且未执行
	 * 2.期计划包含期为当前在售期
	 * 
	 * @param plan	期计划
	 * @param currIssueNumber	当前在售期
	 * @return
	 * @throws RepeatPlanException
	 */
	BetScheme executeIssuePlan(RepeatPlan plan, String currIssueNumber) throws RepeatPlanException;
	
	/**
	 * 根据彩种查询出所有可执行的追号计划
	 * @param lotteryId
	 * @return
	 */
	List<RepeatPlan> queryExecutedRepeatPlan(String lotteryId);
	
	/**
	 * 查询指定状态的追号计划集合
	 * @param planStatus	追号计划状态的枚举值
	 * @return
	 */
	List<RepeatPlan> queryRepeatPlanList(RepeatPlanStatus planStatus, String lotteryId);
	
	/**
	 * 根据计划ID和期号,更新当前期计划对应“期信息”的状态：未开售、已开售，截止
	 * @param lotteryId	彩种ID
	 * @param planId	计划ID
	 * @param issueNumber 期计划中的期号
	 */
	RepeatResp updateIssuePlanStatus(String lotteryId, long planId, String issueNumber);
	
	RepeatPlan findById(long planId);
	
	/**
	 * 当前期计划是否包含已经中奖的期计划：
	 * 
	 * @param planId
	 * @return
	 */
	boolean isContainBonusIssuePlan(long planId);
	
	/**
	 * 查询指定追号计划的总中奖金额
	 * @param planId
	 * @return
	 */
	BigDecimal queryBonusAmountByPlan(long planId);
	
	/**
	 * 查询追号计划
	 * @param lottery
	 * @param userId
	 * @param from
	 * @param to
	 * @param status
	 * @param paging
	 * @return
	 */
	List<RepeatRecord> findRepeatPlan(String lottery, Long userId, Date from, 
			Date to, int status, Paging paging);
	
	/**
	 * 根据计划ID中止追号计划
	 * @param planId
	 */
	boolean stopRepeatPlan(long planId, long userId);
}
