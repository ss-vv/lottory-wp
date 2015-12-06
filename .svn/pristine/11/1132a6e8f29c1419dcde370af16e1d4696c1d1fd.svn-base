package com.xhcms.lottery.commons.persist.service;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.ssq.SSQResult;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 高频彩、福彩期信息服务。
 * 
 * @author Yang Bo
 */
public interface IssueService {
	/**
	 * 通过期号查询“期信息”。
	 * @param lotteryId 彩票id
	 * @param issue 期信息
	 * @return 期信息对象。
	 */
	IssueInfo findByIssue(String lotteryId, String issue);

	/**
	 * 批量更新或插入期信息
	 * @param issueinfos
	 */
	void saveOrUpdate(List<IssueInfo> issueinfos);

	/**
	 * 更新高频彩的LC_status
	 */
	void updateHFLCStatus();
	
	/**
	 * 更新福彩的LC_status
	 */
	void updateWFLCStatus();
	
	/**
	 * 根据指定时间找到高频彩当前销售的期，由于有多个彩种，所以会返回列表
	 * 
	 * @return
	 */
	public List<IssueInfoPO> getAllHFCurrentSalingIssue(Date date);
	
	/**
	 * 根据指定时间找到福彩当前销售的期，由于有多个彩种，所以会返回列表
	 * @return
	 */
	public List<IssueInfoPO> getAllWFCurrentSalingIssue(Date date);
	
	
	/**
	 * 找到lotteryId对应的彩种当前销售期
	 * 对于每个彩种，
	 * 返回符合以下条件的期信息：
	 * 1、当前时间处于startTime和closeTime之间，并且有效的，每个彩种一个
	 * 2、如果有的彩种无法根据条件1找出当前销售的期，则这个彩种的startTime离当前时间最近，并且有效的那一期作为
	 * 当前销售的期
	 * @param lotteryId
	 * @param date
	 * @return
	 */
	public IssueInfoPO getCurrentSalingIssueByLotteryId(String lotteryId,Date date);
	
	/**
	 * 获取福彩当前期信息.
	 * @param lotteryId
	 * @param date
	 * @return
	 * @throws BetException
	 */
	public IssueInfo getCurrentSalingIssueOfWF(String lotteryId, Date date) throws BetException;
	
	/**
	 * 获取当前在售期信息.
	 * @param lotteryId
	 * @param date
	 * @return
	 * @throws BetException
	 */
	public IssueInfo getCurrentOnSalingIssue(String lotteryId, Date date) throws BetException;
	
	/**
	 * 返回为页面显示所准备的指定彩种的当前销售期
	 * <p>对于每个彩种，返回符合以下条件的期信息：</p>
	 * <p>如果指定时间当天有期信息；</p>
	 * <p>	如果指定时间没有超过该彩种当天最晚售票时间：</p>
	 * <p>		返回当天有效，且lc_status为1的；如找不到，则返回异常</p>
	 * <p>	否则:</p>
	 * <p>		根据这个彩种当天第一期的信息推断出第二天第</p>
	 * <p>	一期的期号和相应的时间信息，如startTime、</p>
	 * <p> 		stopTime、stopTimeForUser、ZMCloseTime和</p>
	 * <p>		closeTime；如果找不到，则抛出异常</p>
	 * <p>否则：</p>
	 * <p>	  抛出异常</p>
	 * @param lotteryId
	 * @param date
	 * @return
	 */
	public IssueInfo getCurrentSalingIssueForShow (String lotteryId,Date date) throws BetException;
	
	/**
	 * 找到playId对应的彩种当前销售期列表
	 * 对于每个彩种，
	 * 返回符合以下条件的期信息：
	 * 当前时间处于startTime和closeTime之间，并且有效的，以stopTime时间从小到大排序，每个彩种多个当前销售的期
	 * @param playId
	 * @return
	 */
	public List<IssueInfo> findOnSalingTimeIssueListByPlayId(String playId) ;
	
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
	public List<IssueInfo> findIssuesBetweenStopTimeForUser(String playId,
			Date beginStopTime,Date endStopTime);
	
	/**
	 * 找到playId对应的彩种当前销售期列表
	 * 对于每个彩种，
	 * 返回状态是在售的，并且有效的，每个彩种多个当前销售的期
	 * @param playId
	 * @return
	 */
	public List<IssueInfo> findOnSalingStatusIssueListByPlayId(String playId) ;
	
	/**
	 * 根据彩种ID，开始时间以及期次状态查询期信息
	 * @param paging 分页对象
	 * @param lotteryId
	 * @param from
	 * @param to
	 * @param status
	 * @return
	 */
	public void getCurrentSalingIssue(Paging paging, String lotteryId, Date from,
			Date to, int status);
	
	/**
	 * 根据彩种ID，时间段以及状态查询期信息
	 * @param paging
	 * @param lotteryId
	 * @param from
	 * @param to
	 * @param status
	 */
	public void getIssue(Paging paging, String lotteryId, Date from,
			Date to, int status);
	
	/**
	 * 根据彩种代码、期次状态及日期查询开奖期信息
	 * @param lotteryId	彩种代码
	 * @param status	期次状态
	 * @param date		日期
	 * @param maxResults要取得的记录数
	 * @return	开奖的期信息集合
	 */
	public List<IssueInfo> findBBallotRecords(String lotteryId, String[] status, String date, int maxResults);
	
	/**
	 * 福彩的开奖查询
	 * @param lotteryId 彩种ID, 参考 Lottery 枚举类型
	 * @param status 允许的状态, 通常是{3,4}(未开售=0, 销售中=1，已截止=2,已开奖=3，已派奖=4)
	 * @param date 停售时间
	 * @param maxResults 最大结果数
	 * @return 已经开奖的
	 */
	public List<IssueInfo> findBBallotRecordsOfWF(String lotteryId, String[] status, String date, int maxResults);
	
	public IssueInfo findBBallotDetail(String lotteryId, String issueNumber);
	
	/**
	 * 根据彩种代码、期次状态及日期查询开奖期信息
	 * @param paging
	 * @param lotteryId	彩种代码
	 * @param status	期次状态
	 * @param date		日期
	 * @param maxResults要取得的记录数
	 * @return	开奖的期信息集合
	 */
	public List<IssueInfo> findBBallotRecords(Paging paging, String lotteryId, Integer[] status, String date);
	
	/**
	 * 根据彩种代码、期次状态查询开奖期信息列表
	 * @param paging
	 * @param lotteryId 彩种代码
	 * @param status 期次状态
	 * @return 开奖的期信息集合
	 */
	public List<IssueInfo> findBBallotRecords(Paging paging, String lotteryId, Integer[] status);
	
	/**
	 * 根据彩种代码、期次状态及日期查询开奖期信息
	 * @param paging
	 * @param lotteryId	彩种代码
	 * @param status	期次状态
	 * @param date		日期
	 * @param playId	玩法ID
	 * @param maxResults要取得的记录数
	 * @return	开奖的期信息集合
	 */
	public List<IssueInfo> findBBallotRecordsOfPlayId(Paging paging, String lotteryId, 
			Integer[] status, String playId);
	
	/**
	 * 更新指定期次id的状态
	 * @param id	期次id
	 * @param status	要设定的状态
	 */
	void updateJXIssue(Long id, int status);
	/**
	 * 更改合法性
	 * @param id
	 * @param status
	 * @param valid
	 */
	void updateJXIssue_(Long id, int status,boolean valid);

	/**
	 * 找到唯一期信息，使用playid和期号
	 * @param playId
	 * @param issue
	 * @return
	 */
	IssueInfo findByIssueAndPlayId(String playId, String issue) ;

	/**
	 * 根据id取得期信息
	 * @param id
	 */
	public IssueInfo findById(Long id);
	
	/**
	 * 查询已过期的状态不等于已派奖的期信息
	 * @param lotteryId
	 * @return
	 */
	List<IssueInfoPO> findOldIssuesByLottery(String lotteryId);
	
	/**
	 * 查询已过期的状态不等于已派奖的期信息
	 * @param playId
	 * @return
	 */
	List<IssueInfoPO> findOldIssuesWithStatusNotEQAWARD(String playId);
	
	/**
	 * 查询玩法对应的最大期号的期信息
	 * @param playId
	 * @return
	 */
	IssueInfo findMAXIssueInfoByPlayId(String playId);
	
	/**
	 * 根据彩种ID，查询最近一次开奖期信息
	 * @param lotteryId
	 * @param status
	 * @return
	 */
	IssueInfo findLatestBallotIssue(String lotteryId, Integer[] status);
	
	/**
	 * 根据彩种ID，玩法ID，找到期开奖信息<br>
	 * @param lotteryId
	 * @param status
	 * @param playIdList
	 * @return
	 */
	IssueInfo findLatestBallotIssue(String lotteryId, Integer[] status, String playId);
	
	/**
	 * 获取从当前时间开始的期信息
	 * @param lotteryId
	 * @param from
	 * @param to
	 * @param status
	 * @return
	 */
	public List<IssueInfo> getSalingIssueFromCurrent(String lotteryId, Date now, Integer[] status, int maxResults);
	
	/**
	 * 通过期号查询“期信息”。
	 * @param lotteryId 彩票id
	 * @param issue 期信息
	 * @param 期状态
	 * @return 期信息对象。
	 */
	public IssueInfo findByIssue(String lotteryId, String issue, Integer[] status);
	
	/**
	 * 通过玩法ID查询期信息
	 * @param playId
	 * @param size	需要的条数
	 * @return
	 */
	public List<IssueInfo> findByPlayId(String playId, int size);
	
	/**
	 * 用IssueInfo对象构造双色球的开奖结果对象。<br/>
	 * 主要是计算“大小比值”、和值等双色球特有的属性。
	 * 
	 * @param issueInfoList 期信息对象列表
	 * @return SSQResult list
	 */
	public List<SSQResult> computeSSQResult(List<IssueInfo> issueInfoList);
	
	/**
	 * 查询当前期
	 * @param lotteryId
	 * @param targetDate
	 * @return
	 */
	IssueInfo findCurrentIssue(String lotteryId, Date targetDate);
	
	/**
	 * 根据彩种ID和时间查询当前在售且有效的期信息
	 * @param lotteryId
	 * @param targetDate
	 * @return
	 */
	IssueInfo findCurrentOnSaling(String lotteryId, Date targetDate);
	
	/**
	 * 查询给定彩种和玩法的在售期号
	 * @param lotteryId
	 * @param playId
	 * @return
	 */
	String findOnsale(String lotteryId, String playId);

	/**
	 * 计算给用户看的倒计时
	 * @param issueInfo
	 * @return
	 */
	long computeCountDownTimeForUser(IssueInfo issueInfo);

	/**
	 * 计算自定义的期投注截止时间
	 * @param issueInfo
	 * @return
	 */
	Date computeNewStopTimeForUser(IssueInfo issueInfo);
	
	/**
	 * 分頁查询期信息
	 * @param lotteryId
	 * @param playType
	 * @param issueNum
	 * @param paging
	 */
	Paging findByPage(LotteryId lotteryId, PlayType playType, String issueNum,
			Paging paging);
	
	List<Long> findCTZTIssueInfoList(LotteryId lotteryId,String issueNumber,int maxResult);

	boolean updateCTZCPresetBonus(String issueNumber, String playId,
			float bonus1, float bonus2);
	/**
	 * 查看是否是新的ctzc期
	 * @param issueNumber
	 * @return
	 */
	boolean isExistsCTZCIssueInfoUnset(String lotteryId,String issueNumber,boolean valid);

	IssueInfo getIssueByIssueNumberAndPlayId(String issueNumber, String pid);
}
