package com.unison.lottery.weibo.dataservice.crawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.crawler.download.DownloadService;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.HttpResult;
import com.unison.lottery.weibo.dataservice.commons.crawler.post.PostDataService;
import com.unison.lottery.weibo.dataservice.commons.crawler.post.PostDataServiceImpl;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.DES;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.JsonConvertUtil;
import com.unison.lottery.weibo.dataservice.commons.crawler.download.DownloadServiceImpl;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.AwdataStore;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.Zq_FenXi;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi_lq.Lq_FenXi;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.AnalyseQiutanJIshiIBiFen;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.OddsBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;

/**
 * @author 彭保星
 *
 * @since 2014年12月1日下午6:46:54
 */
public class JishiDataParseServiceImpl implements JishiDataParseService {
	private static final String DES_KEY = "DES_KEY";
	private static final String LQJINGCAI_JISHI_URL = "lqjingcaiJishiUrl";
	private static final String ZQ_JINGCAI_JISHI_URL = "zqJishiUrl";
	private static final String LANGUAGE_PARAM = "lang";
	private static final String MATCH_JISHI_BSID = "ID";
	private static final String MATCH_ODDSID = "OddsID";
	private static final String FLESH = "flesh";
	private static final String ZQ_JINGCAI_LIVE_URL = "zqLiveUrl"; //足球直播地址
	private DownloadService downloadService;

	private Logger logger = LoggerFactory
			.getLogger(JishiDataParseServiceImpl.class);
	private static Zq_FenXi zq_FenXi;
	private static Lq_FenXi lq_FenXi;
	private AnalyseQiutanJIshiIBiFen qiutanJishiBiFenAnalyse;

	private static String key = SystemPropertiesUtil.getPropsValue(DES_KEY);

	private PostDataService postDataService;

	public JishiDataParseServiceImpl() {
		downloadService = new DownloadServiceImpl();
		downloadService.makeHttpUtil();
		postDataService = new PostDataServiceImpl();
		qiutanJishiBiFenAnalyse = new AnalyseQiutanJIshiIBiFen();
		if (lq_FenXi == null) {
			lq_FenXi = new Lq_FenXi();
		}
		if (zq_FenXi == null) {
			zq_FenXi = new Zq_FenXi();
		}
	}

	@Override
	public List<QtMatchBaseModel> getQtFbJishiBifenData(
			Map<String, String> header) {
		List<QtMatchBaseModel> matchBaseModels = null;
		HttpResult httpResult = downloadService
				.downloadJishiBifenToStringWithExtenParams(null, header,
						SystemPropertiesUtil
								.getPropsValue(ZQ_JINGCAI_JISHI_URL));
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
			Map<String, String> header) {
		List<BasketBallMatchModel> qBallMatchModels = null;
		Map<String, String> extendParams = makeJingcaiExtendParams();
		HttpResult httpResult = downloadService
				.downloadJishiBifenToStringWithExtenParams(extendParams,
						header,
						SystemPropertiesUtil.getPropsValue(LQJINGCAI_JISHI_URL));
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
	public List<OddsBaseModel> crawlerZqJingcaiMatchJishiOdds(
			Map<String, String> header, String bsId,
			Qt_fb_match_oddsType oddsType) {
		List<OddsBaseModel> OddsIds = null;

		String oddsUrl;
		oddsUrl = makeOddsUrl(oddsType);
		HttpResult result = downloadService
				.downloadJishiBifenToStringWithExtenParams(
						makeJingcaiMatchJishiOddsExtendParams(bsId), header,
						oddsUrl);
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				OddsIds = zq_FenXi.analyseOddsAndCompanId(
						result.getResponseStr(), oddsType);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				OddsIds = new ArrayList<OddsBaseModel>();
			}
		}

		return OddsIds;
	}

	/**
	 * @param oddsType
	 * @return
	 */
	private String makeOddsUrl(Qt_fb_match_oddsType oddsType) {
		String oddsUrl;
		switch (oddsType) {
		case asia:
			oddsUrl = SystemPropertiesUtil
					.getPropsValue("QtFbJingcaiMatchJishiYpOdds");
			break;
		case ou:
			oddsUrl = SystemPropertiesUtil
					.getPropsValue("QtFbJingcaiMatchJishiOuOdds");
			break;
		case euro:
			oddsUrl = SystemPropertiesUtil
					.getPropsValue("QtFbJingcaiMatchJishiOpOdds");
			break;
		default:
			oddsUrl = "";
			break;
		}
		return oddsUrl;
	}

	private Map<String, String> makeJingcaiMatchJishiOddsExtendParams(
			String bsId) {
		Map<String, String> extendParams = new HashMap<>();
		if (StringUtils.isNotBlank(bsId)) {
			extendParams.put(MATCH_JISHI_BSID, bsId);
		}
		extendParams.put(LANGUAGE_PARAM, "0");
		return extendParams;
	}

	@Override
	public List<QtMatchOpOddsModel> crawlerJingcaiMatchHistoryData(
			Map<String, String> header, String oddsId,
			Qt_fb_match_oddsType oddsType) {
		List<QtMatchOpOddsModel> opOddsModels = null;

		String oddsUrl;
		oddsUrl = makeOddsHistoryUrl(oddsType);
		HttpResult result = downloadService
				.downloadJishiBifenToStringWithExtenParams(
						makeJingcaiMatchHistoryOddsExtendParams(oddsId),
						header, oddsUrl);
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				opOddsModels = zq_FenXi.analyseJishiHistoryOdds(result
						.getResponseStr());
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				opOddsModels = new ArrayList<>();
			}
		}

		return opOddsModels;
	}

	private String makeOddsHistoryUrl(Qt_fb_match_oddsType oddsType) {
		String oddsHistoryUrl;
		switch (oddsType) {
		case asia:
			oddsHistoryUrl = SystemPropertiesUtil
					.getPropsValue("QtFbJingcaiMatchHistoryYpOdds");
			break;
		case ou:
			oddsHistoryUrl = SystemPropertiesUtil
					.getPropsValue("QtFbJingcaiMatchHistoryOuOdds");
			break;
		case euro:
			oddsHistoryUrl = SystemPropertiesUtil
					.getPropsValue("QtFbJingcaiMatchHistoryOpOdds");
			break;
		default:
			oddsHistoryUrl = "";
			break;
		}
		return oddsHistoryUrl;
	}

	private Map<String, String> makeJingcaiMatchHistoryOddsExtendParams(
			String oddsId) {
		Map<String, String> extendParams = new HashMap<>();
		if (StringUtils.isNotBlank(oddsId)) {
			extendParams.put(MATCH_ODDSID, oddsId);
		}
		return extendParams;
	}

	@Override
	public List crawlerMatchEventHasFinishedData(Map<String, String> header,
			String bsId, long id) {
		List<AwdataStore> leagueInfoModels = null;

		HttpResult result = downloadService
				.downloadJishiBifenToStringWithExtenParams(
						makeMatchEventExtendParams(bsId), header,
						SystemPropertiesUtil
								.getPropsValue("QtFbMatchLiveEvent"));
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				leagueInfoModels = zq_FenXi.analyseShiJian(
						result.getResponseStr(), id);
				// logger.info("分析事件信息解析结果:{}", leagueAllInfo);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				leagueInfoModels = new ArrayList<AwdataStore>();
			}
		}

		return leagueInfoModels;
	}

	private Map<String, String> makeMatchEventExtendParams(String bsId) {
		Map<String, String> params = null;
		if (bsId != null) {
			params = new HashMap<>();
			if (StringUtils.isNotBlank(bsId)) {
				params.put(MATCH_JISHI_BSID, bsId);
			}
			params.put(LANGUAGE_PARAM, "0");
		}
		return params;
	}

	@Override
	public List<QtBasketMatchPlayerStatisticModel> crawlerBasketMatchPlayerStatisticHasFinishedData(
			Map<String, String> header, String bsId, Integer id) {
		List<QtBasketMatchPlayerStatisticModel> qBallMatchModels = null;
		HttpResult httpResult = downloadService
				.downloadJishiToStringWithExtendParamsBySpecielUrl(
						makeFleshExtendParams(), header, SystemPropertiesUtil
								.getPropsValue("basketMatchPlayerStatistic"),
						bsId);
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
	public List<QtBasketMatchTeamStatisticModel> crawlerBasketMatchTeamStatisticData(
			Map<String, String> header, String bsId, Integer id) {
		List<QtBasketMatchTeamStatisticModel> qBallMatchModels = null;
		HttpResult httpResult = downloadService
				.downloadJishiBifenToStringWithExtenParams(
						makeMatchTeamStatisticExtendParams(bsId), header,
						SystemPropertiesUtil
								.getPropsValue("qtBasketMatchTeamStatistics"));
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
			params.put(MATCH_JISHI_BSID, bsId);
		}
		return params;
	}

	@Override
	public List<QtMatchOpOddsModel> crawlerChangeOdds(
			Map<String, String> header, Qt_fb_match_oddsType oddsType) {
		List<QtMatchOpOddsModel> oddsModels = null;
		String url = null;
		switch (oddsType) {
		case euro:
			url = SystemPropertiesUtil.getPropsValue("zqEuroOddsChange");
			break;
		case asia:
			url = SystemPropertiesUtil.getPropsValue("zqAsianOddsChange");
			break;
		case ou:
			url = SystemPropertiesUtil.getPropsValue("zqOuOddsChange");
			break;
		default:
			break;
		}
		HttpResult httpResult = downloadService
				.downloadJishiBifenToStringWithExtenParams(null, header, url);
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				oddsModels = zq_FenXi.analyseJishiOdds(
						httpResult.getResponseStr(), oddsType);
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			oddsModels = new ArrayList<>();
		}
		return oddsModels;
	}

	@Override
	public List<QtBasketMatchOddsModel> crawlerLqJingcaiMatchJishiOdds(
			Map<String, String> header, String bsId,
			Qt_fb_match_oddsType oddsType, String lastReponse) {
		List<QtBasketMatchOddsModel> matchOpOddsModels = null;

		String oddsUrl;
		switch (oddsType) {
		case euro:
			oddsUrl = SystemPropertiesUtil.getPropsValue("lqJishiEuroOdds");
			break;
		case asia:
			oddsUrl = SystemPropertiesUtil.getPropsValue("lqJishiAsianOdds");
			break;
		case ou:
			oddsUrl = SystemPropertiesUtil.getPropsValue("lqJishiOuOdds");
			break;
		default:
			oddsUrl = SystemPropertiesUtil.getPropsValue("lqJishiEuroOdds");
			break;
		}
		HttpResult result = downloadService
				.downloadJishiBifenToStringWithExtenParams(
						makeJingcaiMatchJishiOddsExtendParams(bsId), header,
						oddsUrl);
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				matchOpOddsModels = lq_FenXi.analyseBasketJishiOdds(
						result.getResponseStr(), oddsType, lastReponse);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				matchOpOddsModels = new ArrayList<QtBasketMatchOddsModel>();
			}
		}

		return matchOpOddsModels;
	}

	@Override
	public void sendZqJishiBifenData(List<QtMatchBaseModel> matchBaseInfos)
			throws IOException {
		String jsonResult = JsonConvertUtil
				.convertObjectNotNullPropertiesToJsonString(matchBaseInfos);
		String encryptedData = encodeDataUseDES(jsonResult);
		if (encryptedData != null) {
			postDataService.sendZqJishiBifenDataToServer(encryptedData);
		}

	}

	private String encodeDataUseDES(String jsonResult) {
		checkKeyIsNotNull();
		try {
			return DES.encryptDES(jsonResult, key, "UTF-8");
		} catch (Exception e) {
			logger.error("des加解密出错{}", e);
		}
		return null;
	}

	/**
	 * 
	 */
	private void checkKeyIsNotNull() {
		if (key != null) {
			key = SystemPropertiesUtil.getPropsValue(DES_KEY);
		}
	}

	@Override
	public void sendLqjingcaiJishi(List<BasketBallMatchModel> ballMatchModels)
			throws IOException {
		String jsonResult = JsonConvertUtil
				.convertObjectNotNullPropertiesToJsonString(ballMatchModels);
		String encodeData = encodeDataUseDES(jsonResult);
		if (encodeData != null) {
			postDataService.sendBbJingcaiJishiBifenDataToServer(encodeData);
		}
	}

	@Override
	public void sendZqJishiOdds(List<QtMatchOpOddsModel> oddsModels,
			Qt_fb_match_oddsType oddsType) throws IOException {
		if (oddsModels != null && !oddsModels.isEmpty()) {
			String jsonResult = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(oddsModels);
			String encodeString = encodeDataUseDES(jsonResult);
			if (encodeString != null) {
				postDataService
						.sendZqChangeOddsToServer(encodeString, oddsType);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FbMatchBaseInfoPO> gotAllJingcaiZqMatchInLive()
			throws IOException {
		String encodeString = postDataService.receiveAllJingcaiZqMatchInLive();

		if (StringUtils.isNotBlank(encodeString)) {
			String decodeString = decodeStringWithDES(encodeString);
			return (List<FbMatchBaseInfoPO>) JsonConvertUtil
					.convertJsonToObject(decodeString, List.class,
							FbMatchBaseInfoPO.class);
		} else {
			return null;
		}
	}

	private String decodeStringWithDES(String encodeString) {
		// TODO Auto-generated method stub
		checkKeyIsNotNull();
		try {
			return DES.decryptDES(encodeString, key, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void sendZqJishiEvent(List<AwdataStore> qtMatchEventStatistics)
			throws IOException {
		String jsonResult = "";
		if (qtMatchEventStatistics != null && !qtMatchEventStatistics.isEmpty()) {
			jsonResult = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(qtMatchEventStatistics);
			String encodeData = encodeDataUseDES(jsonResult);
			if (StringUtils.isNotBlank(encodeData)) {
				postDataService.sendZqJishiEventToServer(encodeData);
			}
		}
		
	}

	@Override
	public List<BasketBallMatchModel> gotAllBasketMathcInlive() throws IOException {
		String encodeString = postDataService.receiveAllBasketMathcInlive();
		return decodeLqJingcaiMatchJson(encodeString);
	}

	@Override
	public void sendBasketMatchPlayerStatisticData(
			List<QtBasketMatchPlayerStatisticModel> qtMatchEventStatistics) throws IOException {
		if (qtMatchEventStatistics != null && !qtMatchEventStatistics.isEmpty()) {
			String jsonResult = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(qtMatchEventStatistics);
			String encodeString = encodeDataUseDES(jsonResult);
			if (encodeString != null) {
				postDataService
						.sendBasketMatchPlayerStatisticDataToServer(encodeString);
			}
		}
	}

	@Override
	public void sendMatchTeamStatisticData(
			List<QtBasketMatchTeamStatisticModel> qtMatchEventStatistics) throws IOException {
		if (qtMatchEventStatistics != null && !qtMatchEventStatistics.isEmpty()) {
			String jsonResult = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(qtMatchEventStatistics);
			String encodeString = encodeDataUseDES(jsonResult);
			if (encodeString != null) {
				postDataService
						.sendMatchTeamStatisticDataToServer(encodeString);
			}
		}
	}

	@Override
	public List<BasketBallMatchModel> gotAllJingcaiLqMatchNotStart() throws IOException {
		String encodeString = postDataService.gotAllJingcaiLqMatchNotStart();
		return decodeLqJingcaiMatchJson(encodeString);
	}

	/**
	 * @param encodeString
	 * @return
	 */
	private List<BasketBallMatchModel> decodeLqJingcaiMatchJson(
			String encodeString) {
		if (StringUtils.isNotBlank(encodeString)) {
			String decodeString = decodeStringWithDES(encodeString);
			return (List<BasketBallMatchModel>) JsonConvertUtil
					.convertJsonToObject(decodeString, List.class,
							BasketBallMatchModel.class);
		} else {
			return null;
		}
	}

	@Override
	public void sendLqJishiOdds(List<QtBasketMatchOddsModel> oddsModels,
			Qt_fb_match_oddsType oddsType, BasketBallMatchModel matchBaseInfoPO) throws IOException {
		if(oddsModels!=null&&!oddsModels.isEmpty()){
			String oddsData = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(oddsModels);
			oddsData = encodeDataUseDES(oddsData);
			String matchBaseData = JsonConvertUtil.convertObjectNotNullPropertiesToJsonString(matchBaseInfoPO);
			matchBaseData = encodeDataUseDES(matchBaseData);
			postDataService.sendLqJishiOddsToServer(matchBaseData,oddsData,oddsType);
		}
	}

	@Override
	public String crawlerZqLiveUrl(String bsId,Map<String, String> header) {
		String televisonUrl = null;
		Map<String, String> extendsParams = makeZqLiveUrlParams(bsId);
		HttpResult httpResult = downloadService
				.downloadJishiBifenToStringWithExtenParams(extendsParams, header,
						SystemPropertiesUtil
								.getPropsValue(ZQ_JINGCAI_LIVE_URL));
		if (httpResult.isSucc() == true) {
			if (httpResult.getResponseStr() != null) {
				televisonUrl = httpResult.getResponseStr();
			}
		} else if (httpResult.getStatusCode() != null
				&& 500 == httpResult.getStatusCode()) { // 服务器内部错误时跳过本赛季的解析
			televisonUrl = "";
		}
		return televisonUrl;
	}

	private Map<String, String> makeZqLiveUrlParams(String bsId) {
		Map<String, String> extendsMap = new HashMap<>();
		extendsMap.put("scheduleid", bsId);
		extendsMap.put("bt", "1");
		extendsMap.put("from", "1");
		return extendsMap;
	}

	@Override
	public List<FbMatchBaseInfoPO> gotAllZqMatchInMatchNotHaveLiveUrl() throws IOException {
		String encodeData = postDataService.receiveAllZqMatchInMatchNotHaveLiveUrl();
		List<FbMatchBaseInfoPO> matchBaseInfoPOs = null;
		if(StringUtils.isNotBlank(encodeData)){
			String decode = decodeStringWithDES(encodeData);
			matchBaseInfoPOs = (List<FbMatchBaseInfoPO>) JsonConvertUtil.convertJsonToObject(decode, List.class,FbMatchBaseInfoPO.class);
		}
		return matchBaseInfoPOs;
	}

	@Override
	public void sendZqLiveUrl(String televisonUrl, long id) throws IOException {
		// TODO Auto-generated method stub
		String encodeData = encodeDataUseDES(televisonUrl);
		postDataService.sendZqLiveUrlToServer(encodeData,id);
		
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
	public List<QtBasketMatchOddsModel> crawlerLqJingcaiMatchJishiOdds(
			Map<String, String> header, String lastReponse, String corpId) {
		HttpResult result = downloadService
				.downloadJishiBifenToStringWithExtenParams(
						makeJingcaiMatchJishiZhishuOddsExtendParams(corpId), header,
						SystemPropertiesUtil.getPropsValue("lqjishiZhishuOdds"));
		List<QtBasketMatchOddsModel> matchOpOddsModels = null;
		if (result != null && result.isSucc() == true) {
			if (result.getResponseStr() != null) {
				matchOpOddsModels = lq_FenXi.analyseBasketJishiOdds(
						result.getResponseStr(),lastReponse,corpId);
			}
		} else {
			if (result.getStatusCode() != null && result.getStatusCode() == 500) {
				matchOpOddsModels = new ArrayList<QtBasketMatchOddsModel>();
			}
		}

		return matchOpOddsModels;
	}

	private Map<String, String> makeJingcaiMatchJishiZhishuOddsExtendParams(
			String corpId) {
		Map<String, String> extendParams = new HashMap<>();
		if (StringUtils.isNotBlank(corpId)) {
			extendParams.put("companyId", corpId);
		}
		extendParams.put(LANGUAGE_PARAM, "0");
		extendParams.put("type", "2");//竞彩
		return extendParams;
	}

}
