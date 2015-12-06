package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchEventModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.TeamModel;

/**
 * 存储球探网 篮球比赛球队统计
 * @author 崔桂祥
 */
public interface BasketMatchTeamStatisticStoreDao {

	/**
	 * 查询所有正在比赛的数据
	 * @return
	 */
	List<BasketBallMatchPO> queryAllMatchDataHasFinish(int startPos);

	
	/**
	 * 存储篮球比赛球队统计
	 * @param teamModels
	 * @throws Exception
	 */
	void storeMatchTeamStatisticData(List<QtBasketMatchTeamStatisticModel> teamModels) throws Exception;

}
