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

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.BasketMatchPlayerStatisticStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.BasketMatchTeamStatisticStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u.c;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchEventModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * 存储球队技术统计
 * @author 崔桂祥
 * 
 */
@Repository
public class BasketMatchTeamStatisticStoreDaoImpl extends
		DaoImpl<LeagueInfoPO> implements BasketMatchTeamStatisticStoreDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6744352194092053986L;
	private static final int LEAGUE_TYPE = 1; // 联赛类型

	public BasketMatchTeamStatisticStoreDaoImpl() {
		super(LeagueInfoPO.class);
	}

	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<BasketBallMatchPO> queryAllMatchDataHasFinish(int startPos) {
		//查询正在比赛的或者比赛时间大于等于当前时间减去4个小时的时间
		return createQuery(
				"select new BasketBallMatchPO(b.id, b.bsId) from BasketBallMatchPO b where b.source=? and (b.matchState=? or b.matchState = ? or b.matchstate=? or b.matchState=? or b.matchState = ? or b.matchState = ?) and MatchTime>=? order by b.id asc "
				)
				.setInteger(0, 1).setInteger(1, 1).setInteger(2, 2).setInteger(3, 3).setInteger(4, 4).setInteger(5, 5).setInteger(6, -1).setMaxResults(10000).setFirstResult(startPos).list();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public void storeMatchTeamStatisticData(
			final List<QtBasketMatchTeamStatisticModel> dataModels) throws Exception {
		if (dataModels != null && !dataModels.isEmpty()) {
			Map<String,List<QtBasketMatchTeamStatisticModel>> matchTeamStatisticMap = makeInsertTeamStatistics(dataModels);
			final List<QtBasketMatchTeamStatisticModel> insert = matchTeamStatisticMap.get("1");
			final List<QtBasketMatchTeamStatisticModel> update = matchTeamStatisticMap.get("2");
			
			Session session = session();
			if(!insert.isEmpty()){
				session.doWork(new Work() {
					@Override
					public void execute(Connection connection)
							throws SQLException {
						PreparedStatement ps = connection
								.prepareStatement("insert into"
										+ " md_qt_bb_match_teamstatistic_base(bsId,source,processStatus,eventType,zd,kd,createTime)values"
										+ "(?,?,?,?,?,?,now())");
						int i = 0;
						for (QtBasketMatchTeamStatisticModel matchEventPO : insert) {
							ps.setInt(1, matchEventPO.getBsId());
							ps.setInt(2, matchEventPO.getSource());
							ps.setInt(3, matchEventPO.getProcessStatus());
							ps.setString(4, matchEventPO.getEventType());
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
			}
			if(!update.isEmpty()){
				session.doWork(new Work() {
					
					@Override
					public void execute(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement("update md_qt_bb_match_teamstatistic_base"
								+ " set zd=?,kd=? where eventType=? and bsId=?");
						for (QtBasketMatchTeamStatisticModel matchEventPO : update) {
							ps.setString(1, matchEventPO.getZd());
							ps.setString(2, matchEventPO.getKd());
							ps.setString(3, matchEventPO.getEventType());
							ps.setInt(4, matchEventPO.getBsId());
							ps.addBatch();
						}
						ps.executeBatch();
						
					}
				});
			}
				session.flush();
				session.clear();
		}	
		
	}



	private Map<String,List<QtBasketMatchTeamStatisticModel>> makeInsertTeamStatistics(
			List<QtBasketMatchTeamStatisticModel> dataModels) {
		List<String> eventType = createQuery("select eventType from BasketMatchTeamStatisticPO where bsId=?").setInteger(0, dataModels.get(0).getBsId()).list();
		List<QtBasketMatchTeamStatisticModel> insert = new ArrayList<>();
		List<QtBasketMatchTeamStatisticModel> update = new ArrayList<>();
		Map<String,List<QtBasketMatchTeamStatisticModel>> updateMap = new HashMap<String, List<QtBasketMatchTeamStatisticModel>>();
		for(QtBasketMatchTeamStatisticModel teamStatisticModel:dataModels){
			if(eventType.contains(teamStatisticModel.getEventType())){
				update.add(teamStatisticModel);
			}else {
				insert.add(teamStatisticModel);
			}
		}
		updateMap.put("1", insert);
		updateMap.put("2", update);
		return updateMap;
	}


	
}
