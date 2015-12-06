package com.unison.lottery.weibo.data.service.store.persist.service;

import java.util.List;
import com.unison.lottery.weibo.data.service.store.data.ScoreTypeEnum;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeagueScorePO;


/**
 * 提供足球联赛积分服务
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-20
 * @version 1.0
 */
public interface FBLeagueScoreService {
	
	/**
	 * 根据联赛ID获取联赛积分
	 * @param leagueId
	 * @param scoreType 积分榜类型
	 * @return
	 */
	List<FBLeagueScorePO> getLeagueScoreByLeagueId(long leagueId, ScoreTypeEnum scoreType);
	
	/**
	 * 根据大V彩matchId获取联赛积分
	 * @param lcMatchId
	 * @param scoreType 积分榜类型
	 * @return
	 */
	List<FBLeagueScorePO> getLeagueScoreByLcMatchId(String lcMatchId, ScoreTypeEnum scoreType);
}