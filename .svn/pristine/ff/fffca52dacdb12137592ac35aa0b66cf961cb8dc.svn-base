package com.xhcms.lottery.alarm.main;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xhcms.commons.job.JobTimer;
import com.xhcms.commons.job.Task;

public class RunAlarmSystem {
	private static final Logger logger = LoggerFactory.getLogger(RunAlarmSystem.class);
	
	public static void main(String[] args) {
		logger.info("AlarmSystem  started...");
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
        JobTimer timer = new JobTimer();
		SpringMonitorTask mTask = new SpringMonitorTask(timer, context);
		timer.addTask(mTask);
		Map<String, Task> taskMaps = context.getBeansOfType(Task.class);
		Collection<Task> tasks =taskMaps .values();
        for(Task t: tasks){
            timer.addTask(t);
        }
        File lock = new File(SpringMonitorTask.PID_FILE);
        while(lock.exists()) {
	        try {
	            Thread.sleep(2000L);
	        } catch (InterruptedException e) {
	        	
	        }
        }
	}
}
