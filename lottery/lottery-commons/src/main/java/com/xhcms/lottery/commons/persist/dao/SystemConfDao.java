package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.lottery.commons.persist.entity.SystemConfPO;

public interface SystemConfDao {

	List<SystemConfPO> loadAll();

	Integer findIntValueByKey(String key);
	
	String findValueByKey(String key);
	/**
	 * 更新配置
	 * @param key 键
	 * @param value 值
	 */
	void updateIntegerValueByKey(String key,String value);
}
