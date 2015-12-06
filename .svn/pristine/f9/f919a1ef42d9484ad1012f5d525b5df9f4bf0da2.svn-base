package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.JDBCException;
import org.hibernate.cfg.annotations.Nullability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LqLeagueInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.BasketMatchPlayerStatisticStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.BasketMatchTeamStatisticStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LqMatchInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchOpOddsDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueSeasonPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.service.CrawlerLqDataStoreService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.CrawlerDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BbLeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.LeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.util.BeanConvertUtil;

/**
 * @author 彭保星
 *
 * @since 2014年11月19日上午11:22:30
 */
public class CrawlerLqDataStoreServiceImpl extends
		AbstractCrawlerDataStoreService implements CrawlerLqDataStoreService {
	private static final Integer CUP_TYPE = 2;
	private int source; // 数据来源
	@Autowired
	private CrawlerDataParseService crawlerDataParseService;
	@Autowired
	private LqMatchInfoDataStoreDao lqMatchInfoDataStoreDao;
	@Autowired
	private LqLeagueInfoDataStoreDao lqLeagueInfoDataStoreDao;
	@Autowired
	private MatchOpOddsDataStoreDao crawlerMatchOpOddsDataStoreDao;

	@Autowired
	private BasketMatchTeamStatisticStoreDao basketMatchTeamStatisticStoreDao;
	@Autowired
	private BasketMatchPlayerStatisticStoreDao basketMatchPlayerStatisticStoreDao;

	private Logger log = LoggerFactory
			.getLogger(CrawlerLqDataStoreServiceImpl.class);

	@Override
	public void crawlerLqJishiBifen() {
		log.info("抓取球探篮球即时比分数据开始!");
		try {
			List<BasketBallMatchModel> ballMatchModels = null;
			do {
				if (ballMatchModels == null) {
					makeHeaderAndProxy(CrawlerInterfaceName.QtBasketJishiBifen);
				}
				ballMatchModels = crawlerDataParseService
						.crawlerBaskerBallJishiBifen(proxy, header);
			} while (ballMatchModels == null);

			lqMatchInfoDataStoreDao.storeJishiMatchInfo(ballMatchModels, false);
			log.info("篮球即时数据抓取完成!");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("抓取球探篮球即时比分数据出错：{}", e);
		}
	}

	@Override
	public void crawlerLqLeagueSeasonHistoryMess() {
		log.info("抓取并存储球探篮球联赛数据开始。。。。");
		try {
			List<BbLeagueInfoModel> leagueInfoModels = null;
			// 抓取10次，如果为null，结束抓取
			do {
				// 初始化UA
				makeHeaderAndProxy(CrawlerInterfaceName.QtBasketLeagueInfo);
				leagueInfoModels = crawlerDataParseService.getBasketLeagueInfo(
						proxy, header);
			} while (leagueInfoModels == null);
			lqLeagueInfoDataStoreDao.storeBbLeague(leagueInfoModels);
			log.info("抓取并存储球探篮球联赛数据完成。。。。");
		} catch (Exception e) {
			log.error("抓取并存储球探篮球联赛数据出错:{}", e);
		}
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public CrawlerDataParseService getCrawlerDataParseService() {
		return crawlerDataParseService;
	}

	public void setCrawlerDataParseService(
			CrawlerDataParseService crawlerDataParseService) {
		this.crawlerDataParseService = crawlerDataParseService;
	}

	public LqMatchInfoDataStoreDao getLqMatchInfoDataStoreDao() {
		return lqMatchInfoDataStoreDao;
	}

	public void setLqMatchInfoDataStoreDao(
			LqMatchInfoDataStoreDao lqMatchInfoDataStoreDao) {
		this.lqMatchInfoDataStoreDao = lqMatchInfoDataStoreDao;
	}

	/**
	 * 抓取球探赔率数据
	 */
	@Override
	public void crawlerBbMatchOdds(Qt_fb_match_oddsType oddsType) {
		log.info("抓取球探比赛当前赛季欧赔数据开始......");
		try {
			List<String> corpList = crawlerMatchOpOddsDataStoreDao
					.queryBasketOddsCompany(oddsType);
			for (String itemCorpId : corpList) {// 循环赔率公司
				int everyPagge = 10000;
				int startPos = 0;
				do {// 循环分页10000条赛事
					List<BasketBallMatchPO> allMatchHasFinishBsIds = crawlerMatchOpOddsDataStoreDao
							.queryAllBasketMatchDataHasFinish(startPos,
									oddsType, 1);
					if (allMatchHasFinishBsIds != null) {

						everyPagge = allMatchHasFinishBsIds.size();
						if (!allMatchHasFinishBsIds.isEmpty()) {
							storeOneMatchCompanyOpOdds(allMatchHasFinishBsIds,
									itemCorpId, oddsType);
						}

					} else {
						everyPagge = 0;
						log.info("不存在要抓取比赛欧赔数据!");
					}
					log.info("比赛欧赔数据抓取完成，共抓取{}场比赛的数据",
							allMatchHasFinishBsIds.size());

					startPos += everyPagge;
				} while (everyPagge == 10000);
			}

		} catch (Exception exception) {
			log.error("抓取球探比赛欧赔数据出错:{}", exception);
		}

	}

	/**
	 * 抓取指定公司，比赛欧赔数据并存储
	 * 
	 * @param qtMatchEventStatistics
	 * @param allMatchHasFinishBsIds
	 * @throws Exception
	 * @author guixiangcui
	 */
	private void storeOneMatchCompanyOpOdds(
			List<BasketBallMatchPO> allMatchHasFinishBsIds, String companyId,
			Qt_fb_match_oddsType oddsType) throws Exception {
		List<QtBasketMatchOddsModel> qtMatchCompanyOpOdds = null;
		for (BasketBallMatchPO matchBaseInfoPO : allMatchHasFinishBsIds) {// 循环抓取赔率数据
			do {

				if (qtMatchCompanyOpOdds == null) {
					UserAgentPO userAgentPO = userAgentChooser
							.randomChooseOne();
					makeHeader(userAgentPO);

					CrawlerInterfaceName oddsUrl;
					switch (oddsType) {
					case asia:
						oddsUrl = CrawlerInterfaceName.QtBasketMatchYpOdds;
						break;
					case ou:
						oddsUrl = CrawlerInterfaceName.QtBasketMatchOuOdds;
						break;
					case euro:
						oddsUrl = CrawlerInterfaceName.QtBasketMatchOpOdds;
						break;
					default:
						oddsUrl = CrawlerInterfaceName.QtBasketMatchOpOdds;
						break;
					}

					proxy = makeRandomHealthProxy(oddsUrl, header);
				}
				qtMatchCompanyOpOdds = crawlerDataParseService
						.crawlerBasketMatchOpOddFinishedData(header, proxy,
								matchBaseInfoPO.getBsId(),
								matchBaseInfoPO.getId(), companyId, oddsType);

			} while (qtMatchCompanyOpOdds == null);
			crawlerMatchOpOddsDataStoreDao.storeBasketMatchOpOddsData(
					qtMatchCompanyOpOdds, oddsType, matchBaseInfoPO);
		}
	}

	/**
	 * 抓取篮球赛事球队统计
	 */
	@Override
	public void crawlerBasketMatchTeamStatistic() {
		log.info("抓取球探篮球赛事球队统计开始......");
		int everyPagge = 10000;
		do {// 循环分页10000条赛事
			int startPos = 0;
			try {
				List<BasketBallMatchPO> allMatchHasFinishBsIds = basketMatchTeamStatisticStoreDao
						.queryAllMatchDataHasFinish(startPos);
				if (allMatchHasFinishBsIds != null) {
					everyPagge = allMatchHasFinishBsIds.size();
					if (!allMatchHasFinishBsIds.isEmpty()) {
						List<QtBasketMatchTeamStatisticModel> qtMatchEventStatistics = null;
						for (BasketBallMatchPO matchBaseInfoPO : allMatchHasFinishBsIds) {
							do {
								if (qtMatchEventStatistics == null) {
									makeHeaderAndProxy(CrawlerInterfaceName.QtBasketMatchTeamStatistic);
								}
								qtMatchEventStatistics = crawlerDataParseService
										.crawlerBasketMatchTeamStatisticHasFinishedData(
												header, proxy,
												matchBaseInfoPO.getBsId(),
												matchBaseInfoPO.getId());
							} while (qtMatchEventStatistics == null);
							basketMatchTeamStatisticStoreDao
									.storeMatchTeamStatisticData(qtMatchEventStatistics);
						}
					}
				} else {
					log.info("不存在要抓取球探篮球赛事球队统计数据!");
				}
				log.info("球探篮球赛事球队统计数据抓取完成，共抓取{}场比赛的数据",
						allMatchHasFinishBsIds.size());
			} catch (Exception exception) {
				log.error("抓取球探篮球赛事球队统计数据出错:{}", exception);
			}
			startPos += everyPagge;
		} while (everyPagge == 10000);
	}

	/**
	 * 抓取球员统计
	 */
	@Override
	public void crawlerBasketMatchPlayerStatistic() {
		log.info("抓取球探篮球赛事球员统计开始......");
		int everyPagge = 10000;
		do {// 循环分页10000条赛事
			int startPos = 0;
			try {
				List<BasketBallMatchPO> allMatchHasFinishBsIds = basketMatchPlayerStatisticStoreDao
						.queryAllMatchDataHasFinish(startPos);
				if (allMatchHasFinishBsIds != null) {
					everyPagge = allMatchHasFinishBsIds.size();
					if (!allMatchHasFinishBsIds.isEmpty()) {
						
						List<QtBasketMatchPlayerStatisticModel> qtMatchEventStatistics = null;
						for (BasketBallMatchPO matchBaseInfoPO : allMatchHasFinishBsIds) {
							do {

								if (qtMatchEventStatistics == null) {
									makeHeaderAndProxy(CrawlerInterfaceName.QtBasketMatchPlayerStatistic);
								}
								qtMatchEventStatistics = crawlerDataParseService
										.crawlerBasketMatchPlayerStatisticHasFinishedData(
												header, proxy,
												matchBaseInfoPO.getBsId(),
												matchBaseInfoPO.getId());
							} while (qtMatchEventStatistics == null);
							basketMatchPlayerStatisticStoreDao
									.storeMatchPlayerStatisticData(qtMatchEventStatistics);
						}
					}
				} else {
					log.info("不存在要抓取球探篮球赛事球队统计数据!");
				}
				log.info("球探篮球赛事球队统计数据抓取完成，共抓取{}场比赛的数据",
						allMatchHasFinishBsIds.size());
			} catch (Exception exception) {
				log.error("抓取球探篮球赛事球队统计数据出错:{}", exception);
			}
			startPos += everyPagge;
		} while (everyPagge == 10000);
	}

	@Override
	public void crawlerHistoryMatch() {
		log.info("查询篮球联赛当前赛季赛程开始!");
		try {
			// 查询所有当前赛季的联赛赛季子联赛
			List<BasketBallLeagueSeasonPO> ballLeagueSeasonPOs = lqLeagueInfoDataStoreDao
					.queryAllSubLeagueNotCrawler(1, 0, 1);
			if (ballLeagueSeasonPOs != null && !ballLeagueSeasonPOs.isEmpty()) {
				List<BasketBallLeagueSeasonModel> basketBallLeagueSeasonModels = BeanConvertUtil
						.changePOToModel(ballLeagueSeasonPOs,
								BasketBallLeagueSeasonModel.class);
				List<BasketBallMatchModel> ballMatchModels = null;
				for (BasketBallLeagueSeasonModel basketBallLeagueSeasonModel : basketBallLeagueSeasonModels) {
					do {
						if (ballMatchModels == null) {
							makeHeaderAndProxy(CrawlerInterfaceName.LqSaicheng);
						}
						ballMatchModels = crawlerDataParseService
								.crawlerBasketBallLeagueHistoryMatch(proxy,
										header, basketBallLeagueSeasonModel);
					} while (ballMatchModels == null);
					try {
						lqMatchInfoDataStoreDao.storeLqHistoryMatch(
								basketBallLeagueSeasonModel, ballMatchModels);
					} catch (JDBCException e) {
						// TODO: handle exception
						if (e.getErrorCode() == 1062) { // 唯一键重复，数据问题造成,表示已经抓取过
							e.printStackTrace();
							lqMatchInfoDataStoreDao.updateIsCrawler(
									basketBallLeagueSeasonModel.getSeasonId(),
									1);
							continue;
						} else {
							throw e;
						}
					}
				}
			}
		} catch (Exception exception) {
			log.error("抓取球探篮球联赛历史赛程数据出错：{}", exception);
		}
	}

	@Override
	public void crawlerLqJingcaijishiBifen() {
		log.info("抓取球探篮球竞彩即时比分数据开始!");
		try {
			List<BasketBallMatchModel> ballMatchModels = null;
			do {
				if (ballMatchModels == null) {
					makeHeaderAndProxy(CrawlerInterfaceName.QtBasketJingCaiBifen);
				}
				ballMatchModels = crawlerDataParseService
						.crawlerBaskerBallJingCaiBifen(proxy, header);
			} while (ballMatchModels == null);

			lqMatchInfoDataStoreDao.storeJishiMatchInfo(ballMatchModels, true);
			log.info("篮球竞彩即时数据抓取完成!");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("抓取球探篮球竞彩即时比分数据出错：{}", e);
		}
	}

	@Override
	public void crawlerHistorySubLeague() {
		log.info("抓取球探篮球子联赛数据开始!");
		try {
			List<BasketBallLeagueSeasonPO> basketBallLeagueSeasonPOs = lqLeagueInfoDataStoreDao
					.queryAllLeagueSeasonNotSub(1, 1);
			List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels = BeanConvertUtil
					.changePOToModel(basketBallLeagueSeasonPOs,
							BasketBallLeagueSeasonModel.class);
			List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels2 = null;
			for (BasketBallLeagueSeasonModel ballLeagueSeasonModel : ballLeagueSeasonModels) {
				// 抓取赛季的常规赛、季前赛和季后赛
				do {
					if (ballLeagueSeasonModels2 == null) {
						makeHeaderAndProxy(CrawlerInterfaceName.LqSaicheng);
					}
					ballLeagueSeasonModels2 = crawlerDataParseService
							.parseLqSubLeagueSeason(ballLeagueSeasonModel,
									header, proxy, 1);
				} while (ballLeagueSeasonModels2 == null);
				List<BasketBallLeagueSeasonModel> basketBallLeagueSeasonModels = null;
				for (BasketBallLeagueSeasonModel ballLeagueSeasonModel2 : ballLeagueSeasonModels2) {
					do {
						if (ballLeagueSeasonModels2 == null) {
							makeHeaderAndProxy(CrawlerInterfaceName.LqSaicheng);
						}
						basketBallLeagueSeasonModels = crawlerDataParseService
								.parseLqSubLeagueSeason(ballLeagueSeasonModel2,
										header, proxy, 2);
					} while (basketBallLeagueSeasonModels == null);
					lqLeagueInfoDataStoreDao
							.storeSubleague(basketBallLeagueSeasonModels);

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
	public void crawlerHistorySubCup() {
		log.info("抓取球探篮球杯赛当前赛季分组信息历史数据开始......");
		try {
			List<BasketBallLeagueSeasonPO> leagueSeasonBasePOs = lqLeagueInfoDataStoreDao
					.queryAllLeagueSeasonNotSub(source, CUP_TYPE);
			if (leagueSeasonBasePOs != null && !leagueSeasonBasePOs.isEmpty()) {
				List<BasketBallLeagueSeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(leagueSeasonBasePOs,
								BasketBallLeagueSeasonModel.class);
				List<BasketBallLeagueSeasonModel> seasonModels2 = null;
				for (BasketBallLeagueSeasonModel seasonModel : seasonModels) {

					do {
						if (seasonModels2 == null) {
							makeHeaderAndProxy(CrawlerInterfaceName.BasketCupMatchInfo);
						}
						seasonModels2 = crawlerDataParseService
								.getBasketCupHistoryGroupMess(header, proxy,
										seasonModel);
					} while (seasonModels2 == null); // 如果没有抓取到结果
					// // 存储杯赛分组数据
					lqLeagueInfoDataStoreDao.storeBasketSubLeague(
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
	public void crawlerHistoryCupMatch() {
		log.info("查询篮球杯赛历史赛程和积分开始!");
		try {
			// 查询所有未抓取过的杯赛赛季分组赛
			List<BasketBallLeagueSeasonPO> ballLeagueSeasonPOs = lqLeagueInfoDataStoreDao
					.queryAllSubLeagueNotCrawler(1, 0, 2);
			List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels = BeanConvertUtil
					.changePOToModel(ballLeagueSeasonPOs,
							BasketBallLeagueSeasonModel.class);
			Map<String, Object> basketBallMatchAndLeagueScore = null;
			for (BasketBallLeagueSeasonModel basketBallLeagueSeasonModel : ballLeagueSeasonModels) {
				do {
					if (basketBallMatchAndLeagueScore == null) {
						makeHeaderAndProxy(CrawlerInterfaceName.BasketCupMatchInfo);
					}
					basketBallMatchAndLeagueScore = crawlerDataParseService
							.parseLqCupMatch(proxy, header,
									basketBallLeagueSeasonModel);

				} while (basketBallMatchAndLeagueScore == null);
				lqMatchInfoDataStoreDao.storeCupMatch(
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

	@Override
	public void crawlerHistoryLeagueScore() {
		log.info("查询篮球联赛历史积分开始!");
		try {
			// 查询所有的联赛赛季
			List<BasketBallLeagueSeasonPO> ballLeagueSeasonPOs = lqLeagueInfoDataStoreDao
					.queryAllSubLeagueNotCrawler(1, 1, 1);
			List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels = BeanConvertUtil
					.changePOToModel(ballLeagueSeasonPOs,
							BasketBallLeagueSeasonModel.class);
			List<BasketBallLeagueScoreModel> basketBallMatchAndLeagueScore = null;
			for (BasketBallLeagueSeasonModel basketBallLeagueSeasonModel : ballLeagueSeasonModels) {
				do {
					if (basketBallMatchAndLeagueScore == null) {
						makeHeaderAndProxy(null);
					}
					basketBallMatchAndLeagueScore = crawlerDataParseService
							.parseLqLeagueScore(proxy, header,
									basketBallLeagueSeasonModel);

				} while (basketBallMatchAndLeagueScore == null);
				lqMatchInfoDataStoreDao.storeLeagueScore(
						basketBallLeagueSeasonModel,
						basketBallMatchAndLeagueScore);
			}
			log.info("篮球杯赛和对应积分抓取完成，共抓取{}个联赛赛季的数据",
					ballLeagueSeasonModels.size());
		} catch (Exception e) {
			log.error("查询篮球联赛历史积分数据出错:", e);
		}
	}

	

}
