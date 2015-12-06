package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchEventInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchLineupDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchEventInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchEventModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchLineupModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.TeamModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * @author 崔桂祥
 * 
 */
@Repository
public class MatchLineupDataStoreDaoImpl extends DaoImpl<LeagueInfoPO>
		implements MatchLineupDataStoreDao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2665066519647816701L;
	private static final int LEAGUE_TYPE = 1; // 联赛类型

	public MatchLineupDataStoreDaoImpl() {
		super(LeagueInfoPO.class);
	}


	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<FbMatchBaseInfoPO> queryAllMatchDataHasFinish(int startPos) {
		return createQuery(
//				 limit 100
				"select new FbMatchBaseInfoPO(b.id, b.bsId) from FbMatchBaseInfoPO b where b.source=? and b.matchStatus = ? or matchStatus=? and b.id not in(select bsId from FbMatchLineupInfoPO) order by b.id asc "
				)
				.setInteger(0, 1).setInteger(1, 2).setInteger(2, 1).list();
//		return createQuery(
////				 limit 100
//				"select new FbMatchBaseInfoPO(b.id, b.bsId) from FbMatchBaseInfoPO b where b.source=? and b.id>=680521 and b.matchStatus = ? and b.id not in(select bsId from FbMatchLineupInfoPO) order by b.id asc "
//				)
//				.setInteger(0, 1).setInteger(1, -1).setMaxResults(10000).setFirstResult(startPos).list();
	}

	@Override
	@Transactional
	public void storeMatchLineupData(final QtMatchLineupModel matchLineupModel)
			throws Exception {
		if (matchLineupModel != null) {
			
			session()
			.createSQLQuery(
					"insert into"
							+ " md_qt_match_lineup_base(bsId,source,processStatus,zdsf,zdtb,zds,zdt,kdsf,kdtb,kds,kdt,createTime)values"
							+ "(?,?,?,?,?,?,?,?,?,?,?,now())")
			.setString(0, matchLineupModel.getBsId()+"").setInteger(1, matchLineupModel.getSource()).setInteger(2, matchLineupModel.getProcessStatus())
			.setString(3, matchLineupModel.getZdsf()).setString(4, matchLineupModel.getZdtb()).setString(5, matchLineupModel.getZds()) 
			.setString(6, matchLineupModel.getZdt()).setString(7, matchLineupModel.getKdsf()).setString(8, matchLineupModel.getKdtb())
			.setString(9, matchLineupModel.getKds()).setString(10, matchLineupModel.getKdt()).executeUpdate();
			session().flush();

	}
}
}
