package com.xhcms.lottery.dc.persist.persister;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.persist.service.BJDCMatchService;
import com.xhcms.lottery.lang.PlayType;

/**
 * 北京单场胜负过关 赛事更新/保存
 */
public class BJDCMatchSFGGPersisterImpl implements Persister<BDMatch> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BJDCMatchService bJDCMatchService;

	@Override
	public void persist(List<BDMatch> data) {
		PlayType playType = PlayType.BJDC_SF;
		logger.info("开始执行北京单场赛事数据入库任务:playType={}", playType);
		if (null != data && !data.isEmpty()) {
			bJDCMatchService.batchUpdateMatch(data, playType);
		} else {
			logger.info("开始执行北京单场赛事数据入库任务；没有需处理的数据");
		}
	}
}