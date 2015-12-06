package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;

/**
 * 存储球探网 赛事欧赔数据
 * @author 崔桂祥
 */
public interface MatchOpOddsDataStoreDao {

	/**
	 * 查询所有赔率公司
	 * @param oddsType 
	 */
	public List<String> queryOddsCompany(Qt_fb_match_oddsType oddsType);
	
	/**
	 * 查询所有正在比赛的数据
	 * @return
	 */
	List<FbMatchBaseInfoPO> queryAllMatchDataHasFinish(int startPos,Qt_fb_match_oddsType oddsType);

	/**
	 * 存储比赛欧赔数据
	 * @param matchBaseModels
	 * @throws Exception 
	 */
	void storeMatchOpOddsData(List<QtMatchOpOddsModel> teamModels,Qt_fb_match_oddsType oddsType) throws Exception;
	
	
	
	
	/**
	 * 查询 篮球 所有赔率公司
	 * @param oddsType 
	 */
	public List<String> queryBasketOddsCompany(Qt_fb_match_oddsType oddsType);
	
	/**
	 * 查询 篮球 所有正在比赛的数据
	 * @param source 
	 * @return
	 */
	List<BasketBallMatchPO> queryAllBasketMatchDataHasFinish(int startPos,Qt_fb_match_oddsType oddsType, int source);
	
	/**
	 * 存储 篮球 比赛欧赔数据
	 * @param matchBaseInfoPO 
	 * @param matchBaseModels
	 * @throws Exception 
	 */
	void storeBasketMatchOpOddsData(List<QtBasketMatchOddsModel> teamModels,Qt_fb_match_oddsType oddsType, BasketBallMatchPO matchBaseInfoPO) throws Exception;

}
