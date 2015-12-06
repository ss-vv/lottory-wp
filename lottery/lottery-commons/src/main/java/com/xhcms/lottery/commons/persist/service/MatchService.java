package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BasketBallMatchData;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.FootBallMatchData;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.MatchPlay;
import com.xhcms.lottery.commons.persist.entity.MixMatchPlay;
import com.xhcms.lottery.commons.persist.entity.SingalMatchPlay;
import com.xhcms.lottery.lang.LotteryId; 

public interface MatchService {
	/**
	 * 获取指定玩法的在售足球赛事
	 * @param playId
	 * @return
	 */
	List<MatchPlay> listFBOnSale(String playId);
	
	/**
	 * 获取所有在售足球赛事
	 * @return
	 */
	List<MixMatchPlay> listFBInLast2Days();
	
	/**
	 * 获取指定玩法的在售篮球赛事
	 * @param playId
	 * @return
	 */
	List<MatchPlay> listBBOnSale(String playId);
	
	/**
	 * 查询足球赛果
	 * @param paging
	 * @param from
	 * @param to
	 */
	void listFBMatch(Paging paging, Date from, Date to);
	
	/**
	 * 查询篮球赛果
	 * @param paging
	 * @param from
	 * @param to
	 */
	void listBBMatch(Paging paging, Date from, Date to);
	
	
	/**
	 * 根据比赛状态查询足球比赛数量
	 * 
	 * @param status 比赛状态
	 * @return 比赛数量
	 */
	List<Object[]> queryFBMatchCountByStatus(int status); 
	
	/**
	 * 根据比赛状态查询传统足彩比赛数量
	 * 
	 * @param status 比赛状态
	 * @return 比赛数量
	 */
	List<Object[]> queryCTFBMatchCountByStatus(int status); 
	
	/**
	 * 根据比赛状态查询篮球比赛数量
	 * 
	 * @param status 比赛状态
	 * @return 比赛数量
	 */
	List<Object[]> queryBBMatchCountByStatus(int status); 
	/**
	 * 根据比赛销售状态查询足球联赛数
	 * @param status
	 * @return
	 */
	List<String> queryFBLeaguesByStatus(int status);
	
	/**
	 * 根据比赛销售状态查询传统足彩联赛数
	 * @param status
	 * @return
	 */
	List<String> queryCTFBLeaguesByStatus(int status);
	
	/**
	 * 根据比赛销售状态查询篮球联赛数
	 * @param status
	 * @return
	 */
	List<String> queryBBLeaguesByStatus(int status);
	

	/**
	 * 根据玩法id,赛事状态，联赛名称查询篮球相应的赛事和赛事玩法
	 * 
	 * @param playId
	 * @param status
	 * @param leagues
	 * @return
	 */
	List<Object[]> queryBBMatchByPlayIdAndStatusAndLeagueName(String playId,
			int status, List<String> leagues,Integer firstResult,Integer pageMaxResult);
	
	/**
	 * 根据玩法id,赛事状态，联赛名称查询足球相应的赛事和赛事玩法
	 * 
	 * @param playId
	 * @param status
	 * @param leagues
	 * @return
	 */
	List<Object[]> queryFBMatchByPlayIdAndStatusAndLeagueName(String playId,
			int status, List<String> leagues,Integer firstResult,Integer pageMaxResult);
	
	/**
	 * 查询开奖信息(JCZQ,JCLQ)
	 * @param paging
	 * @param from
	 * @param to
	 */
	Map<String, List<Object[]>> listLotteryInfo();
	
	/**
	 * 查询混合投注赛事信息 
	 * @param playId
	 * @return
	 */
	List<MixMatchPlay> listMixFBOnSale(String playId);
	
	/**
	 * 查询混合投注赛事信息 
	 * @param playId
	 * @return
	 */
	List<MixMatchPlay> listMixBBOnSale(String playId);
	
	/**
	 * 获取指定玩法的在售篮球赛事
	 * @param playId
	 * @return
	 */
	LinkedHashMap<String, MatchPlay> mapsBBOnSale(String playId,
			LinkedHashMap<Long, String> leagueMatchIdsCodes);

	/**
	 * 获取指定玩法的在售足球赛事
	 * @param playId
	 * @return
	 */
	LinkedHashMap<String, MatchPlay> mapsFBOnSale(String playId,
			LinkedHashMap<Long, String> leagueMatchIdsCodes);
	
	
	/**
	 * 查询混合投注赛事（足球）信息 -- 客户端api使用
	 * @param playId
	 * @param leagueList
	 * @param minMatchPlayingTime 
	 * @param maxResult
	 * @return
	 */
	List<MixMatchPlay> listMixFBOnSaleForPageAndLeagueName(String playId, 
			List<String> leagueList, Integer firstResult,Integer pageMaxResult, Date minMatchPlayingTime);
	
	/**
	 * 查询混合投注赛事（篮球）信息 -- 客户端api使用
	 * @param playId
	 * @param leagueList
	 * @param minMatchPlayingTime 
	 * @param maxResult
	 * @return
	 */
	List<MixMatchPlay> listMixBBOnSaleForPageAndLeagueName(String playId, 
			List<String> leagueList, Integer firstResult,Integer pageMaxResult, Date minMatchPlayingTime);
	/**
	 * 
	 * @param matchId
	 * @param playIds
	 * @return List<BBMatchPlayPO>
	 * @Throw
	 */
	List<BBMatchPlayPO> findBBMatchPlayPOByMatchIdAndPlayIds(Long matchId,
			Collection<String> playIds);

	List<CTFBMatch> findByIssueNumberAndPlayId(String issueNumber, String playId);
	
	/**
	 * 根据赛事ID和玩法查询预设分值
	 * @param matchId
	 * @param playId
	 * @return
	 */
	Float findByMatchIdAndPlayId(String matchId, String playId);

	/**
	 * 根据球探FB赛事比分数据，更新大V彩FB赛事预设分值
	 * @param lcMatchId
	 * @param scorePreset
	 * @param halfScorePreset
	 */
	void updateScoreToFBMatchPreset(long lcMatchId, String scorePreset, String halfScorePreset);
	
	/**
	 * 根据球探BB赛事比分数据，更新大V彩BB赛事预设分值
	 * @param lcMatchId
	 * @param finalScorePreset
	 */
	void updateScoreToBBMatchPreset(long lcMatchId, String finalScorePreset);

	/**
	 * 查询单关投注赛事（足球）信息 -- 客户端api使用
	 * @param playId
	 * @param leagueList
	 * @param minMatchPlayingTime 
	 * @param maxResult
	 * @return
	 */
	List<SingalMatchPlay> listSingalFBOnSaleForPageAndLeagueName(String playId,
			List<String> leagueList, int parseInt, int pagingMaxResult, Date minMatchPlayingTime);

	/**
	 * 查询单关投注赛事（篮球）信息 -- 客户端api使用
	 * @param playId
	 * @param leagueList
	 * @param minMatchPlayingTime 
	 * @param maxResult
	 * @return
	 */
	List<SingalMatchPlay> listSingalBBOnSaleForPageAndLeagueName(String playId,
			List<String> leagueList, int parseInt, int pagingMaxResult, Date minMatchPlayingTime);

	/**
	 * 判断当前时间是否在竞彩lotteryId规定彩种的可投注时间内
	 * @param lotteryId
	 * @return
	 */
	boolean canBetJC(LotteryId lotteryId);

	/**
	 * 获取在可投注期内并且可投注的足球场次
	 * @param minMatchPlayingTime 
	 * @param matchOnSale
	 * @return
	 */
	List<Object[]> queryJCFBMatchCountByStatusAndAfterDeadLine(int status, Date minMatchPlayingTime);

	List<String> queryJCFBLeaguesByStatusAndAfterDeadLine(int matchOnSale, Date minMatchPlayingTime);

	List<Object[]> queryJCBBMatchCountByStatusAndAfterDeadLine(int matchOnSale, Date minMatchPlayingTime);

	List<String> queryJCBBLeaguesByStatusAndAfterDeadLine(int matchOnSale, Date minMatchPlayingTime);

	List<Object[]> queryJCFBMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(
			String playId, int matchOnSale, List<String> leagueList,
			int parseInt, int pagingMaxResult, Date minMatchPlayingTime);

	public abstract List<Map<String, String>> getFBMatchResultMaps(List<Object[]> matchAndPlayObjs);



	List<Map<String, String>> getBBMatchResultMaps(
			List<Object[]> matchAndPlayObjs);

	List<Object[]> queryJCBBMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(
			String playId, int matchOnSale, List<String> leagueList,
			int parseInt, int pagingMaxResult, Date minMatchPlayingTime);

	public abstract String formatEntrustDeadline(Date entrustDeadline);
	
	/**
	 * 根据lotteryId的不同，计算新的截止时间：
	 * 对于竞彩，是原比赛截止时间减去（beforeCloseMinute-15）分钟作为该比赛的新截止时间;
	 * 对于传统足彩，是原比赛截止时间减去（beforeCloseMinute）分钟作为该比赛的新截止时间
	 * 对于北京单场，是原比赛截止时间减去（beforeCloseMinute-10）分钟作为该比赛的新截止时间
	 * @param entrustDeadline
	 * @param lotteryId
	 * @param beforeCloseMinute
	 * @return
	 */
	public Date computeNewEntrustDeadlineByLotteryId(Date entrustDeadline,String lotteryId,Integer beforeCloseMinute);

	/**
	 * 计算最小截止时间
	 * @param matchs
	 * @param lotteryId
	 * @return
	 */
	Date computeMinEntrustDeadline(List<BetMatch> matchs, String lotteryId);

	BetMatch computeMinEntrustDeadlineMatch(List<BetMatch> matchs, String lotteryId);
	
	/**
	 * 根据当前时间计算可投注的最早比赛开始时间
	 * @return
	 */
	Date computeMinMatchPlayingTime();
	
	/**
	 * 通过时间查询赛事
	 * @param playId
	 * @param leagueMatchIdsCodes
	 * @param from
	 * @return
	 */
	LinkedHashMap<String, MatchPlay> mapsFBByTime(String playId,
			LinkedHashMap<Long, String> leagueMatchIdsCodes, Date from,Date to);

	List<MixMatchPlay> listFBInLastDays(String time);

	List<MixMatchPlay> listBBInLastDays(String time);

	LinkedHashMap<String, MatchPlay> mapsBBByTime(String playId,
			LinkedHashMap<Long, String> leagueMatchIdsCodes, Date from, Date to);
	/**
	 * 获取比分直播的数据
	 * @param matchType , 1 足球 0 篮球
	 * @param subType , 0 未结束 1 已结束 
	 * @param leagueShortName
	 * @return
	 */
	List<BasketBallMatchData> getLQbifenData(String matchType, String subType, String leagueShortName);
	
	List<FootBallMatchData> getZQbifenData(String matchType, String subType, String leagueShortName);

	Date computeMachineOfftime(List<BetMatch> matchs, String lotteryId);

	Map<Long, BigDecimal> findDefaultScoreByMatchIdAndLotteryId(
			List<BetMatch> list, String lotteryId);

	List<BetMatch> makeMatchList(String[] matchArr, Pattern p);
	
	 /**
     * 查询当天的足球赛事（后台管理用---球探）
     * @return
     */
	List<FootBallMatchData> findZQSameDayFromQT(Date form, Date to);
	/**
	 * 查询的足球赛事详情（后台管理用---球探）
	 * @param detailId
	 * @return
	 */
	FootBallMatchData findZQByQT(String detailId);

	/**
	 * 更新数据
	 * @param foBaseInfoPO
	 * @return
	 */
	boolean updateZQByQT(FbMatchBaseInfoPO foBaseInfoPO);

	List<FootBallMatchData> findZQFromQT(Date from, Date to);

	List<BasketBallMatchData> findLQSameDayFromQT(Date from, Date to);

	BasketBallMatchData findLQByQT(String dtailId);

	boolean updateLQByQT(BasketBallMatchPO basketBallMatchPO);

	List<BasketBallMatchData> findLQFromQT(Date from, Date to);
}
