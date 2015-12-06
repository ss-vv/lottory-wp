package com.unison.lottery.weibo.dataservice.crawler.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueSeasonPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.crawler.download.DownloadService;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.HttpResult;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.Proxy;
import com.unison.lottery.weibo.dataservice.commons.crawler.post.PostDataService;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.DES;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.JsonConvertUtil;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.AwdataStore;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.Zq_FenXi;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi_lq.Lq_FenXi;
import com.unison.lottery.weibo.dataservice.crawler.parse.index.analyseCompany;
import com.unison.lottery.weibo.dataservice.crawler.parse.leagueInfo.CupInfo_Fenxi;
import com.unison.lottery.weibo.dataservice.crawler.parse.leagueInfo.LeagueInfo_Fenxi;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.AnalyseQiutanJIshiIBiFen;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u;
import com.unison.lottery.weibo.dataservice.crawler.parse.repository.AnalyseRepository;
import com.unison.lottery.weibo.dataservice.crawler.parse.repository.f;
import com.unison.lottery.weibo.dataservice.crawler.parse.repository.g;
import com.unison.lottery.weibo.dataservice.crawler.parse.repository.l;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BbLeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.LeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchLineupModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtOddsCompanyModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.mq.CrawlerFbZqCompanyOddsHandle;
import com.xhcms.lottery.commons.utils.MakeQiutanURLKeyUtil;

/**
 * @author 彭保星
 *
 * @since 2014年10月28日下午3:43:48
 */
@Service
public class CrawlerDataParseServiceImpl implements CrawlerDataParseService {
	// 赛季参数
	private static final String SEASON_PARAM = "Season";
	private static final String LEAGUE_ID_PARAM = "sclassid";
	private static final String CUP_ID_PARAM = "ID";
	private static final String SUB_ID_PARAM = "subid";
	private static final String LANGUAGE_PARAM = "lang";
	private static final String CUP_GROUP_ID_PARAM = "GroupID";
	private static final String COMPANY_ID = "CompanyID";
	private static final String SCHE_ID = "ScheID";
	private static final String FLESH = "flesh";
	private static final String KIND = "kind";// 篮球联赛子联赛id
	private static final String League_ID_PARAM = "id";
	private static final String DES_KEY = "DES_KEY";


	private Logger logger = LoggerFactory.getLogger(getClass());

	private CrawlerInterfaceName match = CrawlerInterfaceName.MatchProcessInZLKu;

	private CrawlerInterfaceName cupMatch = CrawlerInterfaceName.CupMatchInfo;

	private CrawlerInterfaceName leagueScore = CrawlerInterfaceName.LeagueScore;

	private CrawlerInterfaceName qtJishiBifen = CrawlerInterfaceName.QtJishiBifen;
	private CrawlerInterfaceName qtMatch = CrawlerInterfaceName.qtMatch;

	private CrawlerInterfaceName qtOddsCompany = CrawlerInterfaceName.QtOddsCompany;
	@Autowired
	private AnalyseRepository analyseRepository;
	@Autowired
	private LeagueInfo_Fenxi leagueInfo_Fenxi;
	@Autowired
	private CupInfo_Fenxi cupInfo_Fenxi;

	@Autowired
	private AnalyseQiutanJIshiIBiFen qiutanJishiBiFenAnalyse;

	@Autowired
	private analyseCompany analyseCompanyUtil;

	@Autowired
	private DownloadService downloadService;
	@Autowired
	private Zq_FenXi zq_FenXi;
	@Autowired
	private Lq_FenXi lq_FenXi;
	
	@Autowired
	private PostDataService postDataService;
	private CrawlerInterfaceName qtBasketJishiBifen = CrawlerInterfaceName.QtBasketJishiBifen;
	private CrawlerInterfaceName basketCupMatchInfo = CrawlerInterfaceName.BasketCupMatchInfo;
	private static String key = SystemPropertiesUtil.getPropsValue(DES_KEY);

	@Override
	public List<LeagueInfoModel> getLeagueInfo(Proxy proxy,
			Map<String, String> header) {
		// TODO Auto-generated method stub
		logger.info("抓取资料库中的联赛及赛季信息开始....");
		List<LeagueInfoModel> leagueInfoModels = null;

		HttpResult result = downloadService.downloadToStringWithExtendParams(
				CrawlerInterfaceName.InfoIndex, null, proxy, header);
		logger.info("抓取资料库中的联赛及赛季信息完成....");
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				l leagueAllInfo = analyseRepository.a(result.getResponseStr());
				logger.debug("联赛信息解析结果:{}", leagueAllInfo);
				leagueInfoModels = makeLeagueInfo(leagueAllInfo,
						leagueInfoModels); // 获取所有联赛信息
			}
		} else {
			logger.debug("抓取到的数据为{}" + result);
		}

		return leagueInfoModels;
	}

	private List<LeagueInfoModel> makeLeagueInfo(l leagueAllInfo,
			List<LeagueInfoModel> leagueInfoModels) {

		if (leagueAllInfo != null) {
			logger.info("开始对解析结果进行二次加工....");
			List<u> leagueList = leagueAllInfo.d();
			List<g> countryList = leagueAllInfo.b;
			List<f> asianList = leagueAllInfo.c();
			if (countryList != null && !countryList.isEmpty()
					&& leagueList != null && !leagueList.isEmpty()
					&& asianList != null && !asianList.isEmpty()) {
				leagueInfoModels = new ArrayList<>();
				Map<String, g> countryMap = new HashMap<String, g>();
				for (g country : countryList) {
					countryMap.put(country.a(), country);
				}
				Map<String, String> asianMap = new HashMap<String, String>();
				for (f asian : asianList) {
					asianMap.put(asian.a(), asian.b());
				}
				// 解析联赛数据，并装配model
				for (u league : leagueList) {
					LeagueInfoModel leagueInfoModel = new LeagueInfoModel();
					leagueInfoModel.setLeagueId(league.a);
					leagueInfoModel.setImportance(safeInteger(league.f));
					leagueInfoModel.setChineseName(league.e);
					leagueInfoModel.setChineseNameAll(league.b);
					leagueInfoModel.setCountry(countryMap.get(league.r).b());
					leagueInfoModel.setCountryId(countryMap.get(league.r).a());
					leagueInfoModel.setAreaId(safeInteger(countryMap.get(
							league.r).c()));
					leagueInfoModel.setType(league.s);
					if (league.t != null && !league.t.isEmpty()) {
						// 赛季数据
						List<SeasonModel> seasonModels = new ArrayList<>();
						for (String seasonName : league.t) {
							SeasonModel seasonModel = new SeasonModel();
							seasonModel.setCreateTime(new Date());
							seasonModel.setProcessStatus(0);
							seasonModel.setSeasonName(seasonName);
							seasonModel.setSource(1);
							seasonModels.add(seasonModel);
						}
						leagueInfoModel.setSeasonModels(seasonModels);
					}
					leagueInfoModel.setProcessStatus(0);
					leagueInfoModel.setSource(1); // 来源：球探
					leagueInfoModel.setCreateTime(new Date());
					leagueInfoModels.add(leagueInfoModel);
				}
			}
			logger.debug("联赛数据二次加工完成{}", leagueInfoModels);

		} else {
			logger.info("联赛数据解析结果为null");
		}
		return leagueInfoModels;

	}

	private Integer safeInteger(String b) {
		// TODO Auto-generated method stub
		if (b != null) {
			try {
				return Integer.valueOf(b);
			} catch (NumberFormatException e) {
				logger.error("数字转换出错:{}", e);
			}
		}
		return null;
	}

	private Integer safeInteger(boolean f) {
		// TODO Auto-generated method stub
		if (f == true) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<QtMatchBaseModel> getHistoryMatchProcess(Proxy proxy,
			Map<String, String> header, SeasonModel seasonModel,
			Integer roundNow) {
		// TODO Auto-generated method stub
		Map<String, String> extendParams = makeExtendParams(seasonModel);
		if (extendParams != null) {
			extendParams.put("round", String.valueOf(roundNow));
		}
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(
						CrawlerInterfaceName.MatchProcessInZLKu, extendParams,
						proxy, header);
		List<QtMatchBaseModel> qtMatchBaseModels = null;
		if (httpResult.isSucc() == true && httpResult.getResponseStr() != null) {
			qtMatchBaseModels = leagueInfo_Fenxi.analyseSaiCheng(
					httpResult.getResponseStr(), seasonModel);
		}
		return qtMatchBaseModels;
	}

	private Map<String, String> makeExtendParams(SeasonModel seasonModel) {
		Map<String, String> params = null;
		if (seasonModel != null) {
			params = new HashMap<>();
			if (StringUtils.isNotBlank(seasonModel.getSeasonName())) {
				params.put(SEASON_PARAM, seasonModel.getSeasonName());
			}
			if (StringUtils.isNotBlank(seasonModel.getLeagueId())) {
				params.put(LEAGUE_ID_PARAM, seasonModel.getLeagueId());
			}
			if (StringUtils.isNotBlank(seasonModel.getSubLeagueId())) {
				params.put(SUB_ID_PARAM, seasonModel.getSubLeagueId());
			}
		}
		return params;
	}

	@Override
	public List<SeasonModel> getHistorySubLeagueMess(Proxy proxy,
			Map<String, String> header, SeasonModel seasonModel) {
		// TODO Auto-generated method stub
		logger.info("抓取资料库中的子联赛信息开始....");
		List<SeasonModel> seasonModels = null;
		// 获取本赛季联赛轮数及子联赛信息
		Map<String, String> extendsParams = makeExtendParams(seasonModel);
		HttpResult result = downloadService.downloadToStringWithExtendParams(
				match, extendsParams, proxy, header);
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				// 解析总轮数以及子联赛
				seasonModels = leagueInfo_Fenxi.analyseRoundCount(
						result.getResponseStr(), seasonModel);
			}
		} else if (result.getStatusCode() != null
				&& 500 == result.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			seasonModels = new ArrayList<>();
		}
		return seasonModels;
	}

	@Override
	public List<SeasonModel> getFbCupHistoryGroupMess(
			Map<String, String> header, Proxy proxy, SeasonModel seasonModel) {
		logger.info("抓取资料库杯赛分组赛信息开始...");
		List<SeasonModel> seasonModels = null;
		// 获取本赛季杯赛分组信息
		Map<String, String> extendParams = makeCupExtendParams(seasonModel);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(cupMatch, extendParams,
						proxy, header);
		if (httpResult != null && httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				seasonModels = cupInfo_Fenxi.analyseCupGroup(
						httpResult.getResponseStr(), seasonModel);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			seasonModels = new ArrayList<>();
		}
		return seasonModels;
	}

	private Map<String, String> makeCupExtendParams(SeasonModel seasonModel) {
		// TODO Auto-generated method stub
		Map<String, String> params = null;
		if (seasonModel != null) {
			params = new HashMap<>();
			if (StringUtils.isNotBlank(seasonModel.getSeasonName())) {
				params.put(SEASON_PARAM, seasonModel.getSeasonName());
			}
			if (StringUtils.isNotBlank(seasonModel.getLeagueId())) {
				params.put(CUP_ID_PARAM, seasonModel.getLeagueId());
			}
			if (StringUtils.isNotBlank(seasonModel.getSubLeagueId())) {
				params.put(CUP_GROUP_ID_PARAM, seasonModel.getSubLeagueId());
			}
		}
		return params;
	}

	private Map<String, String> makeMatchEventExtendParams(String matchId) {
		// TODO Auto-generated method stub
		Map<String, String> params = null;
		if (matchId != null) {
			params = new HashMap<>();
			if (StringUtils.isNotBlank(matchId)) {
				params.put(CUP_ID_PARAM, matchId);
			}
			params.put(LANGUAGE_PARAM, "0");
		}
		return params;
	}

	@Override
	public List<QtMatchBaseModel> crawlerCupHistoryMatchData(
			Map<String, String> header, Proxy proxy, SeasonModel seasonModel) {
		// TODO Auto-generated method stub
		logger.info("抓取杯赛历史赛程记录开始...");
		List<QtMatchBaseModel> qtMatchBaseModels = null;

		Map<String, String> extendParams = makeCupExtendParams(seasonModel);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(cupMatch, extendParams,
						proxy, header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				qtMatchBaseModels = cupInfo_Fenxi.analyseSaiChengSaiGuo(
						httpResult.getResponseStr(), seasonModel);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			qtMatchBaseModels = new ArrayList<>();
		}
		return qtMatchBaseModels;
	}

	@Override
	public List<AwdataStore> crawlerMatchEventHasFinishedData(
			Map<String, String> header, Proxy proxy, String matchId, long Id) {
		logger.info("抓取赛事分析事件信息开始....");
		List<AwdataStore> leagueInfoModels = null;

		HttpResult result = downloadService.downloadToStringWithExtendParams(
				CrawlerInterfaceName.MatchEventData,
				makeMatchEventExtendParams(matchId), proxy, header);
		logger.info("抓取赛事分析事件信息完成....");
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				leagueInfoModels = zq_FenXi.analyseShiJian(
						result.getResponseStr(), Id);
				// logger.info("分析事件信息解析结果:{}", leagueAllInfo);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				leagueInfoModels = new ArrayList<AwdataStore>();
			}
			logger.debug("抓取到的数据为{}" + result);
		}

		return leagueInfoModels;
	}

	@Override
	public QtMatchLineupModel crawlerMatchLineupHasFinishedData(
			Map<String, String> header, Proxy proxy, String matchId, long Id) {
		logger.info("抓取赛事分析阵容信息开始....");
		QtMatchLineupModel dataModel = null;

		HttpResult result = downloadService.downloadToStringWithExtendParams(
				CrawlerInterfaceName.MatchLineupData,
				makeMatchEventExtendParams(matchId), proxy, header);
		logger.info("抓取赛事分析阵容信息完成....");
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				dataModel = zq_FenXi.analyseZhenRong(result.getResponseStr(),
						Id);
				// logger.info("分析事件信息解析结果:{}", leagueAllInfo);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				dataModel = new QtMatchLineupModel();
			}
			logger.info("抓取到的阵容数据为{}" + result);
		}

		return dataModel;
	}

	@Override
	public Map<String, Object> getLeagueScoreAndRule(
			Map<String, String> header, Proxy proxy, SeasonModel seasonModel) {
		Map<String, Object> leagueScoreRuleMap = null;
		Map<String, String> extendParams = makeLeagueScoreExtendParams(seasonModel);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(leagueScore, extendParams,
						proxy, header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				leagueScoreRuleMap = leagueInfo_Fenxi.analyseJiFen(
						httpResult.getResponseStr(), seasonModel);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			leagueScoreRuleMap = new HashMap<>();
		}
		return leagueScoreRuleMap;

	}

	private Map<String, String> makeLeagueScoreExtendParams(
			SeasonModel seasonModel) {
		Map<String, String> params = null;
		if (seasonModel != null) {
			params = new HashMap<>();
			if (StringUtils.isNotBlank(seasonModel.getSeasonName())) {
				params.put(SEASON_PARAM, seasonModel.getSeasonName());
			}
			if (StringUtils.isNotBlank(seasonModel.getLeagueId())) {
				params.put(LEAGUE_ID_PARAM, seasonModel.getLeagueId());
			}
			if (StringUtils.isNotBlank(seasonModel.getSubLeagueId())) {
				params.put(SUB_ID_PARAM, seasonModel.getSubLeagueId());
			}
		}
		return params;
	}

	@Override
	public List<QtMatchBaseModel> getQtJishiBifenData(
			Map<String, String> header, Proxy proxy) {
		List<QtMatchBaseModel> matchBaseModels = null;
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(qtJishiBifen, null, proxy,
						header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				matchBaseModels = qiutanJishiBiFenAnalyse
						.analyseJishiBifen(httpResult.getResponseStr());
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			matchBaseModels = new ArrayList<>();
		}
		return matchBaseModels;
	}

	@Override
	public List<QtOddsCompanyModel> crawlerOddsCompanyData(
			Map<String, String> header, Proxy proxy) {
		List<QtOddsCompanyModel> oddsCompanyModels = null;
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(qtOddsCompany, null, proxy,
						header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				oddsCompanyModels = analyseCompanyUtil
						.analyseOddsCompany(httpResult.getResponseStr());
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			oddsCompanyModels = new ArrayList<>();
		}
		return oddsCompanyModels;
	}

	@Override
	public List<QtMatchOpOddsModel> crawlerMatchOpOddFinishedData(
			Map<String, String> header, Proxy proxy, String bsId, long id,
			String companyId, Qt_fb_match_oddsType itemType) {
		logger.info("抓取赛事欧赔信息开始....");
		List<QtMatchOpOddsModel> matchOpOddsModels = null;

		CrawlerInterfaceName oddsUrl;
		switch (itemType) {
		case asia:
			oddsUrl = CrawlerInterfaceName.QtFbMatchYpOdds;
			break;
		case ou:
			oddsUrl = CrawlerInterfaceName.QtFbMatchOuOdds;
			break;
		case euro:
			oddsUrl = CrawlerInterfaceName.QtFbJingcaiMatchJishiOpOdds;
			break;
		default:
			oddsUrl = CrawlerInterfaceName.QtFbMatchOpOdds;
			break;
		}
		HttpResult result = downloadService.downloadToStringWithExtendParams(
				oddsUrl, makeMatchOddsExtendParams(bsId, companyId), proxy,
				header);
		logger.info("抓取赛事欧赔信息完成....");
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				matchOpOddsModels = zq_FenXi.analyseFootIndex(
						result.getResponseStr(), id, companyId);
				logger.debug("分析事件信息解析结果:{}", matchOpOddsModels);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				matchOpOddsModels = new ArrayList<QtMatchOpOddsModel>();
			}
			logger.debug("抓取到的数据为{}" + result);
		}

		return matchOpOddsModels;
	}

	private Map<String, String> makeMatchOddsExtendParams(String bsId,
			String companyId) {
		Map<String, String> params = null;
		params = new HashMap<>();
		if (StringUtils.isNotBlank(companyId)) {
			params.put(COMPANY_ID, companyId);
		}
		if (StringUtils.isNotBlank(bsId)) {
			params.put(SCHE_ID, bsId);
		}
		return params;
	}

	@Override
	public List<BasketBallMatchModel> crawlerBaskerBallJishiBifen(Proxy proxy,
			Map<String, String> header) {
		// TODO Auto-generated method stub
		List<BasketBallMatchModel> qBallMatchModels = null;
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(qtBasketJishiBifen, null,
						proxy, header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				qBallMatchModels = qiutanJishiBiFenAnalyse
						.analyseBasketBallJishiBifen(httpResult
								.getResponseStr());
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			qBallMatchModels = new ArrayList<>();
		}
		return qBallMatchModels;
	}

	@Override
	public List<QtBasketMatchOddsModel> crawlerBasketMatchOpOddFinishedData(
			Map<String, String> header, Proxy proxy, String bsId, long id,
			String companyId, Qt_fb_match_oddsType itemType) {
		Thread current = Thread.currentThread();
		logger.info(current.getName()+"抓取篮球赛事赔率信息开始....");
		List<QtBasketMatchOddsModel> matchOpOddsModels = null;

		CrawlerInterfaceName oddsUrl;
		switch (itemType) {
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
		HttpResult result = downloadService.downloadToStringWithExtendParams(
				oddsUrl, makeMatchOddsExtendParams(bsId, companyId), proxy,
				header);
		logger.info("抓取篮球赛事赔率信息完成....");
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				matchOpOddsModels = lq_FenXi.analyseBasketIndex(
						result.getResponseStr(), id, companyId);
				logger.debug("分析事件信息解析结果:{}", matchOpOddsModels);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				matchOpOddsModels = new ArrayList<QtBasketMatchOddsModel>();
			}
			logger.debug("抓取到的数据为{}" + result);
		}

		return matchOpOddsModels;
	}

	/**
	 * 抓取球探篮球赛事分析中球队统计数据
	 */
	@Override
	public List<QtBasketMatchTeamStatisticModel> crawlerBasketMatchTeamStatisticHasFinishedData(
			Map<String, String> header, Proxy proxy, String bsId, long id) {
		List<QtBasketMatchTeamStatisticModel> qBallMatchModels = null;
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(
						CrawlerInterfaceName.QtBasketMatchTeamStatistic,
						makeMatchTeamStatisticExtendParams(bsId), proxy, header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				qBallMatchModels = lq_FenXi.analyseGaiKuang(
						httpResult.getResponseStr(), id);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			qBallMatchModels = new ArrayList<>();
		}
		return qBallMatchModels;
	}

	private Map<String, String> makeMatchTeamStatisticExtendParams(String bsId) {
		Map<String, String> params = null;
		params = new HashMap<>();
		if (StringUtils.isNotBlank(bsId)) {
			params.put(CUP_ID_PARAM, bsId);
		}
		return params;
	}

	/**
	 * 抓取球探篮球赛事分析中球员统计数据
	 */
	@Override
	public List<QtBasketMatchPlayerStatisticModel> crawlerBasketMatchPlayerStatisticHasFinishedData(
			Map<String, String> header, Proxy proxy, String bsId, long id) {
		List<QtBasketMatchPlayerStatisticModel> qBallMatchModels = null;
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParamsBySpecielUrl(
						CrawlerInterfaceName.QtBasketMatchPlayerStatistic,
						bsId, makeFleshExtendParams(), proxy, header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				qBallMatchModels = lq_FenXi.analyseTongJi(
						httpResult.getResponseStr(), id);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			qBallMatchModels = new ArrayList<>();
		}
		return qBallMatchModels;
	}

	private Map<String, String> makeFleshExtendParams() {
		Map<String, String> params = null;
		params = new HashMap<>();
		params.put(FLESH, new Date().getTime() + "");
		return params;
	}

	@Override
	public List<BbLeagueInfoModel> getBasketLeagueInfo(Proxy proxy,
			Map<String, String> header) {
		// TODO Auto-generated method stub
		logger.info("抓取资料库中的联赛及赛季信息开始....");
		List<BbLeagueInfoModel> leagueInfoModels = null;
		Map<String, String> extendPrarams = new HashMap<>();
		extendPrarams.put("lang", "0");
		HttpResult result = downloadService.downloadToStringWithExtendParams(
				CrawlerInterfaceName.QtBasketLeagueInfo, extendPrarams, proxy,
				header);
		logger.info("抓取资料库中的联赛及赛季信息完成....");
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				leagueInfoModels = analyseRepository.analiyseBasket(result
						.getResponseStr());
			}
		} else if (result.getStatusCode() != null
				&& result.getStatusCode() == 500) {
			leagueInfoModels = new ArrayList<>();
		} else {
			logger.info("抓取到的数据为{}" + result);
		}

		return leagueInfoModels;
	}

	@Override
	public List<FbLeagueScoreModel> getCupScore(Map<String, String> header,
			Proxy proxy, SeasonModel seasonModel) {
		// TODO Auto-generated method stub
		logger.info("抓取杯赛历史积分记录开始...");
		List<FbLeagueScoreModel> qtMatchBaseModels = null;

		Map<String, String> extendParams = makeCupExtendParams(seasonModel);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(cupMatch, extendParams,
						proxy, header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				qtMatchBaseModels = cupInfo_Fenxi.analyseXiaoZuJiFen(
						httpResult.getResponseStr(), seasonModel);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			qtMatchBaseModels = new ArrayList<>();
		}
		return qtMatchBaseModels;
	}

	@Override
	public List<QtMatchBaseModel> getQtJingcaiJishiBifenData(
			Map<String, String> header, Proxy proxy) {
		List<QtMatchBaseModel> matchBaseModels = null;
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(
						CrawlerInterfaceName.QtFootBallJingCaiJishi, null,
						proxy, header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				matchBaseModels = qiutanJishiBiFenAnalyse
						.analyseJingcaiJishiBifen(httpResult.getResponseStr());
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			matchBaseModels = new ArrayList<>();
		}
		return matchBaseModels;
	}

	@Override
	public List<BasketBallMatchModel> crawlerBaskerBallJingCaiBifen(
			Proxy proxy, Map<String, String> header) {
		List<BasketBallMatchModel> qBallMatchModels = null;
		Map<String, String> extendParams = makeJingcaiExtendParams();
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(
						CrawlerInterfaceName.QtBasketJingCaiBifen,
						extendParams, proxy, header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				qBallMatchModels = qiutanJishiBiFenAnalyse
						.analyseBasketBallJishiBifen(httpResult
								.getResponseStr());
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过解析
			qBallMatchModels = new ArrayList<>();
		}
		return qBallMatchModels;
	}

	private Map<String, String> makeJingcaiExtendParams() {
		Map<String, String> extendParams = new HashMap<>();
		extendParams.put("type", "2");
		extendParams.put(LANGUAGE_PARAM, "0");
		return extendParams;
	}

	@Override
	public List<BasketBallLeagueSeasonModel> parseLqSubLeagueSeason(
			BasketBallLeagueSeasonModel ballLeagueSeasonModel,
			Map<String, String> header, Proxy proxy, int type) {
		// TODO Auto-generated method stub
		List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels = null;
		Map<String, String> extendParams = makeSaichengExtendParams(ballLeagueSeasonModel);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(
						CrawlerInterfaceName.LqSaicheng, extendParams, proxy,
						header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				ballLeagueSeasonModels = leagueInfo_Fenxi
						.analyseBasketBallSubLeague(
								httpResult.getResponseStr(),
								ballLeagueSeasonModel, type);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过解析
			ballLeagueSeasonModels = new ArrayList<>();
		}
		return ballLeagueSeasonModels;
	}

	private Map<String, String> makeSaichengExtendParams(
			BasketBallLeagueSeasonModel ballLeagueSeasonModel) {
		Map<String, String> extendParams = new HashMap<>();
		extendParams.put(LEAGUE_ID_PARAM, ballLeagueSeasonModel.getLeagueId());
		extendParams.put(SEASON_PARAM, ballLeagueSeasonModel.getSeasonName());
		if (StringUtils.isNotBlank(ballLeagueSeasonModel.getKind())) {
			extendParams.put(KIND, ballLeagueSeasonModel.getKind());
		}
		if (StringUtils.isNotBlank(ballLeagueSeasonModel.getSubLeagueId())) {
			if (StringUtils.equals(ballLeagueSeasonModel.getKind(), "2")) { // 季后赛，轮数参数用pid表示
				extendParams.put("pid", ballLeagueSeasonModel.getSubLeagueId());
			} else {
				extendParams.put("m", ballLeagueSeasonModel.getSubLeagueId());
			}
		}
		extendParams.put(LANGUAGE_PARAM, "0");
		return extendParams;
	}

	@Override
	public List<BasketBallMatchModel> crawlerBasketBallLeagueHistoryMatch(
			Proxy proxy, Map<String, String> header,
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel) {
		List<BasketBallMatchModel> basketBallMatchModels = null;
		Map<String, String> extendParams = makeSaichengExtendParams(basketBallLeagueSeasonModel);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(
						CrawlerInterfaceName.LqSaicheng, extendParams, proxy,
						header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				basketBallMatchModels = leagueInfo_Fenxi
						.analyseBasketLeagueHistoryMatch(
								httpResult.getResponseStr(),
								basketBallLeagueSeasonModel);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过解析
			basketBallMatchModels = new ArrayList<>();
		}
		return basketBallMatchModels;
	}

	@Override
	public List<BasketBallLeagueSeasonModel> getBasketCupHistoryGroupMess(
			Map<String, String> header, Proxy proxy,
			BasketBallLeagueSeasonModel seasonModel) {
		logger.info("抓取资料库篮球杯赛分组赛信息开始...");
		List<BasketBallLeagueSeasonModel> seasonModels = null;
		// 获取本赛季杯赛分组信息
		Map<String, String> extendParams = makeBbCupExtendParams(seasonModel);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(basketCupMatchInfo,
						extendParams, proxy, header);
		if (httpResult != null && httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				seasonModels = cupInfo_Fenxi.analyseBasketBallCupGroup(
						httpResult.getResponseStr(), seasonModel);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			seasonModels = new ArrayList<>();
		}
		return seasonModels;
	}

	private Map<String, String> makeBbCupExtendParams(
			BasketBallLeagueSeasonModel seasonModel) {
		Map<String, String> params = null;
		if (seasonModel != null) {
			params = new HashMap<>();
			if (StringUtils.isNotBlank(seasonModel.getSeasonName())) {
				params.put(SEASON_PARAM, seasonModel.getSeasonName());
			}
			if (StringUtils.isNotBlank(seasonModel.getLeagueId())) {
				params.put(CUP_ID_PARAM, seasonModel.getLeagueId());
			}
			if (StringUtils.isNotBlank(seasonModel.getSubLeagueId())) {
				params.put(CUP_GROUP_ID_PARAM, seasonModel.getSubLeagueId());
			}
			params.put(LANGUAGE_PARAM, "0");
		}
		return params;
	}

	@Override
	public Map<String, Object> parseLqCupMatch(Proxy proxy,
			Map<String, String> header,
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel) {
		Map<String, Object> matchModels = null;
		// 获取本赛季杯赛分组信息
		Map<String, String> extendParams = makeBbCupExtendParams(basketBallLeagueSeasonModel);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(basketCupMatchInfo,
						extendParams, proxy, header);
		if (httpResult != null && httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				matchModels = cupInfo_Fenxi.analyseBasketBallHistoryMatch(
						httpResult.getResponseStr(),
						basketBallLeagueSeasonModel);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			matchModels = new HashMap<>();
		}
		return matchModels;
	}

	@Override
	public List<BasketBallLeagueScoreModel> parseLqLeagueScore(Proxy proxy,
			Map<String, String> header,
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel) {
		List<BasketBallLeagueScoreModel> leagueScoreModels = null;
		// 获取本赛季杯赛分组信息
		Map<String, String> extendParams = makeBbLeagueScoreExtendParams(basketBallLeagueSeasonModel);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(
						CrawlerInterfaceName.LqLeagueScore, extendParams,
						proxy, header);
		if (httpResult != null && httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				leagueScoreModels = cupInfo_Fenxi
						.analyseBasketBallLeagueScore(httpResult
								.getResponseStr(),basketBallLeagueSeasonModel.getLeagueId());
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			leagueScoreModels = new ArrayList<>();
		}
		return leagueScoreModels;
	}

	private Map<String, String> makeBbLeagueScoreExtendParams(
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel) {
		Map<String, String> params = null;
		if (basketBallLeagueSeasonModel != null) {
			params = new HashMap<>();
			if (StringUtils.isNotBlank(basketBallLeagueSeasonModel
					.getSeasonName())) {
				params.put(SEASON_PARAM,
						basketBallLeagueSeasonModel.getSeasonName());
			}
			if (StringUtils.isNotBlank(basketBallLeagueSeasonModel
					.getLeagueId())) {
				params.put(League_ID_PARAM,
						basketBallLeagueSeasonModel.getLeagueId());
			}
			params.put(LANGUAGE_PARAM, "0");
		}
		return params;
	}

	@Override
	public void sendFbLeague(List<LeagueInfoModel> leagueInfoModels) throws IOException {
		if(leagueInfoModels!=null&&!leagueInfoModels.isEmpty()){
			String jsonResult = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(leagueInfoModels);
			String encodeData = encodeDataUseDES(jsonResult);
			
			postDataService.sendFbLeagueToServer(encodeData);
		}
	}
	private String encodeDataUseDES(String jsonResult) {
		try {
			return DES.encryptDES(jsonResult, key, "UTF-8");
		} catch (Exception e) {
			logger.error("des加解密出错{}", e);
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public List<FbLeagueSeasonBasePO> queryAllSeasonMessageSubLeague(
			Integer source, int leagueType) throws IOException {
		String encodeString = postDataService.receiveAllSeasonMessageSubLeague(source,leagueType);

		if (StringUtils.isNotBlank(encodeString)) {
			String decodeString = decodeStringWithDES(encodeString);
			return (List<FbLeagueSeasonBasePO>) JsonConvertUtil
					.convertJsonToObject(decodeString, List.class,
							FbLeagueSeasonBasePO.class);
		} else {
			return null;
		}
	}

	private String decodeStringWithDES(String encodeString) {
		try {
			return DES.decryptDES(encodeString, key, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encodeString;
	}

	@Override
	public void sendFbSubLeague(List<SeasonModel> subLeagueOfOneSeaon,
			Integer leagueType) throws IOException {
		if(subLeagueOfOneSeaon!=null&&!subLeagueOfOneSeaon.isEmpty()&&leagueType!=null){
			String jsonData = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(subLeagueOfOneSeaon);
			String encode = encodeDataUseDES(jsonData);
			postDataService.sendFbSubLeagueToServer(leagueType,encode);
		}
	}

	@Override
	public List<FbLeagueSeasonBasePO> getAllSeasonMessageNotCrawler(
			Integer source) throws IOException {
		String encodeData = postDataService.receiveAllSeasonMessageNotCrawler(source);
		if(StringUtils.isNotBlank(encodeData)){
			String decodeString = decodeStringWithDES(encodeData);
			return (List<FbLeagueSeasonBasePO>) JsonConvertUtil
					.convertJsonToObject(decodeString, List.class,
							FbLeagueSeasonBasePO.class);
		}
		return null;
	}

	@Override
	public void sendMatchBaseMessage(List<QtMatchBaseModel> qtMatchBaseModels,
			int round, Integer seasonId) throws IOException {
		// TODO Auto-generated method stub
		if(qtMatchBaseModels==null||qtMatchBaseModels.isEmpty()){
			logger.info("抓取到的数据为空，无须发送...");
		}else {
			String jsonData = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(qtMatchBaseModels);
			String encode = encodeDataUseDES(jsonData);
			postDataService.sendFbMatchBaseMessToServer(round,encode,seasonId);
		}
	}

	@Override
	public List<FbLeagueSeasonBasePO> gotAllCupGroupMessToCraw(Integer source,
			Integer cupType) throws IOException {
		String encodeData = postDataService.receiveAllCupGroupMessToCraw(source,cupType);
		if(StringUtils.isNotBlank(encodeData)){
			String decodeString = decodeStringWithDES(encodeData);
			return (List<FbLeagueSeasonBasePO>) JsonConvertUtil
					.convertJsonToObject(decodeString, List.class,
							FbLeagueSeasonBasePO.class);
		}
		return null;
	}

	@Override
	public String gotNowSeasonNameByLeagueId(String leagueId) throws IOException {
		String encodeData = postDataService.receiveNowSeasonNameByLeagueId(leagueId);
		if(StringUtils.isNotBlank(encodeData)){
			String decodeString = decodeStringWithDES(encodeData);
			return decodeString.replace("\"", "");
		}
		return null;
	}

	@Override
	public void sendCupMatchInfo(List<QtMatchBaseModel> qtMatchBaseModels, Integer seasonId) throws IOException {
		if(qtMatchBaseModels!=null&&!qtMatchBaseModels.isEmpty()){
			String jsonDataString = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(qtMatchBaseModels);
			String decodeData = encodeDataUseDES(jsonDataString);
			postDataService.sendCupMatchToServer(decodeData,seasonId);
		}
	}

	@Override
	public List<FbMatchBaseInfoPO> gotAllMatchDataHasFinish() throws IOException {
		String encodeData = postDataService.receiveAllZqMatchNotHaveLineup();
		if(StringUtils.isNotBlank(encodeData)){
			String jsonObject = decodeStringWithDES(encodeData);
			return (List<FbMatchBaseInfoPO>) JsonConvertUtil.convertJsonToObject(jsonObject, List.class,FbMatchBaseInfoPO.class);
		}
		return null;
	}

	@Override
	public void sendMatchLineupData(QtMatchLineupModel qtMatchLineupModel) throws IOException {
		if(qtMatchLineupModel!=null){
			String jsonDataString = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(qtMatchLineupModel);
			String decodeData = encodeDataUseDES(jsonDataString);
			postDataService.sendMatchLineupDataToServer(decodeData);
		}
	}

	@Override
	public List<FbLeagueSeasonBasePO> gotAllSeasonNotHaveRule(int leagueType,
			Integer source) throws IOException {
		String encodeData = postDataService.receiveAllSeasonNotHaveRule(source,leagueType);
		if(StringUtils.isNotBlank(encodeData)){
			String decodeString = decodeStringWithDES(encodeData);
			return (List<FbLeagueSeasonBasePO>) JsonConvertUtil
					.convertJsonToObject(decodeString, List.class,
							FbLeagueSeasonBasePO.class);
		}
		return null;
	}

	@Override
	public void sendLeagueScoreData(Map<String, Object> scoreRuleMap,
			SeasonModel seasonModel) throws IOException {
		if(scoreRuleMap!=null){
			String scoreRuleJson = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(scoreRuleMap);
			String seasonJson = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(seasonModel);
			scoreRuleJson = encodeDataUseDES(scoreRuleJson);
			seasonJson = encodeDataUseDES(seasonJson);
			
			postDataService.sendLeagueScoreDataToServer(scoreRuleJson,seasonJson);
		}
	}

	@Override
	public List<FbLeagueSeasonBasePO> gotAllCupSeasonNotCrawler(int leagueType, int isSubLeague,
			Integer source) throws IOException {
		String encodeData = postDataService.receiveAllCupSeasonNotCrawler(leagueType,isSubLeague,source);
		if(StringUtils.isNotBlank(encodeData)){
			String decodeString = decodeStringWithDES(encodeData);
			return (List<FbLeagueSeasonBasePO>) JsonConvertUtil
					.convertJsonToObject(decodeString, List.class,
							FbLeagueSeasonBasePO.class);
		}
		return null;
	}

	@Override
	public void sendCupScoreData(List<FbLeagueScoreModel> fbLeagueScoreModels,
			SeasonModel seasonModel) throws IOException {
		if(fbLeagueScoreModels!=null&&!fbLeagueScoreModels.isEmpty()){
			String encodeData = encodeDataUseDES(JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(fbLeagueScoreModels));
			String sesonEncode = encodeDataUseDES(JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(seasonModel));
			
			postDataService.sendCupScoreDataToserver(encodeData,sesonEncode);
		}
	}

	@Override
	public void sendFbAllMatchData(List<QtMatchBaseModel> qtMatchBaseModels) throws IOException {
		if(qtMatchBaseModels==null||qtMatchBaseModels.isEmpty()){
			logger.info("抓取到的数据为空，无须发送...");
		}else {
			String jsonData = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(qtMatchBaseModels);
			String encode = encodeDataUseDES(jsonData);
			postDataService.sendFbMatchBaseMessToServer(encode);
		}
	}

	@Override
	public void sendLqJishiMatchInfo(
			List<BasketBallMatchModel> ballMatchModels, boolean isJingcai) throws IOException {
		if(ballMatchModels==null||ballMatchModels.isEmpty()){
			logger.info("抓取到的数据为空，无须发送...");
		}else {
			String jsonData = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(ballMatchModels);
			String encode = encodeDataUseDES(jsonData);
			postDataService.sendLqJishiMatchBaseMessToServer(encode);
		}
	}

	@Override
	public void sendBbLeague(List<BbLeagueInfoModel> leagueInfoModels) throws IOException {
		if(leagueInfoModels==null||leagueInfoModels.isEmpty()){
			logger.info("抓取到的数据为空，无须发送...");
		}else {
			String jsonData = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(leagueInfoModels);
			String encode = encodeDataUseDES(jsonData);
			postDataService.sendBbLeagueToServer(encode);
		}
	}

	@Override
	public List<BasketBallLeagueSeasonPO> gotAllLqSubLeagueNotCrawler(int source,
			int isSubLeague, int leagueType) throws IOException {
		String encodeData = postDataService.receiveLqSubLeague(leagueType,isSubLeague,source);
		if(StringUtils.isNotBlank(encodeData)){
			String decodeString = decodeStringWithDES(encodeData);
			return (List<BasketBallLeagueSeasonPO>) JsonConvertUtil
					.convertJsonToObject(decodeString, List.class,
							BasketBallLeagueSeasonPO.class);
		}
		return null;
	}

	@Override
	public void sendLqHistoryMatch(
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			List<BasketBallMatchModel> ballMatchModels) throws IOException {
		if(ballMatchModels!=null&&!ballMatchModels.isEmpty()){
			String ballJson = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(ballMatchModels);
			String seasonJson = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(basketBallLeagueSeasonModel);
			postDataService.sendLqHistoryMatchToServer(encodeDataUseDES(ballJson),encodeDataUseDES(seasonJson));
		}
	}

	@Override
	public List<BasketBallLeagueSeasonPO> gotAllLqLeagueSeasonNotSub(
			Integer source, Integer leagueType) throws IOException {
		String encodeData = postDataService.receiveLqLeagueNotSub(leagueType,source);
		if(StringUtils.isNotBlank(encodeData)){
			String decodeString = decodeStringWithDES(encodeData);
			return (List<BasketBallLeagueSeasonPO>) JsonConvertUtil
					.convertJsonToObject(decodeString, List.class,
							BasketBallLeagueSeasonPO.class);
		}
		return null;
	}

	@Override
	public void sendLqCupGroup(List<BasketBallLeagueSeasonModel> seasonModels,
			Integer cupType) throws IOException {
		if(seasonModels!=null&&!seasonModels.isEmpty()){
			String jsonData = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(seasonModels);
			jsonData = encodeDataUseDES(jsonData);
			postDataService.sendLqCupGroupToServer(jsonData,cupType);
		}
	}

	@Override
	public void sendLqSubLeague(
			List<BasketBallLeagueSeasonModel> basketBallLeagueSeasonModels) throws IOException {
		if(basketBallLeagueSeasonModels!=null&&!basketBallLeagueSeasonModels.isEmpty()){
			String jsonData = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(basketBallLeagueSeasonModels);
			jsonData = encodeDataUseDES(jsonData);
			postDataService.sendLqSubLeagueToServer(jsonData);
		}
	}

	@Override
	public void sendCupMatch(
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			Map<String, Object> basketBallMatchAndLeagueScore) throws IOException {
		if(basketBallMatchAndLeagueScore!=null&&!basketBallMatchAndLeagueScore.isEmpty()){
			String encodeLeaugeScoreAndMatch = encodeDataUseDES(JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(basketBallMatchAndLeagueScore));
			String encodeSeason = encodeDataUseDES(JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(basketBallLeagueSeasonModel));
			postDataService.sendCupMatchToServer(encodeLeaugeScoreAndMatch, encodeSeason);
		}
		
	}
	
	@Override
	public List<FbMatchBaseInfoPO> gotAllJingcaiZqMatchNotStart() throws IOException {
		String data = postDataService.receiveJingcaiZqMatchNotStart();
		List<FbMatchBaseInfoPO> matchBaseInfoPOs = null;
		if(StringUtils.isNotBlank(data)){
			matchBaseInfoPOs = (List<FbMatchBaseInfoPO>) JsonConvertUtil.convertJsonToObject(data, List.class,FbMatchBaseInfoPO.class);
		}
		return matchBaseInfoPOs;
	}

	@Override
	public FbMatchOpOddsInfoPO crawlerFbMatchOpOddsOneCompany(
			CrawlerFbZqCompanyOddsHandle zqCompanyOddsHandle,Map<String, String> header) {
		
		HttpResult result = downloadService.downloadToStringWithExtendParams(
				CrawlerInterfaceName.zqMatchOpOddsOneCompany, makeMatchOddsExtendParams1(zqCompanyOddsHandle.getQtBsId(), zqCompanyOddsHandle.getOddsId()), null,
				header);
		logger.info("抓取篮球赛事赔率信息完成....");
		FbMatchOpOddsInfoPO matchOpOddsModels = null;
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				matchOpOddsModels = zq_FenXi.analyseJishiHistoryOdds(
						result.getResponseStr(), zqCompanyOddsHandle.getBsId(), zqCompanyOddsHandle.getCompanyId());
				logger.debug("分析事件信息解析结果:{}", matchOpOddsModels);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				matchOpOddsModels = new FbMatchOpOddsInfoPO();
			}
			logger.debug("抓取到的数据为{}" + result);
		}

		return matchOpOddsModels;
	}

	private Map<String, String> makeMatchOddsExtendParams1(String bsId,
			String oddsId) {
		Map<String, String> params = new HashMap<>();
		params.put(SCHE_ID, bsId);
		params.put("OddsID", oddsId);
		return params;
	}

	@Override
	public void sendFbMatchOpOneCompanyOddsToServer(
			FbMatchOpOddsInfoPO fbMatchOpOddsInfoPOs,
			CrawlerFbZqCompanyOddsHandle zqCompanyOddsHandle) throws IOException {
		if(fbMatchOpOddsInfoPOs!=null&&zqCompanyOddsHandle!=null&&StringUtils.isNotBlank(fbMatchOpOddsInfoPOs.getEuroOdds())){
			String fbMatchData = encodeDataUseDES(JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(fbMatchOpOddsInfoPOs));
			postDataService.sendFbMatchOpOneCompanyOddsToServer(fbMatchData, zqCompanyOddsHandle);
		}
	}

	@Override
	public List<QtMatchOpOddsModel> getQtFbJishizhishuOddsDetails(
			Map<String, String> header, Qt_fb_match_oddsType ou, String corpId,
			FbMatchBaseInfoPO fbMatchBaseInfoPO) {
		CrawlerInterfaceName interfaceName = null;
		switch (ou) {
			case ou:
				interfaceName = CrawlerInterfaceName.QtFbJishizhishuOuOddsDetails;
				break;
			case asia:
				interfaceName = CrawlerInterfaceName.QtFbJishizhishuAisanOddsDetails;
			default:
				break;
		}
		HttpResult result = downloadService.downloadToStringWithExtendParams(
				interfaceName, makeMatchOddsExtendParams(fbMatchBaseInfoPO.getBsId(),corpId), null,
				header);
		logger.info("抓取足球赛事赔率信息完成....");
		List<QtMatchOpOddsModel> matchOpOddsModels = null;
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				matchOpOddsModels = zq_FenXi.analyseOddsDetails(
						result.getResponseStr(), fbMatchBaseInfoPO.getId(), corpId);
				logger.debug("分析事件信息解析结果:{}", matchOpOddsModels);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				matchOpOddsModels = new ArrayList<QtMatchOpOddsModel>();
			}
			logger.debug("抓取到的数据为{}" + result);
		}

		return matchOpOddsModels;
	}
	
	@Override
	public List<QtMatchBaseModel> getQtMatchData(Map<String, String> header,
			Proxy proxy) {
		List<QtMatchBaseModel> matchBaseModels = null;
		Map<String, String> extendParams = new HashMap<String, String>();
		extendParams.put("client", "1");
		extendParams.put("transcode", "105");
		extendParams.put("msg", "{\"typeid\":\"101\",\"kind\":\"2\"}");
		extendParams.put("version","1.6");
		extendParams.put("key", "fdd7059153063182f8be3cd190f9dd30");
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(qtMatch, extendParams , proxy,
						header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				logger.debug(httpResult.getResponseStr());
				matchBaseModels = qiutanJishiBiFenAnalyse
						.analyseQtMatch(httpResult.getResponseStr());
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			matchBaseModels = new ArrayList<>();
		}
		return matchBaseModels;
	}

	@Override
	public List<QtMatchBaseModel> getCaikeMatchData(Map<String, String> header,
			Object object, String jingcaiId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		List<QtMatchBaseModel> matchBaseModels = null;
		Map<String, String> extendParams = makeCaikeBettingExtends(jingcaiId);
		HttpResult httpResult = downloadService
				.downloadToStringWithExtendParams(CrawlerInterfaceName.caikeBetting, extendParams, null,
						header);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				matchBaseModels = qiutanJishiBiFenAnalyse
						.analyseQtMatch(httpResult.getResponseStr());
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			matchBaseModels = new ArrayList<>();
		}
		return matchBaseModels;
	}

	private Map<String, String> makeCaikeBettingExtends(String jingcaiId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String msg = "{\"matchid\":\""+jingcaiId+"\",\"kind\":\"2\",\"typeid\":\"100\",\"datatype\":\"1\"}";
		String baseinfoversion = "1_3.0|G1H5I1J1K1L1M1N1O1P1Q1R1S1";
		String client = "1";
		String transcode = "105";
		String version = "3.0";
		String key = MakeQiutanURLKeyUtil.makeClientKey(msg,baseinfoversion,client,transcode,version);
		Map<String, String> extendsParams = new HashMap<String, String>();
		extendsParams.put("msg", msg);
		extendsParams.put("client", client);
		extendsParams.put("transcode", transcode);
		extendsParams.put("key",key);
		extendsParams.put("version", version);
		extendsParams.put("baseinfoversion", baseinfoversion);
		return extendsParams;
	}
}
