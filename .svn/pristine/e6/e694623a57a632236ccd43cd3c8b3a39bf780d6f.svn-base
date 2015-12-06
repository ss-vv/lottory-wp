package com.unison.lottery.weibo.dataservice.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.unison.lottery.weibo.dataservice.commons.parse.ExtractEngine;
import com.unison.lottery.weibo.dataservice.commons.parse.ExtractException;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;

public class ExtractEngine4BBMatchInfoDataOfDateTest {

	private ExtractEngine<BBMatchInfoData> engine;


	@Before
	public void setup(){
		engine=ExtractEngineFactory.configBBMatchInfo4ScheduleEngine();
	}
	
	@Test
	public void testParseBBMatchInfo() throws ExtractException{
		String text="197124^1^1^NBA季前,NBA季前^4^#FF0000^2014/10/23 10:00:00^0^^6^奥兰多魔术,奧蘭多魔術^21^休斯顿火箭,侯斯頓火箭^^^^^15^16^^^^^^^0^^^^^^^^^^0^14-15赛季^季前赛^^^";
		BBMatchInfoData matchBean = engine.extractBeanFromText(text);
		assertTrue(null!=matchBean);
		assertTrue(197124==matchBean.getId());
		assertTrue(1==matchBean.getCupLeagueId());
		assertTrue(1==matchBean.getCupLeague());
		assertTrue(StringUtils.equals("NBA季前", matchBean.getName()));
		assertTrue(StringUtils.equals("NBA季前", matchBean.getNameF()));
		assertTrue(4==matchBean.getSclassType());
		assertTrue(StringUtils.equals("#FF0000", matchBean.getColor()));
		assertTrue(StringUtils.equals("2014-10-23 10:00:00", matchBean.getMatchTimeStr()));
		assertTrue(0==matchBean.getMatchState());
		assertTrue(0==matchBean.getRemainTime());
		assertTrue(6==matchBean.getHomeTeamId());
		assertTrue(StringUtils.equals("奥兰多魔术", matchBean.getHomeTeam()));
		assertTrue(StringUtils.equals("奧蘭多魔術", matchBean.getHomeTeamF()));
		assertTrue(null==matchBean.getHomeTeamE());//新接口没有英文名
		assertTrue(21==matchBean.getGuestTeamId());
		assertTrue(StringUtils.equals("休斯顿火箭", matchBean.getGuestTeam()));
		assertTrue(StringUtils.equals("侯斯頓火箭", matchBean.getGuestTeamF()));
		assertTrue(null==matchBean.getGuestTeamE());//新接口没有英文名
		assertTrue(StringUtils.equals("", matchBean.getHomeTeamRank()));
		assertTrue(StringUtils.equals("", matchBean.getGuestTeamRank()));
		assertTrue(0==matchBean.getHomeScore());
		assertTrue(0==matchBean.getGuestScore());
		assertTrue(15==matchBean.getHomeOne());
		assertTrue(16==matchBean.getGuestOne());
		assertTrue(0==matchBean.getHomeTwo());
		assertTrue(0==matchBean.getGuestTwo());
		assertTrue(0==matchBean.getHomeThree());
		assertTrue(0==matchBean.getGuestThree());
		assertTrue(0==matchBean.getHomeFour());
		assertTrue(0==matchBean.getGuestFour());
		assertTrue(0==matchBean.getAddTime());
		assertTrue(0==matchBean.getHomeAddTime1());
		assertTrue(0==matchBean.getGuestAddTime1());
		assertTrue(0==matchBean.getHomeAddTime2());
		assertTrue(0==matchBean.getGuestAddTime2());
		assertTrue(0==matchBean.getHomeAddTime3());
		assertTrue(0==matchBean.getGuestAddTime3());
		assertFalse(matchBean.isAddTechnic());
		assertTrue(StringUtils.isBlank(matchBean.getTv()));
		assertTrue(StringUtils.isBlank(matchBean.getExplainContent()));
		assertTrue(null==matchBean.getHomeWinOdds());
		assertTrue(null==matchBean.getGuestWinOdds());
		assertTrue(StringUtils.equals("0", matchBean.getMiddleMatchState()));
		assertTrue(null==matchBean.getIsNeutral());
		assertTrue(StringUtils.equals("14-15赛季", matchBean.getSeason()));
		assertTrue(StringUtils.equals("季前赛", matchBean.getMatchType()));
		assertTrue(StringUtils.equals("", matchBean.getMatchClass()));
		assertTrue(StringUtils.equals("", matchBean.getMatchSubClass()));
				
	}
	
}
