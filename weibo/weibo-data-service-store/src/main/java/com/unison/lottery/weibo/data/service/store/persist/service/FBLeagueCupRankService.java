package com.unison.lottery.weibo.data.service.store.persist.service;

import com.unison.lottery.weibo.data.service.store.data.LeagueScoreRank;


/**
 * 联赛杯赛排名服务
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-20
 * @version 1.0
 */
public interface FBLeagueCupRankService {
	
	/**
	 * 由赛事ID查询足球的联赛排名
	 * @param lcMatchId
	 * @return
	 */
	LeagueScoreRank findFBLeagueRankBy(String lcMatchId);
}