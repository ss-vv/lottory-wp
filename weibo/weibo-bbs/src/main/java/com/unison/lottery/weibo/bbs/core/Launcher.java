package com.unison.lottery.weibo.bbs.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhuyongli
 * @date 2013-12-02
 */
public class Launcher {
	private static Logger logger = LoggerFactory.getLogger(Launcher.class);
	
    public static void main( String[] args )
    {
    	logger.info("Weibo Bbs Task start.");
    	
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-*.xml");
        SyncForumThreadTask task = (SyncForumThreadTask) context.getBean("syncForumThread");
    	task.work();

    	logger.info("Weibo Bbs Task end..");
    }
}
