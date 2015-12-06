package com.unison.lottery.weibo.common.nosql.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Tuple;
import com.unison.lottery.weibo.common.nosql.LotteryDao;
import com.unison.lottery.weibo.data.UserSetLottery;
import com.unison.lottery.weibo.utils.LotteryUtil;

@Repository
public class UserLotterySetDaoImpl extends BaseDaoImpl<String> implements
		LotteryDao {

	@Override
	public void saveLotterySet(String uid, String lottery, double score) {
		zadd(score, Keys.userLotterySetKey(uid), lottery);
	}

	@Override
	public List<UserSetLottery> loadLotterySetForUser(String uid, long start, long end) {
		LinkedHashSet<Tuple> linked = zrangeWithScores(
				Keys.userLotterySetKey(uid), start, end);
		List<UserSetLottery> list = new ArrayList<UserSetLottery>();
		if (null != linked) {
			Iterator<Tuple> iter = linked.iterator();
			while (iter.hasNext()) {
				Tuple tuple = (Tuple) iter.next();
				UserSetLottery lotterySet = new UserSetLottery();
				lotterySet.setName(tuple.getElement());
				lotterySet.setScore(tuple.getScore());
				lotterySet.setViewName(LotteryUtil.getName(tuple.getElement()));
				list.add(lotterySet);
			}
		}
		return list;
	}

	@Override
	public void removeLotterySetForUser(String uid) {
		delete(Keys.userLotterySetKey(uid));
	}
}