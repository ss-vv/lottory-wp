package com.unison.lottery.weibo.web.service;

import java.util.List;
import java.util.Map;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;

/**
 * 竞彩足球分析、欧赔、亚赔页面
 * @author baoxing.peng@davcai.com
 * @since 2015-01-23 16:50:50 
 */
public interface JCZQAnalyseService {

	/**
	 * 获取竞彩足球赛事分析
	 * @param daVCaiMatchId
	 * @return
	 */
	Map<String, Object> findFbLeagueRankData(
			String daVCaiMatchId);

	String findAgainstHistory_latest_20(String homeTeamId,
			String guestTeamId);

	/**
	 * 球队近20场战绩情况
	 * @param homeTeamId
	 * @return
	 */
	String findTeamRecentRecord_latest_20(String homeTeamId);

	/**
	 * 联赛积分榜
	 * @param leagueId
	 * @param rankType 排行榜类型{total_rank:总积分榜,zc_rank:主场积分榜,kc_rank:客场积分榜,half_total_rank:半场总积分榜,half_zc_rank:主场半场积分榜,half_kc_rank:客场半场积分榜}
	 * @return
	 */
	String findLeagueScoreRankByLeagueId(String leagueId, String rankType);

	/**
	 * 足球赛事欧赔
	 * @param daVCaiMatchId
	 * @return
	 */
	String findFbMatchEuroOddsById(String daVCaiMatchId);

	List<Map<String, Object>> findFbMatchAsianOddsById(
			String daVCaiMatchId);

	List<FbMatchAsiaOuOddsInfoPO> findFbAsianOddsOneCompany(Long matchId,
			Integer corpId);

	Map<String, Map<String, Map>> findFbOddsPushInit(String corpIds, String time);

	/**
	 * 主客队排名
	 * @param time
	 * @return
	 */
	Map<String, Map> findJczqMatchTeamPosition(String time);
	
}
