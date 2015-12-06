package com.unison.lottery.weibo.web.service;

import java.util.Map;

import com.unison.lottery.weibo.data.service.store.persist.entity.BBMatchInfoPO;

/**
 * @author baoxing.peng@davcai.com 
 *
 * @since 2015年3月2日下午5:12:23
 */
public interface JCLQAnalyseService {

	String findAgainstHistory_latest_20(String homeTeamId, String guestTeamId);

	/**
	 * 
	 * @param daVCaiMatchId
	 * @return
	 */
	Map<String, Object> findMatchMessageById(String daVCaiMatchId);

	String findLeagueScoreRankByLeagueId(String leagueId, String rankType,
			String subLeagueId);

	Map<String, Map<String, Map>> findBbOddsPushInit(String corpIds, String time); 

}
