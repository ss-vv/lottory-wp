package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LqLeagueInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueSeasonPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BbLeagueInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.service.store.persist.dao.impl.BasicDaoImpl;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BbLeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;

/**
 * @author 彭保星
 *
 * @since 2014年11月21日下午3:15:12
 */
@Repository
public class LqLeagueInfoDataStoreDaoImpl extends
		BasicDaoImpl<BbLeagueInfoPO> implements LqLeagueInfoDataStoreDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9116405277204713951L;

	public LqLeagueInfoDataStoreDaoImpl() {
		super(BbLeagueInfoPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void storeBbLeague(List<BbLeagueInfoModel> leagueInfoModels) {
		if (!leagueInfoModels.isEmpty()) {
			// 获取联赛id
			List<String> leagueId = createQuery(
					"select leagueId from BasketBallLeagueInfoPO").list();
			Map<String, Object> bMap = new HashMap<String, Object>();
			makeInsertAndUpdateMess(bMap, leagueId, leagueInfoModels);
			List<BbLeagueInfoModel> insertLeague = (List<BbLeagueInfoModel>) bMap
					.get("1");
			List<BbLeagueInfoModel> updateLeague = (List<BbLeagueInfoModel>) bMap
					.get("2");
			List<BasketBallLeagueSeasonModel> seasonModels = (List<BasketBallLeagueSeasonModel>) bMap
					.get("3");
			insertBatch(insertLeague, seasonModels);
			updateBatch(updateLeague);

		}
	}

	private void updateBatch(final List<BbLeagueInfoModel> list) {
		if (list != null && !list.isEmpty()) {
			session().doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					PreparedStatement ps = connection
							.prepareStatement("update md_bb_league_base set"
									+ " chineseName=?,sclassKind=?,countryId=?,country=?,"
									+ "updateTime=now(),shortName=? where leagueId=? and source=? ");
					int i = 0;
					for (BbLeagueInfoModel leagueInfoPO : list) {
						ps.setString(1, leagueInfoPO.getChineseName());
						if (leagueInfoPO.getSclassKind() != null) {
							ps.setInt(2, leagueInfoPO.getSclassKind());
						} else {
							ps.setInt(2, 1); // 默认为联赛
						}
						ps.setString(3, leagueInfoPO.getCountryId());
						ps.setString(4, leagueInfoPO.getCountry());
						ps.setString(5, leagueInfoPO.getShortName());
						ps.setString(6, leagueInfoPO.getLeagueId());
						ps.setInt(7, leagueInfoPO.getSource());
						ps.addBatch();
						if (++i >= 10000) {
							ps.executeBatch();
							i = 0;
						}
					}
					ps.executeBatch();
				}
			});
		}
	}

	private void insertBatch(final List<BbLeagueInfoModel> insertLeague,
			final List<BasketBallLeagueSeasonModel> seasonModels) {
		Session session = session();
		if (!insertLeague.isEmpty()) {
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = connection
							.prepareStatement("insert into md_bb_league_base("
									+ " chineseName,sclassKind,countryId,country,"
									+ "createTime,leagueId,source,processStatus,shortName)values(?,?,?,?,now(),?,?,?,?)");
					int i = 0;
					for (BbLeagueInfoModel leagueInfoPO : insertLeague) {
						ps.setString(1, leagueInfoPO.getChineseName());
						if (leagueInfoPO.getSclassKind() != null) {
							ps.setInt(2, leagueInfoPO.getSclassKind());
						} else {
							ps.setInt(2, 1); // 默认为联赛
						}
						ps.setString(3, leagueInfoPO.getCountryId());
						ps.setString(4, leagueInfoPO.getCountry());
						ps.setString(5, leagueInfoPO.getLeagueId());
						ps.setInt(6, leagueInfoPO.getSource());
						ps.setInt(7, leagueInfoPO.getProcessStatus());
						ps.setString(8, leagueInfoPO.getShortName());
						ps.addBatch();
						if (++i >= 10000) {
							i = 0;
							ps.executeBatch();
						}
					}
					ps.executeBatch();

				}
			});
		}
		if (!seasonModels.isEmpty()) {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps1 = connection
							.prepareStatement("insert into"
									+ " md_bb_sjlsgx_base(leagueId,seasonName,source,processStatus,crawlerCount,isSubLeague,createTime)values"
									+ "(?,?,?,?,?,?,now())");
					int i = 0;
					for (BasketBallLeagueSeasonModel seasonBasePO : seasonModels) {
						ps1.setString(1, seasonBasePO.getLeagueId());
						ps1.setString(2, seasonBasePO.getSeasonName());
						ps1.setInt(3, seasonBasePO.getSource());
						ps1.setInt(4, seasonBasePO.getProcessStatus());
						ps1.setInt(5, 0); // 初始化已抓取的轮数为0
						ps1.setInt(6, 1); // 1非子联赛
						ps1.addBatch();
						if (++i > 10000) {
							i = 0;
							ps1.executeBatch();
						}
					}
					ps1.executeBatch();
				}
			});
		}
		session.flush();
		session.clear();
	}

	private void makeInsertAndUpdateMess(Map<String, Object> bMap,
			List<String> leagueId, List<BbLeagueInfoModel> leagueInfoModels) {
		List<BbLeagueInfoModel> insertLeague = new ArrayList<>();
		List<BbLeagueInfoModel> updateLeague = new ArrayList<>();
		// 获取所有不是子联赛的所有赛季
		List<String> leagueIdAndSeason = createQuery(
				"select concat(leagueId,seasonName) from BasketBallLeagueSeasonPO where isSubLeague=? ")
				.setInteger(0, 1).list();
		List<BasketBallLeagueSeasonModel> seasonModels = new ArrayList<>();
		for (BbLeagueInfoModel leagueInfoModel : leagueInfoModels) {
			if (leagueId.contains(leagueInfoModel.getLeagueId())) {
				updateLeague.add(leagueInfoModel);
			} else {
				insertLeague.add(leagueInfoModel);
			}
			List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels = leagueInfoModel
					.getBallLeagueSeasonModels();

			if (ballLeagueSeasonModels != null) {
				for (BasketBallLeagueSeasonModel ballLeagueSeasonModel : ballLeagueSeasonModels) {
					if (ballLeagueSeasonModel.getSeasonName() != null
							&& ballLeagueSeasonModel.getLeagueId() != null) {
						if (!leagueIdAndSeason.contains(ballLeagueSeasonModel
								.getLeagueId()
								+ ballLeagueSeasonModel.getSeasonName())) {
							seasonModels.add(ballLeagueSeasonModel);
						}
					}
				}
			}
		}
		bMap.put("1", insertLeague);
		bMap.put("2", updateLeague);
		bMap.put("3", seasonModels);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<BasketBallLeagueSeasonPO> queryAllLeagueSeasonNotSub(
			int source, int leagueType) {
		return createQuery(
				" select a from BasketBallLeagueSeasonPO a,BbLeagueInfoPO b where a.leagueId=b.leagueId and b.sclassKind=? and a.source=? "
						+ "and b.source=? and a.isSubLeague=? and locate(?,a.seasonName)>0 order by a.seasonId asc")
				.setInteger(0, leagueType).setInteger(1, source)
				.setInteger(2, source).setInteger(3, 1).setString(4, DateFormateUtil.getNowYear().substring(2)).list();
	}

	@Override
	@Transactional
	public void storeSubleague(
			List<BasketBallLeagueSeasonModel> basketBallLeagueSeasonModels) {
		if (!basketBallLeagueSeasonModels.isEmpty()) {
			Map<String, List<BasketBallLeagueSeasonModel>> ballMap = makeInsertAndUpdateMess(basketBallLeagueSeasonModels);
			final List<BasketBallLeagueSeasonModel> insert = ballMap.get("1");
			if (!insert.isEmpty()) {
				Session session = session();
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						PreparedStatement ps = connection
								.prepareStatement("insert md_bb_sjlsgx_base("
										+ "leagueId,seasonName,subLeagueId,subLeagueName,source,isSubLeague,kind,processStatus,createTime)values"
										+ "(?,?,?,?,?,?,?,?,now())");
						for (BasketBallLeagueSeasonModel ballLeagueSeasonModel : insert) {
							ps.setString(1, ballLeagueSeasonModel.getLeagueId());
							ps.setString(2,
									ballLeagueSeasonModel.getSeasonName());
							ps.setString(3,
									ballLeagueSeasonModel.getSubLeagueId());
							ps.setString(4,
									ballLeagueSeasonModel.getSubLeagueName());
							ps.setInt(5, ballLeagueSeasonModel.getSource());
							ps.setInt(6, 0);
							ps.setString(7, ballLeagueSeasonModel.getKind());
							ps.setInt(8,
									ballLeagueSeasonModel.getProcessStatus());
							ps.addBatch();
						}
						ps.executeBatch();
					}
				});
				session.flush();
				session.clear();
			}
		}
	}

	private Map<String, List<BasketBallLeagueSeasonModel>> makeInsertAndUpdateMess(
			List<BasketBallLeagueSeasonModel> basketBallLeagueSeasonModels) {
		Map<String, List<BasketBallLeagueSeasonModel>> ballMap = new HashMap<>();
		List<BasketBallLeagueSeasonModel> insert = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<String> index = createQuery(
				"select concat(leagueId,seasonName,subLeagueId,kind) from BasketBallLeagueSeasonPO where source=? and isSubLeague=0")
				.setInteger(0, 1).list();
		for (BasketBallLeagueSeasonModel ballLeagueSeasonModel : basketBallLeagueSeasonModels) {
			String s1 = ballLeagueSeasonModel.getLeagueId()
					+ ballLeagueSeasonModel.getSeasonName()
					+ ballLeagueSeasonModel.getSubLeagueId()
					+ ballLeagueSeasonModel.getKind();
			if (!index.contains(s1)) {
				insert.add(ballLeagueSeasonModel);
			}
		}
		ballMap.put("1", insert);
		return ballMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<BasketBallLeagueSeasonPO> queryAllSubLeagueNotCrawler(
			int source, int isSubLeague, int leagueType) {
		//.setString(4, DateFormateUtil.getNowYear().substring(2))
		//and locate(?,a.seasonName)>0
		return createQuery(
				"select a from BasketBallLeagueSeasonPO a,BbLeagueInfoPO b where a.leagueId=b.leagueId and a.source=? and b.source=? and a.isSubLeague=? and b.sclassKind=? and locate(?,a.seasonName)>0   order by a.seasonId asc")
				.setInteger(0, source).setInteger(1, source)
				.setInteger(2, isSubLeague).setInteger(3, leagueType).setString(4, DateFormateUtil.getNowYear().substring(2)).list();
	}

	@Override
	@Transactional
	public void storeBasketSubLeague(
			List<BasketBallLeagueSeasonModel> seasonModels2, Integer cupType) {
		if (!seasonModels2.isEmpty()) {
			Map<String, List<BasketBallLeagueSeasonModel>> bMap = new HashMap<String, List<BasketBallLeagueSeasonModel>>();
			makeInsertAndUpdateMess(bMap, seasonModels2);
			final List<BasketBallLeagueSeasonModel> insert = bMap.get("1");
			if(!insert.isEmpty()){
				session().doWork(new Work() {
					@Override
					public void execute(Connection connection) throws SQLException {
						PreparedStatement ps = connection
								.prepareStatement("insert into md_bb_sjlsgx_base("
										+ "subLeagueId,subLeagueName,isSubLeague,source,processStatus,seasonName,leagueId,isNow,createTime)values("
										+ "?,?,?,?,?,?,?,?,now())");
						for (BasketBallLeagueSeasonModel seasonModel : insert) {
							ps.setString(1, seasonModel.getSubLeagueId());
							ps.setString(2, seasonModel.getSubLeagueName());
							ps.setInt(3, seasonModel.getIsSubLeague());
							ps.setInt(4, seasonModel.getSource());
							ps.setInt(5, seasonModel.getProcessStatus());
							ps.setString(6, seasonModel.getSeasonName());
							ps.setString(7, seasonModel.getLeagueId());
							ps.setString(8, seasonModel.getIsNow());
							ps.addBatch();
						}
						ps.executeBatch();
					}
				});
			}
			
		}
	}

	private void makeInsertAndUpdateMess(
			Map<String, List<BasketBallLeagueSeasonModel>> bMap,
			List<BasketBallLeagueSeasonModel> seasonModels2) {
		List<String> index = createQuery(
				"select concat(leagueId,seasonName,subLeagueId) from BasketBallLeagueSeasonPO where source=1 and isSubLeague=0")
				.list();
		List<BasketBallLeagueSeasonModel> insert = new ArrayList<>();
		for (BasketBallLeagueSeasonModel ballLeagueSeasonModel : seasonModels2) {
			StringBuffer sb = new StringBuffer();
			sb.append(ballLeagueSeasonModel.getLeagueId())
					.append(ballLeagueSeasonModel.getSeasonName())
					.append(ballLeagueSeasonModel.getSubLeagueId());
			if (index.contains(sb.toString())) {
			}else {
				insert.add(ballLeagueSeasonModel);
			}
		}
		bMap.put("1",insert);
	}

}
