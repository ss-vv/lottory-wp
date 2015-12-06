package com.xhcms.lottery.dc.persist.persister;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.BDOdds;
import com.xhcms.lottery.dc.persist.service.BJDCMatchService;

/**
 * @desc
 * @createTime 2014-7-28
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class BJDCOddsPersisterImpl implements Persister<BDOdds> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BJDCMatchService bJDCMatchService;
	
	@Override
	public void persist(List<BDOdds> data) {
		logger.info("开始执行北京单场赔率数据入库任务:type={}", getType(data));
		if (null != data && !data.isEmpty()) {
			bJDCMatchService.batchUpdateOdds(data);
		} else {
			logger.info("开始执行北京单场赔率数据入库任务；没有需处理的数据");
		}
	}
	
	private String getType(List<BDOdds> data) {
		if(null != data && data.size() > 0) {
			return data.get(0).getPlayId();
		}
		return null;
	}
}