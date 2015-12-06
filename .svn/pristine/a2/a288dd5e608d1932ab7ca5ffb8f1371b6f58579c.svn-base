package com.unison.lottery.weibo.common.nosql.impl;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.common.nosql.RecomendUserDao;

@Repository
public class RecomendUserDaoImpl extends BaseDaoImpl<String> implements RecomendUserDao {

	@Override
	public Set<String> getRecWeiboUserId() {
		return zrangeByScore(Keys.getWeiboRecomendUser(), "0", "" + new Date().getTime());
	}

	@Override
	public Set<String> getLotteryUserId() {
		return zrange(Keys.getWeiboLotteryUser(), 0, -1);
	}
}
