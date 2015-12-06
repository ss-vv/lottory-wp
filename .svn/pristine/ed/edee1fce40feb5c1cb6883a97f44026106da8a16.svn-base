package com.unison.lottery.weibo.data.crawler.core;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.xhcms.commons.job.JobTimer;
import com.xhcms.commons.job.Task;

public class WeiboFbEuroOddsLauncher {
	private static final Logger log = LoggerFactory.getLogger(WeiboFbEuroOddsLauncher.class);

	public static void main(String[] args) {
		log.info("weibo-zq-euro-odds-crawler starting...");
		
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"classpath:mq-crawler-service.xml","pushClient.xml"});
		
		File lock = new File(SpringMonitorTask.PID_FILE);
		System.out.println(lock.exists());
        while(lock.exists()) {
	        try {
	            Thread.sleep(3000L);
	        } catch (InterruptedException e) {
	        }
        }
        log.info("weibo-zq-euro-odds-crawler exit.");
	}
}
