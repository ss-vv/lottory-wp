package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.WeakReferenceMonitor.ReleaseListener;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueScoreDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.impl.BasicDaoImpl;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreRuleModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;

/**
 * @author 彭保星
 *
 * @since 2014年11月11日上午11:37:29
 */
@Repository
public class LeagueScoreDaoImpl extends BasicDaoImpl<FbLeagueScoreModel>
		implements LeagueScoreDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1849372562101801299L;

	public LeagueScoreDaoImpl() {
		super(FbLeagueScoreModel.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void storeLeagueScoreData(Map<String, Object> scoreRuleMap, SeasonModel seasonModel) {
		if (scoreRuleMap != null) {
			insertLeagueRule(scoreRuleMap, seasonModel);
			insertLeagueScore(scoreRuleMap,seasonModel);
		}
	}

	/**
	 * @param scoreRuleMap
	 * @param seasonModel 
	 * @throws HibernateException
	 */
	private void insertLeagueScore(Map<String, Object> scoreRuleMap, final SeasonModel seasonModel)
			throws HibernateException {
		if (scoreRuleMap.get("2") != null) {
			@SuppressWarnings("unchecked")
			final List<FbLeagueScoreModel> ruleModels = (List<FbLeagueScoreModel>) scoreRuleMap
					.get("2");
			Map<String, List<FbLeagueScoreModel>> fMap = new HashMap<>();
			makeInsertAndUpdateLeagueScore(ruleModels,fMap,seasonModel,1);
			final List<FbLeagueScoreModel> insert = fMap.get("1");
			final List<FbLeagueScoreModel> update = fMap.get("2");
			Session session = session();
			if (!insert.isEmpty()) {
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						// TODO Auto-generated method stub
						PreparedStatement ps = connection
								.prepareStatement("insert into md_fb_league_score_base(rank,teamName,teamId,"
										+ "processStatus,source,totalMatches,winMatches,loseMatches,drawMatches,"
										+ "goal,miss,score,scoreType,leagueId,seasonName,leagueType,ruleNum,seasonId,subLeagueId,createTime)values(?,?,?,?,?,?,?,?,?,?,?,?"
										+ ",?,?,?,?,?,?,?,now())");
						for (FbLeagueScoreModel scoreModel : insert) {
							ps.setInt(1, scoreModel.getRank() == null ? -1
									: scoreModel.getRank());
							ps.setString(2, scoreModel.getTeamName());
							ps.setString(3, scoreModel.getTeamId());
							ps.setInt(4, scoreModel.getProcessStatus());
							ps.setInt(5, scoreModel.getSource());
							ps.setInt(6,
									scoreModel.getTotalMatches() == null ? 0
											: scoreModel.getTotalMatches());
							ps.setInt(7, scoreModel.getWinMatches() == null ? 0
									: scoreModel.getWinMatches());
							ps.setInt(8,
									scoreModel.getLoseMatches() == null ? 0
											: scoreModel.getLoseMatches());
							ps.setInt(9,
									scoreModel.getDrawMatches() == null ? 0
											: scoreModel.getDrawMatches());
							ps.setInt(10, scoreModel.getGoal() == null ? 0
									: scoreModel.getGoal());
							ps.setInt(11, scoreModel.getMiss() == null ? 0
									: scoreModel.getMiss());
							ps.setInt(12, scoreModel.getScore() == null ? 0
									: scoreModel.getScore());
							ps.setInt(13, scoreModel.getScoreType());
							ps.setString(14, seasonModel.getLeagueId());
							ps.setString(15, seasonModel.getSeasonName());
							ps.setInt(16, scoreModel.getLeagueType());
							ps.setInt(17, scoreModel.getRuleNum());
							ps.setInt(18, scoreModel.getSeasonId());
							ps.setString(19, seasonModel.getSubLeagueId());
							ps.addBatch();
						}
						ps.executeBatch();
					}
				});
			}
			if(!update.isEmpty()){
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						// TODO Auto-generated method stub
						PreparedStatement ps = connection
								.prepareStatement("update md_fb_league_score_base set rank=?,"
										+ "totalMatches=?,winMatches=?,loseMatches=?,drawMatches=?,"
										+ "goal=?,miss=?,score=?,scoreType=?,ruleNum=?,updateTime=now() where seasonId=? and teamId=?");
						for (FbLeagueScoreModel scoreModel : update) {
							ps.setInt(1, scoreModel.getRank() == null ? -1
									: scoreModel.getRank());
							ps.setInt(2,
									scoreModel.getTotalMatches() == null ? 0
											: scoreModel.getTotalMatches());
							ps.setInt(3, scoreModel.getWinMatches() == null ? 0
									: scoreModel.getWinMatches());
							ps.setInt(4,
									scoreModel.getLoseMatches() == null ? 0
											: scoreModel.getLoseMatches());
							ps.setInt(5,
									scoreModel.getDrawMatches() == null ? 0
											: scoreModel.getDrawMatches());
							ps.setInt(6, scoreModel.getGoal() == null ? 0
									: scoreModel.getGoal());
							ps.setInt(7, scoreModel.getMiss() == null ? 0
									: scoreModel.getMiss());
							ps.setInt(8, scoreModel.getScore() == null ? 0
									: scoreModel.getScore());
							ps.setInt(9, scoreModel.getScoreType());
							ps.setInt(10, scoreModel.getRuleNum());
							ps.setInt(11, seasonModel.getSeasonId());
							ps.setString(12, scoreModel.getTeamId());
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

	private void makeInsertAndUpdateLeagueScore(
			List<FbLeagueScoreModel> ruleModels,
			Map<String, List<FbLeagueScoreModel>> fMap, SeasonModel seasonModel,int type) {
		List<String> seasonIds = createQuery("select concat(seasonId,teamId) from FbLeagueScorePO a,LeagueInfoPO b where a.leagueId=b.leagueId and b.type=? and a.source=1 and b.source=1 and locate(?,seasonName)>0 ").setInteger(0, type).setString(1,seasonModel.getSeasonName()).list();
		String unique = String.valueOf(seasonModel.getSeasonId());
		List<FbLeagueScoreModel> fbinsert = new ArrayList<>();
		List<FbLeagueScoreModel> fbUpdate = new ArrayList<>();
		for(FbLeagueScoreModel leagueScoreModel:ruleModels){
			if(seasonIds.contains(unique+leagueScoreModel.getTeamId())){
				fbUpdate.add(leagueScoreModel);
			}else {
				fbinsert.add(leagueScoreModel);
			}
		}
		fMap.put("1", fbinsert);
		fMap.put("2", fbUpdate);
	}

	/**
	 * @param scoreRuleMap
	 * @param seasonModel 
	 * @throws HibernateException
	 */
	private void insertLeagueRule(Map<String, Object> scoreRuleMap, final SeasonModel seasonModel)
			throws HibernateException {
		if (scoreRuleMap.get("1") != null) {
			@SuppressWarnings("unchecked")
			final List<FbLeagueScoreRuleModel> ruleModels = (List<FbLeagueScoreRuleModel>) scoreRuleMap
					.get("1");
			Map<String, List<FbLeagueScoreRuleModel>> fMap = new HashMap<String, List<FbLeagueScoreRuleModel>>();
			makeInsertAndUpdateRule(ruleModels,fMap, seasonModel);
			final List<FbLeagueScoreRuleModel> fbLeagueScoreRuleModels = fMap.get("1");
			if (fbLeagueScoreRuleModels!=null&&!fbLeagueScoreRuleModels.isEmpty()) {
				session().doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						// TODO Auto-generated method stub
						PreparedStatement ps = connection
								.prepareStatement("insert into md_fb_rule_detail_base(seasonId,source,processStatus,"
										+ "ruleName,ruleNum,ruleColor,leagueId,subLeagueId,seasonName,createTime)values(?,?,?,?,?,?,?,?,?,now())");
						for (FbLeagueScoreRuleModel ruleModel : fbLeagueScoreRuleModels) {
							ps.setInt(1, ruleModel.getSeasonId());
							ps.setInt(2, ruleModel.getSource());
							ps.setInt(3, ruleModel.getProcessStatus());
							ps.setString(4, ruleModel.getRuleName());
							ps.setInt(5, ruleModel.getRuleNum());
							ps.setString(6, ruleModel.getRuleColor());
							ps.setString(7, seasonModel.getLeagueId());
							ps.setString(8, seasonModel.getSubLeagueId());
							ps.setString(9,seasonModel.getSeasonName());
							ps.addBatch();
						}
						ps.executeBatch();
					}
				});
			}
		}
	}

	private void makeInsertAndUpdateRule(List<FbLeagueScoreRuleModel> ruleModels, Map<String, List<FbLeagueScoreRuleModel>> fMap, SeasonModel seasonModel) {
		List<Integer> seasonIds = createQuery("select distinct(seasonId) from FbLeagueScoreRulePO where locate(?,seasonName)>0").setString(0, seasonModel.getSeasonName()).list();
		Integer unique = seasonModel.getSeasonId();

		if(!seasonIds.contains(unique)){
			fMap.put("1",  ruleModels);
		}
		
	}

	@Override
	@Transactional
	public void storeCupScoreData(final List<FbLeagueScoreModel> fbLeagueScoreModels,final SeasonModel seasonModel) {
		if(!fbLeagueScoreModels.isEmpty()){
			Map<String,List<FbLeagueScoreModel>> fmMap = new HashMap<>();
			makeInsertAndUpdateLeagueScore(fbLeagueScoreModels, fmMap, seasonModel,2);
			final List<FbLeagueScoreModel> insert = fmMap.get("1");
			final List<FbLeagueScoreModel> update = fmMap.get("2");
			Session session = session();
			if (!insert.isEmpty()) {
				
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						// TODO Auto-generated method stub
						PreparedStatement ps = connection
								.prepareStatement("insert into md_fb_league_score_base(rank,teamName,teamId,"
										+ "processStatus,source,totalMatches,winMatches,loseMatches,drawMatches,"
										+ "goal,miss,score,scoreType,leagueId,seasonName,leagueType,ruleNum,seasonId,`group`,teamColor,subLeagueId,createTime)values(?,?,?,?,?,?,?,?,?,?,?,?,?"
										+ ",?,?,?,?,?,?,?,?,now())");
						for (FbLeagueScoreModel scoreModel : insert) {
							ps.setInt(1, scoreModel.getRank() == null ? -1
									: scoreModel.getRank());
							ps.setString(2, scoreModel.getTeamName());
							ps.setString(3, scoreModel.getTeamId());
							ps.setInt(4, scoreModel.getProcessStatus());
							ps.setInt(5, scoreModel.getSource());
							ps.setInt(6, scoreModel.getTotalMatches());
							ps.setInt(7, scoreModel.getWinMatches());
							ps.setInt(8, scoreModel.getLoseMatches());
							ps.setInt(9, scoreModel.getDrawMatches());
							ps.setInt(10, scoreModel.getGoal());
							ps.setInt(11, scoreModel.getMiss());
							ps.setInt(12, scoreModel.getScore());
							ps.setInt(13, 0);
							ps.setString(14, seasonModel.getLeagueId());
							ps.setString(15, seasonModel.getSeasonName());
							ps.setInt(16, 2);
							ps.setInt(17, scoreModel.getRuleNum());
							ps.setInt(18, seasonModel.getSeasonId());
							ps.setString(19, scoreModel.getGroup());
							ps.setString(20, scoreModel.getTeamColor());
							ps.setString(21, seasonModel.getSubLeagueId());
							ps.addBatch();
						}
						ps.executeBatch();

					}
				});
			}
			if(!update.isEmpty()){
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						// TODO Auto-generated method stub
						PreparedStatement ps = connection
								.prepareStatement("update md_fb_league_score_base set rank=?,"
										+ "totalMatches=?,winMatches=?,loseMatches=?,drawMatches=?,"
										+ "goal=?,miss=?,score=?,ruleNum=?,updateTime=now(),`group`=?,teamColor=? where seasonId=? and teamId=?");
						for (FbLeagueScoreModel scoreModel : update) {
							ps.setInt(1, scoreModel.getRank() == null ? -1
									: scoreModel.getRank());
							ps.setInt(2,scoreModel.getTotalMatches() );
							ps.setInt(3, scoreModel.getWinMatches());
							ps.setInt(4,scoreModel.getLoseMatches());
							ps.setInt(5,scoreModel.getDrawMatches());
							ps.setInt(6, scoreModel.getGoal());
							ps.setInt(7, scoreModel.getMiss());
							ps.setInt(8, scoreModel.getScore() );
							ps.setInt(9, scoreModel.getRuleNum());
							ps.setString(10, scoreModel.getGroup());
							ps.setString(11, scoreModel.getTeamColor());
							ps.setInt(12, seasonModel.getSeasonId());
							ps.setString(13, scoreModel.getTeamId());
							
							ps.addBatch();
						}
						ps.executeBatch();
					}
				});
			}
			session.flush();
			session.clear();
		}
//			updateSeasonIsCrawler(1,seasonModel);
//		}else {
//			updateSeasonIsCrawler(3, seasonModel);
//		}
	}

	/**
	 * 更新是否抓取杯赛赛季状态
	 * @param isCrawler
	 * @param seasonModel
	 * @throws HibernateException
	 */
	private void updateSeasonIsCrawler(Integer isCrawler,SeasonModel seasonModel)
			throws HibernateException {
		createQuery("update FbLeagueSeasonBasePO set isCrawler=?,updateTime=now() where seasonId=?").setInteger(0, isCrawler).setInteger(1, seasonModel.getSeasonId()).executeUpdate();
	}

}
