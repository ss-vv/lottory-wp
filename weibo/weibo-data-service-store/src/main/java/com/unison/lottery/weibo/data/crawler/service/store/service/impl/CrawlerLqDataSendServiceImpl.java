package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueSeasonPO;
import com.unison.lottery.weibo.data.crawler.service.store.service.CrawlerLqDataSendService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.parse.index.i;
import com.unison.lottery.weibo.dataservice.crawler.parse.repository.c;
import com.unison.lottery.weibo.dataservice.crawler.service.CrawlerDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BbLeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.util.BeanConvertUtil;

/**
 * 
 * @author baoxing.peng
 *
 * @since 2015年1月12日下午4:43:25
 */
public class CrawlerLqDataSendServiceImpl implements CrawlerLqDataSendService {
	private static final int CRAWLER_NUM = 10; // 约定要抓取的次数
	private static final Integer CUP_TYPE = 2;
	private static final Integer LEAGUE_TYPE = 1;

	public Logger log = LoggerFactory.getLogger(getClass());
	public Integer source = 1;
	private Map<String, String> header;
	@Autowired
	private CrawlerDataParseService crawlerDataParseService;
	@Override
	public void crawlerLqJishiBifenAndSend() {
		log.info("抓取球探篮球即时比分数据开始!");
		try {
			List<BasketBallMatchModel> ballMatchModels = null;
			int i=0;
			do {
				if (ballMatchModels == null) {
					makeHeaderFromFile();
				}
				ballMatchModels = crawlerDataParseService
						.crawlerBaskerBallJishiBifen(null, header);
				i++;
			} while (ballMatchModels == null && i<=CRAWLER_NUM);

			crawlerDataParseService.sendLqJishiMatchInfo(ballMatchModels, false);
			log.info("篮球即时数据抓取完成!");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("抓取球探篮球即时比分数据出错：{}", e);
		}
	}
	
	private void makeHeaderFromFile() {
		if (header == null) {
			header = new HashMap<String, String>();
		}
		String headerList = SystemPropertiesUtil.getPropsValue("headerList");
		String[] header1 = headerList.split("\\;", -1);
		int size = header1.length;
		Random random = new Random();
		int index = random.nextInt(size);
		if (header1[index] != null) {
			String[] agent = header1[index].split("\\:", -1);
			// 生成ua
			String user_agent = String.format("Score (%s; %s)", new Object[] {
					agent[1], agent[0] });
			log.debug("user_agent", agent.length);
			header.put("User-Agent", user_agent);
		}
	}

	@Override
	public void crawlerLqLeagueNowSeasonMessAndSend() {
		log.info("抓取并存储球探篮球联赛数据开始。。。。");
		try {
			int i=0;
			List<BbLeagueInfoModel> leagueInfoModels = null;
			// 抓取10次，如果为null，结束抓取
			do {
				// 初始化UA
				makeHeaderFromFile();
				leagueInfoModels = crawlerDataParseService.getBasketLeagueInfo(
						null, header);
				i++;
			} while (leagueInfoModels == null&&i<CRAWLER_NUM);
			crawlerDataParseService.sendBbLeague(leagueInfoModels);
			log.info("抓取并存储球探篮球联赛数据完成。。。。");
		} catch (Exception e) {
			log.error("抓取并存储球探篮球联赛数据出错:{}", e);
		}
	}

	@Override
	public void crawlerHistoryMatchAndSend() {
		log.info("查询篮球联赛当前赛季赛程开始!");
		try {
			// 查询所有当前赛季的联赛赛季子联赛
			List<BasketBallLeagueSeasonPO> ballLeagueSeasonPOs = crawlerDataParseService
					.gotAllLqSubLeagueNotCrawler(1, 0, 1);
			if (ballLeagueSeasonPOs != null && !ballLeagueSeasonPOs.isEmpty()) {
				List<BasketBallLeagueSeasonModel> basketBallLeagueSeasonModels = BeanConvertUtil
						.changePOToModel(ballLeagueSeasonPOs,
								BasketBallLeagueSeasonModel.class);
				List<BasketBallMatchModel> ballMatchModels = null;
				for (BasketBallLeagueSeasonModel basketBallLeagueSeasonModel : basketBallLeagueSeasonModels) {
					do {
						if (ballMatchModels == null) {
							makeHeaderFromFile();
						}
						ballMatchModels = crawlerDataParseService
								.crawlerBasketBallLeagueHistoryMatch(null,
										header, basketBallLeagueSeasonModel);
					} while (ballMatchModels == null);
						crawlerDataParseService.sendLqHistoryMatch(
								basketBallLeagueSeasonModel, ballMatchModels);
				}
			}
		} catch (Exception exception) {
			log.error("抓取球探篮球联赛历史赛程数据出错：{}", exception);
		}
	}

	@Override
	public void crawlerHistorySubCupAndSend() {
		log.info("抓取球探篮球杯赛当前赛季分组信息历史数据开始......");
		try {
			List<BasketBallLeagueSeasonPO> leagueSeasonBasePOs = crawlerDataParseService
					.gotAllLqLeagueSeasonNotSub(source, CUP_TYPE);
			if (leagueSeasonBasePOs != null && !leagueSeasonBasePOs.isEmpty()) {
				List<BasketBallLeagueSeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(leagueSeasonBasePOs,
								BasketBallLeagueSeasonModel.class);
				List<BasketBallLeagueSeasonModel> seasonModels2 = null;
				for (BasketBallLeagueSeasonModel seasonModel : seasonModels) {
					int i=0;
					do {
						if (seasonModels2 == null) {
							makeHeaderFromFile();
						}
						seasonModels2 = crawlerDataParseService
								.getBasketCupHistoryGroupMess(header, null,
										seasonModel);
						i++;
					} while (seasonModels2 == null&&i<=CRAWLER_NUM); // 如果没有抓取到结果
					// // 存储杯赛分组数据
					crawlerDataParseService.sendLqCupGroup(
							seasonModels2, CUP_TYPE);

				}
			}
			log.info("探杯赛分组信息历史数据抓取完成.....");
		} catch (Exception exception) {
			exception.printStackTrace();
			log.error("抓取球探杯赛分组信息历史数据出错:", exception);
		}
	}

	@Override
	public void crawlerHistorySubLeagueAndSend() {
		log.info("抓取球探篮球子联赛数据开始!");
		try {
			List<BasketBallLeagueSeasonPO> basketBallLeagueSeasonPOs = crawlerDataParseService
					.gotAllLqLeagueSeasonNotSub(1, 1);
			List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels = BeanConvertUtil
					.changePOToModel(basketBallLeagueSeasonPOs,
							BasketBallLeagueSeasonModel.class);
			List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels2 = null;
			for (BasketBallLeagueSeasonModel ballLeagueSeasonModel : ballLeagueSeasonModels) {
				// 抓取赛季的常规赛、季前赛和季后赛
				do {
					if (ballLeagueSeasonModels2 == null) {
						makeHeaderFromFile();
					}
					ballLeagueSeasonModels2 = crawlerDataParseService
							.parseLqSubLeagueSeason(ballLeagueSeasonModel,
									header, null, 1);
				} while (ballLeagueSeasonModels2 == null);
				List<BasketBallLeagueSeasonModel> basketBallLeagueSeasonModels = null;
				for (BasketBallLeagueSeasonModel ballLeagueSeasonModel2 : ballLeagueSeasonModels2) {
					do {
						if (ballLeagueSeasonModels2 == null) {
							makeHeaderFromFile();
						}
						basketBallLeagueSeasonModels = crawlerDataParseService
								.parseLqSubLeagueSeason(ballLeagueSeasonModel2,
										header, null, 2);
					} while (basketBallLeagueSeasonModels == null);
					crawlerDataParseService
							.sendLqSubLeague(basketBallLeagueSeasonModels);

				}
				log.info("联赛id为{}的{}的子联赛抓取完成...",
						ballLeagueSeasonModel.getLeagueId(),
						ballLeagueSeasonModel.getSeasonName());

			}
		} catch (Exception exception) {
			log.error("抓取球探篮球子联赛数据出错:{}", exception);
		}
	}

	@Override
	public void crawlerHistoryCupMatchAndSend() {
		log.info("查询篮球杯赛历史赛程和积分开始!");
		try {
			// 查询所有未抓取过的杯赛赛季分组赛
			List<BasketBallLeagueSeasonPO> ballLeagueSeasonPOs = crawlerDataParseService
					.gotAllLqSubLeagueNotCrawler(1, 0, 2);
			List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels = BeanConvertUtil
					.changePOToModel(ballLeagueSeasonPOs,
							BasketBallLeagueSeasonModel.class);
			Map<String, Object> basketBallMatchAndLeagueScore = null;
			for (BasketBallLeagueSeasonModel basketBallLeagueSeasonModel : ballLeagueSeasonModels) {
				do {
					if (basketBallMatchAndLeagueScore == null) {
						makeHeaderFromFile();
					}
					basketBallMatchAndLeagueScore = crawlerDataParseService
							.parseLqCupMatch(null, header,
									basketBallLeagueSeasonModel);

				} while (basketBallMatchAndLeagueScore == null);
				crawlerDataParseService.sendCupMatch(
						basketBallLeagueSeasonModel,
						basketBallMatchAndLeagueScore);
			}
			log.info("篮球杯赛和对应积分抓取完成，共抓取{}个联赛赛季的数据",
					ballLeagueSeasonModels.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("抓取球探杯赛历史赛程数据出错:", e);
		}
	}
}
