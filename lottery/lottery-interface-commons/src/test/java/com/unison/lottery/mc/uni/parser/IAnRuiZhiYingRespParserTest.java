package com.unison.lottery.mc.uni.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchesResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingResponse;
import com.davcai.lottery.platform.client.anruizhiying.parser.AnRuiZhiYingJCMatchesParser;
import com.davcai.lottery.platform.client.anruizhiying.parser.IAnRuiZhiYingRespParser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/spring-interface.xml")
public class IAnRuiZhiYingRespParserTest {
	String testResp = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+"<Matchs>"
			+"	<Match MATCH_ID=\"29987\" MATCH_TYPE=\"0\" LEAGUE_NO=\"周四001\" WEEK_TYPE=\"4\" LEAGUE_NAME=\"国际赛\" HOME_TEAM=\"阿曼U23\" GUEST_TEAM=\"乌兹别克U23\" PASS_HANDICAP=\"\" MATCH_STATUS=\"1\" MATCH_TIME=\"2012-03-29 20:00:00\"/>"
			+"	<Match MATCH_ID=\"29988\" MATCH_TYPE=\"0\" LEAGUE_NO=\"周四002\" WEEK_TYPE=\"4\" LEAGUE_NAME=\"欧罗巴联赛\" HOME_TEAM=\"马德里竞技\" GUEST_TEAM=\"汉诺威96\" PASS_HANDICAP=\"\" MATCH_STATUS=\"1\" MATCH_TIME=\"2012-03-30 03:05:00\"/>"
			+"	<Match MATCH_ID=\"29989\" MATCH_TYPE=\"0\" LEAGUE_NO=\"周四003\" WEEK_TYPE=\"4\" LEAGUE_NAME=\"欧罗巴联赛\" HOME_TEAM=\"阿尔克马尔\" GUEST_TEAM=\"巴伦西亚\" PASS_HANDICAP=\"\" MATCH_STATUS=\"1\" MATCH_TIME=\"2012-03-30 03:05:00\"/>"
			+" 	<Match MATCH_ID=\"29990\" MATCH_TYPE=\"0\" LEAGUE_NO=\"周四004\" WEEK_TYPE=\"4\" LEAGUE_NAME=\"欧罗巴联赛\" HOME_TEAM=\"沙尔克04\" GUEST_TEAM=\"毕尔巴鄂竞技\" PASS_HANDICAP=\"\" MATCH_STATUS=\"1\" MATCH_TIME=\"2012-03-30 03:05:00\"/>"
			+" 	<Match MATCH_ID=\"29991\" MATCH_TYPE=\"0\" LEAGUE_NO=\"周四005\" WEEK_TYPE=\"4\" LEAGUE_NAME=\"欧罗巴联赛\" HOME_TEAM=\"里斯本竞技\" GUEST_TEAM=\"哈尔科夫金工\" PASS_HANDICAP=\"\" MATCH_STATUS=\"1\" MATCH_TIME=\"2012-03-30 03:05:00\"/>"
			+" 	<Match MATCH_ID=\"29992\" MATCH_TYPE=\"0\" LEAGUE_NO=\"周四006\" WEEK_TYPE=\"4\" LEAGUE_NAME=\"南美解放者杯\" HOME_TEAM=\"博卡青年\" GUEST_TEAM=\"兵工厂\" PASS_HANDICAP=\"\" MATCH_STATUS=\"1\" MATCH_TIME=\"2012-03-30 06:45:00\"/>"
			+" 	<Match MATCH_ID=\"29993\" MATCH_TYPE=\"0\" LEAGUE_NO=\"周四007\" WEEK_TYPE=\"4\" LEAGUE_NAME=\"南美解放者杯\" HOME_TEAM=\"萨莫拉FC\" GUEST_TEAM=\"弗鲁米嫩塞\" PASS_HANDICAP=\"\" MATCH_STATUS=\"1\" MATCH_TIME=\"2012-03-30 09:00:00\"/>"
			+"</Matchs>";
	@Test
	public void testParseBody(){
		IAnRuiZhiYingRespParser iparse = new AnRuiZhiYingJCMatchesParser();
		AnRuiZhiYingResponse a = iparse.parseResp(testResp);
		Assert.assertEquals(Boolean.TRUE,Boolean.valueOf(a instanceof AnRuiZhiYingJCMatchesResponse));
		AnRuiZhiYingJCMatchesResponse b = (AnRuiZhiYingJCMatchesResponse)a;
		Assert.assertEquals(7,b.getMatches().size());
		Assert.assertEquals("29990",b.getMatches().get(3).getMatchId());
	}
}
