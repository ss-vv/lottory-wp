package com.xhcms.lottery.commons.persist.dao;

import java.util.List;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.HfBetContentPO;

/**
 * 高频彩投注DAO。
 */
public interface HfBetContentDao extends Dao<HfBetContentPO> {
	
	/**
	 * 根据方案ID获得用户高频彩投注内容
	 * @param schemeId 方案ID
	 * @return 投注内容列表
	 */
	List<HfBetContentPO> findHfBetContent(Long schemeId);
	
}
