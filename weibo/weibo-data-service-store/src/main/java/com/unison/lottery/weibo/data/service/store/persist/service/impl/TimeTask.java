package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.util.Date;

/**
 * 按照日期执行的任务接口。
 * 
 * @author Yang Bo
 */
public interface TimeTask {

	void runOnDay(Date date);

}
