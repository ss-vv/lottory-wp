package com.unison.lottery.pm.winlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.service.ShowFollowWinListService;
import com.xhcms.commons.job.Job;

public class ShowWinListTask extends Job {
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ShowFollowWinListService showFollowWinListService;
	
	@Override
	protected void execute() throws Exception {		
		log.info("count show scheme win list start");
		showFollowWinListService.countShowWinListAndSave();
		log.info("count show scheme win list end");

	}


}
