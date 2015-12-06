package com.davcai.data.statistic.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.davcai.data.statistic.model.BetScheme;
import com.davcai.data.statistic.model.Top5Recommend;
import com.davcai.data.statistic.model.Top5RecommendMiddle;
import com.davcai.data.statistic.task.model.BBMatch;
import com.davcai.data.statistic.task.model.BJDCMatch;
import com.davcai.data.statistic.task.model.FBMatch;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;


public interface Top5RecommendStatisticMapper {

	/**
	 * 准备足球数据
	 * @return
	 */
	List<Top5RecommendMiddle> loadFootballData();

	/**
	 * 准备篮球数据
	 * @return
	 */
	List<Top5RecommendMiddle> loadBasketballData();

	/**
	 * 准备北单数据
	 * @return
	 */
	List<Top5RecommendMiddle> loadBeidanData();

	/**
	 * 7天胜率前5
	 * @param from
	 * @param to
	 * @return
	 */
	List<Top5Recommend> find7dayWinRatioTop5List(@Param("fromDate")Date from, @Param("toDate")Date to);

	/**
	 * 50场胜率前5
	 * @return
	 */
	List<Top5Recommend> find50WinRatioTop5List();

	List<Top5Recommend> find7dayRevenueRatioTop5List(@Param("fromDate")Date from,@Param("toDate") Date to);

	List<Top5Recommend> find50RevenueRatioTop5List();

	/**
	 * 删除中间表所有数据
	 */
	void deleteTop5RecommendMiddle();

	void saveTop5RecommendMiddle(Top5RecommendMiddle top5RecommendMiddle);

	/**
	 * 删除最终结果表中7天胜率数据
	 */
	void deleteTop5Recommend4SevenDayWinRatio();

	/**
	 * 生成最终结果表记录
	 * @param top5Recommend
	 */
	void saveTop5Recommend(Top5Recommend top5Recommend);

	/**
	 * 删除最终结果表中50场胜率数据
	 */
	void deleteTop5Recommend4FiftyWinRatio();

	/**
	 * 删除最终结果表中7天盈利率数据
	 */
	void deleteTop5Recommend4SevenDayRevenueRatio();

	/**
	 * 删除最终结果表中50场盈利率数据
	 */
	void deleteTop5Recommend4FiftyRevenueRatio();

	/**
	 * 删除经排序的中间表
	 */
	void deleteOrderedMiddle();

	/**
	 * 生成经排序的中间表
	 */
	void makeOrderedMiddle();

	/**
	 * 7天跟单奖金排行
	 * @param from
	 * @param to
	 * @return
	 */
	List<Top5Recommend> find7dayFollowBonusTop5List(@Param("fromDate")Date from, @Param("toDate")Date to);

	/**
	 * 50场跟单奖金排行
	 * @param to 
	 * @return
	 */
	List<Top5Recommend> find50FollowBonusTop5List(@Param("toDate")Date to);

	void deleteTop5Follow4SevenDayBonus();

	void deleteTop5Follow4FiftyBonus();

	List<Top5Recommend> find7dayFollowWinRatioTop5List(@Param("fromDate")Date from,@Param("toDate") Date to);

	List<Top5Recommend> find50FollowWinRatioTop5List(@Param("toDate")Date to);

	void deleteTop5Follow4SevenDayWinRatio();

	void deleteTop5Follow4FiftyWinRatio();

	List<Top5Recommend> find7dayShowSchemeHelpTop5List(@Param("fromDate")Date from, @Param("toDate")Date to);

	List<Top5Recommend> find50ShowSchemeHelpTop5List(@Param("toDate")Date to);

	List<Top5Recommend> find7dayShowSchemeWinRatioTop5List(@Param("fromDate")Date from, @Param("toDate")Date to);

	List<Top5Recommend> find50ShowSchemeWinRatioTop5List(@Param("toDate")Date to);

	void deleteTop5ByTopTypeAndDimension(@Param("topType")String topType, @Param("dimension")String dimension);

	List<BetScheme> findYesterdayScheme(@Param("beginTime")Date yesterdaybeginTime,
			@Param("endTime")Date yesterdayEndTime);

	List<PlayMatch> findFootballMatchInfoBySchemeId(@Param("betSchemeId")Long betSchemeId, @Param("playId")String playId);

	List<PlayMatch> findBasketballMatchInfoBySchemeId(@Param("betSchemeId")Long betSchemeId, @Param("playId")String playId);

	List<PlayMatch> findBJDCMatchInfoBySchemeId(@Param("betSchemeId")Long betSchemeId, @Param("playId")String playId);

	FBMatch findFBMatch(@Param("matchId")Long matchId);

	BBMatch findBBMatch(@Param("matchId")Long matchId);

	List<HotAndRecommendMatch> findTeamPosition(@Param("matchId")Long matchId);

	BJDCMatch findBJDCMatch(@Param("matchId")Long matchId);

	void saveHotAndRecommendMatch(HotAndRecommendMatch match);

	void deleteHotAndRecommendMatch();
	

}
