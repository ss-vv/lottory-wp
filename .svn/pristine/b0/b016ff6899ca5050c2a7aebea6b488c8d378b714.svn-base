package com.unison.lottery.weibo.dataservice.parse;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.unison.lottery.weibo.dataservice.commons.parse.ParseService;
import com.unison.lottery.weibo.dataservice.commons.parse.ParseServiceImpl;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;

public class BBMatchInfoDataParseServiceTest {
	
	private BBMatchInfoDataParseService parseService;
	

	@Before
	public void setUp(){
		parseService=new BBMatchInfoDataParseServiceImpl();
	}
	
	@Test
	public void testParseBBMatchInfoDataFromStrings() throws ParseException{
		List<String> strs=new ArrayList<String>();
		strs.add("193652^1^1^NBA季前,NBA季前^4^#FF0000^2014/10/10 7:30:00^0^^9^底特律活塞,底特律活塞^12^密尔沃基雄鹿,密爾沃基公鹿^东14^东3^^^^^^^^^^^0^^^^^^^^^^0");
		strs.add("193653^1^1^NBA季前,NBA季前^4^#FF0000^2014/10/10 8:00:00^1^^21^休斯顿火箭,侯斯頓火箭^22^孟菲斯灰熊,孟菲斯灰熊^西3^西12^15^21^15^21^^^^^^^0^^^^^^^^^^1");
		List<BBMatchInfoData> matches=parseService.parseBBMatchInfoDataFromStrings(strs);
		assertTrue(null!=matches&&!matches.isEmpty()&&matches.size()==2);
		BBMatchInfoData match1 = matches.get(0);
		BBMatchInfoData match2 = matches.get(1);
		BBMatchInfoData tartgetMatch=new BBMatchInfoData();
		tartgetMatch.setId(193652);
		tartgetMatch.setCupLeagueId(1);
		tartgetMatch.setCupLeague(1);
		tartgetMatch.setName("NBA季前");
		tartgetMatch.setNameF("NBA季前");
		tartgetMatch.setSclassType(4);
		tartgetMatch.setColor("#FF0000");
		tartgetMatch.setMatchTimeStr("2014-10-10 07:30:00");
		tartgetMatch.setMatchTime(DateUtils.parseDate("2014-10-10 07:30:00", "yyyy-MM-dd hh:mm:ss"));
		tartgetMatch.setMatchState(0);
		tartgetMatch.setRemainTime(0);
		tartgetMatch.setHomeTeamId(9);
		tartgetMatch.setHomeTeam("底特律活塞");
		tartgetMatch.setHomeTeamF("底特律活塞");
		tartgetMatch.setGuestTeamId(12);
		tartgetMatch.setGuestTeam("密尔沃基雄鹿");
		tartgetMatch.setGuestTeamF("密爾沃基公鹿");
		tartgetMatch.setHomeTeamRank("东14");
		tartgetMatch.setGuestTeamRank("东3");
		tartgetMatch.setHomeScore(0);
		tartgetMatch.setGuestScore(0);
		tartgetMatch.setHomeOne(0);
		tartgetMatch.setGuestOne(0);
		tartgetMatch.setHomeTwo(0);
		tartgetMatch.setGuestTwo(0);
		tartgetMatch.setHomeThree(0);
		tartgetMatch.setGuestThree(0);
		tartgetMatch.setHomeFour(0);
		tartgetMatch.setGuestFour(0);
		tartgetMatch.setAddTime(0);
		tartgetMatch.setHomeAddTime1(0);
		tartgetMatch.setGuestAddTime1(0);
		tartgetMatch.setHomeAddTime2(0);
		tartgetMatch.setGuestAddTime2(0);
		tartgetMatch.setHomeAddTime3(0);
		tartgetMatch.setGuestAddTime3(0);
		tartgetMatch.setAddTechnic(false);
		tartgetMatch.setTv("");
		tartgetMatch.setExplainContent("");
		tartgetMatch.setMiddleMatchState("0");
		tartgetMatch.setIsNeutral(false);
		checkMatch(match1,tartgetMatch);
		BBMatchInfoData tartgetMatch2=new BBMatchInfoData() ;
		tartgetMatch2.setId(193653);
		tartgetMatch2.setCupLeagueId(1);
		tartgetMatch2.setCupLeague(1);
		tartgetMatch2.setName("NBA季前");
		tartgetMatch2.setNameF("NBA季前");
		tartgetMatch2.setSclassType(4);
		tartgetMatch2.setColor("#FF0000");
		tartgetMatch2.setMatchTimeStr("2014-10-10 08:00:00");
		tartgetMatch2.setMatchTime(DateUtils.parseDate("2014-10-10 08:00:00", "yyyy-MM-dd HH:mm:ss"));
		tartgetMatch2.setMatchState(1);
		tartgetMatch2.setRemainTime(0);
		tartgetMatch2.setHomeTeamId(21);
		tartgetMatch2.setHomeTeam("休斯顿火箭");
		tartgetMatch2.setHomeTeamF("侯斯頓火箭");
		tartgetMatch2.setGuestTeamId(22);
		tartgetMatch2.setGuestTeam("孟菲斯灰熊");
		tartgetMatch2.setGuestTeamF("孟菲斯灰熊");
		tartgetMatch2.setHomeTeamRank("西3");
		tartgetMatch2.setGuestTeamRank("西12");
		tartgetMatch2.setHomeScore(15);
		tartgetMatch2.setGuestScore(21);
		tartgetMatch2.setHomeOne(15);
		tartgetMatch2.setGuestOne(21);
		tartgetMatch2.setHomeTwo(0);
		tartgetMatch2.setGuestTwo(0);
		tartgetMatch2.setHomeThree(0);
		tartgetMatch2.setGuestThree(0);
		tartgetMatch2.setHomeFour(0);
		tartgetMatch2.setGuestFour(0);
		tartgetMatch2.setAddTime(0);
		tartgetMatch2.setHomeAddTime1(0);
		tartgetMatch2.setGuestAddTime1(0);
		tartgetMatch2.setHomeAddTime2(0);
		tartgetMatch2.setGuestAddTime2(0);
		tartgetMatch2.setHomeAddTime3(0);
		tartgetMatch2.setGuestAddTime3(0);
		tartgetMatch2.setAddTechnic(false);
		tartgetMatch2.setTv("");
		tartgetMatch2.setExplainContent("");
		tartgetMatch2.setMiddleMatchState("1");
		tartgetMatch2.setIsNeutral(true);
		checkMatch(match2,tartgetMatch2);
		
	}
	
	
	@Test
	public void testParseBBMatchInfoDataOfDateFromStrings() throws ParseException{
		List<String> strs=new ArrayList<String>();
		strs.add("197124^1^1^NBA季前,NBA季前^4^#FF0000^2014/10/23 7:00:00^0^^6^奥兰多魔术,奧蘭多魔術^21^休斯顿火箭,侯斯頓火箭^^^^^^^^^^^^^0^^^^^^^True^^^0^14-15赛季^季前赛^^^");
		strs.add("197125^1^1^NBA季前,NBA季前^4^#FF0000^2014/10/23 7:30:00^1^^15^多伦多猛龙,多倫多速龍^2144^海法热马卡比,海法馬卡比^^^15^21^15^21^^^^^^^0^^^^^^^^^^1^14-15赛季^季前赛^^^");
		List<BBMatchInfoData> matches=parseService.parseBBMatchInfoDataOfScheduleFromStrings(strs);
		assertTrue(null!=matches&&!matches.isEmpty()&&matches.size()==2);
		BBMatchInfoData match1 = matches.get(0);
		BBMatchInfoData match2 = matches.get(1);
		BBMatchInfoData tartgetMatch=new BBMatchInfoData();
		tartgetMatch.setId(197124);
		tartgetMatch.setCupLeagueId(1);
		tartgetMatch.setCupLeague(1);
		tartgetMatch.setName("NBA季前");
		tartgetMatch.setNameF("NBA季前");
		tartgetMatch.setSclassType(4);
		tartgetMatch.setColor("#FF0000");
		tartgetMatch.setMatchTimeStr("2014-10-23 07:00:00");
		tartgetMatch.setMatchTime(DateUtils.parseDate("2014-10-23 07:00:00", "yyyy-MM-dd hh:mm:ss"));
		tartgetMatch.setMatchState(0);
		tartgetMatch.setRemainTime(0);
		tartgetMatch.setHomeTeamId(6);
		tartgetMatch.setHomeTeam("奥兰多魔术");
		tartgetMatch.setHomeTeamF("奧蘭多魔術");
		tartgetMatch.setGuestTeamId(21);
		tartgetMatch.setGuestTeam("休斯顿火箭");
		tartgetMatch.setGuestTeamF("侯斯頓火箭");
		tartgetMatch.setHomeTeamRank("");
		tartgetMatch.setGuestTeamRank("");
		tartgetMatch.setHomeScore(0);
		tartgetMatch.setGuestScore(0);
		tartgetMatch.setHomeOne(0);
		tartgetMatch.setGuestOne(0);
		tartgetMatch.setHomeTwo(0);
		tartgetMatch.setGuestTwo(0);
		tartgetMatch.setHomeThree(0);
		tartgetMatch.setGuestThree(0);
		tartgetMatch.setHomeFour(0);
		tartgetMatch.setGuestFour(0);
		tartgetMatch.setAddTime(0);
		tartgetMatch.setHomeAddTime1(0);
		tartgetMatch.setGuestAddTime1(0);
		tartgetMatch.setHomeAddTime2(0);
		tartgetMatch.setGuestAddTime2(0);
		tartgetMatch.setHomeAddTime3(0);
		tartgetMatch.setGuestAddTime3(0);
		tartgetMatch.setAddTechnic(true);
		tartgetMatch.setTv("");
		tartgetMatch.setExplainContent("");
		tartgetMatch.setMiddleMatchState("0");
		tartgetMatch.setIsNeutral(false);
		tartgetMatch.setSeason("14-15赛季");
		tartgetMatch.setMatchType("季前赛");
		tartgetMatch.setMatchClass("");
		tartgetMatch.setMatchSubClass("");
		checkMatch(match1,tartgetMatch);
		BBMatchInfoData tartgetMatch2=new BBMatchInfoData() ;
		tartgetMatch2.setId(197125);
		tartgetMatch2.setCupLeagueId(1);
		tartgetMatch2.setCupLeague(1);
		tartgetMatch2.setName("NBA季前");
		tartgetMatch2.setNameF("NBA季前");
		tartgetMatch2.setSclassType(4);
		tartgetMatch2.setColor("#FF0000");
		tartgetMatch2.setMatchTimeStr("2014-10-23 07:30:00");
		tartgetMatch2.setMatchTime(DateUtils.parseDate("2014-10-23 07:30:00", "yyyy-MM-dd HH:mm:ss"));
		tartgetMatch2.setMatchState(1);
		tartgetMatch2.setRemainTime(0);
		tartgetMatch2.setHomeTeamId(15);
		tartgetMatch2.setHomeTeam("多伦多猛龙");
		tartgetMatch2.setHomeTeamF("多倫多速龍");
		tartgetMatch2.setGuestTeamId(2144);
		tartgetMatch2.setGuestTeam("海法热马卡比");
		tartgetMatch2.setGuestTeamF("海法馬卡比");
		tartgetMatch2.setHomeTeamRank("");
		tartgetMatch2.setGuestTeamRank("");
		tartgetMatch2.setHomeScore(15);
		tartgetMatch2.setGuestScore(21);
		tartgetMatch2.setHomeOne(15);
		tartgetMatch2.setGuestOne(21);
		tartgetMatch2.setHomeTwo(0);
		tartgetMatch2.setGuestTwo(0);
		tartgetMatch2.setHomeThree(0);
		tartgetMatch2.setGuestThree(0);
		tartgetMatch2.setHomeFour(0);
		tartgetMatch2.setGuestFour(0);
		tartgetMatch2.setAddTime(0);
		tartgetMatch2.setHomeAddTime1(0);
		tartgetMatch2.setGuestAddTime1(0);
		tartgetMatch2.setHomeAddTime2(0);
		tartgetMatch2.setGuestAddTime2(0);
		tartgetMatch2.setHomeAddTime3(0);
		tartgetMatch2.setGuestAddTime3(0);
		tartgetMatch2.setAddTechnic(false);
		tartgetMatch2.setTv("");
		tartgetMatch2.setExplainContent("");
		tartgetMatch2.setMiddleMatchState("1");
		tartgetMatch2.setIsNeutral(true);
		tartgetMatch2.setSeason("14-15赛季");
		tartgetMatch2.setMatchType("季前赛");
		tartgetMatch2.setMatchClass("");
		tartgetMatch2.setMatchSubClass("");
		checkMatch(match2,tartgetMatch2);
		
	}
	
	@Test
	public void testParseBBMatchInfoDataRealTimeFromStrings() throws ParseException{
		List<String> strs=new ArrayList<String>();
		strs.add("198619^4^09:44^66^61^20^20^21^16^25^25^0^0^0^^4^^^^^^^^^1.61,2.23");
		List<BBMatchInfoData> matches = parseService.parseBBMatchInfoDataRealTimeFromStrings(strs);
		assertTrue(null!=matches&&matches.size()==1);
		BBMatchInfoData tartgetMatch=new BBMatchInfoData();
		tartgetMatch.setId(198619);
		tartgetMatch.setMatchState(4);
		tartgetMatch.setRemainTime(584);
		tartgetMatch.setHomeScore(66);
		tartgetMatch.setGuestScore(61);
		tartgetMatch.setHomeOne(20);
		tartgetMatch.setGuestOne(20);
		tartgetMatch.setHomeTwo(21);
		tartgetMatch.setGuestTwo(16);
		tartgetMatch.setHomeThree(25);
		tartgetMatch.setGuestThree(25);
		tartgetMatch.setHomeFour(0);
		tartgetMatch.setGuestFour(0);
		tartgetMatch.setAddTime(0);
		tartgetMatch.setExplainContent("");
		tartgetMatch.setSclassType(4);
		tartgetMatch.setHomeAddTime1(0);
		tartgetMatch.setGuestAddTime1(0);
		tartgetMatch.setHomeAddTime2(0);
		tartgetMatch.setGuestAddTime2(0);
		tartgetMatch.setHomeAddTime3(0);
		tartgetMatch.setGuestAddTime3(0);
		tartgetMatch.setExplain2("");
		tartgetMatch.setAddTechnic(false);
		tartgetMatch.setHomeWinOdds(new BigDecimal("1.61"));
		tartgetMatch.setGuestWinOdds(new BigDecimal("2.23"));
		
		BBMatchInfoData match1=matches.get(0);
		checkMatch(match1,tartgetMatch);
	}

	private void checkMatch(BBMatchInfoData match, BBMatchInfoData tartgetMatch) {
		assertTrue(null!=match&&null!=tartgetMatch);
		assertTrue(tartgetMatch.getId()==match.getId());
		assertTrue(tartgetMatch.getCupLeagueId()==match.getCupLeagueId());
		assertTrue(tartgetMatch.getCupLeague()==match.getCupLeague());
		assertTrue(StringUtils.equals(tartgetMatch.getName(), match.getName()));
		assertTrue(StringUtils.equals(tartgetMatch.getNameF(), match.getNameF()));
		assertTrue(tartgetMatch.getSclassType()==match.getSclassType());
		assertTrue(StringUtils.equals(tartgetMatch.getColor(), match.getColor()));
		assertTrue(StringUtils.equals(tartgetMatch.getMatchTimeStr(), match.getMatchTimeStr()));
		assertTrue(null!=tartgetMatch.getMatchTime()?(tartgetMatch.getMatchTime().equals(match.getMatchTime())):null==match.getMatchTime());
		assertTrue(tartgetMatch.getMatchState()==match.getMatchState());
		assertTrue(tartgetMatch.getRemainTime()==match.getRemainTime());
		assertTrue(tartgetMatch.getHomeTeamId()==match.getHomeTeamId());
		assertTrue(StringUtils.equals(tartgetMatch.getHomeTeam(), match.getHomeTeam()));
		assertTrue(StringUtils.equals(tartgetMatch.getHomeTeamF(), match.getHomeTeamF()));
		assertTrue(tartgetMatch.getHomeTeamE()==match.getHomeTeamE());//新接口没有英文名
		assertTrue(tartgetMatch.getGuestTeamId()==match.getGuestTeamId());
		assertTrue(StringUtils.equals(tartgetMatch.getGuestTeam(), match.getGuestTeam()));
		assertTrue(StringUtils.equals(tartgetMatch.getGuestTeamF(), match.getGuestTeamF()));
		assertTrue(tartgetMatch.getGuestTeamE()==match.getGuestTeamE());//新接口没有英文名
		assertTrue(StringUtils.equals(tartgetMatch.getHomeTeamRank(), match.getHomeTeamRank()));
		assertTrue(StringUtils.equals(tartgetMatch.getGuestTeamRank(), match.getGuestTeamRank()));
		assertTrue(tartgetMatch.getHomeScore()==match.getHomeScore());
		assertTrue(tartgetMatch.getGuestScore()==match.getGuestScore());
		assertTrue(tartgetMatch.getHomeOne()==match.getHomeOne());
		assertTrue(tartgetMatch.getGuestOne()==match.getGuestOne());
		assertTrue(tartgetMatch.getHomeTwo()==match.getHomeTwo());
		assertTrue(tartgetMatch.getGuestTwo()==match.getGuestTwo());
		assertTrue(tartgetMatch.getHomeThree()==match.getHomeThree());
		assertTrue(tartgetMatch.getGuestThree()==match.getGuestThree());
		assertTrue(tartgetMatch.getHomeFour()==match.getHomeFour());
		assertTrue(tartgetMatch.getGuestFour()==match.getGuestFour());
		assertTrue(tartgetMatch.getAddTime()==match.getAddTime());
		assertTrue(tartgetMatch.getHomeAddTime1()==match.getHomeAddTime1());
		assertTrue(tartgetMatch.getGuestAddTime1()==match.getGuestAddTime1());
		assertTrue(tartgetMatch.getHomeAddTime2()==match.getHomeAddTime2());
		assertTrue(tartgetMatch.getGuestAddTime2()==match.getGuestAddTime2());
		assertTrue(tartgetMatch.getHomeAddTime3()==match.getHomeAddTime3());
		assertTrue(tartgetMatch.getGuestAddTime3()==match.getGuestAddTime3());
		assertTrue(tartgetMatch.isAddTechnic()==match.isAddTechnic());
		assertTrue(StringUtils.equals(tartgetMatch.getTv(), match.getTv()));
		assertTrue(StringUtils.equals(tartgetMatch.getExplainContent(),match.getExplainContent()));
		assertTrue(StringUtils.equals(tartgetMatch.getMiddleMatchState(), match.getMiddleMatchState()));
		assertTrue(null==tartgetMatch.getIsNeutral()?(null==match.getIsNeutral()):tartgetMatch.getIsNeutral().equals(match.getIsNeutral()));
		assertTrue(StringUtils.equals(tartgetMatch.getSeason(), match.getSeason()));
		assertTrue(StringUtils.equals(tartgetMatch.getMatchType(), match.getMatchType()));
		assertTrue(StringUtils.equals(tartgetMatch.getMatchClass(), match.getMatchClass()));
		assertTrue(StringUtils.equals(tartgetMatch.getMatchSubClass(), match.getMatchSubClass()));
		assertTrue(null==tartgetMatch.getHomeWinOdds()?null==match.getHomeWinOdds():(tartgetMatch.getHomeWinOdds().equals(match.getHomeWinOdds())));
		assertTrue(null==tartgetMatch.getGuestWinOdds()?null==match.getGuestWinOdds():(tartgetMatch.getGuestWinOdds().equals(match.getGuestWinOdds())));
	}
	
	@Test
	public void testParseBBMatchInfoDataRealTimeFromStringsWhenRemainTimeFormatError() throws ParseException{
		List<String> strs=new ArrayList<String>();
		strs.add("198619^4^0944^66^61^20^20^21^16^25^25^0^0^0^^4^^^^^^^^^1.61,2.23");
		strs.add("198619^4^09:44^66^61^20^20^21^16^25^25^0^0^0^^4^^^^^^^^^1.61,2.23");
		List<BBMatchInfoData> matches = parseService.parseBBMatchInfoDataRealTimeFromStrings(strs);
		assertTrue(null!=matches&&matches.size()==1);
		BBMatchInfoData tartgetMatch=new BBMatchInfoData();
		tartgetMatch.setId(198619);
		tartgetMatch.setMatchState(4);
		tartgetMatch.setRemainTime(584);
		tartgetMatch.setHomeScore(66);
		tartgetMatch.setGuestScore(61);
		tartgetMatch.setHomeOne(20);
		tartgetMatch.setGuestOne(20);
		tartgetMatch.setHomeTwo(21);
		tartgetMatch.setGuestTwo(16);
		tartgetMatch.setHomeThree(25);
		tartgetMatch.setGuestThree(25);
		tartgetMatch.setHomeFour(0);
		tartgetMatch.setGuestFour(0);
		tartgetMatch.setAddTime(0);
		tartgetMatch.setExplainContent("");
		tartgetMatch.setSclassType(4);
		tartgetMatch.setHomeAddTime1(0);
		tartgetMatch.setGuestAddTime1(0);
		tartgetMatch.setHomeAddTime2(0);
		tartgetMatch.setGuestAddTime2(0);
		tartgetMatch.setHomeAddTime3(0);
		tartgetMatch.setGuestAddTime3(0);
		tartgetMatch.setExplain2("");
		tartgetMatch.setAddTechnic(false);
		tartgetMatch.setHomeWinOdds(new BigDecimal("1.61"));
		tartgetMatch.setGuestWinOdds(new BigDecimal("2.23"));
		
		BBMatchInfoData match1=matches.get(0);
		checkMatch(match1,tartgetMatch);
	}


}
