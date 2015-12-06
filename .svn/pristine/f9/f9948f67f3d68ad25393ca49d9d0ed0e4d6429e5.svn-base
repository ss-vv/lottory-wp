package com.unison.lottery.weibo.data.crawler.service.store.pushOdds;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;  

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.eclipse.jetty.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.davcai.lottery.push.client.NewPushMessageHandlerRedisImpl;
import com.davcai.lottery.push.client.PushClientCometdImpl;
import com.davcai.lottery.push.client.PushClientException;
import com.davcai.lottery.push.client.PushResponse;
import com.davcai.lottery.push.client.PushTask;
import com.davcai.lottery.push.common.model.LotteryType;
import com.davcai.lottery.push.common.model.OddsDataMessage;
import com.davcai.lottery.push.common.model.OddsType;
import com.davcai.lottery.push.common.model.PushLiveOdds;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushResult;
import com.davcai.lottery.push.common.redis.RedisClientImpl;
import com.davcai.lottery.push.common.redis.dao.PushMessageDaoImpl;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.data.crawler.service.store.mq.CrawlerQtWeiboDataMessageSender;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.JishiBifenDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.unison.lottery.weibo.mq.OddsData;
import com.unison.lottery.weibo.mq.PushOddsMessageHandle;

/**
 * 
 * @author baoxing.peng
 *
 * @since 2015年3月6日上午11:51:48
 */
public class PushOddsDataImpl implements PushOddsDataService {

	private static final String REDIS_PORT = "redis.port";

	private static final String REDIS_URL = "redis.url";
	
	private static PushClientCometdImpl pushClient;

	private NewPushMessageHandlerRedisImpl checker;

	private PushMessageDaoImpl pushMessageDao;

	private RedisClientImpl redisClient;

	private RedisTemplate redisTemplate;
	
	private Logger log = LoggerFactory.getLogger(PushOddsDataImpl.class);

	private JishiBifenDataStoreDao jishiBifenDataStoreDao;

	private static Map<String, String> fbOpCorpId = new HashMap<>();

	private static final List<String> corpIds = new ArrayList<>();

	private static final Map<String, String> jingcaiCode = new HashMap<>();
	
	private static final String SCHEDULE_MATCH_TIME = "scheduleMatchTime";

	private static final String HALF_START_TIME = "halfStartTime";
	@Autowired
	private CrawlerQtWeiboDataMessageSender messageSender;

	public PushOddsDataImpl(JishiBifenDataStoreDao jishiBifenDataStoreDao){
		this.jishiBifenDataStoreDao = jishiBifenDataStoreDao;
		ApplicationContext context = new ClassPathXmlApplicationContext("push-live-odd-mq-service.xml");
		messageSender = (CrawlerQtWeiboDataMessageSender) context.getBean("messageSender");
		redisClient = new RedisClientImpl();
		Set<String> propertyNamesOfDate = new HashSet<>();
		propertyNamesOfDate.add(SCHEDULE_MATCH_TIME);
		propertyNamesOfDate.add(HALF_START_TIME);
		redisTemplate = new RedisTemplate(
				SystemPropertiesUtil.getPropsValue(REDIS_URL),
				Integer.valueOf(SystemPropertiesUtil.getPropsValue(REDIS_PORT)));
		pushMessageDao = new PushMessageDaoImpl();
		pushMessageDao.setRedisTemplate(redisTemplate);
		pushMessageDao.setPropertyNamesOfDate(propertyNamesOfDate);
		redisClient.setPushMessageDao(pushMessageDao);
	}
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
		//用于篮球需要推送的赔率公司校验
		corpIds.add("Macauslot");
		corpIds.add("BWin");
		corpIds.add("Bet365");
		corpIds.add("Coral");
		corpIds.add("easybets");
		corpIds.add("Expekt");
		
		corpIds.add("澳门");
		corpIds.add("易胜博");
		corpIds.add("皇冠");
		corpIds.add("bet365");
		corpIds.add("韦德");
		
		
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
		
		//篮球亚赔、大小公司
		fbOpCorpId.put("澳门","1");
		fbOpCorpId.put("易胜博","2");
		fbOpCorpId.put("皇冠", "3");
		fbOpCorpId.put("Bet365", "8");
		fbOpCorpId.put("韦德", "9");
		
		//篮球欧赔公司
		fbOpCorpId.put("Macauslot","4");
		fbOpCorpId.put("BWin","12");
		fbOpCorpId.put("bet365","8");
		fbOpCorpId.put("Coral","15");
		fbOpCorpId.put("easybets","16");
		fbOpCorpId.put("Expekt","17");

		jingcaiCode.put("周一", "1");
		jingcaiCode.put("周二", "2");
		jingcaiCode.put("周三", "3");
		jingcaiCode.put("周四", "4");
		jingcaiCode.put("周五", "5");
		jingcaiCode.put("周六", "6");
		jingcaiCode.put("周日", "7");
	}
	@Override
	public void pushFbOddsMessage(Qt_fb_match_oddsType oddsType,
			List<QtMatchOpOddsModel> oddsModels) {
		List<PushLiveOdds> pushLiveOddses = new ArrayList<>();
		OddsType pushOddsDataType = makeDataType(oddsType);

		try {
			int oldCount = 0;
			for (QtMatchOpOddsModel opOddsModel : oddsModels) {
				// 根据比赛id获取playingCodeId
				if (corpIds.contains(opOddsModel.getCorpId())) {
					FbMatchBaseInfoPO fbMatchBaseInfoPO = jishiBifenDataStoreDao
							.queryFbMatchById(opOddsModel.getQtBsId());
					if (fbMatchBaseInfoPO != null) {

						String playingCodeId = makePlayingCodeId(fbMatchBaseInfoPO.getJingcaiId(),fbMatchBaseInfoPO.getMatchTime());
						// 获取上一次推送的赔率
						String oddsData = redisClient.findFbOddsByCorpId(
								playingCodeId, opOddsModel.getCorpId(),
								pushOddsDataType);
						if (StringUtils.isNotBlank(oddsData)) {
							String[] oddses = oddsData.split(",");
							if (oddses.length >= 3) {
								// 判断是否需要推送
								if (StringUtils.equals(
										opOddsModel.getHomeWinOdds() + "",oddses[0])
										&& StringUtils.equals(opOddsModel.getDrawOdds() + "",oddses[1])
										&& StringUtils.equals(opOddsModel.getGuestWinOdds()+ "", oddses[2])) {
									oldCount++;
								} else {
									makeInitLiveOdds(pushLiveOddses, pushOddsDataType, opOddsModel, playingCodeId);
								}
							}
						} else {
							makeInitLiveOdds(pushLiveOddses, pushOddsDataType,
									opOddsModel, playingCodeId);
						}

					}
				}

			}
			if(!pushLiveOddses.isEmpty()){
				PushOddsMessageHandle pushOddsMessageHandles = new PushOddsMessageHandle();
				List<OddsData> oddsDatas = new ArrayList<OddsData>();
				pushOddsMessageHandles.setOldCount(oldCount);
				for(PushLiveOdds pushLiveOdds:pushLiveOddses){
					OddsData oddsData = new OddsData();
					oddsData.setCorpId(pushLiveOdds.getCorpId());
					oddsData.setLotteryId(pushLiveOdds.getLotteryId().toString());
					oddsData.setMatchId(pushLiveOdds.matchId);
					oddsData.setOddsType(pushLiveOdds.getOddsType().toString());
					oddsData.setData(pushLiveOdds.data.toString());
					oddsDatas.add(oddsData);
				}
				pushOddsMessageHandles.setOddsDatas(oddsDatas);
				messageSender.send(pushOddsMessageHandles);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private OddsType makeDataType(Qt_fb_match_oddsType oddsType) {
		OddsType oddsType2 = null;
		switch (oddsType) {
		case euro:
			oddsType2 = OddsType.europe;
			break;
		case asia:
			oddsType2 = OddsType.asian;
			break;
		case ou:
			oddsType2 = OddsType.bigsmall;
			break;
		default:
			break;
		}
		return oddsType2;
	}
	private void makeInitLiveOdds(List<PushLiveOdds> pushLiveOddses,
			OddsType oddsType2, QtMatchOpOddsModel opOddsModel,
			String playingCodeId) {
		PushLiveOdds liveOdds = new PushLiveOdds();
		liveOdds.setOddsType(oddsType2);
		liveOdds.setLotteryId(LotteryType.JCZQ);
		liveOdds.matchId = playingCodeId;
		OddsDataMessage data = new OddsDataMessage();
		data.setNowDrawOdds(String.valueOf(opOddsModel.getDrawOdds()));
		data.setNowLoseOdds(String.valueOf(opOddsModel.getGuestWinOdds()));
		data.setNowWinOdds(String.valueOf(opOddsModel.getHomeWinOdds()));
		data.setTime(String.valueOf((new Date()).getTime()));
		liveOdds.setCorpId(opOddsModel.getCorpId());

		liveOdds.data = data;
		redisClient.addOdds(liveOdds.matchId, data.toString(),
				opOddsModel.getCorpId(),oddsType2,LotteryType.JCZQ);
		pushLiveOddses.add(liveOdds);
	}
	private String makePlayingCodeId(String jingcaiId,Date matchTime) {
		// TODO Auto-generated method stub
		String matchTimeAfterFormat = DateFormateUtil.getStringOfDate("yyyyMMddHHmmss",
				matchTime);

		return  jingcaiCode.get(StringUtils.substring(jingcaiId, 0,
				jingcaiId.length() - 3))
		+ StringUtils.substring(jingcaiId, jingcaiId.length() - 3,jingcaiId.length())+ "-"+matchTimeAfterFormat;
	}

	@Override
	public void pushBbOddsMessage(List<QtBasketMatchOddsModel> basketMatchOddsModels,Map<String, BasketBallMatchPO> basMap) {
		List<PushLiveOdds> pushLiveOddses = new ArrayList<>();
		try {
			int oldCount = 0;
			for (QtBasketMatchOddsModel opOddsModel : basketMatchOddsModels) {
				// 根据比赛id获取playingCodeId
					String corpId = opOddsModel.getCorpId();
					BasketBallMatchPO matchBaseInfoPO = basMap.get(opOddsModel.getQtBsId());
					if(matchBaseInfoPO!=null){
						String playingCodeId = makePlayingCodeId(matchBaseInfoPO.getJingcaiId(),matchBaseInfoPO.getMatchTime());
						OddsType pushOddsDataType = makeDataType(opOddsModel.getOddsType());
						// 获取上一次推送的赔率
						String oddsData = redisClient.findBbOddsByCorpId(
								playingCodeId, corpId,
								pushOddsDataType);
						if (StringUtils.isNotBlank(oddsData)) {
							String[] oddses = oddsData.split(",");
							if (oddses.length >= 3) {
								// 判断是否需要推送
								if (StringUtils.equals(
										opOddsModel.getHomeWinOdds() + "",oddses[0])
										&& StringUtils.equals(opOddsModel.getHandicapOrScore() + "",oddses[1])
										&& StringUtils.equals(opOddsModel.getGuestWinOdds()+ "", oddses[2])) {
									oldCount++;
								} else {
									makeBbOddsPushData(pushLiveOddses,
											pushOddsDataType, opOddsModel,
											playingCodeId,corpId);
								}
							}
						}else {
							makeBbOddsPushData(pushLiveOddses, pushOddsDataType, opOddsModel, playingCodeId,corpId);
						}
					}
			}
			PushOddsMessageHandle pushOddsMessageHandles = new PushOddsMessageHandle();
			List<OddsData> oddsDatas = new ArrayList<OddsData>();
			pushOddsMessageHandles.setOldCount(oldCount);
			for(PushLiveOdds pushLiveOdds:pushLiveOddses){
				OddsData oddsData = new OddsData();
				oddsData.setCorpId(pushLiveOdds.getCorpId());
				oddsData.setLotteryId(pushLiveOdds.getLotteryId().toString());
				oddsData.setMatchId(pushLiveOdds.matchId);
				oddsData.setOddsType(pushLiveOdds.getOddsType().toString());
				oddsData.setData(pushLiveOdds.data.toString());
				oddsDatas.add(oddsData);
			}
			pushOddsMessageHandles.setOddsDatas(oddsDatas);
			messageSender.send(pushOddsMessageHandles);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("推送数据出错:{}", e);
		}
	}
	
	private void makeBbOddsPushData(List<PushLiveOdds> pushLiveOddses,
			OddsType pushOddsDataType, QtBasketMatchOddsModel opOddsModel,
			String playingCodeId,String corpId) {
		PushLiveOdds liveOdds = new PushLiveOdds();
		liveOdds.setOddsType(pushOddsDataType);
		liveOdds.setLotteryId(LotteryType.JCLQ);
		liveOdds.matchId = playingCodeId;
		OddsDataMessage data = new OddsDataMessage();
		data.setNowDrawOdds(String.valueOf(opOddsModel.getHandicapOrScore()));
		data.setNowLoseOdds(String.valueOf(opOddsModel.getGuestWinOdds()));
		data.setNowWinOdds(String.valueOf(opOddsModel.getHomeWinOdds()));
		data.setTime(String.valueOf(opOddsModel.getTimestamp().getTime()));
		liveOdds.setCorpId(corpId);
		liveOdds.data = data;
		// 存入redis
		redisClient.addOdds(playingCodeId,data.toString(),corpId,pushOddsDataType,LotteryType.JCLQ);

		pushLiveOddses.add(liveOdds);
	}
	public CrawlerQtWeiboDataMessageSender getMessageSender() {
		return messageSender;
	}
	public void setMessageSender(CrawlerQtWeiboDataMessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
}
