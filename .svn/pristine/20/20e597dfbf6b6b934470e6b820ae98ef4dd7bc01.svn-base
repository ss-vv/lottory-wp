package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;
import java.util.Map;

import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;

/**
 * @author 彭保星
 *
 * @since 2014年11月10日下午5:21:43
 */
public interface LeagueScoreDao {

	/**
	 * 新增联赛积分
	 * @param scoreRuleMap
	 * @param seasonModel 
	 */
	void storeLeagueScoreData(Map<String, Object> scoreRuleMap, SeasonModel seasonModel);

	void storeCupScoreData(List<FbLeagueScoreModel> fbLeagueScoreModels,SeasonModel seasonModel);

}
