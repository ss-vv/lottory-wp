package com.davcai.lottery.weibo.analyse.service;

/**
 * 赛事分析业务层接口
 * @author baoxing.peng
 *
 */
public interface MatchAnalyseService {

	void analyseFbLeaguseScoreRank();

	void analyseBbLeagueScoreRank();

	/**
	 * 半场积分统计
	 */
	void analyseFbHalfLeagueScoreRank();

}
