package com.xhcms.lottery.commons.persist.dao;

import java.util.List;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.Top5RecommendPO;

public interface Top5RecommendDBDao extends Dao<Top5RecommendPO>{

	//通用版 胜率 盈利
	public List<Top5RecommendPO> generalFindTop5Recommend(String topType,String dimension);
}
