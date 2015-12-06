package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.data.service.store.persist.dao.BBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBTeamPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-10
 * @version 1.0
 */
@Repository
@SuppressWarnings("unchecked")
public class BBTeamDaoImpl extends DaoImpl<BBTeamPO> implements BBTeamDao {

	private static final long serialVersionUID = -5167797685294484328L;

	public BBTeamDaoImpl() {
		super(BBTeamPO.class);
	}

	/**
	 * 返回id集合
	 * 
	 * @return
	 */
	@Override
	public List<Long> teamIdList() {
		List<Long> teamIdList = createQuery(
				"select f.teamId from BBTeamPO as f ").list();
		return teamIdList;
	}

	/**
	 * 批量新增
	 */
	@Override
	public void batchInsert(final Collection<BBTeamPO> data) {
		this.session().doWork(new Work() {

			@Override
			public void execute(Connection conn) throws SQLException {
				StringBuffer buf = new StringBuffer();
				buf.append("insert into md_bb_team(")
						.append("teamId, leagueId, shortName, chineseName, traditionalName, englishName,")
						.append("logoUrl, teamUrl, locationId, matchAddrId, city, gymnasium, capacity, joinYear,")
						.append("winNumber, drillmaster, createTime")
						.append(") ").append("values(")
						.append("?, ?, ?, ?, ?, ?, ")
						.append("?, ?, ?, ?, ?, ?, ?, ?, ")
						.append("?, ?, now()").append(")");

				PreparedStatement stmt = conn.prepareStatement(buf.toString());
				int i = 0;
				for (BBTeamPO team : data) {
					stmt.setLong(1, team.getTeamId());
					stmt.setLong(1, team.getTeamId());
					stmt.setLong(2, team.getLeagueId());
					stmt.setString(3, team.getShortName());
					stmt.setString(4, team.getChineseName());
					stmt.setString(5, team.getTraditionalName());
					stmt.setString(6, team.getEnglishName());
					stmt.setString(7, team.getLogoUrl());
					stmt.setString(8, team.getTeamUrl());
					stmt.setLong(9, team.getLocationId());
					stmt.setLong(10, team.getMatchAddrID());
					stmt.setString(11, team.getCity());
					stmt.setString(12, team.getGymnasium());
					stmt.setInt(13, team.getCapacity());
					stmt.setInt(14, team.getJoinYear());
					stmt.setInt(15, team.getWinNumber());
					stmt.setString(16, team.getDrillmaster());
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
	public void batchUpdate(final Collection<BBTeamPO> bbTeamList) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				StringBuffer buf = new StringBuffer();
				buf.append("update md_bb_team ")
				.append("leagueId = ?, chineseName = ?, traditionalName = ?, ")
				.append("englishName = ?, drillmaster = ? ")
				.append("where teamId = ? ");
				
				PreparedStatement stmt = conn.prepareStatement(buf.toString());
				int i = 0;
				for (BBTeamPO team : bbTeamList) {
					stmt.setLong(1, team.getLeagueId());
					stmt.setString(2, team.getChineseName());
					stmt.setString(3, team.getTraditionalName());
					stmt.setString(4, team.getEnglishName());
					stmt.setString(5, team.getDrillmaster());
					stmt.setLong(6, team.getTeamId());
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
	public BBTeamPO findByLeagueIdAndChineseName(long leagueId, String teamName){
		Criteria criteria = createCriteria();
		if (StringUtils.isBlank(teamName)) {
			return null;
		} else {
			criteria.add(Restrictions.and(Restrictions.eq("leagueId", leagueId), 
					Restrictions.eq("chineseName", teamName)));
			Object o = criteria.uniqueResult();
			if(null != o){
				return (BBTeamPO)o;
			} else {
				return null;
			}
		}
	}
	
	@Override
	public long findIdByChineseName(String teamName) {
		Criteria criteria = createCriteria();
		if (StringUtils.isBlank(teamName)) {
			return -1;
		} else {
			criteria.add(Restrictions.eq("chineseName", teamName));
			List<BBTeamPO> BBTeamPOs = criteria.list();
			BBTeamPO BBTeamPO = BBTeamPOs.size() > 0 ? BBTeamPOs.get(0) : null;
			if (null == BBTeamPO) {
				return -1;
			} else {
				return BBTeamPO.getTeamId();
			}
		}
	}

	@Override
	public long findIdByTraditionalName(String teamName) {
		Criteria criteria = createCriteria();
		if (StringUtils.isBlank(teamName)) {
			return -1;
		} else {
			criteria.add(Restrictions.eq("traditionalName", teamName));
			List<BBTeamPO> BBTeamPOs = criteria.list();
			BBTeamPO BBTeamPO = BBTeamPOs.size() > 0 ? BBTeamPOs.get(0) : null;
			if (null == BBTeamPO) {
				return -1;
			} else {
				return BBTeamPO.getTeamId();
			}
		}
	}

	@Override
	public BBTeamPO findByTeamId(Long teamId) {
		return (BBTeamPO) createQuery("from BBTeamPO where teamId=?").setLong(
				0, teamId).uniqueResult();
	}

	@Override
	public BBTeamPO findByChineseName(String teamName) {
		Criteria criteria = createCriteria();
		if (StringUtils.isBlank(teamName)) {
			return null;
		} else {
			criteria.add(Restrictions.eq("chineseName", teamName));
			Object o = criteria.uniqueResult();
			if(null != o){
				return (BBTeamPO)o;
			} else {
				return null;
			}
		}
	}
}
