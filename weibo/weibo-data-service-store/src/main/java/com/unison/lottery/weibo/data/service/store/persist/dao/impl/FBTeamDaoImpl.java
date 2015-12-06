package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.data.service.store.persist.dao.FBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBTeamPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * 足球球队信息dao
 * @author Wang Lei
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class FBTeamDaoImpl extends DaoImpl<FBTeamPO> implements FBTeamDao {

	private static final long serialVersionUID = -5167797685294484328L;

	public FBTeamDaoImpl(){
		super(FBTeamPO.class);
	}
	
	/**
	 * 返回id集合
	 * @return
	 */
	@Override
	public List<Long> ids(){
		List<Long> ids=createQuery("select f.teamId from FBTeamPO as f ").list();
         return ids;
	}
	
	/**
	 * 批量新增
	 */
	@Override
	public void batchInsert(final Collection<FBTeamPO> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement(
						"insert into md_fb_team(teamId,leagueId,chineseName,traditionalName,englishName,createTime,foundDate,area,gym,capacity,flag,address,url,master) " +
						"values(?, ?, ?, ?,  ?,now(),?,?,?,?,?,?,?,?)");
				int i = 0;
				for (FBTeamPO d : data) {
					stmt.setLong(1, d.getTeamId());
					stmt.setLong(2, d.getLeagueId());
					stmt.setString(3, d.getChineseName());
					stmt.setString(4, d.getTraditionalName());
					stmt.setString(5, d.getEnglishName());
					stmt.setString(6, d.getFoundDate());
					stmt.setString(7, d.getArea());
					stmt.setString(8, d.getGym());
					stmt.setString(9, d.getCapacity());
					stmt.setString(10, d.getFlag());
					stmt.setString(11,d.getAddress());
					stmt.setString(12, d.getUrl());
					stmt.setString(13, d.getMaster());
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
	public void batchUpdate(final Collection<FBTeamPO> data) {
		this.session().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("update md_fb_team set leagueId = ? , chineseName = ?,traditionalName = ?," +
						"englishName = ?,foundDate=?,area=?,gym=?,capacity=?,flag=?,address=?,url=?,master=?,updateTime=? where teamId = ?");
				int i = 0;
				for (FBTeamPO d : data) {
					stmt.setLong(1, d.getLeagueId());
					stmt.setString(2, d.getChineseName());
					stmt.setString(3, d.getTraditionalName());
					stmt.setString(4, d.getEnglishName());
					stmt.setString(5, d.getFoundDate());
					stmt.setString(6, d.getArea());
					stmt.setString(7, d.getGym());
					stmt.setString(8, d.getCapacity());
					stmt.setString(9, d.getFlag());
					stmt.setString(10,d.getAddress());
					stmt.setString(11, d.getUrl());
					stmt.setString(12, d.getMaster());
					stmt.setDate(13, new Date(System.currentTimeMillis()));
					stmt.setLong(14, d.getTeamId());
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
	public long findIdByChineseName(String teamName) {
		Criteria criteria = createCriteria();
		if(StringUtils.isBlank(teamName)){
			return -1;
		} else {
			criteria.add(Restrictions.eq("chineseName", teamName));
			List<FBTeamPO> fbTeamPOs = criteria.list();
			FBTeamPO fbTeamPO = fbTeamPOs.size() > 0 ? fbTeamPOs.get(0):null;
			if(null == fbTeamPO){
				return -1;
			} else {
				return fbTeamPO.getTeamId();
			}
		}
	}

	@Override
	public long findIdByTraditionalName(String teamName) {
		Criteria criteria = createCriteria();
		if(StringUtils.isBlank(teamName)){
			return -1;
		} else {
			criteria.add(Restrictions.eq("traditionalName", teamName));
			List<FBTeamPO> fbTeamPOs = criteria.list();
			FBTeamPO fbTeamPO = fbTeamPOs.size() > 0 ? fbTeamPOs.get(0):null;
			if(null == fbTeamPO){
				return -1;
			} else {
				return fbTeamPO.getTeamId();
			}
		}
	}

	@Override
	public FBTeamPO findByTeamId(Long teamId) {
		return (FBTeamPO) createQuery("from FBTeamPO where teamId=?").setLong(0, teamId).uniqueResult();
	}
}
