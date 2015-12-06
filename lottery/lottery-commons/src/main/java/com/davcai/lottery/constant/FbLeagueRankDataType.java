package com.davcai.lottery.constant;

/**
 * 竞彩足球联赛排名数据类型
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年1月27日 下午12:44:05
 */
public enum FbLeagueRankDataType {
	homeTeamTotalRank, //主队联赛总排名
	homeTeamZCRank, //主队主场排名
	homeTeamKCRank, //主队客场排名
	homeTeamRankLatest_6, //主队近六场排名
	
	guestTeamTotalRank, //客队联赛总排名
	guestTeamZCRank, //客队主场排名
	guestTeamKCRank, //客队客场排名
	guestTeamRankLatest_6, //客队近六场排名
	
	homeTeamHalfTotalRank, //主队半场联赛总排名
	homeTeamHalfZCRank, //主队半场主场排名
	homeTeamHalfKCRank, //主队半场客场排名
	homeTeamHalfRankLatest_6, //主队半场近六场排名
	
	guestTeamHalfTotalRank, //客队半场联赛总排名
	guestTeamHalfZCRank, //客队半场主场排名
	guestTeamHalfKCRank, //客队半场客场排名
	guestTeamHalfRankLatest_6, //客队半场近六场排名
	
	matchMess, //对应的比赛信息
	
}
