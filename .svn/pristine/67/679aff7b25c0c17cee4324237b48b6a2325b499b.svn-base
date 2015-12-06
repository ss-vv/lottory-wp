package com.xhcms.lottery.dc.task.preset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.dc.core.DataStore;
import com.xhcms.lottery.lang.LotteryId;


/**
 * 竞彩足球方案预兑奖的后台任务
 * @author lei.li@davcai.com
 */
public class FBPresetSchemeTask extends Job {

	private static final Logger logger = LoggerFactory.getLogger(FBPresetSchemeTask.class);
	
	private DataStore dataStore;
	
	private String storeDataName;
	
	@Autowired
	private BetSchemeBaseService betSchemeBaseService;
	
	@Override
	protected void execute() {
		try {
			logger.debug("FB Preset Scheme Task execute ...");
			Map<Long, List<Long>> map = betSchemeBaseService.queryPresetSchemeList(LotteryId.JCZQ.name());
			if(null != map && map.size() > 0) {
				logger.info("find {} size fb preset scheme!schemeIds={}", 
						new Object[]{map.size(), map.keySet()});
				
				List<Map<Long, List<Long>>> list = new ArrayList<Map<Long,List<Long>>>();
				list.add(map);
				dataStore.put(storeDataName, list);
			}
		} catch (Exception e) {
			logger.error("executed {}, error={}", FBPresetSchemeTask.class, e);
		}
	}

	public String getStoreDataName() {
		return storeDataName;
	}

	public void setStoreDataName(String storeDataName) {
		this.storeDataName = storeDataName;
	}

	public DataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}
}