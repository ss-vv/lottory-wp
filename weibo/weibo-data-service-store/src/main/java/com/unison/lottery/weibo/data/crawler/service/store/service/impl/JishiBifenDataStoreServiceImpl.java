package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.davcai.lottery.push.client.NewPushMessageHandlerRedisImpl;
import com.davcai.lottery.push.client.PushClientCometdImpl;
import com.davcai.lottery.push.client.PushClientException;
import com.davcai.lottery.push.client.PushResponse;
import com.davcai.lottery.push.client.PushTask;
import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushResult;
import com.davcai.lottery.push.common.redis.RedisClientImpl;
import com.davcai.lottery.push.common.redis.dao.PushMessageDaoImpl;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.JishiBifenDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.ConnectionPool;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.JishiBifenDataStoreDaoImpl;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataImpl;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataService;
import com.unison.lottery.weibo.data.crawler.service.store.service.JishiBifenDataStoreService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi_lq.Lq_FenXi;
import com.unison.lottery.weibo.dataservice.crawler.parse.index.i;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u.c;
import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseServiceImpl;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;

/**
 * 
 * @author 彭保星
 *
 * @since 2014年12月1日下午5:29:29
 */
public class JishiBifenDataStoreServiceImpl implements
		JishiBifenDataStoreService {

	private static final String REDIS_PORT = "redis.port";

	private static final String REDIS_URL = "redis.url";

	private static final String HALF_START_TIME = "halfStartTime";

	private static final String SCHEDULE_MATCH_TIME = "scheduleMatchTime";

	private static final String PUSH_SERVER_URL = "pushServerUrl";

	private static final long KEEPALIVETIME = 700;

	private static final List<String> corpIds = new ArrayList<>();

	private static final int MAX_POOL_SIZE = 16;

	private static final int CORE_POOL_SIZE = 10;

	private static final String PUSH_HX_SERVER_URL = "pushHXServerUrl";

	private Logger log = LoggerFactory
			.getLogger(JishiBifenDataStoreServiceImpl.class);

	private JishiBifenDataStoreDao jishiBifenDataStoreDao;

	private static Map<String, String> header;

	private JishiDataParseService jishiDataParseService;

	private PushClientCometdImpl pushClient;

	private NewPushMessageHandlerRedisImpl checker;

	private PushMessageDaoImpl pushMessageDao;

	private RedisClientImpl redisClient;

	private RedisTemplate redisTemplate;


	static {
		// 抓取篮球赔率数据前，公司id的初始化
		corpIds.add("1");
		corpIds.add("2");
		corpIds.add("3");
		corpIds.add("8");
		corpIds.add("9");
	}

	public JishiBifenDataStoreServiceImpl() {
		jishiBifenDataStoreDao = new JishiBifenDataStoreDaoImpl();
		header = new HashMap<>();
		jishiDataParseService = new JishiDataParseServiceImpl();
	}

	public JishiBifenDataStoreServiceImpl(String parameter) {
		header = new HashMap<>();
		jishiDataParseService = new JishiDataParseServiceImpl();
	}

	@Override
	public void crawlerQtJishiBifen() {
		log.info("抓取球探足球即时比分数据...");

		try {

			List<QtMatchBaseModel> matchBaseInfos = null;
			do {
				if (matchBaseInfos == null) {
					makeHeader();
				}
				matchBaseInfos = jishiDataParseService
						.getQtFbJishiBifenData(header);
			} while (matchBaseInfos == null);

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
			log.info("即时比分数据抓取完成...");
		} catch (Exception e) {
			log.error("即时比分数据抓取出错:{}", e);
		}
		System.exit(0);
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
					.valueOf(qtMatchBaseModel.getGuestTeamPosition()));
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
			Integer state = qtMatchBaseModel.getMatchStatus();
			String time = null;
//			Integer puShiTime = null;
			if(state == 1){
				time = makeMinutes(qtMatchBaseModel,0);
			} else if(state == 3){
				time = makeMinutes(qtMatchBaseModel, 45);
			} else if(state == 4){
				time = makeMinutes(qtMatchBaseModel, 90);
			}else {
				SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("HH:mm");
				time = simpleDateFormat.format(qtMatchBaseModel.getMatchTime());
			}
			footballMatchMessage.setMatchTime(state == 2 ? "-1" : time);
			footballMatchMessage.setMatchCode(qtMatchBaseModel.getJingcaiId());
			footballMatchMessage.setLiveContent(qtMatchBaseModel.getLiveContent() == null ? "": qtMatchBaseModel.getLiveContent());
			footballMatchMessages.add(footballMatchMessage);
		}
		createPushClient("football");
		PushResult pushResult = pushClient.pushMessages(footballMatchMessages);
		PushResponse pushResponse = (PushResponse) pushResult;
		if (null != pushResponse) {
			if (null != pushResponse.pushToComted) {
				log.info("总共往cometd推送的数目{},推送结果:成功{}个，无需更新{}个,失败{}个",
						footballMatchMessages.size(),
						pushResponse.pushToComted.getCountOfSucc(),
						pushResponse.pushToComted.getCountOfOld(),
						pushResponse.pushToComted.getCountOfFail());
			}
		} else {
			log.error("推送失败");
		}
	}

	private String makeMinutes(QtMatchBaseModel qtMatchBaseModel, int minutes) {
		String time;
		if(qtMatchBaseModel.getHalfStartTime() != null){
			time = String.valueOf(((new Date().getTime() - qtMatchBaseModel.getHalfStartTime().getTime()))/(60*1000)+minutes);
		}else{
			time = String.valueOf(((new Date().getTime() - qtMatchBaseModel.getMatchTime().getTime()))/(60*1000));
		}
		return time;
	}

	/**
	 * @param type 
	 * 
	 */
	private void createPushClient(String type) {
		pushClient = new PushClientCometdImpl();
		checker = new NewPushMessageHandlerRedisImpl();
		redisClient = new RedisClientImpl();
		Set<String> propertyNamesOfDate = new HashSet<>();
		propertyNamesOfDate.add(SCHEDULE_MATCH_TIME);
		propertyNamesOfDate.add(HALF_START_TIME);
		if(StringUtils.equals(type, "basketBall")){
			propertyNamesOfDate.add("matchTime");
		}
		redisTemplate = new RedisTemplate(
				SystemPropertiesUtil.getPropsValue(REDIS_URL),
				Integer.valueOf(SystemPropertiesUtil.getPropsValue(REDIS_PORT)));
		pushMessageDao = new PushMessageDaoImpl();
		pushMessageDao.setRedisTemplate(redisTemplate);
		pushMessageDao.setPropertyNamesOfDate(propertyNamesOfDate);
		redisClient.setPushMessageDao(pushMessageDao);
		checker.setRedisClient(redisClient);
		HttpClient jettyHttpClient = new org.eclipse.jetty.client.HttpClient();
//		BasicHttpParams httpParameters = new BasicHttpParams();
//		HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
//		HttpConnectionParams.setSoTimeout(httpParameters, 30000);
//		PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
//		DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager,
//				httpParameters);
		PushTask pushTask = new PushTask();
//		pushTask.setHttpClient(httpClient);
		pushTask.setJettyHttpClient(jettyHttpClient);
		pushTask.setAppId(SystemPropertiesUtil.getPropsValue("appID"));
		pushTask.setHost(SystemPropertiesUtil.getPropsValue("ge.tui"));
		pushTask.setAppkey(SystemPropertiesUtil.getPropsValue("appKey"));
		pushTask.setMaster(SystemPropertiesUtil.getPropsValue("masterSecret"));
		pushTask.setPushHXServerUrl(SystemPropertiesUtil
				.getPropsValue(PUSH_HX_SERVER_URL));
		pushTask.setPushServerUrl(SystemPropertiesUtil
				.getPropsValue(PUSH_SERVER_URL));
		pushClient.setPushTask(pushTask);
		pushClient.setNewPushMessageHandler(checker);
	}

	private String makeMatchId(Object qtMatchBaseModel) {
		// TODO Auto-generated method stub
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

	private void makeHeader() {
		// TODO Auto-generated method stub
		if (header == null) {
			header = new HashMap<String, String>();
		}
		UserAgentPO userAgentPO = null;
		try {

			userAgentPO = jishiBifenDataStoreDao.getRandomHeader();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (userAgentPO != null) {
			// 生成ua
			String user_agent = String.format(
					"Score (%s; %s)",
					new Object[] { userAgentPO.getSystemVersion(),
							userAgentPO.getPhoneType() });
			header.put("User-Agent", user_agent);
		}
	}

	@Override
	public void crawlerQtLqJishiBifen() {
		log.info("抓取球探篮球竞彩即时比分数据开始!");
		try {
			List<BasketBallMatchModel> ballMatchModels = null;
			do {
				if (ballMatchModels == null) {
					makeHeader();
				}
				ballMatchModels = jishiDataParseService
						.crawlerBaskerBallJingCaiBifen(header);
			} while (ballMatchModels == null);
			if (!ballMatchModels.isEmpty()) {
				// 推送比分数据
				pushBasketBallMatch(ballMatchModels);
				jishiBifenDataStoreDao.storeLqJishi(ballMatchModels, true);
			}

			log.info("篮球竞彩即时数据抓取完成!");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("抓取球探篮球竞彩即时比分数据出错：{}", e);
		}
		System.exit(0);
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
			basketballMatchMessage.setMatchCode(ballMatchModel.getJingcaiId());
			basketballMatchMessage.setMatchTime(ballMatchModel.getMatchTime());
			basketballMatchMessage.setRemainTime(ballMatchModel.getRemainTime());
			pushMessages.add(basketballMatchMessage);
			log.debug(pushMessages.toString());
		}
		createPushClient("basketBall");
		PushResult pushResult = pushClient.pushMessages(pushMessages);
		PushResponse pushResponse = (PushResponse) pushResult;
		if (null != pushResponse) {
			if (null != pushResponse.pushToComted) {
				log.info("总共往cometd推送的数目{},推送结果:成功{}个，无需更新{}个,失败{}个",
						ballMatchModels.size(),
						pushResponse.pushToComted.getCountOfSucc(),
						pushResponse.pushToComted.getCountOfOld(),
						pushResponse.pushToComted.getCountOfFail());
			}
			if (null != pushResponse.pushToGeTui) {
				log.info("总共往个推推送的数目{},推送结果:成功{}个，无需更新{}个,失败{}个",
						ballMatchModels.size(),
						pushResponse.pushToGeTui.getCountOfSucc(),
						pushResponse.pushToGeTui.getCountOfOld(),
						pushResponse.pushToGeTui.getCountOfFail());
			}

		} else {
			log.error("推送失败");
		}

	}

	/**
	 * 抓取比赛欧赔
	 */
	public void crawlerFbMatchOdds(Qt_fb_match_oddsType oddsType) {
		log.info("抓取当前赛季球探竞彩足球比赛赔率数据开始......");
		try {
			List<FbMatchBaseInfoPO> jingcaiMatch = jishiBifenDataStoreDao
					.queryAllJingcaiMatch();
			if (jingcaiMatch != null) {
				if (!jingcaiMatch.isEmpty()) {
					storeOneMatchCompanyOpOdds(jingcaiMatch, oddsType);
				}
			} else {
				log.info("不存在要抓取比赛欧赔数据!");
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
	 * @author baoxing.peng
	 */
	private void storeOneMatchCompanyOpOdds(
			List<FbMatchBaseInfoPO> allMatchHasFinishBsIds,
			Qt_fb_match_oddsType oddsType) throws Exception {
		// 创建一个线程池
		ExecutorService pool = new ThreadPoolExecutor(CORE_POOL_SIZE,
				MAX_POOL_SIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
				new LinkedBlockingDeque<Runnable>());
		for (FbMatchBaseInfoPO matchBaseInfoPO : allMatchHasFinishBsIds) {// 循环抓取赔率数据

			CrawlerZqJingcaiOddsUseThread jingcaiOdds = new CrawlerZqJingcaiOddsUseThread(
					oddsType, matchBaseInfoPO);
			pool.execute(jingcaiOdds);
		}
		pool.shutdown();

	}

	@Override
	public void crawlerLqMatchOdds(Qt_fb_match_oddsType oddsType) {
		log.info("抓取当前赛季球探竞彩足球比赛赔率数据开始......");
		try {
			// 创建一个线程池
			ExecutorService executorService = Executors
					.newFixedThreadPool(corpIds.size());
			List<CrawlerLqJingcaiOddsUseThread> lqJingcaiOddsUseThreads = new ArrayList<CrawlerLqJingcaiOddsUseThread>();
			// 读取上一次抓取的赔率内容，并清空文件
			CrawlerLqJingcaiOddsUseThread.lastReponse = getTheLastResponseFromFile(oddsType);
			for (String corpId : corpIds) {
				CrawlerLqJingcaiOddsUseThread lqJingcaiOddsUseThread = new CrawlerLqJingcaiOddsUseThread(corpId);
				lqJingcaiOddsUseThreads.add(lqJingcaiOddsUseThread);
			}
			executorService.invokeAll(lqJingcaiOddsUseThreads);
			System.exit(0);
		} catch (Throwable exception) {
			log.error("抓取球探比赛赔率数据出错:{}", exception);
		} finally {
			ConnectionPool.getInstance().closePool();
		}
	}

	/**
	 * @param responseStr
	 * @param oddsType
	 * @return
	 * @throws IOException
	 */
	private String getTheLastResponseFromFile(Qt_fb_match_oddsType oddsType) {
		String fileName = "";
		if (oddsType == Qt_fb_match_oddsType.euro) {
			fileName = SystemPropertiesUtil.getPropsValue("lqEuroFile");
		} else if (oddsType == Qt_fb_match_oddsType.asia) {
			fileName = SystemPropertiesUtil.getPropsValue("lqAsianFile");
		} else if (oddsType == Qt_fb_match_oddsType.ou) {
			fileName = SystemPropertiesUtil.getPropsValue("lqOuFile");
		} else if( oddsType == Qt_fb_match_oddsType.odds){
			fileName = SystemPropertiesUtil.getPropsValue("lqOddsFile");
		}
		File file = FileUtils.getFile(Lq_FenXi.class.getResource("/").getPath()
				+ fileName);
		String content = "";

		try {
			if (file.exists()) {
				content = FileUtils.readFileToString(file);
			} else {
				file.createNewFile();
			}
			FileUtils.write(file, "", false); // 清空文件内容
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	@Override
	public void crawlerFbMatchEventDataAndStore() {
		log.info("抓取球探对阵即时事件数据开始......");
		try {
			List<FbMatchBaseInfoPO> allMatchInLiveBsIds = jishiBifenDataStoreDao
					.queryAllZqMatchInLive();

			if (allMatchInLiveBsIds != null && !allMatchInLiveBsIds.isEmpty()) {
				ExecutorService pool = new ThreadPoolExecutor(CORE_POOL_SIZE,
						MAX_POOL_SIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
						new LinkedBlockingDeque<Runnable>());
				for (FbMatchBaseInfoPO matchBaseInfoPO : allMatchInLiveBsIds) {
					ZqJishiEventUseThread zqJishiEventUseThread = new ZqJishiEventUseThread(
							matchBaseInfoPO);
					pool.execute(zqJishiEventUseThread);
				}
				pool.shutdown();
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} else {
				log.info("不存在要抓取赛事事件历史数据!");
			}
			log.info("球探对阵即时事件数据抓取完成");
		} catch (Throwable exception) {
			log.error("抓取球探赛事事件数据出错:{}", exception);
		}
	}

	@Override
	public void crawlerBasketMatchPlayerStatistic() {
		log.info("抓取球探篮球赛事球员统计开始......");
		try {
			List<BasketBallMatchModel> allMatchInLiveBsIds = jishiBifenDataStoreDao
					.queryAllBasketMatchInLive();
			if (allMatchInLiveBsIds != null && !allMatchInLiveBsIds.isEmpty()) {

				ExecutorService pool = new ThreadPoolExecutor(CORE_POOL_SIZE,
						MAX_POOL_SIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
						new LinkedBlockingDeque<Runnable>());
				for (BasketBallMatchModel matchModel : allMatchInLiveBsIds) {
					LqJishiStatisticsUseThread lqJishiStatisticsUseThread = new LqJishiStatisticsUseThread(
							matchModel);
					pool.execute(lqJishiStatisticsUseThread);
				}
				pool.shutdown();
			} else {
				log.info("不存在要抓取球探篮球赛事球队统计数据!");
			}
		} catch (Throwable exception) {
			log.error("篮球赛事球员统计抓取出错:", exception);
		}
	}

	@Override
	public void crawlerBasketMatchTeamStatistic() {
		log.info("抓取球探篮球赛事球队统计开始......");
		try {
			List<BasketBallMatchModel> allMatchHasFinishBsIds = jishiBifenDataStoreDao
					.queryAllBasketMatchInLive();
			if (allMatchHasFinishBsIds != null
					&& !allMatchHasFinishBsIds.isEmpty()) {
				ExecutorService pool = new ThreadPoolExecutor(CORE_POOL_SIZE,
						MAX_POOL_SIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
						new LinkedBlockingDeque<Runnable>());
				for (BasketBallMatchModel matchBaseInfoPO : allMatchHasFinishBsIds) {
					LqJishiTeamStatisticUseThread jishiTeam = new LqJishiTeamStatisticUseThread(
							matchBaseInfoPO);
					pool.execute(jishiTeam);
				}
				pool.shutdown();
			} else {
				log.info("不存在要抓取球探篮球赛事球队统计数据!");
			}
		} catch (Exception exception) {
			log.error("抓取球探篮球赛事球队统计数据出错:{}", exception);
		}
	}

	@Override
	public void crawlerOddsChange(Qt_fb_match_oddsType oddsType) {
		log.info("抓取球探竞彩足球即时变化赔率数据开始!");
		try {
			List<QtMatchOpOddsModel> oddsModels = null;
			int i = 0;
			do {
				if (oddsModels == null) {
					makeHeader();
				}
				oddsModels = jishiDataParseService.crawlerChangeOdds(header,
						oddsType);
				i++;
			} while (oddsModels == null && i <= 10);
			if (!oddsModels.isEmpty()) {
				pushOddsMessage(oddsType, oddsModels);
				jishiBifenDataStoreDao.storeZqJishiOdds(oddsModels,
							oddsType);
			}

			log.info("球探足球即时赔率抓取完成!");
		} catch (Throwable e) {
			// TODO: handle exception
			log.error("抓取球探篮球竞彩即时比分数据出错：{}", e);
		}
		System.exit(0);

	}

	public void pushOddsMessage(Qt_fb_match_oddsType oddsType,
			List<QtMatchOpOddsModel> oddsModels) {
		PushOddsDataService pushOddsDataService = new PushOddsDataImpl(jishiBifenDataStoreDao);
		pushOddsDataService.pushFbOddsMessage(oddsType, oddsModels);
	}
	@Override
	public void crawlerQtFbJishiBifenAndSend() {
		log.info("抓取球探足球即时比分数据...");

		try {

			List<QtMatchBaseModel> matchBaseInfos = null;
			do {
				if (matchBaseInfos == null) {
					makeHeaderFromFile();
				}
				matchBaseInfos = jishiDataParseService
						.getQtFbJishiBifenData(header);
			} while (matchBaseInfos == null);
			if (!matchBaseInfos.isEmpty()) {
				jishiDataParseService.sendZqJishiBifenData(matchBaseInfos);
			}
			log.info("即时比分数据抓取完成...");
		} catch (Exception e) {
			log.error("即时比分数据抓取出错:{}", e);
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
	public void crawlerQtLqJingcaiJishiBifenAndSend() {
		log.info("抓取球探篮球竞彩即时比分数据开始!");
		try {
			List<BasketBallMatchModel> ballMatchModels = null;
			do {
				if (ballMatchModels == null) {
					makeHeaderFromFile();
				}
				ballMatchModels = jishiDataParseService
						.crawlerBaskerBallJingCaiBifen(header);
			} while (ballMatchModels == null);
			if (!ballMatchModels.isEmpty()) {
				// 推送比分数据
				jishiDataParseService.sendLqjingcaiJishi(ballMatchModels);
			}

			log.info("篮球竞彩即时数据抓取完成!");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("抓取球探篮球竞彩即时比分数据出错：{}", e);
		}
		System.exit(0);
	}

	@Override
	public void crawlerOddsChangeAndSend(Qt_fb_match_oddsType oddsType) {
		log.info("抓取球探竞彩足球即时变化赔率数据开始!");
		try {
			List<QtMatchOpOddsModel> oddsModels = null;
			int i = 0;
			do {
				if (oddsModels == null) {
					makeHeaderFromFile();
				}
				oddsModels = jishiDataParseService.crawlerChangeOdds(header,
						oddsType);
			} while (oddsModels == null && i <= 10);
			if (!oddsModels.isEmpty()) {
				jishiDataParseService.sendZqJishiOdds(oddsModels, oddsType);
			}

			log.info("球探足球即时赔率抓取完成!");
		} catch (Throwable e) {
			// TODO: handle exception
			log.error("抓取球探篮球竞彩即时比分数据出错：{}", e);
		}
		System.exit(0);
	}

	@Override
	public void crawlerFbJishiEventsAndSend() {
		log.info("抓取球探对阵即时事件数据开始......");
		try {
			List<FbMatchBaseInfoPO> allMatchInLiveBsIds = jishiDataParseService
					.gotAllJingcaiZqMatchInLive();

			if (allMatchInLiveBsIds != null && !allMatchInLiveBsIds.isEmpty()) {
				ExecutorService pool = new ThreadPoolExecutor(CORE_POOL_SIZE,
						MAX_POOL_SIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
						new LinkedBlockingDeque<Runnable>());
				for (FbMatchBaseInfoPO matchBaseInfoPO : allMatchInLiveBsIds) {
					ZqJishiEventUseThreadAndSend zqJishiEventUseThread = new ZqJishiEventUseThreadAndSend(
							matchBaseInfoPO);
					pool.execute(zqJishiEventUseThread);
				}
				pool.shutdown();
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} else {
				log.info("不存在要抓取赛事事件历史数据!");
			}
			log.info("球探对阵即时事件数据抓取完成");
		} catch (Throwable exception) {
			log.error("抓取球探赛事事件数据出错:{}", exception);
		} finally {
			ConnectionPool.getInstance().closePool();
		}

	}

	@Override
	public void crawlerBasketMatchPlayerStatisticAndSend() {
		log.info("抓取球探篮球赛事球员统计开始......");
		try {
			List<BasketBallMatchModel> allMatchInLiveBsIds = jishiDataParseService
					.gotAllBasketMathcInlive();
			if (allMatchInLiveBsIds != null && !allMatchInLiveBsIds.isEmpty()) {

				ExecutorService pool = new ThreadPoolExecutor(CORE_POOL_SIZE,
						MAX_POOL_SIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
						new LinkedBlockingDeque<Runnable>());
				for (BasketBallMatchModel matchModel : allMatchInLiveBsIds) {
					LqJishiStatisticsUseThreadAndSend lqJishiStatisticsUseThread = new LqJishiStatisticsUseThreadAndSend(
							matchModel);
					pool.execute(lqJishiStatisticsUseThread);
				}
				pool.shutdown();
			} else {
				log.info("不存在要抓取球探篮球赛事球队统计数据!");
			}
		} catch (Throwable exception) {
			log.error("篮球赛事球员统计抓取出错:", exception);
		}
	}

	@Override
	public void crawlerBasketMatchTeamStatisticAndSend() {
		log.info("抓取球探篮球赛事球队统计开始......");
		try {
			List<BasketBallMatchModel> allMatchHasFinishBsIds = jishiDataParseService
					.gotAllBasketMathcInlive();
			if (allMatchHasFinishBsIds != null
					&& !allMatchHasFinishBsIds.isEmpty()) {
				ExecutorService pool = new ThreadPoolExecutor(CORE_POOL_SIZE,
						MAX_POOL_SIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
						new LinkedBlockingDeque<Runnable>());
				for (BasketBallMatchModel matchBaseInfoPO : allMatchHasFinishBsIds) {
					LqJishiTeamStatisticUseThreadAndSend jishiTeam = new LqJishiTeamStatisticUseThreadAndSend(
							matchBaseInfoPO);
					pool.execute(jishiTeam);
				}
				pool.shutdown();
			} else {
				log.info("不存在要抓取球探篮球赛事球队统计数据!");
			}
		} catch (Exception exception) {
			log.error("抓取球探篮球赛事球队统计数据出错:{}", exception);
		}
	}

	@Override
	public void crawlerLqMatchOddsAndSend(Qt_fb_match_oddsType oddsType) {
		log.info("抓取当前赛季球探竞彩篮球比赛赔率数据开始......");

		List<BasketBallMatchModel> jingcaiBasketMatch = null;
		try {
			jingcaiBasketMatch = jishiDataParseService
					.gotAllJingcaiLqMatchNotStart();
			if (jingcaiBasketMatch != null && !jingcaiBasketMatch.isEmpty()) {
				storeOneLqMatchCompanyOpOddsAndSend(jingcaiBasketMatch,
						oddsType);
			} else {
				log.info("不存在要抓取比赛欧赔数据!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("抓取球探比赛赔率数据出错:{}", e);
		} finally {
			ConnectionPool.getInstance().closePool();
		}

	}

	private void storeOneLqMatchCompanyOpOddsAndSend(
			List<BasketBallMatchModel> allBasketMatch,
			Qt_fb_match_oddsType oddsType) throws InterruptedException {
		// 创建一个线程池
		ExecutorService pool = new ThreadPoolExecutor(CORE_POOL_SIZE,
				MAX_POOL_SIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
				new LinkedBlockingDeque<Runnable>());
		// 读取上一次抓取的赔率内容，并清空文件
		CrawlerLqJingcaiOddsUseThreadAndSend.lastReponse = getTheLastResponseFromFile(oddsType);
		for (BasketBallMatchModel basketBallMatchModel : allBasketMatch) {
			CrawlerLqJingcaiOddsUseThreadAndSend lqJingcaiOddsUseThread = new CrawlerLqJingcaiOddsUseThreadAndSend(
					oddsType, basketBallMatchModel);
			pool.execute(lqJingcaiOddsUseThread);
		}
		pool.shutdown();
		pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		log.info("比赛{}数据抓取完成，共抓取{}场比赛的数据", oddsType, allBasketMatch.size());

	}

	@Override
	public void crawlerZqMatchLiveAndSend() {
		try {
			List<FbMatchBaseInfoPO> qtBaseInfoPOs = jishiDataParseService
					.gotAllZqMatchInMatchNotHaveLiveUrl();
			if (qtBaseInfoPOs != null && qtBaseInfoPOs.size() > 0) {
				for (FbMatchBaseInfoPO baseInfoPO : qtBaseInfoPOs) {
					int i = 0;
					String televisonUrl = null;
					do {
						if (televisonUrl == null) {
							makeHeaderFromFile();
						}
						televisonUrl = jishiDataParseService.crawlerZqLiveUrl(
								baseInfoPO.getBsId(), header);
					} while (i < MAX_POOL_SIZE && televisonUrl == null);
					if (StringUtils.isNotBlank(televisonUrl)) {
						jishiDataParseService.sendZqLiveUrl(televisonUrl,
								baseInfoPO.getId());
					}
				}
			}
		} catch (Throwable e) {
			log.error("抓取球探足球比赛直播地址出错:", e);
		}
	}

	@Override
	public void crawlerZqMatchLiveAndStore() {
		// TODO Auto-generated method stub
		try {
			List<FbMatchBaseInfoPO> qtBaseInfoPOs = jishiBifenDataStoreDao
					.queryAllZqMatchInMatchNotHaveLiveUrl();
			if (qtBaseInfoPOs != null && qtBaseInfoPOs.size() > 0) {
				for (FbMatchBaseInfoPO baseInfoPO : qtBaseInfoPOs) {
					int i = 0;
					String televisonUrl = null;
					do {
						if (televisonUrl == null) {
							makeHeaderFromFile();
						}
						televisonUrl = jishiDataParseService.crawlerZqLiveUrl(
								baseInfoPO.getBsId(), header);
					} while (i < MAX_POOL_SIZE && televisonUrl == null);
					if (StringUtils.isNotBlank(televisonUrl)) {
						jishiBifenDataStoreDao.saveZqLiveUrl(televisonUrl,
								baseInfoPO.getId());
					}
				}
			}
		} catch (Throwable e) {
			log.error("抓取球探足球比赛直播地址出错:", e);
		}
	}

}
