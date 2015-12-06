package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueSeasonPO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BbLeagueInfoModel;

/**
 * @author 彭保星
 *
 * @since 2014年11月21日下午3:14:37
 */
public interface LqLeagueInfoDataStoreDao {

	/**
	 * 联赛赛季信息存储
	 * @param leagueInfoModels
	 */
	void storeBbLeague(List<BbLeagueInfoModel> leagueInfoModels);

	/**
	 * 查询所有不属于子联赛的赛季
	 * @param source 数据来源
	 * @param leagueType 1联赛，2杯赛
	 * @return
	 */
	List<BasketBallLeagueSeasonPO> queryAllLeagueSeasonNotSub(int source, int leagueType);

	/**
	 * 存储子联赛
	 * @param basketBallLeagueSeasonModels
	 */
	void storeSubleague(
			List<BasketBallLeagueSeasonModel> basketBallLeagueSeasonModels);

	/**
	 * 
	 * @param source
	 * @param isSubLeague 是否子联赛 0是，1否
	 * @param leagueType 联赛类型  1联赛 2杯赛
	 * @return
	 */
	List<BasketBallLeagueSeasonPO> queryAllSubLeagueNotCrawler(int source, int isSubLeague,
			int leagueType);

	/**
	 * 
	 * @param seasonModels2
	 * @param cupType
	 */
	void storeBasketSubLeague(List<BasketBallLeagueSeasonModel> seasonModels2,
			Integer cupType);

}
