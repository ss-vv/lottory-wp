package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.OddsCompanyStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtOddsCompanyModel;
import com.xhcms.commons.persist.hibernate.DaoImpl;

@Repository
public class OddsCompanyStoreDaoImpl extends DaoImpl<LeagueInfoPO>
	implements OddsCompanyStoreDao {	

	@Override
	public void crawlerOdddCompany() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void storeOdddCompany(final List<QtOddsCompanyModel> oddsCompanyModels) throws Exception{
			if (oddsCompanyModels != null && !oddsCompanyModels.isEmpty()) {
				Session session = session();
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						PreparedStatement ps = connection
								.prepareStatement("insert into"
										+ " md_lottery_corp_base(corpId,corpName,source,processStatus,ypType,opType,ouType,createTime)values"
										+ "(?,?,?,?,?,?,?,now())");
						int i = 0;
						for (QtOddsCompanyModel itemModel : oddsCompanyModels) {
							ps.setInt(1, itemModel.getCorpId());
							ps.setString(2, itemModel.getCorpName());
							ps.setInt(3, itemModel.getSource());
							ps.setInt(4, itemModel.getProcessStatus());
							ps.setInt(5, itemModel.getYpType()); // 
							ps.setInt(6, itemModel.getOpType()); 
							ps.setInt(7, itemModel.getOuType()); 
							ps.addBatch();
							if (++i > 10000) {
								ps.executeBatch();
							}
						}
						ps.executeBatch();
					}
				});
			}	
	}

	@Override
	@Transactional
	public void storeAsianOddDatas(List<QtMatchOpOddsModel> opOddsData,Qt_fb_match_oddsType oddsType) {
		if(opOddsData != null && !opOddsData.isEmpty()){
			Query query = null;
			try {
				for(QtMatchOpOddsModel qt : opOddsData){
					query = createQuery("delete FbMatchAsiaOuOddsInfoPO f where f.bsId =:bsIds and f.oddsType=:oddsType and f.corpId=:corpId");
					query.setParameter("bsIds", qt.getBsId()).setParameter("oddsType", oddsType==Qt_fb_match_oddsType.ou?2:1).setParameter("corpId", qt.getCorpId()).executeUpdate();
					break;
				}
				doInsertAsiaOuOdds(opOddsData,oddsType, session());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	private void doInsertAsiaOuOdds(final List<QtMatchOpOddsModel> OpOddsData,
			final Qt_fb_match_oddsType oddsType, Session session)
			throws HibernateException {
		session.doWork(new Work() {

			@Override
			public void execute(Connection connection)
					throws SQLException {
				PreparedStatement psAsiaOu = connection.prepareStatement("insert into"
					+ " md_qt_odds_asiaOu_base(bsId,source,processStatus,corpId,HomeWinOdds,handicap,GuestWinOdds,timestamp,oddsType,createTime)values"
					+ "(?,?,?,?,?,?,?,?,?,now())");
				int j = 0;
				for (QtMatchOpOddsModel itemMatchOpOddsModel : OpOddsData) {
					psAsiaOu.setInt(1, itemMatchOpOddsModel.getBsId());
					psAsiaOu.setInt(2, 1);
					psAsiaOu.setInt(3, 0);
					psAsiaOu.setString(4, itemMatchOpOddsModel.getCorpId());
					psAsiaOu.setDouble(5, itemMatchOpOddsModel.getHomeWinOdds());
					psAsiaOu.setDouble(6, itemMatchOpOddsModel.getHandicap());
					psAsiaOu.setDouble(7, itemMatchOpOddsModel.getGuestWinOdds());
					psAsiaOu.setTimestamp(8, new Timestamp(itemMatchOpOddsModel.getTimestamp().getTime()));
					psAsiaOu.setInt(9,oddsType == Qt_fb_match_oddsType.asia ? 1 : oddsType == Qt_fb_match_oddsType.ou ? 2 : 0);
					psAsiaOu.addBatch();
					if (++j > 10000) {
						psAsiaOu.executeBatch();
					}
				}
				psAsiaOu.executeBatch();
			}
		});
	}
}
