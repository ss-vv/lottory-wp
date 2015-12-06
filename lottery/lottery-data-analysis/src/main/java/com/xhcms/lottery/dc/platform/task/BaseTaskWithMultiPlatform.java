package com.xhcms.lottery.dc.platform.task;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.ILotteryPlatformClient;
import com.davcai.lottery.platform.client.chooser.ILotteryPlatformChooser;
import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.davcai.lottery.platform.util.ILotteryPlatformPriorityService;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.dc.core.DataStore;
import com.xhcms.lottery.lang.LotteryPlatformId;

/**
 * 多平台任务调度抽象类
 * @author haoxiang.jiang@davcai.com
 * @date 2015年1月9日 下午6:10:09
 */
public abstract class BaseTaskWithMultiPlatform extends Job {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DataStore dataStore;
	protected String storeDataName;
	@Autowired
	private ILotteryPlatformChooser lotteryPlatformChooser;
	@Autowired
	private ILotteryPlatformPriorityService lotteryPlatformPriorityService;
	private String currentPlatformId;
	/**
	 * 根据接口名称获取提供接口的平台ID
	 * @param lotteryId TODO
	 * @param lotteryInterfaceName
	 * @return
	 */
	private String getPlatformId(LotteryInterfaceName name, String lotteryId){
		LotteryPlatformPriority lotteryPlatformPriority = lotteryPlatformPriorityService.findOneByInterfaceName(name, lotteryId);
		if(null!=lotteryPlatformPriority){
			return lotteryPlatformPriority.getLotteryPlatformId();
		}
		return LotteryPlatformId.AN_RUI_ZHI_YING;
	}
	protected String getCurrentPlatformId() {
		return currentPlatformId;
	}
	protected ILotteryPlatformClient getClient(LotteryInterfaceName name, String lotteryId) {
		currentPlatformId = getPlatformId(name, lotteryId);
		logger.info("当前平台ID：{}",currentPlatformId);
		return lotteryPlatformChooser.chooseOnePlatformClient(currentPlatformId, name);
	}
	/**
	 * 异步保存数据。
	 * @param name 数据名称
	 * @param data 被保存的数据对象列表
	 */
	protected void storeData(String name, List<?> data) {
		dataStore.put(name, data);
	}
	public String getStoreDataName() {
		return storeDataName;
	}
	public void setStoreDataName(String storeDataName) {
		this.storeDataName = storeDataName;
	}
	public ILotteryPlatformPriorityService getLotteryPlatformPriorityService() {
		return lotteryPlatformPriorityService;
	}
	public void setLotteryPlatformPriorityService(
			ILotteryPlatformPriorityService lotteryPlatformPriorityService) {
		this.lotteryPlatformPriorityService = lotteryPlatformPriorityService;
	}
}
