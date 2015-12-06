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
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.dc.data.Bonus;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.OddsBase;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.dc.data.ZCResult;
import com.xhcms.lottery.dc.persist.dao.FBMatchDao;

/**
 * @author xulang
 */
public class FBMatchDaoImpl extends DaoImpl<FBMatchPO> implements FBMatchDao {
	private static final long serialVersionUID = 7090799754810646378L;

	public FBMatchDaoImpl() {
		super(FBMatchPO.class);
	}
	
	@Override
	public void batchInsertMatch(final Collection<Match> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = "insert into fb_match(id,cn_code,code,name,league_name,offtime,home_team_name,guest_team_name,playing_time,entrust_deadline,concede_points,status,long_league_name) values(?,?,?,?,?,?,?,?,?,?,?,1,?)";
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				int i = 0;
				for (Match d : data) {
					// fb_match
					stmt.setLong(1, d.getMatchId());
					stmt.setString(2, d.getCnCode());
					stmt.setString(3, d.getCode());
					stmt.setString(4, d.getName());
					stmt.setString(5, d.getLeague());
					stmt.setTimestamp(6, new Timestamp(d.getOfftime().getTime()));
					stmt.setString(7, d.getHomeTeam());
					stmt.setString(8, d.getGuestTeam());
					stmt.setTimestamp(9, new Timestamp(d.getPlayingTime().getTime()));
					stmt.setTimestamp(10, new Timestamp(d.getEntrustDeadline().getTime()));
					stmt.setInt(11, d.getConcedePoints());
					stmt.setString(12, d.getLongLeagueName());
					
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
				String matchSql = "update fb_match set offtime=?,playing_time=?,entrust_deadline=?,concede_points=? where id=?";
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				int i = 0;
				for (Match d : data) {
					stmt.setTimestamp(1, new java.sql.Timestamp(d.getOfftime().getTime()));
					stmt.setTimestamp(2, new java.sql.Timestamp(d.getPlayingTime().getTime()));
					stmt.setTimestamp(3, new java.sql.Timestamp(d.getEntrustDeadline().getTime()));
					stmt.setInt(4, d.getConcedePoints());
					stmt.setLong(5, d.getMatchId());
					
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
	public void batchInsertMatchPlay(final Collection<ZCOdds> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String playSql = "insert into fb_match_play(id,match_id,play_id,options,odds) values(?, ?, ?, ?, ?)";
				PreparedStatement stmt = conn.prepareStatement(playSql);
				int i = 0;
				for (OddsBase d : data) {
					// fb_match_play
					stmt.setString(1, d.getId());
					stmt.setLong(2, d.getMatchId());
					stmt.setString(3, d.getPlayId());
					stmt.setString(4, d.getOptions());
					stmt.setString(5, d.getOdds());
					
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
	
//	@Override
//	public void batchUpdateMatchConcede(final Collection<ZCOdds> data) {
//		this.session().doWork(new Work() {
//			@Override
//			public void execute(Connection conn) throws SQLException {
//				String matchSql = "update fb_match set concede_points=? where id=?";
//				
//				PreparedStatement mstmt = conn.prepareStatement(matchSql);
//				
//				int i = 0;
//				for (ZCOdds d : data) {
//					mstmt.setInt(1, d.getConcedePoints());
//					mstmt.setLong(2, d.getMatchId());
//					
//					mstmt.addBatch();
//
//					if (++i % 20 == 0) {
//						mstmt.executeBatch();
//					}
//				}
//				if (i % 20 != 0) {
//					mstmt.executeBatch();
//				}
//			}
//		});
//	}
	
	@Override
	public void batchUpdateMatchPlay(final Collection<ZCOdds> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement("update fb_match_play set options=?, odds=?, status=?  where id=? and status !=2");
				
				int i = 0;
				for (OddsBase d : data) {
					pstmt.setString(1, d.getOptions());
					pstmt.setString(2, d.getOdds());
					pstmt.setInt(3, d.getStatus());
					pstmt.setString(4, d.getId());
					
					pstmt.addBatch();

					if (++i % 20 == 0) {
						pstmt.executeBatch();
					}
				}
				if (i % 20 != 0) {
					pstmt.executeBatch();
				}
			}
		});
	}

	@Override
	public void batchUpdateMatchResult(final List<ZCResult> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String sql = "";
				sql = "update fb_match set status=?, concede_points=? where id=?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				int i = 0;
				for (int size = data.size(); i < size;) {
					ZCResult d = data.get(i);
					
					stmt.setInt(1, d.getStatus());
					stmt.setInt(2, d.getConcedePoints());
					stmt.setLong(3, d.getMatchId());

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
	public List<Long> filterExist(Collection<Long> ids) {
		return createCriteria().add(Restrictions.in("id", ids)).setProjection(Projections.property("id")).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> filterExistMatchPlay(Collection<String> ids) {
		return createSQLQuery("SELECT id FROM fb_match_play WHERE id IN (:ids)").setParameterList("ids", ids).list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<FBMatchPO> findOnSale(Date from){
	    return createQuery("from FBMatchPO where status=1 and entrustDeadline>=?").setDate(0, from).list();
	}
	
	@Override
	public void batchUpdateResultBonus(final List<Bonus> bs) {
		this.session().doWork(new Work(){
			@Override
			public void execute(Connection conn) throws SQLException {
				String sql = "update fb_match_play set win_bonus=?, win_option=? where id=?";
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
}
