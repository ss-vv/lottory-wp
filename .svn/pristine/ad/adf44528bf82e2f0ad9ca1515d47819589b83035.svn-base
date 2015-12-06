package com.xhcms.lottery.commons.persist.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.OpenSaleTime;
import com.xhcms.lottery.commons.data.UpdateAndInsertList;
import com.xhcms.lottery.commons.data.ssq.SSQResult;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
import com.xhcms.lottery.commons.persist.dao.LotteryOpenSaleDao;
import com.xhcms.lottery.commons.persist.dao.SsqIssueInfoDao;
import com.xhcms.lottery.commons.persist.dao.SystemConfDao;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.entity.LotteryOpenSalePO;
import com.xhcms.lottery.commons.persist.entity.SsqInfoPO;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.util.IssueinfoPOChangeUtil;
import com.xhcms.lottery.commons.util.OpenSaleTimeUtil;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.BeanUtilsTools;
import com.xhcms.lottery.utils.POUtils;

/**
 * 期信息服务。
 * 
 * @author 陈岩，Yang Bo, lilei
 */
@Service
@Transactional
public class IssueServiceImpl implements IssueService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IssueInfoDao issueInfoDao;
	
	@Autowired
	private SsqIssueInfoDao ssqIssueInfoDao;
	
	@Autowired
	private IssueinfoPOChangeUtil issueinfoPOChangeUtil;
	
	private List<String> hfLotteryIdList;
	
	private List<String> wfLotteryIdList;
	
	@Autowired
	private SystemConfDao systemConfDao;
	
	@Autowired
	private LotteryOpenSaleDao lotteryOpenSaleDao;

	@Override
	public IssueInfo findByIssue(String lottery, String issue) {
		IssueInfoPO issueInfoPO = issueInfoDao.findByLotteryIdAndIssueNumber(lottery, issue);
		IssueInfo issueInfo = new IssueInfo();
		if(null != issueInfoPO) {
			BeanUtils.copyProperties(issueInfoPO, issueInfo);
		}
		return issueInfo;
	}

	@Override
	public void saveOrUpdate(List<IssueInfo> issueinfos) {
		UpdateAndInsertList updateAndInsertList = 
				issueinfoPOChangeUtil.split2UpdateAndInsertList(issueinfos);
		if (null != updateAndInsertList) {
			issueInfoDao.batchSaveOrUpdate(
					updateAndInsertList.getIssueinfoPOsShouldUpdate(),
					updateAndInsertList.getIssueinfoPOsShouldInsert());
		}
	}

	@Override
	public void updateHFLCStatus() {
		issueInfoDao.updateLCStatusFromZMStatus(hfLotteryIdList);
		Date now=new Date();
		List<IssueInfoPO> salingIssueList=getAllHFCurrentSalingIssue(now);
		issueInfoDao.batchUpdateLCStatus2Saling(salingIssueList);
	}
	
	@Override
	public void updateWFLCStatus() {
		issueInfoDao.updateLCStatusFromZMStatus(wfLotteryIdList);
		Date now=new Date();
		List<IssueInfoPO> salingIssueList=getAllWFCurrentSalingIssue(now);
		issueInfoDao.batchUpdateLCStatus2Saling(salingIssueList);
	}
	
	@Override
	public List<IssueInfoPO> getAllHFCurrentSalingIssue(Date date) {
		List<IssueInfoPO> result=null;
		//先看目前有多少彩种
		List<String> distinctLotteryIds=issueInfoDao.findValidDistinctLotteryIdInExpectedListByDate(date,hfLotteryIdList);
		if(null!=distinctLotteryIds&&!distinctLotteryIds.isEmpty()){
			result=new ArrayList<IssueInfoPO>();
			for(String distinctLotteryId:distinctLotteryIds){
				IssueInfoPO currentSalingIssue=getCurrentSalingIssueByLotteryId(distinctLotteryId,date);
				if(null!=currentSalingIssue){
					result.add(currentSalingIssue);
				}
			}
		}
		return result;
	}

	@Override
	public List<IssueInfoPO> getAllWFCurrentSalingIssue(Date date) {
		List<IssueInfoPO> result=null;
		List<String> distinctLotteryIds=issueInfoDao.findValidLotteryIdOfWF(date,wfLotteryIdList);
		if(null!=distinctLotteryIds&&!distinctLotteryIds.isEmpty()){
			result=new ArrayList<IssueInfoPO>();
			for(String distinctLotteryId:distinctLotteryIds){
				IssueInfoPO currentSalingIssue=getCurrentSalingIssueByLotteryId(distinctLotteryId,date);
				if(null!=currentSalingIssue){
					result.add(currentSalingIssue);
				}
			}
		}
		return result;
	}
	
	@Override
	public IssueInfoPO getCurrentSalingIssueByLotteryId(String lotteryId, Date date) {
		IssueInfoPO result=null;
		if(StringUtils.isNotBlank(lotteryId)){
			IssueInfoPO candiateCurrentSalingIssue=issueInfoDao.findValidIssueByLotteryIdBetweenStartTimeAndStopTimeForUser(date,lotteryId);
			if(null!=candiateCurrentSalingIssue){
				result=candiateCurrentSalingIssue;
			}
			else{
				result=issueInfoDao.findValidIssueRecentlyByLotteryId(date,lotteryId);
			}						
		}
		return result;
	}

	@Override
	public void getCurrentSalingIssue(Paging paging, String lotteryId, Date from,
			Date to, int status) {
		List<IssueInfoPO> data = issueInfoDao.getCurrentSalingIssue(paging, lotteryId, from, to, status);
        List<IssueInfo> results = new ArrayList<IssueInfo>(data.size());
        for (IssueInfoPO po : data) {
        	IssueInfo m = new IssueInfo();
            BeanUtils.copyProperties(po, m);
            results.add(m);
        }
        paging.setResults(results);
	}
	
	@Override
	@Transactional
	public void getIssue(Paging paging, String lotteryId, Date from,
			Date to, int status) {
		List<IssueInfoPO> data = issueInfoDao.findIssue(paging, lotteryId, from, to, status);
        List<IssueInfo> results = new ArrayList<IssueInfo>(data.size());
        for (IssueInfoPO po : data) {
        	IssueInfo m = new IssueInfo();
            BeanUtils.copyProperties(po, m);
            results.add(m);
        }
        paging.setResults(results);
	}
	
	@Override
	@Transactional
	public void updateJXIssue(Long id, int status) {
		IssueInfoPO issueInfo = issueInfoDao.get(id);
		if (issueInfo != null) {
			issueInfo.setStatus(status);
		}
	}

	@Override
	@Transactional
	public List<IssueInfo> findBBallotRecords(String lotteryId,
			String[] status, String date, int maxResults) {
		return issueInfoDao.findBBallotRecords(lotteryId, status, date, maxResults);
	}
	
	@Override
	@Transactional
	public List<IssueInfo> findBBallotRecordsOfWF(String lotteryId,
			String[] status, String date, int maxResults) {
		List<IssueInfoPO> issues = issueInfoDao.findBBallotRecordsOfWF(lotteryId, status, date, maxResults);
		return POUtils.copyBeans(issues, IssueInfo.class);
	}

	@Override
	@Transactional(readOnly = true)
	public IssueInfo getCurrentSalingIssueOfWF(String lotteryId, Date date) throws BetException {
		IssueInfoPO currSalingIssue = issueInfoDao.findCurrentSalingValid(lotteryId, date);
		IssueInfo issue = new IssueInfo();
		if(null != currSalingIssue && StringUtils.isNotBlank(currSalingIssue.getLotteryId())) {
			BeanUtils.copyProperties(currSalingIssue, issue);
		}
		return issue;
	}
	
	@Override
	@Transactional(readOnly = true)
	public IssueInfo getCurrentOnSalingIssue(String lotteryId, Date date) throws BetException {
		IssueInfoPO currSalingIssue = issueInfoDao.findValidIssueByLotteryIdBetweenStartTimeAndStopTimeForUser(date, lotteryId);
		IssueInfo issue = new IssueInfo();
		if(null != currSalingIssue && StringUtils.isNotBlank(currSalingIssue.getLotteryId())) {
			BeanUtils.copyProperties(currSalingIssue, issue);
		}
		return issue;
	}
	
	@Override
	public IssueInfo getCurrentSalingIssueForShow(String lotteryId, Date date)
			throws BetException {
		IssueInfo result=null;
		if(haveIssueInfoInTargetDate(lotteryId,date)){
			 IssueInfoPO  validSalingTime=issueInfoDao.findValidSalingTimeForShowByLotteryId(lotteryId, date);
			 if(beforeLastStopTimeForUser(date,validSalingTime)){
				 IssueInfoPO currentSalingIssue=null;
				 try{
					 currentSalingIssue=issueInfoDao.findCurrentSalingIssueForShowByLotteryId(lotteryId,date); 
				 }
				 catch(Exception e){
					 logger.error("can not find current saling issue for show by lottery id: {}", lotteryId, e);
					 currentSalingIssue=null;
				 }
				 
				 if(null!=currentSalingIssue){
					result=new IssueInfo();
					BeanUtils.copyProperties(currentSalingIssue, result);
				 }
				 else{
					 throwException(date);
				 }
			 }
			 else{
				 IssueInfoPO firstIssue=null;
				 try{
					 firstIssue=issueInfoDao.findFirstByLotteryId(lotteryId, date);
				 }
				 catch(Exception e){
					 logger.error("can not find first by lottery id: {}", lotteryId, e);
					 firstIssue=null;
				 }
		
				 if(null!=firstIssue){
					 result=initNextDateFirstIssue(firstIssue,date);
				 }
				 else{
					 throwException(date);
				 }
			 }
		}
		else{
			throwException(date);
		}
		return result;
	}

	/**
	 * 根据今天的第一期信息推断明天第一期信息
	 * 如果是江西11选五：
	 * 明天第一期：
	 * 	<p>期号为YYMMdd01</p>
	 * 	<p>startTime时间与今天第一期一样，只是日期加一天</p>
	 * <p>stopTimeForUser时间与今天第一期一样，只是日期加一天</p>
	 * @param firstIssue
	 * @param targetDate 
	 * @return
	 */
	private IssueInfo initNextDateFirstIssue(IssueInfoPO firstIssue, Date targetDate) {
		IssueInfo result=null;
		if(null!=firstIssue){
			if(firstIssue.getLotteryId().equals(Constants.JX11)){
				Date now=targetDate;
				Date tomorrow=DateUtils.addDays(now, 1);
				SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
				String dateStr=sdf.format(tomorrow);
				String issueNumber=dateStr+"01";
				Date startTime=DateUtils.addDays(firstIssue.getStartTime(), 1);
				Date stopTimeForUser=DateUtils.addDays(firstIssue.getStopTimeForUser(), 1);
				result=new IssueInfo();
				result.setIssueNumber(issueNumber);
				result.setStartTime(startTime);
				result.setStopTimeForUser(stopTimeForUser);
			}
		}
		return result;
	}

	

	private void throwException(Date date) throws BetException {
		throw new BetException("can't find current saling issue for show in target date:"+date,AppCode.CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND);
	}

	/**
	 * 指定时间早于等于最晚的StopTimeForUser
	 * @param targetDate 
	 * @param validSalingTime
	 * @return
	 */
	private boolean beforeLastStopTimeForUser(Date targetDate, IssueInfoPO validSalingTime) {
		
		return null!=validSalingTime
				&&null!=targetDate
				&&null!=validSalingTime.getStopTimeForUser()
				&&
				(
						targetDate.before(validSalingTime.getStopTimeForUser())
						||
						targetDate.equals(validSalingTime.getStopTimeForUser())
				)
				;
	}
	
	
	/**
	 * 在指定时间当天，有期信息
	 * @param lotteryId
	 * @param date
	 * @return
	 */
	private boolean haveIssueInfoInTargetDate(String lotteryId, Date date) {
		return issueInfoDao.haveIssueInfoInTargetDate(lotteryId,date);
	}

	public List<String> getHfLotteryIdList() {
		return hfLotteryIdList;
	}

	public void setHfLotteryIdList(List<String> hfLotteryIdList) {
		this.hfLotteryIdList = hfLotteryIdList;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<IssueInfo> findOnSalingTimeIssueListByPlayId(String playId) {
		List<IssueInfo> results=null;
		if(StringUtils.isNotBlank(playId)){
			List<IssueInfoPO> poResults=issueInfoDao.findValidOnSaleIssueListByPlayId(new Date(),playId);
			results=POUtils.copyBeans(poResults, IssueInfo.class);
		}
		return results;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<IssueInfo> findIssuesBetweenStopTimeForUser(String playId,
			Date beginStopTime,Date endStopTime) {
		List<IssueInfo> issueInfos = null;
		List<IssueInfoPO> isuuePOs = issueInfoDao.findIssuesBetweenStopTimeForUser(playId, beginStopTime, endStopTime);
		if(isuuePOs != null){
			issueInfos = POUtils.copyBeans(isuuePOs, IssueInfo.class);
		}
		return issueInfos;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<IssueInfo> findOnSalingStatusIssueListByPlayId(String playId) {
		List<IssueInfo> results=null;
		if(StringUtils.isNotBlank(playId)){
			List<IssueInfoPO> poResults=issueInfoDao.findIssuesByPlayIdAndStatus(playId,Constants.ISSUE_STATUS_SALE);
			results=POUtils.copyBeans(poResults, IssueInfo.class);
		}
		return results;
	}
	
	@Override
	@Transactional(readOnly=true)
	public IssueInfo findByIssueAndPlayId(String playId, String issue) {
		IssueInfoPO issueInfoPO = issueInfoDao.findByPlayIdAndIssueNumber(playId, issue);
		IssueInfo issueInfo = new IssueInfo();
		if(null == issueInfoPO){
			return null;
		}
		BeanUtils.copyProperties(issueInfoPO, issueInfo);
		
		return issueInfo;
	}
	
	@Override
	@Transactional(readOnly=true)
	public IssueInfo findById(Long id) {
		IssueInfoPO issueInfoPO = issueInfoDao.get(id);
		IssueInfo issueInfo = new IssueInfo();
		BeanUtils.copyProperties(issueInfoPO, issueInfo);
		return issueInfo;
	}

	@Override
	@Transactional(readOnly=true)
	public List<IssueInfoPO> findOldIssuesWithStatusNotEQAWARD(String playId) {
		if(StringUtils.isBlank(playId)){
			return null;
		}
		return issueInfoDao.findOldIssuesNotEqualStatus(playId, Constants.ISSUE_STATUS_AWARD);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<IssueInfoPO> findOldIssuesByLottery(String lotteryId) {
		if(StringUtils.isBlank(lotteryId)){
			return null;
		}
		return issueInfoDao.findOldIssuesByLottery(lotteryId, Constants.ISSUE_STATUS_AWARD);
	}
	
	@Override
	@Transactional(readOnly=true)
	public IssueInfo findMAXIssueInfoByPlayId(String playId) {
		if(StringUtils.isBlank(playId)){
			return null;
		}
		IssueInfoPO issueInfoPO = issueInfoDao.findMAXIssueInfoByPlayId(playId);
		IssueInfo issueInfo = null;
		if(null != issueInfoPO){
			issueInfo = new IssueInfo();
			BeanUtils.copyProperties(issueInfoPO, issueInfo);
		}
		return issueInfo;
	}

	@Override
	@Transactional
	public IssueInfo findLatestBallotIssue(String lotteryId, Integer[] status) {
		IssueInfoPO po = issueInfoDao.findLatestBallotIssue(lotteryId, status);
		IssueInfo issue = new IssueInfo();
		if(null != po) {
			BeanUtils.copyProperties(po, issue);
		}
		return issue;
	}

	@Override
	@Transactional
	public List<IssueInfo> findBBallotRecords(Paging paging, String lotteryId,
			Integer[] status, String date) {
		List<IssueInfo> ballotIssueList = new ArrayList<IssueInfo>();
		List<IssueInfoPO> list = issueInfoDao.findBBallotRecords(paging, lotteryId, status, date);
		if(null != list) {
			for(IssueInfoPO po : list) {
				IssueInfo issue = new IssueInfo();
				BeanUtils.copyProperties(po, issue);
				ballotIssueList.add(issue);
			}
			paging.setResults(ballotIssueList);
		}
		return ballotIssueList;
	}
	
	@Override
	@Transactional
	public List<IssueInfo> findBBallotRecords(Paging paging, String lotteryId,
			Integer[] status) {
		List<IssueInfo> ballotIssueList = new ArrayList<IssueInfo>();
		List<IssueInfoPO> list = issueInfoDao.findBBallotRecords(paging, lotteryId, status);
		if(null != list) {
			for(IssueInfoPO po : list) {
				IssueInfo issue = new IssueInfo();
				BeanUtils.copyProperties(po, issue);
				ballotIssueList.add(issue);
			}
			paging.setResults(ballotIssueList);
		}
		return ballotIssueList;
	}

	@Override
	@Transactional
	public List<IssueInfo> getSalingIssueFromCurrent(String lotteryId,
			Date now, Integer[] status, int maxResults) {
		List<IssueInfoPO> issueInfoList = issueInfoDao.getSalingIssueFromCurrent(lotteryId, now, status, maxResults);
		List<IssueInfo> list = new ArrayList<IssueInfo>();
		if(null != issueInfoList) {
			for(IssueInfoPO po : issueInfoList) {
				IssueInfo issue = new IssueInfo();
				BeanUtils.copyProperties(po, issue);
				list.add(issue);
			}
		}
		return list;
	}

	@Override
	@Transactional
	public IssueInfo findByIssue(String lotteryId, String issue,
			Integer[] status) {
		IssueInfoPO po = issueInfoDao.findByIssue(lotteryId, issue, status);
		IssueInfo issueInfo = null;
		if(null != po) {
			issueInfo = new IssueInfo();
			BeanUtils.copyProperties(po, issueInfo);
		}
		return issueInfo;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<IssueInfo> findByPlayId(String playId, int size) {
		List<IssueInfoPO> issues = issueInfoDao.findbyplayId(playId, size);
		List<IssueInfo> result = new ArrayList<IssueInfo>();
		if(null != issues && issues.size() > 0) {
			for(IssueInfoPO po : issues) {
				IssueInfo issue = new IssueInfo();
				BeanUtils.copyProperties(po, issue);
				result.add(issue);
			}
		}
		return result;
	}

	public List<String> getWfLotteryIdList() {
		return wfLotteryIdList;
	}

	public void setWfLotteryIdList(List<String> wfLotteryIdList) {
		this.wfLotteryIdList = wfLotteryIdList;
	}

	@Override
	@Transactional(readOnly = true)
	public IssueInfo findBBallotDetail(String lotteryId,
			String issueNumber) {
		IssueInfoPO issue = issueInfoDao.findBBallotDetail(lotteryId, issueNumber);
		if(null != issue) {
			IssueInfo issueInfo = new IssueInfo();
			BeanUtils.copyProperties(issue, issueInfo);
			return issueInfo;
		}
		return null;
	}

	@Transactional
	@Override
	public List<SSQResult> computeSSQResult(List<IssueInfo> issueInfoList) {
		ArrayList<SSQResult> results = new ArrayList<SSQResult>();
		for (IssueInfo issueInfo:issueInfoList){
			SSQResult ssqResult = new SSQResult();
			String resultCode = issueInfo.getBonusCode();
			String[] redBlue = resultCode.split("\\|");
			ssqResult.setRedBalls(redBlue[0].trim().replaceAll(",", " "));
			ssqResult.setBlueBall(redBlue[1].trim());
			ssqResult.setBigSmall(computeSSQBigSmall(ssqResult.getRedBalls()));
			ssqResult.setSum(computeSSQSum(ssqResult.getRedBalls()));
			ssqResult.setIssueNum(issueInfo.getIssueNumber());
			ssqResult.setShortIssueNum(issueInfo.getIssueNumber().substring(4));
			
			//查询奖池(滚存是用上一期期号去查找的.)
			int issueNum = Integer.parseInt(issueInfo.getIssueNumber());
			SsqInfoPO ssqIssue = ssqIssueInfoDao.getSsqInfoPOByIssueNum(issueNum+"");
			if(null != ssqIssue) {
				ssqResult.setJackpot(ssqIssue.getJackpot());
			} else {
				logger.error("无法查询SSQ, 期号={}, 的奖池滚存.", issueInfo.getIssueNumber());
			}
			
			results.add(ssqResult);
		}
		return results;
	}

	// 计算双色球大小比值. 01-16 小，17-33 大
	private String computeSSQBigSmall(String redBalls) {
		int big = 0;
		int small = 0;
		String[] balls = redBalls.split(" ");
		for(String theBall:balls) {
			int num = Integer.parseInt(theBall, 10);
			if (num <17){
				small++;
			}else{
				big++;
			}
		}
		return String.format("%d:%d", big, small);
	}
	
	// 计算双色球的和值
	private String computeSSQSum(String redBalls) {
		int sum = 0;
		String[] balls = redBalls.split(" ");
		for(String theBall:balls) {
			int num = Integer.parseInt(theBall, 10);
			sum += num;
		}
		return ""+sum;
	}
	
	@Override
	@Transactional
	public IssueInfo findCurrentIssue(String lotteryId, Date targetDate) {
		IssueInfoPO infoPO = issueInfoDao.findCurrentSalingValid(lotteryId, targetDate);
		IssueInfo issueInfo = null;
		if(null != infoPO) {
			issueInfo = new IssueInfo();
			BeanUtils.copyProperties(infoPO, issueInfo);
		}
		return issueInfo;
	}

	@Override
	@Transactional(readOnly = true)
	public IssueInfo findLatestBallotIssue(String lotteryId, Integer[] status,
			String playId) {
		IssueInfoPO issueInfoPO = issueInfoDao.findLatestBallotIssue(lotteryId, status, playId);
		IssueInfo issue = new IssueInfo();
		if(null != issueInfoPO) {
			BeanUtils.copyProperties(issueInfoPO, issue);
		}	
		return issue;
	}
	
	@Override
	@Transactional
	public IssueInfo findCurrentOnSaling(String lotteryId, Date targetDate) {
		IssueInfoPO infoPO = issueInfoDao.findCurrentOnSaling(lotteryId, targetDate);
		IssueInfo issueInfo = null;
		if(null != infoPO) {
			issueInfo = new IssueInfo();
			BeanUtils.copyProperties(infoPO, issueInfo);
		}
		return issueInfo;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<IssueInfo> findBBallotRecordsOfPlayId(Paging paging, String lotteryId,
			Integer[] status, String playId) {
		List<IssueInfo> issueList = new ArrayList<IssueInfo>();
		List<IssueInfoPO> list = issueInfoDao.findBBallotRecordsOfPlayId(paging, lotteryId, status, playId);
		if(null != list && list.size() > 0) {
			for(IssueInfoPO po : list) {
				IssueInfo issue = new IssueInfo();
				BeanUtils.copyProperties(po, issue);
				issueList.add(issue);
			}
		}
		return issueList;
	}
	
	@Transactional
	@Override
	public String findOnsale(String lotteryId, String playId) {
		String issueNumber = issueInfoDao.findOnSaleIssueBy(lotteryId, playId, EntityStatus.MATCH_ON_SALE);
		logger.debug("查询期信息: lotteryId={}, playId={}, issueNumber={}", 
				new Object[]{lotteryId, playId, issueNumber});
		
		return issueNumber;
	}

	@Override
	@Transactional
	public long computeCountDownTimeForUser(IssueInfo issueInfo) {
		Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
		long countdownTime = -1;
		if(null != issueInfo) {
			Date closeTime = issueInfo.getCloseTime();
			
			if(null != closeTime) {
				Date newStopTimeForUser=DateUtils.addMinutes(closeTime, -1*beforeCloseMinute);
				countdownTime = newStopTimeForUser.getTime() - System.currentTimeMillis();
				countdownTime = countdownTime / 1000;
			}
		}
		return countdownTime;
	}

	@Override
	@Transactional
	public Date computeNewStopTimeForUser(IssueInfo issueInfo) {
		if(null!=issueInfo){
			Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
			List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
			Date now=new Date();
			OpenSaleTime openSaleTime=OpenSaleTimeUtil.transferToOpenSaleTime(openAndEndTimes,now);
			Date closeTime = issueInfo.getCloseTime();
			if(null != closeTime&&null!=openSaleTime) {
				Date newCloseTime=DateUtils.addMinutes(closeTime, -1*beforeCloseMinute);
				if(newCloseTime.compareTo(openSaleTime.getTodayOpenDateTime())<=0){//当前期结束时间提前一小时后，仍然早于今日投注开始时间
					return null;//当前期无法投注
				}
				else if(newCloseTime.compareTo(openSaleTime.getTodayOpenDateTime())>0&&newCloseTime.compareTo(openSaleTime.getTodayEndDateTime())<0){
					if(now.compareTo(newCloseTime)>=0){//当前时间已经晚于期的新截止时间
						return null;//当前期无法投注
					}
					
				}
				else if(newCloseTime.compareTo(openSaleTime.getTodayEndDateTime())>=0&&newCloseTime.compareTo(openSaleTime.getTomorrowOpenDateTime())<0){
					if(now.compareTo(newCloseTime)>=0){//当前时间已经晚于今日投注结束时间
						return null;//当前期无法投注
					}
				}
				
				return newCloseTime;
			}
		}
		return null;
	}

	@Override
	public Paging findByPage(LotteryId lotteryId, PlayType playType,
			String issueNumber, Paging paging) {
		List<IssueInfo> infos = new ArrayList<IssueInfo>();
		List<IssueInfoPO> infoPOs = issueInfoDao.findByPage(lotteryId, playType,issueNumber, paging);
		for (IssueInfoPO issueInfoPO : infoPOs) {
			IssueInfo issueInfo = new IssueInfo();
			BeanUtilsTools.copyNotNullProperties(issueInfoPO, issueInfo, null);
			infos.add(issueInfo);
		}
		paging.setResults(infos);
		return paging;
	}

	@Override
	public List<Long> findCTZTIssueInfoList(LotteryId lotteryId,String issueNumber,int maxResult) {
		return issueInfoDao.findIssueInfoListByLotteryId(lotteryId,issueNumber,maxResult);
	}

	@Override
	public boolean updateCTZCPresetBonus(String issueNumber, String playId,
			float bonus1, float bonus2) {
		IssueInfoPO issueInfoPO = issueInfoDao.findByPlayIdAndIssueNumber(playId, issueNumber);
		if(bonus1>0 && bonus2 >0){
			issueInfoPO.setPresetBonusInfo(bonus1+";"+bonus2);
		} else{
			issueInfoPO.setPresetBonusInfo(bonus1+"");
		}
		return true;
	}
	@Override
	public boolean isExistsCTZCIssueInfoUnset(String lotteryId,String issueNumber,boolean valid){
		Integer isHaveIssueInfo=issueInfoDao.isExistsCTZCIssueInfo(lotteryId,issueNumber);
		if(isHaveIssueInfo>0){
			Integer unSetIssueInfoCount=issueInfoDao.findIssueCountByLotteryIdAndIssueNumber(lotteryId, issueNumber,valid);
			if(unSetIssueInfoCount>0){
				return true;
			}
			return false;
		}
		return true;
	}
	@Override
	@Transactional
	public void updateJXIssue_(Long id, int status,boolean valid){
		IssueInfoPO issueInfo = issueInfoDao.get(id);
		if (issueInfo != null) {
			issueInfo.setStatus(status);
			issueInfo.setValid(valid);
		}
	}

	@Override
	@Transactional
	public IssueInfo getIssueByIssueNumberAndPlayId(String issueNumber,
			String pid) {
		IssueInfoPO po = issueInfoDao.findIssueByIssueNumberAndPlayId(issueNumber,pid);
		IssueInfo i = new IssueInfo();
		BeanUtils.copyProperties(po, i);
		return i;
	}
}
