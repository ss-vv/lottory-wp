package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueScoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchEventInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchLineupDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchOpOddsDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.OddsCompanyStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.SeasonInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.unison.lottery.weibo.dataservice.crawler.service.CrawlerDataParseService;
import com.unison.lottery.weibo.mq.CrawlerFbZqCompanyOddsHandle;
import com.xhcms.commons.mq.MessageHandler;

/**
 * 处理抓取单个比赛的单个公司的赔率数据activemq监听处理对象
 * 
 * @author baoxing.peng
 *
 * @since 2015年2月3日下午5:19:01
 */
public class CrawlerFbMatchHistoryOddsOneCompany extends
		AbstractCrawlerDataStoreService implements
		MessageHandler<CrawlerFbZqCompanyOddsHandle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6299603205135837506L;
	private static final int CRAWLER_NUM = 10; // 约定要抓取的次数
	@Autowired
	public OddsCompanyStoreDao crawlerOddsCompanyStoreDao;

	@Autowired
	private MatchOpOddsDataStoreDao crawlerMatchOpOddsDataStoreDao;

	public Logger log = LoggerFactory.getLogger(getClass());
	public Integer source;
	@Autowired
	private CrawlerDataParseService crawlerDataParseService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void handle(CrawlerFbZqCompanyOddsHandle zqCompanyOddsHandle) {

		if (zqCompanyOddsHandle != null) {
			try {
				logger.info("", zqCompanyOddsHandle);
				FbMatchOpOddsInfoPO fbMatchOpOddsInfoPOs = null;
				int i = 0;
				do {
					if (fbMatchOpOddsInfoPOs == null) {
						makeHeaderFromFile();
					}
					// 抓取
					fbMatchOpOddsInfoPOs = crawlerDataParseService
							.crawlerFbMatchOpOddsOneCompany(
									zqCompanyOddsHandle, header);
					i++;
				} while (fbMatchOpOddsInfoPOs == null && i < CRAWLER_NUM);
				if (fbMatchOpOddsInfoPOs !=null && StringUtils.isNotBlank(fbMatchOpOddsInfoPOs.getEuroOdds())) {
					crawlerDataParseService
							.sendFbMatchOpOneCompanyOddsToServer(
									fbMatchOpOddsInfoPOs, zqCompanyOddsHandle);
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				logger.info("抓取球探足球新接口欧赔数据出错:",e);
			}
		}
	}

}
