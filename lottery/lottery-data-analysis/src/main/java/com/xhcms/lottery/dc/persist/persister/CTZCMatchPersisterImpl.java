package com.xhcms.lottery.dc.persist.persister;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.persist.service.CTFBMatchService;

/**
 * 传统足球赛事更新/保存
 * @author Wang Lei
 *
 */
public class CTZCMatchPersisterImpl implements Persister<CTFBMatch> {
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CTFBMatchService cTFBMatchService;

	@Override
	public void persist(List<CTFBMatch> data) {
		logger.debug("开始执行传统足球赛事数据入库任务...");
		if(null!=data&&!data.isEmpty()){
			logger.debug("有需处理数据");
			cTFBMatchService.batchSaveOrUpdateMatch(data);
		}
		else{
			logger.debug("没有需处理的数据");
		}
	}

}
