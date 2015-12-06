package com.xhcms.lottery.commons.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.MatchPlatform;
import com.xhcms.lottery.commons.persist.dao.MatchPlatformDao;
import com.xhcms.lottery.commons.persist.entity.MatchPlatformPO;

@Service
public class MatchPlatformDaoImpl extends DaoImpl<MatchPlatformPO> implements MatchPlatformDao {

	private static final long serialVersionUID = 1L;
	private Query query;

	public MatchPlatformDaoImpl() {
		super(MatchPlatformPO.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MatchPlatformPO getMatchPlatformPO(String lotteryId, String platformId, Long matchId) {
		Query query = createQuery("from MatchPlatformPO where platform_id=? and lottery_id=? and match_id=?");
		query.setString(0, platformId);
		query.setString(1, lotteryId);
		query.setLong(2, matchId);
		List<MatchPlatformPO> l = query.list();
		return l.size() > 0 ? l.get(0):null;
	}
	
	@Override
	public void save(final List<MatchPlatform> matchPlatforms) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				Timestamp now = new Timestamp(System.currentTimeMillis());
				PreparedStatement stmt = conn.prepareStatement("insert into lt_match_platform(platform_id,lottery_id, match_id, p_match_id, created_time) values(?,?,?,?,?)");
				int i = 0;
				for (int size = matchPlatforms.size(); i < size;) {
					MatchPlatform m = matchPlatforms.get(i);
					stmt.setString(1, m.getPlatformId());
					stmt.setString(2, m.getLotteryId());
					stmt.setLong(3, m.getMatchId());
					stmt.setLong(4, m.getPlatformMatchId());
					stmt.setTimestamp(5, now);
					stmt.addBatch();
					if (++i % 20 == 0) {
						stmt.executeBatch();
					}
				}
				if (i % 20 != 0) {
					stmt.executeBatch();
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchPlatformPO> findJcOfficial(String lotteryId, Collection<Long> idSet,
			String lotteryPlatformId) {
		query = createQuery("from MatchPlatformPO mp where platformId=:platformId and lotteryId=:lotteryId and matchId in (:matchIds)");
		query.setString("platformId", lotteryPlatformId);
		query.setString("lotteryId", lotteryId);
		query.setParameterList("matchIds", idSet);
		return query.list();
	}
}
