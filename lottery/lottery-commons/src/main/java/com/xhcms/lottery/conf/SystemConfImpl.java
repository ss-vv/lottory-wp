package com.xhcms.lottery.conf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.SystemConfDao;
import com.xhcms.lottery.commons.persist.entity.SystemConfPO;

public class SystemConfImpl implements SystemConf {
	
	

	private static final String SHOULD_UPGRADE_SHITIDIANS = "should_upgrade_shitidians";

	private static final String BIG_TICKET_MONEY_THRESHOLD = "big_ticket_money_threshold";

	private static final String SHOULD_ARRANGE_BIG_TICKET = "should_arrange_big_ticket";

	private static final String ALLOW_SELF_REGIST = "allow_self_regist";

	private static final String CAN_SEND_MONITOR_EMAIL_START_TIME = "send_monitor_email_start_time";

	private static final String CAN_SEND_MONITOR_EMAIL_END_TIME = "send_monitor_email_end_time";
	
	//远程出票条件限制配置项目
	/**
	 * 远程出票分配票的开始时间
	 */
	public static final String YUANCHENGCHUPIAO_START_TIME ="yuanchengchupiao_start_time";
	/**
	 * 远程出票分配票的结束时间
	 */
	public static final String YUANCHENGCHUPIAO_STOP_TIME ="yuanchengchupiao_stop_time";
	/**
	 * 远程出票 分配票的最大总金额
	 */
	public static final String YUANCHENGCHUPIAO_TOTAL_MONEY ="yuanchengchupiao_totalMoney";
	/**
	 * 远程出票 分配票的总最大理论奖金
	 */
	public static final String YUANCHENGCHUPIAO_MAXBONUS ="yuanchengchupiao_maxBonus";
	/**
	 * 当天分给远程出票的累计金额
	 */
	public static final String YUANCHENGCHUPIAO_CURRENT_MONEY ="yuanchengchupiao_currentMoney";
	
	
	/**
	 * 远程出票分配标志
	 */
	public static final String YUANCHENGCHUPIAO_FLAG ="yuanchengchupiao_flag";
	
	

	@Autowired
	private SystemConfDao systemConfDao;
	
	private Map<String,String> keyValueMap=new HashMap<String,String>();
	
	private Date lastInitTime;
	
	private int defaultTimeoutInMinute=5;

	
	@Transactional
	private synchronized void  init() {
		Map<String,String> keyValueMapTemp=new HashMap<String,String>();
		List<SystemConfPO> systemConfPOList=systemConfDao.loadAll();
		if(null!=systemConfPOList&&!systemConfPOList.isEmpty()){
			for(SystemConfPO systemConfPO:systemConfPOList){
				keyValueMapTemp.put(systemConfPO.getConfKey(), systemConfPO.getConfValue());
			}
		}
		if(null!=keyValueMapTemp&&!keyValueMapTemp.isEmpty()){
			keyValueMap=keyValueMapTemp;
			lastInitTime=new Date();
		}
		

	}

	@Override
	@Transactional
	public Integer getIntegerValueByKey(String key) {
		Integer result=null;
		if(shouldInit()){
			init();
		}
		if(null!=keyValueMap&&StringUtils.isNotBlank(key)){
			String value=keyValueMap.get(key);
			if(StringUtils.isNotBlank(value)){
				try{
					result=Integer.parseInt(value);
				}
				catch(Exception e){
					e.printStackTrace();
					result=null;
				}
				
			}
		}
		return result;
	}
	
	@Override
	@Transactional
	public Long getLongValueByKey(String key) {
		Long result=null;
		if(shouldInit()){
			init();
		}
		if(null!=keyValueMap&&StringUtils.isNotBlank(key)){
			String value=keyValueMap.get(key);
			if(StringUtils.isNotBlank(value)){
				try{
					result=Long.parseLong(value);
				}
				catch(Exception e){
					e.printStackTrace();
					result=null;
				}
				
			}
		}
		return result;
	}

	private synchronized boolean shouldInit() {
		boolean result=false;
		if(null==lastInitTime){
			result=true;
		}
		else{
			Date now=new Date();
			Date expectedTime=DateUtils.addMinutes(lastInitTime, this.defaultTimeoutInMinute);
			if(now.after(expectedTime)){
				result=true;
			}
		}
		return result;
	}

	public int getDefaultTimeoutInMinute() {
		return defaultTimeoutInMinute;
	}

	public void setDefaultTimeoutInMinute(int defaultTimeoutInMinute) {
		this.defaultTimeoutInMinute = defaultTimeoutInMinute;
	}

	public SystemConfDao getSystemConfDao() {
		return systemConfDao;
	}

	public void setSystemConfDao(SystemConfDao systemConfDao) {
		this.systemConfDao = systemConfDao;
	}

	@Transactional
	@Override
	public String findValueByKey(String key) {
		return systemConfDao.findValueByKey(key);
	}

	@Override
	@Transactional
	public boolean isAllowSelfRegiste() {
		boolean result=false;
		Integer value = systemConfDao.findIntValueByKey(ALLOW_SELF_REGIST);
		if(null!=value&&value==1){
			result=true;
		}
		return result;
	}

	@Override
	@Transactional
	public boolean canSendMonitorEmail() {
		boolean result=false;
		String startTimeStr = systemConfDao.findValueByKey(CAN_SEND_MONITOR_EMAIL_START_TIME);
		String endTimeStr = systemConfDao.findValueByKey(CAN_SEND_MONITOR_EMAIL_END_TIME);
		if(StringUtils.isNotBlank(startTimeStr)&&StringUtils.isNotBlank(endTimeStr)){
			SimpleDateFormat yyyyMMddSdf=new SimpleDateFormat("yyyy-MM-dd");
			Date now=new Date();
			String todayStr=yyyyMMddSdf.format(now);
			String todayStartTimeStr=todayStr+" "+startTimeStr;
			String todayEndTimeStr=todayStr+" "+endTimeStr;
			try {
				Date todayStartTime=DateUtils.parseDate(todayStartTimeStr, "yyyy-MM-dd HH:mm:ss");
				Date todayEndTime=DateUtils.parseDate(todayEndTimeStr, "yyyy-MM-dd HH:mm:ss");
				if(now.compareTo(todayStartTime)>=0&&now.compareTo(todayEndTime)<=0){
					result=true;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean shouldArrangeBigTicket() {
		boolean result=false;
		Integer value = systemConfDao.findIntValueByKey(SHOULD_ARRANGE_BIG_TICKET);
		if(null!=value&&value==1){
			result=true;
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public Integer getBigTicketMoneyThreshold() {
		return systemConfDao.findIntValueByKey(BIG_TICKET_MONEY_THRESHOLD);
	}

	@Override
	@Transactional(readOnly=true)
	public String getShouldUpgradeShiTiDians() {
		return systemConfDao.findValueByKey(SHOULD_UPGRADE_SHITIDIANS);
	}

	@Override
	@Transactional(readOnly=false)
	public void updateLongValueByKey(String key, String value) {
		systemConfDao.updateIntegerValueByKey(key, value);
		
	}
	@Override
	@Transactional(readOnly=false)
	public void updateStrinfValueByKey(String key, String value) {
		systemConfDao.updateIntegerValueByKey(key, value);
		
	}
}
