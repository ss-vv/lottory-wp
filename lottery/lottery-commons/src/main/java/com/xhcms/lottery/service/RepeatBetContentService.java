package com.xhcms.lottery.service;

import java.util.List;
import com.xhcms.lottery.commons.data.repeat.RepeatBetContent;

/**
 * 追号计划对应的投注内容管理服务
 * 
 * @author lei.li@unison.net.cn
 */
public interface RepeatBetContentService {
	
	/**
	 * 查询指定追号计划对应期计划的投注内容列表
	 * @param planId
	 * @return
	 */
	List<RepeatBetContent> queryBetContentOfPlanId(long planId);
	
	
	List<String> queryBetListOfPlanId(long planId);
	
	/**
	 * 保存追号的投注内容
	 * @param betContentList
	 * @return
	 */
	void saveRepeatBetContent(List<RepeatBetContent> betContentList);
}
