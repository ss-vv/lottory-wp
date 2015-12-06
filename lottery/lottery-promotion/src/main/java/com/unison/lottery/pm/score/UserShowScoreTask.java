package com.unison.lottery.pm.score;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.service.UserShowScoreService;
import com.xhcms.commons.job.Job;

public class UserShowScoreTask extends Job {
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserShowScoreService userShowScoreService;
	
	@Override
	protected void execute() throws Exception {		
		log.info("count user score start");
		userShowScoreService.countUserShowScore();
		log.info("count user score end");

	}


}
