package com.unison.lottery.weibo.dataservice.commons.parse;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.unison.lottery.weibo.dataservice.commons.constants.DataInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.model.BBMatchInfoDocument;
import com.unison.lottery.weibo.dataservice.commons.model.BaseNameModel;
import com.unison.lottery.weibo.dataservice.commons.model.FBBFDataModel;
import com.unison.lottery.weibo.dataservice.commons.model.MatchAgendaModel;
/**
 * 球探新接口数据测试
 * @author 彭保星
 * @since 2014年10月9日下午4:23:03
 */
public class ParseServiceTest {
	
	
	private ParseService parseService;
	private String stringToParse;
	
	
	@Before
	public void setUp(){
		parseService=new ParseServiceImpl();
//		stringToParse = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
//		+ "<list>"
//		+ "<match>"
//		+ "<ID>1063054</ID>"
//		+ "<color>#FF2F73</color>"
//		+ "<leagueID>435</leagueID>"
//		+ "<kind>2</kind>"
//		+ "<level>0</level>"
//		+ "<league>女亚冠U16,女亞冠U16,AFC WU16</league>"
//		+ "<subLeague></subLeague>"
//		+ "<time>2014/10/9 16:30:00</time>"
//		+ "<time2>2014/10/9 16:30:00</time2>"
//		+ "<home>中国台北女足U16,中國臺北女足U16,Chinese Taipei (w) U16,12187</home>"
//		+ "<away>缅甸女足U16,緬甸女足U16,Myanmar (w) U16,9183</away>"
//		+ "<state>1</state>"
//		+ "<homeScore>1</homeScore>"
//		+ "<awayScore>0</awayScore>"
//		+ "<bc1></bc1>"
//		+ "<bc2></bc2>"
//		+ "<red1>0</red1>"
//		+ "<red2>0</red2>"
//		+ "<yellow1>0</yellow1>"
//		+ "<yellow2>0</yellow2>"
//		+ "<order1></order1>"
//		+ "<order2></order2>"
//		+ "<explain><![CDATA[]]></explain>"
//		+ "<zl>True</zl>"
//		+ "<tv><![CDATA[]]></tv>"
//		+ "<lineup></lineup>"
//		+ "<explain2><![CDATA[]]></explain2>"
//		+ "</match>"
//		+ "</list>";
		stringToParse = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<list><match><ID>1064561</ID>"
				+ "<color>#006666</color>"
				+ "<leagueID>81</leagueID>"
				+ "<kind>2</kind>"
				+ "<level>1</level>"
				+ "<league>西杯,西盃,SPA CUP</league>"
				+ "<subLeague></subLeague>"
				+ "<time>2014/10/17 3:00:00</time>"
				+ "<time2>2014/10/17 4:01:27</time2>"
				+ "<home>拉斯帕尔马斯,拉斯彭馬斯,Las Palmas,991</home>"
				+ "<away>努曼西亚,紐文西亞,Numancia,87</away>"
				+ "<state>-1</state>"
				+ "<homeScore>2</homeScore>"
				+ "<awayScore>0</awayScore>"
				+ "<bc1>1</bc1>"
				+ "<bc2>0</bc2>"
				+ "<red1>0</red1>"
				+ "<red2>1</red2>"
				+ "<yellow1>4</yellow1>"
				+ "<yellow2>3</yellow2>"
				+ "<order1>西乙5</order1>"
				+ "<order2>西乙18</order2>"
				+ "<explain><![CDATA[]]></explain>"
				+ "<zl>False</zl>"
				+ "<tv><![CDATA[]]></tv>"
				+ "<lineup></lineup>"
				+ "<explain2><![CDATA[]]></explain2>"
				+ "</match></list>";

	}
	
	/**
	 * 测试足球当天比分数据解析
	 */
	@Test
	public void testParseXmlFromStringWithJAXB(){
		Object xmlData = parseService.parseXmlFromStringWithJAXB(stringToParse, DataInterfaceName.FBBFDayOdds);
		assertTrue(null!=xmlData);
		assertTrue(xmlData instanceof FBBFDataModel);
		FBBFDataModel fBBFData=(FBBFDataModel) xmlData;
		assertTrue(null!=fBBFData.matchAgendaList);
		for(MatchAgendaModel matchAgenda:fBBFData.matchAgendaList){
			assertTrue(null!=matchAgenda);
			assertEquals("1063054", matchAgenda.matchId);
			assertEquals("#FF2F73", matchAgenda.colour);
			assertEquals("435", matchAgenda.leagueID);
			assertEquals("2", matchAgenda.kind);
			assertTrue(null!=matchAgenda.getMatchTypeName());
			BaseNameModel baseName = matchAgenda.getMatchTypeName();
			assertEquals("女亚冠U16", baseName.getChineseName());
			assertEquals("女亞冠U16", baseName.getTraditional());
			assertEquals("AFC WU16", baseName.getEnglishName());
			assertEquals("2014/10/9 16:30:00", matchAgenda.matchTimeWithHourAndMin);
			assertEquals(new Date("2014/10/9 16:30:00"), matchAgenda.matchBeginTime);
			assertEquals("1", matchAgenda.homeScores);
			assertEquals("0", matchAgenda.awayScores);
			assertTrue(null!=matchAgenda.getHomeName());
			BaseNameModel baseName1 = matchAgenda.getHomeName();
			assertEquals("中国台北女足U16", baseName1.getChineseName());
			assertEquals("中國臺北女足U16", baseName1.getTraditional());
			assertEquals("Chinese Taipei (w) U16", baseName1.getEnglishName());
			assertEquals("12187", matchAgenda.getHomeTeamId());
			assertTrue(null!=matchAgenda.getAwayName());
			BaseNameModel baseName2 = matchAgenda.getAwayName();
			assertEquals("缅甸女足U16", baseName2.getChineseName());
			assertEquals("緬甸女足U16", baseName2.getTraditional());
			assertEquals("Myanmar (w) U16", baseName2.getEnglishName());
			assertEquals("9183", matchAgenda.getAwayTeamId());
			assertEquals("", matchAgenda.homeHalfScores);
			assertEquals("", matchAgenda.awayHalfScores);
			assertEquals("0", matchAgenda.homeRedCards);
			assertEquals("0", matchAgenda.awayRedCards);
			assertEquals("0", matchAgenda.homeYellowCards);
			assertEquals("0", matchAgenda.awayYellowCards);
			assertEquals("", matchAgenda.homeRanking);
			assertEquals("", matchAgenda.awayRanking);
			assertEquals("", matchAgenda.matchMessage);
			assertEquals("True", matchAgenda.isCenter);
			assertEquals("", matchAgenda.TVLive);
			assertEquals("", matchAgenda.isHaveBattleArray);
			assertEquals("1", matchAgenda.matchStatus);
		}
		
	}

	
	@Test
	public  void testParseBasketBallTodayBF(){
		String content="<?xml  version=\"1.0\" encoding=\"utf-8\"?><c><h><![CDATA[193652^1^1^NBA季前,NBA季前^4^#FF0000^2014/10/10 7:30:00^0^^9^底特律活塞,底特律活塞^12^密尔沃基雄鹿,密爾沃基公鹿^东14^东3^^^^^^^^^^^0^^^^^^^^^^0]]></h><h><![CDATA[193653^1^1^NBA季前,NBA季前^4^#FF0000^2014/10/10 8:00:00^0^^21^休斯顿火箭,侯斯頓火箭^22^孟菲斯灰熊,孟菲斯灰熊^西3^西12^^^^^^^^^^^0^^^^^^^^^^0]]></h></c>";
		BBMatchInfoDocument matchInfoDoc = (BBMatchInfoDocument) 
				parseService.parseXmlFromStringWithJAXB(content, DataInterfaceName.BBMatchInfoToday);
		assertTrue(null!=matchInfoDoc);
		assertTrue(null!=matchInfoDoc.contentStringList&&!matchInfoDoc.contentStringList.isEmpty());
		for(String contentString :matchInfoDoc.contentStringList){
			System.out.println("contentString="+contentString);
			assertTrue(StringUtils.isNotBlank(contentString));
		}
	}
	
	@Test
	public  void testParseBasketBallBFForDate(){
		String content="<?xml  version=\"1.0\" encoding=\"utf-8\"?><c><h><![CDATA[197124^1^1^NBA季前,NBA季前^4^#FF0000^2014/10/23 7:00:00^0^^6^奥兰多魔术,奧蘭多魔術^21^休斯顿火箭,侯斯頓火箭^^^^^^^^^^^^^0^^^^^^^^^^0^14-15赛季^季前赛^^^]]></h><h><![CDATA[197125^1^1^NBA季前,NBA季前^4^#FF0000^2014/10/23 7:30:00^0^^15^多伦多猛龙,多倫多速龍^2144^海法热马卡比,海法馬卡比^^^^^^^^^^^^^0^^^^^^^^^^0^14-15赛季^季前赛^^^]]></h></c>";
		BBMatchInfoDocument matchInfoDoc = (BBMatchInfoDocument) 
				parseService.parseXmlFromStringWithJAXB(content, DataInterfaceName.BBMatchInfo);
		assertTrue(null!=matchInfoDoc);
		assertTrue(null!=matchInfoDoc.contentStringList&&!matchInfoDoc.contentStringList.isEmpty());
		for(String contentString :matchInfoDoc.contentStringList){
			System.out.println("contentString="+contentString);
			assertTrue(StringUtils.isNotBlank(contentString));
		}
	}

}
