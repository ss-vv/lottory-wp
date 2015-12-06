package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.LeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;

/**
 * 存储球探网数据
 * @author 彭保星
 *
 * @since 2014年10月28日下午6:42:46
 */
public interface LeagueInfoDataStoreDao {

	/**
	 * 更新或存储球探联赛和赛季数据
	 * @param leagueInfoModels 
	 * @throws Exception 
	 */
	void storeFbLeague(List<LeagueInfoModel> leagueInfoModels) throws Exception;

	/**
	 * 查询所有未抓取的历史赛季信息
	 * @param source 赛季来源：1球探
	 * @return
	 */
	List<FbLeagueSeasonBasePO> queryAllSeasonMessageNotCrawler(Integer source);

	/**
	 * 存储所有赛程历史记录
	 * @param matchBaseModels
	 * @param round 当前比赛属于第几轮
	 * @throws Exception 
	 */
	void storeMatchBaseMessage(List<QtMatchBaseModel> matchBaseModels, int round) throws Exception;

	/**
	 * 更新已抓取的赛程轮数
	 * @param seasonId
	 * @param round 
	 */
	void updateCrawlerCount(int seasonId, int round);

	/**
	 * 查询当前的所有赛季
	 * @param type 赛事类型：1联赛，2杯赛
	 * @param source 
	 * @return 
	 */
	List<FbLeagueSeasonBasePO> queryAllSeasonNotHaveRule(Integer type,Integer source);

	/**
	 * 查询没有抓取过的足球杯赛赛季
	 * @param type
	 * @param isSubLeagueId
	 * @param source
	 * @return
	 */
	List<FbLeagueSeasonBasePO> queryAllCupSeasonNotCrawler(int type,Integer isSubLeagueId, Integer source);

	/**
	 * 保存单场比赛单个公司的欧赔数据
	 * @param bsId
	 * @param companyId
	 * @param matchOpOddsInfoPO
	 */
	void saveFbMatchOpOneCompany(Long bsId, String companyId,
			FbMatchOpOddsInfoPO matchOpOddsInfoPO);

	/**
	 * 查询未开赛的竞彩赛事
	 * @return
	 */
	List<FbMatchBaseInfoPO> queryFbJingCaiMatchNotStart();


}
