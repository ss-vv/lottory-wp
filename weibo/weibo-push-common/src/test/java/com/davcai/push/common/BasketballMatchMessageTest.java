package com.davcai.push.common;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.MatchMessage;



/**
 * Unit test for simple App.
 */
public class BasketballMatchMessageTest 
{
    @Test
    public void testCheckIfShouldUnsubscribe(){
    	MatchMessage match = new BasketballMatchMessage();
    	match.setState("-2");//待定
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    	match = new BasketballMatchMessage();
    	match.setState("-3");//中断
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    	match = new BasketballMatchMessage();
    	match.setState("-5");//推迟
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    	match = new BasketballMatchMessage();
    	match.setState("-1");//完场
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    	match = new BasketballMatchMessage();
    	match.setState("-4");//取消
    	match.checkAndSetShouldUnsubscribe();
    	assertTrue(match.isShouldUnsubscribe());
    	
    }
    
    @Test
    public void testStateDesc(){
    	BasketballMatchMessage match = new BasketballMatchMessage();
    	match.setState("-2");//待定
    	assertTrue(StringUtils.equals(match.getStateDesc(), "待定"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("-3");//中断
    	assertTrue(StringUtils.equals(match.getStateDesc(), "中断"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("-5");//推迟
    	assertTrue(StringUtils.equals(match.getStateDesc(), "推迟"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("-1");//完场
    	assertTrue(StringUtils.equals(match.getStateDesc(), "完场"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("-4");//取消
    	assertTrue(StringUtils.equals(match.getStateDesc(), "取消"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("0");//未开
    	assertTrue(StringUtils.equals(match.getStateDesc(), "未开"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("1");//第一节
    	match.setRemainTime("1:00");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第一节"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("1");//第一节
    	match.setRemainTime("");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第一节结束"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("2");//第二节
    	match.setRemainTime("1:00");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第二节"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("2");//第二节
    	match.setRemainTime("");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第二节结束"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("3");//第三节
    	match.setRemainTime("1:00");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第三节"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("3");//第三节
    	match.setRemainTime("");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第三节结束"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("4");//第四节
    	match.setRemainTime("1:00");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第四节"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("4");//第四节
    	match.setRemainTime("");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第四节结束"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("5");//加时赛第一节
    	match.setRemainTime("1:00");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第一个加时"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("5");//加时赛第一节
    	match.setRemainTime("");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第一个加时结束"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("6");//加时赛第二节
    	match.setRemainTime("1:00");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第二个加时"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("6");//加时赛第二节
    	match.setRemainTime("");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第二个加时结束"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("7");//加时赛第三节
    	match.setRemainTime("1:01");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第三个加时"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("7");//加时赛第三节
    	match.setRemainTime("");
    	assertTrue(StringUtils.equals(match.getStateDesc(), "第三个加时结束"));
    	
    	match = new BasketballMatchMessage();
    	match.setState("50");//中场
    	assertTrue(StringUtils.equals(match.getStateDesc(), "中场"));
    	
    	match = new BasketballMatchMessage();
    	match.setState(null);
    	assertTrue(StringUtils.equals(match.getStateDesc(), "未开"));
    	
    }
    
    @Test
    public void testIsSame() throws ParseException{
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
    	match1.setRemainTime("1:00");
    	match1.setHomeTeamPosition("东2");
    	match1.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
    	BasketballMatchMessage match2 = new BasketballMatchMessage();
    	match2.setMatchId("20141125201");
    	match2.setHomeTeamName("A");
    	match2.setGuestTeamName("B");
    	match2.setHomeScore(44);
    	match2.setGuestScore(47);
    	match2.setState("2");
    	match2.setGuestTeamHalfScore(44);
    	match2.setHomeTeamHalfScore(47);
    	match2.setGuestTeamPosition("东1");
    	match2.setHomeOne(20);
    	match2.setGuestOne(23);
    	match2.setHomeTwo(24);
    	match2.setGuestTwo(24);
    	match2.setRemainTime("1:00");
    	match2.setHomeTeamPosition("东2");
    	match2.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
    	assertTrue(match1.isSame(match2)&&match2.isSame(match1));
    }
    
    @Test
    public void testIsNotSame() throws ParseException{
    	BasketballMatchMessage match1 = new BasketballMatchMessage();
    	match1.setMatchId("201412043311");
    	match1.setHomeTeamName("洛杉矶快船");
    	match1.setGuestTeamName("奥兰多魔术");
    	match1.setHomeScore(114);
    	match1.setGuestScore(86);
    	match1.setState("-1");
    	match1.setGuestTeamHalfScore(0);
    	match1.setHomeTeamHalfScore(0);
    	match1.setGuestTeamPosition(null);
    	match1.setHomeOne(31);
    	match1.setGuestOne(23);
    	match1.setHomeTwo(26);
    	match1.setGuestTwo(21);
    	match1.setRemainTime("");
    	match1.setHomeTeamPosition(null);
    	match1.setScheduleMatchTime(DateUtils.parseDate("2014-12-04 11:30:00", "yyyy-MM-dd HH:mm:ss"));
    	match1.setExplainContent("快船-得分:格里芬(21) 篮板:乔丹(16) 助攻:保罗(10)<br/>魔术-得分:T.哈里斯(16) 篮板:T.哈里斯(8) 助攻:E.佩顿(6)");
    	BasketballMatchMessage match2 = new BasketballMatchMessage();
    	match2.setMatchId("201412043311");
    	match2.setHomeTeamName("洛杉矶快船");
    	match2.setGuestTeamName("奥兰多魔术");
    	match2.setHomeScore(114);
    	match2.setGuestScore(86);
    	match2.setState("-1");
    	match2.setGuestTeamHalfScore(0);
    	match2.setHomeTeamHalfScore(0);
    	match2.setGuestTeamPosition(null);
    	match2.setHomeOne(31);
    	match2.setGuestOne(23);
    	match2.setHomeTwo(26);
    	match2.setGuestTwo(21);
    	match2.setRemainTime("");
    	match2.setHomeTeamPosition(null);
    	match2.setScheduleMatchTime(DateUtils.parseDate("2014-12-04 11:30:00", "yyyy-MM-dd HH:mm:ss"));
    	match2.setExplainContent("快船-得分:格里芬(21) 篮板:乔丹(16) 助攻:保罗(10)<br/>魔术-得分:T.哈里斯(16) 篮板:T.哈里斯(8) 助攻:E.佩顿(6)");
    	assertTrue(match1.isSame(match2)&&match2.isSame(match1));
    }
    
    @Test
    public void testIsSameWhenDateIsNUll() throws ParseException{
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
    	match1.setRemainTime("1:00");
    	match1.setHomeTeamPosition("东2");
    	match1.setScheduleMatchTime(null);
    	BasketballMatchMessage match2 = new BasketballMatchMessage();
    	match2.setMatchId("20141125201");
    	match2.setHomeTeamName("A");
    	match2.setGuestTeamName("B");
    	match2.setHomeScore(44);
    	match2.setGuestScore(47);
    	match2.setState("2");
    	match2.setGuestTeamHalfScore(42);
    	match2.setHomeTeamHalfScore(47);
    	match2.setGuestTeamPosition("东1");
    	match2.setHomeOne(20);
    	match2.setGuestOne(23);
    	match2.setHomeTwo(24);
    	match2.setGuestTwo(24);
    	match2.setRemainTime("1:00");
    	match2.setHomeTeamPosition("东2");
    	match2.setScheduleMatchTime(null);
    	assertTrue(match1.isSame(match2)&&match2.isSame(match1));
    }
    
    @Test
    public void testGetDefaultState(){
    	BasketballMatchMessage match1 = new BasketballMatchMessage();
    	match1.setState(null);
    	assertTrue(StringUtils.equals(match1.getState(), "0"));
    }
}
