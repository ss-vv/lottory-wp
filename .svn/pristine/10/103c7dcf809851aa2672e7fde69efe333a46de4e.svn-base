package com.xhcms.lottery.commons.persist.service;

import java.util.List;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetMatchRecVo;
import com.xhcms.lottery.commons.persist.entity.BetMatchRecPO;

/**
 * @desc 推荐方案关联的比赛投注信息
 * @author lei.li@unison.net.cn
 * @createTime 2014-4-2
 * @version 1.0
 */
public interface BetMatchRecService {

	List<BetMatchRecPO> findBySchemeId(long schemeId);
	
	BetMatchRecPO getById(long id);
	
	Paging getBetMatchRec(Paging paging,String matchType);
	
	void addRecommendMatch(List<Integer> id);
}