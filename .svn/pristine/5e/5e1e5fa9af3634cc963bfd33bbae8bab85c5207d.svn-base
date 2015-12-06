/**
 * 
 */
package com.xhcms.lottery.dc.task.fetch.zm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.mc.uni.ZMClient;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.dc.core.DataStore;
import com.xhcms.lottery.dc.task.ticket.OpenSaleIntercessor;

/**
 * 从中民新接口获取“赛程”、“赛果”、“赔率”的基础任务类。<br/>
 * 需要配置"storeDataName、dataStore、zmClient"属性，"openSaleIntercessor"属性可选。
 * 
 * @author Yang Bo
 */
public abstract class ZMFetchTask extends Job {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	// 开售时间处理器
	private OpenSaleIntercessor openSaleIntercessor;
	// 沿用星汇的异步保存数据机制。
	private DataStore dataStore;
	// 接口访问客户端
	private ZMClient zmClient;

	protected String storeDataName;
	
	/**
	 * 异步保存数据。
	 * @param name 数据名称
	 * @param data 被保存的数据对象列表
	 */
	protected void storeData(String name, List<?> data) {
		dataStore.put(name, data);
	}

	public DataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	public OpenSaleIntercessor getOpenSaleIntercessor() {
		return openSaleIntercessor;
	}

	public void setOpenSaleIntercessor(OpenSaleIntercessor openSaleIntercessor) {
		this.openSaleIntercessor = openSaleIntercessor;
	}

	public ZMClient getZmClient() {
		return zmClient;
	}

	public void setZmClient(ZMClient zmClient) {
		this.zmClient = zmClient;
	}

	public String getStoreDataName() {
		return storeDataName;
	}

	public void setStoreDataName(String storeDataName) {
		this.storeDataName = storeDataName;
	}
}
