package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;

/**
 * @author 彭保星
 *
 * @since 2014年11月7日下午3:09:13
 */
public interface MatchInfoDataStoreDao {

	void storeCupMatchInfo(List<QtMatchBaseModel> qtMatchBaseModels);

	void updateMatchData(List<QtMatchBaseModel> matchBaseInfos);

	String getNowSeasonByLeagueId(String leagueId);

	void updateJingCaiMatchData(List<QtMatchBaseModel> qtMatchBaseModels);

	void updateMatchJingcaiId(List<QtMatchBaseModel> qtMatchBaseModels);

	String queryLeagueIdByChineseName(String leagueId);

}
