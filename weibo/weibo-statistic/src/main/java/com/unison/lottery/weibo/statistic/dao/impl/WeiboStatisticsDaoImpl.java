package com.unison.lottery.weibo.statistic.dao.impl;

import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.statistic.dao.WeiboStatisticsDao;
import com.unison.lottery.weibo.statistic.data.OfficialUserData;

@Repository
public class WeiboStatisticsDaoImpl extends BaseDaoImpl<OfficialUserData> implements WeiboStatisticsDao {

}
