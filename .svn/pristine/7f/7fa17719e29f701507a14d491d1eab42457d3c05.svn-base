package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.ConnectionPool;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.JishiBifenDataStoreDaoImpl;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataImpl;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseServiceImpl;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;

public class CrawlerLqJingcaiOddsUseThreadAndSend extends AbstractJishiDataUseThread implements Runnable{
	private Logger log = LoggerFactory
			.getLogger(CrawlerZqJingcaiOddsUseThread.class);

	private BasketBallMatchModel matchBaseInfoPO;
	
	private Qt_fb_match_oddsType oddsType;
	
	protected static String lastReponse;
	
	public CrawlerLqJingcaiOddsUseThreadAndSend(Qt_fb_match_oddsType oddsType,BasketBallMatchModel matchBaseInfoPO) {
		jishiBifenDataStoreDao = new JishiBifenDataStoreDaoImpl();
		header = new HashMap<>();
		jishiBifenParseService = new JishiDataParseServiceImpl();
		this.oddsType = oddsType;
		this.matchBaseInfoPO = matchBaseInfoPO;
	}
	@Override
	public void run(){
		try {
			log.info("抓取并存储bsId为{}的{}赔率数据...", matchBaseInfoPO.getBsId(),oddsType);
			List<QtBasketMatchOddsModel> oddsModels = null;
			Integer i=0;
			do {
				if (oddsModels == null) {
					makeHeaderFromFile();
				}
				oddsModels = jishiBifenParseService.crawlerLqJingcaiMatchJishiOdds(
						header, matchBaseInfoPO.getBsId(), oddsType,lastReponse);
				if (oddsModels != null) {
						jishiBifenParseService.sendLqJishiOdds(
								oddsModels, oddsType, matchBaseInfoPO);
				}
				i++;
			} while (oddsModels == null&&i<=MAX_CRAWLER_COUNT);
		} catch (Exception e) {
			log.error("抓取{}赔率数据出错{}",oddsType,e.getStackTrace());
			System.exit(0);
		}
	}
	private void makeHeaderFromFile() {
		if (header == null) {
			header = new HashMap<String, String>();
		}
		String headerList = SystemPropertiesUtil.getPropsValue("headerList");
		String[] header1 = headerList.split("\\;",-1);
		int size = header1.length;
		Random random = new Random();
		int index = random.nextInt(size);
		if (header1[index] != null) {
			String[] agent = header1[index].split("\\:",-1);
			// 生成ua
			String user_agent = String.format(
					"Score (%s; %s)",
					new Object[] { agent[1],
							agent[0] });
			log.debug("user_agent",agent.length);
			header.put("User-Agent", user_agent);
		}
	}
	
}
