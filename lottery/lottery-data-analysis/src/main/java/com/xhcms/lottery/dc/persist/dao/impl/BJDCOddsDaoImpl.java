package com.xhcms.lottery.dc.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.data.BDOdds;
import com.xhcms.lottery.dc.persist.dao.BJDCOddsDao;
import com.xhcms.lottery.lang.PlayType;

public class BJDCOddsDaoImpl extends DaoImpl<BJDCMatchPlayPO> implements BJDCOddsDao {

	private static final long serialVersionUID = 1L;

	public BJDCOddsDaoImpl(){
		super(BJDCMatchPlayPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> filterExist(Collection<String> ids) {
		return createCriteria().add(Restrictions.in("id", ids)).
				setProjection(Projections.property("id")).list();
	}

	@Override
	public void batchInsertOdds(final Collection<BDOdds> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = 
						"insert into bjdc_match_play(id,issue_number," +
						"match_id,play_id,options,odds,created_time,update_time) " +
						"values(?,?,?,?,?,?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				int i = 0;
				Timestamp ttp = new Timestamp(Calendar.getInstance().getTime().getTime());
				for (BDOdds d : data) {
					stmt.setString(1, d.getId());
					stmt.setString(2, d.getIssueNumber());
					stmt.setLong(3, d.getMatchId());
					stmt.setString(4, d.getPlayId());
					stmt.setString(5, d.getOptions());
					stmt.setString(6, d.getOdds());
					stmt.setTimestamp(7, ttp);
					stmt.setTimestamp(8, ttp);
					
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
	public void batchUpdateOdds(final Collection<BDOdds> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = "update bjdc_match_play set odds=?,update_time=? where id=?";
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				int i = 0;
				Timestamp ttp = new Timestamp(Calendar.getInstance().getTime().getTime());
				for (BDOdds d : data) {
					stmt.setString(1, d.getOdds());
					stmt.setTimestamp(2, ttp);
					stmt.setString(3, d.getId());
					
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
	public void batchUpdateConcedePonitsByLottery(final Collection<BDMatch> data, final PlayType playType) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = "update bjdc_match_play set concede_points=?,update_time=? " +
						"where id = ? and concede_points is null";
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				int i = 0;
				Timestamp ttp = new Timestamp(Calendar.getInstance().getTime().getTime());
				for (BDMatch d : data) {
					stmt.setBigDecimal(1, d.getConcedeBalls());
					stmt.setTimestamp(2, ttp);
					String keySPFId = d.getMatchId() + playType.getShortPlayStr();
					stmt.setString(3, keySPFId);
					
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