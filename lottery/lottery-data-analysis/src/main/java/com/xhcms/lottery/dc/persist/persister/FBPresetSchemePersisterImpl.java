package com.xhcms.lottery.dc.persist.persister;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.persist.service.PresetAwardService;
import com.xhcms.lottery.commons.util.MatchResults;
import com.xhcms.lottery.dc.core.Persister;

/**
 * 竞彩足球预兑奖方案持久化服务
 * 
 * @author lei.li@davcai.com
 */
public class FBPresetSchemePersisterImpl implements Persister<Map<Long, List<Long>>> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PresetAwardService presetAwardService;
	
	@Override
	public void persist(List<Map<Long, List<Long>>> data) {
		logger.info("收到竞彩足球预兑奖方案请求:data size={}", data.size());
		if(null != data && data.size() > 0) {
			for(int i=0; i<data.size(); i++) {
					Map<Long, List<Long>> map = data.get(i);
					Set<Entry<Long, List<Long>>> set = map.entrySet();
					Iterator<Entry<Long, List<Long>>> iter = set.iterator();
					while(iter.hasNext()) {
						try {
							Entry<Long, List<Long>> entry = iter.next();
							Long schemeId = entry.getKey();
							List<Long> matchIds = entry.getValue();
							
							List<Long> list = new ArrayList<Long>();
							for(Object o : matchIds) {
								list.add(((BigInteger)o).longValue());
							}
							
							MatchResults matchResults = presetAwardService.computeFBMatchResults(list);
							boolean result = presetAwardService.presetPrizes(schemeId, matchResults);
							logger.info("方案Id={},预兑奖返回结果={}", schemeId, result);
						} catch (Exception e) {
							logger.error("预兑奖错误,数据：{}, 错误信息：{}", data.get(i), e);
						}
					}
			}
		}
	}
}