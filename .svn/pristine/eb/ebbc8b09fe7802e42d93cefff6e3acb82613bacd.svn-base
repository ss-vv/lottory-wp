package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.BasketMatchPlayerStatisticStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.BasketMatchTeamStatisticStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchEventModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * 存储球员技术统计
 * 
 * @author 崔桂祥
 * 
 */
@Repository
public class BasketMatchPlayerStatisticStoreDaoImpl extends
		DaoImpl<LeagueInfoPO> implements BasketMatchPlayerStatisticStoreDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6744352194092053986L;
	private static final int LEAGUE_TYPE = 1; // 联赛类型

	public BasketMatchPlayerStatisticStoreDaoImpl() {
		super(LeagueInfoPO.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<BasketBallMatchPO> queryAllMatchDataHasFinish(int startPos) {
		return createQuery(
				"select new BasketBallMatchPO(b.id, b.bsId) from BasketBallMatchPO b where b.source=? and b.matchState = ? and b.id not in(select bsId from BasketMatchPlayerStatisticPO) order by b.id asc ")
				.setInteger(0, 1).setInteger(1, -1).setMaxResults(10000)
				.setFirstResult(startPos).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public void storeMatchPlayerStatisticData(
			final List<QtBasketMatchPlayerStatisticModel> dataModels)
			throws Exception {
		if (dataModels != null && !dataModels.isEmpty()) {
			Session session = session();
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {

					PreparedStatement ps = connection
							.prepareStatement("insert into"
									+ " md_qt_bb_match_playerstatistic_base(bsId,source,processStatus,`playerId`, `playerName`, "
									+ "`playerNickName`, `playerEnglishName`, `starter`, `lineUp`, `shootHitNumber`, `shootTotalNumber`, "
									+ "`threeHitNumber`, `threeTotalNumber`, `penaltyHitNumber`, `penaltyTotalNunber`, `backboardAttackNumber`,"
									+ " `backboardDefenceNumber`, `backboardTotalNumber`, `assist`, `foul`, `steal`, `fault`, `blockedShot`,"
									+ " `score`, `homeGuestType`,createTime)values"
									+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())");
					int i = 0;
					for (QtBasketMatchPlayerStatisticModel matchEventPO : dataModels) {
						ps.setInt(1, matchEventPO.getBsId());
						ps.setInt(2, matchEventPO.getSource());
						ps.setInt(3, matchEventPO.getProcessStatus());
						
						ps.setString(4, matchEventPO.getPlayerId());
						ps.setString(5, matchEventPO.getPlayerName());
						ps.setString(6, matchEventPO.getPlayerNickName());
						ps.setString(7, matchEventPO.getPlayerEnglishName());
						ps.setString(8, matchEventPO.getStarter());
						ps.setInt(9, matchEventPO.getLineUp());
						ps.setInt(10, matchEventPO.getShootHitNumber());
						ps.setInt(11, matchEventPO.getShootTotalNumber());
						ps.setInt(12, matchEventPO.getThreeHitNumber());
						ps.setInt(13, matchEventPO.getThreeTotalNumber());
						ps.setInt(14, matchEventPO.getPenaltyHitNumber());
						ps.setInt(15, matchEventPO.getPenaltyTotalNunber());
						ps.setInt(16, matchEventPO.getBackboardAttackNumber());
						ps.setInt(17, matchEventPO.getBackboardDefenceNumber());
						ps.setInt(18, matchEventPO.getBackboardTotalNumber());
						ps.setInt(19, matchEventPO.getAssist());
						ps.setInt(20, matchEventPO.getFoul());
						ps.setInt(21, matchEventPO.getSteal());
						ps.setInt(22, matchEventPO.getFault());
						ps.setInt(23, matchEventPO.getBlockedShot());
						ps.setInt(24, matchEventPO.getScore());
						ps.setInt(25, matchEventPO.getHomeGuestType());
						ps.addBatch();
						if (++i > 10000) {
							ps.executeBatch();
						}
					}
					ps.executeBatch();
				}
			});
			session.flush();
			session.clear();
		}

	}

}
