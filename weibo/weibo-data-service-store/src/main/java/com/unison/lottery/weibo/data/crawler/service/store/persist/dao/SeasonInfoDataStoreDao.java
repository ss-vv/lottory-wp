package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;

/**
 * @author 彭保星
 *
 * @since 2014年11月3日下午12:25:46
 */
public interface SeasonInfoDataStoreDao {

	void storeFbSubLeague(List<SeasonModel> subLeagueOfOneSeaon,Integer leagueType);
	/**
	 * 查询当前和上一赛季不是子联赛或分组赛的赛季数据
	 * @param source
	 * @param leagueType 1联赛，2杯赛
	 * @return
	 */
	List<FbLeagueSeasonBasePO> queryAllSeasonMessageSubLeague(Integer source,
			Integer leagueType);
	
	/**
	 * 查询所有未抓取过的杯赛小组赛赛程信息
	 * @param source 
	 * @param cupType 
	 * @return
	 */
	List<FbLeagueSeasonBasePO> queryAllCupGroupMess(Integer source, Integer cupType);
	/**
	 * 查询当前赛季的杯赛分组信息
	 * @param source
	 * @param cupType
	 * @return
	 */
	List<FbLeagueSeasonBasePO> queryAllCupGroupMessToCraw(Integer source,
			Integer cupType);
	String queryNowSeasonNameByLeagueId(String leagueId);
	
}
