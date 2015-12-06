package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;
import java.util.Map;

import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;

/**
 * 
 * @author 彭保星
 *
 * @since 2014年11月19日下午5:46:48
 */
public interface LqMatchInfoDataStoreDao {

	/**
	 * 
	 * @param ballMatchModels
	 * @param isJingcai 是否竞彩比分
	 */
	void storeJishiMatchInfo(List<BasketBallMatchModel> ballMatchModels,boolean isJingcai);

	/**
	 * 
	 * @param basketBallLeagueSeasonModel 
	 * @param ballMatchModels
	 */
	void storeLqHistoryMatch(BasketBallLeagueSeasonModel basketBallLeagueSeasonModel, List<BasketBallMatchModel> ballMatchModels);

	/**
	 * 
	 * @param seasonId
	 * @param isCrawler
	 */
	void updateIsCrawler(Integer seasonId, int isCrawler);

	/**
	 * 保存篮球比赛和积分数据
	 * @param basketBallLeagueSeasonModel
	 * @param basketBallMatchAndLeagueScore
	 */
	void storeCupMatch(BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			Map<String, Object> basketBallMatchAndLeagueScore);

	/**
	 * 保存篮球联赛积分榜数据
	 * @param basketBallLeagueSeasonModel
	 * @param basketBallMatchAndLeagueScore
	 */
	void storeLeagueScore(
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			List<BasketBallLeagueScoreModel> basketBallMatchAndLeagueScore);

}
