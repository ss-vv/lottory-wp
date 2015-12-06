package com.unison.lottery.weibo.data.service.store.persist.dao;

import com.unison.lottery.weibo.data.service.store.persist.entity.BBLeaguePO;
import com.xhcms.commons.persist.Dao;

/**
 * @desc 篮球联赛信息数据库操作
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-10
 * @version 1.0
 */
public interface BBLeagueDao extends Dao<BBLeaguePO> {

	void save(BBLeaguePO data);

	public BBLeaguePO findByLeagueId(Long leagueId);
	
	/**
	 * 通过联赛名称查询当前赛季信息:篮球接口赛事数据中没有联赛ID，故通过名称匹配
	 * @param leagueName
	 * @return
	 */
	BBLeaguePO findByShortName(String leagueName);
}
