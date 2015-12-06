package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.e;
import com.unison.lottery.weibo.dataservice.crawler.service.model.LeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * @author 彭保星
 *
 * @since 2014年10月28日下午6:44:34
 */
@Repository
public class LeagueInfoDataStoreDaoImpl extends DaoImpl<LeagueInfoPO> implements
		LeagueInfoDataStoreDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8389821800607976350L;
	private static final int LEAGUE_TYPE = 1; // 联赛类型

	public LeagueInfoDataStoreDaoImpl() {
		super(LeagueInfoPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void storeFbLeague(List<LeagueInfoModel> leagueInfoModels)
			throws Exception {
		if (leagueInfoModels != null && !leagueInfoModels.isEmpty()) {
			List<String> leagueIds = getAllLeagueId(1); // 获取来源为球探的所有联赛id
			List<String> seasonNameAndLeagueIds = createFbSeasonNameAndLeagueId();
			Map leageInfoMap = makeInsertAndUpdatePos(leagueIds,
					seasonNameAndLeagueIds, leagueInfoModels);
			updateBatch((List<LeagueInfoPO>) leageInfoMap.get("1"));
			insertBatch((List<LeagueInfoPO>) leageInfoMap.get("2"));
			insertSeasonBatch((List<FbLeagueSeasonBasePO>) leageInfoMap
					.get("3"));
		}
	}

	private void insertSeasonBatch(final List<FbLeagueSeasonBasePO> list) {
		if (list != null && !list.isEmpty()) {
			Session session = session();
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = connection
							.prepareStatement("insert into"
									+ " md_fb_league_season_base(leagueId,seasonName,source,processStatus,crawlerCount,isSubLeague,createTime)values"
									+ "(?,?,?,?,?,?,now())");
					int i = 0;
					for (FbLeagueSeasonBasePO seasonBasePO : list) {
						ps.setString(1, seasonBasePO.getLeagueId());
						ps.setString(2, seasonBasePO.getSeasonName());
						ps.setInt(3, seasonBasePO.getSource());
						ps.setInt(4, seasonBasePO.getProcessStatus());
						ps.setInt(5, 0); // 初始化已抓取的轮数为0
						ps.setInt(6, 1); // 1非子联赛
						ps.addBatch();
						if (++i > 10000) {
							i = 0;
							ps.executeBatch();
						}
					}
					ps.executeBatch();
				}
			});
		}
	}

	/**
	 * 
	 * @return
	 */
	private List<String> createFbSeasonNameAndLeagueId() {
		List<String> seasonNameAndLeagueIds = new ArrayList<>();
		List<FbLeagueSeasonBasePO> seasonBasePOs = getAllSeasonId(1);// 获取来源为球探的所有的赛季
		if (seasonBasePOs != null && !seasonBasePOs.isEmpty()) {
			for (FbLeagueSeasonBasePO seasonBasePO : seasonBasePOs) {
				if (seasonBasePO.getSeasonName() != null
						&& seasonBasePO.getLeagueId() != null) {
					seasonNameAndLeagueIds.add(seasonBasePO.getSeasonName()
							+ seasonBasePO.getLeagueId());
				}
			}
		}
		return seasonNameAndLeagueIds;
	}

	private List<FbLeagueSeasonBasePO> getAllSeasonId(int source) {
		// TODO Auto-generated method stub
		return createQuery("from FbLeagueSeasonBasePO where source=?")
				.setInteger(0, source).list();
	}

	private void insertBatch(final List<LeagueInfoPO> list) {
		// TODO Auto-generated method stub
		if (list != null && !list.isEmpty()) {
			Session session = session();
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					PreparedStatement ps = connection
							.prepareStatement("insert into md_fb_league_base("
									+ " chineseName,chineseNameAll,type,countryId,country,areaId,"
									+ "importance,createTime,leagueId,source,processStatus)values(?,?,?,?,?,?,?,now(),?,?,?)");
					int i = 0;
					for (LeagueInfoPO leagueInfoPO : list) {
						ps.setString(1, leagueInfoPO.getChineseName());
						ps.setString(2, leagueInfoPO.getChineseNameAll());
						if (leagueInfoPO.getType() != null) {
							ps.setInt(3, leagueInfoPO.getType());
						} else {
							ps.setInt(3, 1); // 默认为联赛
						}
						ps.setString(4, leagueInfoPO.getCountryId());
						ps.setString(5, leagueInfoPO.getCountry());
						ps.setInt(6, leagueInfoPO.getAreaId() == null ? 0
								: leagueInfoPO.getAreaId());
						ps.setInt(7, leagueInfoPO.getImportance() == null ? 0
								: leagueInfoPO.getImportance()); // 默认次要赛事
						ps.setString(8, leagueInfoPO.getLeagueId());
						ps.setInt(9, leagueInfoPO.getSource());
						ps.setInt(10,
								leagueInfoPO.getProcessStatus() == null ? 1
										: leagueInfoPO.getProcessStatus());
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

	}

	public void updateBatch(final List<LeagueInfoPO> list) {
		// TODO Auto-generated method stub
		if (list != null && !list.isEmpty()) {
			session().doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					PreparedStatement ps = connection
							.prepareStatement("update md_fb_league_base set"
									+ " chineseName=?,chineseNameAll=?,type=?,countryId=?,country=?,areaId=?,"
									+ "importance=?,updateTime=now() where leagueId=? and source=? ");
					int i = 0;
					for (LeagueInfoPO leagueInfoPO : list) {
						ps.setString(1, leagueInfoPO.getChineseName());
						ps.setString(2, leagueInfoPO.getChineseNameAll());
						if (leagueInfoPO.getType() != null) {
							ps.setInt(3, leagueInfoPO.getType());
						} else {
							ps.setInt(3, 1); // 默认为联赛
						}
						ps.setString(4, leagueInfoPO.getCountryId());
						ps.setString(5, leagueInfoPO.getCountry());
						ps.setInt(6, leagueInfoPO.getAreaId() == null ? 0
								: leagueInfoPO.getAreaId());
						ps.setInt(7, leagueInfoPO.getImportance() == null ? 0
								: leagueInfoPO.getImportance()); // 默认次要赛事
						ps.setString(8, leagueInfoPO.getLeagueId());
						ps.setInt(9, leagueInfoPO.getSource());
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

	private Map makeInsertAndUpdatePos(List<String> leagueIds,
			List<String> seasonNameAndLeagueIds,
			List<LeagueInfoModel> leagueInfoModels)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		List<LeagueInfoPO> insertPos = new ArrayList<>();
		List<FbLeagueSeasonBasePO> insertSeasonBasePOs = new ArrayList<>();
		List<LeagueInfoPO> updateInfoPOs = new ArrayList<>();
		for (LeagueInfoModel leagueInfoModel : leagueInfoModels) {
			if (leagueIds.contains(leagueInfoModel.getLeagueId())) {
				LeagueInfoPO leagueInfoPO = new LeagueInfoPO();
				BeanUtils.copyProperties(leagueInfoPO, leagueInfoModel);
				updateInfoPOs.add(leagueInfoPO);

			} else {
				LeagueInfoPO leagueInfoPO = new LeagueInfoPO();
				BeanUtils.copyProperties(leagueInfoPO, leagueInfoModel);
				insertPos.add(leagueInfoPO);
			}
			// 生成需要新增的赛季信息
			createInsertSeason(seasonNameAndLeagueIds, insertSeasonBasePOs,
					leagueInfoModel);
		}
		Map leageInfoMap = new HashMap();
		leageInfoMap.put("1", updateInfoPOs);
		leageInfoMap.put("2", insertPos);
		leageInfoMap.put("3", insertSeasonBasePOs);
		return leageInfoMap;
	}

	private void createInsertSeason(List<String> seasonNameAndLeagueIds,
			List<FbLeagueSeasonBasePO> insertSeasonBasePOs,
			LeagueInfoModel leagueInfoModel) throws IllegalAccessException,
			InvocationTargetException {
		List<SeasonModel> seasonModels = leagueInfoModel.getSeasonModels();
		String leagueId = leagueInfoModel.getLeagueId();
		if (seasonModels != null) {
			for (SeasonModel seasonModel : seasonModels) {

				String seasonName = seasonModel.getSeasonName();
				if (leagueId == null
						|| seasonName == null
						|| !seasonNameAndLeagueIds.contains(seasonName
								+ leagueId)) {
					FbLeagueSeasonBasePO seasonBasePO = new FbLeagueSeasonBasePO();
					BeanUtils.copyProperties(seasonBasePO, seasonModel);
					seasonBasePO.setLeagueId(leagueId);
					insertSeasonBasePOs.add(seasonBasePO);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<String> getAllLeagueId(Integer source) {
		return createQuery("select leagueId from LeagueInfoPO where source=?")
				.setInteger(0, source).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<FbLeagueSeasonBasePO> queryAllSeasonMessageNotCrawler(
			Integer source) {
		// and locate(?,seasonName)>0 .setString(2,
		// DateFormateUtil.getNowYear())
		return createQuery(
				"select a from FbLeagueSeasonBasePO a,LeagueInfoPO b"
						+ " where a.source=? and a.totalRound is not null and a.crawlerCount<a.totalRound"
						+ " and a.leagueId=b.leagueId and b.type=? and locate(?,seasonName)>0  order by a.seasonId asc")
				.setInteger(0, source).setInteger(1, LEAGUE_TYPE)
				.setString(2, DateFormateUtil.getNowYear()).list();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void storeMatchBaseMessage(final List<QtMatchBaseModel> list,
			final int round) throws Exception {

		if (list != null && !list.isEmpty()) {
			// 查询所有属于当前赛季的联赛比赛
			List<String> bsids = createQuery(
					"select bsId from FbMatchBaseInfoPO where locate(?,season)>0")
					.setString(0, DateFormateUtil.getNowYear()).list();
			Map<String, List<QtMatchBaseModel>> matchMap = new HashMap<>();
			makeUpdateAndInsertMatch(bsids, list, matchMap);
			final List<QtMatchBaseModel> insert = matchMap.get("1");
			final List<QtMatchBaseModel> update = matchMap.get("2");
			if (!insert.isEmpty()) {
				insertMatchBatch(insert, round);
			}
			if (!update.isEmpty()) {
				updateMatchBatch(update, round);
			}
		}

	}

	private void updateMatchBatch(final List<QtMatchBaseModel> update,
			final int round) {
		// TODO Auto-generated method stub
		Session session = session();
		Work work1 = new Work() {

			@Override
			public void execute(Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = connection
						.prepareStatement("update md_qt_match_base set "
								+ " matchTime=?,homeTeamId=?,guestTeamId=?,"
								+ "seasonId=?,matchStatus=?,homeTeamScore=?,guestTeamScore=?,homeTeamHalfScore=?,"
								+ "guestTeamHalfScore=?,homeTeamPosition=?,guestTeamPosition=?,"
								+ "source=?,processStatus=?,matchMessage=?,homeTeamName=?,guestTeamName=?,round=?,season=?,leagueId=?,updateTime=now() where bsId=?");
				for (QtMatchBaseModel qtMatchBaseModel : update) {
					ps.setTimestamp(1, new Timestamp(qtMatchBaseModel
							.getMatchTime().getTime()));
					ps.setString(2, qtMatchBaseModel.getHomeTeamId());
					ps.setString(3, qtMatchBaseModel.getGuestTeamId());
					ps.setInt(4, qtMatchBaseModel.getSeasonId());
					ps.setInt(5, qtMatchBaseModel.getMatchStatus());
					ps.setInt(6, qtMatchBaseModel.getHomeTeamScore());
					ps.setInt(7, qtMatchBaseModel.getGuestTeamScore());
					ps.setInt(8,
							qtMatchBaseModel.getHomeTeamHalfScore() == null ? 0
									: qtMatchBaseModel.getHomeTeamHalfScore());
					ps.setInt(
							9,
							qtMatchBaseModel.getGuestTeamHalfScore() == null ? 0
									: qtMatchBaseModel.getGuestTeamHalfScore());
					ps.setInt(10,
							qtMatchBaseModel.getHomeTeamPosition() == null ? -1
									: qtMatchBaseModel.getHomeTeamPosition());
					ps.setInt(
							11,
							qtMatchBaseModel.getGuestTeamPosition() == null ? -1
									: qtMatchBaseModel.getGuestTeamPosition());
					ps.setInt(12, qtMatchBaseModel.getSource());
					ps.setInt(13, qtMatchBaseModel.getProcessStatus());
					ps.setString(14, qtMatchBaseModel.getMatchMessage());
					ps.setString(15, qtMatchBaseModel.getHomeTeamId());
					ps.setString(16, qtMatchBaseModel.getGuestTeamId());
					ps.setString(17, String.valueOf(round));
					ps.setString(18, qtMatchBaseModel.getSeason());
					ps.setString(19, qtMatchBaseModel.getLeagueId());
					ps.setString(20, qtMatchBaseModel.getBsId());

					ps.addBatch();
				}
				ps.executeBatch();
			}
		};
		session.doWork(work1);
		// 更新联赛赛季已经抓取的轮数
		updateSeasonCrawlerRound(update, round, session);
		session.flush();
	}

	/**
	 * @param update
	 * @param round
	 * @param session
	 * @throws HibernateException
	 */
	private void updateSeasonCrawlerRound(final List<QtMatchBaseModel> update,
			final int round, Session session) throws HibernateException {
		session.createSQLQuery(
				"update md_fb_league_season_base set crawlerCount=?,updateTime=now() where seasonId=?")
				.setInteger(0, round)
				.setInteger(1, update.get(0).getSeasonId()).executeUpdate();
	}

	/**
	 * @param list
	 * @param round
	 * @throws HibernateException
	 */
	private void insertMatchBatch(final List<QtMatchBaseModel> list,
			final int round) throws HibernateException {
		Session session = session();
		Work work1 = new Work() {

			@Override
			public void execute(Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = connection
						.prepareStatement("insert into md_qt_match_base("
								+ " bsId,matchTime,homeTeamId,guestTeamId,leagueId,season,"
								+ "seasonId,matchStatus,homeTeamScore,guestTeamScore,homeTeamHalfScore,"
								+ "guestTeamHalfScore,homeTeamPosition,guestTeamPosition,"
								+ "source,processStatus,matchMessage,homeTeamName,guestTeamName,round,createTime)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())");
				for (QtMatchBaseModel qtMatchBaseModel : list) {
					ps.setString(1, qtMatchBaseModel.getBsId());
					ps.setTimestamp(2, new Timestamp(qtMatchBaseModel
							.getMatchTime().getTime()));
					ps.setString(3, qtMatchBaseModel.getHomeTeamId());
					ps.setString(4, qtMatchBaseModel.getGuestTeamId());

					ps.setString(5, qtMatchBaseModel.getLeagueId());
					ps.setString(6, qtMatchBaseModel.getSeason());
					ps.setInt(7, qtMatchBaseModel.getSeasonId());
					ps.setInt(8, qtMatchBaseModel.getMatchStatus());
					ps.setInt(9, qtMatchBaseModel.getHomeTeamScore());
					ps.setInt(10, qtMatchBaseModel.getGuestTeamScore());
					ps.setInt(11,
							qtMatchBaseModel.getHomeTeamHalfScore() == null ? 0
									: qtMatchBaseModel.getHomeTeamHalfScore());
					ps.setInt(
							12,
							qtMatchBaseModel.getGuestTeamHalfScore() == null ? 0
									: qtMatchBaseModel.getGuestTeamHalfScore());
					ps.setInt(13,
							qtMatchBaseModel.getHomeTeamPosition() == null ? -1
									: qtMatchBaseModel.getHomeTeamPosition());
					ps.setInt(
							14,
							qtMatchBaseModel.getGuestTeamPosition() == null ? -1
									: qtMatchBaseModel.getGuestTeamPosition());
					ps.setInt(15, qtMatchBaseModel.getSource());
					ps.setInt(16, qtMatchBaseModel.getProcessStatus());
					ps.setString(17, qtMatchBaseModel.getMatchMessage());
					ps.setString(18, qtMatchBaseModel.getHomeTeamId());
					ps.setString(19, qtMatchBaseModel.getGuestTeamId());
					ps.setString(20, String.valueOf(round));
					ps.addBatch();
				}
				ps.executeBatch();
			}
		};
		session.doWork(work1);
		updateSeasonCrawlerRound(list, round, session);
		session.flush();
	}

	private void makeUpdateAndInsertMatch(List<String> bsids,
			List<QtMatchBaseModel> list,
			Map<String, List<QtMatchBaseModel>> matchMap) {
		List<QtMatchBaseModel> insert = new ArrayList<>();
		List<QtMatchBaseModel> update = new ArrayList<>();
		for (QtMatchBaseModel qtMatchBaseModel : list) {
			if (bsids.contains(qtMatchBaseModel.getBsId())) {
				update.add(qtMatchBaseModel);
			} else {
				insert.add(qtMatchBaseModel);
			}
		}
		matchMap.put("1", insert);
		matchMap.put("2", update);
	}

	@Override
	@Transactional
	public void updateCrawlerCount(int seasonId, int round) {
		// 更新联赛赛季已经抓取的轮数
		session()
				.createSQLQuery(
						"update md_fb_league_season_base set crawlerCount=?,updateTime=now() where seasonId=?")
				.setInteger(0, round).setInteger(1, seasonId).executeUpdate();
		session().flush();
	}

	@Override
	@Transactional
	public List<FbLeagueSeasonBasePO> queryAllSeasonNotHaveRule(Integer type,
			Integer source) {
		return createQuery(
				"select new FbLeagueSeasonBasePO(a.leagueId,a.subLeagueId,a.seasonName,a.seasonId,a.isSubLeague) from FbLeagueSeasonBasePO a,LeagueInfoPO b where a.leagueId=b.leagueId and a.source=? and b.type=? and locate(?,a.seasonName)>0 order by a.seasonId asc")
				.setInteger(0, source).setInteger(1, type)
				.setString(2, DateFormateUtil.getNowYear()).list();
	}

	@Override
	@Transactional
	public List<FbLeagueSeasonBasePO> queryAllCupSeasonNotCrawler(int type,
			Integer isSubLeague, Integer source) {
		return createQuery(
				"select new FbLeagueSeasonBasePO(a.leagueId,a.subLeagueId,a.seasonName,a.seasonId,a.isSubLeague) from FbLeagueSeasonBasePO a,LeagueInfoPO b where a.leagueId=b.leagueId and a.source=? and b.type=? and a.isSubLeague=? and b.source=? and locate(?,a.seasonName)>0 order by a.seasonId asc")
				.setInteger(0, source).setInteger(1, type)
				.setInteger(2, isSubLeague).setInteger(3, source)
				.setString(4, DateFormateUtil.getNowYear()).list();
	}

	@Override
	@Transactional
	public void saveFbMatchOpOneCompany(Long bsId, String companyId,
			FbMatchOpOddsInfoPO matchOpOddsInfoPO) {
		String sql = "select euroOdds from FbMatchOpOddsInfoPO where corpId=? and bsId=? order by createTime desc";
		List<String> euroOdds = createQuery(sql).setString(0, companyId)
				.setLong(1, bsId).list();
		if (euroOdds != null && euroOdds.size() > 0) {
			if (matchOpOddsInfoPO.getEuroOdds().length() > euroOdds.get(0)
					.length()) {
				createQuery(
						"update FbMatchOpOddsInfoPO set changeTime=?,euroOdds=?,kellyIndex=?,updateTime=now() where bsId=? and corpId=?")
						.setString(0, matchOpOddsInfoPO.getChangeTime())
						.setString(1, matchOpOddsInfoPO.getEuroOdds())
						.setString(2, matchOpOddsInfoPO.getKellyIndex())
						.setLong(3, bsId).setString(4, companyId)
						.executeUpdate();
			}

		} else {
			matchOpOddsInfoPO.setSource(1);
			matchOpOddsInfoPO.setBsId(bsId);
			matchOpOddsInfoPO.setCorpId(companyId);
			matchOpOddsInfoPO.setProcessStatus(0);
			matchOpOddsInfoPO.setCreateTime(new Date());
			session().save(matchOpOddsInfoPO);
//			createQuery(
//					"insert into FbMatchOpOddsInfoPO(changeTime,euroOdds,kellyIndex,bsId,corpId,processStatus,source,createTime)values(?,?,?,?,?,?,?,?)")
//					.setString(0, matchOpOddsInfoPO.getChangeTime())
//					.setString(1, matchOpOddsInfoPO.getEuroOdds())
//					.setString(2, matchOpOddsInfoPO.getKellyIndex())
//					.setLong(3, bsId).setString(4, companyId).setInteger(5, 0)
//					.setInteger(6, 1).setDate(7, new Date()).executeUpdate();
		}
	}

	@Override
	@Transactional
	public List<FbMatchBaseInfoPO> queryFbJingCaiMatchNotStart() {
		
		return createQuery("select new FbMatchBaseInfoPO(bsId,id) from FbMatchBaseInfoPO where jingcaiId is not null and matchStatus=0 and matchTime>=now()").list();
	}
}
