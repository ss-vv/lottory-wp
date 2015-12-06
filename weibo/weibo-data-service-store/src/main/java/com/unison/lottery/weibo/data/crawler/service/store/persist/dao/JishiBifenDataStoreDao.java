package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.OddsBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;

/**
 * @author 彭保星
 *
 * @since 2014年12月1日下午5:39:45
 */
public interface JishiBifenDataStoreDao {

	UserAgentPO getRandomHeader() throws SQLException;

	String getNowSeasonByLeagueId(String leagueId) throws SQLException;

	/**
	 * 足球竞彩比分
	 * @param qtMatchBaseModels
	 * @throws SQLException 
	 */
	void updateMatchData(List<QtMatchBaseModel> qtMatchBaseModels) throws SQLException;

	/**
	 * 是否竞彩篮球比分
	 * @param ballMatchModels
	 * @param b
	 * @throws SQLException 
	 * @throws UnsupportedEncodingException 
	 */
	void storeLqJishi(List<BasketBallMatchModel> ballMatchModels, boolean b) throws SQLException, UnsupportedEncodingException;

	/**
	 * 根据类型查询赔率公司
	 */
	List<String> queryOddsCompany(Qt_fb_match_oddsType oddsType);

	/**
	 * 查询当天未开赛的足球竞彩赛程
	 * @return
	 * @throws SQLException 
	 */
	List<FbMatchBaseInfoPO> queryAllJingcaiMatch() throws SQLException;

	/**
	 * 存储即时赔率
	 * @param qtMatchOpOddsModels
	 * @param oddsType
	 * @param matchBaseInfoPO
	 * @param oddsBaseModel
	 * @throws SQLException 
	 */
	void storeJishiOdds(List<QtMatchOpOddsModel> qtMatchOpOddsModels,
			Qt_fb_match_oddsType oddsType, FbMatchBaseInfoPO matchBaseInfoPO,
			OddsBaseModel oddsBaseModel) throws SQLException;

	/**
	 * 查询所有未开赛或者正在比赛的竞彩篮球赛程
	 * @return
	 * @throws SQLException 
	 */
	List<BasketBallMatchModel> queryAllJingcaiLqMatch() throws SQLException;

	List<FbMatchBaseInfoPO> queryAllZqMatchInLive() throws SQLException;

	void storeMatchEventData(List matchEvents) throws SQLException;

	void storeMatchStatisticData(
			List<QtMatchStatisticModel> matchStatisticModels) throws SQLException;

	/**
	 * 查询所有正在比赛竞彩篮球
	 * @return
	 * @throws SQLException 
	 */
	List<BasketBallMatchModel> queryAllBasketMatchInLive() throws SQLException;

	/**
	 * 
	 * @param qtMatchEventStatistics
	 * @throws SQLException 
	 */
	void storeBasketMatchPlayerStatisticData(
			List<QtBasketMatchPlayerStatisticModel> qtMatchEventStatistics) throws SQLException;

	/**
	 * 
	 * @param qtMatchEventStatistics
	 * @throws SQLException 
	 */
	void storeMatchTeamStatisticData(
			List<QtBasketMatchTeamStatisticModel> qtMatchEventStatistics) throws SQLException;

	void storeZqJishiOdds(List<QtMatchOpOddsModel> oddsModels,
			Qt_fb_match_oddsType oddsType) throws SQLException;

	void storeLqJishiOdds(List<QtBasketMatchOddsModel> oddsModels) throws SQLException;

	/**
	 * 获取正在比赛有直播并且没有抓取直播地址的比赛
	 * @return
	 * @throws SQLException 
	 */
	List<FbMatchBaseInfoPO> queryAllZqMatchInMatchNotHaveLiveUrl() throws SQLException;

	/**
	 * 保存足球比赛直播地址
	 * @param televisonUrl
	 * @param id
	 * @throws SQLException 
	 */
	void saveZqLiveUrl(String televisonUrl, long id) throws SQLException;

	/**
	 * 根据球探比赛id获取竞彩比赛
	 * @param string
	 * @return
	 * @throws SQLException 
	 */
	FbMatchBaseInfoPO queryFbMatchById(String string) throws SQLException;

	/**
	 * 查询足球欧赔
	 * @param corpId
	 * @param qtBsId
	 * @return
	 * @throws SQLException 
	 */
	String queryFbOpOdds(String corpId, String qtBsId) throws SQLException;

	FbMatchAsiaOuOddsInfoPO queryFbAsianOuInitOdd(String corpId, String qtBsId,int oddsType);

	BasketBallMatchPO queryBasketMatchById(String qtBsId);

}
