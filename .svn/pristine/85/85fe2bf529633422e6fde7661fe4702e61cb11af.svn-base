package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LqMatchInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.service.store.persist.dao.impl.BasicDaoImpl;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Constants;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;

/**
 * 篮球
 * 
 * @author 彭保星
 *
 * @since 2014年11月19日下午5:50:37
 */
@Repository
public class LqMatchInfoDataStoreDaoImpl extends
		BasicDaoImpl<BasketBallMatchPO> implements LqMatchInfoDataStoreDao {

	private static final String SCORE = "score";
	private static final String MATCH = "match";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2093477437967962160L;

	public LqMatchInfoDataStoreDaoImpl() {
		super(BasketBallMatchPO.class);
	}

	@Override
	@Transactional
	public void storeJishiMatchInfo(List<BasketBallMatchModel> ballMatchModels,
			final boolean isJingcai) {
		Map<String, List<BasketBallMatchModel>> matchMap = new HashMap<String, List<BasketBallMatchModel>>();
		makeInsertAndUpdate(matchMap, ballMatchModels);
		final List<BasketBallMatchModel> updateBallMatchModels = matchMap
				.get("1");
		final List<BasketBallMatchModel> insertBallMatchModels = matchMap
				.get("2");
		Session session = session();
		if (!updateBallMatchModels.isEmpty()) {
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					String sql = "update md_bb_match_base set "
							+ " color=?,matchTime=?,matchState=?,homeTeam=?,guestTeam=?,homeScore=?"
							+ ",guestScore=?,letBallNum=?,explainContent=?,homeOne=?,guestOne=?,homeTwo=?,guestTwo=?"
							+ ",homeThree=?,guestThree=?,homeFour=?,guestFour=?,remainTime=?,homeAddTime1=?,guestAddTime1=?,name=?,updateTime=now()";
					if (isJingcai) {
						sql += ",jingcaiId=? ";
					}
					sql += " where bsId=?";
					PreparedStatement ps = connection.prepareStatement(sql);
					for (BasketBallMatchModel ballMatchModel : updateBallMatchModels) {

						ps.setString(1, ballMatchModel.getColor());
						ps.setTimestamp(2,
								ballMatchModel.getMatchTime() == null ? null
										: new Timestamp(ballMatchModel
												.getMatchTime().getTime()));
						ps.setInt(3, ballMatchModel.getMatchState());
						ps.setString(4, ballMatchModel.getHomeTeam());
						ps.setString(5, ballMatchModel.getGuestTeam());
						ps.setInt(6, ballMatchModel.getHomeScore());
						ps.setInt(7, ballMatchModel.getGuestScore());
						ps.setInt(8, ballMatchModel.getLetBallNum());
						if(ballMatchModel.getExplainContent()!=null){
							try {
								ps.setBlob(9, Hibernate.createBlob(ballMatchModel.getExplainContent().getBytes("utf-8")));
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							ps.setBlob(9, Hibernate.createBlob("".getBytes()));
						}
						ps.setInt(10, ballMatchModel.getHomeOne());
						ps.setInt(11, ballMatchModel.getGuestOne());
						ps.setInt(12, ballMatchModel.getHomeTwo());
						ps.setInt(13, ballMatchModel.getGuestTwo());
						ps.setInt(14, ballMatchModel.getHomeThree());
						ps.setInt(15, ballMatchModel.getGuestThree());
						ps.setInt(16, ballMatchModel.getHomeFour());
						ps.setInt(17, ballMatchModel.getGuestFour());
						ps.setString(18, ballMatchModel.getRemainTime());
						ps.setInt(19, ballMatchModel.getHomeAddTime1());
						ps.setInt(20, ballMatchModel.getGuestAddTime1());
						ps.setString(21, ballMatchModel.getName());
						if (isJingcai) {
							ps.setString(22, ballMatchModel.getJingcaiId());
							ps.setString(23, ballMatchModel.getBsId());
						} else {
							ps.setString(22, ballMatchModel.getBsId());
						}
						ps.addBatch();
					}
					ps.executeBatch();
				}
			});

		}
		if (!insertBallMatchModels.isEmpty()) {
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					String sql = null;
					if (!isJingcai) {
						sql = "insert into md_bb_match_base "
								+ " (color,matchTime,matchState,homeTeam,guestTeam,homeScore"
								+ ",guestScore,letBallNum,explainContent,homeOne,guestOne,homeTwo,guestTwo"
								+ ",homeThree,guestThree,homeFour,guestFour,remainTime,bsId,leagueId,source,processStatus,homeAddTime1,guestAddTime1,name,createTime)values("
								+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
					} else {
						sql = "insert into md_bb_match_base "
								+ " (color,matchTime,matchState,homeTeam,guestTeam,homeScore"
								+ ",guestScore,letBallNum,explainContent,homeOne,guestOne,homeTwo,guestTwo"
								+ ",homeThree,guestThree,homeFour,guestFour,remainTime,bsId,leagueId,source,processStatus,createTime,homeAddTime1,guestAddTime1,name,jingcaiId)values("
								+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?)";
					}
					PreparedStatement ps = connection.prepareStatement(sql);
					for (BasketBallMatchModel ballMatchModel : insertBallMatchModels) {

						ps.setString(1, ballMatchModel.getColor());
						ps.setTimestamp(2,
								ballMatchModel.getMatchTime() == null ? null
										: new Timestamp(ballMatchModel
												.getMatchTime().getTime()));
						ps.setInt(3, ballMatchModel.getMatchState());
						ps.setString(4, ballMatchModel.getHomeTeam());
						ps.setString(5, ballMatchModel.getGuestTeam());
						ps.setInt(6, ballMatchModel.getHomeScore());
						ps.setInt(7, ballMatchModel.getGuestScore());
						ps.setInt(8, ballMatchModel.getLetBallNum());
						ps.setString(9, ballMatchModel.getExplainContent());
						ps.setInt(10, ballMatchModel.getHomeOne());
						ps.setInt(11, ballMatchModel.getGuestOne());
						ps.setInt(12, ballMatchModel.getHomeTwo());
						ps.setInt(13, ballMatchModel.getGuestTwo());
						ps.setInt(14, ballMatchModel.getHomeThree());
						ps.setInt(15, ballMatchModel.getGuestThree());
						ps.setInt(16, ballMatchModel.getHomeFour());
						ps.setInt(17, ballMatchModel.getGuestFour());
						ps.setString(18, ballMatchModel.getRemainTime());
						ps.setString(19, ballMatchModel.getBsId());
						ps.setString(20, ballMatchModel.getLeagueId());
						ps.setInt(21, ballMatchModel.getSource());
						ps.setInt(22, ballMatchModel.getProcessStatus());
						ps.setInt(23, ballMatchModel.getHomeAddTime1());
						ps.setInt(24, ballMatchModel.getGuestAddTime1());
						ps.setString(25, ballMatchModel.getName());
						if (isJingcai) {
							ps.setString(26, ballMatchModel.getJingcaiId());
						}
						ps.addBatch();
					}
					ps.executeBatch();
				}
			});
		}
		session.flush();
		session.clear();
	}

	/**
	 * 抓取即时比分时使用
	 * @param matchMap
	 * @param ballMatchModels
	 */
	private void makeInsertAndUpdate(
			Map<String, List<BasketBallMatchModel>> matchMap,
			List<BasketBallMatchModel> ballMatchModels) {
		
		//  比赛时间大于等于2天前的凌晨
		Query query = createQuery("select explainContent from BasketBallMatchPO where bsId=?");
		List<BasketBallMatchModel> update = new ArrayList<>();
		List<BasketBallMatchModel> insert = new ArrayList<>();
		query.setMaxResults(1);
		for (BasketBallMatchModel ballMatchModel : ballMatchModels) {
			query.setString(0, ballMatchModel.getBsId());
			String blob  = (String) query.uniqueResult();
			if (blob!=null) {
				if(StringUtils.isBlank(ballMatchModel.getExplainContent())){
					ballMatchModel.setExplainContent(blob);
				}else if(StringUtils.isNotBlank(blob)&&!StringUtils.contains(blob,ballMatchModel.getExplainContent())){
					ballMatchModel.setExplainContent(blob+"^^"+ballMatchModel.getExplainContent());
				}
				update.add(ballMatchModel);
			} else {
				insert.add(ballMatchModel);
			}
		}
		matchMap.put("1", update);
		matchMap.put("2", insert);
	}

	private String convertBlobToString(Blob blob) {
		String cont = "";
		if(blob!=null){
			byte[] content = null;
			try {
				content = blob.getBytes(1, (int) blob.length());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(content!=null && content.length>0){
				try {
					cont = new String(content,"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return cont;
	}

	@Override
	@Transactional
	public void storeLqHistoryMatch(
			final BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			final List<BasketBallMatchModel> ballMatchModels) {
		Map<String, List<BasketBallMatchModel>> matchMap = new HashMap<>();
		makeInsertAndUpdateHistoryMatch(matchMap, ballMatchModels, basketBallLeagueSeasonModel);
		final List<BasketBallMatchModel> insert = matchMap.get("1");
		final List<BasketBallMatchModel> update = matchMap.get("2");
		Session session = session();
		if (!insert.isEmpty()) {
			
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					PreparedStatement ps = connection
							.prepareStatement("insert into md_bb_match_base "
									+ " (matchTime,matchState,homeTeam,guestTeam,homeScore"
									+ ",guestScore,bsId,leagueId,source,processStatus,createTime,seasonId,season,kind,subLeagueId)values("
									+ "?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?)");
					for (BasketBallMatchModel basketBallMatchModel : insert) {
						ps.setTimestamp(1, new Timestamp(basketBallMatchModel
								.getMatchTime().getTime()));
						ps.setInt(2, basketBallMatchModel.getMatchState());
						ps.setString(3, basketBallMatchModel.getHomeTeam());
						ps.setString(4, basketBallMatchModel.getGuestTeam());
						ps.setInt(5, basketBallMatchModel.getHomeScore());
						ps.setInt(6, basketBallMatchModel.getGuestScore());
						ps.setString(7, basketBallMatchModel.getBsId());
						ps.setString(8, basketBallMatchModel.getLeagueId());
						ps.setInt(9, basketBallMatchModel.getSource());
						ps.setInt(10, basketBallMatchModel.getProcessStatus());
						ps.setInt(11, basketBallLeagueSeasonModel.getSeasonId());
						ps.setString(12, basketBallMatchModel.getSeason());
						ps.setString(13, basketBallLeagueSeasonModel.getKind());
						ps.setString(14,
								basketBallLeagueSeasonModel.getSubLeagueId());
						ps.addBatch();

					}
					ps.executeBatch();
				}
			});
			
//			updateIsCrawler(basketBallLeagueSeasonModel.getSeasonId(), 1);
		}
		
		if(!update.isEmpty()){
			session.doWork(new Work() {
				
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = connection
							.prepareStatement("update md_bb_match_base "
									+ " set matchTime=?,matchState=?,homeScore=?"
									+ ",guestScore=?,updateTime=now(),seasonId=?,season=?,kind=?,subLeagueId=? where bsId=? and leagueId=? and source=?");
					for (BasketBallMatchModel basketBallMatchModel : update) {
						ps.setTimestamp(1, new Timestamp(basketBallMatchModel
								.getMatchTime().getTime()));
						ps.setInt(2, basketBallMatchModel.getMatchState());
						ps.setInt(3, basketBallMatchModel.getHomeScore());
						ps.setInt(4, basketBallMatchModel.getGuestScore());
						ps.setInt(5, basketBallLeagueSeasonModel.getSeasonId());
						ps.setString(6, basketBallLeagueSeasonModel.getSeasonName());
						ps.setString(7, basketBallLeagueSeasonModel.getKind());
						ps.setString(8, basketBallLeagueSeasonModel.getSubLeagueId());
						ps.setString(9, basketBallMatchModel.getBsId());
						ps.setString(10,
								basketBallLeagueSeasonModel.getLeagueId());
						ps.setInt(11, basketBallLeagueSeasonModel.getSource());
						ps.addBatch();

					}
					ps.executeBatch();
				}
			});
		}
		session.flush();
		session.clear();
	}

	private void makeInsertAndUpdateHistoryMatch(
			Map<String, List<BasketBallMatchModel>> matchMap,
			List<BasketBallMatchModel> ballMatchModels, BasketBallLeagueSeasonModel basketBallLeagueSeasonModel) {
		List<BasketBallMatchModel> insert = new ArrayList<>();
		List<BasketBallMatchModel> update = new ArrayList<>();
		List<String> indexs = createQuery("select bsId from BasketBallMatchPO where leagueId=?  and source=? and season is null or locate(?,season)>0 ")
				.setString(0, basketBallLeagueSeasonModel.getLeagueId())
				.setInteger(1, basketBallLeagueSeasonModel.getSource()).setString(2, DateFormateUtil.getNowYear().substring(2)).list();
		String index = "";
		for(BasketBallMatchModel basketBallMatchModel:ballMatchModels){
			if(indexs.contains(basketBallMatchModel.getBsId())){
				update.add(basketBallMatchModel);
			}else {
				insert.add(basketBallMatchModel);
			}
		}
		matchMap.put("1", insert);
		matchMap.put("2", update);
	}

	@Override
	@Transactional
	public void updateIsCrawler(Integer seasonId, int isCrawler) {
		createQuery(
				"update BasketBallLeagueSeasonPO set isCrawler=? where seasonId=?")
				.setInteger(0, isCrawler).setInteger(1, seasonId)
				.executeUpdate();
	}

	@Override
	@Transactional
	public void storeCupMatch(
			final BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			Map<String, Object> basketBallMatchAndLeagueScore) {
		Session session = insertOrUpdateBbMatch(basketBallLeagueSeasonModel,
				basketBallMatchAndLeagueScore);
		
		@SuppressWarnings("unchecked")
		final List<BasketBallLeagueScoreModel> leagueScoreModels = (List<BasketBallLeagueScoreModel>) basketBallMatchAndLeagueScore
				.get(Constants.SCORE);
		if (!leagueScoreModels.isEmpty()) {
			Map<String, List<BasketBallLeagueScoreModel>> scoreMap = new HashMap<>();
			makeInsertAndUpdateLeagueScoreMap(scoreMap,
					basketBallLeagueSeasonModel, leagueScoreModels);
			final List<BasketBallLeagueScoreModel> insert = scoreMap.get("1");
			final List<BasketBallLeagueScoreModel> update = scoreMap.get("2");
			if (!insert.isEmpty()) {
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						PreparedStatement ps = connection
								.prepareStatement("insert into md_bb_league_score_base "
										+ " (groupName,teamName,totalMatches,winMatches,loseMatches"
										+ ",winPercent,averageGoal,averageLose,source,processStatus,createTime,winContinuous,leagueId,season,seasonId,subLeagueId)values("
										+ "?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?)");
						for (BasketBallLeagueScoreModel leagueScoreModel : insert) {
							ps.setString(1, leagueScoreModel.getGroupName());
							ps.setString(2, leagueScoreModel.getTeamName());
							ps.setInt(3, leagueScoreModel.getTotalMatches());
							ps.setInt(4, leagueScoreModel.getWinMatches());
							ps.setInt(5, leagueScoreModel.getLoseMatches());
							ps.setDouble(6, leagueScoreModel.getWinPercent());
							ps.setDouble(7, leagueScoreModel.getAverageGoal());
							ps.setDouble(8, leagueScoreModel.getAverageLose());
							ps.setInt(9, leagueScoreModel.getSource());
							ps.setInt(10, leagueScoreModel.getProcessStatus());
							ps.setInt(11, leagueScoreModel.getWinContinuous());
							ps.setString(12,
									basketBallLeagueSeasonModel.getLeagueId());
							ps.setString(13,
									basketBallLeagueSeasonModel.getSeasonName());
							ps.setInt(14,
									basketBallLeagueSeasonModel.getSeasonId());
							ps.setString(15, basketBallLeagueSeasonModel
									.getSubLeagueId());
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
						PreparedStatement ps = connection
								.prepareStatement("update md_bb_league_score_base set "
										+ " groupName=?,totalMatches=?,winMatches=?,loseMatches=?"
										+ ",winPercent=?,averageGoal=?,averageLose=?,updateTime=now(),winContinuous=? where seasonId=? and teamName=? and source=?");
						for (BasketBallLeagueScoreModel leagueScoreModel : update) {
							ps.setString(1, leagueScoreModel.getGroupName());
							ps.setInt(2, leagueScoreModel.getTotalMatches());
							ps.setInt(3, leagueScoreModel.getWinMatches());
							ps.setInt(4, leagueScoreModel.getLoseMatches());
							ps.setDouble(5, leagueScoreModel.getWinPercent());
							ps.setDouble(6, leagueScoreModel.getAverageGoal());
							ps.setDouble(7, leagueScoreModel.getAverageLose());
							ps.setInt(8, leagueScoreModel.getWinContinuous());
							ps.setInt(9,
									basketBallLeagueSeasonModel.getSeasonId());
							ps.setString(10, leagueScoreModel.getTeamName());
							ps.setInt(11, basketBallLeagueSeasonModel.getSource());
							ps.addBatch();
						}
						ps.executeBatch();
					}
				});
			}
		}
		session.flush();
		session.clear();

	}

	private void makeInsertAndUpdateLeagueScoreMap(
			Map<String, List<BasketBallLeagueScoreModel>> scoreMap,
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			List<BasketBallLeagueScoreModel> leagueScoreModels) {
		List<String> teamName = createQuery("select teamName from BasketBallLeagueScorePO where seasonId=? and source=?").
				setInteger(0, basketBallLeagueSeasonModel.getSeasonId()).setInteger(1, basketBallLeagueSeasonModel.getSource()).list();
		List<BasketBallLeagueScoreModel> insert = new ArrayList<>();
		List<BasketBallLeagueScoreModel> update = new ArrayList<>();
		for(BasketBallLeagueScoreModel scoreModel:leagueScoreModels){
			if(teamName.contains(scoreModel.getTeamName())){
				update.add(scoreModel);
			}else {
				insert.add(scoreModel);
			}
		}
		scoreMap.put("1", insert);
		scoreMap.put("2", update);
	}

	/**
	 * @param basketBallLeagueSeasonModel
	 * @param basketBallMatchAndLeagueScore
	 * @return
	 * @throws HibernateException
	 */
	private Session insertOrUpdateBbMatch(
			final BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			Map<String, Object> basketBallMatchAndLeagueScore)
			throws HibernateException {
		@SuppressWarnings("unchecked")
		final List<BasketBallMatchModel> basketBallMatchModels = (List<BasketBallMatchModel>) basketBallMatchAndLeagueScore
				.get(Constants.MATCH);
		Map<String, List<BasketBallMatchModel>> matchMap = new HashMap<>();
		makeInsertAndUpdateHistoryMatch(matchMap, basketBallMatchModels, basketBallLeagueSeasonModel);
		final List<BasketBallMatchModel> insert = matchMap.get("1");
		final List<BasketBallMatchModel> update = matchMap.get("2");
		
		
		Session session = session();
		if (!insert.isEmpty()) {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = connection
							.prepareStatement("insert into md_bb_match_base "
									+ " (matchTime,matchState,homeTeam,guestTeam,homeScore"
									+ ",guestScore,bsId,leagueId,source,processStatus,createTime,seasonId,season,subLeagueId,homeOne,guestOne,homeThree,guestThree,matchSubClass)values("
									+ "?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?)");
					for (BasketBallMatchModel basketBallMatchModel : insert) {
						ps.setTimestamp(1, new Timestamp(basketBallMatchModel
								.getMatchTime().getTime()));
						ps.setInt(2, basketBallMatchModel.getMatchState());
						ps.setString(3, basketBallMatchModel.getHomeTeam());
						ps.setString(4, basketBallMatchModel.getGuestTeam());
						ps.setInt(5, basketBallMatchModel.getHomeScore());
						ps.setInt(6, basketBallMatchModel.getGuestScore());
						ps.setString(7, basketBallMatchModel.getBsId());
						ps.setString(8,
								basketBallLeagueSeasonModel.getLeagueId());
						ps.setInt(9, basketBallMatchModel.getSource());
						ps.setInt(10, basketBallMatchModel.getProcessStatus());
						ps.setInt(11, basketBallLeagueSeasonModel.getSeasonId());
						ps.setString(12,
								basketBallLeagueSeasonModel.getSeasonName());
						ps.setString(13,
								basketBallLeagueSeasonModel.getSubLeagueId());
						ps.setInt(14, basketBallMatchModel.getHomeOne());
						ps.setInt(15, basketBallMatchModel.getGuestOne());
						ps.setInt(16, basketBallMatchModel.getHomeScore()
								- basketBallMatchModel.getHomeOne());
						ps.setInt(17, basketBallMatchModel.getGuestScore()
								- basketBallMatchModel.getGuestOne());
						ps.setString(18,
								basketBallMatchModel.getMatchSubClass()); // 组名
						ps.addBatch();

					}
					ps.executeBatch();
				}
			});
		}
		
		if(!update.isEmpty()){
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = connection
							.prepareStatement("update md_bb_match_base "
									+ " set matchState=?,homeScore=?"
									+ ",guestScore=?,updateTime=now(),seasonId=?,season=?,subLeagueId=?,matchSubClass=? where leagueId=? and bsId=? and source=?");
					for (BasketBallMatchModel basketBallMatchModel : update) {
						ps.setInt(1, basketBallMatchModel.getMatchState());
						ps.setInt(2, basketBallMatchModel.getHomeScore());
						ps.setInt(3, basketBallMatchModel.getGuestScore());
						ps.setInt(4, basketBallLeagueSeasonModel.getSeasonId());
						ps.setString(5,
								basketBallLeagueSeasonModel.getSeasonName());
						ps.setString(6,
								basketBallLeagueSeasonModel.getSubLeagueId());
						ps.setString(7,
								basketBallMatchModel.getMatchSubClass()); // 组名
						ps.setString(8, basketBallLeagueSeasonModel.getLeagueId());
						ps.setString(9, basketBallMatchModel.getBsId());
						ps.setInt(10, basketBallLeagueSeasonModel.getSource());
						ps.addBatch();

					}
					ps.executeBatch();
				}
			});
		}
		return session;
	}

	@Override
	@Transactional
	public void storeLeagueScore(
			final BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			final List<BasketBallLeagueScoreModel> basketBallMatchAndLeagueScore) {
		if(!basketBallMatchAndLeagueScore.isEmpty()){
			Map<String, List<BasketBallLeagueScoreModel>> scoreMap = new HashMap<>();
			makeInsertAndUpdateLeagueScoreMap(scoreMap, basketBallLeagueSeasonModel, basketBallMatchAndLeagueScore);
			final List<BasketBallLeagueScoreModel> insert = scoreMap.get("1");
			final List<BasketBallLeagueScoreModel> update = scoreMap.get("2");
			Session session = session();
			if (!insert.isEmpty()) {

				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						PreparedStatement ps = connection
								.prepareStatement("insert into md_bb_league_score_base "
										+ " (teamName,winMatches,loseMatches"
										+ ",winPercent,winNet,leagueRecord,pastTen,source,processStatus,createTime,winContinuous,leagueId,season,seasonId,rank,scoreType)values("
										+ "?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?)");
						for (BasketBallLeagueScoreModel leagueScoreModel : insert) {
							ps.setString(1, leagueScoreModel.getTeamName());
							ps.setInt(2, leagueScoreModel.getWinMatches());
							ps.setInt(3, leagueScoreModel.getLoseMatches());
							ps.setDouble(4, leagueScoreModel.getWinPercent());
							ps.setDouble(5, leagueScoreModel.getWinNet());
							ps.setString(6, leagueScoreModel.getLeagueRecord());
							ps.setString(7, leagueScoreModel.getPastTen());
							ps.setInt(8,
									basketBallLeagueSeasonModel.getSource());
							ps.setInt(9, basketBallLeagueSeasonModel
									.getProcessStatus());
							ps.setInt(10, leagueScoreModel.getWinContinuous());
							ps.setString(11,
									basketBallLeagueSeasonModel.getLeagueId());
							ps.setString(12,
									basketBallLeagueSeasonModel.getSeasonName());
							ps.setInt(13,
									basketBallLeagueSeasonModel.getSeasonId());
							ps.setInt(14, leagueScoreModel.getRank());
							ps.setInt(15, leagueScoreModel.getScoreType()==null?0:leagueScoreModel.getScoreType());
							ps.addBatch();
						}
						ps.executeBatch();
					}
				});
			}
			
			if (!update.isEmpty()) {
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						PreparedStatement ps = connection
								.prepareStatement("update md_bb_league_score_base set "
										+ " winMatches=?,loseMatches=?"
										+ ",winPercent=?,winNet=?,leagueRecord=?,updateTime=now(),pastTen=?,winContinuous=?,rank=?,scoreType=? where seasonId=? and teamName=? and source=?");
						for (BasketBallLeagueScoreModel leagueScoreModel : update) {
							ps.setInt(1, leagueScoreModel.getWinMatches());
							ps.setInt(2, leagueScoreModel.getLoseMatches());
							ps.setDouble(3, leagueScoreModel.getWinPercent());
							ps.setDouble(4, leagueScoreModel.getWinNet());
							ps.setString(5, leagueScoreModel.getLeagueRecord());
							ps.setString(6, leagueScoreModel.getPastTen());
							ps.setInt(7, leagueScoreModel.getWinContinuous());
							ps.setInt(8, leagueScoreModel.getRank());
							ps.setInt(9, leagueScoreModel.getScoreType()==null?0:leagueScoreModel.getScoreType());
							ps.setInt(10,
									basketBallLeagueSeasonModel.getSeasonId());
							ps.setString(11, leagueScoreModel.getTeamName());
							ps.setInt(12, basketBallLeagueSeasonModel.getSource());
							
							ps.addBatch();
						}
						ps.executeBatch();
					}
				});
			}
//			updateIsCrawler(basketBallLeagueSeasonModel.getSeasonId(), 1);
			session.flush();
			session.clear();
		}
		
	}

}
