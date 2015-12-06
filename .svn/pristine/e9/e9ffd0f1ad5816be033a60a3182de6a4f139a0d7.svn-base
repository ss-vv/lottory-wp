package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.BatchUpdateException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.JishiBifenDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.OddsBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchEventModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.xhcms.lottery.commons.persist.dao.PsDao;

/**
 * @author 彭保星
 *
 * @since 2014年12月1日下午5:40:05
 */
public class JishiBifenDataStoreDaoImpl implements JishiBifenDataStoreDao {
	private Logger logger = LoggerFactory
			.getLogger(JishiBifenDataStoreDaoImpl.class);

	private static Map<String, String> corpId_Name = new HashMap<>();
	private static List<UserAgentPO> userAgentPOs;

	public JishiBifenDataStoreDaoImpl() {
		if (userAgentPOs == null) {
			initUserAgent();
		}
	}

	/**
	 * @throws SQLException
	 */
	private void initUserAgent() {

		String sql = "select * from system_phone_version order by id desc";
		PreparedStatement ps;
		try {
			ps = getPreparedStatement(sql);

			ps.executeQuery();
			ResultSet rs = ps.getResultSet();

			if (rs != null) {
				userAgentPOs = new ArrayList<>();
				while (rs.next()) {
					UserAgentPO userAgentPO = new UserAgentPO();
					userAgentPO.setId(rs.getInt("id"));
					userAgentPO.setPhoneType(rs.getString("phoneType"));
					userAgentPO.setSystemVersion(rs.getString("systemVersion"));
					userAgentPOs.add(userAgentPO);
				}
			}
			releaseConnect(ps);
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initCorp() throws SQLException {
		if (corpId_Name.isEmpty()) {
			PreparedStatement ps = getPreparedStatement("select corpId,corpName from md_lottery_corp_base");
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if (rs != null) {
				while (rs.next()) {
					corpId_Name.put(rs.getString("corpName"),
							rs.getString("corpId"));
				}
				rs.close();
			}
			releaseConnect(ps);
			ps.close();
		}
	}

	@Override
	public synchronized UserAgentPO getRandomHeader() throws SQLException {
		if (userAgentPOs == null) {
			initUserAgent();
		}
		return randomChoosOneFromList(userAgentPOs);
	}

	private PreparedStatement getPreparedStatement(String sql)
			throws SQLException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);

		return stmt;
	}

	private UserAgentPO randomChoosOneFromList(List<UserAgentPO> userAgentPOs) {
		// TODO Auto-generated method stub
		UserAgentPO userAgentPO;
		int size = userAgentPOs.size();
		Random random = new Random();
		int index = random.nextInt(size);
		userAgentPO = userAgentPOs.get(index);
		return userAgentPO;
	}

	@Override
	public String getNowSeasonByLeagueId(String leagueId) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = getPreparedStatement("select max(seasonName) as seasonName from md_fb_league_season_base where leagueId=? and source=1 order by seasonName desc");
		ps.setString(1, leagueId);
		ResultSet rs = ps.executeQuery();
		String seasonName = null;
		if (rs != null) {
			rs.next();
			seasonName = rs.getString("seasonName");
		}
		releaseConnect(ps);
		rs.close();

		ps.close();

		return seasonName;
	}

	private void releaseConnect(PreparedStatement ps) throws SQLException {
		// TODO Auto-generated method stub
		ConnectionPool.getInstance().release(ps.getConnection());
	}

	@Override
	public void updateMatchData(List<QtMatchBaseModel> qtMatchBaseModels)
			throws SQLException {

		Map<String, List<QtMatchBaseModel>> matchMap = new HashMap<String, List<QtMatchBaseModel>>();
		makeInsertMatchInfo(qtMatchBaseModels, matchMap);
		final List<QtMatchBaseModel> insert = matchMap.get("1");
		List<QtMatchBaseModel> update = matchMap.get("2");
		if (update != null && !update.isEmpty()) {
			updateJingcaiMatchBatch(update);
		}
		if (insert != null && !insert.isEmpty()) {
			insertJingCaiMatch(insert);
		}
	}

	private void insertJingCaiMatch(List<QtMatchBaseModel> insert)
			throws SQLException {
		PreparedStatement ps = getPreparedStatement("insert into md_qt_match_base("
				+ " bsId,matchTime,homeTeamId,guestTeamId,leagueId,season,"
				+ " matchStatus,homeTeamScore,guestTeamScore,homeTeamHalfScore,"
				+ "guestTeamHalfScore,homeTeamPosition,guestTeamPosition,"
				+ "source,processStatus,matchMessage,homeTeamName,guestTeamName,handiCap,televison,jingcaiId,createTime,isNow)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)");
		for (QtMatchBaseModel qtMatchBaseModel : insert) {
			ps.setString(1, qtMatchBaseModel.getBsId());
			ps.setTimestamp(2, new Timestamp(qtMatchBaseModel.getMatchTime()
					.getTime()));
			ps.setString(3, qtMatchBaseModel.getHomeTeamId());
			ps.setString(4, qtMatchBaseModel.getGuestTeamId());

			ps.setString(5, qtMatchBaseModel.getLeagueId());
			ps.setString(6, qtMatchBaseModel.getSeason());
			ps.setInt(7, qtMatchBaseModel.getMatchStatus());
			ps.setInt(8, qtMatchBaseModel.getHomeTeamScore());
			ps.setInt(9, qtMatchBaseModel.getGuestTeamScore());
			ps.setInt(10, qtMatchBaseModel.getHomeTeamHalfScore() == null ? 0
					: qtMatchBaseModel.getHomeTeamHalfScore());
			ps.setInt(11, qtMatchBaseModel.getGuestTeamHalfScore() == null ? 0
					: qtMatchBaseModel.getGuestTeamHalfScore());
			ps.setInt(12, qtMatchBaseModel.getHomeTeamPosition() == null ? -1
					: qtMatchBaseModel.getHomeTeamPosition());
			ps.setInt(13, qtMatchBaseModel.getGuestTeamPosition() == null ? -1
					: qtMatchBaseModel.getGuestTeamPosition());
			ps.setInt(14, qtMatchBaseModel.getSource());
			ps.setInt(15, qtMatchBaseModel.getProcessStatus());
			ps.setString(16, qtMatchBaseModel.getMatchMessage());
			ps.setString(17, qtMatchBaseModel.getHomeTeamId());
			ps.setString(18, qtMatchBaseModel.getGuestTeamId());
			ps.setDouble(19, qtMatchBaseModel.getHandiCap());
			ps.setString(20, qtMatchBaseModel.getTelevison());
			ps.setString(21, qtMatchBaseModel.getJingcaiId());
			ps.setString(22, "1");//是否当前 1当前

			ps.addBatch();
		}
		try {
			ps.executeBatch();
		} catch (Exception exception) {
			// 联赛不存在
			if (exception instanceof BatchUpdateException) {
				BatchUpdateException batchUpdateException = (BatchUpdateException) exception;
				int[] updatedCount = batchUpdateException.getUpdateCounts();
				logger.info("更新失败的数据:{}", insert.get(updatedCount.length - 1));
				//已存在更新
				List<QtMatchBaseModel> matchBaseModels = new ArrayList<>();
				matchBaseModels.add(insert.get(updatedCount.length-1));
				updateJingcaiMatchBatch(matchBaseModels);
				if (updatedCount.length <= insert.size()) {
					List<QtMatchBaseModel> qtMatchBaseModels = insert.subList(
							updatedCount.length, insert.size());
					insertJingCaiMatch(qtMatchBaseModels);
				}
			}
		}

		releaseConnect(ps);
		ps.close();
		logger.info("本次插入的比赛数目为{}", insert.size());

	}

	private void updateJingcaiMatchBatch(List<QtMatchBaseModel> update)
			throws SQLException {
		String sql = "update md_qt_match_base set"
				+ " matchStatus=?,matchTime=?,halfStartTime=?,homeTeamScore=?,"
				+ " guestTeamScore=?,homeTeamHalfScore=?,homeTeamRed=?,guestTeamRed=?,"
				+ " homeTeamYellow=?,guestTeamYellow=?,handiCap=?,"
				+ " guestTeamHalfScore=?,homeTeamPosition=?,guestTeamPosition=?,televison=?,jingcaiId=ifnull(jingcaiId,?),updateTime=now(),isNow=?,halfEndTime=ifnull(halfEndTime,?),liveContent=? where bsId=? and source=?";
		PreparedStatement ps = getPreparedStatement(sql);
		for (QtMatchBaseModel qtMatchBaseModel : update) {
			ps.setInt(1, qtMatchBaseModel.getMatchStatus());
			ps.setTimestamp(2, new Timestamp(qtMatchBaseModel.getMatchTime()
					.getTime()));
			ps.setTimestamp(3,
					qtMatchBaseModel.getHalfStartTime() == null ? null
							: new Timestamp(qtMatchBaseModel.getHalfStartTime()
									.getTime()));
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
			ps.setString(20, qtMatchBaseModel.getBsId());
			ps.setInt(21, 1);// 来源为球探
			ps.setString(17, "1");
			ps.setTimestamp(18, qtMatchBaseModel.getHalfEndTime()==null?null:new Timestamp(qtMatchBaseModel.getHalfEndTime().getTime()));
			ps.setString(19, qtMatchBaseModel.getLiveContent());
			ps.addBatch();
		}
		ps.executeBatch();
		releaseConnect(ps);
		ps.close();

		logger.info("本次更新的比赛数目为{}", update.size());
	}

	private void makeInsertMatchInfo(List<QtMatchBaseModel> qtMatchBaseModels,
			Map<String, List<QtMatchBaseModel>> matchMap) throws SQLException {
		List<QtMatchBaseModel> qtMatchBaseModels2 = new ArrayList<>();
		List<QtMatchBaseModel> update = new ArrayList<>();
		qtMatchBaseModels2 = new ArrayList<>();
		for (QtMatchBaseModel qtMatchBaseModel : qtMatchBaseModels) {
			if (countExistsBsIds(qtMatchBaseModel.getBsId())>0) {
				if(qtMatchBaseModel.getMatchStatus()==null||(!StringUtils.equals(qtMatchBaseModel.getMatchStatus().toString(), "-1"))){
					update.add(qtMatchBaseModel);
				}
			} else {
				qtMatchBaseModels2.add(qtMatchBaseModel);
			}
		}
		matchMap.put("1", qtMatchBaseModels2);
		matchMap.put("2", update);
	}

	/**
	 * @throws SQLException
	 * 
	 */
	private int countExistsBsIds(String bsId) throws SQLException {
		PreparedStatement ps = getPreparedStatement("select count(bsId) from md_qt_match_base where bsId=?");
		ps.setString(1, bsId);
		ps.executeQuery();
		ResultSet st = ps.getResultSet();
		int count = 0;
		if (st != null) {
			st.next();
			count = st.getInt(1);
			st.close();
		}
		releaseConnect(ps);
		ps.close();

		return count;
	}

	@Override
	public void storeLqJishi(List<BasketBallMatchModel> ballMatchModels,
			final boolean isJingcai) throws SQLException, UnsupportedEncodingException {
		Map<String, List<BasketBallMatchModel>> matchMap = new HashMap<String, List<BasketBallMatchModel>>();
		makeInsertAndUpdate(matchMap, ballMatchModels);
		final List<BasketBallMatchModel> updateBallMatchModels = matchMap
				.get("1");
		final List<BasketBallMatchModel> insertBallMatchModels = matchMap
				.get("2");
		if (!updateBallMatchModels.isEmpty()) {
			// TODO Auto-generated method stub
			String sql = "update md_bb_match_base set "
					+ " color=?,matchTime=?,matchState=?,homeTeam=?,guestTeam=?,homeScore=?"
					+ ",guestScore=?,letBallNum=?,explainContent=?,homeOne=?,guestOne=?,homeTwo=?,guestTwo=?"
					+ ",homeThree=?,guestThree=?,homeFour=?,guestFour=?,remainTime=?,homeAddTime1=?,guestAddTime1=?,name=?,updateTime=now(),isNow='1'";
			if (isJingcai) {
				sql += ",jingcaiId=? ";
			}
			sql += " where bsId=?";
			PreparedStatement ps = getPreparedStatement(sql);
			for (BasketBallMatchModel ballMatchModel : updateBallMatchModels) {

				ps.setString(1, ballMatchModel.getColor());
				ps.setTimestamp(2,
						ballMatchModel.getMatchTime() == null ? null
								: new Timestamp(ballMatchModel.getMatchTime()
										.getTime()));
				ps.setInt(3, ballMatchModel.getMatchState());
				ps.setString(4, ballMatchModel.getHomeTeam());
				ps.setString(5, ballMatchModel.getGuestTeam());
				ps.setInt(6, ballMatchModel.getHomeScore());
				ps.setInt(7, ballMatchModel.getGuestScore());
				ps.setInt(8, ballMatchModel.getLetBallNum());
				ByteArrayInputStream byteArray = new ByteArrayInputStream(ballMatchModel.getExplainContent().getBytes("utf-8"));
				ps.setBinaryStream(9, byteArray);
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
			releaseConnect(ps);
			ps.close();

		}
		if (!insertBallMatchModels.isEmpty()) {
			String sql = null;
			if (!isJingcai) {
				sql = "insert into md_bb_match_base "
						+ " (color,matchTime,matchState,homeTeam,guestTeam,homeScore"
						+ ",guestScore,letBallNum,explainContent,homeOne,guestOne,homeTwo,guestTwo"
						+ ",homeThree,guestThree,homeFour,guestFour,remainTime,bsId,leagueId,source,processStatus,homeAddTime1=?,guestAddTime1,name,createTime)values("
						+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
			} else {
				sql = "insert into md_bb_match_base "
						+ " (color,matchTime,matchState,homeTeam,guestTeam,homeScore"
						+ ",guestScore,letBallNum,explainContent,homeOne,guestOne,homeTwo,guestTwo"
						+ ",homeThree,guestThree,homeFour,guestFour,remainTime,bsId,leagueId,source,processStatus,createTime,homeAddTime1,guestAddTime1,name,isNow,jingcaiId)values("
						+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?)";
			}
			PreparedStatement ps = getPreparedStatement(sql);
			for (BasketBallMatchModel ballMatchModel : insertBallMatchModels) {

				ps.setString(1, ballMatchModel.getColor());
				ps.setTimestamp(2,
						ballMatchModel.getMatchTime() == null ? null
								: new Timestamp(ballMatchModel.getMatchTime()
										.getTime()));
				ps.setInt(3, ballMatchModel.getMatchState());
				ps.setString(4, ballMatchModel.getHomeTeam());
				ps.setString(5, ballMatchModel.getGuestTeam());
				ps.setInt(6, ballMatchModel.getHomeScore());
				ps.setInt(7, ballMatchModel.getGuestScore());
				ps.setInt(8, ballMatchModel.getLetBallNum());
				ByteArrayInputStream byteArray = new ByteArrayInputStream(ballMatchModel.getExplainContent().getBytes("utf-8"));
				ps.setBinaryStream(9, byteArray);
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
				ps.setString(26, "1");
				if (isJingcai) {
					ps.setString(27, ballMatchModel.getJingcaiId());
				}
				ps.addBatch();
			}
			ps.executeBatch();
			releaseConnect(ps);
			ps.close();
		}
	}

	private void makeInsertAndUpdate(
			Map<String, List<BasketBallMatchModel>> matchMap,
			List<BasketBallMatchModel> ballMatchModels) throws SQLException {
		// 比赛时间大于等于2天前的凌晨
		PreparedStatement ps = getPreparedStatement("select bsId,explainContent from md_bb_match_base where bsId=?");
		
		List<BasketBallMatchModel> update = new ArrayList<>();
		List<BasketBallMatchModel> insert = new ArrayList<>();
		for (BasketBallMatchModel ballMatchModel : ballMatchModels) {
			ps.setString(1,ballMatchModel.getBsId());
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if(rs.next()){
				if(ballMatchModel.getMatchState()==null||(!StringUtils.equals(ballMatchModel.getMatchState().toString(), "-1"))){
					Blob blob = rs.getBlob("explainContent");
					String cont = convertBlobToString(blob);
					if(StringUtils.isBlank(ballMatchModel.getExplainContent())){
						ballMatchModel.setExplainContent(cont);
					}else if(StringUtils.isNotBlank(cont)&&!StringUtils.contains(cont,ballMatchModel.getExplainContent())){
						ballMatchModel.setExplainContent(cont+"^^"+ballMatchModel.getExplainContent());
					}
					update.add(ballMatchModel);
				}
			} else {
				insert.add(ballMatchModel);
			}
		}
		matchMap.put("1", update);
		matchMap.put("2", insert);
	}

	private String convertBlobToString(Blob blob) throws SQLException {
		String cont = "";
		if(blob!=null){
			byte[] content = blob.getBytes(1, (int) blob.length());
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
	public List<String> queryOddsCompany(Qt_fb_match_oddsType oddsType) {

		return null;
	}

	@Override
	public List<FbMatchBaseInfoPO> queryAllJingcaiMatch() throws SQLException {
		List<FbMatchBaseInfoPO> fbMatchBaseInfoPOs = new ArrayList<>();
		PreparedStatement ps = getPreparedStatement("select bsId,id from md_qt_match_base where (matchStatus=0 or matchStatus=1 or matchStatus=2 or matchStatus=3 or matchStatus=4) and source=1 and  matchTime<=? and matchTime>=? and jingcaiId is not null");
		ps.setDate(1, new Date(DateFormateUtil.getDateOfBefore(2).getTime()));
		ps.setDate(2, new Date((new java.util.Date()).getTime()));
		ps.executeQuery();
		ResultSet st = ps.getResultSet();
		if (st != null) {
			while (st.next()) {
				FbMatchBaseInfoPO matchBaseInfoPO = new FbMatchBaseInfoPO();
				matchBaseInfoPO.setBsId(st.getString("bsId"));
				matchBaseInfoPO.setId(st.getInt("id"));
				fbMatchBaseInfoPOs.add(matchBaseInfoPO);
			}
			st.close();
		}
		releaseConnect(ps);
		ps.close();
		return fbMatchBaseInfoPOs;
	}

	@Override
	public void storeJishiOdds(List<QtMatchOpOddsModel> qtMatchOpOddsModels,
			Qt_fb_match_oddsType oddsType, FbMatchBaseInfoPO matchBaseInfoPO,
			OddsBaseModel oddsBaseModel) throws SQLException {
		if (qtMatchOpOddsModels != null && !qtMatchOpOddsModels.isEmpty()) {
			switch (oddsType) {
			case euro:
				List<QtMatchOpOddsModel> odssList = filterInsertEvent(
						qtMatchOpOddsModels, matchBaseInfoPO, oddsBaseModel);
				doInsertEuroOdds(odssList, matchBaseInfoPO, oddsBaseModel);
				break;
			case asia:
			case ou:
				// 初始化公司id map
				initCorp();
				List<QtMatchOpOddsModel> odssList1 = makeInsertAsianOuOdds(
						qtMatchOpOddsModels, oddsType, matchBaseInfoPO,
						oddsBaseModel);
				doInsertAsiaOuOdds(odssList1, oddsType, matchBaseInfoPO,
						oddsBaseModel);
				break;

			default:
				break;
			}

		}
	}

	private void doInsertEuroOdds(List<QtMatchOpOddsModel> odssList1,
			FbMatchBaseInfoPO matchBaseInfoPO, OddsBaseModel oddsBaseModel)
			throws SQLException {
		if (!odssList1.isEmpty()) {
			PreparedStatement psEuro = getPreparedStatement("insert into"
					+ " md_qt_odds_euro_base(bsId,source,processStatus,corpId,HomeWinOdds,DrawOdds,GuestWinOdds,timestamp,createTime)values"
					+ "(?,?,?,?,?,?,?,?,now())");
			for (QtMatchOpOddsModel itemMatchOpOddsModel : odssList1) {
				psEuro.setLong(1, matchBaseInfoPO.getId());
				psEuro.setInt(2, itemMatchOpOddsModel.getSource());
				psEuro.setInt(3, itemMatchOpOddsModel.getProcessStatus());
				psEuro.setString(4, oddsBaseModel.getCorpId());

				psEuro.setDouble(5, itemMatchOpOddsModel.getHomeWinOdds());
				psEuro.setDouble(6, itemMatchOpOddsModel.getDrawOdds());
				psEuro.setDouble(7, itemMatchOpOddsModel.getGuestWinOdds());
				psEuro.setTimestamp(8, new Timestamp(itemMatchOpOddsModel
						.getTimestamp().getTime()));

				psEuro.addBatch();
			}
			psEuro.executeBatch();
			releaseConnect(psEuro);
			psEuro.close();
		}
	}

	private void doInsertAsiaOuOdds(List<QtMatchOpOddsModel> odssList,
			Qt_fb_match_oddsType oddsType, FbMatchBaseInfoPO matchBaseInfoPO,
			OddsBaseModel oddsBaseModel) throws SQLException {
		if (!odssList.isEmpty()) {
			String corpId = corpId_Name.get(oddsBaseModel.getCorpName());
			PreparedStatement psAsiaOu = getPreparedStatement("insert into"
					+ " md_qt_odds_asiaOu_base(bsId,source,processStatus,corpId,HomeWinOdds,handicap,GuestWinOdds,timestamp,oddsType,createTime)values"
					+ "(?,?,?,?,?,?,?,?,?,now())");
			int j = 0;
			for (QtMatchOpOddsModel itemMatchOpOddsModel : odssList) {
				psAsiaOu.setLong(1, matchBaseInfoPO.getId());
				psAsiaOu.setInt(2, itemMatchOpOddsModel.getSource());
				psAsiaOu.setInt(3, itemMatchOpOddsModel.getProcessStatus());
				psAsiaOu.setString(4, corpId);

				psAsiaOu.setDouble(5, itemMatchOpOddsModel.getHomeWinOdds());
				psAsiaOu.setDouble(6, itemMatchOpOddsModel.getHandicap());
				psAsiaOu.setDouble(7, itemMatchOpOddsModel.getGuestWinOdds());
				psAsiaOu.setTimestamp(8, new Timestamp(itemMatchOpOddsModel
						.getTimestamp().getTime()));
				psAsiaOu.setInt(9, oddsType == Qt_fb_match_oddsType.asia ? 1
						: oddsType == Qt_fb_match_oddsType.ou ? 2 : 0);
				psAsiaOu.addBatch();
			}
			psAsiaOu.executeBatch();
			releaseConnect(psAsiaOu);
			psAsiaOu.close();
		}
	}

	private List<QtMatchOpOddsModel> filterInsertEvent(
			List<QtMatchOpOddsModel> opOddsData,
			FbMatchBaseInfoPO matchBaseInfoPO, OddsBaseModel oddsBaseModel)
			throws SQLException {
		PreparedStatement ps = getPreparedStatement("select timestamp from md_qt_odds_euro_base where bsId=? and corpId=?");
		ps.setLong(1, matchBaseInfoPO.getId());
		ps.setString(2, oddsBaseModel.getCorpId());
		ResultSet rs = ps.executeQuery();
		List<String> timeStamp = new ArrayList<>();
		if (rs != null) {
			while (rs.next()) {
				Timestamp date = rs.getTimestamp(1);
				timeStamp.add(DateFormateUtil.getStringOfDate("yyyyMMddHHmmss",
						date));
			}
			rs.close();
		}
		releaseConnect(ps);
		ps.close();
		List<QtMatchOpOddsModel> qtMatchOpOddsModels = new ArrayList<>();
		if (timeStamp.size() != 0) {
			for (QtMatchOpOddsModel oddsModel : opOddsData) {
				if (!timeStamp.contains(DateFormateUtil.getStringOfDate(
						"yyyyMMddHHmmss", oddsModel.getTimestamp()))) {
					qtMatchOpOddsModels.add(oddsModel);
				}
			}
		} else {
			qtMatchOpOddsModels.addAll(opOddsData);
		}
		return qtMatchOpOddsModels;
	}

	private List<QtMatchOpOddsModel> makeInsertAsianOuOdds(
			List<QtMatchOpOddsModel> opOddsData, Qt_fb_match_oddsType oddsType,
			FbMatchBaseInfoPO matchBaseInfoPO, OddsBaseModel oddsBaseModel)
			throws SQLException {
		PreparedStatement ps = getPreparedStatement("select timestamp from md_qt_odds_asiaOu_base where bsId=? and oddsType=? and corpId=?");
		ps.setLong(1, matchBaseInfoPO.getId());
		ps.setInt(2, oddsType == Qt_fb_match_oddsType.ou ? 2 : 1);
		// 抓取亚赔和大小时corpId会null
		ps.setString(3, corpId_Name.get(oddsBaseModel.getCorpName()));

		List<String> timeStamp = new ArrayList<>();
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			while (rs.next()) {
				Timestamp date = rs.getTimestamp(1);
				timeStamp.add(DateFormateUtil.getStringOfDate("yyyyMMddHHmmss",
						date));
			}
			rs.close();
		}
		releaseConnect(ps);
		ps.close();
		List<QtMatchOpOddsModel> qtMatchOpOddsModels = new ArrayList<>();
		for (QtMatchOpOddsModel oddsModel : opOddsData) {
			if (!timeStamp.contains(DateFormateUtil.getStringOfDate(
					"yyyyMMddHHmmss", oddsModel.getTimestamp()))) {
				qtMatchOpOddsModels.add(oddsModel);
			}
		}
		return qtMatchOpOddsModels;
	}

	@Override
	public List<BasketBallMatchModel> queryAllJingcaiLqMatch()
			throws SQLException {
		List<BasketBallMatchModel> ballMatchModels = new ArrayList<>();
		PreparedStatement ps = getPreparedStatement("select bsId,id,jingcaiId,matchTime from md_bb_match_base where (matchState=0) and  matchTime<=? and matchTime>=? and  jingcaiId is not null");
		ps.setDate(1, new Date(DateFormateUtil.getDateOfBefore(2).getTime()));
		ps.setDate(2, new Date((new java.util.Date()).getTime()));
		ResultSet st = ps.executeQuery();
		if (st != null) {
			while (st.next()) {
				BasketBallMatchModel matchBaseInfoPO = new BasketBallMatchModel();
				matchBaseInfoPO.setBsId(st.getString("bsId"));
				matchBaseInfoPO.setId(st.getInt("id"));
				matchBaseInfoPO.setJingcaiId(st.getString("jingcaiId"));
				matchBaseInfoPO.setMatchTime(st.getTimestamp("matchTime"));
				ballMatchModels.add(matchBaseInfoPO);
			}
			st.close();
		}
		releaseConnect(ps);
		ps.close();
		return ballMatchModels;
	}

	@Override
	public List<FbMatchBaseInfoPO> queryAllZqMatchInLive() throws SQLException {
		List<FbMatchBaseInfoPO> ballMatchModels = new ArrayList<>();
		PreparedStatement ps = getPreparedStatement("select bsId,id from md_qt_match_base where (matchStatus=1  or matchStatus=2 or matchStatus=3 or matchStatus=4) and  jingcaiId is not null");
		ps.executeQuery();
		ResultSet st = ps.getResultSet();
		if (st != null) {
			while (st.next()) {
				FbMatchBaseInfoPO matchBaseInfoPO = new FbMatchBaseInfoPO();
				matchBaseInfoPO.setBsId(st.getString("bsId"));
				matchBaseInfoPO.setId(st.getInt("id"));
				ballMatchModels.add(matchBaseInfoPO);
			}
			st.close();
		}
		releaseConnect(ps);
		ps.close();
		return ballMatchModels;
	}

	@Override
	public void storeMatchEventData(List matchEvents) throws SQLException {
		if (matchEvents != null && !matchEvents.isEmpty()) {
			final List<QtMatchEventModel> insert = new ArrayList<>();
			filterInsertEvent(insert, matchEvents);
			if (!insert.isEmpty()) {

				PreparedStatement ps = getPreparedStatement("insert into"
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
				releaseConnect(ps);
				ps.close();
			}
		}

	}

	private void filterInsertEvent(List<QtMatchEventModel> insert,
			List<QtMatchEventModel> matchEvents) throws SQLException {
		@SuppressWarnings("unchecked")
		List<String> shiJianShijianList = new ArrayList<>();
		PreparedStatement ps = getPreparedStatement("select minute from md_qt_match_event_base where bsId=? and source=1");
		ps.setInt(1, matchEvents.get(0).getBsId());
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			while (rs.next()) {
				shiJianShijianList.add(rs.getString("minute"));
			}
			rs.close();
		}
		releaseConnect(ps);
		ps.close();
		for (QtMatchEventModel qtMatchEventModel : matchEvents) {
			if (shiJianShijianList.contains(qtMatchEventModel.getMinute())) {
			} else {
				insert.add(qtMatchEventModel);
			}
		}
	}

	@Override
	public void storeMatchStatisticData(
			List<QtMatchStatisticModel> matchStatisticModels)
			throws SQLException {
		if (matchStatisticModels != null && !matchStatisticModels.isEmpty()) {
			PreparedStatement ps = getPreparedStatement("delete from md_qt_match_statistic_base where bsId="
					+ matchStatisticModels.get(0).getBsId());
			ps.executeUpdate();
			releaseConnect(ps);
			ps.close();
			ps = getPreparedStatement("insert into"
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
			releaseConnect(ps);
			ps.close();
		}
	}

	@Override
	public List<BasketBallMatchModel> queryAllBasketMatchInLive()
			throws SQLException {
		List<BasketBallMatchModel> ballMatchModels = new ArrayList<>();
		PreparedStatement ps = getPreparedStatement("select bsId,id from md_bb_match_base where ( matchState=2  or matchState=1 or matchState=3 or matchState=4 or matchState=5 ) and  jingcaiId is not null");
		ps.executeQuery();
		ResultSet st = ps.getResultSet();
		if (st != null) {
			while (st.next()) {
				BasketBallMatchModel matchBaseInfoPO = new BasketBallMatchModel();
				matchBaseInfoPO.setBsId(st.getString("bsId"));
				matchBaseInfoPO.setId(st.getInt("id"));
				ballMatchModels.add(matchBaseInfoPO);
			}
			st.close();
		}
		releaseConnect(ps);
		ps.close();
		return ballMatchModels;
	}

	@Override
	public void storeBasketMatchPlayerStatisticData(
			List<QtBasketMatchPlayerStatisticModel> playerStatisticModels)
			throws SQLException {
		if (playerStatisticModels != null && !playerStatisticModels.isEmpty()) {
			Map<String, List<QtBasketMatchPlayerStatisticModel>> updateMap = new HashMap<>();

			makeInsertAndUpdatePlayerStatistics(updateMap,
					playerStatisticModels);
			List<QtBasketMatchPlayerStatisticModel> insert = updateMap.get("1");
			List<QtBasketMatchPlayerStatisticModel> update = updateMap.get("2");
			PreparedStatement ps = null;
			if (!insert.isEmpty()) {
				ps = getPreparedStatement("insert into"
						+ " md_qt_bb_match_playerstatistic_base(bsId,source,processStatus,`playerId`, `playerName`, "
						+ "`playerNickName`, `playerEnglishName`, `starter`, `lineUp`, `shootHitNumber`, `shootTotalNumber`, "
						+ "`threeHitNumber`, `threeTotalNumber`, `penaltyHitNumber`, `penaltyTotalNumber`, `backboardAttackNumber`,"
						+ " `backboardDefenceNumber`, `backboardTotalNumber`, `assist`, `foul`, `steal`, `fault`, `blockedShot`,"
						+ " `score`, `homeGuestType`,createTime)values"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())");
				int i = 0;
				for (QtBasketMatchPlayerStatisticModel matchEventPO : insert) {
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
				releaseConnect(ps);
			}
			if (!update.isEmpty()) {
				if (ps != null) {
					ps.clearBatch();
				}
				ps = getPreparedStatement("update md_qt_bb_match_playerstatistic_base "
						+ "set starter=?,lineUp=?,shootHitNumber=?,shootTotalNumber=?,threeHitNumber=?,threeTotalNumber=?,penaltyHitNumber=?,penaltyTotalNumber=?,"
						+ "backboardAttackNumber=?,backboardDefenceNumber=?,backboardTotalNumber=?,assist=?,foul=?,steal=?,fault=?,blockedShot=?,score=?,updateTime=now()"
						+ " where playerId=? and bsId=? and source=?");
				for (QtBasketMatchPlayerStatisticModel matchEventPO : update) {

					ps.setString(1, matchEventPO.getStarter());
					ps.setInt(2, matchEventPO.getLineUp());
					ps.setInt(3, matchEventPO.getShootHitNumber());
					ps.setInt(4, matchEventPO.getShootTotalNumber());
					ps.setInt(5, matchEventPO.getThreeHitNumber());
					ps.setInt(6, matchEventPO.getThreeTotalNumber());
					ps.setInt(7, matchEventPO.getPenaltyHitNumber());
					ps.setInt(8, matchEventPO.getPenaltyTotalNunber());
					ps.setInt(9, matchEventPO.getBackboardAttackNumber());
					ps.setInt(10, matchEventPO.getBackboardDefenceNumber());
					ps.setInt(11, matchEventPO.getBackboardTotalNumber());
					ps.setInt(12, matchEventPO.getAssist());
					ps.setInt(13, matchEventPO.getFoul());
					ps.setInt(14, matchEventPO.getSteal());
					ps.setInt(15, matchEventPO.getFault());
					ps.setInt(16, matchEventPO.getBlockedShot());
					ps.setInt(17, matchEventPO.getScore());
					ps.setString(18, matchEventPO.getPlayerId());
					ps.setInt(19, matchEventPO.getBsId());
					ps.setInt(20, matchEventPO.getSource());
					ps.addBatch();
				}
				ps.executeBatch();
				releaseConnect(ps);
				ps.close();
			}
		}
	}

	private void makeInsertAndUpdatePlayerStatistics(
			Map<String, List<QtBasketMatchPlayerStatisticModel>> updateMap,
			List<QtBasketMatchPlayerStatisticModel> playerStatisticModels)
			throws SQLException {
		List<QtBasketMatchPlayerStatisticModel> update = new ArrayList<>();
		List<QtBasketMatchPlayerStatisticModel> insert = new ArrayList<>();
		PreparedStatement ps = getPreparedStatement("select playerId from md_qt_bb_match_playerstatistic_base where bsId=? and source=1");
		ps.setInt(1, playerStatisticModels.get(0).getBsId());
		ResultSet rs = ps.executeQuery();
		List<String> playerIds = new ArrayList<>();
		if (rs != null) {
			while (rs.next()) {
				playerIds.add(rs.getString("playerId"));
			}
			rs.close();
		}
		releaseConnect(ps);
		ps.close();
		if (playerIds.isEmpty()) {
			insert.addAll(playerStatisticModels);
		} else {
			for (QtBasketMatchPlayerStatisticModel playerStatisticModel : playerStatisticModels) {
				if (playerIds.contains(playerStatisticModel.getPlayerId())) {
					update.add(playerStatisticModel);
				} else {
					insert.add(playerStatisticModel);
				}
			}
		}
		updateMap.put("1", insert);
		updateMap.put("2", update);
	}

	@Override
	public void storeMatchTeamStatisticData(
			List<QtBasketMatchTeamStatisticModel> qtMatchEventStatistics)
			throws SQLException {
		if (qtMatchEventStatistics != null && !qtMatchEventStatistics.isEmpty()) {
			Map<String, List<QtBasketMatchTeamStatisticModel>> matchTeamStatisticMap = makeInsertTeamStatistics(qtMatchEventStatistics);
			final List<QtBasketMatchTeamStatisticModel> insert = matchTeamStatisticMap
					.get("1");
			final List<QtBasketMatchTeamStatisticModel> update = matchTeamStatisticMap
					.get("2");

			if (!insert.isEmpty()) {
				PreparedStatement ps = getPreparedStatement("insert into"
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
				releaseConnect(ps);
				ps.close();
			}
			if (!update.isEmpty()) {
				PreparedStatement ps = getPreparedStatement("update md_qt_bb_match_teamstatistic_base"
						+ " set zd=?,kd=?,updateTime=now() where eventType=? and bsId=?");
				for (QtBasketMatchTeamStatisticModel matchEventPO : update) {
					ps.setString(1, matchEventPO.getZd());
					ps.setString(2, matchEventPO.getKd());
					ps.setString(3, matchEventPO.getEventType());
					ps.setInt(4, matchEventPO.getBsId());
					ps.addBatch();
				}
				ps.executeBatch();
				releaseConnect(ps);
				ps.close();

			}
		}
	}

	private Map<String, List<QtBasketMatchTeamStatisticModel>> makeInsertTeamStatistics(
			List<QtBasketMatchTeamStatisticModel> dataModels)
			throws SQLException {
		PreparedStatement ps = getPreparedStatement("select eventType from md_qt_bb_match_teamstatistic_base where bsId=? and source=?");
		ps.setInt(1, dataModels.get(0).getBsId());
		ps.setInt(2, dataModels.get(0).getSource());
		ResultSet rs = ps.executeQuery();
		List<String> eventType = new ArrayList<>();
		if (rs != null) {
			while (rs.next()) {
				eventType.add(rs.getString("eventType"));
			}
			rs.close();
		}
		releaseConnect(ps);
		ps.close();

		List<QtBasketMatchTeamStatisticModel> insert = new ArrayList<>();
		List<QtBasketMatchTeamStatisticModel> update = new ArrayList<>();
		Map<String, List<QtBasketMatchTeamStatisticModel>> updateMap = new HashMap<String, List<QtBasketMatchTeamStatisticModel>>();
		if (!eventType.isEmpty()) {
			for (QtBasketMatchTeamStatisticModel teamStatisticModel : dataModels) {
				if (eventType.contains(teamStatisticModel.getEventType())) {
					update.add(teamStatisticModel);
				} else {
					insert.add(teamStatisticModel);
				}
			}
		} else {
			insert.addAll(dataModels);
		}
		updateMap.put("1", insert);
		updateMap.put("2", update);
		return updateMap;
	}

	@Override
	public void storeZqJishiOdds(List<QtMatchOpOddsModel> odssList1,
			Qt_fb_match_oddsType oddsType) throws SQLException {
		if (!odssList1.isEmpty()) {
			List<QtMatchOpOddsModel> oddsModels = new ArrayList<>();
			for (QtMatchOpOddsModel qtMatchOpOddsModel : odssList1) {
				Integer bsId = queryBsId(qtMatchOpOddsModel.getQtBsId());
				if (bsId != 0) {
					qtMatchOpOddsModel.setBsId(bsId);
					oddsModels.add(qtMatchOpOddsModel);
				}
			}
			if (!oddsModels.isEmpty()) {
				if (oddsType == Qt_fb_match_oddsType.euro) {
					PreparedStatement psEuro = getPreparedStatement("update "
							+ " md_qt_odds_euro_base set euroOdds=?+'!'+euroOdds,changeTime=?+','+changeTime,"
							+ " kellyIndex='0.95,0.96,0.95!'+kellyIndex,updateTime=now() where bsId=? and corpId=?");
					for (QtMatchOpOddsModel itemMatchOpOddsModel : oddsModels) {
						psEuro.setString(1, itemMatchOpOddsModel.getHomeWinOdds()+","+itemMatchOpOddsModel.getDrawOdds()+","+itemMatchOpOddsModel.getGuestWinOdds());
						psEuro.setString(2, DateFormateUtil.getStringOfDate("yyyyMMddHHmmss", itemMatchOpOddsModel.getTimestamp()));
						psEuro.setLong(3,itemMatchOpOddsModel.getBsId());
						psEuro.setString(4, itemMatchOpOddsModel.getCorpId());
						psEuro.addBatch();
					}
					psEuro.executeBatch();
					releaseConnect(psEuro);
					psEuro.close();
				} else if (oddsType == Qt_fb_match_oddsType.asia
						|| oddsType == Qt_fb_match_oddsType.ou) {
					int type =  oddsType == Qt_fb_match_oddsType.asia ? 1
							: oddsType == Qt_fb_match_oddsType.ou ? 2
									: 0;
					List<QtMatchOpOddsModel> insert = makeFbMatchOuAsiaOddsInsert(oddsModels,type);
					PreparedStatement psAsiaOu = getPreparedStatement("insert into"
							+ " md_qt_odds_asiaOu_base(bsId,source,processStatus,corpId,HomeWinOdds,handicap,GuestWinOdds,timestamp,oddsType,createTime)values"
							+ "(?,?,?,?,?,?,?,?,?,now())");
					int j = 0;
					for (QtMatchOpOddsModel itemMatchOpOddsModel : insert) {
						psAsiaOu.setLong(1, itemMatchOpOddsModel.getBsId());
						psAsiaOu.setInt(2, itemMatchOpOddsModel.getSource());
						psAsiaOu.setInt(3,
								itemMatchOpOddsModel.getProcessStatus());
						psAsiaOu.setString(4, itemMatchOpOddsModel.getCorpId());

						psAsiaOu.setDouble(5,
								itemMatchOpOddsModel.getHomeWinOdds());
						psAsiaOu.setDouble(6,
								itemMatchOpOddsModel.getHandicap());
						psAsiaOu.setDouble(7,
								itemMatchOpOddsModel.getGuestWinOdds());
						psAsiaOu.setTimestamp(8, new Timestamp(
								itemMatchOpOddsModel.getTimestamp().getTime()));
						psAsiaOu.setInt(9,type);
						psAsiaOu.addBatch();
					}
					psAsiaOu.executeBatch();
					releaseConnect(psAsiaOu);
					psAsiaOu.close();
				}
			}
		}
	}

	private List<QtMatchOpOddsModel> makeFbMatchOuAsiaOddsInsert(
			List<QtMatchOpOddsModel> oddsModels,int type) throws SQLException {
		List<QtMatchOpOddsModel> asiaOuOddses = new ArrayList<>();
		PreparedStatement ps = getPreparedStatement("select HomeWinOdds,GuestWinOdds,handicap from md_qt_odds_asiaOu_base where bsId=? and corpId=? and oddsType=? order by timestamp desc limit 1");
		for(int i=0;i<oddsModels.size();i++){
			ps.setInt(1, oddsModels.get(i).getBsId());
			ps.setString(2, oddsModels.get(i).getCorpId());
			ps.setInt(3,type);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if(rs!=null){
				if(rs.next()){
					if(rs.getDouble("HomeWinOdds")==oddsModels.get(i).getHomeWinOdds()
							&& rs.getDouble("GuestWinOdds")==oddsModels.get(i).getGuestWinOdds()
							&& rs.getDouble("handicap")==oddsModels.get(i).getHandicap()){
					}else {
						asiaOuOddses.add(oddsModels.get(i));
					}
				}else{
					asiaOuOddses.add(oddsModels.get(i));
				}
			}
			rs = null;
			
		}
		releaseConnect(ps);
		ps.close();
		return asiaOuOddses;
	}

	private Integer queryBsId(String qtBsId) throws SQLException {
		int id = 0;
		PreparedStatement ps = getPreparedStatement("select id from md_qt_match_base where bsId=? order by id desc");
		ps.setString(1, qtBsId);
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			if (rs.next()) {
				id = rs.getInt(1);
				rs.close();
			}
		}
		releaseConnect(ps);
		ps.close();
		return id;
	}

	@Override
	public void storeLqJishiOdds(List<QtBasketMatchOddsModel> oddsData)
			throws SQLException {
		if (oddsData != null && !oddsData.isEmpty()) {
			// final List<QtMatchEventModel> insert = new ArrayList<>();
			// filterInsertEvent(insert, OpOddsData);
			// if (!OpOddsData.isEmpty()) {
			List<QtBasketMatchOddsModel> euroOdds = new ArrayList<QtBasketMatchOddsModel>();
			List<QtBasketMatchOddsModel> ouAsianOdds = new ArrayList<QtBasketMatchOddsModel>();
			for(QtBasketMatchOddsModel qtBasketMatchOddsModel:oddsData){
				if(qtBasketMatchOddsModel.getOddsType()==Qt_fb_match_oddsType.euro){
					euroOdds.add(qtBasketMatchOddsModel);
				}else{
					ouAsianOdds.add(qtBasketMatchOddsModel);
				}
			}
			storeEuroOdds(euroOdds);
			storeAsianOuOdds(ouAsianOdds);

		}
	}

	/**
	 * @param oddsData
	 * @param connection
	 * @param matchPO
	 * @throws SQLException
	 */
	private void storeEuroOdds(final List<QtBasketMatchOddsModel> oddsData) throws SQLException {
		if (!oddsData.isEmpty()) {
			List<QtBasketMatchOddsModel> insert = makeInsertBasketBallEuroOdds(oddsData);
			if(insert!=null&&!insert.isEmpty()){
				PreparedStatement psEuro = getPreparedStatement("insert into"
						+ " md_qt_basket_odds_euro_base(bsId,source,processStatus,corpId,HomeWinOdds,GuestWinOdds,timestamp,createTime)values"
						+ "(?,?,?,?,?,?,?,now())");
				int i = 0;
				for (QtBasketMatchOddsModel itemMatchOpOddsModel : insert) {
					psEuro.setInt(1, itemMatchOpOddsModel.getBsId());
					psEuro.setInt(2, itemMatchOpOddsModel.getSource());
					psEuro.setInt(3, itemMatchOpOddsModel.getProcessStatus());
					psEuro.setString(4, itemMatchOpOddsModel.getCorpId());
	
					psEuro.setDouble(5, itemMatchOpOddsModel.getHomeWinOdds());
					psEuro.setDouble(6, itemMatchOpOddsModel.getGuestWinOdds());
					psEuro.setTimestamp(7, new Timestamp(itemMatchOpOddsModel
							.getTimestamp().getTime()));
					psEuro.addBatch();
					if (++i > 10000) {
						psEuro.executeBatch();
					}
				}
				psEuro.executeBatch();
				releaseConnect(psEuro);
				psEuro.close();
			}
		}
	}

	private List<QtBasketMatchOddsModel> makeInsertBasketBallEuroOdds(
			List<QtBasketMatchOddsModel> oddsData) throws SQLException {
		List<QtBasketMatchOddsModel> matchOddsModels = new ArrayList<>();
		 
		PreparedStatement ps = getPreparedStatement("select HomeWinOdds,GuestWinOdds from md_qt_basket_odds_euro_base where bsId=? and corpId=? order by timestamp desc limit 1");
		for(int i=0;i<oddsData.size();i++){
			ps.setInt(1, oddsData.get(i).getBsId());
			ps.setString(2, oddsData.get(i).getCorpId());
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if(rs!=null){
				if(rs.next()){
					double homwin = rs.getDouble("HomeWinOdds");
					double guestwin = rs.getDouble("GuestWinOdds");
					if(oddsData.get(i).getHomeWinOdds()== homwin&&oddsData.get(i).getGuestWinOdds() == guestwin){
						//无需更新
					}else {
						matchOddsModels.add(oddsData.get(i));
					}
				}else{
					matchOddsModels.add(oddsData.get(i));
				}
			}
			rs = null;
		}
		releaseConnect(ps);
		ps.close();
		return matchOddsModels;
	}

	private boolean checkIsHavingEuroChuPan(Integer id) throws SQLException {
		PreparedStatement ps = getPreparedStatement("select count(*) from md_qt_basket_odds_euro_base where bsId=?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		int count = 0;
		if (rs != null) {
			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
		}
		releaseConnect(ps);
		ps.close();
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @param oddsData
	 * @param oddsType
	 * @param connection
	 * @param matchPO
	 * @throws SQLException
	 */
	private void storeAsianOuOdds(final List<QtBasketMatchOddsModel> oddsData)
			throws SQLException {

		int j = 0;
		List<QtBasketMatchOddsModel> insert = makeInsertBasketBallAsiaOuOdds(oddsData);
		if (!oddsData.isEmpty()) {
			PreparedStatement psAsiaOu = getPreparedStatement("insert into"
					+ " md_qt_basket_odds_asiaOu_base(bsId,source,processStatus,corpId,HomeWinOdds,handicapOrScore,GuestWinOdds,timestamp,oddsType,createTime)values"
					+ "(?,?,?,?,?,?,?,?,?,now())");
			for (QtBasketMatchOddsModel itemMatchOpOddsModel : insert) {
				psAsiaOu.setInt(1, itemMatchOpOddsModel.getBsId());
				psAsiaOu.setInt(2, itemMatchOpOddsModel.getSource());
				psAsiaOu.setInt(3, itemMatchOpOddsModel.getProcessStatus());
				psAsiaOu.setString(4, itemMatchOpOddsModel.getCorpId());

				psAsiaOu.setDouble(5, itemMatchOpOddsModel.getHomeWinOdds());
				psAsiaOu.setDouble(6, itemMatchOpOddsModel.getHandicapOrScore());
				psAsiaOu.setDouble(7, itemMatchOpOddsModel.getGuestWinOdds());
				psAsiaOu.setTimestamp(8, new Timestamp(itemMatchOpOddsModel
						.getTimestamp().getTime()));
				psAsiaOu.setInt(9, itemMatchOpOddsModel.getOddsType() == Qt_fb_match_oddsType.asia ? 1
						: itemMatchOpOddsModel.getOddsType() == Qt_fb_match_oddsType.ou ? 2 : 0);
				psAsiaOu.addBatch();
				if (++j > 10000) {
					psAsiaOu.executeBatch();
				}
			}
			psAsiaOu.executeBatch();
			releaseConnect(psAsiaOu);
			psAsiaOu.close();
		}
	}

	private List<QtBasketMatchOddsModel> makeInsertBasketBallAsiaOuOdds(
			List<QtBasketMatchOddsModel> oddsData) throws SQLException {
		List<QtBasketMatchOddsModel> oddsModels = new ArrayList<>();
		PreparedStatement ps = getPreparedStatement("select homeWinOdds,guestWinOdds,handicapOrScore from md_qt_basket_odds_asiaOu_base where bsId=? and corpId=? and oddsType=? order by timestamp desc limit 1");
		for(int i=0;i<oddsData.size();i++){
			int type = oddsData.get(i).getOddsType()==Qt_fb_match_oddsType.asia?1:2;
			ps.setInt(1,  oddsData.get(i).getBsId());
			ps.setString(2, oddsData.get(i).getCorpId());
			ps.setInt(3, type);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if(rs!=null){
				if(rs.next()){
					if(oddsData.get(i).getHomeWinOdds()==rs.getDouble("homeWinOdds")&&oddsData.get(i).getGuestWinOdds()==rs.getDouble("guestWinOdds")&&
							oddsData.get(i).getHandicapOrScore()==rs.getDouble("handicapOrScore")){
						//无需更新
					}else {
						oddsModels.add(oddsData.get(i));
					}
				}else{
					oddsModels.add(oddsData.get(i));
				}
			}
		}
		releaseConnect(ps);
		ps.close();
		return oddsModels;
	}

	private boolean checkIsHavingAsianOuChuPan(Integer id,
			Qt_fb_match_oddsType oddsType) throws SQLException {
		PreparedStatement ps = getPreparedStatement("select count(*) from md_qt_basket_odds_asiaOu_base where bsId=? and oddsType=?");
		ps.setInt(1, id);
		ps.setInt(2, oddsType==Qt_fb_match_oddsType.asia?1:2);
		ResultSet rs = ps.executeQuery();
		int count = 0;
		if (rs != null) {
			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
		}
		releaseConnect(ps);
		ps.close();
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<FbMatchBaseInfoPO> queryAllZqMatchInMatchNotHaveLiveUrl() throws SQLException {
		List<FbMatchBaseInfoPO> ballMatchModels = new ArrayList<>();
		PreparedStatement ps = getPreparedStatement("select bsId,id from md_qt_match_base where (matchStatus=1  or matchStatus=2 or matchStatus=3 or matchStatus=4) and televison='1' and televisonUrl is null and  jingcaiId is not null");
		ps.executeQuery();
		ResultSet st = ps.getResultSet();
		if (st != null) {
			while (st.next()) {
				FbMatchBaseInfoPO matchBaseInfoPO = new FbMatchBaseInfoPO();
				matchBaseInfoPO.setBsId(st.getString("bsId"));
				matchBaseInfoPO.setId(st.getInt("id"));
				ballMatchModels.add(matchBaseInfoPO);
			}
			st.close();
		}
		releaseConnect(ps);
		ps.close();
		return ballMatchModels;
	}

	@Override
	public void saveZqLiveUrl(String televisonUrl, long id) throws SQLException {
		PreparedStatement preparedStatement = getPreparedStatement("update md_qt_match_base set televisonUrl=? where id=?");
		preparedStatement.setString(1, televisonUrl);
		preparedStatement.setLong(2, id);
		preparedStatement.executeUpdate();
		releaseConnect(preparedStatement);
		preparedStatement.close();
	}

	@Override
	public FbMatchBaseInfoPO queryFbMatchById(String qtbsId) throws SQLException {
		PreparedStatement preparedStatement = getPreparedStatement("select jingcaiId,matchTime from md_qt_match_base where bsId=? and jingcaiId is not null");
		preparedStatement.setString(1, qtbsId);
		preparedStatement.executeQuery();
		ResultSet st = preparedStatement.getResultSet();
		FbMatchBaseInfoPO fbMatchBaseInfoPO = null;
		if(st!=null){
			while(st.next()){
				fbMatchBaseInfoPO = new FbMatchBaseInfoPO();
				fbMatchBaseInfoPO.setJingcaiId(st.getString("jingcaiId"));
				fbMatchBaseInfoPO.setMatchTime(st.getTimestamp("matchTime"));
			}
		}
		releaseConnect(preparedStatement);
		preparedStatement.close();
		return fbMatchBaseInfoPO;
	}

	@Override
	public String queryFbOpOdds(String corpId, String qtBsId) throws SQLException {
		String euroodds = null;
		PreparedStatement preparedStatement = getPreparedStatement(
				"select euroOdds from md_qt_odds_euro_base a,md_qt_match_base b,"
				+ "md_lottery_corp_base c where a.corpId=c.euroId and a.bsId=b.id "
				+ " and c.corpId=? and b.bsId=? order by a.createTime desc");
		preparedStatement.setString(1, corpId);
		preparedStatement.setString(2, qtBsId);
		preparedStatement.executeQuery();
		ResultSet st = preparedStatement.getResultSet();
		if(st!=null){
			while(st.next()){
				euroodds=st.getString("euroOdds");
			}
		}
		releaseConnect(preparedStatement);
		preparedStatement.close();
		return euroodds;
	}

	@Override
	public FbMatchAsiaOuOddsInfoPO queryFbAsianOuInitOdd(String corpId,
			String qtBsId,int oddsType) {
		// TODO Auto-generated method stub
		FbMatchAsiaOuOddsInfoPO asiaOuOddsInfoPO = null;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getPreparedStatement(
					"select HomeWinOdds,GuestWinOdds,b.handicap from md_qt_odds_asiaOu_base a,md_qt_match_base b "
					+ "where a.bsId=b.id and b.bsId=? and a.corpId=? and a.oddsType=? order by timestamp asc limit 1");
			preparedStatement.setString(1,qtBsId);
			preparedStatement.setString(2, corpId);
			preparedStatement.setInt(3, oddsType);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.getResultSet();
			if(rs!=null){
				while (rs.next()) {
					asiaOuOddsInfoPO = new FbMatchAsiaOuOddsInfoPO();
					asiaOuOddsInfoPO
							.setHomeWinOdds(rs.getDouble("homeWinOdds"));
					asiaOuOddsInfoPO.setHandicap(rs.getDouble("handicap"));
					asiaOuOddsInfoPO.setGuestWinOdds(rs
							.getDouble("guestWinOdds"));
				}
				
			}
			releaseConnect(preparedStatement);
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
		return asiaOuOddsInfoPO;
	}

	@Override
	public BasketBallMatchPO queryBasketMatchById(String qtBsId) {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = getPreparedStatement("select matchTime,jingcaiId,id from md_bb_match_base where bsId=?");
			preparedStatement.setString(1, qtBsId);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.getResultSet();
			BasketBallMatchPO matchPO = null;
			if(rs.next()){
				matchPO = new BasketBallMatchPO();
				matchPO.setMatchTime(rs.getTimestamp("matchTime"));
				matchPO.setJingcaiId(rs.getString("jingcaiId"));
				matchPO.setId(rs.getLong("id"));
			}
			releaseConnect(preparedStatement);
			preparedStatement.close();
			return matchPO;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

}
