package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.JDBCException;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchOpOddsDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueScoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchEventInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchLineupDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.OddsCompanyStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.SeasonInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.service.CrawlerZqDataStoreService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.AwdataStore;
import com.unison.lottery.weibo.dataservice.crawler.parse.index.i;
import com.unison.lottery.weibo.dataservice.crawler.service.CrawlerDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.LeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchLineupModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtOddsCompanyModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.util.BeanConvertUtil;

/**
 * 抓取球探网数据并存储
 * 
 * @author 彭保星
 *
 * @since 2014年10月28日上午11:57:11
 */
public class CrawlerZqDataStoreServiceImpl extends
		AbstractCrawlerDataStoreService implements CrawlerZqDataStoreService {

	private static final int CRAWLER_NUM = 10; // 约定要抓取的次数
	private static final Integer CUP_TYPE = 2;
	private static final Integer LEAGUE_TYPE = 1;
	@Autowired
	public LeagueInfoDataStoreDao crawlerDataStoreDao;
	@Autowired
	public MatchInfoDataStoreDao crawlerMatchInfoDataStoreDao;
	@Autowired
	public SeasonInfoDataStoreDao crawlerSeasonInfoDataStoreDao;
	@Autowired
	public MatchEventInfoDataStoreDao crawlerMatchEventInfoDataStoreDao;
	@Autowired
	public MatchLineupDataStoreDao crawlerMatchLineupDataStoreDao;
	@Autowired
	public LeagueScoreDao crawlerLeagueScoreDao;
	@Autowired
	public OddsCompanyStoreDao crawlerOddsCompanyStoreDao;

	@Autowired
	private MatchOpOddsDataStoreDao crawlerMatchOpOddsDataStoreDao;

	public Logger log = LoggerFactory.getLogger(getClass());
	public Integer source;
	@Autowired
	private CrawlerDataParseService crawlerDataParseService;
	@Autowired
	private OddsCompanyStoreDao oddsCompanyStoreDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unison.lottery.weibo.data.crawler.service.store.service.
	 * CrawlerDataStoreService#crawlerFbLeagueDataAndStore()
	 */
	@Transactional
	@Override
	public void crawlerFbLeagueDataAndStore() {
		log.info("抓取并存储球探联赛数据开始。。。。");
		try {
			List<LeagueInfoModel> leagueInfoModels = null;
			int count = 0;
			// 抓取10次，如果为null，结束抓取
			do {
				// 初始化UA
				makeHeaderAndProxy(CrawlerInterfaceName.InfoIndex);
				leagueInfoModels = crawlerDataParseService.getLeagueInfo(proxy,
						header);
				count++;
			} while (leagueInfoModels == null && count <= CRAWLER_NUM);
			crawlerDataStoreDao.storeFbLeague(leagueInfoModels);
			log.info("抓取并存储球探联赛数据完成。。。。");
		} catch (Exception e) {
			log.error("抓取并存储球探联赛数据出错:{}", e);
		}
	}

	@Override
	public void crawlerFbMatchDataAndStore() {
		// TODO Auto-generated method stub
		log.info("抓取并存储球探资料库赛程数据开始。。。。");
		try {
			// 查询球探所有赛季信息
			List<FbLeagueSeasonBasePO> fbLeagueSeasonBasePOs = crawlerDataStoreDao
					.queryAllSeasonMessageNotCrawler(source); // 查询所有未抓取完的联赛赛季历史数据
			List<SeasonModel> seasonModels = BeanConvertUtil.changePOToModel(
					fbLeagueSeasonBasePOs, SeasonModel.class);
			if (seasonModels != null && !seasonModels.isEmpty()) {
				int countSeasonNum = 0;
				for (SeasonModel seasonModel : seasonModels) {
					crawlerAndStoreOneSeasonMatch(seasonModel);
					countSeasonNum++;
				}
				log.info("抓取并存储球探资料库赛程数据完成,本次共抓取{}个赛季的数据...", countSeasonNum);

			}

			log.info("抓取并存储球探资料库赛程数据完成。。。。");
		} catch (Exception e) {
			log.error("抓取并存储球探资料库赛程数据出错:", e);
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
						makeHeaderAndProxy(CrawlerInterfaceName.MatchProcessInZLKu);
					}
					// 如果是子联赛，轮数从0开始抓取
					if (seasonModel.getIsSubLeague() == 0) {
						round = i - 1;
					} else {
						round = i;
					}
					qtMatchBaseModels = crawlerDataParseService
							.getHistoryMatchProcess(proxy, header, seasonModel,
									round);
				} while (qtMatchBaseModels == null); // 如果没有抓取到结果

				try {
					crawlerDataStoreDao.storeMatchBaseMessage(
							qtMatchBaseModels, i);
				} catch (JDBCException e) {
					// TODO: handle exception
					if (e.getErrorCode() == 1062) { // 唯一键重复，数据问题造成,表示本轮已经抓取过
						e.printStackTrace();
						crawlerDataStoreDao.updateCrawlerCount(
								seasonModel.getSeasonId(), i);
						continue;
					} else {
						throw e;
					}
				}
				log.info("抓取来源为球探资料库中的联赛id为{}子联赛id为{}的{}赛季第{}轮赛程信息完成....",
						seasonModel.getLeagueId(),
						seasonModel.getSubLeagueId(),
						seasonModel.getSeasonName(), i);
			}

		}
	}

	@Override
	public void crawlerFbSubLeagueSeasonMess() {
		log.info("球探网子联赛数据抓取开始...");
		try {
			// 查询球探所有赛季信息
			List<FbLeagueSeasonBasePO> fbLeagueSeasonBasePOs = crawlerSeasonInfoDataStoreDao
					.queryAllSeasonMessageSubLeague(source, 1); // 查询所有不属于子联赛的联赛赛季历史数据
			List<SeasonModel> seasonModels = BeanConvertUtil.changePOToModel(
					fbLeagueSeasonBasePOs, SeasonModel.class);
			if (seasonModels != null && !seasonModels.isEmpty()) {
				crawlerAndStoreSeasonSubMesaage(seasonModels);
			}
		} catch (Exception e) {
			log.error("抓取并存储球探子联赛的联赛赛季历史数据出错:{}", e);
		}
	}

	/**
	 * 抓取并存储球探赛季子联赛数据，完善联赛数据信息，为抓取比赛数据做准备
	 * 
	 * @param seasonModels
	 */
	@Override
	public void crawlerAndStoreSeasonSubMesaage(List<SeasonModel> seasonModels) {
		// TODO Auto-generated method stub
		int count = 0;
		log.info("抓取球探赛季的子联赛数据开始...");
		do {
			// 初始化UA
			UserAgentPO userAgentPO = userAgentChooser.randomChooseOne();
			makeHeader(userAgentPO);
			proxy = makeRandomHealthProxy(
					CrawlerInterfaceName.MatchProcessInZLKu, header);

			for (int i = count; i < seasonModels.size(); i++) {
				List<SeasonModel> subLeagueOfOneSeaon = crawlerDataParseService
						.getHistorySubLeagueMess(proxy, header,
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

	@Override
	public void storeService(List<SeasonModel> subLeagueOfOneSeaon) {
		crawlerSeasonInfoDataStoreDao.storeFbSubLeague(subLeagueOfOneSeaon,
				LEAGUE_TYPE);
	}

	@Override
	public void crawlerFbCupGroupDataAndStore() {
		// TODO Auto-generated method stub
		log.info("抓取球探杯赛分组信息历史数据开始......");
		try {
			List<FbLeagueSeasonBasePO> leagueSeasonBasePOs = crawlerSeasonInfoDataStoreDao
					.queryAllSeasonMessageSubLeague(source, CUP_TYPE);
			if (leagueSeasonBasePOs != null && !leagueSeasonBasePOs.isEmpty()) {
				List<SeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(leagueSeasonBasePOs, SeasonModel.class);
				List<SeasonModel> seasonModels2 = null;
				for (SeasonModel seasonModel : seasonModels) {
					int i = 0;
					do {
						if (seasonModels2 == null) {
							makeHeaderAndProxy(CrawlerInterfaceName.CupMatchInfo);
						}
						seasonModels2 = crawlerDataParseService
								.getFbCupHistoryGroupMess(header, proxy,
										seasonModel);
						i++;
					} while (seasonModels2 == null&&i<=CRAWLER_NUM); // 如果没有抓取到结果
					// 存储杯赛分组数据
					crawlerSeasonInfoDataStoreDao.storeFbSubLeague(
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
	public void crawlerFbCupMatchDataAndStore() {
		log.info("抓取球探杯赛历史数据开始......");
		try {
			List<FbLeagueSeasonBasePO> leagueSeasonBasePOs = crawlerSeasonInfoDataStoreDao
					.queryAllCupGroupMessToCraw(source, CUP_TYPE);
			if (leagueSeasonBasePOs != null && !leagueSeasonBasePOs.isEmpty()) {
				List<SeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(leagueSeasonBasePOs, SeasonModel.class);
				List<QtMatchBaseModel> qtMatchBaseModels = null;

				for (SeasonModel seasonModel : seasonModels) {
					// 查询联赛的当前赛季
					String seasonName = crawlerSeasonInfoDataStoreDao
							.queryNowSeasonNameByLeagueId(seasonModel
									.getLeagueId());
					// 不抓取不是当前赛季的数据
					if (StringUtils.equals(seasonName,
							seasonModel.getSeasonName())) {
						do {
							if (qtMatchBaseModels == null) {
								makeHeaderAndProxy(CrawlerInterfaceName.CupMatchInfo);
							}
							qtMatchBaseModels = crawlerDataParseService
									.crawlerCupHistoryMatchData(header, proxy,
											seasonModel);

						} while (qtMatchBaseModels == null);
						try {
							crawlerMatchInfoDataStoreDao
									.storeCupMatchInfo(qtMatchBaseModels);
						} catch (JDBCException e) {
							// TODO: handle exception
							if (e.getErrorCode() == 1062) { // 唯一键重复，数据问题造成,表示已经抓取过
								e.printStackTrace();
								crawlerDataStoreDao.updateCrawlerCount(
										seasonModel.getSeasonId(), 1);
								continue;
							} else {
								throw e;
							}
						}
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

	/**
	 * 抓取赛事分析中事件数据
	 */
	@Override
	public void crawlerFbMatchEventDataAndStore() {
		log.info("抓取球探对阵事件数据开始......");
		int everyPagge = 10000;
		try {
			int startPos = 0;
			do {
				List<FbMatchBaseInfoPO> allMatchHasFinishBsIds = crawlerMatchEventInfoDataStoreDao
						.queryAllMatchDataHasFinish(startPos);

				if (allMatchHasFinishBsIds != null
						&& !allMatchHasFinishBsIds.isEmpty()) {
					everyPagge = allMatchHasFinishBsIds.size();

					storeOneMatchEventsAndStatistics(allMatchHasFinishBsIds);

				} else {
					log.info("不存在要抓取赛事事件历史数据!");
				}
				log.info("赛事事件数据抓取完成，共抓取{}场比赛的数据",
						allMatchHasFinishBsIds.size());
				startPos += everyPagge;
			} while (everyPagge == 10000);

		} catch (Exception exception) {
			log.error("抓取球探赛事事件数据出错:{}", exception);
		}
	}

	/**
	 * @param qtMatchEventStatistics
	 * @param matchBaseInfoPO
	 * @throws Exception
	 */
	private void storeOneMatchEventsAndStatistics(
			List<FbMatchBaseInfoPO> allMatchHasFinishBsIds) throws Exception {
		List qtMatchEventStatistics = null;
		int i;
		for (FbMatchBaseInfoPO matchBaseInfoPO : allMatchHasFinishBsIds) {
			i=0;
			do {

				if (qtMatchEventStatistics == null) {
					makeHeaderAndProxy(CrawlerInterfaceName.MatchEventData);
				}
				qtMatchEventStatistics = crawlerDataParseService
						.crawlerMatchEventHasFinishedData(header, proxy,
								matchBaseInfoPO.getBsId(),
								matchBaseInfoPO.getId());
				i++;
			} while (qtMatchEventStatistics == null&&i<CRAWLER_NUM);

			AwdataStore matchEvents = qtMatchEventStatistics.size() > 0 ? (AwdataStore) qtMatchEventStatistics
					.get(0) : null;

			if (matchEvents != null) {
				if (matchEvents.a.equals("比赛事件")) {
					crawlerMatchEventInfoDataStoreDao
							.storeMatchEventData(matchEvents.b);
				} else if (matchEvents.a.equals("赛事技术统计")) {
					crawlerMatchEventInfoDataStoreDao
							.storeMatchStatisticData(matchEvents.b);
				}
			}

			AwdataStore matchStatistics = qtMatchEventStatistics.size() > 1 ? (AwdataStore) qtMatchEventStatistics
					.get(1) : null;

			if (matchStatistics != null && matchStatistics.a.equals("赛事技术统计")) {
				crawlerMatchEventInfoDataStoreDao
						.storeMatchStatisticData(matchStatistics.b);
			}
		}
	}

	/**
	 * 抓取赛事分析中阵容信息
	 */
	@Override
	public void crawlerFbMatchLineupDataAndStore() {
		log.info("抓取球探对阵阵容数据开始......");
//		int everyPagge = 10000;
		try {
//			int startPos = 0;
//			do {
				List<FbMatchBaseInfoPO> allMatchHasFinishBsIds = crawlerMatchLineupDataStoreDao
						.queryAllMatchDataHasFinish(0);
				if (allMatchHasFinishBsIds != null
						&& !allMatchHasFinishBsIds.isEmpty()) {
//					everyPagge = allMatchHasFinishBsIds.size();
					QtMatchLineupModel qtMatchLineupModel = null;
					for (FbMatchBaseInfoPO matchBaseInfoPO : allMatchHasFinishBsIds) {
						do {
							if (qtMatchLineupModel == null) {
								makeHeaderAndProxy(CrawlerInterfaceName.MatchLineupData);
							}
							qtMatchLineupModel = crawlerDataParseService
									.crawlerMatchLineupHasFinishedData(header,
											proxy, matchBaseInfoPO.getBsId(),
											matchBaseInfoPO.getId());

						} while (qtMatchLineupModel == null);

						if (qtMatchLineupModel.getBsId() != null) {

							crawlerMatchLineupDataStoreDao
									.storeMatchLineupData(qtMatchLineupModel);
						}

						// log.info("抓取来源为球探资料库中的杯赛id为{}事件信息完成....",bs);
					}
				} else {
					log.info("不存在要抓取赛事事件历史数据!");
				}
				log.info("赛事事件数据抓取完成，共抓取{}场比赛的数据",
						allMatchHasFinishBsIds.size());
//				startPos += everyPagge;
//			} while (everyPagge == 10000);
		} catch (Exception exception) {
			log.error("抓取球探赛事事件数据出错:{}", new Object[] { exception });
		}
	}

	@Override
	public void crawlerFbLeagueScoreDataAndStore() {
		log.info("抓取球探联赛积分开始...");
		try {
			List<FbLeagueSeasonBasePO> seasonBasePOs = crawlerDataStoreDao
					.queryAllSeasonNotHaveRule(1, source);
			if (seasonBasePOs != null && !seasonBasePOs.isEmpty()) {
				Map<String, Object> scoreRuleMap = null;
				List<SeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(seasonBasePOs, SeasonModel.class);
				for (SeasonModel seasonModel : seasonModels) {
					do {
						if (scoreRuleMap == null) {
							makeHeaderAndProxy(CrawlerInterfaceName.LeagueScore);
						}

						scoreRuleMap = crawlerDataParseService
								.getLeagueScoreAndRule(header, proxy,
										seasonModel);
					} while (scoreRuleMap == null);
					if("0".equals(scoreRuleMap.get("0"))){
						crawlerLeagueScoreDao
								.storeLeagueScoreData(scoreRuleMap,seasonModel);

						log.info("抓取联赛id为{}的{}赛季的联赛积分完成...",
								seasonModel.getLeagueId(),
								seasonModel.getSeasonName());
					}else{
						
					}
				}
				log.info("联赛积分数据抓取完成，一共抓取{}个赛季的数据!", seasonModels.size());
			}
		} catch (Exception exception) {
			log.error("抓取球探联赛积分出错:{}", exception);
		}
	}

	@Override
	public void crawlerQtJishiBifen() {
		log.info("抓取球探足球即时比分数据...");

		try {

			List<QtMatchBaseModel> matchBaseInfos = null;
			do {
				if (matchBaseInfos == null) {
					makeHeaderAndProxy(CrawlerInterfaceName.QtJishiBifen);
				}
				matchBaseInfos = crawlerDataParseService.getQtJishiBifenData(
						header, null);
			} while (matchBaseInfos == null);

			// 获取当前赛季并装配到model中
			if (!matchBaseInfos.isEmpty()) {
				List<QtMatchBaseModel> qtMatchBaseModels = new ArrayList<>();
				for (QtMatchBaseModel qtMatchBaseModel : matchBaseInfos) {
					String seasonName = crawlerMatchInfoDataStoreDao
							.getNowSeasonByLeagueId(qtMatchBaseModel
									.getLeagueId());
					qtMatchBaseModel.setSeason(seasonName);
					qtMatchBaseModels.add(qtMatchBaseModel);
				}
				crawlerMatchInfoDataStoreDao.updateMatchData(qtMatchBaseModels);
			}
			log.info("即时比分数据抓取完成...");
		} catch (Exception e) {
			log.error("即时比分数据抓取出错:{}", e);
		}
	}
	@Override
	public void crawlerQtMatch() {
		log.info("抓取球探赛程数据...");
		try {
			List<QtMatchBaseModel> matchBaseInfos = null;
			do {
				if (matchBaseInfos == null) {
					makeHeaderAndProxy(CrawlerInterfaceName.qtMatch);
				}
				matchBaseInfos = crawlerDataParseService.getQtMatchData(header, null);
			} while (matchBaseInfos == null);
			//获取leangueId装配到model
			if (!matchBaseInfos.isEmpty()) {
				
				storeCaiKeJingcaiMatch(matchBaseInfos);
				String jingcaiId = matchBaseInfos.get(matchBaseInfos.size()-1).getJingcaiId();
				//抓取彩客下一页数据
				if(jingcaiId!=null){
					int i=0;
					do{
						if(matchBaseInfos==null){
							makeHeaderAndProxy(CrawlerInterfaceName.qtMatch);
						}
						matchBaseInfos = crawlerDataParseService.getCaikeMatchData(header, null,jingcaiId);
						if(matchBaseInfos!=null&&!matchBaseInfos.isEmpty()){
							storeCaiKeJingcaiMatch(matchBaseInfos);
							jingcaiId = matchBaseInfos.get(matchBaseInfos.size()-1).getJingcaiId();
							matchBaseInfos = null;
							i=0;
						}
					}while(matchBaseInfos==null && ++i<10);
					
				}
			}
			log.info("即时比分数据抓取完成...");
		} catch (Exception e) {
			log.error("即时比分数据抓取出错:{}", e);
		}
	}

	private void storeCaiKeJingcaiMatch(List<QtMatchBaseModel> matchBaseInfos) {
		List<QtMatchBaseModel> qtMatchBaseModels = new ArrayList<>();
		for (QtMatchBaseModel qtMatchBaseModel : matchBaseInfos) {
			qtMatchBaseModel.setLeagueId(crawlerMatchInfoDataStoreDao.queryLeagueIdByChineseName(qtMatchBaseModel.getLeagueId()));
			if(qtMatchBaseModel.getLeagueId()!=null){
				qtMatchBaseModels.add(qtMatchBaseModel);
			}
		}
		crawlerMatchInfoDataStoreDao.updateMatchJingcaiId(qtMatchBaseModels);
	}

	/**
	 * 抓取赔率指数公司
	 */
	@Override
	public void crawlerFbOddsCompany() {
		log.info("抓取球探指数公司开始...");
		try {
			// crawlerOddsCompanyStoreDao.crawlerOddsCompany();
			List<QtOddsCompanyModel> oddsCompanyModels = null;

			do {
				oddsCompanyModels = crawlerDataParseService
						.crawlerOddsCompanyData(header, proxy);
			} while (oddsCompanyModels == null);
			crawlerOddsCompanyStoreDao.storeOdddCompany(oddsCompanyModels);

		} catch (Exception exception) {
			log.error("抓取球探指数公司出错:{}", exception);
		}
	}

	/**
	 * 抓取比赛欧赔
	 */
	public void crawlerFbMatchOdds(Qt_fb_match_oddsType oddsType) {
		log.info("抓取当前赛季球探竞彩足球比赛赔率数据开始......");
		try {
			List<String> corpList = crawlerMatchOpOddsDataStoreDao
					.queryOddsCompany(oddsType);
			for (String itemCorpId : corpList) {// 循环赔率公司
				int everyPagge = 10000;
				int startPos = 0;
				do {// 循环分页10000条赛事
					List<FbMatchBaseInfoPO> allMatchHasFinishBsIds = crawlerMatchOpOddsDataStoreDao
							.queryAllMatchDataHasFinish(startPos, oddsType);
					if (allMatchHasFinishBsIds != null){
						everyPagge = allMatchHasFinishBsIds.size();
						if (!allMatchHasFinishBsIds.isEmpty()) {

							storeOneMatchCompanyOpOdds(allMatchHasFinishBsIds,
									itemCorpId, oddsType);
						}
					} else {
						log.info("不存在要抓取比赛欧赔数据!");
					}
					log.info("比赛欧赔数据抓取完成，共抓取{}场比赛的数据",
							allMatchHasFinishBsIds.size());

					startPos += everyPagge;
				} while (everyPagge == 10000);
			}

		} catch (Exception exception) {
			log.error("抓取球探比赛赔率数据出错:{}", exception);
		}
	}

	/**
	 * 抓取指定公司，比赛欧赔数据并存储
	 * 
	 * @param qtMatchEventStatistics
	 * @param matchBaseInfoPO
	 * @throws Exception
	 * @author guixiangcui
	 */
	private void storeOneMatchCompanyOpOdds(
			List<FbMatchBaseInfoPO> allMatchHasFinishBsIds, String companyId,
			Qt_fb_match_oddsType oddsType) throws Exception {
		List<QtMatchOpOddsModel> qtMatchCompanyOpOdds = null;
		for (FbMatchBaseInfoPO matchBaseInfoPO : allMatchHasFinishBsIds) {// 循环抓取赔率数据
			do {

				if (qtMatchCompanyOpOdds == null) {
					UserAgentPO userAgentPO = userAgentChooser
							.randomChooseOne();
					makeHeader(userAgentPO);

					CrawlerInterfaceName oddsUrl;
					switch (oddsType) {
					case asia:
						oddsUrl = CrawlerInterfaceName.QtFbMatchYpOdds;
						break;
					case ou:
						oddsUrl = CrawlerInterfaceName.QtFbMatchOuOdds;
						break;
					case euro:
						oddsUrl = CrawlerInterfaceName.QtFbMatchOpOdds;
						break;
					default:
						oddsUrl = CrawlerInterfaceName.QtFbMatchOpOdds;
						break;
					}

					proxy = makeRandomHealthProxy(oddsUrl, header);
				}
				qtMatchCompanyOpOdds = crawlerDataParseService
						.crawlerMatchOpOddFinishedData(header, proxy,
								matchBaseInfoPO.getBsId(),
								matchBaseInfoPO.getId(), companyId, oddsType);

			} while (qtMatchCompanyOpOdds == null);
			crawlerMatchOpOddsDataStoreDao.storeMatchOpOddsData(
					qtMatchCompanyOpOdds, oddsType);
		}
	}

	@Override
	public void crawlerCupScoreDataAndStore() {
		// TODO Auto-generated method stub
		log.info("抓取球探杯赛积分历史数据开始...");
		try {
			// 获取没有抓取过积分的杯赛分组赛数据
			List<FbLeagueSeasonBasePO> seasonBasePOs = crawlerDataStoreDao
					.queryAllCupSeasonNotCrawler(2, 0, source);
			if (seasonBasePOs != null && !seasonBasePOs.isEmpty()) {
				List<FbLeagueScoreModel> fbLeagueScoreModels = null;
				List<SeasonModel> seasonModels = BeanConvertUtil
						.changePOToModel(seasonBasePOs, SeasonModel.class);
				for (SeasonModel seasonModel : seasonModels) {
					do {
						if (fbLeagueScoreModels == null) {
							makeHeaderAndProxy(CrawlerInterfaceName.CupMatchInfo);
						}

						fbLeagueScoreModels = crawlerDataParseService
								.getCupScore(header, proxy, seasonModel);
					} while (fbLeagueScoreModels == null);
					if (!fbLeagueScoreModels.isEmpty()) {
						crawlerLeagueScoreDao.storeCupScoreData(
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
	public void crawlerFbJingcaiJishi() {
		log.info("抓取球探足球竞彩即时比分数据...");

		try {

			List<QtMatchBaseModel> matchBaseInfos = null;
			do {
				if (matchBaseInfos == null) {
					makeHeaderAndProxy(CrawlerInterfaceName.QtFootBallJingCaiJishi);
				}
				matchBaseInfos = crawlerDataParseService.getQtJingcaiJishiBifenData(
						header, proxy);
			} while (matchBaseInfos == null);

			// 获取当前赛季并装配到model中
			if (!matchBaseInfos.isEmpty()) {
				List<QtMatchBaseModel> qtMatchBaseModels = new ArrayList<>();
				for (QtMatchBaseModel qtMatchBaseModel : matchBaseInfos) {
					String seasonName = crawlerMatchInfoDataStoreDao
							.getNowSeasonByLeagueId(qtMatchBaseModel
									.getLeagueId());
					qtMatchBaseModel.setSeason(seasonName);
					qtMatchBaseModels.add(qtMatchBaseModel);
				}
				crawlerMatchInfoDataStoreDao.updateJingCaiMatchData(qtMatchBaseModels);
			}
			log.info("竞彩即时比分数据抓取完成...");
		} catch (Exception e) {
			log.error("竞彩即时比分数据抓取出错:{}", e);
		}
	}

	@Override
	public void crawlerFbMatchOddsDetail(Qt_fb_match_oddsType ou) {
		log.info("抓取球探足球即时指数大小赔率详情数据...");

		try {
			List<FbMatchBaseInfoPO> fbMatchBaseInfoPOs = crawlerDataStoreDao.queryFbJingCaiMatchNotStart();
			List<QtMatchOpOddsModel> matchOpOddsModels = null;
			if(fbMatchBaseInfoPOs!=null&&!fbMatchBaseInfoPOs.isEmpty()){
				String[] corpIds = new String[]{"1","3","4","12","14","17","22","23","24","31","35","8"};
				for(FbMatchBaseInfoPO fbMatchBaseInfoPO:fbMatchBaseInfoPOs){
					
					for(String corpId:corpIds){
						int i=0;
						do {
							if (matchOpOddsModels == null) {
								makeHeaderAndProxy(CrawlerInterfaceName.QtFbJishizhishuOuOddsDetails);
							}
							matchOpOddsModels = crawlerDataParseService.getQtFbJishizhishuOddsDetails(
									header, ou, corpId, fbMatchBaseInfoPO);
							i++;
						} while (matchOpOddsModels == null&&i<=CRAWLER_NUM);
						
						// 获取当前赛季并装配到model中
						if (!matchOpOddsModels.isEmpty()) {
							oddsCompanyStoreDao.storeAsianOddDatas(matchOpOddsModels,ou);
						}
						Thread.currentThread().sleep(200);
					}
				}
				log.info("足球即时指数大小赔率详情抓取完成...");
			}
		} catch (Exception e) {
			log.error("足球即时指数大小赔率详情抓取出错:{}", e);
		}
	}
	public LeagueInfoDataStoreDao getCrawlerDataStoreDao() {
		return crawlerDataStoreDao;
	}

	public void setCrawlerDataStoreDao(
			LeagueInfoDataStoreDao crawlerDataStoreDao) {
		this.crawlerDataStoreDao = crawlerDataStoreDao;
	}

	public SeasonInfoDataStoreDao getCrawlerSeasonInfoDataStoreDao() {
		return crawlerSeasonInfoDataStoreDao;
	}

	public void setCrawlerSeasonInfoDataStoreDao(
			SeasonInfoDataStoreDao crawlerSeasonInfoDataStoreDao) {
		this.crawlerSeasonInfoDataStoreDao = crawlerSeasonInfoDataStoreDao;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public CrawlerDataParseService getCrawlerDataParseService() {
		return crawlerDataParseService;
	}

	public void setCrawlerDataParseService(
			CrawlerDataParseService crawlerDataParseService) {
		this.crawlerDataParseService = crawlerDataParseService;
	}

}
