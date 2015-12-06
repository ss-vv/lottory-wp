package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchEventModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.TeamModel;

/**
 * 存储球探网 赛事事件数据
 * @author 崔桂祥
 */
public interface MatchEventInfoDataStoreDao {

	/**
	 * 查询所有正在比赛的数据
	 * @return
	 */
	List<FbMatchBaseInfoPO> queryAllMatchDataHasFinish(int startPos);

	/**
	 * 存储分析中事件数据
	 * @param matchBaseModels
	 * @throws Exception 
	 */
	void storeMatchEventData(List<QtMatchEventModel> teamModels) throws Exception;
	
	
	void storeMatchStatisticData(List<QtMatchStatisticModel> teamModels) throws Exception;

}
