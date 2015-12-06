package com.unison.lottery.pm.count;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.service.CountService;
import com.xhcms.commons.job.Job;

/**
 * 
 * @author yonglizhu
 *
 */
public class CountTask extends Job {
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CountService countService;
	
	@Override
	protected void execute() throws Exception {
		log.info("统计数据开始 ");
		countService.count();
		log.info("统计数据结束");
	}

}
