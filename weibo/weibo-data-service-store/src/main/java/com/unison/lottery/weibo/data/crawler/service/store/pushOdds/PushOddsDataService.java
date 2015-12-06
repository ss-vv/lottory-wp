package com.unison.lottery.weibo.data.crawler.service.store.pushOdds;

import java.util.List;
import java.util.Map;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;

/**
 * 
 * @author baoxing.peng
 *
 * @since 2015年3月6日上午11:51:29
 */
public interface PushOddsDataService {
	public void pushFbOddsMessage(Qt_fb_match_oddsType oddsType,
			List<QtMatchOpOddsModel> oddsModels);
	/**
	 * 
	 * @param basketMatchOddsModels
	 * @param basMap key为qtBsId
	 */
	public void pushBbOddsMessage(List<QtBasketMatchOddsModel> basketMatchOddsModels, Map<String, BasketBallMatchPO> basMap);
}
