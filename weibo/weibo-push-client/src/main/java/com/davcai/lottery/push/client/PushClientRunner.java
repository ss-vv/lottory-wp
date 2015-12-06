package com.davcai.lottery.push.client;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushResult;


public class PushClientRunner {

	private static Logger logger=LoggerFactory.getLogger(PushClientRunner.class);
	public static void main(String[] args) throws ParseException {
		logger.info("PushClientRunner starting...");
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"classpath:pushClient.xml"});
		PushClient pushClient=(PushClient) context.getBean("pushClient");
		List<PushMessage> messageList=new ArrayList<PushMessage>();
    	FootballMatchMessage match = new FootballMatchMessage();
    	match.setMatchId("201412033002");
    	match.setHomeTeamName("赛事A");
    	match.setGuestTeamName("赛事B");
    	match.setHomeScore(5);
    	match.setGuestScore(5);
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
     	match2.setHomeScore(2);
     	match2.setGuestScore(14);
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
     	match3.setGuestScore(18);
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
     	PushResult pushResult = null;
		try {
			pushResult=pushClient.pushMessages(messageList);
		} catch (PushClientException e) {
			e.printStackTrace();
		}
		
		logger.info("pushResult:{}",pushResult);
		System.exit(0);

	}

}
