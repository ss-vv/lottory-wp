package com.davcai.lottery.weibo.data.receviceStore.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davcai.lottery.push.client.NewPushMessageHandlerRedisImpl;
import com.davcai.lottery.push.client.PushClientCometdImpl;
import com.davcai.lottery.push.client.PushClientException;
import com.davcai.lottery.push.client.PushResponse;
import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.LotteryType;
import com.davcai.lottery.push.common.model.OddsDataMessage;
import com.davcai.lottery.push.common.model.OddsType;
import com.davcai.lottery.push.common.model.PushLiveOdds;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushResult;
import com.davcai.lottery.push.common.redis.RedisClientImpl;
import com.davcai.lottery.push.common.redis.dao.PushMessageDaoImpl;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.JishiBifenDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LeagueScoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LqLeagueInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.LqMatchInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.MatchLineupDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.SeasonInfoDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueSeasonPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataImpl;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Constants;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.DES;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.JsonConvertUtil;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.AwdataStore;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u.c;
import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BbLeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreRuleModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.LeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchEventModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchLineupModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.util.BeanConvertUtil;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;

/**
 *
 * @author baoxing.peng
 * @since 2014年12月31日下午4:06:35
 */
@Service
public class ReceiveAndSendDataServiceImpl implements ReceiveAndSendDataService {
	private static final String CHAR_SET = "UTF-8";
	Logger log = LoggerFactory.getLogger(ReceiveAndSendDataServiceImpl.class);
	@Value("${DES_KEY}")
	private String desKey;
	@Autowired
	private JishiBifenDataStoreDao jishiBifenDataStoreDao;
	@Autowired
	private JishiDataParseService jishiDataParseService;
	@Autowired
	private PushClientCometdImpl pushClient;
	@Autowired
	private NewPushMessageHandlerRedisImpl checker;
	@Autowired
	private PushMessageDaoImpl pushMessageDao;
	@Autowired
	private RedisClientImpl redisClient;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	public SeasonInfoDataStoreDao crawlerSeasonInfoDataStoreDao;
	@Autowired
	public MatchInfoDataStoreDao crawlerMatchInfoDataStoreDao;
	@Autowired
	public LeagueInfoDataStoreDao crawlerDataStoreDao;
	@Autowired
	private MatchLineupDataStoreDao crawlerMatchLineupDataStoreDao;
	@Autowired
	public LeagueScoreDao crawlerLeagueScoreDao;
	@Autowired
	private LqMatchInfoDataStoreDao lqMatchInfoDataStoreDao;
	@Autowired
	private LqLeagueInfoDataStoreDao lqLeagueInfoDataStoreDao;
	private static final List<String> corpIds = new ArrayList<>();
	private static final Map<String, String> fbOpCorpId = new HashMap<>();
	private static final Map<String, String> jingcaiCode = new HashMap<>();

	static {
		// 推送赔率数据前，公司id的初始化
		corpIds.add("1");
		corpIds.add("3");
		corpIds.add("4");
		corpIds.add("8");
		corpIds.add("12");
		corpIds.add("14");
		corpIds.add("17");
		corpIds.add("22");
		corpIds.add("23");
		corpIds.add("24");
		corpIds.add("31");
		corpIds.add("35");
		// 数据库足球欧赔公司id与即时欧赔公司id对照
		fbOpCorpId.put("1", "80");
		fbOpCorpId.put("3", "545");
		fbOpCorpId.put("4", "82");
		fbOpCorpId.put("8", "281");
		fbOpCorpId.put("12", "90");
		fbOpCorpId.put("14", "81");
		fbOpCorpId.put("17", "517");
		fbOpCorpId.put("22", "16");
		fbOpCorpId.put("23", "499");
		fbOpCorpId.put("24", "18");
		fbOpCorpId.put("31", "474");
		fbOpCorpId.put("35", "659");

		jingcaiCode.put("周一", "1");
		jingcaiCode.put("周二", "2");
		jingcaiCode.put("周三", "3");
		jingcaiCode.put("周四", "4");
		jingcaiCode.put("周五", "5");
		jingcaiCode.put("周六", "6");
		jingcaiCode.put("周日", "7");
	}
	
	@Override
	public void saveFbJingcaiJishiBifen(String jsonObjectDes) {
		// DES decryption

		try {
			List<QtMatchBaseModel> matchBaseInfos = decodeFbMatchToModel(jsonObjectDes);
			// 获取当前赛季并装配到model中
			if (!matchBaseInfos.isEmpty()) {
				// 推送比分数据内容
				pushFootMatch(matchBaseInfos);
				List<QtMatchBaseModel> qtMatchBaseModels = new ArrayList<>();
				for (QtMatchBaseModel qtMatchBaseModel : matchBaseInfos) {
					String seasonName = jishiBifenDataStoreDao
							.getNowSeasonByLeagueId(qtMatchBaseModel
									.getLeagueId());
					qtMatchBaseModel.setSeason(seasonName);
					qtMatchBaseModels.add(qtMatchBaseModel);
				}
				jishiBifenDataStoreDao.updateMatchData(qtMatchBaseModels);
			}
			log.info("足球竞彩即时比分数据存储完成...");
		} catch (Exception e) {
			log.error("即时比分数据抓取出错:{}", e);
		}
	}

	private void pushFootMatch(List<QtMatchBaseModel> matchBaseInfos)
			throws ParseException, PushClientException {
		List<PushMessage> footballMatchMessages = new ArrayList<>();
		for (QtMatchBaseModel qtMatchBaseModel : matchBaseInfos) {
			FootballMatchMessage footballMatchMessage = new FootballMatchMessage();
			footballMatchMessage.setMatchId(makeMatchId(qtMatchBaseModel));
			footballMatchMessage.setHomeTeamName(qtMatchBaseModel
					.getHomeTeamId());
			footballMatchMessage.setGuestTeamName(qtMatchBaseModel
					.getGuestTeamId());
			footballMatchMessage.setHomeScore(qtMatchBaseModel
					.getHomeTeamScore());
			footballMatchMessage.setGuestScore(qtMatchBaseModel
					.getGuestTeamScore());
			footballMatchMessage.setState(String.valueOf(qtMatchBaseModel
					.getMatchStatus()));
			footballMatchMessage.setGuestTeamHalfScore(qtMatchBaseModel
					.getGuestTeamHalfScore());
			footballMatchMessage.setHomeTeamHalfScore(qtMatchBaseModel
					.getHomeTeamHalfScore());
			footballMatchMessage.setGuestTeamPosition(String
					.valueOf(qtMatchBaseModel.getHomeTeamPosition()));
			footballMatchMessage.setGuestTeamRed(qtMatchBaseModel
					.getGuestTeamRed());
			footballMatchMessage.setGuestTeamYellow(qtMatchBaseModel
					.getGuestTeamYellow());
			footballMatchMessage.setHalfStartTime(qtMatchBaseModel
					.getHalfStartTime());
			footballMatchMessage.setHomeTeamPosition(String
					.valueOf(qtMatchBaseModel.getHomeTeamPosition()));
			footballMatchMessage.setHomeTeamRed(qtMatchBaseModel
					.getHomeTeamRed());
			footballMatchMessage.setHomeTeamYellow(qtMatchBaseModel
					.getHomeTeamYellow());
			footballMatchMessage.setScheduleMatchTime(qtMatchBaseModel
					.getMatchTime());
			footballMatchMessages.add(footballMatchMessage);
		}
		PushResult pushResult = pushClient.pushMessages(footballMatchMessages);
		PushResponse pushResponse=(PushResponse) pushResult;
		if(null!=pushResponse){
			if(null!=pushResponse.pushToComted){
				log.info("总共往cometd推送的数目{},推送结果:成功{}个，无需更新{}个,失败{}个",
						footballMatchMessages.size(), pushResponse.pushToComted.getCountOfSucc(),
						pushResponse.pushToComted.getCountOfOld(), pushResponse.pushToComted.getCountOfFail());
			}
			if(null!=pushResponse.pushToHX){
				log.info("总共往环信推送的数目{},推送结果:成功{}个，无需更新{}个,失败{}个",
						footballMatchMessages.size(), pushResponse.pushToHX.getCountOfSucc(),
						pushResponse.pushToHX.getCountOfOld(), pushResponse.pushToHX.getCountOfFail());
			}
			
		}
		else{
			log.error("推送失败");
		}
		
	}

	private String makeMatchId(Object qtMatchBaseModel) {
		StringBuilder matchId = new StringBuilder("");
		String jingcaiId = "";
		if (qtMatchBaseModel instanceof QtMatchBaseModel) {
			QtMatchBaseModel qtMatchBaseModel2 = (QtMatchBaseModel) qtMatchBaseModel;
			matchId.append(DateFormateUtil.getNowDate(qtMatchBaseModel2
					.getMatchTime()));
			jingcaiId = qtMatchBaseModel2.getJingcaiId();
		} else if (qtMatchBaseModel instanceof BasketBallMatchModel) {
			BasketBallMatchModel qtMatchBaseModel2 = (BasketBallMatchModel) qtMatchBaseModel;
			matchId.append(DateFormateUtil.getNowDate(qtMatchBaseModel2
					.getMatchTime()));
			jingcaiId = qtMatchBaseModel2.getJingcaiId();
		}
		if (jingcaiId != null) {
			String weekend = jingcaiId.substring(0, jingcaiId.length() - 3);
			String index = jingcaiId.substring(jingcaiId.length() - 3); // 序号，例如周三004指的就是004
			switch (weekend) {
			case "周一":
				weekend = "1";
				break;
			case "周二":
				weekend = "2";
				break;
			case "周三":
				weekend = "3";
				break;
			case "周四":
				weekend = "4";
				break;
			case "周五":
				weekend = "5";
				break;
			case "周六":
				weekend = "6";
				break;
			case "周日":
				weekend = "7";
				break;
			default:
				weekend = "0";
				break;
			}
			matchId.append(weekend);
			matchId.append(index);
		}
		return matchId.toString();
	}

	private String decryptionData(String jsonObjectDes) throws Exception {
		log.debug("deskey:{}", desKey);
		return DES.decryptDES(jsonObjectDes, desKey, CHAR_SET);
	}

	public String getDesKey() {
		return desKey;
	}

	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}

	@Override
	public void saveBbJingcaiJishiBifen(String jsonObject) {
		String originJsonData;
		try {
			originJsonData = decryptionData(jsonObject);
			List<BasketBallMatchModel> ballMatchModels = (List<BasketBallMatchModel>) JsonConvertUtil
					.convertJsonToObject(originJsonData, List.class,
							BasketBallMatchModel.class);
			if (!ballMatchModels.isEmpty()) {
				// 推送比分数据
				pushBasketBallMatch(ballMatchModels);
				jishiBifenDataStoreDao.storeLqJishi(ballMatchModels, true);
			}

			log.info("篮球竞彩即时数据存储完成!");
		} catch (Throwable e) {
			// TODO: handle exception
			log.error("抓取球探篮球竞彩即时比分数据出错：{}", e);
		}
	}

	private void pushBasketBallMatch(List<BasketBallMatchModel> ballMatchModels)
			throws PushClientException {
		List<PushMessage> pushMessages = new ArrayList<>();
		for (BasketBallMatchModel ballMatchModel : ballMatchModels) {
			BasketballMatchMessage basketballMatchMessage = new BasketballMatchMessage();
			basketballMatchMessage.setMatchId(makeMatchId(ballMatchModel));
			basketballMatchMessage
					.setHomeTeamName(ballMatchModel.getHomeTeam());
			basketballMatchMessage.setGuestTeamName(ballMatchModel
					.getGuestTeam());
			basketballMatchMessage.setHomeScore(ballMatchModel.getHomeScore());
			basketballMatchMessage
					.setGuestScore(ballMatchModel.getGuestScore());
			basketballMatchMessage.setHomeOne(ballMatchModel.getHomeOne());
			basketballMatchMessage.setGuestOne(ballMatchModel.getGuestOne());
			basketballMatchMessage.setHomeTwo(ballMatchModel.getHomeTwo());
			basketballMatchMessage.setGuestTwo(ballMatchModel.getGuestTwo());
			basketballMatchMessage.setHomeFour(ballMatchModel.getHomeFour());
			basketballMatchMessage.setGuestFour(ballMatchModel.getGuestFour());
			basketballMatchMessage.setHomeThree(ballMatchModel.getHomeThree());
			basketballMatchMessage
					.setGuestThree(ballMatchModel.getGuestThree());
			basketballMatchMessage
					.setRemainTime(ballMatchModel.getRemainTime());
			basketballMatchMessage.setHomeAddTimeScore(ballMatchModel
					.getHomeAddTime1());
			basketballMatchMessage.setGuestAddTimeScore(ballMatchModel
					.getGuestAddTime1());
			basketballMatchMessage.setScheduleMatchTime(ballMatchModel
					.getMatchTime());
			basketballMatchMessage.setExplainContent(ballMatchModel
					.getExplainContent());
			if (ballMatchModel.getMatchState() != null) {
				basketballMatchMessage.setState(String.valueOf(ballMatchModel
						.getMatchState()));
			} else {
				basketballMatchMessage.setState("");
			}
			pushMessages.add(basketballMatchMessage);
			log.debug(pushMessages.toString());
		}
		PushResult pushResult = pushClient.pushMessages(pushMessages);
		PushResponse pushResponse=(PushResponse) pushResult;
		if(null!=pushResponse){
			if(null!=pushResponse.pushToComted){
				log.info("总共往cometd推送的数目{},推送结果:成功{}个，无需更新{}个,失败{}个",
						ballMatchModels.size(), pushResponse.pushToComted.getCountOfSucc(),
						pushResponse.pushToComted.getCountOfOld(), pushResponse.pushToComted.getCountOfFail());
			}
			if(null!=pushResponse.pushToHX){
				log.info("总共往环信推送的数目{},推送结果:成功{}个，无需更新{}个,失败{}个",
						ballMatchModels.size(), pushResponse.pushToHX.getCountOfSucc(),
						pushResponse.pushToHX.getCountOfOld(), pushResponse.pushToHX.getCountOfFail());
			}
			
		}
		else{
			log.error("推送失败");
		}
	}

	@Override
	public void saveFbOddChange(String jsonObject, Qt_fb_match_oddsType oddsType) {
		log.info("球探足球即时{}赔率存库开始!", oddsType);
		try {
			String originJsonData = decryptionData(jsonObject);
			@SuppressWarnings("unchecked")
			List<QtMatchOpOddsModel> oddsModels = (List<QtMatchOpOddsModel>) JsonConvertUtil
					.convertJsonToObject(originJsonData, List.class,
							QtMatchOpOddsModel.class);
			if (!oddsModels.isEmpty()) {
				//推送足球即时欧赔
				pushOddsMessage(oddsType, oddsModels);
				if (!StringUtils.equals(oddsType.toString(), "euro")) {
					jishiBifenDataStoreDao.storeZqJishiOdds(oddsModels,
							oddsType);
				}
			}

			log.info("球探足球即时赔率存库完成!");
		} catch (Throwable e) {
			// TODO: handle exception
			log.error("存储球探篮球竞彩即时赔率数据出错：{}", e);
		}
	}

	private void pushOddsMessage(Qt_fb_match_oddsType oddsType,
			List<QtMatchOpOddsModel> oddsModels) {
		// TODO Auto-generated method stub
		PushOddsDataService pushOddsDataService = new PushOddsDataImpl(jishiBifenDataStoreDao);
		pushOddsDataService.pushFbOddsMessage(oddsType, oddsModels);
	}
	

	@Override
	public String gotFbJingcaiMatchInLive() {
		log.info("获取正在直播的竞彩足球赛事数据...");
		try {
			List<FbMatchBaseInfoPO> matchBaseInfoPOs = jishiBifenDataStoreDao
					.queryAllZqMatchInLive();
			String jsonObject = "";
			if (matchBaseInfoPOs != null && matchBaseInfoPOs.size() > 0) {
				jsonObject = JsonConvertUtil
						.convertObjectNotNullPropertiesToJsonString(matchBaseInfoPOs);
			}
			String desdata = jsonObject.equals("") ? ""
					: encodeData(jsonObject);
			log.info("获取正在直播的竞彩足球赛事{}条数据完成...", matchBaseInfoPOs.size());
			return desdata;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("查询足球竞彩直播数据出错：{}", e);
		}

		return null;
	}

	private String encodeData(String jsonData) throws Exception {
		// TODO Auto-generated method stub
		if (jsonData != null) {
			return DES.encryptDES(jsonData, desKey, CHAR_SET);
		} else {
			return "";
		}
	}

	@Override
	public void saveFbJishiEvent(String jsonObject) {
		log.info("开始存储足球比赛事件和技术统计...");
		try {
			String originJsonData = decryptionData(jsonObject);
			List<AwdataStore> qtMatchEventStatistics = (List<AwdataStore>) JsonConvertUtil
					.convertJsonToObject(originJsonData, ArrayList.class,
							AwdataStore.class);
			AwdataStore matchEvents = qtMatchEventStatistics.size() > 0 ? (AwdataStore) qtMatchEventStatistics
					.get(0) : null;

			if (matchEvents != null) {
				if (matchEvents.a.equals("比赛事件")) {
					// 比赛事件model

					jishiBifenDataStoreDao.storeMatchEventData(BeanConvertUtil
							.makeBean(matchEvents.b, new QtMatchEventModel()));
				} else if (matchEvents.a.equals("赛事技术统计")) {
					jishiBifenDataStoreDao
							.storeMatchStatisticData(BeanConvertUtil.makeBean(
									matchEvents.b, new QtMatchStatisticModel()));
				}
			}

			AwdataStore matchStatistics = qtMatchEventStatistics.size() > 1 ? (AwdataStore) qtMatchEventStatistics
					.get(1) : null;

			if (matchStatistics != null && matchStatistics.a.equals("赛事技术统计")) {
				jishiBifenDataStoreDao.storeMatchStatisticData(BeanConvertUtil
						.makeBean(matchStatistics.b,
								new QtMatchStatisticModel()));
			}
			log.info("存储足球比赛事件和技术统计完成...");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("存储足球直播事件数据出错：{}", e);
		}
	}

	@Override
	public void saveFbLeagueInfo(String jsonObject) {
		log.info("存储足球联赛数据开始...");
		try {
			String decodeData = decryptionData(jsonObject);
			List<LeagueInfoModel> leagueInfoModels = (List<LeagueInfoModel>) JsonConvertUtil
					.convertJsonToObject(decodeData, List.class,
							LeagueInfoModel.class);
			crawlerDataStoreDao.storeFbLeague(leagueInfoModels);
			log.info("存储足球联赛数据完成...");
		} catch (Throwable e) {
			log.error("存储足球联赛信息出错:{}", e);
		}
	}

	@Override
	public String gotFbSeasonMessSubLeagueMess(String source, String leagueType) {
		log.info("获取所有不属于子联赛的联赛赛季历史数据...");
		if (source != null && leagueType != null) {
			try {
				// 查询球探所有赛季信息
				List<FbLeagueSeasonBasePO> fbLeagueSeasonBasePOs = crawlerSeasonInfoDataStoreDao
						.queryAllSeasonMessageSubLeague(
								Integer.valueOf(source),
								Integer.valueOf(leagueType)); // 查询所有不属于子联赛的联赛赛季历史数据
				String desdata = makeLeagueSeasonPOEncodeMess(fbLeagueSeasonBasePOs);
				log.info("获取所有不属于子联赛的联赛赛季历史数据完成...");
				return desdata;
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				log.error("获取所有不属于子联赛的联赛赛季历史数据出错：{}", e);
			}
		}
		return null;
	}

	@Override
	public void saveFbSubLeague(String jsonObject, String leagueType) {
		log.info("存储足球子联赛数据开始...");
		try {
			String decodeData = decryptionData(jsonObject);
			List<SeasonModel> subLeagueOfOneSeaon = (List<SeasonModel>) JsonConvertUtil
					.convertJsonToObject(decodeData, List.class,
							SeasonModel.class);
			crawlerSeasonInfoDataStoreDao.storeFbSubLeague(subLeagueOfOneSeaon,
					Integer.valueOf(leagueType));
			log.info("存储足球子联赛数据完成...");
		} catch (Exception exception) {
			log.error("存储足球子联赛数据出错{}", exception);
		}
	}

	@Override
	public String gotAllSeasonMessNotCrawler(String source) {
		log.info("查询所有未抓取完的联赛赛季历史数据...");
		String desdata = null;
		if (StringUtils.isNotBlank(source)) {
			try {
				// 查询球探所有赛季信息
				List<FbLeagueSeasonBasePO> fbLeagueSeasonBasePOs = crawlerDataStoreDao
						.queryAllSeasonMessageNotCrawler(Integer
								.valueOf(source)); // 查询所有未抓取完的联赛赛季历史数据
				desdata = makeLeagueSeasonPOEncodeMess(fbLeagueSeasonBasePOs);
				log.info("查询所有未抓完的联赛赛季历史数据完成...");
			} catch (Exception e) {
				log.error("查询所有未抓完的联赛赛季历史数据出错:{}", e);
			}
		}
		return desdata;
	}

	/**
	 * @param fbLeagueSeasonBasePOs
	 * @return
	 * @throws Exception
	 */
	private String makeLeagueSeasonPOEncodeMess(
			List<FbLeagueSeasonBasePO> fbLeagueSeasonBasePOs) throws Exception {
		String jsonObject = "";
		if (fbLeagueSeasonBasePOs != null && fbLeagueSeasonBasePOs.size() > 0) {
			jsonObject = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(fbLeagueSeasonBasePOs);
		}
		String desdata = jsonObject.equals("") ? "" : encodeData(jsonObject);
		return desdata;
	}

	@Override
	public void saveFbMatchList(String jsonObject, int round, int seasonId) {
		if (StringUtils.isNotBlank(jsonObject)) {
			log.info("存储球探足球比赛赛程数据开始...");
			try {
				List<QtMatchBaseModel> qtMatchBaseModels = decodeFbMatchToModel(jsonObject);

				crawlerDataStoreDao.storeMatchBaseMessage(qtMatchBaseModels,
						round);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				if (e instanceof JDBCException) {
					if (((JDBCException) e).getErrorCode() == 1062) { // 唯一键重复，数据问题造成,表示已经抓取过
						e.printStackTrace();
						crawlerDataStoreDao.updateCrawlerCount(
								Integer.valueOf(seasonId), 1);
					}
				}
			}
			log.info("存储球探足球比赛赛程数据完成...");
		}
	}

	/**
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	private List<QtMatchBaseModel> decodeFbMatchToModel(String jsonObject)
			throws Exception {
		String decodeData = decryptionData(jsonObject);
		List<QtMatchBaseModel> qtMatchBaseModels = (List<QtMatchBaseModel>) JsonConvertUtil
				.convertJsonToObject(decodeData, List.class,
						QtMatchBaseModel.class);
		return qtMatchBaseModels;
	}

	@Override
	public String receiveFbCupGroupMess(String source, String cupType) {
		log.info("查询足球杯赛分组赛信息开始...");
		String desdata = "";
		try {
			List<FbLeagueSeasonBasePO> leagueSeasonBasePOs = crawlerSeasonInfoDataStoreDao
					.queryAllCupGroupMessToCraw(Integer.valueOf(source),
							Integer.valueOf(cupType));
			desdata = makeLeagueSeasonPOEncodeMess(leagueSeasonBasePOs);
			log.info("查询足球杯赛分组赛信息完成...");
		} catch (Exception exception) {
			log.error("查询足球杯赛分组赛信息出错{}", exception);
		}
		return desdata;
	}

	@Override
	public String receiveFbLeagueNowSeason(String leagueId) {
		// TODO Auto-generated method stub
		String desdata = "";
		try {
			String nowSeason = crawlerSeasonInfoDataStoreDao
					.queryNowSeasonNameByLeagueId(leagueId);
			List<String> list = new ArrayList<>();
			list.add(nowSeason);
			desdata = encodeData(JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(nowSeason));
		} catch (Exception exception) {
			log.error("{}", exception);
		}
		return desdata;
	}

	@Override
	public void saveFbCupMatchInfo(String jsonObject, String seasonId)
			throws Exception {
		List<QtMatchBaseModel> qtMatchBaseModels = null;
		try {
			qtMatchBaseModels = decodeFbMatchToModel(jsonObject);

			crawlerMatchInfoDataStoreDao.storeCupMatchInfo(qtMatchBaseModels);

		} catch (Exception e1) {
			log.error("存储球探杯赛数据出错{}", e1);
			if (e1 instanceof JDBCException) {
				if (((JDBCException) e1).getErrorCode() == 1062) { // 唯一键重复，数据问题造成,表示已经抓取过
					e1.printStackTrace();
					crawlerDataStoreDao.updateCrawlerCount(
							Integer.valueOf(seasonId), 1);
				} else {
					throw e1;
				}
			} else {
				throw e1;
			}
		}
	}

	@Override
	public String receiveZqMatchNotHaveLineup() {
		List<FbMatchBaseInfoPO> allMatchHasFinishBsIds = crawlerMatchLineupDataStoreDao
				.queryAllMatchDataHasFinish(0);
		String desdata = "";
		if (allMatchHasFinishBsIds != null && !allMatchHasFinishBsIds.isEmpty()) {
			String jsonData = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(allMatchHasFinishBsIds);
			try {
				desdata = encodeData(jsonData);
			} catch (Exception e) {
			}
		}
		return desdata;
	}

	@Override
	public void saveFbMatchLineup(String jsonObject) {
		try {
			if (StringUtils.isNotBlank(jsonObject)) {
				String decodeData = decryptionData(jsonObject);
				QtMatchLineupModel lineupModel = (QtMatchLineupModel) JsonConvertUtil
						.convertJsonToObject(decodeData,
								QtMatchLineupModel.class);
				crawlerMatchLineupDataStoreDao
						.storeMatchLineupData(lineupModel);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("存储足球比赛首发阵容出错{}", e);
		}

	}

	@Override
	public String queryFbLeagueSeasonNotHaveRule(String leagueType) {
		// TODO Auto-generated method stub
		List<FbLeagueSeasonBasePO> seasonBasePOs = crawlerDataStoreDao
				.queryAllSeasonNotHaveRule(Integer.valueOf(leagueType), 1);
		String encodeData = "";
		if (seasonBasePOs != null && !seasonBasePOs.isEmpty()) {
			String jsonData = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(seasonBasePOs);
			try {
				encodeData = encodeData(jsonData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("", e);
			}

		}
		return encodeData;
	}

	@Override
	public void saveFbLeagueScore(String encodeSeasonJson, String jsonObject) {
		try {
			if (StringUtils.isNotBlank(jsonObject)) {
				String decodeData = decryptionData(jsonObject);
				Map<String, Object> scoreRule1 = (Map<String, Object>) JsonConvertUtil
						.convertJsonToObject(decodeData, Map.class,
								String.class, Object.class);
				List<Map<String, String>> leagueScoreModelMap = (List<Map<String, String>>) scoreRule1
						.get("2");
				List<FbLeagueScoreModel> leagueScoreModels = BeanConvertUtil
						.makeBean(leagueScoreModelMap, new FbLeagueScoreModel());

				Map<String, Object> scoreRule = new HashMap<>();
				scoreRule.put("2", leagueScoreModels);
				List<Map<String, String>> scoreRuleModelMap = (List<Map<String, String>>) scoreRule1
						.get("1");
				List<FbLeagueScoreRuleModel> leagueScoreRuleModels = scoreRuleModelMap != null ? BeanConvertUtil
						.makeBean(scoreRuleModelMap,
								new FbLeagueScoreRuleModel()) : null;
				scoreRule.put("1", leagueScoreRuleModels);
				decodeData = decryptionData(encodeSeasonJson);
				SeasonModel seasonModel = (SeasonModel) JsonConvertUtil
						.convertJsonToObject(decodeData, SeasonModel.class);
				crawlerLeagueScoreDao.storeLeagueScoreData(scoreRule,
						seasonModel);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("存储足球联赛积分出错{}", e);
		}
	}

	@Override
	public String queryAllCupSeasonNotCrawler(String leagueType, String source,
			String isSubLeague) {
		String encodeData = "";
		try {
			// 获取没有抓取过积分的杯赛分组赛
			List<FbLeagueSeasonBasePO> seasonBasePOs = crawlerDataStoreDao
					.queryAllCupSeasonNotCrawler(Integer.valueOf(leagueType),
							Integer.valueOf(isSubLeague),
							Integer.valueOf(source));
			if (seasonBasePOs != null && !seasonBasePOs.isEmpty()) {
				encodeData = JsonConvertUtil
						.convertObjectNotNullPropertiesToJsonString(seasonBasePOs);
				encodeData = encodeData(encodeData);
			}
		} catch (Throwable e) {
			log.error("查询当前赛季杯赛分组赛出错:{}", e);
		}
		return encodeData;
	}

	@Override
	public void saveFbCupSocre(String encodeSeasonJson, String jsonObject) {
		if (StringUtils.isNotBlank(jsonObject)) {
			try {
				String json = decryptionData(encodeSeasonJson);
				SeasonModel seasonModel = (SeasonModel) JsonConvertUtil
						.convertJsonToObject(json, SeasonModel.class);
				json = decryptionData(jsonObject);
				List<FbLeagueScoreModel> fbLeagueScoreModels = (List<FbLeagueScoreModel>) JsonConvertUtil
						.convertJsonToObject(json, List.class,
								FbLeagueScoreModel.class);

				crawlerLeagueScoreDao.storeCupScoreData(fbLeagueScoreModels,
						seasonModel);
			} catch (Exception e) {
				log.error("存储足球杯赛积分出错:{}", e);
			}
		}
	}

	@Override
	public void updateFbAllJishiMatchMess(String jsonObject) {
		try {
			String json = decryptionData(jsonObject);
			List<QtMatchBaseModel> qtMatchBaseModels = (List<QtMatchBaseModel>) JsonConvertUtil
					.convertJsonToObject(json, List.class,
							QtMatchBaseModel.class);
			if (qtMatchBaseModels != null) {
				crawlerMatchInfoDataStoreDao.updateMatchData(qtMatchBaseModels);
			}
		} catch (Exception exception) {
			log.error("更新足球所有比赛的即时比分的信息时出错{}", exception);
		}
	}

	@Override
	public void updateLqAllJishiMatchMess(String jsonObject) {
		if (StringUtils.isNotBlank(jsonObject)) {
			try {
				String json = decryptionData(jsonObject);
				List<BasketBallMatchModel> ballMatchModels = (List<BasketBallMatchModel>) JsonConvertUtil
						.convertJsonToObject(json, List.class,
								BasketBallMatchModel.class);
				lqMatchInfoDataStoreDao.storeJishiMatchInfo(ballMatchModels,
						false);
			} catch (Exception exception) {
				log.error("更新篮球所有比赛即时比分数据出错:{}", exception);
			}
		}
	}

	@Override
	public void saveBbLeagueSeason(String jsonObject) {
		if (StringUtils.isNotBlank(jsonObject)) {
			try {
				String json = decryptionData(jsonObject);
				List<BbLeagueInfoModel> leagueInfoModels = (List<BbLeagueInfoModel>) JsonConvertUtil
						.convertJsonToObject(json, List.class,
								BbLeagueInfoModel.class);
				lqLeagueInfoDataStoreDao.storeBbLeague(leagueInfoModels);
			} catch (Exception exception) {
				log.error("更新篮球联赛及赛季信息时出错:{}", exception);
			}
		}
	}

	@Override
	public String queryLqSubLeague(String leagueType, String source,
			String isSubLeague) {
		String encodeData = "";
		try {
			// 获取没有抓取过积分的杯赛分组赛
			List<BasketBallLeagueSeasonPO> seasonBasePOs = lqLeagueInfoDataStoreDao
					.queryAllSubLeagueNotCrawler(Integer.valueOf(source),
							Integer.valueOf(isSubLeague),
							Integer.valueOf(leagueType));
			if (seasonBasePOs != null && !seasonBasePOs.isEmpty()) {
				encodeData = JsonConvertUtil
						.convertObjectNotNullPropertiesToJsonString(seasonBasePOs);
				encodeData = encodeData(encodeData);
			}
		} catch (Throwable e) {
			log.error("查询篮球联赛当前赛季子联赛信息出错:{}", e);
		}
		return encodeData;
	}

	@Override
	public void saveLqLeagueMatchMessage(String seasonDecodeJson,
			String jsonObject) {
		if (StringUtils.isNotBlank(jsonObject)) {
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel = null;
			try {
				String json = decryptionData(seasonDecodeJson);
				basketBallLeagueSeasonModel = (BasketBallLeagueSeasonModel) JsonConvertUtil
						.convertJsonToObject(json,
								BasketBallLeagueSeasonModel.class);
				json = decryptionData(jsonObject);
				List<BasketBallMatchModel> ballMatchModels = (List<BasketBallMatchModel>) JsonConvertUtil
						.convertJsonToObject(json, List.class,
								BasketBallMatchModel.class);

				lqMatchInfoDataStoreDao.storeLqHistoryMatch(
						basketBallLeagueSeasonModel, ballMatchModels);
			} catch (Exception e) {
				log.error("存储篮球当前赛季联赛赛程信息出错:{}", e);
				if (e instanceof JDBCException) {
					JDBCException exception = (JDBCException) e;
					if (exception.getErrorCode() == 1062) { // 唯一键重复，数据问题造成,表示已经抓取过
						lqMatchInfoDataStoreDao.updateIsCrawler(
								basketBallLeagueSeasonModel.getSeasonId(), 1);
					}
				}
			}
		}
	}

	@Override
	public String queryLqLeagueNotSubByType(String leagueType, String source) {
		String encodeData = "";
		try {
			// 获取没有抓取过积分的杯赛分组赛
			List<BasketBallLeagueSeasonPO> leagueSeasonBasePOs = lqLeagueInfoDataStoreDao
					.queryAllLeagueSeasonNotSub(Integer.valueOf(source),
							Integer.valueOf(leagueType));
			if (leagueSeasonBasePOs != null && !leagueSeasonBasePOs.isEmpty()) {
				encodeData = JsonConvertUtil
						.convertObjectNotNullPropertiesToJsonString(leagueSeasonBasePOs);
				encodeData = encodeData(encodeData);
			}
		} catch (Throwable e) {
			log.error("查询篮球联赛当前赛季子联赛信息出错:{}", e);
		}
		return encodeData;
	}

	@Override
	public void saveLqCupGroup(String jsonObject, String leagueType) {
		if (StringUtils.isNotBlank(jsonObject)) {
			try {
				List<BasketBallLeagueSeasonModel> leagueSeasonModels = decodeLeagueSeasonFromJson(jsonObject);

				lqLeagueInfoDataStoreDao.storeBasketSubLeague(
						leagueSeasonModels, Integer.valueOf(leagueType));

			} catch (Exception e) {
				log.error("存储篮球当前赛季杯赛分组赛信息出错:{}", e);
			}
		}
	}

	@Override
	public void saveLqSubLeague(String jsonObject) {
		if (StringUtils.isNotBlank(jsonObject)) {
			try {
				List<BasketBallLeagueSeasonModel> leagueSeasonModels = decodeLeagueSeasonFromJson(jsonObject);

				lqLeagueInfoDataStoreDao.storeSubleague(leagueSeasonModels);

			} catch (Exception e) {
				log.error("存储篮球当前赛季联赛子联赛信息出错:{}", e);
			}
		}
	}

	/**
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	private List<BasketBallLeagueSeasonModel> decodeLeagueSeasonFromJson(
			String jsonObject) throws Exception {
		String json = decryptionData(jsonObject);
		json = decryptionData(jsonObject);
		List<BasketBallLeagueSeasonModel> leagueSeasonModels = (List<BasketBallLeagueSeasonModel>) JsonConvertUtil
				.convertJsonToObject(json, List.class,
						BasketBallLeagueSeasonModel.class);
		return leagueSeasonModels;
	}

	@Override
	public void saveLqCupMatchAndCupScore(String jsonObject,
			String seasonEncodeJson) {
		try {
			if (StringUtils.isNotBlank(jsonObject)) {
				String decodeData = decryptionData(jsonObject);
				Map<String, Object> scoreRule1 = (Map<String, Object>) JsonConvertUtil
						.convertJsonToObject(decodeData, Map.class,
								String.class, Object.class);
				List<Map<String, String>> leagueScoreModelMap = (List<Map<String, String>>) scoreRule1
						.get(Constants.SCORE);
				List<BasketBallLeagueScoreModel> leagueScoreModels = leagueScoreModelMap != null ? BeanConvertUtil
						.makeBean(leagueScoreModelMap,
								new BasketBallLeagueScoreModel()) : null;

				Map<String, Object> basketBallMatchAndLeagueScore = new HashMap<>();
				basketBallMatchAndLeagueScore.put(Constants.SCORE,
						leagueScoreModels);
				List<Map<String, String>> scoreRuleModelMap = (List<Map<String, String>>) scoreRule1
						.get(Constants.MATCH);
				List<BasketBallMatchModel> leagueScoreRuleModels = scoreRuleModelMap != null ? BeanConvertUtil
						.makeBean(scoreRuleModelMap, new BasketBallMatchModel())
						: null;
				basketBallMatchAndLeagueScore.put(Constants.MATCH,
						leagueScoreRuleModels);
				decodeData = decryptionData(seasonEncodeJson);
				BasketBallLeagueSeasonModel basketBallLeagueSeasonModel = (BasketBallLeagueSeasonModel) JsonConvertUtil
						.convertJsonToObject(decodeData,
								BasketBallLeagueSeasonModel.class);
				lqMatchInfoDataStoreDao.storeCupMatch(
						basketBallLeagueSeasonModel,
						basketBallMatchAndLeagueScore);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("存储足球联赛积分出错{}", e);
		}
	}

	@Override
	public String queryAllJingcaiBasketMathcInlive() {
		String encodeData = "";
		try {
			List<BasketBallMatchModel> allMatchInLiveBsIds = jishiBifenDataStoreDao
					.queryAllBasketMatchInLive();
			if (allMatchInLiveBsIds != null && !allMatchInLiveBsIds.isEmpty()) {
				encodeData = JsonConvertUtil
						.convertObjectNotNullPropertiesToJsonString(allMatchInLiveBsIds);
				encodeData = encodeData(encodeData);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("查询正在比赛的竞彩篮球赛程出错:{}", e);
		}
		return encodeData;
	}

	@Override
	public void saveBasketMatchPlayerStatisticData(String jsonObject) {
		if (StringUtils.isNotBlank(jsonObject)) {
			try {
				String json = decryptionData(jsonObject);
				List<QtBasketMatchPlayerStatisticModel> qtMatchEventStatistics = (List<QtBasketMatchPlayerStatisticModel>) JsonConvertUtil
						.convertJsonToObject(json, List.class,
								QtBasketMatchPlayerStatisticModel.class);
				jishiBifenDataStoreDao
						.storeBasketMatchPlayerStatisticData(qtMatchEventStatistics);
			} catch (Exception exception) {
				log.error("更新篮球比赛球员统计信息数据出错:{}", exception);
			}
		}
	}

	@Override
	public void saveLqMatchTeamStatistic(String jsonObject) {
		if (StringUtils.isNotBlank(jsonObject)) {
			try {
				String json = decryptionData(jsonObject);
				List<QtBasketMatchTeamStatisticModel> qtMatchEventStatistics = (List<QtBasketMatchTeamStatisticModel>) JsonConvertUtil
						.convertJsonToObject(json, List.class,
								QtBasketMatchTeamStatisticModel.class);
				jishiBifenDataStoreDao
						.storeMatchTeamStatisticData(qtMatchEventStatistics);
			} catch (Exception exception) {
				log.error("更新篮球比赛球队统计信息数据出错:{}", exception);
			}
		}

	}

	@Override
	public String queryAllJingcaiLqMatchNotStart() {
		String encodeData = "";
		try {
			List<BasketBallMatchModel> jingcaiBasketMatch = jingcaiBasketMatch = jishiBifenDataStoreDao
					.queryAllJingcaiLqMatch();
			if (jingcaiBasketMatch != null && !jingcaiBasketMatch.isEmpty()) {
				encodeData = JsonConvertUtil
						.convertObjectNotNullPropertiesToJsonString(jingcaiBasketMatch);
				encodeData = encodeData(encodeData);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
		return encodeData;
	}

	@Override
	public void saveLqJishiOdds(String jsonObject, String lqMatch,
			Qt_fb_match_oddsType oddsType) {
		if (StringUtils.isNotBlank(jsonObject)) {
			try {
				String json = decryptionData(jsonObject);
				List<QtBasketMatchOddsModel> oddsModels = (List<QtBasketMatchOddsModel>) JsonConvertUtil
						.convertJsonToObject(json, List.class,
								QtBasketMatchOddsModel.class);
				String decodeMatch = decryptionData(lqMatch);
				BasketBallMatchModel matchBaseInfoPO = (BasketBallMatchModel) JsonConvertUtil
						.convertJsonToObject(decodeMatch,
								BasketBallMatchModel.class);
				//推送赔率数据到客户端
				PushOddsDataService pushOddsDataService = new PushOddsDataImpl(jishiBifenDataStoreDao);
//				pushOddsDataService.pushBbOddsMessage(oddsModels);
//				jishiBifenDataStoreDao.storeLqJishiOdds(oddsModels, oddsType,
//						matchBaseInfoPO);
			} catch (Exception exception) {
				log.error("更新篮球即时赔率数据出错:{}", exception);
			}
		}
	}

	@Override
	public String queryAllZqMatchInMatchNotHaveLiveUrl() {
		String encodeData = "";
		try {
			List<FbMatchBaseInfoPO> matchBaseInfoPOs = jishiBifenDataStoreDao
					.queryAllZqMatchInMatchNotHaveLiveUrl();
			if (matchBaseInfoPOs != null && !matchBaseInfoPOs.isEmpty()) {
				encodeData = JsonConvertUtil
						.convertObjectNotNullPropertiesToJsonString(matchBaseInfoPOs);
				encodeData = encodeData(encodeData);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询竞彩足球未获取直播地址的赛程数据出错:{}", e);
		}
		return encodeData;
	}

	@Override
	public void saveZqMatchTelevisonUrl(String id, String jsonObject) {
		// TODO Auto-generated method stub
		try {
			if (StringUtils.isNotBlank(jsonObject)) {
				String televisonUrl = decryptionData(jsonObject);
				jishiBifenDataStoreDao.saveZqLiveUrl(televisonUrl,
						Long.valueOf(id));
			}
		} catch (Throwable e) {
			log.error("保存球探足球比赛直播地址出错:", e);
		}
	}

	@Override
	public String queryJingcaiZqMatchNotStart() {
		String encodeData = "";
		try {
			List<FbMatchBaseInfoPO> matchBaseInfoPOs = jishiBifenDataStoreDao
					.queryAllJingcaiMatch();
			if (matchBaseInfoPOs != null && !matchBaseInfoPOs.isEmpty()) {
				encodeData = JsonConvertUtil
						.convertObjectNotNullPropertiesToJsonString(matchBaseInfoPOs);
			}
		} catch (Throwable e) {
			log.error("查询竞彩足球未开赛程出错:", e);
		}
		return encodeData;
	}

	@Override
	public void saveFbMatchOpOneCompany(String jsonObject, String companyId,
			Long bsId) {
		FbMatchOpOddsInfoPO matchOpOddsInfoPO = null;
		try {
			matchOpOddsInfoPO = (FbMatchOpOddsInfoPO) JsonConvertUtil.convertJsonToObject(decryptionData(jsonObject), FbMatchOpOddsInfoPO.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(matchOpOddsInfoPO!=null){
			crawlerDataStoreDao.saveFbMatchOpOneCompany(bsId,companyId,matchOpOddsInfoPO);
		}
		
	}

}
