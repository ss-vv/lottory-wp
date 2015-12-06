package com.davcai.lottery.push.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.MatchMessage;
import com.davcai.lottery.push.common.model.MessageType;
import com.davcai.lottery.push.common.model.OddsDataMessage;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushMessageFactory;
import com.davcai.lottery.push.common.odds.service.JCZQOddsMessageService;
import com.davcai.lottery.push.common.redis.RedisClient;

import freemarker.cache.StringTemplateLoader;

@Named
@Singleton
@Service("QueryMatchInfoService")
public class QueryMatchInfoService {
	@Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;
    
    @Autowired
    private RedisClient redisClient;
    
    @Autowired
    private JCZQOddsMessageService oddsService;
	private Logger logger=LoggerFactory.getLogger(getClass());
    
    @PostConstruct
    public void init()
    {
        logger.info("QueryMatchInfoService初始化");
    }
    
    @Listener("/meta/subscribe")
    public void queryMatch(ServerSession remote, ServerMessage.Mutable message){
    	String channel=message.getChannel();
    	logger.info("获取到订阅channel:{}的消息:{}",channel,message.toString());
    	
		String matchId=(String) message.get("matchId");
		logger.info("matchId="+matchId);
		String matchType=(String) message.get("matchType");
		logger.info("matchType="+matchType);
		String actionType=(String) message.get("actionType");
		logger.info("actionType="+actionType);
		if(StringUtils.equals(actionType, "init")&&StringUtils.isNotBlank(matchId)&&StringUtils.isNotBlank(matchType)){//是初始化查询比赛类型的消息，需要查询该比赛目前的信息，然后传给发消息的客户端
			PushMessage matchMessage=queryMatchInfo(matchId,matchType);
			logger.info("查询到的比赛初始信息:{}",matchMessage);
			if(null!=matchMessage){//                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     将比赛初始信息发送给订阅该比赛channel的客户端，而不是所有订阅该比赛channel的客户端
				String matchChannel=matchMessage.makeChannelName();
    			Map<String, Object> matchDataMap=matchMessage.toJsonDataMap();
    			
    			remote.deliver(serverSession, matchChannel,matchDataMap);
    			logger.info("发送matchDataMap:{}到渠道{}",matchDataMap,matchChannel);
			}
			
		}
    		
    	
    }

	private Map<String, Object> queryOddsInfo(String subscription) {
		List<Map<String, String>> dataList = null;
		String corpId = subscription.replaceAll("[a-zA-Z//]", "");
		if(subscription.contains("JCZQ")){
			if(subscription.contains("europe")){
				if(StringUtils.isNotBlank(corpId)&&!corpId.equals("avg_europe_odd")){
					dataList = oddsService.findFbEuroOdds(corpId);
				}else if(StringUtils.isNotBlank(corpId)){
					
				}
			}
		}
		Map<String, Object> datMap = new HashMap<String, Object>();
		datMap.put("init", "init");//
		datMap.put("odds", dataList);
		return datMap;
	}

	private PushMessage queryMatchInfo(String matchId, String matchType) {
		PushMessage result=null;
		PushMessage pushMessageTemplate=PushMessageFactory.createTemplate(matchId,matchType);
		if(null!=pushMessageTemplate){
			result=	redisClient.getPushMessageByTemplate(pushMessageTemplate);
		}
		return result;
		
	}

}
