package com.xhcms.lottery.dc.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.dc.persist.dao.CTFBMatchDao;

/**
 * @author Wang Lei
 */
public class CTFBMatchDaoImpl extends DaoImpl<CTFBMatchPO> implements CTFBMatchDao {
	private static final long serialVersionUID = 7090799754810646378L;

	public CTFBMatchDaoImpl() {
		super(CTFBMatchPO.class);
	}
	
	@Override
	public void batchInsertMatch(final Collection<CTFBMatch> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = "insert into ctfb_match(" +
				"id,issue_number,play_id,match_id,league_name,home_team_name,guest_team_name," +
				"status,playing_time,offtime,entrust_deadline,half_score,score,win_option) values(" +
				"?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				int i = 0;
				for (CTFBMatch d : data) {
					// ctfb_match
					stmt.setString(1, d.getId());
					stmt.setString(2, d.getIssueNumber());
					stmt.setString(3, d.getPlayId());
					stmt.setLong(4, d.getMatchId());
					stmt.setString(5, d.getLeagueName());
					stmt.setString(6, d.getHomeTeamName());
					stmt.setString(7, d.getGuestTeamName());
					stmt.setInt(8, d.getStatus());
					stmt.setTimestamp(9, new Timestamp(d.getOfftime().getTime()));
					stmt.setTimestamp(10, new Timestamp(d.getPlayingTime().getTime()));
					stmt.setTimestamp(11, new Timestamp(d.getEntrustDeadline().getTime()));
					stmt.setString(12, d.getHalfScore()==null?"":d.getHalfScore());
					stmt.setString(13, d.getScore()==null?"":d.getScore());
					stmt.setString(14, d.getWinOption());
					
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
	
	@Override
	public void batchUpdateMatch(final Collection<CTFBMatch> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = "update ctfb_match set " +
						"match_id = ? ," +
						"league_name = ? ," +
						"home_team_name = ? ," +
						"guest_team_name = ? ," +
						"status = ? ," +
						"playing_time = ? ," +
						"offtime = ? ," +
						"entrust_deadline = ? ," +
						"win_option = ? " +
				"where id=?";
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				int i = 0;
				for (CTFBMatch d : data) {
					stmt.setLong(1, d.getMatchId());
					stmt.setString(2, d.getLeagueName());
					stmt.setString(3, d.getHomeTeamName());
					stmt.setString(4, d.getGuestTeamName());
					stmt.setInt(5, d.getStatus());
					stmt.setTimestamp(6, new Timestamp(d.getOfftime().getTime()));
					stmt.setTimestamp(7, new Timestamp(d.getPlayingTime().getTime()));
					stmt.setTimestamp(8, new Timestamp(d.getEntrustDeadline().getTime()));
					stmt.setString(9, d.getWinOption());
					stmt.setString(10, d.getId());
					
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
	public List<String> filterExist(Collection<String> ids) {
		return createCriteria().add(Restrictions.in("id", ids)).setProjection(Projections.property("id")).list();
	}
}
