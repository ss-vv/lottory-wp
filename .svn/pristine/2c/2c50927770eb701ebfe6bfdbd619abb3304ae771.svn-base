package com.unison.lottery.weibo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.IndexMatchService;
import com.xhcms.commons.job.Job;

/**
 * @Description: 索引足球赛事 
 * @author 江浩翔   
 * @date 2014-1-21 下午1:56:15 
 * @version V1.0
 */
public class IndexMatchTask extends Job{
	private static final Logger logger = LoggerFactory
			.getLogger(IndexMatchTask.class);
	
	@Autowired
	private IndexMatchService indexMatchService;
	
	public IndexMatchTask() {
		logger.debug("execute task : index football match on sale...");
	}
	
	@Override
	protected void execute() {
		try{
			indexMatchService.buildJCZQMatchIndex();
			indexMatchService.buildJCLQMatchIndex();
			indexMatchService.buildCTZCMatchIndex();
			indexMatchService.buildBJDCMatchIndex();
		} catch (Exception e) {
			logger.error("建立赛事索引失败！",e);
		}
	}
}
