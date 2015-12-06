package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.JishiBifenDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.ConnectionPool;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.JishiBifenDataStoreDaoImpl;
import com.unison.lottery.weibo.data.crawler.service.store.service.JishiBifenDataStoreService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseServiceImpl;
import com.unison.lottery.weibo.dataservice.crawler.service.model.OddsBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;

/**
 * 抓取赔率
 * @author 彭保星
 *
 * @since 2014年12月11日下午2:00:22
 */
public class CrawlerZqJingcaiOddsUseThread extends AbstractJishiDataUseThread implements Runnable {
	
	private Logger log = LoggerFactory
			.getLogger(CrawlerZqJingcaiOddsUseThread.class);

	private FbMatchBaseInfoPO matchBaseInfoPO;
	
	private Qt_fb_match_oddsType oddsType;
	
	public CrawlerZqJingcaiOddsUseThread(Qt_fb_match_oddsType oddsType,FbMatchBaseInfoPO matchBaseInfoPO) throws SQLException {
		jishiBifenDataStoreDao = new JishiBifenDataStoreDaoImpl();
		header = new HashMap<>();
		jishiBifenParseService = new JishiDataParseServiceImpl();
		this.oddsType = oddsType;
		this.matchBaseInfoPO = matchBaseInfoPO;
	}
	
	@Override
	public void run() {
		String oddsName = "";
		switch (oddsType) {
		case euro:
			oddsName = "欧赔";
			break;
		case asia:
			oddsName = "亚赔";
		default:
			oddsName = "大小";
			break;
		}
		log.info("线程[{}]抓取并存储bsId为{}的{}数据...", Thread.currentThread()
				.getName(), matchBaseInfoPO.getBsId(),oddsName);
		try {
			List<QtMatchOpOddsModel> qtMatchOpOddsModels = null;
			List<OddsBaseModel> oddsId = null;
			int count = 0;
			do {
				if (oddsId == null) {
					makeHeader();
				}
				oddsId = jishiBifenParseService.crawlerZqJingcaiMatchJishiOdds(
						header, matchBaseInfoPO.getBsId(), oddsType);
				if (oddsId != null) {
					count = 0;
					for (OddsBaseModel oddsBaseModel : oddsId) {
						do {
							qtMatchOpOddsModels = jishiBifenParseService
									.crawlerJingcaiMatchHistoryData(header,
											oddsBaseModel.getOddsId(), oddsType);
							count++;
						} while (qtMatchOpOddsModels == null && count<MAX_CRAWLER_COUNT);
						jishiBifenDataStoreDao.storeJishiOdds(
								qtMatchOpOddsModels, oddsType, matchBaseInfoPO,
								oddsBaseModel);
					}
				}
				count++;
			} while (oddsId == null && count<MAX_CRAWLER_COUNT);
			
		} catch (Exception e) {
			log.error("抓取过程中出错:",e);
		}
		log.info("线程[{}]抓取并存储bsId为{}的{}数据完成", Thread.currentThread()
				.getName(), matchBaseInfoPO.getBsId(),oddsName);
		
	}

}
