package com.xhcms.lottery.dc.persist.persister;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.LCResult;
import com.xhcms.lottery.dc.persist.service.BBMatchService;

public class LCMatchResultPersisterImpl implements Persister<LCResult> {
	Logger log = LoggerFactory.getLogger(getClass());
	private BBMatchService bbMatchService;

	public void setBbMatchService(BBMatchService bbMatchService) {
		this.bbMatchService = bbMatchService;
	}

	@Override
	public void persist(List<LCResult> data) {
		log.info("竞彩篮球：1.开始更新比赛各玩法中奖选项；2.不使用平台提供的比分数据!");
		bbMatchService.batchUpdateMatchResult(data);
	}
}
