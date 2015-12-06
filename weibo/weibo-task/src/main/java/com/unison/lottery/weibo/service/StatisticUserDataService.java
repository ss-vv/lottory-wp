package com.unison.lottery.weibo.service;

/**
 * 统计微博用户微博相关数据服务
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-5-27 下午5:23:17
 */
public interface StatisticUserDataService {
	void statisticRealWeiboData();
	void statisticRecWeiboData();
	void statisticBonusTop10();
	void statisticGuoDanLvTop10();
	void statisticActiveUser();
	void statisticAllUserLast7DaysWin();
}
