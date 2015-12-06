package com.xhcms.lottery.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.xhcms.lottery.commons.data.OpenSaleTime;
import com.xhcms.lottery.commons.persist.entity.LotteryOpenSalePO;

public class OpenSaleTimeUtil {

	public static OpenSaleTime transferToOpenSaleTime(
			List<LotteryOpenSalePO> openAndEndTimes, Date now) {
		OpenSaleTime result=null;
		Map<Integer,LotteryOpenSalePO> map=new HashMap<Integer,LotteryOpenSalePO>();
		if (null != openAndEndTimes&&!openAndEndTimes.isEmpty()){
			
			for(LotteryOpenSalePO openAndEndTime:openAndEndTimes){
				if(null!=openAndEndTime&& null != openAndEndTime.getOpenTime()
						&& null != openAndEndTime.getEndTime()
						){
					map.put(openAndEndTime.getDayOfWeek(), openAndEndTime);
				}
			}
		}
				
			if(null!=now&&null!=map&&map.size()==7) {
			try{
				result=new OpenSaleTime();
				Date tomorrow=DateUtils.addDays(now, 1);
				Date yesterday=DateUtils.addDays(now, -1);
				SimpleDateFormat yyyyMMddSdf=new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat hhMMSSSdf=new SimpleDateFormat("HH:mm:ss");
				String todayStr=yyyyMMddSdf.format(now);
				String yesterdayStr=yyyyMMddSdf.format(yesterday);
				String tomorrowStr=yyyyMMddSdf.format(tomorrow);
				LotteryOpenSalePO openAndEndTime=map.get(DateUtils.toCalendar(now).get(Calendar.DAY_OF_WEEK));
				if(null!=openAndEndTime){
					String todayOpenTimeStr=hhMMSSSdf.format(openAndEndTime.getOpenTime());
					String todayOpenDateTimeStr=todayStr+" "+todayOpenTimeStr;
					Date todayOpenDateTime = DateUtils.parseDate(todayOpenDateTimeStr, "yyyy-MM-dd HH:mm:ss");
					
					String todayEndTimeStr=hhMMSSSdf.format(openAndEndTime.getEndTime());
					String todayEndDateTimeStr=null;
					if(openAndEndTime.getIsEndTimeCrossDay()==1){//跨天
						todayEndDateTimeStr=tomorrowStr+" "+todayEndTimeStr;
					}
					else{//不跨天
						todayEndDateTimeStr=todayStr+" "+todayEndTimeStr;
					}
					Date todayEndDateTime=DateUtils.parseDate(todayEndDateTimeStr, "yyyy-MM-dd HH:mm:ss");
					
					LotteryOpenSalePO tomorrowOpenTime=map.get(DateUtils.toCalendar(tomorrow).get(Calendar.DAY_OF_WEEK));
					String tomorrowOpenTimeStr=hhMMSSSdf.format(tomorrowOpenTime.getOpenTime());
					String tomorrowOpenDateTimeStr=tomorrowStr+" "+tomorrowOpenTimeStr;
					Date tomorrowOpenDateTime=DateUtils.parseDate(tomorrowOpenDateTimeStr, "yyyy-MM-dd HH:mm:ss");
					
					LotteryOpenSalePO yesterdayEndTime=map.get(DateUtils.toCalendar(yesterday).get(Calendar.DAY_OF_WEEK));
					Date yesterdayEndDateTime=null;
					if(null!=yesterdayEndTime){
						String yesterdayEndDateTimeStr=null;
						String yesterdayEndTimeStr=hhMMSSSdf.format(yesterdayEndTime.getEndTime());
						if(yesterdayEndTime.getIsEndTimeCrossDay()==1){//昨日的结束时间是跨天的
							yesterdayEndDateTimeStr=todayStr+" "+yesterdayEndTimeStr;
						}
						else{//昨日的结束时间不跨天
							yesterdayEndDateTimeStr=yesterdayStr+" "+yesterdayEndTimeStr;
						}
						yesterdayEndDateTime=DateUtils.parseDate(yesterdayEndDateTimeStr, "yyyy-MM-dd HH:mm:ss");
					}
					
					
					result.setTodayEndDateTime(todayEndDateTime);
					result.setTodayOpenDateTime(todayOpenDateTime);
					result.setTomorrowOpenDateTime(tomorrowOpenDateTime);
					result.setYesterdayEndDateTime(yesterdayEndDateTime);
				}
				
				
				
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static OpenSaleTime transferToMachineOpenSaleTime(List<LotteryOpenSalePO> openAndEndTimes, Date now) {
		OpenSaleTime result=null;
		Map<Integer,LotteryOpenSalePO> map=new HashMap<Integer,LotteryOpenSalePO>();
		if (null != openAndEndTimes&&!openAndEndTimes.isEmpty()){
			for(LotteryOpenSalePO openAndEndTime:openAndEndTimes){
				if(null!=openAndEndTime&& null != openAndEndTime.getOpenTime()
						&& null != openAndEndTime.getEndTime()
						){
					map.put(openAndEndTime.getDayOfWeek(), openAndEndTime);
				}
			}
		}
		
		if(null!=now&&null!=map&&map.size()==7) {
			try{
				result=new OpenSaleTime();
				Date tomorrow=DateUtils.addDays(now, 1);
				Date yesterday=DateUtils.addDays(now, -1);
				SimpleDateFormat yyyyMMddSdf=new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat hhMMSSSdf=new SimpleDateFormat("HH:mm:ss");
				String todayStr=yyyyMMddSdf.format(now);
				String yesterdayStr=yyyyMMddSdf.format(yesterday);
				String tomorrowStr=yyyyMMddSdf.format(tomorrow);
				LotteryOpenSalePO openAndEndTime=map.get(DateUtils.toCalendar(now).get(Calendar.DAY_OF_WEEK));
				if(null!=openAndEndTime){
					String todayOpenTimeStr=hhMMSSSdf.format(openAndEndTime.getOpenTime());
					String todayOpenDateTimeStr=todayStr+" "+todayOpenTimeStr;
					Date todayOpenDateTime = DateUtils.parseDate(todayOpenDateTimeStr, "yyyy-MM-dd HH:mm:ss");
					
					String todayEndTimeStr=hhMMSSSdf.format(openAndEndTime.getMachineOfftime());
					String todayEndDateTimeStr=null;
					if(openAndEndTime.getIsMachineOfftimeCrossDay()==1){//跨天
						todayEndDateTimeStr=tomorrowStr+" "+todayEndTimeStr;
					} else {//不跨天
						todayEndDateTimeStr=todayStr+" "+todayEndTimeStr;
					}
					Date todayEndDateTime=DateUtils.parseDate(todayEndDateTimeStr, "yyyy-MM-dd HH:mm:ss");
					
					LotteryOpenSalePO tomorrowOpenTime=map.get(DateUtils.toCalendar(tomorrow).get(Calendar.DAY_OF_WEEK));
					String tomorrowOpenTimeStr=hhMMSSSdf.format(tomorrowOpenTime.getOpenTime());
					String tomorrowOpenDateTimeStr=tomorrowStr+" "+tomorrowOpenTimeStr;
					Date tomorrowOpenDateTime=DateUtils.parseDate(tomorrowOpenDateTimeStr, "yyyy-MM-dd HH:mm:ss");
					
					LotteryOpenSalePO yesterdayEndTime=map.get(DateUtils.toCalendar(yesterday).get(Calendar.DAY_OF_WEEK));
					String yesterdayEndTimeStr=hhMMSSSdf.format(yesterdayEndTime.getMachineOfftime());
					String yesterdayEndDateTimeStr="";
					if(yesterdayEndTime.getIsMachineOfftimeCrossDay()==1){
						yesterdayEndDateTimeStr=todayStr+" "+yesterdayEndTimeStr;
					} else {
						yesterdayEndDateTimeStr=yesterdayStr+" "+yesterdayEndTimeStr;
					}
					
					Date yesterdayEndDateTime=DateUtils.parseDate(yesterdayEndDateTimeStr, "yyyy-MM-dd HH:mm:ss");
					
					result.setTodayEndDateTime(todayEndDateTime);
					result.setTodayOpenDateTime(todayOpenDateTime);
					result.setTomorrowOpenDateTime(tomorrowOpenDateTime);
					result.setYesterdayEndDateTime(yesterdayEndDateTime);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

}
