package com.davcai.data.statistic;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.converters.CalendarConverter;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.davcai.data.statistic.task.Top5RecommendStatisticTask;


public class WeiboStatisticTaskLauncher {
	private static final Logger logger = LoggerFactory.getLogger(WeiboStatisticTaskLauncher.class);
	
	public static void main(String[] args){
		logger.info("WeiboStatisticTask starting...");
		long start=System.currentTimeMillis();
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:weiboStatisticTask.xml");
		Top5RecommendStatisticTask task=(Top5RecommendStatisticTask) context.getBean("top5RecommendStatisticTask");
		
		task.run(get7From(), get7To());
		
		
		long end=System.currentTimeMillis();
		logger.info("WeiboStatisticTask finished.耗时:{}ms",end-start);
		context.close();
	}
	
	private static Date get7From(){
		Calendar cal = Calendar.getInstance();
		Date fromDate=DateUtils.addDays(new Date(), -7);
		
		cal.setTime(fromDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	private static Date get7To(){
		Calendar cal = Calendar.getInstance();
		Date toDate=DateUtils.addDays(new Date(), -1);
		cal.setTime(toDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
