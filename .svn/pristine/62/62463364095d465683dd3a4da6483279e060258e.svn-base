package com.xhcms.lottery.dc.persist.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.jdbc.Work;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.MatchSupportPlay;
import com.xhcms.lottery.commons.persist.entity.MatchSupportPlayPO;
import com.xhcms.lottery.dc.persist.dao.MatchSupportPlayDao;
import com.xhcms.lottery.lang.LotteryId;

public class MatchSupportPlayDaoImpl extends DaoImpl<MatchSupportPlayPO> implements MatchSupportPlayDao {

	private static final long serialVersionUID = 1L;

	public MatchSupportPlayDaoImpl() {
		super(MatchSupportPlayPO.class);
	}

	@Override
	public void save(final List<MatchSupportPlay> supportPlays) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				Timestamp now = new Timestamp(System.currentTimeMillis());
				
				PreparedStatement stmt = conn.prepareStatement("insert into lt_match_support_play(lottery_id, match_id, play_id, created_time) values(?,?,?,?)");
				int i = 0;
				for (int size = supportPlays.size(); i < size;) {
					MatchSupportPlay supportPlay = supportPlays.get(i);
					stmt.setString(1, supportPlay.getLotteryId());
					stmt.setLong(2, supportPlay.getMatchId());
					stmt.setString(3, supportPlay.getPlayId());
					stmt.setTimestamp(4, now);
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
	public List<BigInteger> findCurrSupportMatchId(LotteryId lotteryId) {
		Date date = com.xhcms.lottery.utils.DateUtils.yesterdaybeginTime();
		SQLQuery sqlQuery = createSQLQuery("select distinct match_id from lt_match_support_play where lottery_id = ? and created_time >= ?");
		sqlQuery.setParameter(0, lotteryId.name());
		sqlQuery.setParameter(1, date);
		return sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findCurrSupportMatchPlay(LotteryId lotteryId) {
		List<java.util.Date> list = com.xhcms.lottery.utils.DateUtils.getDayBeginAndEnd();
		
		SQLQuery sqlQuery = createSQLQuery("select distinct concat(match_id, play_id) from lt_match_support_play where lottery_id = ? and created_time between ? and ?");
		sqlQuery.setParameter(0, lotteryId.name());
		sqlQuery.setParameter(1, list.get(0));
		sqlQuery.setParameter(2, list.get(1));
		return sqlQuery.list();
	}

	@Override
	public void removeNotSupportPlay(final long matchId,final String playId) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("delete from lt_match_support_play where match_id=? and play_id=?");
				stmt.setLong(1, matchId);
				stmt.setString(2, playId);
				stmt.execute();
			}
		});
	}
}
