package com.davcai.push.common;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.MatchMessage;



/**
 * Unit test for simple App.
 */
public class FootballMatchMessageTest 
{
    @Test
    public void testCheckIfShouldUnsubscribe(){
    	MatchMessage match = new FootballMatchMessage();
    	match.setState("-11");//待定
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    	match = new FootballMatchMessage();
    	match.setState("-12");//腰斩
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    	match = new FootballMatchMessage();
    	match.setState("-13");//中断
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    	match = new FootballMatchMessage();
    	match.setState("-14");//推迟
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    	match = new FootballMatchMessage();
    	match.setState("-1");//完场
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    	match = new FootballMatchMessage();
    	match.setState("-10");//取消
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    }
    
    @Test
    public void testStateDesc(){
    	MatchMessage match = new FootballMatchMessage();
    	match.setState("-11");//待定
    	assertTrue(StringUtils.equals(match.getStateDesc(), "待定"));
    	
    	match = new FootballMatchMessage();
    	match.setState("-12");//腰斩
    	assertTrue(StringUtils.equals(match.getStateDesc(), "腰斩"));
    	
    	match = new FootballMatchMessage();
    	match.setState("-13");//中断
    	assertTrue(StringUtils.equals(match.getStateDesc(), "中断"));
    	
    	match = new FootballMatchMessage();
    	match.setState("-14");//推迟
    	assertTrue(StringUtils.equals(match.getStateDesc(), "推迟"));
    	
    	match = new FootballMatchMessage();
    	match.setState("-1");//完场
    	assertTrue(StringUtils.equals(match.getStateDesc(), "完场"));
    	
    	match = new FootballMatchMessage();
    	match.setState("-10");//取消
    	assertTrue(StringUtils.equals(match.getStateDesc(), "取消"));
    	
    	match = new FootballMatchMessage();
    	match.setState("0");//未开
    	assertTrue(StringUtils.equals(match.getStateDesc(), "未开"));
    	
    	match = new FootballMatchMessage();
    	match.setState("1");//上半场
    	assertTrue(StringUtils.equals(match.getStateDesc(), "上半场"));
    	
    	match = new FootballMatchMessage();
    	match.setState("2");//中场
    	assertTrue(StringUtils.equals(match.getStateDesc(), "中场"));
    	
    	match = new FootballMatchMessage();
    	match.setState("3");//下半场
    	assertTrue(StringUtils.equals(match.getStateDesc(), "下半场"));
    	
    	match = new FootballMatchMessage();
    	match.setState("4");//加时
    	assertTrue(StringUtils.equals(match.getStateDesc(), "加时"));
    	
    	match = new FootballMatchMessage();
    	match.setState(null);
    	assertTrue(StringUtils.equals(match.getStateDesc(), "未开"));
    	
    }
    
    @Test
    public void testIsSame() throws ParseException{
    	FootballMatchMessage match1 = new FootballMatchMessage();
    	match1.setMatchId("20141125201");
    	match1.setHomeTeamName("A");
    	match1.setGuestTeamName("B");
    	match1.setHomeScore(1);
    	match1.setGuestScore(0);
    	match1.setState("1");
    	match1.setGuestTeamHalfScore(0);
    	match1.setHomeTeamHalfScore(1);
    	match1.setGuestTeamPosition("1");
    	match1.setGuestTeamRed(0);
    	match1.setGuestTeamYellow(1);
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	match1.setHomeTeamPosition("2");
    	match1.setHomeTeamRed(0);
    	match1.setHomeTeamYellow(0);
    	match1.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
    	FootballMatchMessage match2 = new FootballMatchMessage();
    	match2.setMatchId("20141125201");
    	match2.setHomeTeamName("A");
    	match2.setGuestTeamName("B");
    	match2.setHomeScore(1);
    	match2.setGuestScore(0);
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
    	assertTrue(match1.isSame(match2)&&match2.isSame(match1));
    }
    
    @Test
    public void testIsSameWhenDateIsNUll() throws ParseException{
    	FootballMatchMessage match1 = new FootballMatchMessage();
    	match1.setMatchId("20141125201");
    	match1.setHomeTeamName("A");
    	match1.setGuestTeamName("B");
    	match1.setHomeScore(1);
    	match1.setGuestScore(0);
    	match1.setState("1");
    	match1.setGuestTeamHalfScore(0);
    	match1.setHomeTeamHalfScore(1);
    	match1.setGuestTeamPosition("1");
    	match1.setGuestTeamRed(0);
    	match1.setGuestTeamYellow(1);
    	match1.setHalfStartTime(null);
    	match1.setHomeTeamPosition("2");
    	match1.setHomeTeamRed(0);
    	match1.setHomeTeamYellow(0);
    	match1.setScheduleMatchTime(null);
    	FootballMatchMessage match2 = new FootballMatchMessage();
    	match2.setMatchId("20141125201");
    	match2.setHomeTeamName("A");
    	match2.setGuestTeamName("B");
    	match2.setHomeScore(1);
    	match2.setGuestScore(0);
    	match2.setState("1");
    	match2.setGuestTeamHalfScore(0);
    	match2.setHomeTeamHalfScore(1);
    	match2.setGuestTeamPosition("1");
    	match2.setGuestTeamRed(0);
    	match2.setGuestTeamYellow(1);
    	match2.setHalfStartTime(null);
    	match2.setHomeTeamPosition("2");
    	match2.setHomeTeamRed(0);
    	match2.setHomeTeamYellow(0);
    	match2.setScheduleMatchTime(null);
    	assertTrue(match1.isSame(match2)&&match2.isSame(match1));
    }
    
    @Test
    public void testGetDefaultState(){
    	FootballMatchMessage match1 = new FootballMatchMessage();
    	match1.setState(null);
    	assertTrue(StringUtils.equals(match1.getState(), "0"));
    }
    
    @Test
    public void testGetPlayingTime() throws ParseException{
    	FootballMatchMessage match1 = new FootballMatchMessage();
    	match1.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	match1.setState("0");
    	Date now=DateUtils.parseDate("2014-11-25 18:30:00", "yyyy-MM-dd HH:mm:ss");
		assertTrue(0==match1.getPlayingTime(now));
    	
    	match1.setState("1");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 18:31:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(31==match1.getPlayingTime(now));
    	
    	match1.setState("1");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 18:35:43", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(35==match1.getPlayingTime(now));
    	
    	match1.setState("2");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:00:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 18:46:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(0==match1.getPlayingTime(now));
    	
    	match1.setState("3");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:00:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 19:10:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue((45+10)==match1.getPlayingTime(now));
    	
    	match1.setState("3");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:00:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 19:13:01", "yyyy-MM-dd HH:mm:ss");
    	assertTrue((45+13)==match1.getPlayingTime(now));
    	
    	match1.setState("3");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:10:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 19:13:01", "yyyy-MM-dd HH:mm:ss");
    	assertTrue((45+3)==match1.getPlayingTime(now));
    	
    	match1.setState("4");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:10:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 20:00:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(0==match1.getPlayingTime(now));
    	
    	match1.setState("-10");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:10:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 20:10:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(0==match1.getPlayingTime(now));
    	
    	match1.setState("-11");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:10:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 20:10:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(0==match1.getPlayingTime(now));
    	
    	match1.setState("-12");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:10:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 20:10:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(0==match1.getPlayingTime(now));
    	
    	match1.setState("-13");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:10:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 20:10:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(0==match1.getPlayingTime(now));
    	
    	match1.setState("-14");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:10:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 20:10:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(0==match1.getPlayingTime(now));
    	
    	match1.setState("-1");
    	match1.setHalfStartTime(DateUtils.parseDate("2014-11-25 19:10:00", "yyyy-MM-dd HH:mm:ss"));
    	now=DateUtils.parseDate("2014-11-25 20:10:00", "yyyy-MM-dd HH:mm:ss");
    	assertTrue(0==match1.getPlayingTime(now));
    	
    }
}
