package com.xhcms.lottery.dc.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import com.unison.lottery.mc.uni.parser.util.ZMStatusMapper;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.data.BDResult;
import com.xhcms.lottery.dc.data.Bonus;
import com.xhcms.lottery.dc.persist.dao.BJDCMatchDao;

/**
 * 北京单场赛事dao
 *
 */
public class BJDCMatchDaoImpl extends DaoImpl<BJDCMatchPO> implements BJDCMatchDao {

	private static final long serialVersionUID = 1L;

	public BJDCMatchDaoImpl(){
		super(BJDCMatchPO.class);
	}
	
	@Override
	public void batchInsertMatch(final Collection<BDMatch> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = 
						"insert into bjdc_match(id,cn_code," +
						"issue_number,code,name,league_name," +
						"offtime,home_team_name,guest_team_name," +
						"playing_time,entrust_deadline,status,created_time,update_time) " +
						"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				int i = 0;
				Timestamp ttp = new Timestamp(Calendar.getInstance().getTime().getTime());
				for (BDMatch d : data) {
					stmt.setLong(1, d.getMatchId());
					stmt.setString(2, d.getCnCode());
					stmt.setString(3, d.getIssueNumber());
					stmt.setString(4, d.getCode());
					stmt.setString(5, d.getName());
					stmt.setString(6, d.getLeague());
					stmt.setTimestamp(7, new Timestamp(d.getOfftime().getTime()));
					stmt.setString(8, d.getHomeTeam());
					stmt.setString(9, d.getGuestTeam());
					stmt.setTimestamp(10, new Timestamp(d.getPlayingTime().getTime()));
					stmt.setTimestamp(11, new Timestamp(d.getEntrustDeadline().getTime()));
					stmt.setInt(12, ZMStatusMapper.convertBjdcStatus(d.getStatus()));
					stmt.setTimestamp(13, ttp);
					stmt.setTimestamp(14, ttp);
					
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
	public void batchUpdateMatch(final Collection<BDMatch> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				String matchSql = "update bjdc_match set offtime=?," +
						"playing_time=?,entrust_deadline=?,status=?,update_time=? where id=?";
				PreparedStatement stmt = conn.prepareStatement(matchSql);
				int i = 0;
				Timestamp ttp = new Timestamp(Calendar.getInstance().getTime().getTime());
				for (BDMatch d : data) {
					stmt.setTimestamp(1, new java.sql.Timestamp(d.getOfftime().getTime()));
					stmt.setTimestamp(2, new java.sql.Timestamp(d.getPlayingTime().getTime()));
					stmt.setTimestamp(3, new java.sql.Timestamp(d.getEntrustDeadline().getTime()));
					stmt.setInt(4, ZMStatusMapper.convertBjdcStatus(d.getStatus()));
					stmt.setTimestamp(5, ttp);
					stmt.setLong(6, d.getMatchId());
					
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

	@SuppressWarnings("unchecked")
	@Override
	public List<BJDCMatchPO> findMatchsGreatThanEntrust(Date from) {
		return createQuery("from BJDCMatchPO where status=1 and entrustDeadline>=?").setDate(0, from).list();
	}
	
	@Override
	public void batchUpdateMatchResult(final List<BDResult> data) {
		this.session().doWork(new Work(){
			@Override
			public void execute(Connection conn) throws SQLException{
				PreparedStatement ps=conn.prepareStatement("update bjdc_match set half_score=?,score=?, status=? where id=?");
				int i = 0;
				for(int size=data.size();i<size;){
					BDResult bd=data.get(i);
					ps.setString(1, bd.getHalfScore());
					ps.setString(2, bd.getScore());
					ps.setInt(3, bd.getStatus());
					ps.setLong(4, bd.getMatchId());
					
					ps.addBatch();
					if(++i%20==0){
						ps.executeBatch();
					}
				}
				if (i % 20 != 0) {
					ps.executeBatch();
				}
			}
		});
	}
	
	@Override
	public void batchUpdateResultBonus(final List<Bonus> bs) {
		this.session().doWork(new Work(){
			@Override
			public void execute(Connection conn) throws SQLException {
				String sql = "update bjdc_match_play set final_odds=?, win_option=? where id=?";
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
	@Override
	public void batchUpdateNoSpResultBonus(final List<Bonus> bs) {
		this.session().doWork(new Work(){
			@Override
			public void execute(Connection conn) throws SQLException {
				String sql = "update bjdc_match_play set win_option=? where id=?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				int i = 0;
				for(Bonus b : bs) {
				
					stmt.setString(1, b.getOption());
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
	public List<String> getIssueNumber(String playId) {
		
		 List<String> issueList=new ArrayList<String>();
		 //select issue_number from lt_issueinfo where play_id='91_BJDC_SPF  96_BJDC_SF' AND lottery_id='BJDC'  AND  status in(2,3,4) and stop_time>period_add(now(),-15)
		 List list=createSQLQuery("select issue_number from lt_issueinfo where play_id=?  and lottery_id='BJDC' and status in(1,2,3,4) and stop_time>period_add(now(),-15)")
				 .setParameter(0, playId).list();
		 if(list!=null&&list.size()>0){
			 for(int i=0;i<list.size();i++){
				 
				 issueList.add(list.get(i).toString());
			 }
			 
		 }
		 
		 return issueList;
	}
	
}