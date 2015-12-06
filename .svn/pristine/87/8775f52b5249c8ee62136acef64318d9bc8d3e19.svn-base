package com.unison.lottery.weibo.data.crawler.service.store.pushOdds;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.mq.OddsData;
import com.unison.lottery.weibo.mq.PushOddsMessageHandle;
import com.xhcms.commons.mq.MessageHandler;

public class PushLqLiveOddsFromActivemq implements
	MessageHandler<PushOddsMessageHandle> {

	private static final String REDIS_PORT = "redis.port";

	private static final String REDIS_URL = "redis.url";
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
	
	private static final String PUSH_SERVER_URL = "pushServerUrl";
	
	private Logger log = LoggerFactory.getLogger(PushOddsDataImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1981818659055404656L;

	@Override
	public void handle(PushOddsMessageHandle pushOddsMessageHandle) {
		// TODO Auto-generated method stub
		if(pushOddsMessageHandle!=null){
			int oldCount = pushOddsMessageHandle.getOldCount();
			if(pushOddsMessageHandle.getOddsDatas()!=null&&!pushOddsMessageHandle.getOddsDatas().isEmpty()){
					List<PushMessage> pushLiveOddses = new ArrayList<PushMessage>();
					for(OddsData oddsData:pushOddsMessageHandle.getOddsDatas()){
						PushLiveOdds pushLiveOdds = new PushLiveOdds();
						pushLiveOdds.setCorpId(oddsData.getCorpId());
						pushLiveOdds.setLotteryId(oddsData.getLotteryId().equals(LotteryType.JCLQ.toString())?LotteryType.JCLQ:LotteryType.JCZQ);
						pushLiveOdds.setOddsType(oddsData.getOddsType().equals(OddsType.europe.toString())?OddsType.europe:
								oddsData.getOddsType().equals(OddsType.bigsmall.toString())?
										OddsType.bigsmall:OddsType.asian);
						pushLiveOdds.data = new OddsDataMessage();
						String[] data = oddsData.getData().split(",",-1);
						pushLiveOdds.data.setNowWinOdds(data[0]);
						pushLiveOdds.data.setNowDrawOdds(data[1]);
						pushLiveOdds.data.setNowLoseOdds(data[2]);
						pushLiveOdds.matchId = oddsData.getMatchId();
						pushLiveOddses.add(pushLiveOdds);
					}
					PushResult result = null;
					createPushTask();
					try {
						result = pushClient.pushOddsMessages(pushLiveOddses, oldCount);
					} catch (PushClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PushResponse pushResponse = (PushResponse) result;
					if (null != pushResponse) {
						if (null != pushResponse.pushToComted) {
							log.info("总共往cometd推送的赔率的数目{},推送结果:成功{}个，无需更新{}个,失败{}个",
									pushLiveOddses.size(),
									pushResponse.pushToComted.getCountOfSucc(),
									oldCount,
									pushResponse.pushToComted.getCountOfFail());
						}
						if(null!=pushResponse.pushToGeTui){
							log.info("总共往个推推送的赔率的数目{},推送结果:成功{}个，无需更新{}个,失败{}个",
									pushLiveOddses.size(),
									pushResponse.pushToGeTui.getCountOfSucc(),
									oldCount,
									pushResponse.pushToGeTui.getCountOfFail());
						}
		
					} else {
						log.error("推送失败");
					}
				}
		}
		
	}

	private void createPushTask() {
		// TODO Auto-generated method stub
		PushTask pushTask = new PushTask();
//		pushTask.setHttpClient(httpClient);
//		pushTask.setJettyHttpClient(jettyHttpClient);
		pushTask.setAppId(SystemPropertiesUtil.getPropsValue("appID"));
		pushTask.setHost(SystemPropertiesUtil.getPropsValue("ge.tui"));
		pushTask.setAppkey(SystemPropertiesUtil.getPropsValue("appKey"));
		pushTask.setMaster(SystemPropertiesUtil.getPropsValue("masterSecret"));
//		pushTask.setPushHXServerUrl(SystemPropertiesUtil
//				.getPropsValue(PUSH_HX_SERVER_URL));
		pushTask.setPushServerUrl(SystemPropertiesUtil
				.getPropsValue(PUSH_SERVER_URL));
		pushClient.setPushTask(pushTask);
	}

	public PushClientCometdImpl getPushClient() {
		return pushClient;
	}

	public void setPushClient(PushClientCometdImpl pushClient) {
		this.pushClient = pushClient;
	}

	public NewPushMessageHandlerRedisImpl getChecker() {
		return checker;
	}

	public void setChecker(NewPushMessageHandlerRedisImpl checker) {
		this.checker = checker;
	}

	public PushMessageDaoImpl getPushMessageDao() {
		return pushMessageDao;
	}

	public void setPushMessageDao(PushMessageDaoImpl pushMessageDao) {
		this.pushMessageDao = pushMessageDao;
	}

	public RedisClientImpl getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClientImpl redisClient) {
		this.redisClient = redisClient;
	}

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public static String getRedisPort() {
		return REDIS_PORT;
	}

	public static String getRedisUrl() {
		return REDIS_URL;
	}
	
	

}
