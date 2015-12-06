package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.SeasonInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * @author 彭保星
 *
 * @since 2014年11月3日下午12:26:53
 */
@Repository
public class SeasonInfoDataStoreDaoImpl extends
		DaoImpl<FbLeagueSeasonBasePO> implements SeasonInfoDataStoreDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3148902684568520809L;

	public SeasonInfoDataStoreDaoImpl() {
		super(FbLeagueSeasonBasePO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<FbLeagueSeasonBasePO> queryAllSeasonMessageSubLeague(
			Integer source,Integer leagueType) {
		// TODO Auto-generated method stub
		//and locate(?,a.seasonName)>0 如果查询当前赛季数据需要加上此段代码
		return createQuery(
				"select a from FbLeagueSeasonBasePO a,LeagueInfoPO b " + "where a.leagueId=b.leagueId and a.source=b.source and a.source=? "
						+ "  and a.isSubLeague!=? and b.type=? and locate(?,a.seasonName)>0  order by a.seasonId asc").setInteger(0, source)
				.setInteger(1, 0).setInteger(2, leagueType).setString(3, DateFormateUtil.getNowYear()).list();
	}

	@Override
	@Transactional
	public void storeFbSubLeague(List<SeasonModel> subSeasonModels,Integer leagueType) {
		if (subSeasonModels != null && !subSeasonModels.isEmpty()) {
			Map<String, List<FbLeagueSeasonBasePO>> insertAndUpdateSeaonModel = makeInsertAndUpdateSubLeagueSeaon(subSeasonModels,leagueType);
			insertSeasonPO(insertAndUpdateSeaonModel.get("1"));
			upadteSeasonPO(insertAndUpdateSeaonModel.get("2"));
			updateSubLeaguePO(insertAndUpdateSeaonModel.get("3"));
		}
	}

	private void updateSubLeaguePO(final List<FbLeagueSeasonBasePO> list) {
		// TODO Auto-generated method stub
		if (!list.isEmpty()) {
			session().doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					PreparedStatement ps = connection
							.prepareStatement("update md_fb_league_season_base "
									+ "set totalRound=?,subLeagueName=?,isHaveSubLeague=?,isSubLeague=?,subTranditionName=?,updateTime=now() where subLeagueId=? and leagueId=? and seasonName=? and source=1");
					for (FbLeagueSeasonBasePO seasonModel : list) {
						ps.setInt(1, seasonModel.getTotalRound()==null||seasonModel.getTotalRound()==0?1:seasonModel.getTotalRound());
						
						ps.setString(2, seasonModel.getSubLeagueName());
						ps.setInt(3, seasonModel.getIsHaveSubLeague());
						ps.setInt(4, seasonModel.getIsSubLeague());
						ps.setString(5, seasonModel.getSubTranditionName());
						ps.setString(6, seasonModel.getSubLeagueId());
						ps.setString(7, seasonModel.getLeagueId());
						ps.setString(8, seasonModel.getSeasonName());
						ps.addBatch();
					}
					ps.executeBatch();
				}
			});
		}
	}

	private void upadteSeasonPO(final List<FbLeagueSeasonBasePO> list) {
		if (!list.isEmpty()) {
			session().doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					PreparedStatement ps = connection
							.prepareStatement("update md_fb_league_season_base "
									+ "set totalRound=?,subLeagueId=?,subLeagueName=?,isHaveSubLeague=?,isSubLeague=?,subTranditionName=?,updateTime=now() where seasonId=?");
					for (FbLeagueSeasonBasePO seasonModel : list) {
						ps.setInt(1, seasonModel.getTotalRound()==null||seasonModel.getTotalRound()==0?1:seasonModel.getTotalRound());
						ps.setString(2, seasonModel.getSubLeagueId());
						ps.setString(3, seasonModel.getSubLeagueName());
						ps.setInt(4, seasonModel.getIsHaveSubLeague());
						ps.setInt(5, seasonModel.getIsSubLeague());
						ps.setString(6, seasonModel.getSubTranditionName());
						ps.setInt(7, seasonModel.getSeasonId());
						ps.addBatch();
					}
					ps.executeBatch();
				}
			});
		}
	}

	private void insertSeasonPO(final List<FbLeagueSeasonBasePO> list) {
		if (!list.isEmpty()) {
			session().doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					PreparedStatement ps = connection
							.prepareStatement("insert into md_fb_league_season_base("
									+ "totalRound,subLeagueId,subLeagueName,isHaveSubLeague,isSubLeague,subTranditionName,source,processStatus,crawlerCount,seasonName,leagueId,createTime)values("
									+ "?,?,?,?,?,?,?,?,?,?,?,now())");
					for (FbLeagueSeasonBasePO seasonModel : list) {
						ps.setInt(1, seasonModel.getTotalRound()==null||seasonModel.getTotalRound()==0?1:seasonModel.getTotalRound());
						ps.setString(2, seasonModel.getSubLeagueId());
						ps.setString(3, seasonModel.getSubLeagueName());
						ps.setInt(4, seasonModel.getIsHaveSubLeague());
						ps.setInt(5, seasonModel.getIsSubLeague());
						ps.setString(6, seasonModel.getSubTranditionName());
						ps.setInt(7, seasonModel.getSource());
						ps.setInt(8, seasonModel.getProcessStatus());
						ps.setInt(9, 0);
						ps.setString(10, seasonModel.getSeasonName());
						ps.setString(11, seasonModel.getLeagueId());
						ps.addBatch();
					}
					ps.executeBatch();
				}
			});
		}
	}

	private Map<String, List<FbLeagueSeasonBasePO>> makeInsertAndUpdateSubLeagueSeaon(
			List<SeasonModel> subSeasonModels,Integer leagueType) {
		// TODO Auto-generated method stub
		Map<String, List<FbLeagueSeasonBasePO>> fbLeagueSeasonBasePOs = new HashMap<>();
		List<FbLeagueSeasonBasePO> insert = new ArrayList<>();
		List<FbLeagueSeasonBasePO> update = new ArrayList<>(); //更新联赛信息
		List<FbLeagueSeasonBasePO> updateSubLeague = new ArrayList<>(); //更新子联赛信息
		// 查询所有的子联赛或杯赛分组赛
		List<String> subSeasonLeagueModels = createQuery(
				"select concat(a.subLeagueId,a.leagueId,a.seasonName) from FbLeagueSeasonBasePO a,LeagueInfoPO b where a.source=1 and a.leagueId=b.leagueId and b.type=? and a.isSubLeague=0")
				.setInteger(0, leagueType).list();

		for (SeasonModel seasonModel : subSeasonModels) {
			FbLeagueSeasonBasePO fbLeagueSeasonBasePO = new FbLeagueSeasonBasePO();
			try {
				BeanUtils.copyProperties(fbLeagueSeasonBasePO, seasonModel);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (seasonModel.getIsSubLeague()==0
					&& !subSeasonLeagueModels.contains(seasonModel
							.getSubLeagueId()+seasonModel.getLeagueId()+seasonModel.getSeasonName())) { // 是子联赛或分组赛并且没有入库
				insert.add(fbLeagueSeasonBasePO);
			} else {
				fbLeagueSeasonBasePO.setSeasonId(seasonModel.getSeasonId());
				if(seasonModel.getIsSubLeague()==0){
					updateSubLeague.add(fbLeagueSeasonBasePO);
				}else {
					update.add(fbLeagueSeasonBasePO);
				}
			}
		}
		fbLeagueSeasonBasePOs.put("1", insert);
		fbLeagueSeasonBasePOs.put("2", update);
		fbLeagueSeasonBasePOs.put("3", updateSubLeague);
		return fbLeagueSeasonBasePOs;
	}

	@Override
	@Transactional(readOnly=true)
	public List<FbLeagueSeasonBasePO> queryAllCupGroupMess(Integer source,Integer cupType) {
		// TODO Auto-generated method stub
		return createQuery(
				"select a from FbLeagueSeasonBasePO a,LeagueInfoPO b " + "where a.leagueId=b.leagueId and a.source=b.source and a.source=? "
						+ "  and a.isSubLeague=? and b.type=? order by a.seasonId asc").setInteger(0,source)
				.setInteger(1, 0).setInteger(2, cupType).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<FbLeagueSeasonBasePO> queryAllCupGroupMessToCraw(
			Integer source, Integer cupType) {
		// TODO Auto-generated method stub
		//and locate(?,a.seasonName)>0 .setString(3, DateFormateUtil.getNowYear())
		return createQuery(
				"select a from FbLeagueSeasonBasePO a,LeagueInfoPO b " + "where a.leagueId=b.leagueId and a.seasonId>=13586 and a.source=b.source and a.source=? "
						+ "  and a.isSubLeague=? and b.type=? and locate(?,a.seasonName)>0 order by a.seasonId asc").setInteger(0,source)
				.setInteger(1, 0).setInteger(2, cupType).setString(3, DateFormateUtil.getNowYear()).list();
	}

	@Override
	@Transactional
	public String queryNowSeasonNameByLeagueId(String leagueId) {
		// TODO Auto-generated method stub
		return (String) createQuery("select max(seasonName) from FbLeagueSeasonBasePO where leagueId=?").setString(0, leagueId).uniqueResult();
	}

}
