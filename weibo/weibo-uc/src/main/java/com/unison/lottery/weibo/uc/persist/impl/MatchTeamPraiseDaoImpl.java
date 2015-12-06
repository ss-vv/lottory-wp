package com.unison.lottery.weibo.uc.persist.impl;

import java.util.Set;
import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.uc.persist.MatchTeamPraiseDao;

@Repository
public class MatchTeamPraiseDaoImpl extends BaseDaoImpl<Object> implements MatchTeamPraiseDao {

	@Override
	public Long addPraise(long qtMatchId, long teamId,
			long weiboUserId, String lottery) {
		String key = Keys.teamPraise(qtMatchId, teamId, lottery);
		return zadd(System.currentTimeMillis(), key, String.valueOf(weiboUserId));
	}

	@Override
	public Set<String> findTeamPraisedWeiboUser(long qtMatchId, long teamId,
			String lottery, String min, String max) {
		String key = Keys.teamPraise(qtMatchId, teamId, lottery);
		return zrangeByScore(key, min, max);
	}

	@Override
	public long countTeamPraiseWeiboUser(long qtMatchId, long teamId, String lottery) {
		return zcard(Keys.teamPraise(qtMatchId, teamId, lottery));
	}

	@Override
	public Set<String> findTeamPraisedWeiboUser(long qtMatchId, long teamId,
			String lottery, int count) {
		String key = Keys.teamPraise(qtMatchId, teamId, lottery);
		return zrevrange(key, 0, count - 1);
	}

	@Override
	public long delPraise(long qtMatchId, long teamId, long weiboUserId,
			String lottery) {
		String key = Keys.teamPraise(qtMatchId, teamId, lottery);
		return zrem(key, String.valueOf(weiboUserId));
	}
}
