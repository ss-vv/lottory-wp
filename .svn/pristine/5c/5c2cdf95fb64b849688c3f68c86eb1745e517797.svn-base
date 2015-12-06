package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.inject.util.FinalizablePhantomReference;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.service.store.persist.dao.impl.BasicDaoImpl;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.unison.lottery.weibo.dataservice.crawler.util.ValueConvertUtil;

/**
 * @author 彭保星
 *
 * @since 2014年11月7日下午3:10:30
 */
@Repository
public class MatchInfoDataStoreDaoImpl extends BasicDaoImpl<FbMatchBaseInfoPO>
		implements MatchInfoDataStoreDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6673761331150566745L;

	Logger logger = LoggerFactory.getLogger(LeagueInfoDataStoreDaoImpl.class);

	public MatchInfoDataStoreDaoImpl() {
		super(FbMatchBaseInfoPO.class);
	}

	@Override
	@Transactional
	public void storeCupMatchInfo(List<QtMatchBaseModel> qtMatchBaseModels) {
		Map<String, List<QtMatchBaseModel>> matchMap = new HashMap<String, List<QtMatchBaseModel>>();
		makeInsertMatchInfo(qtMatchBaseModels, matchMap);
		final List<QtMatchBaseModel> insert = matchMap.get("1");
		List<QtMatchBaseModel> update = matchMap.get("2");
		if (update != null && !update.isEmpty()) {
			updateMatchBatch(update);
		}
		if (insert != null && !insert.isEmpty()) {
			insertMatch(qtMatchBaseModels, insert);
		}
	}

	/**
	 * @param qtMatchBaseModels
	 * @param insert
	 * @throws HibernateException
	 */
	private void insertMatch(List<QtMatchBaseModel> qtMatchBaseModels,
			final List<QtMatchBaseModel> insert) throws HibernateException {
		if (!qtMatchBaseModels.isEmpty()) {
			Session session = session();
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					PreparedStatement ps = connection
							.prepareStatement("insert into md_qt_match_base("
									+ " bsId,matchTime,homeTeamId,guestTeamId,leagueId,season,"
									+ "seasonId,matchStatus,homeTeamScore,guestTeamScore,homeTeamHalfScore,"
									+ "guestTeamHalfScore,homeTeamPosition,guestTeamPosition,"
									+ "source,processStatus,matchMessage,homeTeamName,guestTeamName,groupId,createTime)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())");
					for (QtMatchBaseModel qtMatchBaseModel : insert) {
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
						ps.setInt(
								11,
								qtMatchBaseModel.getHomeTeamHalfScore() == null ? 0
										: qtMatchBaseModel
												.getHomeTeamHalfScore());
						ps.setInt(
								12,
								qtMatchBaseModel.getGuestTeamHalfScore() == null ? 0
										: qtMatchBaseModel
												.getGuestTeamHalfScore());
						ps.setInt(
								13,
								qtMatchBaseModel.getHomeTeamPosition() == null ? -1
										: qtMatchBaseModel
												.getHomeTeamPosition());
						ps.setInt(
								14,
								qtMatchBaseModel.getGuestTeamPosition() == null ? -1
										: qtMatchBaseModel
												.getGuestTeamPosition());
						ps.setInt(15, qtMatchBaseModel.getSource());
						ps.setInt(16, qtMatchBaseModel.getProcessStatus());
						ps.setString(17, qtMatchBaseModel.getMatchMessage());
						ps.setString(18, qtMatchBaseModel.getHomeTeamId());
						ps.setString(19, qtMatchBaseModel.getGuestTeamId());
						ps.setString(20, qtMatchBaseModel.getGroupId());
						ps.addBatch();
					}
					ps.executeBatch();

				}
			});
			updateSeasonCrawlerRound(qtMatchBaseModels, 1, session);
			session.flush();
			session.clear();
		}
	}

	/**
	 * @param qtMatchBaseModels
	 * @param session
	 * @throws HibernateException
	 */
	private void updateSeasonCrawlerRound(
			List<QtMatchBaseModel> qtMatchBaseModels, Integer round,
			Session session) throws HibernateException {
		// 更新杯赛赛季已经抓取的轮数
		session.createSQLQuery(
				"update md_fb_league_season_base set crawlerCount=?,updateTime=now() where seasonId=?")
				.setInteger(0, 1)
				.setInteger(1, qtMatchBaseModels.get(0).getSeasonId())
				.executeUpdate();
	}

	/**
	 * 过滤未入库的比赛
	 * 
	 * @param qtMatchBaseModels
	 * @return
	 */
	private void makeInsertMatchInfo(List<QtMatchBaseModel> qtMatchBaseModels,
			Map<String, List<QtMatchBaseModel>> matchMap) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Query query = createQuery(
				"select count(bsId) from FbMatchBaseInfoPO where bsId=?");
		List<QtMatchBaseModel> qtMatchBaseModels2 = new ArrayList<>();
		List<QtMatchBaseModel> update = new ArrayList<>();
		qtMatchBaseModels2 = new ArrayList<>();
		for (QtMatchBaseModel qtMatchBaseModel : qtMatchBaseModels) {
			query.setString(0, qtMatchBaseModel.getBsId());
			if (((Long)query.uniqueResult())>0) {
				update.add(qtMatchBaseModel);
			} else {
				qtMatchBaseModels2.add(qtMatchBaseModel);
			}
			
		}
		matchMap.put("1", qtMatchBaseModels2);
		matchMap.put("2", update);
	}

	private void updateMatchBatch(final List<QtMatchBaseModel> update) {
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
								+ "source=?,processStatus=?,matchMessage=?,homeTeamName=?,guestTeamName=?,round=?,season=?,leagueId=?,updateTime=now() where bsId=? ");
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
					ps.setString(17, null);
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
		updateSeasonCrawlerRound(update, 1, session);
		session.flush();
	}

	@Override
	@Transactional
	public void updateMatchJingcaiId(final List<QtMatchBaseModel> matchBaseInfos) {
		if (matchBaseInfos != null && !matchBaseInfos.isEmpty()) {
			Map<String, List<QtMatchBaseModel>> matchMap = new HashMap<String, List<QtMatchBaseModel>>();
			makeInsertMatchInfo(matchBaseInfos, matchMap);
			final List<QtMatchBaseModel> insert = matchMap.get("1");
			final List<QtMatchBaseModel> update = matchMap.get("2");
			updateCaikeSchel(update);
			insertCaikeSchel(insert);
		}
	}

	private void insertCaikeSchel(final List<QtMatchBaseModel> insert) {
		if(!insert.isEmpty()){
			session().doWork(new Work() {
				
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement("insert into md_qt_match_base"
									+ " (jingcaiId,bsId,color,matchTime,leagueId,homeTeamId,homeTeamName,guestTeamId,guestTeamName,source,matchStatus,processStatus,createTime,isNow)"
									+ " values(?,?,?,?,?,?,?,?,?,?,?,?,now(),1)");
					for (QtMatchBaseModel qtMatchBaseModel : insert) {
						ps.setString(1, qtMatchBaseModel.getJingcaiId());
						ps.setString(2, qtMatchBaseModel.getBsId());
						ps.setString(3, qtMatchBaseModel.getColor());
						ps.setTimestamp(4, new Timestamp(qtMatchBaseModel.getMatchTime().getTime()));
						ps.setString(5, qtMatchBaseModel.getLeagueId());
						ps.setString(6, qtMatchBaseModel.getHomeTeamName());
						ps.setString(7, qtMatchBaseModel.getHomeTeamName());
						ps.setString(8, qtMatchBaseModel.getGuestTeamName());
						ps.setString(9, qtMatchBaseModel.getGuestTeamName());
						ps.setInt(10, 1);// 来源为球探
						ps.setInt(11, 0);
						ps.setInt(12, 0);
						ps.addBatch();
					}
					try {
						ps.executeBatch();
					} catch (Exception exception) {
						// 联赛不存在
						if (exception instanceof BatchUpdateException) {
							BatchUpdateException batchUpdateException = (BatchUpdateException) exception;
							int[] updatedCount = batchUpdateException
									.getUpdateCounts();
							logger.info("插入失败的数据:{}",
									insert.get(updatedCount.length - 1));
							//插入失败表示已经存在，更新
							List<QtMatchBaseModel> matchBaseModels = new ArrayList<>();
							matchBaseModels.add(insert.get(updatedCount.length-1));
							updateCaikeSchel(matchBaseModels);
							if (updatedCount.length < insert.size()) {
								List<QtMatchBaseModel> qtMatchBaseModels = insert
										.subList(updatedCount.length, insert.size());
								logger.info("本次插入的比赛数目为{}", qtMatchBaseModels.size());
								insertCaikeSchel(qtMatchBaseModels);
							}
						}
					}
					logger.info("总共更新的比赛数目为{}", insert.size());
				}
			});
		}
	}

	private void updateCaikeSchel(final List<QtMatchBaseModel> update) {
		if (!update.isEmpty()) {
			session().doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement("update md_qt_match_base set "
									+ " leagueId=?,jingcaiId=ifnull(jingcaiId,?),updateTime=now(),matchTime=?,isNow=1 where bsId=? and source=?");
					for (QtMatchBaseModel qtMatchBaseModel : update) {
						ps.setString(1, qtMatchBaseModel.getLeagueId());
						ps.setString(2, qtMatchBaseModel.getJingcaiId());
						ps.setTimestamp(3, new Timestamp(qtMatchBaseModel.getMatchTime().getTime()));
						ps.setString(4, qtMatchBaseModel.getBsId());
						ps.setInt(5, 1);// 来源为球探
						ps.addBatch();
					}
					ps.executeBatch();
					logger.info("本次更新的比赛数目为{}", update.size());
				}
			});
			session().flush();
			session().clear();
		}
	}
	
	@Override
	@Transactional
	public void updateMatchData(final List<QtMatchBaseModel> matchBaseInfos) {
		if (matchBaseInfos != null && !matchBaseInfos.isEmpty()) {
			Map<String, List<QtMatchBaseModel>> matchMap = new HashMap<String, List<QtMatchBaseModel>>();
			makeInsertMatchInfo(matchBaseInfos, matchMap);
			final List<QtMatchBaseModel> insert = matchMap.get("1");
			final List<QtMatchBaseModel> update = matchMap.get("2");
			if (!update.isEmpty()) {
				updateJishiMatch(update);
			}
			if (!insert.isEmpty()) {
				insertJishiMatch(insert);
			}
		}
	}

	private void insertJishiMatch(final List<QtMatchBaseModel> insert) {
		Session session = session();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = connection
						.prepareStatement("insert into md_qt_match_base("
								+ " bsId,matchTime,homeTeamId,guestTeamId,leagueId,season,"
								+ " matchStatus,homeTeamScore,guestTeamScore,homeTeamHalfScore,"
								+ "guestTeamHalfScore,homeTeamPosition,guestTeamPosition,"
								+ "source,processStatus,matchMessage,homeTeamName,guestTeamName,handiCap,lordOdds,flatOdds,negativeOdds,televison,createTime,jingcaiId)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)");
				for (QtMatchBaseModel qtMatchBaseModel : insert) {
					ps.setString(1, qtMatchBaseModel.getBsId());
					ps.setTimestamp(2, new Timestamp(qtMatchBaseModel
							.getMatchTime().getTime()));
					ps.setString(3, qtMatchBaseModel.getHomeTeamId());
					ps.setString(4, qtMatchBaseModel.getGuestTeamId());

					ps.setString(5, qtMatchBaseModel.getLeagueId());
					ps.setString(6, qtMatchBaseModel.getSeason());
					ps.setInt(7, qtMatchBaseModel.getMatchStatus());
					ps.setInt(8, qtMatchBaseModel.getHomeTeamScore());
					ps.setInt(9, qtMatchBaseModel.getGuestTeamScore());
					ps.setInt(10,
							qtMatchBaseModel.getHomeTeamHalfScore() == null ? 0
									: qtMatchBaseModel.getHomeTeamHalfScore());
					ps.setInt(
							11,
							qtMatchBaseModel.getGuestTeamHalfScore() == null ? 0
									: qtMatchBaseModel.getGuestTeamHalfScore());
					ps.setInt(12,
							qtMatchBaseModel.getHomeTeamPosition() == null ? -1
									: qtMatchBaseModel.getHomeTeamPosition());
					ps.setInt(
							13,
							qtMatchBaseModel.getGuestTeamPosition() == null ? -1
									: qtMatchBaseModel.getGuestTeamPosition());
					ps.setInt(14, qtMatchBaseModel.getSource());
					ps.setInt(15, qtMatchBaseModel.getProcessStatus());
					ps.setString(16, qtMatchBaseModel.getMatchMessage());
					ps.setString(17, qtMatchBaseModel.getHomeTeamId());
					ps.setString(18, qtMatchBaseModel.getGuestTeamId());
					ps.setDouble(19, qtMatchBaseModel.getHandiCap());
					ps.setDouble(20, qtMatchBaseModel.getLordOdds());
					ps.setDouble(21, qtMatchBaseModel.getFlatOdds());
					ps.setDouble(22, qtMatchBaseModel.getNegativeOdds());
					ps.setString(23, qtMatchBaseModel.getTelevison());
					ps.setString(24, qtMatchBaseModel.getJingcaiId());
					ps.addBatch();
				}
				try {
					ps.executeBatch();
				} catch (Exception exception) {
					// 联赛不存在
					if (exception instanceof BatchUpdateException) {
						BatchUpdateException batchUpdateException = (BatchUpdateException) exception;
						int[] updatedCount = batchUpdateException
								.getUpdateCounts();
						logger.info("插入失败的数据:{}",
								insert.get(updatedCount.length - 1));
						//插入失败表示已经存在，更新
						List<QtMatchBaseModel> matchBaseModels = new ArrayList<>();
						matchBaseModels.add(insert.get(updatedCount.length-1));
						updateJishiMatch(matchBaseModels);
						if (updatedCount.length < insert.size()) {
							List<QtMatchBaseModel> qtMatchBaseModels = insert
									.subList(updatedCount.length, insert.size());
							logger.info("本次插入的比赛数目为{}", qtMatchBaseModels.size());
							insertJishiMatch(qtMatchBaseModels);
						}
					}
				}
				logger.info("总共更新的比赛数目为{}", insert.size());

			}
		});
		session.flush();
	}

	/**
	 * @param matchBaseInfos
	 * @param update
	 * @throws HibernateException
	 */
	private void updateJishiMatch(final List<QtMatchBaseModel> update)
			throws HibernateException {
		session().doWork(new Work() {

			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement("update md_qt_match_base set"
								+ " matchStatus=?,matchTime=?,halfStartTime=?,homeTeamScore=?,"
								+ " guestTeamScore=?,homeTeamHalfScore=?,homeTeamRed=?,guestTeamRed=?,"
								+ " homeTeamYellow=?,guestTeamYellow=?,handiCap=?,lordOdds=?,flatOdds=?,"
								+ " guestTeamHalfScore=?,negativeOdds=?,homeTeamPosition=?,guestTeamPosition=?,televison=?,updateTime=now(),jingcaiId=ifnull(jingcaiId,?) where bsId=? and source=?");
				for (QtMatchBaseModel qtMatchBaseModel : update) {
					ps.setInt(1, qtMatchBaseModel.getMatchStatus());
					ps.setTimestamp(2, new Timestamp(qtMatchBaseModel
							.getMatchTime().getTime()));
					ps.setTimestamp(3,
							qtMatchBaseModel.getHalfStartTime() == null ? null
									: new Timestamp(qtMatchBaseModel
											.getHalfStartTime().getTime()));
					ps.setInt(4, qtMatchBaseModel.getHomeTeamScore());
					ps.setInt(5, qtMatchBaseModel.getGuestTeamScore());
					ps.setInt(6, qtMatchBaseModel.getHomeTeamHalfScore());
					ps.setInt(7, qtMatchBaseModel.getHomeTeamRed());
					ps.setInt(8, qtMatchBaseModel.getGuestTeamRed());
					ps.setInt(9, qtMatchBaseModel.getHomeTeamYellow());
					ps.setInt(10, qtMatchBaseModel.getGuestTeamYellow());
					ps.setDouble(11, qtMatchBaseModel.getHandiCap());
					ps.setDouble(12, qtMatchBaseModel.getLordOdds());
					ps.setDouble(13, qtMatchBaseModel.getFlatOdds());
					ps.setInt(14, qtMatchBaseModel.getGuestTeamHalfScore());
					ps.setDouble(15, qtMatchBaseModel.getNegativeOdds());
					ps.setInt(16, qtMatchBaseModel.getHomeTeamPosition());
					ps.setInt(17, qtMatchBaseModel.getGuestTeamPosition());
					ps.setString(18, qtMatchBaseModel.getTelevison());
					ps.setString(19, qtMatchBaseModel.getJingcaiId());
					ps.setString(20, qtMatchBaseModel.getBsId());
					ps.setInt(21, 1);// 来源为球探
					ps.addBatch();
				}
				ps.executeBatch();

				logger.info("本次更新的比赛数目为{}", update.size());
			}
		});
		session().flush();
		session().clear();
	}

	@Override
	@Transactional
	public String getNowSeasonByLeagueId(String leagueId) {
		// TODO Auto-generated method stub
		return (String) createQuery(
				"select max(seasonName) from FbLeagueSeasonBasePO where leagueId=? order by seasonName desc")
				.setString(0, leagueId).uniqueResult();
	}

	@Override
	@Transactional
	public void updateJingCaiMatchData(List<QtMatchBaseModel> qtMatchBaseModels) {
		// TODO Auto-generated method stub
		Map<String, List<QtMatchBaseModel>> matchMap = new HashMap<String, List<QtMatchBaseModel>>();
		makeInsertMatchInfo(qtMatchBaseModels, matchMap);
		final List<QtMatchBaseModel> insert = matchMap.get("1");
		List<QtMatchBaseModel> update = matchMap.get("2");
		if (update != null && !update.isEmpty()) {
			updateJingcaiMatchBatch(update);
		}
		if (insert != null && !insert.isEmpty()) {
			insertJingCaiMatch(qtMatchBaseModels, insert);
		}
	}

	private void insertJingCaiMatch(List<QtMatchBaseModel> qtMatchBaseModels,
			final List<QtMatchBaseModel> insert) {
		// TODO Auto-generated method stub
		Session session = session();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = connection
						.prepareStatement("insert into md_qt_match_base("
								+ " bsId,matchTime,homeTeamId,guestTeamId,leagueId,season,"
								+ " matchStatus,homeTeamScore,guestTeamScore,homeTeamHalfScore,"
								+ "guestTeamHalfScore,homeTeamPosition,guestTeamPosition,"
								+ "source,processStatus,matchMessage,homeTeamName,guestTeamName,handiCap,televison,jingcaiId,createTime)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())");
				for (QtMatchBaseModel qtMatchBaseModel : insert) {
					ps.setString(1, qtMatchBaseModel.getBsId());
					ps.setTimestamp(2, new Timestamp(qtMatchBaseModel
							.getMatchTime().getTime()));
					ps.setString(3, qtMatchBaseModel.getHomeTeamId());
					ps.setString(4, qtMatchBaseModel.getGuestTeamId());

					ps.setString(5, qtMatchBaseModel.getLeagueId());
					ps.setString(6, qtMatchBaseModel.getSeason());
					ps.setInt(7, qtMatchBaseModel.getMatchStatus());
					ps.setInt(8, qtMatchBaseModel.getHomeTeamScore());
					ps.setInt(9, qtMatchBaseModel.getGuestTeamScore());
					ps.setInt(10,
							qtMatchBaseModel.getHomeTeamHalfScore() == null ? 0
									: qtMatchBaseModel.getHomeTeamHalfScore());
					ps.setInt(
							11,
							qtMatchBaseModel.getGuestTeamHalfScore() == null ? 0
									: qtMatchBaseModel.getGuestTeamHalfScore());
					ps.setInt(12,
							qtMatchBaseModel.getHomeTeamPosition() == null ? -1
									: qtMatchBaseModel.getHomeTeamPosition());
					ps.setInt(
							13,
							qtMatchBaseModel.getGuestTeamPosition() == null ? -1
									: qtMatchBaseModel.getGuestTeamPosition());
					ps.setInt(14, qtMatchBaseModel.getSource());
					ps.setInt(15, qtMatchBaseModel.getProcessStatus());
					ps.setString(16, qtMatchBaseModel.getMatchMessage());
					ps.setString(17, qtMatchBaseModel.getHomeTeamId());
					ps.setString(18, qtMatchBaseModel.getGuestTeamId());
					ps.setDouble(19, qtMatchBaseModel.getHandiCap());
					ps.setString(20, qtMatchBaseModel.getTelevison());
					ps.setString(21, qtMatchBaseModel.getJingcaiId());
					
					ps.addBatch();
				}
				try {
					ps.executeBatch();
				} catch (Exception exception) {
					// 联赛不存在
					if (exception instanceof BatchUpdateException) {
						BatchUpdateException batchUpdateException = (BatchUpdateException) exception;
						int[] updatedCount = batchUpdateException
								.getUpdateCounts();
						logger.info("更新失败的数据:{}",
								insert.get(updatedCount.length - 1));
						if (updatedCount.length < insert.size()) {
							List<QtMatchBaseModel> qtMatchBaseModels = insert
									.subList(updatedCount.length, insert.size());
							insertJishiMatch(qtMatchBaseModels);
						}
					}
				}
				logger.info("本次插入的比赛数目为{}", insert.size());

			}
		});
		session.flush();
	}

	private void updateJingcaiMatchBatch(final List<QtMatchBaseModel> update) {
		session().doWork(new Work() {

			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement("update md_qt_match_base set"
								+ " matchStatus=?,matchTime=?,halfStartTime=?,homeTeamScore=?,"
								+ " guestTeamScore=?,homeTeamHalfScore=?,homeTeamRed=?,guestTeamRed=?,"
								+ " homeTeamYellow=?,guestTeamYellow=?,handiCap=?,"
								+ " guestTeamHalfScore=?,homeTeamPosition=?,guestTeamPosition=?,televison=?,jingcaiId=?,updateTime=now() where bsId=? and source=?");
				for (QtMatchBaseModel qtMatchBaseModel : update) {
					ps.setInt(1, qtMatchBaseModel.getMatchStatus());
					ps.setTimestamp(2, new Timestamp(qtMatchBaseModel
							.getMatchTime().getTime()));
					ps.setTimestamp(3,
							qtMatchBaseModel.getHalfStartTime() == null ? null
									: new Timestamp(qtMatchBaseModel
											.getHalfStartTime().getTime()));
					ps.setInt(4, qtMatchBaseModel.getHomeTeamScore());
					ps.setInt(5, qtMatchBaseModel.getGuestTeamScore());
					ps.setInt(6, qtMatchBaseModel.getHomeTeamHalfScore());
					ps.setInt(7, qtMatchBaseModel.getHomeTeamRed());
					ps.setInt(8, qtMatchBaseModel.getGuestTeamRed());
					ps.setInt(9, qtMatchBaseModel.getHomeTeamYellow());
					ps.setInt(10, qtMatchBaseModel.getGuestTeamYellow());
					ps.setDouble(11, qtMatchBaseModel.getHandiCap());

					ps.setInt(12, qtMatchBaseModel.getGuestTeamHalfScore());
					ps.setInt(13, qtMatchBaseModel.getHomeTeamPosition());
					ps.setInt(14, qtMatchBaseModel.getGuestTeamPosition());
					ps.setString(15, qtMatchBaseModel.getTelevison());
					ps.setString(16, qtMatchBaseModel.getJingcaiId());
					ps.setString(17, qtMatchBaseModel.getBsId());
					ps.setInt(18, 1);// 来源为球探
					ps.addBatch();
				}
				ps.executeBatch();

				logger.info("本次更新的比赛数目为{}", update.size());
			}
		});
		session().flush();
		session().clear();
	}

	@Override
	@Transactional
	public String queryLeagueIdByChineseName(String leagueId) {
		return (String) createQuery("select leagueId from LeagueInfoPO where chineseName=:chineseName").setString("chineseName",leagueId).uniqueResult();
	}

}
