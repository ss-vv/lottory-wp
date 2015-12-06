package com.xhcms.lottery.dc.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.dc.data.Bonus;
import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.data.LCResult;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.Score;
import com.xhcms.lottery.dc.persist.dao.BBMatchDao;

/**
 * @author langhsu
 *
 */
public class BBMatchDaoImpl extends DaoImpl<BBMatchPO> implements BBMatchDao {
	private static final long serialVersionUID = -5624775115665612318L;

	public BBMatchDaoImpl() {
		super(BBMatchPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> filterExist(Collection<Long> ids) {
		return createCriteria().add(Restrictions.in("id", ids)).setProjection(Projections.property("id")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> filterExistMatchPlay(Collection<String> ids) {
		return createSQLQuery("SELECT id FROM bb_match_play WHERE id IN (:ids)").setParameterList("ids", ids).list();
	}

	@Override
	public void batchUpdateMatchResult(final List<LCResult> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				//"update bb_match set final_score=?,final_score_preset=?,status=5 where id=?"
				String sql = "update bb_match set status=5 where id=?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				int i = 0;
				for (int size = data.size(); i < size;) {
					LCResult d = data.get(i);
					
					stmt.setLong(1, d.getMatchId());

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
	public void batchInsertMatch(final Collection<Match> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = "insert into bb_match(id,cn_code,code,name,league_name,home_team_name,guest_team_name,offtime,playing_time,entrust_deadline,status,long_league_name) values(?,?,?,?,?,?,?,?,?,?,1,?)";
				
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				
				int i = 0;
				for (Match d : data) {
					stmt.setLong(1, d.getMatchId());
					stmt.setString(2, d.getCnCode());
					stmt.setString(3, d.getCode());
					stmt.setString(4, d.getName());
					stmt.setString(5, d.getLeague());
					stmt.setString(6, d.getHomeTeam());
					stmt.setString(7, d.getGuestTeam());
					stmt.setTimestamp(8, new Timestamp(d.getOfftime().getTime()));
					stmt.setTimestamp(9, new Timestamp(d.getPlayingTime().getTime()));
					stmt.setTimestamp(10, new Timestamp(d.getEntrustDeadline().getTime()));
					stmt.setString(11, d.getLongLeagueName());
					
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
	public void batchUpdateMatch(final Collection<Match> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = "update bb_match set offtime=?,playing_time=?,entrust_deadline=? where id=?";
				
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				
				int i = 0;
				for (Match d : data) {
					// bb_match
					stmt.setTimestamp(1, new java.sql.Timestamp(d.getOfftime().getTime()));
					stmt.setTimestamp(2, new java.sql.Timestamp(d.getPlayingTime().getTime()));
					stmt.setTimestamp(3, new java.sql.Timestamp(d.getEntrustDeadline().getTime()));
					stmt.setLong(4, d.getMatchId());
					
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
	public void batchUpdateMatchPlay(final Collection<LCOdds> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("update bb_match_play set options=?, odds=?, default_score=? , status=? where id=? and status!=2");
				
				int i = 0;
				for (LCOdds d : data) {
					stmt.setString(1, d.getOptions());
					stmt.setString(2, d.getOdds());
					stmt.setFloat(3, d.getDefaultScore());
					stmt.setInt(4, d.getStatus());
					stmt.setString(5, d.getId());
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
	public void batchInsertMatchPlay(final Collection<LCOdds> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String playSql = "insert into bb_match_play(id,match_id,play_id,options,odds,default_score) values(?, ?, ?, ?, ?,?)";
				
				PreparedStatement stmt = conn.prepareStatement(playSql);
				
				int i = 0;
				for (LCOdds d : data) {
					//bb_match_play
					stmt.setString(1, d.getId());
					stmt.setLong(2, d.getMatchId());
					stmt.setString(3, d.getPlayId());
					stmt.setString(4, d.getOptions());
					stmt.setString(5, d.getOdds());
					stmt.setFloat(6, d.getDefaultScore());
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
	public void batchUpdateResultBonus(final List<Bonus> bs) {
		this.session().doWork(new Work(){
			@Override
			public void execute(Connection conn) throws SQLException {
				String sql = "update bb_match_play set win_bonus=?, win_option=? where id=?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				int i = 0;
				for(Bonus b : bs) {
					stmt.setDouble(1, b.getBonus());
					stmt.setString(2, b.getOption());
					stmt.setString(3, b.getId());
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
    public List<BBMatchPO> findOnSale(Date from) {
        return createQuery("from BBMatchPO where status=1 and entrustDeadline>=?").setDate(0, from).list();
    }

	@Override
	public void batchUpdateScore(final List<Score> scores) {
		this.session().doWork(new Work(){
			@Override
			public void execute(Connection conn) throws SQLException {
				String sql = "update bb_match_play set default_score=? where id=?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				int i = 0;
				for(Score b : scores) {
					stmt.setFloat(1, b.getScore());
					stmt.setString(2, b.getId());
					
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
	public void batchInsertScore(final List<Score> scores) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String sql = "insert into bb_match_play(id, match_id, play_id, odds, default_score) values(?, ?, ?, ?, ?)";
				PreparedStatement stmt = conn.prepareStatement(sql);
				int i = 0;
				for(Score b : scores) {
					
					stmt.setString(1, b.getId());
					stmt.setLong(2, b.getMatchId());
					stmt.setString(3, b.getPlayId());
					stmt.setString(4, "");
					stmt.setFloat(5, b.getScore());
					
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
}
