package com.davcai.lottery.service.impl;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;

public class MatchComparator implements Comparator<Object[]> {

	private static Calendar calendar;
	
	public MatchComparator() {
		if (calendar == null) {
			calendar = Calendar.getInstance();
		}
	}
	@Override
	public int compare(Object[] t1, Object[] t2) {
		if(t1[0] instanceof FbMatchBaseInfoPO){
			FbMatchBaseInfoPO fbMatchBaseInfoPO1 = (FbMatchBaseInfoPO) t1[0];
			FbMatchBaseInfoPO fbMatchBaseInfoPO2 = (FbMatchBaseInfoPO)t2[0];
			Long matchTime1 = fbMatchBaseInfoPO1.getMatchTime().getTime();
			Long matchTime2 = fbMatchBaseInfoPO2.getMatchTime().getTime();
			String jingcaiId1 = fbMatchBaseInfoPO1.getJingcaiId();
			String jingcaiId2 = fbMatchBaseInfoPO2.getJingcaiId();
			String week = getWeekOfDate();
			return compareResult(matchTime1, matchTime2, jingcaiId1,
					jingcaiId2, week);
			
		}else if(t1[0] instanceof BasketBallMatchPO){
			BasketBallMatchPO fbMatchBaseInfoPO1 = (BasketBallMatchPO) t1[0];
			BasketBallMatchPO fbMatchBaseInfoPO2 = (BasketBallMatchPO)t2[0];
			Long matchTime1 = fbMatchBaseInfoPO1.getMatchTime().getTime();
			Long matchTime2 = fbMatchBaseInfoPO2.getMatchTime().getTime();
			String jingcaiId1 = fbMatchBaseInfoPO1.getJingcaiId();
			String jingcaiId2 = fbMatchBaseInfoPO2.getJingcaiId();
			String week = getWeekOfDate();
			return compareResult(matchTime1, matchTime2, jingcaiId1,
					jingcaiId2, week);
		}
		return 1;
	}
	private int compareResult(Long matchTime1, Long matchTime2,
			String jingcaiId1, String jingcaiId2, String week) {
		if(StringUtils.contains(
				jingcaiId1,week)&&
				StringUtils.contains(jingcaiId2, week)){
			if(jingcaiId1.compareToIgnoreCase(jingcaiId2)>=0){
				return 1;
			}else{
				return -1;
			}
		}else if(StringUtils.contains(
				jingcaiId1,week)){
			return -1;
		}else if(StringUtils.contains(
				jingcaiId2,week)){
			return 1;
		}else{
			if(matchTime1>matchTime2){
				return -1;
			}
			return 1;
		}
	}
	
	 /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    private String getWeekOfDate() {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        calendar.setTime(new Date());
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
