package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.data.service.store.persist.dao.FBLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeaguePO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * 联赛杯赛信息dao
 * @author Wang Lei
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class FBLeagueDaoImpl extends DaoImpl<FBLeaguePO> implements FBLeagueDao {

	private static final long serialVersionUID = -7314686034001490363L;
	
	public FBLeagueDaoImpl(){
		super(FBLeaguePO.class);
	}
	
	@Override
	public List<Object[]> findLeagueIdsByShortNames(Set<String> names){
		return createQuery("select l.chineseName,l.leagueId from  FBLeaguePO as l where l.chineseName in (:chineseName)")
				.setParameterList("chineseName", names).list();
	}
	
	/**
	 * 返回id集合
	 * @return
	 */
	@Override
	public List<Long> ids(){
		List<Long> ids=createQuery("select f.leagueId from FBLeaguePO as f ").list();
         return ids;
	}
	
	/**
	 * 批量新增
	 */
	@Override
	public void batchInsert(final Collection<FBLeaguePO> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("insert into md_fb_league(leagueId,colour,chineseName,chineseNameAll,traditionalName," +
						"traditionalNameAll,englishName,englishNameAll,type,sumRound,currRound,currMatchSeason,countryID,country,areaID,createTime) " +
						"values(?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)");
				int i = 0;
				for (FBLeaguePO d : data) {
					stmt.setLong(1, d.getLeagueId());
					stmt.setString(2, d.getColour());
					stmt.setString(3, d.getChineseName());
					stmt.setString(4, d.getChineseNameAll());
					stmt.setString(5, d.getTraditionalName());
					stmt.setString(6, d.getTraditionalNameAll());
					stmt.setString(7, d.getEnglishName());
					stmt.setString(8, d.getEnglishNameAll());
					stmt.setInt(9, d.getType());
					stmt.setInt(10, d.getSumRound());
					stmt.setInt(11, d.getCurrRound());
					stmt.setString(12, d.getCurrMatchSeason());
					stmt.setInt(13, d.getCountryID());
					stmt.setString(14, d.getCountry());
					stmt.setInt(15, d.getAreaID());
					stmt.setDate(16, new Date(System.currentTimeMillis()));
					stmt.addBatch();
					if (++i % 10000 == 0) {
						stmt.executeBatch();
					}
				}
				stmt.executeBatch();
			}
		});
	}
	
	/**
	 * 批量修改
	 */
	@Override
	public void batchUpdate(final Collection<FBLeaguePO> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("update md_fb_league set colour = ? , chineseName = ?,chineseNameAll = ?,traditionalName = ?," +
						"traditionalNameAll = ?,englishName = ?,englishNameAll = ?,type = ?,sumRound = ?,currRound = ?,currMatchSeason = ?,countryID = ?," +
						"country = ? , areaID = ? ,updateTime= ? where leagueId = ?");
				int i = 0;
				for (FBLeaguePO d : data) {
					stmt.setString(1, d.getColour());
					stmt.setString(2, d.getChineseName());
					stmt.setString(3, d.getChineseNameAll());
					stmt.setString(4, d.getTraditionalName());
					stmt.setString(5, d.getTraditionalNameAll());
					stmt.setString(6, d.getEnglishName());
					stmt.setString(7, d.getEnglishNameAll());
					stmt.setInt(8, d.getType());
					stmt.setInt(9, d.getSumRound());
					stmt.setInt(10, d.getCurrRound());
					stmt.setString(11, d.getCurrMatchSeason());
					stmt.setInt(12, d.getCountryID());
					stmt.setString(13, d.getCountry());
					stmt.setInt(14, d.getAreaID());
					stmt.setDate(15, new Date(System.currentTimeMillis()));
					stmt.setLong(16, d.getLeagueId());
					stmt.addBatch();
					if (++i % 10000 == 0) {
						stmt.executeBatch();
					}
				}
				stmt.executeBatch();
			}
		});
	}

	@Override
	public List<FBLeaguePO> listAllLeagues() {
		return createQuery("from FBLeaguePO order by id asc").list();
	}
}
