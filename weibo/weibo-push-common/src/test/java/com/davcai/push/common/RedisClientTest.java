package com.davcai.push.common;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.redis.RedisClient;
import com.davcai.lottery.push.common.redis.RedisClientImpl;
import com.davcai.lottery.push.common.redis.dao.PushMessageDao;
import com.davcai.lottery.push.common.redis.dao.PushMessageDaoImpl;
import com.unison.lottery.weibo.common.redis.RedisTemplate;


public class RedisClientTest {
	
	private RedisClientImpl client;
	private RedisTemplate redisTemplate;
	private PushMessageDaoImpl pushMessageDao;
	
	@Before
	public void setUp() {
		client=new RedisClientImpl();
		pushMessageDao=new PushMessageDaoImpl();
		String host="182.92.191.193";
		int port=22122;
		redisTemplate=new RedisTemplate(host, port);
		pushMessageDao.setRedisTemplate(redisTemplate);
		Set<String> propertyNamesOfDate=new HashSet<String>();
		propertyNamesOfDate.add("scheduleMatchTime");
		propertyNamesOfDate.add("halfStartTime");
		pushMessageDao.setPropertyNamesOfDate(propertyNamesOfDate);
		client.setPushMessageDao(pushMessageDao);
		
	}
	
	@Test
	public void testGetPushMessage() throws ParseException{
	
		
		FootballMatchMessage match = new FootballMatchMessage();
    	match.setMatchId("1");		
    	match.setHomeTeamName("赛事A");
    	match.setGuestTeamName("赛事B");
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
    	
		client.savePushMessage(match);
		PushMessage message = client.getPushMessageByTemplate(match);
		assertTrue(null!=message);
		System.out.println("message="+message);
		assertTrue(message.isSame(match)&&match.isSame(message));
		client.deletePushMessage(match);
	}
	
	@Test
	public void testGetPushBasketMessage() throws ParseException{
	
		
		BasketballMatchMessage match1 = new BasketballMatchMessage();
    	match1.setMatchId("20141125201");
    	match1.setHomeTeamName("A");
    	match1.setGuestTeamName("B");
    	match1.setHomeScore(44);
    	match1.setGuestScore(47);
    	match1.setState("2");
    	match1.setGuestTeamHalfScore(44);
    	match1.setHomeTeamHalfScore(47);
    	match1.setGuestTeamPosition("东1");
    	match1.setHomeOne(20);
    	match1.setGuestOne(23);
    	match1.setHomeTwo(24);
    	match1.setGuestTwo(24);
    	match1.setRemainTime("");
    	match1.setHomeTeamPosition("东2");
    	match1.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
    	
		client.savePushMessage(match1);
		PushMessage message = client.getPushMessageByTemplate(match1);
		assertTrue(null!=message);
		System.out.println("message="+message);
		assertTrue(message.isSame(match1)&&match1.isSame(message));
		client.deletePushMessage(match1);
	}
	

	

}
