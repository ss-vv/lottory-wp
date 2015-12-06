package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.data.crawler.service.store.mq.CrawlerQtWeiboDataMessageSender;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueScoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchEventInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchLineupDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchOpOddsDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.OddsCompanyStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.SeasonInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.service.CrawlerZqDataSendService;
import com.unison.lottery.weibo.data.crawler.service.store.service.impl.mq.CrawlerZqJingcaiOddsUseThreadAndSendActivemq;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.crawler.post.PostDataService;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.service.CrawlerDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.LeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.OddsBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchLineupModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.util.BeanConvertUtil;
import com.unison.lottery.weibo.mq.CrawlerFbZqCompanyOddsHandle;
import com.xhcms.commons.mq.MessageSender;

/**
 * 
 * @author baoxing.peng
 *
 * @since 2015年1月8日下午4:32:07
 */
public class CrawlerZqDataSendServiceImpl implements CrawlerZqDataSendService {
	private static final int CRAWLER_NUM = 10; // 约定要抓取的次数
	private static final Integer CUP_TYPE = 2;
	private static final Integer LEAGUE_TYPE = 1;

	public Logger log = LoggerFactory.getLogger(getClass());
	public Integer source = 1;
	private Map<String, String> header;
	@Autowired
	private CrawlerDataParseService crawlerDataParseService;
	@Autowired
	private CrawlerQtWeiboDataMessageSender messageSender;
	
	@Override
	public void crawlerFbLeagueDataAndSend() {
		log.info("抓取并存储球探联赛数据开始。。。。");
		try {
			List<LeagueInfoModel> leagueInfoModels = null;
			int count = 0;
			// 抓取10次，如果为null，结束抓取
			do {
				// 初始化UA
				makeHeaderFromFile();
				leagueInfoModels = crawlerDataParseService.getLeagueInfo(null,
						header);
				count++;
			} while (leagueInfoModels == null && count <= CRAWLER_NUM);
			crawlerDataParseService.sendFbLeague(leagueInfoModels);
			log.info("抓取并存储球探联赛数据完成。。。。");
		} catch (Exception e) {
			log.error("抓取并存储球探联赛数据出错:{}", e);
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
	public void crawlerFbSubLeagueSeasonMessAndSend() {
		log.info("球探网子联赛数据抓取开始...");
		try {
			// 查询球探所有赛季信息
			List<FbLeagueSeasonBasePO> fbLeagueSeasonBasePOs = crawlerDataParseService
					.queryAllSeasonMessageSubLeague(source, 1); // 查询所有不属于子联赛的联赛赛季历史数据
			List<SeasonModel> seasonModels = BeanConvertUtil.changePOToModel(
					fbLeagueSeasonBasePOs, SeasonModel.class);
			if (seasonModels != null && !seasonModels.isEmpty()) {
				crawlerAndStoreSeasonSubMesaageAndSend(seasonModels);
			}
		} catch (Exception e) {
			log.error("抓取并存储球探子联赛的联赛赛季历史数据出错:{}", e);
		}
	}

	/**
	 * 抓取并存储球探赛季子联赛数据，完善联赛数据信息，为抓取比赛数据做准备
	 * 
	 * @param seasonModels
	 * @throws IOException
	 */
	@Override
	public void crawlerAndStoreSeasonSubMesaageAndSend(
			List<SeasonModel> seasonModels) throws IOException {
		// TODO Auto-generated method stub
		int count = 0;
		log.info("抓取球探赛季的子联赛数据开始...");
		do {
			// 初始化UA
			makeHeaderFromFile();
			for (int i = count; i < seasonModels.size(); i++) {
				List<SeasonModel> subLeagueOfOneSeaon = crawlerDataParseService
						.getHistorySubLeagueMess(null, header,
								seasonModels.get(i));
				if (subLeagueOfOneSeaon != null
						&& subLeagueOfOneSeaon.size() > 0) {
					storeService(subLeagueOfOneSeaon);
					SeasonModel seasonModel = seasonModels.get(i);
					log.info("联赛id为{}的{}赛季的总轮数及子联赛数据抓取完成!",
							seasonModel.getLeagueId(),
							seasonModel.getSeasonName());
					count++;
				} else if (subLeagueOfOneSeaon != null
						&& subLeagueOfOneSeaon.size() == 0) { // 服务器内部错误时，跳过此联赛的当前赛季解析
					count++;
					continue;
				} else {
					break;
				}
			}
		} while (count < seasonModels.size());
		log.info("总共更新的赛季数目为:{}", String.valueOf(count));
	}

	private void storeService(List<SeasonModel> subLeagueOfOneSeaon)
			throws IOException {
		crawlerDataParseService.sendFbSubLeague(subLeagueOfOneSeaon,
				LEAGUE_TYPE);
	}

	@Override
	public void crawlerFbMatchDataAndSend() {
		log.info("抓取球探资料库赛程数据开始。。。。");
		try {
			// 查询球探所有赛季信息
			List<FbLeagueSeasonBasePO> fbLeagueSeasonBasePOs = crawlerDataParseService
					.getAllSeasonMessageNotCrawler(source); // 查询所有未抓取完的联赛赛季历史数据
			if (fbLeagueSeasonBasePOs != null
					&& !fbLeagueSeasonBasePOs.isEmpty()) {
				List<SeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(fbLeagueSeasonBasePOs,
								SeasonModel.class);
				if (seasonModels != null && !seasonModels.isEmpty()) {
					int countSeasonNum = 0;
					for (SeasonModel seasonModel : seasonModels) {
						crawlerAndStoreOneSeasonMatch(seasonModel);
						countSeasonNum++;
					}
					log.info("抓取球探资料库赛程数据完成,本次共抓取{}个赛季的数据...", countSeasonNum);

				}
			} else {
				log.info("不存在为抓取完的联赛当前赛季赛程数据!");
			}
			log.info("抓取并存储球探资料库赛程数据完成。。。。");
		} catch (Exception e) {
			log.error("抓取球探资料库赛程数据出错:", e);
		}
	}

	/**
	 * 抓取并存储单个赛季的数据
	 * 
	 * @param qtMap
	 * @param seasonModel
	 * @throws Exception
	 */
	public void crawlerAndStoreOneSeasonMatch(SeasonModel seasonModel)
			throws Exception {
		// 抓取10次，如果为null，结束抓取

		if (seasonModel != null) {
			// if (1 == seasonModel.getIsSubLeague()) { // 不是子联赛
			int crawlerCount = seasonModel.getCrawlerCount(); // 已抓取的轮数
			int roundCount = seasonModel.getTotalRound();// 总轮数
			List<QtMatchBaseModel> qtMatchBaseModels = null;
			int round = 0;
			for (int i = crawlerCount + 1; i <= roundCount + 1; i++) {
				do {
					if (qtMatchBaseModels == null) {
						makeHeaderFromFile();
					}
					// 如果是子联赛，轮数从0开始抓取
					if (seasonModel.getIsSubLeague() == 0) {
						round = i - 1;
					} else {
						round = i;
					}
					qtMatchBaseModels = crawlerDataParseService
							.getHistoryMatchProcess(null, header, seasonModel,
									round);
				} while (qtMatchBaseModels == null); // 如果没有抓取到结果

				crawlerDataParseService.sendMatchBaseMessage(qtMatchBaseModels,
						i, seasonModel.getSeasonId());
				log.info("抓取来源为球探资料库中的联赛id为{}子联赛id为{}的{}赛季第{}轮赛程信息完成....",
						seasonModel.getLeagueId(),
						seasonModel.getSubLeagueId(),
						seasonModel.getSeasonName(), i);
			}

		}
	}

	@Override
	public void crawlerFbCupGroupDataAndSend() {
		log.info("抓取球探杯赛分组信息历史数据开始......");
		try {
			List<FbLeagueSeasonBasePO> leagueSeasonBasePOs = crawlerDataParseService
					.queryAllSeasonMessageSubLeague(source, CUP_TYPE);
			if (leagueSeasonBasePOs != null && !leagueSeasonBasePOs.isEmpty()) {
				List<SeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(leagueSeasonBasePOs, SeasonModel.class);
				List<SeasonModel> seasonModels2 = null;
				for (SeasonModel seasonModel : seasonModels) {

					do {
						if (seasonModels2 == null) {
							makeHeaderFromFile();
						}
						seasonModels2 = crawlerDataParseService
								.getFbCupHistoryGroupMess(header, null,
										seasonModel);
					} while (seasonModels2 == null); // 如果没有抓取到结果
					// 存储杯赛分组数据
					crawlerDataParseService.sendFbSubLeague(seasonModels2,
							CUP_TYPE);

				}
			}
			log.info("探杯赛分组信息历史数据抓取完成.....");
		} catch (Exception exception) {
			exception.printStackTrace();
			log.error("抓取球探杯赛分组信息历史数据出错:", exception);
		}
	}

	@Override
	public void crawlerFbCupMatchDataAndSend() {
		log.info("抓取球探杯赛历史数据开始......");
		try {
			List<FbLeagueSeasonBasePO> leagueSeasonBasePOs = crawlerDataParseService
					.gotAllCupGroupMessToCraw(source, CUP_TYPE);
			if (leagueSeasonBasePOs != null && !leagueSeasonBasePOs.isEmpty()) {
				List<SeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(leagueSeasonBasePOs, SeasonModel.class);
				List<QtMatchBaseModel> qtMatchBaseModels = null;

				for (SeasonModel seasonModel : seasonModels) {
					// 查询联赛的当前赛季
					String seasonName = crawlerDataParseService
							.gotNowSeasonNameByLeagueId(seasonModel
									.getLeagueId());
					// 不抓取不是当前赛季的数据
					if (StringUtils.equals(seasonName,
							seasonModel.getSeasonName())) {
						do {
							if (qtMatchBaseModels == null) {
								makeHeaderFromFile();
							}
							qtMatchBaseModels = crawlerDataParseService
									.crawlerCupHistoryMatchData(header, null,
											seasonModel);

						} while (qtMatchBaseModels == null);
						crawlerDataParseService.sendCupMatchInfo(
								qtMatchBaseModels, seasonModel.getSeasonId());

						log.info("抓取来源为球探资料库中的杯赛id为{}分组赛为{}的{}赛季赛程信息完成....",
								seasonModel.getLeagueId(),
								seasonModel.getSubLeagueName(),
								seasonModel.getSeasonName());
					}
				}
			} else {
				log.info("不存在要抓取杯赛历史数据!");
			}
			log.info("杯赛历史数据抓取完成，共抓取{}场比赛的数据", leagueSeasonBasePOs.size());

		} catch (Exception exception) {
			exception.printStackTrace();

			log.error("抓取球探杯赛历史数据出错:{}", new Object[] { exception });
		}
	}

	@Override
	public void crawlerFbMatchLineupDataAndSend() {
		log.info("抓取球探对阵阵容数据开始......");
		try {
			List<FbMatchBaseInfoPO> allMatchHasFinishBsIds = crawlerDataParseService
					.gotAllMatchDataHasFinish();
			if (allMatchHasFinishBsIds != null
					&& !allMatchHasFinishBsIds.isEmpty()) {
				QtMatchLineupModel qtMatchLineupModel = null;
				for (FbMatchBaseInfoPO matchBaseInfoPO : allMatchHasFinishBsIds) {
					do {
						if (qtMatchLineupModel == null) {
							makeHeaderFromFile();
						}
						qtMatchLineupModel = crawlerDataParseService
								.crawlerMatchLineupHasFinishedData(header,
										null, matchBaseInfoPO.getBsId(),
										matchBaseInfoPO.getId());

					} while (qtMatchLineupModel == null);

					if (qtMatchLineupModel.getBsId() != null) {

						crawlerDataParseService
								.sendMatchLineupData(qtMatchLineupModel);
					}

					// log.info("抓取来源为球探资料库中的杯赛id为{}事件信息完成....",bs);
				}
			} else {
				log.info("不存在要抓取赛事事件历史数据!");
			}
			log.info("赛事事件数据抓取完成，共抓取{}场比赛的数据", allMatchHasFinishBsIds.size());
		} catch (Exception exception) {
			log.error("抓取球探赛事事件数据出错:{}", new Object[] { exception });
		}
	}

	@Override
	public void crawlerFbLeagueScoreDataAndSend() {
		log.info("抓取球探联赛积分开始...");
		try {
			List<FbLeagueSeasonBasePO> seasonBasePOs = crawlerDataParseService
					.gotAllSeasonNotHaveRule(1, source);
			if (seasonBasePOs != null && !seasonBasePOs.isEmpty()) {
				Map<String, Object> scoreRuleMap = null;
				List<SeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(seasonBasePOs, SeasonModel.class);
				for (SeasonModel seasonModel : seasonModels) {
					do {
						if (scoreRuleMap == null) {
							makeHeaderFromFile();
						}

						scoreRuleMap = crawlerDataParseService
								.getLeagueScoreAndRule(header, null,
										seasonModel);
					} while (scoreRuleMap == null);
					if ("0".equals(scoreRuleMap.get("0"))) {
						crawlerDataParseService.sendLeagueScoreData(
								scoreRuleMap, seasonModel);

						log.info("抓取联赛id为{}的{}赛季的联赛积分完成...",
								seasonModel.getLeagueId(),
								seasonModel.getSeasonName());
					} else {

					}
				}
				log.info("联赛积分数据抓取完成，一共抓取{}个赛季的数据!", seasonModels.size());
			}
		} catch (Exception exception) {
			log.error("抓取球探联赛积分出错:{}", exception);
		}
	}

	@Override
	public void crawlerCupScoreDataAndSend() {
		log.info("抓取球探杯赛积分历史数据开始...");
		try {
			// 获取没有抓取过积分的杯赛分组赛数据
			List<FbLeagueSeasonBasePO> seasonBasePOs = crawlerDataParseService
					.gotAllCupSeasonNotCrawler(2, 0, source);
			if (seasonBasePOs != null && !seasonBasePOs.isEmpty()) {
				List<FbLeagueScoreModel> fbLeagueScoreModels = null;
				List<SeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(seasonBasePOs, SeasonModel.class);
				for (SeasonModel seasonModel : seasonModels) {
					do {
						if (fbLeagueScoreModels == null) {
							makeHeaderFromFile();
						}

						fbLeagueScoreModels = crawlerDataParseService
								.getCupScore(header, null, seasonModel);
					} while (fbLeagueScoreModels == null);
					if (!fbLeagueScoreModels.isEmpty()) {
						crawlerDataParseService.sendCupScoreData(
								fbLeagueScoreModels, seasonModel);
						log.info("抓取杯赛id为{}的{}赛季的杯赛积分完成...",
								seasonModel.getLeagueId(),
								seasonModel.getSeasonName());
					}

				}
				log.info("杯赛积分数据抓取完成，一共抓取{}个赛季的数据!", seasonModels.size());
			}
		} catch (Exception exception) {
			log.error("抓取球探杯赛积分出错:{}", exception);
		}
	}

	@Override
	public void crawlerQtJishiBifenAndSend() {
		log.info("抓取球探足球即时比分数据...");

		try {

			List<QtMatchBaseModel> matchBaseInfos = null;
			do {
				if (matchBaseInfos == null) {
					makeHeaderFromFile();
				}
				matchBaseInfos = crawlerDataParseService.getQtJishiBifenData(
						header, null);
			} while (matchBaseInfos == null);

			// 获取当前赛季并装配到model中
			if (!matchBaseInfos.isEmpty()) {
				List<QtMatchBaseModel> qtMatchBaseModels = new ArrayList<>();
				for (QtMatchBaseModel qtMatchBaseModel : matchBaseInfos) {
					String seasonName = crawlerDataParseService
							.gotNowSeasonNameByLeagueId(qtMatchBaseModel
									.getLeagueId());
					qtMatchBaseModel.setSeason(seasonName);
					qtMatchBaseModels.add(qtMatchBaseModel);
				}
				crawlerDataParseService.sendFbAllMatchData(qtMatchBaseModels);
			}
			log.info("即时比分数据抓取完成...");
		} catch (Exception e) {
			log.error("即时比分数据抓取出错:{}", e);
		}
	}

	@Override
	public void crawlerFbEuroOddsNewAndSend(Qt_fb_match_oddsType oddsType) {
		log.info("抓取当前赛季球探竞彩足球比赛赔率数据开始......");

		List<FbMatchBaseInfoPO> jingcaiZqMatch = null;
		try {
			jingcaiZqMatch = crawlerDataParseService
					.gotAllJingcaiZqMatchNotStart();

			if (jingcaiZqMatch != null && !jingcaiZqMatch.isEmpty()) {
				List<QtMatchBaseModel> qtMatchBaseModels = BeanConvertUtil
						.changePOToModel(jingcaiZqMatch, QtMatchBaseModel.class);
				// 创建一个线程池
				ExecutorService executorService = Executors
						.newFixedThreadPool(qtMatchBaseModels.size());
				List<CrawlerZqJingcaiOddsUseThreadAndSendActivemq> tasks = new ArrayList<CrawlerZqJingcaiOddsUseThreadAndSendActivemq>();

				for (QtMatchBaseModel matchBaseModel : qtMatchBaseModels) {
					CrawlerZqJingcaiOddsUseThreadAndSendActivemq lqJingcaiOddsUseThread = new CrawlerZqJingcaiOddsUseThreadAndSendActivemq(
							matchBaseModel,messageSender);
					tasks.add(lqJingcaiOddsUseThread);
				}
				executorService.invokeAll(tasks);
				// pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
				log.info("比赛欧赔数据抓取完成，共抓取{}场比赛的数据", qtMatchBaseModels.size());
				System.exit(0);
			} else {
				log.info("不存在要抓取比赛欧赔数据!");
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("抓取球探足球竞彩比赛欧赔数据出错:{}", e);
		}
	}
}
