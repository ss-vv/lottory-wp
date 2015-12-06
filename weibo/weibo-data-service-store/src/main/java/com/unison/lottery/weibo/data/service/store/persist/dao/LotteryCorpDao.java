package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.List;
import java.util.Map;
import com.unison.lottery.weibo.data.service.store.persist.entity.LotteryCorpPO;
import com.xhcms.commons.persist.Dao;

public interface LotteryCorpDao extends Dao<LotteryCorpPO>{

	/**
	 * 列出所有精选公司。
	 */
	List<LotteryCorpPO> listElites();
	
	/**
	 * 列出所有博彩公司
	 */
	List<LotteryCorpPO> listCorp();
	
	/**
	 * 以Map方式列出博彩公司，k/v:id/name
	 * @return
	 */
	List<Map<String, String>> map();
}
