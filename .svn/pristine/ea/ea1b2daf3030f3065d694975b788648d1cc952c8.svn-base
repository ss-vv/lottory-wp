package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataImpl;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;

public class CrawlerLqJingcaiOddsUseThread extends AbstractJishiDataUseThread implements Callable<Integer> {
	private Logger log = LoggerFactory
			.getLogger(CrawlerZqJingcaiOddsUseThread.class);

	private BasketBallMatchModel matchBaseInfoPO;
	
	private Qt_fb_match_oddsType oddsType;
	
	protected static String lastReponse;
	
	private String corpId;
	
	public CrawlerLqJingcaiOddsUseThread(Qt_fb_match_oddsType oddsType,BasketBallMatchModel matchBaseInfoPO) throws SQLException {
		header = new HashMap<>();
		this.oddsType = oddsType;
		this.matchBaseInfoPO = matchBaseInfoPO;
	}
	public CrawlerLqJingcaiOddsUseThread(String corpId) {
		this.corpId = corpId;
	}
	@Override
	public Integer call(){
		try {
			log.info("抓取并存储篮球即时指数赔率数据...");
			List<QtBasketMatchOddsModel> oddsModels = null;
			Integer i=0;
			do {
				if (oddsModels == null) {
					makeHeader();
				}
				oddsModels = jishiBifenParseService.crawlerLqJingcaiMatchJishiOdds(
						header, lastReponse,corpId);
				
				i++;

			} while (oddsModels == null&&i<=MAX_CRAWLER_COUNT);
			if (oddsModels != null&&!oddsModels.isEmpty()) {
				List<QtBasketMatchOddsModel> oddsModels2 = new ArrayList<QtBasketMatchOddsModel>();
				Map<String, BasketBallMatchPO> basMap = new HashMap<String, BasketBallMatchPO>(oddsModels.size());
				for(int j=0;j<oddsModels.size();j++){
					BasketBallMatchPO basketBallMatchPO = basMap.get(oddsModels.get(j).getQtBsId());
					if(basketBallMatchPO==null){
						basketBallMatchPO = jishiBifenDataStoreDao.queryBasketMatchById(oddsModels.get(j).getQtBsId());
						if(basketBallMatchPO!=null){
							oddsModels.get(j).setBsId(Integer.valueOf(String.valueOf(basketBallMatchPO.getId())));
							oddsModels2.add(oddsModels.get(j));
							basMap.put(oddsModels.get(j).getQtBsId(), basketBallMatchPO);
						}
					}else{
						oddsModels.get(j).setBsId(Integer.valueOf(String.valueOf(basketBallMatchPO.getId())));
						oddsModels2.add(oddsModels.get(j));
					}
				}
				if(!oddsModels2.isEmpty()){
					PushOddsDataService pushOddsDataService = new PushOddsDataImpl(jishiBifenDataStoreDao);
					pushOddsDataService.pushBbOddsMessage(oddsModels2,basMap);
				}
				jishiBifenDataStoreDao.storeLqJishiOdds(oddsModels2);
			}
		} catch (Throwable e) {
			log.error("抓取{}赔率数据出错{}",oddsType,e);
			System.exit(0);
		}
		return 1;
	}
	
}
