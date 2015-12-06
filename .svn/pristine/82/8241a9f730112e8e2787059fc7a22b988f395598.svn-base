package com.davcai.lottery.push.client;

import static org.junit.Assert.*;

import java.text.ParseException;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.UnknownMatchMessage;
import com.davcai.lottery.push.common.redis.RedisClient;

public class NewPushMessageHandlerTest {
	
	private NewPushMessageHandler checker;
	@Mocked RedisClient redisClient;

	@Before
	public void setUp(){
		 checker=new NewPushMessageHandlerRedisImpl();
		 checker.setRedisClient(redisClient);
	}
	
	@Test
	public void testWhenPushMessageIsNull(){
		
		
		PushMessage pushMessage=null;
		boolean result=checker.checkIfNew(pushMessage);
		assertFalse(result);
	}
	
	@Test
	public void testWhenPushMessageWithoutId(){
		
		PushMessage pushMessage=new UnknownMatchMessage();
		boolean result=checker.checkIfNew(pushMessage);
		assertFalse(result);
	}

	@Test
	public void testWhenFootballPushMessageIsOld() throws ParseException{
		final FootballMatchMessage existingPushMessage=new FootballMatchMessage();
		existingPushMessage.setMatchId("20141125201");
		existingPushMessage.setHomeTeamName("A");
		existingPushMessage.setGuestTeamName("B");
		existingPushMessage.setHomeScore(1);
		existingPushMessage.setGuestScore(0);
		existingPushMessage.setState("1");
		existingPushMessage.setGuestTeamHalfScore(0);
		existingPushMessage.setHomeTeamHalfScore(1);
		existingPushMessage.setGuestTeamPosition("1");
		existingPushMessage.setGuestTeamRed(0);
		existingPushMessage.setGuestTeamYellow(1);
		existingPushMessage.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
		existingPushMessage.setHomeTeamPosition("2");
		existingPushMessage.setHomeTeamRed(0);
		existingPushMessage.setHomeTeamYellow(0);
		existingPushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
	
		final FootballMatchMessage pushMessage=new FootballMatchMessage();
		pushMessage.setMatchId("20141125201");
		pushMessage.setHomeTeamName("A");
		pushMessage.setGuestTeamName("B");
		pushMessage.setHomeScore(1);
		pushMessage.setGuestScore(0);
		pushMessage.setState("1");
		pushMessage.setGuestTeamHalfScore(0);
		pushMessage.setHomeTeamHalfScore(1);
		pushMessage.setGuestTeamPosition("1");
		pushMessage.setGuestTeamRed(0);
		pushMessage.setGuestTeamYellow(1);
		pushMessage.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
		pushMessage.setHomeTeamPosition("2");
		pushMessage.setHomeTeamRed(0);
		pushMessage.setHomeTeamYellow(0);
		pushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
		
		new Expectations() {{
			redisClient.getPushMessageByTemplate(withSameInstance(pushMessage)); result = existingPushMessage; 
		}};
		boolean result=checker.checkIfNew(pushMessage);
		assertFalse(result);
	}
	
	@Test
	public void testWhenFootballPushMessageIsNew() throws ParseException{
		final FootballMatchMessage existingPushMessage=new FootballMatchMessage();
		existingPushMessage.setMatchId("20141125201");
		existingPushMessage.setHomeTeamName("A");
		existingPushMessage.setGuestTeamName("B");
		existingPushMessage.setHomeScore(1);
		existingPushMessage.setGuestScore(0);
		existingPushMessage.setState("1");
		existingPushMessage.setGuestTeamHalfScore(0);
		existingPushMessage.setHomeTeamHalfScore(1);
		existingPushMessage.setGuestTeamPosition("1");
		existingPushMessage.setGuestTeamRed(0);
		existingPushMessage.setGuestTeamYellow(1);
		existingPushMessage.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
		existingPushMessage.setHomeTeamPosition("2");
		existingPushMessage.setHomeTeamRed(0);
		existingPushMessage.setHomeTeamYellow(0);
		existingPushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
		new Expectations() {{
			redisClient.getPushMessageByTemplate(withInstanceOf(FootballMatchMessage.class)); result = existingPushMessage; 
		}};
		FootballMatchMessage pushMessage=new FootballMatchMessage();
		pushMessage.setMatchId("20141125201");
		pushMessage.setHomeTeamName("A");
		pushMessage.setGuestTeamName("B");
		pushMessage.setHomeScore(1);
		pushMessage.setGuestScore(1);
		pushMessage.setState("1");
		pushMessage.setGuestTeamHalfScore(0);
		pushMessage.setHomeTeamHalfScore(1);
		pushMessage.setGuestTeamPosition("1");
		pushMessage.setGuestTeamRed(0);
		pushMessage.setGuestTeamYellow(1);
		pushMessage.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
		pushMessage.setHomeTeamPosition("2");
		pushMessage.setHomeTeamRed(0);
		pushMessage.setHomeTeamYellow(0);
		pushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
		boolean result=checker.checkIfNew(pushMessage);
		assertTrue(result);
	}
	
	@Test
	public void testWhenBasketballPushMessageIsOld() throws ParseException{
		final BasketballMatchMessage existingPushMessage=new BasketballMatchMessage();
		existingPushMessage.setMatchId("20141125201");
		existingPushMessage.setHomeTeamName("A");
		existingPushMessage.setGuestTeamName("B");
		existingPushMessage.setHomeScore(1);
		existingPushMessage.setGuestScore(0);
		existingPushMessage.setState("1");
		existingPushMessage.setGuestTeamHalfScore(44);
		existingPushMessage.setHomeTeamHalfScore(47);
		existingPushMessage.setGuestTeamPosition("东1");
		existingPushMessage.setHomeOne(20);
		existingPushMessage.setGuestOne(23);
		existingPushMessage.setHomeTwo(24);
		existingPushMessage.setGuestTwo(24);
		existingPushMessage.setRemainTime("1:00");
		existingPushMessage.setHomeTeamPosition("东2");
		existingPushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
		new Expectations() {{
			redisClient.getPushMessageByTemplate(withInstanceOf(BasketballMatchMessage.class)); result = existingPushMessage; 
		}};
		BasketballMatchMessage pushMessage=new BasketballMatchMessage();
		pushMessage.setMatchId("20141125201");
		pushMessage.setHomeTeamName("A");
		pushMessage.setGuestTeamName("B");
		pushMessage.setHomeScore(1);
		pushMessage.setGuestScore(0);
		pushMessage.setState("1");
		pushMessage.setGuestTeamHalfScore(44);
		pushMessage.setHomeTeamHalfScore(47);
		pushMessage.setGuestTeamPosition("东1");
		pushMessage.setHomeOne(20);
		pushMessage.setGuestOne(23);
		pushMessage.setHomeTwo(24);
		pushMessage.setGuestTwo(24);
		pushMessage.setRemainTime("1:00");
		pushMessage.setHomeTeamPosition("东2");
		pushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
		boolean result=checker.checkIfNew(pushMessage);
		assertFalse(result);
	}
	
	@Test
	public void testWhenBasketballPushMessageIsNew() throws ParseException{
		final BasketballMatchMessage existingPushMessage=new BasketballMatchMessage();
		existingPushMessage.setMatchId("20141125201");
		existingPushMessage.setHomeTeamName("A");
		existingPushMessage.setGuestTeamName("B");
		existingPushMessage.setHomeScore(1);
		existingPushMessage.setGuestScore(0);
		existingPushMessage.setState("1");
		existingPushMessage.setGuestTeamHalfScore(44);
		existingPushMessage.setHomeTeamHalfScore(47);
		existingPushMessage.setGuestTeamPosition("东1");
		existingPushMessage.setHomeOne(20);
		existingPushMessage.setGuestOne(23);
		existingPushMessage.setHomeTwo(24);
		existingPushMessage.setGuestTwo(24);
		existingPushMessage.setRemainTime("1:00");
		existingPushMessage.setHomeTeamPosition("东2");
		existingPushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
		new Expectations() {{
			redisClient.getPushMessageByTemplate(withInstanceOf(BasketballMatchMessage.class)); result = existingPushMessage; 
		}};
		BasketballMatchMessage pushMessage=new BasketballMatchMessage();
		pushMessage.setMatchId("20141125201");
		pushMessage.setHomeTeamName("A");
		pushMessage.setGuestTeamName("B");
		pushMessage.setHomeScore(1);
		pushMessage.setGuestScore(0);
		pushMessage.setState("1");
		pushMessage.setGuestTeamHalfScore(44);
		pushMessage.setHomeTeamHalfScore(48);
		pushMessage.setGuestTeamPosition("东1");
		pushMessage.setHomeOne(20);
		pushMessage.setGuestOne(23);
		pushMessage.setHomeTwo(24);
		pushMessage.setGuestTwo(25);
		pushMessage.setRemainTime("1:00");
		pushMessage.setHomeTeamPosition("东2");
		pushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
		boolean result=checker.checkIfNew(pushMessage);
		assertTrue(result);
	}
	
	@Test
	public void testIsSameForHuanxin() throws ParseException{
		final BasketballMatchMessage existingPushMessage=new BasketballMatchMessage();
		existingPushMessage.setMatchId("20141125201");
		existingPushMessage.setHomeTeamName("A");
		existingPushMessage.setGuestTeamName("B");
		existingPushMessage.setHomeScore(1);
		existingPushMessage.setGuestScore(0);
		existingPushMessage.setState("2");
		existingPushMessage.setGuestTeamHalfScore(44);
		existingPushMessage.setHomeTeamHalfScore(48);
		existingPushMessage.setGuestTeamPosition("东1");
		existingPushMessage.setHomeOne(20);
		existingPushMessage.setGuestOne(23);
		existingPushMessage.setHomeTwo(24);
		existingPushMessage.setGuestTwo(24);
		existingPushMessage.setRemainTime("");
		existingPushMessage.setHomeTeamPosition("东2");
		existingPushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
//		new Expectations() {{
//			redisClient.getPushMessageByTemplate(withInstanceOf(BasketballMatchMessage.class)); result = existingPushMessage; 
//		}};
		BasketballMatchMessage pushMessage=new BasketballMatchMessage();
		pushMessage.setMatchId("20141125201");
		pushMessage.setHomeTeamName("A");
		pushMessage.setGuestTeamName("B");
		pushMessage.setHomeScore(1);
		pushMessage.setGuestScore(0);
		pushMessage.setState("50");
		pushMessage.setGuestTeamHalfScore(44);
		pushMessage.setHomeTeamHalfScore(44);
		pushMessage.setGuestTeamPosition("东1");
		pushMessage.setHomeOne(20);
		pushMessage.setGuestOne(25);
		pushMessage.setHomeTwo(24);
		pushMessage.setGuestTwo(25);
		pushMessage.setRemainTime("");
		pushMessage.setHomeTeamPosition("东2");
		pushMessage.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
		boolean result=pushMessage.isSameForHuanxin(existingPushMessage);
		assertTrue(result);
	}
}
