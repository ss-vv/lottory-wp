package com.davcai.lottery.push.client;



import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.eclipse.jetty.client.HttpClient;
import org.junit.Before;
import org.junit.Test;

import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.MatchMessage;
import com.davcai.lottery.push.common.model.MessageType;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushResult;
import com.davcai.lottery.push.common.model.UnknownMatchMessage;
import com.davcai.lottery.push.common.redis.RedisClient;
import com.davcai.lottery.push.common.redis.RedisClientImpl;
import com.davcai.lottery.push.common.redis.dao.PushMessageDaoImpl;
import com.unison.lottery.weibo.common.redis.RedisTemplate;





/**
 * Unit test for simple App.
 */
public class PushClientTest {
	
	private PushClientCometdImpl pushClient;
	private NewPushMessageHandler checker;
	@Mocked RedisClient redisClient;

	@Before
	public void setUp(){
		
		pushClient = new PushClientCometdImpl();
		checker = new NewPushMessageHandlerRedisImpl();
		checker.setRedisClient(redisClient);
		HttpClient jettyHttpClient = new org.eclipse.jetty.client.HttpClient();
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
		HttpConnectionParams.setSoTimeout(httpParameters, 30000);
		PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
		DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager,httpParameters);
		PushTask pushTask=new PushTask();
//		pushTask.setHttpClient(httpClient);
		pushTask.setJettyHttpClient(jettyHttpClient);
		pushTask.setPushHXServerUrl("http://182.92.191.193:28080/lottery-api/syncLiveScores");
		pushTask.setPushServerUrl("http://182.92.191.193:18080/push-server/comet");
		pushTask.setAppId("VyQlMW52Fy8eqjzNcJfS02");
		pushTask.setAppkey("tfJ1BbBf3W5vvtsBbYnVC");
		pushTask.setMaster("KfryGt6ZqK6O51rW2vfR82");
		pushTask.setHost("http://sdk.open.api.igexin.com/apiex.htm");
		pushClient.setPushTask(pushTask);
		pushClient.setNewPushMessageHandler(checker);
	}
   
    @Test
    public void testPushMatchMessageOne() throws ParseException{
    	
    	new Expectations() {{
			redisClient.getPushMessageByTemplate(withInstanceOf(PushMessage.class)); result = null; //期望永远返回null
		}};
    	
		FootballMatchMessage match = new FootballMatchMessage();
    	match.setMatchId("201412125002");
    	match.setHomeTeamName("赛事A");
    	match.setGuestTeamName("赛事B");
    	match.setHomeScore(2);
    	match.setGuestScore(232);
    	match.setState("1");
    	match.setGuestTeamHalfScore(0);
    	match.setHomeTeamHalfScore(1);
    	match.setGuestTeamPosition("1");
    	match.setGuestTeamRed(0);
    	match.setGuestTeamYellow(1);
    	match.setHalfStartTime(DateUtils.parseDate("2014-12-12 18:20:00", "yyyy-MM-dd HH:mm:ss"));
    	match.setHomeTeamPosition("2");
    	match.setHomeTeamRed(0);
    	match.setHomeTeamYellow(0);
    	match.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
		PushResult pushResult = null;
		try {
			pushResult = pushClient.pushMessage(match);
		} catch (PushClientException e) {
			e.printStackTrace();
		}
		System.out.println("response=====----------------------------------------------------------------: " + pushResult.toString());
		assertTrue(pushResult.isPublishSucc()&&pushResult.getCountOfSucc()==1&&pushResult.getCountOfFail()==0);
    }
    
    
    @Test
    public void testPushOldMatchMessageOne() throws ParseException{
    	final FootballMatchMessage oldMatchMessage=new FootballMatchMessage();
    	oldMatchMessage.setMatchId("20141125201");
    	oldMatchMessage.setHomeTeamName("A");
    	oldMatchMessage.setGuestTeamName("B");
    	oldMatchMessage.setHomeScore(5);
    	oldMatchMessage.setGuestScore(4);
    	oldMatchMessage.setState("1");
    	oldMatchMessage.setGuestTeamHalfScore(0);
    	oldMatchMessage.setHomeTeamHalfScore(1);
    	oldMatchMessage.setGuestTeamPosition("1");
    	oldMatchMessage.setGuestTeamRed(0);
    	oldMatchMessage.setGuestTeamYellow(1);
    	oldMatchMessage.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	oldMatchMessage.setHomeTeamPosition("2");
    	oldMatchMessage.setHomeTeamRed(0);
    	oldMatchMessage.setHomeTeamYellow(0);
    	oldMatchMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
    	
    	
		final FootballMatchMessage match = new FootballMatchMessage();
    	match.setMatchId("20141125201");
    	match.setHomeTeamName("A");
    	match.setGuestTeamName("B");
    	match.setHomeScore(5);
    	match.setGuestScore(4);
    	match.setState("1");
    	match.setGuestTeamHalfScore(0);
    	match.setHomeTeamHalfScore(1);
    	match.setGuestTeamPosition("1");
    	match.setGuestTeamRed(0);
    	match.setGuestTeamYellow(1);
    	match.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	match.setHomeTeamPosition("2");
    	match.setHomeTeamRed(0);
    	match.setHomeTeamYellow(0);
    	match.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
    	
    	new Expectations() {{
			redisClient.getPushMessageByTemplate(withSameInstance(match)); result = oldMatchMessage; //期望永远返回null
		}};
    	//match.setType(MessageType.FOOTBALL);
		PushResult pushResult = null;
		try {
			pushResult = pushClient.pushMessage(match);
		} catch (PushClientException e) {
			e.printStackTrace();
		}
		assertTrue(!pushResult.isPublishSucc()
				&& pushResult.getCountOfSucc() == 0
				&& pushResult.getCountOfFail() == 0
				&& pushResult.getCountOfOld() == 1);
    }
    
    @Test
    public void testPushNewMatchMessageOne() throws ParseException{
    	final FootballMatchMessage oldMatchMessage=new FootballMatchMessage();
    	oldMatchMessage.setMatchId("201412066001");
    	oldMatchMessage.setHomeTeamName("A");
    	oldMatchMessage.setGuestTeamName("B");
    	oldMatchMessage.setHomeScore(0);
    	oldMatchMessage.setGuestScore(0);
    	oldMatchMessage.setState("0");
    	oldMatchMessage.setGuestTeamHalfScore(0);
    	oldMatchMessage.setHomeTeamHalfScore(0);
    	oldMatchMessage.setGuestTeamPosition("1");
    	oldMatchMessage.setGuestTeamRed(0);
    	oldMatchMessage.setGuestTeamYellow(0);
    	oldMatchMessage.setHalfStartTime(null);
    	oldMatchMessage.setHomeTeamPosition("2");
    	oldMatchMessage.setHomeTeamRed(0);
    	oldMatchMessage.setHomeTeamYellow(0);
    	oldMatchMessage.setScheduleMatchTime(DateUtils.parseDate("2014-12-04 18:20:00", "yyyy-MM-dd HH:mm:ss"));
    	
    	
		final FootballMatchMessage match = new FootballMatchMessage();
    	match.setMatchId("201412066001");
    	match.setHomeTeamName("A");
    	match.setGuestTeamName("B");
    	match.setHomeScore(4);
    	match.setGuestScore(7);
    	match.setState("1");
    	match.setGuestTeamHalfScore(0);
    	match.setHomeTeamHalfScore(1);
    	match.setGuestTeamPosition("1");
    	match.setGuestTeamRed(0);
    	match.setGuestTeamYellow(1);
    	match.setHalfStartTime(DateUtils.parseDate("2014-12-06 01:20:00", "yyyy-MM-dd HH:mm:ss"));
    	match.setHomeTeamPosition("2");
    	match.setHomeTeamRed(0);
    	match.setHomeTeamYellow(0);
    	match.setScheduleMatchTime(DateUtils.parseDate("2014-12-06 01:20:00", "yyyy-MM-dd HH:mm:ss"));
    	
    	new Expectations() {{
			redisClient.getPushMessageByTemplate(withSameInstance(match)); result = oldMatchMessage; 
			
		}};
    	//match.setType(MessageType.FOOTBALL);
		PushResult pushResult = null;
		try {
			pushResult = pushClient.pushMessage(match);
		} catch (PushClientException e) {
			e.printStackTrace();
		}
		assertTrue(pushResult.isPublishSucc()
				&& pushResult.getCountOfSucc() == 1
				&& pushResult.getCountOfFail() == 0
				&& pushResult.getCountOfOld() == 0);
    }
    
    @Test
    public void testPushShouldUnsubscribeMatchMessage() {
    	new Expectations() {{
			redisClient.getPushMessageByTemplate(withInstanceOf(PushMessage.class)); result = null; //期望永远返回null
		}};
    	MatchMessage match = new FootballMatchMessage();
    	match.setMatchId("2");
    	match.setHomeTeamName("赛事A");
    	match.setGuestTeamName("赛事B");
    	match.setHomeScore(2);
    	match.setGuestScore(5);
//    	match.setType(MessageType.FOOTBALL);
    	match.setState("-11");//待定
    	PushResult pushResult = null;
		try {
			pushResult = pushClient.pushMessage(match);
		} catch (PushClientException e) {
			e.printStackTrace();
		}
		assertTrue(pushResult.isPublishSucc()&&pushResult.getCountOfSucc()==1&&pushResult.getCountOfFail()==0);
    	
		
		match = new BasketballMatchMessage();
    	match.setMatchId("1");
    	match.setHomeTeamName("赛事A");
    	match.setGuestTeamName("赛事B");
    	match.setHomeScore(2);
    	match.setGuestScore(2);
//    	match.setType(MessageType.BASKETBALL);
    	match.setState("-1");
		try {
			pushResult = pushClient.pushMessage(match);
		} catch (PushClientException e) {
			e.printStackTrace();
		}
		assertTrue(pushResult.isPublishSucc()&&pushResult.getCountOfSucc()==1&&pushResult.getCountOfFail()==0);
    	
    	
    }
    
    @Test
    public void testBatchPushMatchMessage() throws ParseException{
    	new Expectations() {{
			redisClient.getPushMessageByTemplate(withInstanceOf(PushMessage.class)); result = null; //期望永远返回null
		}};
    	List<PushMessage> messageList=new ArrayList<PushMessage>();
    	FootballMatchMessage match = new FootballMatchMessage();
    	match.setMatchId("201502112001");
    	match.setHomeTeamName("莱切斯特城");
    	match.setGuestTeamName("莱切斯特城2");
    	match.setHomeScore(2);
    	match.setGuestScore(1);
    	match.setState("3");
    	match.setGuestTeamHalfScore(0);
    	match.setHomeTeamHalfScore(1);
    	match.setGuestTeamPosition("1");
    	match.setGuestTeamRed(0);
    	match.setGuestTeamYellow(1);
    	match.setHalfStartTime(DateUtils.parseDate("2015-02-10 17:55:00", "yyyy-MM-dd HH:mm:ss"));
    	match.setHomeTeamPosition("2");
    	match.setHomeTeamRed(0);
    	match.setHomeTeamYellow(0);
    	match.setScheduleMatchTime(DateUtils.parseDate("2015-02-10 17:00:00", "yyyy-MM-dd HH:mm:ss"));
    	match.setLiveContent(" 测试 ,足球");
    	
    	FootballMatchMessage match2 = new FootballMatchMessage();
    	match2.setMatchId("201502112002");
     	match2.setHomeTeamName("莱切斯特城A");
     	match2.setGuestTeamName("莱切斯特城CC");
     	match2.setHomeScore(2);
     	match2.setGuestScore(2);
     	match2.setState("3");
    	match2.setGuestTeamHalfScore(0);
    	match2.setHomeTeamHalfScore(1);
    	match2.setGuestTeamPosition("1");
    	match2.setGuestTeamRed(0);
    	match2.setGuestTeamYellow(1);
    	match2.setHalfStartTime(DateUtils.parseDate("2015-02-10 18:05:00", "yyyy-MM-dd HH:mm:ss"));
    	match2.setHomeTeamPosition("6");
    	match2.setHomeTeamRed(0);
    	match2.setHomeTeamYellow(0);
    	match2.setScheduleMatchTime(DateUtils.parseDate("2015-02-10 17:10:00", "yyyy-MM-dd HH:mm:ss"));
     	
    	BasketballMatchMessage match3 = new BasketballMatchMessage();
     	match3.setMatchId("201501201009");
     	match3.setHomeTeamName("篮球赛事P9");
     	match3.setGuestTeamName("篮球赛事P9");
     	match3.setHomeScore(45);
     	match3.setGuestScore(58);
     	match3.setGuestTeamHalfScore(42);
     	match3.setHomeTeamHalfScore(40);
     	match3.setGuestTeamPosition("东2");
     	match3.setHomeOne(20);
     	match3.setGuestOne(23);
     	match3.setHomeTwo(25);
     	match3.setGuestTwo(25);
     	match3.setRemainTime("1:20");
     	match3.setHomeTeamPosition("东2");
     	match3.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:36:00", "yyyy-MM-dd HH:mm:ss"));
     	match3.setState("2");
     	
     	messageList.add(match);
     	messageList.add(match2);
     	messageList.add(match3);
    	
     	PushResult pushResult = null;
		try {
			pushResult=pushClient.pushMessages(messageList);
		} catch (PushClientException e) {
			e.printStackTrace();
		}
		System.out.println("-----------------------------------------------------------------------" + pushResult.toString());
//		assertTrue(pushResult.isPublishSucc()&&pushResult.getCountOfSucc()==messageList.size()&&pushResult.getCountOfFail()==0);
		
    }
    
    
    @Test
    public void testBatchPushMatchMessageWithOldOne() throws ParseException{
    	final FootballMatchMessage oldMatchMessage=new FootballMatchMessage();
    	oldMatchMessage.setMatchId("20141125201");
    	oldMatchMessage.setHomeTeamName("A");
    	oldMatchMessage.setGuestTeamName("B");
    	oldMatchMessage.setHomeScore(5);
    	oldMatchMessage.setGuestScore(4);
    	oldMatchMessage.setState("1");
    	oldMatchMessage.setGuestTeamHalfScore(0);
    	oldMatchMessage.setHomeTeamHalfScore(1);
    	oldMatchMessage.setGuestTeamPosition("1");
    	oldMatchMessage.setGuestTeamRed(0);
    	oldMatchMessage.setGuestTeamYellow(1);
    	oldMatchMessage.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	oldMatchMessage.setHomeTeamPosition("2");
    	oldMatchMessage.setHomeTeamRed(0);
    	oldMatchMessage.setHomeTeamYellow(0);
    	oldMatchMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
    	
    	List<PushMessage> messageList=new ArrayList<PushMessage>();
    	final FootballMatchMessage match = new FootballMatchMessage();
    	match.setMatchId("20141125201");
    	match.setHomeTeamName("A");
    	match.setGuestTeamName("B");
    	match.setHomeScore(5);
    	match.setGuestScore(4);
    	match.setState("1");
    	match.setGuestTeamHalfScore(0);
    	match.setHomeTeamHalfScore(1);
    	match.setGuestTeamPosition("1");
    	match.setGuestTeamRed(0);
    	match.setGuestTeamYellow(1);
    	match.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	match.setHomeTeamPosition("2");
    	match.setHomeTeamRed(0);
    	match.setHomeTeamYellow(0);
    	match.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
    	
    	FootballMatchMessage match2 = new FootballMatchMessage();
     	match2.setMatchId("2");
     	match2.setHomeTeamName("赛事A");
     	match2.setGuestTeamName("赛事B");
     	match2.setHomeScore(4);
     	match2.setGuestScore(15);
     	match2.setState("1");
    	match2.setGuestTeamHalfScore(0);
    	match2.setHomeTeamHalfScore(1);
    	match2.setGuestTeamPosition("1");
    	match2.setGuestTeamRed(0);
    	match2.setGuestTeamYellow(1);
    	match2.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	match2.setHomeTeamPosition("2");
    	match2.setHomeTeamRed(0);
    	match2.setHomeTeamYellow(0);
    	match2.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
     	
    	BasketballMatchMessage match3 = new BasketballMatchMessage();
     	match3.setMatchId("20141125201");
     	match3.setHomeTeamName("篮球赛事A");
     	match3.setGuestTeamName("篮球赛事B");
     	match3.setHomeScore(15);
     	match3.setGuestScore(20);
     	match3.setGuestTeamHalfScore(44);
     	match3.setHomeTeamHalfScore(47);
     	match3.setGuestTeamPosition("东1");
     	match3.setHomeOne(20);
     	match3.setGuestOne(23);
     	match3.setHomeTwo(24);
     	match3.setGuestTwo(25);
     	match3.setRemainTime("1:00");
     	match3.setHomeTeamPosition("东2");
     	match3.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
     	match3.setState("1");
     	messageList.add(match);
     	messageList.add(match2);
     	messageList.add(match3);
    	
     	new Expectations() {{
			redisClient.getPushMessageByTemplate(withSameInstance(match)); result = oldMatchMessage; 
		}};
     	PushResult pushResult = null;
		try {
			pushResult=pushClient.pushMessages(messageList);
		} catch (PushClientException e) {
			e.printStackTrace();
		}
		assertTrue(!pushResult.isPublishSucc()&&pushResult.getCountOfSucc()==2&&pushResult.getCountOfFail()==0&&pushResult.getCountOfOld()==1);
		
    }
    
    @Test(expected=PushClientException.class)
    public void testBatchPushMatchMessageWhenMessagesIsBlank() throws PushClientException{
    	List<PushMessage> messageList=new ArrayList<PushMessage>();
    	pushClient.pushMessages(messageList);
    }
    
    @Test(expected=PushClientException.class)
    public void testBatchPushMatchMessageWhenMessagesIsNull() throws PushClientException{
    	List<PushMessage> messageList=null;
    	pushClient.pushMessages(messageList);
    }
    
    @Test(expected=PushClientException.class)
    public void testBatchPushMatchMessageWhenOneMessageIsNull() throws PushClientException{
    	List<PushMessage> messageList=new ArrayList<PushMessage>();
    	PushMessage message=null;
		messageList.add(message);
    	pushClient.pushMessages(messageList);
    }
    
    @Test(expected=PushClientException.class)
    public void testBatchPushMatchMessageWhenTypeOfOneMessageIsNull() throws PushClientException{
    	List<PushMessage> messageList=new ArrayList<PushMessage>();
    	PushMessage message=new UnknownMatchMessage();
//    	message.setType(null);
		messageList.add(message);
    	pushClient.pushMessages(messageList);
    }
   
    
}
