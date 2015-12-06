package com.xhcms.lottery.commons.persist.dao;

import java.util.List;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.RepeatBetContentPO;

/**
 * 追号计划的投注内容数据访问层管理
 * @author lei.li@unison.net.cn
 */
public interface RepeatBetContentDao extends Dao<RepeatBetContentPO> {
	
	void saveBetContentBatch(List<RepeatBetContentPO> list);

	/**
	 * 查询对应期计划的投注内容
	 * @param planId
	 * @return
	 */
	List<RepeatBetContentPO> queryBetContentOfPlanId(long planId);

	List<String> queryBetListOfPlanId(long planId);
}