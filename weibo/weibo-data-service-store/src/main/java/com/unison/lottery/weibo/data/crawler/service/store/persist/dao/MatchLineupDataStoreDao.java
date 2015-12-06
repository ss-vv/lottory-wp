package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchLineupModel;

/**
 * 存储球探网 赛事阵容
 * 
 * @author 崔桂祥
 */
public interface MatchLineupDataStoreDao {

	/**
	 * 存储分析中事件数据
	 * 
	 * @param matchBaseModels
	 * @throws Exception
	 */
	void storeMatchLineupData(QtMatchLineupModel matchLineupModel)
			throws Exception;

	/**
	 * 查询正在比赛的首发阵容没有入库的比赛
	 * @param startPos
	 * @return
	 */
	List<FbMatchBaseInfoPO> queryAllMatchDataHasFinish(int startPos);

}
