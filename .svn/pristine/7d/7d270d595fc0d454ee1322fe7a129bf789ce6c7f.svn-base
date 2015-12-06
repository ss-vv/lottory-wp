package com.xhcms.lottery.commons.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.UpdateAndInsertList;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryId;


@Transactional
public class IssueinfoPOChangeUtilImpl implements IssueinfoPOChangeUtil {
	
	//private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SystemConf systemConf;
	
	@Autowired
	private IssueInfoDao issueinfoDao;
	

	/* (non-Javadoc)
	 * @see com.xhcms.lottery.commons.util.IssueinfoPOChangeUtil#split2UpdateAndInsertList(java.util.List)
	 */
	@Override
	public  UpdateAndInsertList split2UpdateAndInsertList(
			List<IssueInfo> issueinfos) {
		
		UpdateAndInsertList result=new UpdateAndInsertList();
		Map<String, IssueInfoPO> map4OldPO = initMap4OldPO(issueinfos);
		Map<String,IssueInfo> map4VO=initMap4VO(issueinfos);
		int aheadInMinute4ZMCloseTime;
		int aheadInMinute4LCStopTime;

		LotteryId issueLotteryId = LotteryId.valueOf(issueinfos.get(0).getLotteryId());
		
		switch(issueLotteryId){
		case CTZC:
			aheadInMinute4ZMCloseTime = 0;
			aheadInMinute4LCStopTime = computeLCStopTimeIntervalForCTZC();
			break;
		case SSQ:
			aheadInMinute4ZMCloseTime = 10;	// 中民接票停止提前时间，相对期截止时间
			aheadInMinute4LCStopTime = 1;	// 大V彩接票停止提前时间，相对中民接票停止时间
			break;
		case FC3D:
			aheadInMinute4ZMCloseTime = 10;	// 中民接票停止提前时间，相对期截止时间
			aheadInMinute4LCStopTime = 1;	// 来彩接票停止提前时间，相对中民接票停止时间
			break;
		default:
			aheadInMinute4ZMCloseTime = computeZMCloseTimeInterval();
			aheadInMinute4LCStopTime = computeLCStopTimeInterval();
			break;
		}
		List<IssueInfoPO> POList4Update = initPOList4Update(issueinfos,
				map4OldPO, map4VO, aheadInMinute4ZMCloseTime,
				aheadInMinute4LCStopTime);
		
		List<IssueInfoPO> POList4Insert = initPOList4Insert(issueinfos,
				map4OldPO, map4VO, aheadInMinute4ZMCloseTime,
				aheadInMinute4LCStopTime);
		
		result.setIssueinfoPOsShouldInsert(POList4Insert);
		result.setIssueinfoPOsShouldUpdate(POList4Update);
		return result;
	}

	/**
	 * 初始化传统足彩 大V彩提前截止时间
	 * @return
	 */
	private int computeLCStopTimeIntervalForCTZC() {
		int intervalInMinute4LCStopTime=1;//由大V彩定义的用户投注结束时间间隔		
		Integer intervalInMinute4LCStopTimeTemp=systemConf.getIntegerValueByKey(SystemConf.CLOSETIME.CTZC_LC_AHEAD_SECOND);
		if(null!=intervalInMinute4LCStopTimeTemp){
			intervalInMinute4LCStopTime=intervalInMinute4LCStopTimeTemp;
		}
		return intervalInMinute4LCStopTime;
	}
	
	private int computeLCStopTimeInterval() {
		int intervalInMinute4LCStopTime=1;//由大V彩定义的用户投注结束时间间隔		
		Integer intervalInMinute4LCStopTimeTemp=systemConf.getIntegerValueByKey(SystemConf.KEY_INTERVAL_IN_MINUTE_4_LC_STOPTIME);
		if(null!=intervalInMinute4LCStopTimeTemp){
			intervalInMinute4LCStopTime=intervalInMinute4LCStopTimeTemp;
		}
		return intervalInMinute4LCStopTime;
	}

	private int computeZMCloseTimeInterval() {
		int intervalInMinute4ZMCloseTime=2;//中民截止时间间隔		
		Integer intervalInMinute4ZMCloseTimeTemp=systemConf.getIntegerValueByKey(SystemConf.KEY_INTERVAL_IN_MINUTE_4_ZM_CLOSETIME);
		if(null!=intervalInMinute4ZMCloseTimeTemp){
			intervalInMinute4ZMCloseTime=intervalInMinute4ZMCloseTimeTemp;
		}
		return intervalInMinute4ZMCloseTime;
	}
	
	private Map<String, IssueInfoPO> initMap4OldPO(List<IssueInfo> issueinfos) {
		Map<String, IssueInfoPO> result=null;
		List<IssueInfoPO> oldIssueinfoPOs=issueinfoDao.findListByLotteryIdAndIssueNumber(issueinfos);
		
		if(null!=oldIssueinfoPOs&&!oldIssueinfoPOs.isEmpty()){
			result=new HashMap<String,IssueInfoPO>();
			for(IssueInfoPO oldIssueinfoPO:oldIssueinfoPOs){
				String key=oldIssueinfoPO.getLotteryId()+"_"+oldIssueinfoPO.getIssueNumber();
				result.put(key, oldIssueinfoPO);
			}
		}
		
		return result;
	}

	private Map<String, IssueInfo> initMap4VO(List<IssueInfo> issueinfos) {
		Map<String, IssueInfo> result=null;
		if(null!=issueinfos&&!issueinfos.isEmpty()){
			result=new HashMap<String,IssueInfo>();
			for(IssueInfo issueinfo:issueinfos){
				String key=issueinfo.getLotteryId()+"_"+issueinfo.getIssueNumber();
				result.put(key, issueinfo);
			}
		}
		return result;
	}

	/**
	 * 初始化待插入列表
	 * @param issueinfos
	 * @param map4vo 
	 * @param map4OldPO 
	 * @param intervalInMinute4LCStopTime 
	 * @param intervalInMinute4ZMCloseTime2 
	 */
	private List<IssueInfoPO> initPOList4Insert(List<IssueInfo> issueinfos, Map<String, IssueInfoPO> map4OldPO, Map<String, IssueInfo> map4vo, int intervalInMinute4ZMCloseTime, int intervalInMinute4LCStopTime) {
		List<IssueInfoPO> POList4Insert =new ArrayList<IssueInfoPO>();
		if(null!=map4OldPO&&!map4OldPO.isEmpty()){
			for(String key4VO:map4vo.keySet()){
				if(!map4OldPO.containsKey(key4VO)){
					IssueInfoPO issueinfoPO=transfer2PO4Insert(map4vo.get(key4VO),intervalInMinute4ZMCloseTime,intervalInMinute4LCStopTime);
					POList4Insert.add(issueinfoPO);
				}
			}
		}
		else{
			for(IssueInfo issueinfo:issueinfos){
				IssueInfoPO issueinfoPO=transfer2PO4Insert(issueinfo,intervalInMinute4ZMCloseTime,intervalInMinute4LCStopTime);
				POList4Insert.add(issueinfoPO);
			}
		}
		return POList4Insert;
	}

	private IssueInfoPO transfer2PO4Insert(IssueInfo issueinfo, int intervalInMinute4ZMCloseTime, int intervalInMinute4LCStopTime) {
		IssueInfoPO issueinfoPO=new IssueInfoPO();
		issueinfoPO.setIssueNumber(issueinfo.getIssueNumber());
		issueinfoPO.setLotteryId(issueinfo.getLotteryId());
		issueinfoPO.setBonusCode(issueinfo.getBonusCode());
		issueinfoPO.setBonusInfo(issueinfo.getBonusInfo());
		issueinfoPO.setCloseTime(issueinfo.getCloseTime());
		issueinfoPO.setStartTime(issueinfo.getStartTime());
		issueinfoPO.setStatus(issueinfo.getStatus());
		issueinfoPO.setStopTime(issueinfo.getStopTime());
		issueinfoPO.setPrizeTime(issueinfo.getPrizeTime());
		Integer platformId=systemConf.getIntegerValueByKey(SystemConf.CTZC_PLATFORM.GET_CTZC_DATA_PLATFORM);
		if(0==platformId){
			issueinfoPO.setValid(issueinfo.getValid());
		}else if(1==platformId){
			issueinfoPO.setValid(true);
		}
		if(Constants.CTZC.equals(issueinfo.getLotteryId())){
			issueinfoPO.setZMCloseTime(issueinfo.getStopTime());//中民截止时间比官方截止时间(及时开奖时间)提前n分钟
			issueinfoPO.setStopTimeForUser(computeTimeBySeconds(issueinfoPO.getZMCloseTime(),intervalInMinute4LCStopTime));//初始时将大V彩定义的结束时间定为比中民截止时间提前n分钟
		}else{
			issueinfoPO.setZMCloseTime(computeTime(issueinfo.getCloseTime(),intervalInMinute4ZMCloseTime));//初始时将中民截止时间定为比官方截止时间(及时开奖时间)提前n分钟
			issueinfoPO.setStopTimeForUser(computeTime(issueinfoPO.getZMCloseTime(),intervalInMinute4LCStopTime));//初始时将大V彩定义的结束时间定为比中民截止时间提前n分钟
		}
		issueinfoPO.setPlayId(issueinfo.getPlayId());
		
		return issueinfoPO;
	}

	private Date computeTimeBySeconds(Date time, int intervalInSeconds) {
		Date result=null;
		if(null!=time){
			if(intervalInSeconds>0){
				result=DateUtils.addSeconds(time, -1*intervalInSeconds);
			}
			else{
				result=DateUtils.addSeconds(time, intervalInSeconds);
			}
			
		}
		return result;
	}
	
	private Date computeTime(Date time, int intervalInMinute) {
		Date result=null;
		if(null!=time){
			if(intervalInMinute>0){
				result=DateUtils.addMinutes(time, -1*intervalInMinute);
			}
			else{
				result=DateUtils.addMinutes(time, intervalInMinute);
			}
			
		}
		return result;
	}

	/**
	 * 初始化待更新列表
	 * @param issueinfos
	 * @param map4vo 
	 * @param map4OldPO 
	 * @param intervalInMinute4LCStopTimeTemp 
	 * @param intervalInMinute4ZMCloseTimeTemp 
	 * 
	 */
	private List<IssueInfoPO> initPOList4Update(List<IssueInfo> issueinfos, 
			Map<String, IssueInfoPO> map4OldPO, Map<String, IssueInfo> map4vo, 
			int intervalInMinute4ZMCloseTime, int intervalInMinute4LCStopTime) {
		
		List<IssueInfoPO> POList4Update =new ArrayList<IssueInfoPO>();
		if(null!=map4OldPO&&!map4OldPO.isEmpty()){
			for(String key4VO:map4vo.keySet()){
				if (map4OldPO.containsKey(key4VO)) {
					IssueInfoPO issueinfoPO = transfer2PO4Update(
							map4vo.get(key4VO), map4OldPO.get(key4VO),
							intervalInMinute4ZMCloseTime,
							intervalInMinute4LCStopTime);
					POList4Update.add(issueinfoPO);
				}
			}
		}
		return POList4Update;
	}


	/**
	 * 数据库中已有该期内容，则进行更新。
	 * 更新时，注意不要造成错误修改原来已经
	 * 有的值（比如已经开奖的期信息中原来已
	 * 经有开奖号码了，在之后查询的返回结果
	 * 中，这一期的开奖号码字段可能是空的，
	 * 就不要用新值更新数据库里的旧值）
	 *
	 * @param issueinfo
	 * @param issueinfoPO
	 * @param intervalInMinute4ZMCloseTime
	 * @param intervalInMinute4LCStopTime
	 * @return
	 */
	private IssueInfoPO transfer2PO4Update(IssueInfo issueinfo,
			IssueInfoPO issueinfoPO, int intervalInMinute4ZMCloseTime, int intervalInMinute4LCStopTime) {
		if(null!=issueinfoPO){
			if(StringUtils.isNotBlank(issueinfo.getBonusCode())){
				issueinfoPO.setBonusCode(issueinfo.getBonusCode());
			}
			if(StringUtils.isNotBlank(issueinfo.getBonusInfo())){
				issueinfoPO.setBonusInfo(issueinfo.getBonusInfo());
			}
			if(null!=issueinfo.getPrizeTime()){
				issueinfoPO.setPrizeTime(issueinfo.getPrizeTime());
			}
			if(null!=issueinfo.getCloseTime()){
				issueinfoPO.setCloseTime(issueinfo.getCloseTime());
				if(Constants.CTZC.equals(issueinfo.getLotteryId())){
					issueinfoPO.setZMCloseTime(issueinfo.getStopTime());//初始时将中民截止时间定为比官方截止时间(及时开奖时间)提前n分钟
					if(!issueinfoPO.isStopTimeForUserLocked()){//只有在没有锁定的情况下，才更新
						issueinfoPO.setStopTimeForUser(computeTimeBySeconds(issueinfoPO.getZMCloseTime(),intervalInMinute4LCStopTime));//初始时将大V彩定义的结束时间定为比中民截止时间提前n分钟
					}
				}else{
					issueinfoPO.setZMCloseTime(computeTime(issueinfo.getCloseTime(),intervalInMinute4ZMCloseTime));//初始时将中民截止时间定为比官方截止时间(及时开奖时间)提前n分钟
					if(!issueinfoPO.isStopTimeForUserLocked()){//只有在没有锁定的情况下，才更新
						issueinfoPO.setStopTimeForUser(computeTime(issueinfoPO.getZMCloseTime(),intervalInMinute4LCStopTime));//初始时将大V彩定义的结束时间定为比中民截止时间提前n分钟
					}
				}
			}
			if(null!=issueinfo.getStartTime()){
				issueinfoPO.setStartTime(issueinfo.getStartTime());
			}
			if(issueinfo.getStatus()>=0){
				Integer platformId=systemConf.getIntegerValueByKey(SystemConf.CTZC_PLATFORM.GET_CTZC_DATA_PLATFORM);
				if(0==platformId){
					
				}else if(1==platformId){
					issueinfoPO.setStatus(issueinfo.getStatus());
				}
			}
			if(null!=issueinfo.getStopTime()){
				issueinfoPO.setStopTime(issueinfo.getStopTime());				
			}
		}
		return issueinfoPO;
	}

	public SystemConf getSystemConf() {
		return systemConf;
	}

	public void setSystemConf(SystemConf systemConf) {
		this.systemConf = systemConf;
	}

	public IssueInfoDao getIssueinfoDao() {
		return issueinfoDao;
	}

	public void setIssueinfoDao(IssueInfoDao issueinfoDao) {
		this.issueinfoDao = issueinfoDao;
	}
}
