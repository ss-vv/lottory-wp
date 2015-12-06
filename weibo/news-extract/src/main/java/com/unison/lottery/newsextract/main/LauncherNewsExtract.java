package com.unison.lottery.newsextract.main;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xhcms.commons.job.JobTimer;
import com.xhcms.commons.job.Task;

/**
 * 新闻抓取器
 * @author haoxiang.jiang@unison.com.cn
 * @date 2014-3-10 下午4:21:35
 */
public class LauncherNewsExtract {
	private static final Logger logger = LoggerFactory.getLogger(LauncherNewsExtract.class);
	public static void main(String[] args) {
		logger.info("开始抓取新闻...");
		String seedUrl = "";
		logger.info("获得新闻URL种子：{}",seedUrl);
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:spring-*.xml");

		JobTimer timer = new JobTimer();
		SpringMonitorTask mTask = new SpringMonitorTask(timer, context);
		timer.addTask(mTask);

		for (Task t : context.getBeansOfType(Task.class).values()) {
			timer.addTask(t);
		}
		
		File lock = new File(SpringMonitorTask.PID_FILE);
        while(lock.exists()) {
	        try {
	            Thread.sleep(2000L);
	        } catch (InterruptedException e) {
	        }
        }
        System.out.println("Fasd");
	}

}
