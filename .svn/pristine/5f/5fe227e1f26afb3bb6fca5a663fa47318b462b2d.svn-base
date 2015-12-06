package com.xhcms.lottery.dc.task;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.dc.core.DataStore;

/**
 * 更新期的lc_status（大V彩定义的状态，决定哪一期是在售状态）任务
 * 
 * @author u9
 *
 */
public class UpdateIssueinfoLCStatusTask extends Job{
	
	
	
	
	
	// 沿用星汇的异步保存数据机制。
	private DataStore dataStore;
	

	private String storeDataName;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	

	@Override
	protected void execute() throws Exception {
		if (StringUtils.isBlank(storeDataName)){
			throw new IllegalStateException("storeDataName不能为空!");
		}
		logger.debug("开始执行更新期lc_status任务...");
		storeData();
		
		
	}


	

	
	private void storeData() {
		List<IssueInfo> emptyList =new ArrayList<IssueInfo>();
		emptyList.add(new IssueInfo());// 由于列表必须有数据才能执行，这里增加一条空数据
		this.dataStore.put(storeDataName, emptyList);

	}




	

	public DataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	public String getStoreDataName() {
		return storeDataName;
	}

	public void setStoreDataName(String storeDataName) {
		this.storeDataName = storeDataName;
	}


	
	

}
