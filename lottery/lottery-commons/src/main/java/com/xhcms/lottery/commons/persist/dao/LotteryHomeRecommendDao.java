package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.LtLotteryHomeRecommendPO;
import com.xhcms.lottery.lang.LotteryId;

public interface LotteryHomeRecommendDao extends Dao<LtLotteryHomeRecommendPO>{

	/**
	 * 
	 * @param l
	 * @param flag(判断是否取未比赛结束的推荐  true为 取 ，false 为不取)
	 * @return
	 */
	List<LtLotteryHomeRecommendPO> getLtLotteryHomeRecommendByLotteryId(LotteryId l,boolean flag);
	
	Integer isOn(Long id);
	
	List<Object[]> getLtLotteryHomeRecommendBy(Paging p,String matchType);
	
	Integer getLtLotteryHomeRecommendCount(String matchType);
	
	void updateStatus(Long id,int status);
}
