package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 高频彩期信息DAO。
 * 
 * @author Yang Bo
 */
public interface IssueInfoDao extends Dao<IssueInfoPO>{

	IssueInfoPO findByLotteryIdAndIssueNumber(String lotteryId,
			String issueNumber);

	List<IssueInfoPO> findListByLotteryIdAndIssueNumber(
			List<IssueInfo> issueinfos);

	void batchSaveOrUpdate(List<IssueInfoPO> issueinfoPOsShouldUpdate,
			List<IssueInfoPO> issueinfoPOsShouldInsert);

	
	
	
	
	/**
	 * 批量更新lc_status为在销售状态
	 * @param salingIssueList
	 */
	void batchUpdateLCStatus2Saling(List<IssueInfoPO> salingIssueList);

	
	/**
	 * 找到当天有多少个有效彩种
	 * @return
	 */
	
	List<String> findDistinctLotteryId();


	
	/**
	 * 找到彩票id等于lotteryId的期信息的有效时间
	 * 
	 * @param lotteryId
	 * @param date
	 * @return
	 */
	IssueInfoPO findValidSalingTimeByLotteryId(String lotteryId,Date date);

	

	/**
	 * 找到符合以下条件
	 * 1、当前时间大于等于start_time，并且小于等于stop_time_for_user
	 * 2、彩种id为lotteryId
	 * 3、有效的
	 * 的期信息
	 * @param now
	 * @param lotteryId
	 * @return
	 */
	IssueInfoPO findValidIssueByLotteryIdBetweenStartTimeAndStopTimeForUser(
			Date now, String lotteryId);
	
	/** 以时间和玩法id 查询在售的期信息
	 * @author Wang Lei
	 * @param now
	 * @param playId
	 * @return
	 */
	List<IssueInfoPO> findValidOnSaleIssueListByPlayId(Date now, String playId);
	
	/**
	 * 查询已过期的状态不等于status的期信息
	 * @param playId
	 * @param status
	 * @return
	 */
	List<IssueInfoPO> findOldIssuesNotEqualStatus(String playId,int status);
	
	/**
	 * 查询已过期的状态不等于status的期信息
	 * @param lotteryId
	 * @param status
	 * @return
	 */
	List<IssueInfoPO> findOldIssuesByLottery(String lotteryId,int status);
	
	/** 查询当前时间之前的最近截止的期号
	 * @author Wang Lei
	 * @param now
	 * @param playId
	 * @return
	 */
	IssueInfoPO findBeforeIssueInfoByPlayId(Date now, String playId);
	
	/**
	 * 找到start_time大于等于当前时间，彩种id为lotteryId，且有效的期信息
	 * @param now
	 * @param lotteryId
	 * @return
	 */
	IssueInfoPO findValidIssueRecentlyByLotteryId(Date now, String lotteryId);

	

	/**
	 * 找到彩票id等于lotteryId的期信息的有效出票时间
	 * 有效出票时间是指在startTime和zm_close_time指尖
	 * @param lotteryId
	 * @param targetDate
	 * @return
	 */
	IssueInfoPO findValidBuyTicketTimeByLotteryId(String lotteryId,
			Date targetDate);

	/**
	 * 根据彩种ID，开始时间以及状态查询期信息
	 * @param lotteryId 彩种ID
	 * @param from
	 * @param to
	 * @param status 期次状态
	 * @return
	 */
	 List<IssueInfoPO> getCurrentSalingIssue(Paging paging, String lotteryId, Date from, Date to, int status);
	 
	 /**
	  * 根据彩种ID，时间段以及状态查询期信息
	  * @param paging
	  * @param lotteryId
	  * @param from
	  * @param to
	  * @param status
	  * @return
	  */
	 List<IssueInfoPO> findIssue(Paging paging, String lotteryId, Date from, Date to, int status);
	 
	 /**
	 * 根据彩种代码、期次状态及日期查询开奖期信息
	 * @param lotteryId	彩种代码
	 * @param status	期次状态
	 * @param date		日期
	 * @return	开奖的期信息集合
	 */
	 public List<IssueInfo> findBBallotRecords(String lotteryId, String[] status, String date, int maxResults);
	 
	 /**
	  * 福彩开奖号码查询
	  * @param lotteryId
	  * @param status
	  * @param date
	  * @param maxResults
	  * @return
	  */
	 public List<IssueInfoPO> findBBallotRecordsOfWF(String lotteryId, String[] status, String date, int maxResults);

	 /**
	  * 找到彩票id等于lotteryId的期信息的有效出票时间
	 * 有效出票时间是指在最早的startTime和最晚的stop_time_for_user之间
	  * @param lotteryId
	  * @param date
	  * @return
	  */
	IssueInfoPO findValidSalingTimeForShowByLotteryId(String lotteryId,
			Date date);

	IssueInfoPO findCurrentSalingValid(String lotteryId, Date targetDate);
	
	IssueInfoPO findCurrentOnSaling(String lotteryId, Date targetDate);
	
	/**
	 * 为页面显示当前正在销售期，需满足以下条件：
	 * <p>1、彩票id等于lotteryId</p>
	 * <p>2、is_valid等于1</p>
	 * <p>3、lc_status等于1</p>
	 * <p>4、startTime与targetDate是同一天</p>
	 * @param lotteryId
	 * @param date
	 * @return
	 */
	IssueInfoPO findCurrentSalingIssueForShowByLotteryId(String lotteryId,
			Date targetDate);

	/**
	 * 找到彩票id在指定时间当天的第一期
	 * <p>按startTime升序排列，且期号以01结尾,取第一个</p>
	 * @param lotteryId
	 * @param date
	 * @return
	 */
	IssueInfoPO findFirstByLotteryId(String lotteryId, Date date);

	/**
	 * 在指定时间当天，彩票id是否有期信息
	 * @param lotteryId
	 * @param date
	 * @return
	 */
	boolean haveIssueInfoInTargetDate(String lotteryId, Date date);

	/**
	 * 
	 * 按中民的status更新指定彩种的lc_status
	 *
	 * @param lotteryIds
	 */
	void updateLCStatusFromZMStatus(List<String> lotteryIds);

	/**
	 * 找到date日期中有多少不同的有效彩种id,这些彩种id必须在expectedLotteryIdList中
	 * @param date
	 * @param expectedLotteryIdList
	 * @return
	 */
	List<String> findValidDistinctLotteryIdInExpectedListByDate(Date date,
			List<String> expectedLotteryIdList);
	
	List<String> findValidLotteryIdOfWF(Date date,
			List<String> expectedLotteryIdList);
	
	IssueInfoPO findByPlayIdAndIssueNumber(String playId,
			String issueNumber);
	
	List<IssueInfoPO> findIssuesByPlayIdAndStatus(String playId,
			int status) ;
	
	String findOnSaleIssueBy(String lotteryId, String playId,
			int status) ;
	
	/**
	 * 找到stopTimeforUser处于一段时间内的期信息<br>
	 * 1，palyId不能为空<br>
	 * 2，beginStopTime为空则默认为当前时间<br>
	 * 3，endStopTime为空则默认不加此条件
	 * @param playId
	 * @param beginStopTime
	 * @param endStopTime
	 * @return
	 */
	List<IssueInfoPO> findIssuesBetweenStopTimeForUser(String playId,
			Date beginStopTime,Date endStopTime) ;
	/**
	 * 查询玩法对应的最大期号的期信息
	 * @param playId
	 */
	IssueInfoPO findMAXIssueInfoByPlayId(String playId);
	
	/**
	 * 根据彩种ID，查询最近一次开奖期信息
	 * @param lotteryId
	 * @param status
	 * @return
	 */
	IssueInfoPO findLatestBallotIssue(String lotteryId, Integer[] status);
	
	/**
	 * 根据彩种代码、期次状态及日期查询开奖期信息
	 * @param paging
	 * @param lotteryId	彩种代码
	 * @param status	期次状态
	 * @param date		日期
	 * @param maxResults要取得的记录数
	 * @return	开奖的期信息集合
	 */
	public List<IssueInfoPO> findBBallotRecords(Paging paging, String lotteryId, Integer[] status, String date);
	
	/**
	 * 根据彩种代码、期次状态查询开奖期信息列表
	 * @param paging
	 * @param lotteryId 彩种代码
	 * @param status 期次状态
	 * @return 开奖的期信息集合
	 */
	public List<IssueInfoPO> findBBallotRecords(Paging paging, String lotteryId, Integer[] status);
	
	/**
	 * 获取从当前时间开始的期信息
	 * @param lotteryId
	 * @param from
	 * @param to
	 * @param status
	 * @return
	 */
	public List<IssueInfoPO> getSalingIssueFromCurrent(String lotteryId, Date now, Integer[] status, int maxResults);
	
	/**
	 * 通过期号查询“期信息”。
	 * @param lotteryId 彩票id
	 * @param issue 期信息
	 * @param 期状态
	 * @return 期信息对象。
	 */
	IssueInfoPO findByIssue(String lotteryId, String issue, Integer[] status);

	IssueInfoPO findBBallotDetail(String lotteryId, String issueNumber);

	/**
	 * 根据彩种ID，玩法ID查询开奖信息
	 * @param lotteryId
	 * @param status
	 * @param playId
	 * @return
	 */
	IssueInfoPO findLatestBallotIssue(String lotteryId, Integer[] status,
			String playId);

	/**
	 * 根据彩种代码、期次状态、玩法ID及日期查询开奖期信息
	 * @param paging
	 * @param lotteryId	彩种代码
	 * @param status	期次状态
	 * @param date		日期
	 * @param maxResults要取得的记录数
	 * @param playId
	 * @return	开奖的期信息集合
	 */
	List<IssueInfoPO> findBBallotRecordsOfPlayId(Paging paging, String lotteryId,
			Integer[] status, String playId);

	List<IssueInfoPO> findbyplayId(String playId, int size);
	
	Integer isHaveSSQ();

	List<IssueInfoPO> findByPage(LotteryId lotteryId, PlayType playType,
			String issueNumber, Paging paging);
	
	List<Long> findIssueInfoListByLotteryId(LotteryId lotteryId,String issueNumber,int maxResult);

	
	List<IssueInfoPO> findByIssueNumber(String issueNumber);
	
	Integer findIssueCountByLotteryIdAndIssueNumber(String lotteryId,String issueNumber,boolean valid);
	/**
	 * 
	 * @param lotteryId
	 * @param issueNumber
	 * @return
	 */
	Integer isExistsCTZCIssueInfo(String lotteryId,String issueNumber);

	IssueInfoPO findIssueByIssueNumberAndPlayId(String issueNumber, String pid);

}
