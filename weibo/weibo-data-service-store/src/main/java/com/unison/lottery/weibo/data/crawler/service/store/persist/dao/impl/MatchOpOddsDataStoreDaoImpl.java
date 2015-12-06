package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchOpOddsDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * @author 崔桂祥
 * 
 */
@Repository
public class MatchOpOddsDataStoreDaoImpl extends DaoImpl<LeagueInfoPO>
		implements MatchOpOddsDataStoreDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5986081047454814799L;

	public MatchOpOddsDataStoreDaoImpl() {
		super(LeagueInfoPO.class);
	}

	@Override
	@Transactional
	public List<String> queryOddsCompany(Qt_fb_match_oddsType oddsType) {
		List<String> companyList = null;
		switch (oddsType) {
		case euro:
			companyList = createSQLQuery(
					"select b.corpId from md_lottery_corp_base b where b.opType=1")
					.list();
			break;
		case asia:
			companyList = createSQLQuery(
					"select b.corpId from md_lottery_corp_base b where b.ypType=1")
					.list();
			break;
		case ou:
			companyList = createSQLQuery(
					"select b.corpId from md_lottery_corp_base b where b.ouType=1")
					.list();
			break;
		}
		return companyList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<FbMatchBaseInfoPO> queryAllMatchDataHasFinish(int startPos,
			Qt_fb_match_oddsType oddsType) {

		List dataList = null;
//		switch (oddsType) {
//		case asia:
//		case ou:
//			int sqlOddsType = oddsType == Qt_fb_match_oddsType.asia ? 1
//					: oddsType == Qt_fb_match_oddsType.ou ? 2 : 0;
			dataList = createQuery(
			// limit 100
					"select new FbMatchBaseInfoPO(b.id, b.bsId) from FbMatchBaseInfoPO b where b.source=? and b.matchStatus=0 or b.matchStatus=-11 and jingcaiId is not null order by b.id asc ")
					.setInteger(0, 1).setFirstResult(startPos)
					.setMaxResults(10000).list();
//			break;
//		case euro:
//			dataList = createQuery(
//			// limit 100
//					"select new FbMatchBaseInfoPO(b.id, b.bsId) from FbMatchBaseInfoPO b where b.source=? and b.matchStatus in(0,-11) order by b.id asc ")
//					.setInteger(0, 1).setFirstResult(startPos)
//					.setMaxResults(10000).list();
//			break;
//		default:
//			break;
//		}
		return dataList;
	}

	@Override
	@Transactional
	public void storeMatchOpOddsData(final List<QtMatchOpOddsModel> OpOddsData,
			final Qt_fb_match_oddsType oddsType) throws Exception {

		if (OpOddsData != null && !OpOddsData.isEmpty()) {
			// final List<QtMatchEventModel> insert = new ArrayList<>();
			// f
			// if (!OpOddsData.isEmpty()) {
			Session session = session();
			switch (oddsType) {
			case euro:
				List<QtMatchOpOddsModel>  odssList = filterInsertEvent(OpOddsData);
				doInsertEuroOdds(odssList, session);
				break;
			case asia:
			case ou:
				List<QtMatchOpOddsModel>  odssList1=makeInsertAsianOuOdds(OpOddsData,oddsType);
				doInsertAsiaOuOdds(odssList1, oddsType, session);
				break;

			default:
				break;
			}
			session.flush();
			session.clear();
			// }

		}

	}

	private List<QtMatchOpOddsModel> makeInsertAsianOuOdds(
			List<QtMatchOpOddsModel> opOddsData, Qt_fb_match_oddsType oddsType) {
		List<String> timeStamp = createQuery("select str(timestamp) from FbMatchAsiaOuOddsInfoPO where bsId=? and oddsType=? and corpId=?").setInteger(0, opOddsData.get(0).getBsId()).setInteger(1, oddsType==Qt_fb_match_oddsType.ou?2:1)
				.setString(2, opOddsData.get(0).getCorpId()).list();
		List<QtMatchOpOddsModel> qtMatchOpOddsModels = new ArrayList<>();
		for(QtMatchOpOddsModel oddsModel:opOddsData){
			if(!timeStamp.contains(DateFormateUtil.getStringOfDate("yyyy-MM-dd HH:mm:ss",oddsModel.getTimestamp()))){
				qtMatchOpOddsModels.add(oddsModel);
			}
		}
		return qtMatchOpOddsModels;
	}

	private List<QtMatchOpOddsModel> filterInsertEvent(
			List<QtMatchOpOddsModel> opOddsData) {
		List<String> timeStamp = createQuery("select str(timestamp) from FbMatchOpOddsInfoPO where bsId=? and corpId=?")
				.setInteger(0, opOddsData.get(0).getBsId()).setString(1, opOddsData.get(0).getCorpId()).list();
		List<QtMatchOpOddsModel> qtMatchOpOddsModels = new ArrayList<>();
		for(QtMatchOpOddsModel oddsModel:opOddsData){
			if(!timeStamp.contains(DateFormateUtil.getStringOfDate("yyyy-MM-dd HH:mm:ss",oddsModel.getTimestamp()))){
				qtMatchOpOddsModels.add(oddsModel);
			}
		}
		return qtMatchOpOddsModels;
	}

	/**
	 * @param OpOddsData
	 * @param oddsType
	 * @param session
	 * @throws HibernateException
	 */
	private void doInsertAsiaOuOdds(final List<QtMatchOpOddsModel> OpOddsData,
			final Qt_fb_match_oddsType oddsType, Session session)
			throws HibernateException {
		session.doWork(new Work() {

			@Override
			public void execute(Connection connection)
					throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement psAsiaOu = connection
						.prepareStatement("insert into"
								+ " md_qt_odds_asiaOu_base(bsId,source,processStatus,corpId,HomeWinOdds,handicap,GuestWinOdds,timestamp,oddsType,createTime)values"
								+ "(?,?,?,?,?,?,?,?,?,now())");
				int j = 0;
				for (QtMatchOpOddsModel itemMatchOpOddsModel : OpOddsData) {
					psAsiaOu.setInt(1, itemMatchOpOddsModel.getBsId());
					psAsiaOu.setInt(2, itemMatchOpOddsModel.getSource());
					psAsiaOu.setInt(3,
							itemMatchOpOddsModel.getProcessStatus());
					psAsiaOu.setString(4,
							itemMatchOpOddsModel.getCorpId());

					psAsiaOu.setDouble(5,
							itemMatchOpOddsModel.getHomeWinOdds());
					psAsiaOu.setDouble(6,
							itemMatchOpOddsModel.getHandicap());
					psAsiaOu.setDouble(7,
							itemMatchOpOddsModel.getGuestWinOdds());
					psAsiaOu.setTimestamp(8, new Timestamp(
							itemMatchOpOddsModel.getTimestamp()
									.getTime()));
					psAsiaOu.setInt(
							9,
							oddsType == Qt_fb_match_oddsType.asia ? 1
									: oddsType == Qt_fb_match_oddsType.ou ? 2
											: 0);
					psAsiaOu.addBatch();
					if (++j > 10000) {
						psAsiaOu.executeBatch();
					}
				}
				psAsiaOu.executeBatch();
			}
		});
	}

	/**
	 * @param OpOddsData
	 * @param session
	 * @throws HibernateException
	 */
	private void doInsertEuroOdds(final List<QtMatchOpOddsModel> OpOddsData,
			Session session) throws HibernateException {
		session.doWork(new Work() {

			@Override
			public void execute(Connection connection)
					throws SQLException {
				makeEuroOdds(OpOddsData, connection);

			}

			/**
			 * @param OpOddsData
			 * @param connection
			 * @throws SQLException
			 */
			private void makeEuroOdds(
					final List<QtMatchOpOddsModel> OpOddsData,
					Connection connection) throws SQLException {
				PreparedStatement psEuro = connection
						.prepareStatement("insert into"
								+ " md_qt_odds_euro_base(bsId,source,processStatus,corpId,HomeWinOdds,DrawOdds,GuestWinOdds,timestamp,createTime)values"
								+ "(?,?,?,?,?,?,?,?,now())");
				int i = 0;
				for (QtMatchOpOddsModel itemMatchOpOddsModel : OpOddsData) {
					psEuro.setInt(1, itemMatchOpOddsModel.getBsId());
					psEuro.setInt(2, itemMatchOpOddsModel.getSource());
					psEuro.setInt(3,
							itemMatchOpOddsModel.getProcessStatus());
					psEuro.setString(4,
							itemMatchOpOddsModel.getCorpId());

					psEuro.setDouble(5,
							itemMatchOpOddsModel.getHomeWinOdds());
					psEuro.setDouble(6,
							itemMatchOpOddsModel.getDrawOdds());
					psEuro.setDouble(7,
							itemMatchOpOddsModel.getGuestWinOdds());
					psEuro.setTimestamp(8, new Timestamp(
							itemMatchOpOddsModel.getTimestamp()
									.getTime()));

					psEuro.addBatch();
					if (++i > 10000) {
						psEuro.executeBatch();
					}
				}
				psEuro.executeBatch();
			}
		});
	}

	@Override
	@Transactional
	public List<String> queryBasketOddsCompany(Qt_fb_match_oddsType oddsType) {
		return createSQLQuery(
				"select b.corpId from md_qt_basket_lottery_corp_base b").list();
	}

	@Override
	@Transactional
	public List<BasketBallMatchPO> queryAllBasketMatchDataHasFinish(
			int startPos, Qt_fb_match_oddsType oddsType,int source) {
		List dataList = null;
//		switch (oddsType) {
//		case asia:
//		case ou:
//			int sqlOddsType = oddsType == Qt_fb_match_oddsType.asia ? 1
//					: oddsType == Qt_fb_match_oddsType.ou ? 2 : 0;
			dataList = createQuery(
			// limit 100
					"select new BasketBallMatchPO(b.id, b.bsId) from BasketBallMatchPO b where b.source=? and b.matchState=0 or b.matchState=-11 order by b.id asc ")
					.setInteger(0, source).setFirstResult(startPos).setMaxResults(10000).list();
//			break;
//		case euro:
//			dataList = createQuery(
//			// limit 100
//					"select new BasketBallMatchPO(b.id, b.bsId) from BasketBallMatchPO b where b.source=? and b.id not in(select bsId from BasketMatchOpOddsInfoPO) order by b.id asc ")
//					.setInteger(0, 1).setFirstResult(startPos)
//					.setMaxResults(10000).list();
//			break;
//		default:
//			break;
//		}
		return dataList;
	}

	@Override
	@Transactional
	public void storeBasketMatchOpOddsData(
			final List<QtBasketMatchOddsModel> oddsData,
			final Qt_fb_match_oddsType oddsType,final BasketBallMatchPO matchPO) throws Exception {
		if (oddsData != null && !oddsData.isEmpty()) {
			// final List<QtMatchEventModel> insert = new ArrayList<>();
			// filterInsertEvent(insert, OpOddsData);
			// if (!OpOddsData.isEmpty()) {
			Session session = session();
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					switch (oddsType) {
					case euro:
						
						storeEuroOdds(oddsData, connection,matchPO);
						break;
					case asia:
					case ou:
						storeAsianOuOdds(oddsData, oddsType, connection,matchPO);

						break;

					default:
						break;
					}
				}

				/**
				 * @param oddsData
				 * @param oddsType
				 * @param connection
				 * @param matchPO 
				 * @throws SQLException
				 */
				private void storeAsianOuOdds(
						final List<QtBasketMatchOddsModel> oddsData,
						final Qt_fb_match_oddsType oddsType,
						Connection connection, BasketBallMatchPO matchPO) throws SQLException {
					
					int j = 0;
					List<QtBasketMatchOddsModel> oddsModels = makeInsertAssianOuOdds(oddsData,oddsType,matchPO);
					if (!oddsModels.isEmpty()) {
						PreparedStatement psAsiaOu = connection
								.prepareStatement("insert into"
										+ " md_qt_basket_odds_asiaOu_base(bsId,source,processStatus,corpId,HomeWinOdds,handicapOrScore,GuestWinOdds,timestamp,oddsType,createTime)values"
										+ "(?,?,?,?,?,?,?,?,?,now())");
						for (QtBasketMatchOddsModel itemMatchOpOddsModel : oddsModels) {
							psAsiaOu.setInt(1, itemMatchOpOddsModel.getBsId());
							psAsiaOu.setInt(2, itemMatchOpOddsModel.getSource());
							psAsiaOu.setInt(3,
									itemMatchOpOddsModel.getProcessStatus());
							psAsiaOu.setString(4,
									itemMatchOpOddsModel.getCorpId());

							psAsiaOu.setDouble(5,
									itemMatchOpOddsModel.getHomeWinOdds());
							psAsiaOu.setDouble(6,
									itemMatchOpOddsModel.getHandicapOrScore());
							psAsiaOu.setDouble(7,
									itemMatchOpOddsModel.getGuestWinOdds());
							psAsiaOu.setTimestamp(8, new Timestamp(
									itemMatchOpOddsModel.getTimestamp()
											.getTime()));
							psAsiaOu.setInt(
									9,
									oddsType == Qt_fb_match_oddsType.asia ? 1
											: oddsType == Qt_fb_match_oddsType.ou ? 2
													: 0);
							psAsiaOu.addBatch();
							if (++j > 10000) {
								psAsiaOu.executeBatch();
							}
						}
						psAsiaOu.executeBatch();
					}
				}

				private List<QtBasketMatchOddsModel> makeInsertAssianOuOdds(
						List<QtBasketMatchOddsModel> oddsData,
						Qt_fb_match_oddsType oddsType, BasketBallMatchPO matchPO) {
					List<String> timeStamp = createQuery("select str(timestamp) from BasketMatchAsiaOuOddsInfoPO where bsId=? and oddsType=? and corpId=?").setInteger(0, oddsData.get(0).getBsId()).setInteger(1, oddsType==Qt_fb_match_oddsType.ou?2:1)
							.setString(2, oddsData.get(0).getCorpId()).list();
					List<QtBasketMatchOddsModel> qtMatchOpOddsModels = new ArrayList<>();
					for(QtBasketMatchOddsModel oddsModel:oddsData){
						if(!timeStamp.contains(DateFormateUtil.getStringOfDate("yyyy-MM-dd HH:mm:ss",oddsModel.getTimestamp()))){
							qtMatchOpOddsModels.add(oddsModel);
						}
					}
					return qtMatchOpOddsModels;
				}

				/**
				 * @param oddsData
				 * @param connection
				 * @param matchPO 
				 * @throws SQLException
				 */
				private void storeEuroOdds(
						final List<QtBasketMatchOddsModel> oddsData,
						Connection connection, BasketBallMatchPO matchPO) throws SQLException {
					List<QtBasketMatchOddsModel> oddsModels = makeInsertBasketEuroOdds(oddsData,matchPO);
					if (!oddsModels.isEmpty()) {
						PreparedStatement psEuro = connection
								.prepareStatement("insert into"
										+ " md_qt_basket_odds_euro_base(bsId,source,processStatus,corpId,HomeWinOdds,GuestWinOdds,timestamp,createTime)values"
										+ "(?,?,?,?,?,?,?,now())");
						int i = 0;
						for (QtBasketMatchOddsModel itemMatchOpOddsModel : oddsModels) {
							psEuro.setInt(1, itemMatchOpOddsModel.getBsId());
							psEuro.setInt(2, itemMatchOpOddsModel.getSource());
							psEuro.setInt(3,
									itemMatchOpOddsModel.getProcessStatus());
							psEuro.setString(4,
									itemMatchOpOddsModel.getCorpId());

							psEuro.setDouble(5,
									itemMatchOpOddsModel.getHomeWinOdds());
							psEuro.setDouble(6,
									itemMatchOpOddsModel.getGuestWinOdds());
							psEuro.setTimestamp(7, new Timestamp(
									itemMatchOpOddsModel.getTimestamp()
											.getTime()));

							psEuro.addBatch();
							if (++i > 10000) {
								psEuro.executeBatch();
							}
						}
						psEuro.executeBatch();
					}
				}

				private List<QtBasketMatchOddsModel> makeInsertBasketEuroOdds(
						List<QtBasketMatchOddsModel> oddsData,
						BasketBallMatchPO matchPO) {
					@SuppressWarnings("unchecked")
					List<String> timeStamp = createQuery("select str(timestamp) from BasketMatchOpOddsInfoPO where bsId=? and corpId=?")
							.setLong(0, matchPO.getId()).setString(1, oddsData.get(0).getCorpId()).list();
					List<QtBasketMatchOddsModel> qtMatchOpOddsModels = new ArrayList<>();
					for(QtBasketMatchOddsModel oddsModel:oddsData){
						if(!timeStamp.contains(DateFormateUtil.getStringOfDate("yyyy-MM-dd HH:mm:ss",oddsModel.getTimestamp()))){
							qtMatchOpOddsModels.add(oddsModel);
						}
					}
					return qtMatchOpOddsModels;
				}
			});
			session.flush();
			session.clear();
			// }

		}

	}

	// private void filterInsertEvent(List<QtMatchEventModel> insert,
	// List<QtMatchEventModel> matchEvents) {
	// for (QtMatchEventModel qtMatchEventModel : matchEvents) {
	// @SuppressWarnings("unchecked")
	// List<String> shiJianShijianList = createQuery(
	// "select minute from FbMatchEventInfoPO where bsId=? and source=1")
	// .setInteger(0, qtMatchEventModel.getBsId()).list();
	// if (shiJianShijianList != null
	// && shiJianShijianList.contains(qtMatchEventModel
	// .getMinute())) {
	// } else {
	// insert.add(qtMatchEventModel);
	// }
	// }
	// }
	//
	//
	// private void filterInsertStatistic(List<QtMatchStatisticModel> insert,
	// List<QtMatchStatisticModel> matchStatisticModels) {
	// for (QtMatchStatisticModel qtMatchEventModel : matchStatisticModels) {
	// @SuppressWarnings("unchecked")
	// List<String> shiJianShijianList = createQuery(
	// "select minute from FbMatchEventInfoPO where bsId=?")
	// .setInteger(0, qtMatchEventModel.getBsId()).list();
	// // if (shiJianShijianList != null
	// // && shiJianShijianList.contains(qtMatchEventModel
	// // .getMinute())) {
	// // } else {
	// // insert.add(qtMatchEventModel);
	// // }
	// }
	// }

}
