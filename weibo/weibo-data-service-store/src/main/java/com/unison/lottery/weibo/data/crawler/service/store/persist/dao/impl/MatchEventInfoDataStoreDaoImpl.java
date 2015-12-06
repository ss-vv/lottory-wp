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

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchEventInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchEventInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.lottery.weibo.data.service.store.data.QTMatchVO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchEventModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.TeamModel;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.dc.data.Match;

/**
 * @author 崔桂祥
 * 
 */
@Repository
public class MatchEventInfoDataStoreDaoImpl extends
		DaoImpl<LeagueInfoPO> implements MatchEventInfoDataStoreDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8389821800607976350L;
	private static final int LEAGUE_TYPE = 1; // 联赛类型

	public MatchEventInfoDataStoreDaoImpl() {
		super(LeagueInfoPO.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<FbMatchBaseInfoPO> queryAllMatchDataHasFinish(int startPos) {
		return createQuery(
		// limit 100
				//抓取当前赛季事件的sql
				"select new FbMatchBaseInfoPO(b.id, b.bsId) from FbMatchBaseInfoPO b where b.source=? and b.matchStatus = ? or matchStatus=? or matchStatus=? or matchStatus=? ")
				.setInteger(0, 1).setInteger(1, 1).setInteger(2, 2)
				.setInteger(3, 3).setInteger(4, 4).list();
		//抓取历史sql
//				"select new FbMatchBaseInfoPO(b.id, b.bsId) from FbMatchBaseInfoPO b where b.source=? and b.matchStatus = ? and b.id>=587837 and b.id not in(select bsId from FbMatchEventInfoPO) order by b.id asc "
//				)
//				.setInteger(0, 1).setInteger(1, -1).setMaxResults(10000).setFirstResult(startPos).list();
	}

	@Override
	@Transactional
	public void storeMatchEventData(final List<QtMatchEventModel> matchEvents)
			throws Exception {

		if (matchEvents != null && !matchEvents.isEmpty()) {
			final List<QtMatchEventModel> insert = new ArrayList<>();
			filterInsertEvent(insert, matchEvents);
			if (!insert.isEmpty()) {
				Session session = session();
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						PreparedStatement ps = connection
								.prepareStatement("insert into"
										+ " md_qt_match_event_base(bsId,eventType,source,processStatus,minute,eventDetail,teamType,createTime)values"
										+ "(?,?,?,?,?,?,?,now())");
						int i = 0;
						for (QtMatchEventModel matchEventPO : insert) {
							ps.setInt(1, matchEventPO.getBsId());
							ps.setInt(2, matchEventPO.getEventType());
							ps.setInt(3, matchEventPO.getSource());
							ps.setInt(4, matchEventPO.getProcessStatus());
							ps.setString(5, matchEventPO.getMinute()); //
							ps.setString(6, matchEventPO.getEventDetail());
							ps.setInt(7, matchEventPO.getTeamType());
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

	private void filterInsertEvent(List<QtMatchEventModel> insert,
			List<QtMatchEventModel> matchEvents) {
		for (QtMatchEventModel qtMatchEventModel : matchEvents) {
			@SuppressWarnings("unchecked")
			List<String> shiJianShijianList = createQuery(
					"select minute from FbMatchEventInfoPO where bsId=? and source=1")
					.setInteger(0, qtMatchEventModel.getBsId()).list();
			if (shiJianShijianList != null
					&& shiJianShijianList.contains(qtMatchEventModel
							.getMinute())) {
			} else {
				insert.add(qtMatchEventModel);
			}
		}
	}

	@Override
	@Transactional
	public void storeMatchStatisticData(
			final List<QtMatchStatisticModel> matchStatisticModels)
			throws Exception {
		if (matchStatisticModels != null && !matchStatisticModels.isEmpty()) {
//			final List<QtMatchStatisticModel> insert = new ArrayList<>();
//			filterInsertStatistic(insert, matchStatisticModels);
//			if (!matchStatisticModels.isEmpty()) {
			createQuery("delete from FbMatchStatisticInfoPO where bsId=?").setInteger(0, matchStatisticModels.get(0).getBsId()).executeUpdate();
				Session session = session();
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						PreparedStatement ps = connection
								.prepareStatement("insert into"
										+ " md_qt_match_statistic_base(bsId,eventType,source,processStatus,zd,kd,createTime)values"
										+ "(?,?,?,?,?,?,now())");
						int i = 0;
						for (QtMatchStatisticModel matchEventPO : matchStatisticModels) {
							ps.setInt(1, matchEventPO.getBsId());
							ps.setInt(2, matchEventPO.getEventType());
							ps.setInt(3, matchEventPO.getSource());
							ps.setInt(4, matchEventPO.getProcessStatus());
							ps.setString(5, matchEventPO.getZd()); //
							ps.setString(6, matchEventPO.getKd());
							ps.addBatch();
							if (++i > 10000) {
								ps.executeBatch();
							}
						}
						ps.executeBatch();
					}
				});
				session().flush();
				session.clear();
//			}
		}
	}

	private void filterInsertStatistic(List<QtMatchStatisticModel> insert,
			List<QtMatchStatisticModel> matchStatisticModels) {
		for (QtMatchStatisticModel qtMatchEventModel : matchStatisticModels) {
			@SuppressWarnings("unchecked")
			List<String> shiJianShijianList = createQuery(
					"select minute from FbMatchEventInfoPO where bsId=?")
					.setInteger(0, qtMatchEventModel.getBsId()).list();
//			if (shiJianShijianList != null
//					&& shiJianShijianList.contains(qtMatchEventModel
//							.getMinute())) {
//			} else {
//				insert.add(qtMatchEventModel);
//			}
		}
	}

}
