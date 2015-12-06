package com.unison.lottery.mc.uni.parser;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.IssueInfo;





@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/spring-interface.xml")
public class QueryIssueResponseParserTest {

	@Autowired
	private QueryIssueResponseParser parser;
	
	/**
	 * 检查只有一个issueinfo节点的情况
	 * @throws ParseException
	 */
	@Test
	public void testParseOneIssueinfoBody() throws ParseException {
		String response="transcode=101&msg=<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"101\" partnerid=\"349182\" version=\"1.0\" time=\"20120904093545\" /><body><issueinfo lotteryId=\"JX11X5\" issueNumber=\"12090404\" startTime=\"\" stopTime=\"2011/08/21 13:00:00\" closeTime=\"2011/08/21 13:00:00\" status=\"1\" bonusCode=\"\" bonusInfo=\"\"/></body></msg>&key=1fed5725110e7dbb5d81bc3945319808&partnerid=349182";
		QueryIssueResponseParserStatus status = new QueryIssueResponseParserStatus();
		int code = parser.parse(response, status);
		Assert.assertEquals(101, code);
		Assert.assertTrue(null!=status.getIssueinfos()&&!status.getIssueinfos().isEmpty());
		Assert.assertEquals(status.getIssueinfos().size(), 1);
		IssueInfo issueinfo=status.getIssueinfos().get(0);
		checkIssueinfo(issueinfo);
	}
	
	@Test 
	public void testBodyContainsAmpChar()throws ParseException {
		String response = "transcode=101&msg=<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"101\" partnerid=\"349032\" version=\"1.0\" time=\"20130604124855\" /><body><issueinfo lotteryId=\"SSQ\" issueNumber=\"2013058\" startTime=\"\" stopTime=\"2013/5/21 19:51:00\" closeTime=\"2013/5/21 20:00:00\" prizeTime=\"2013/5/21 22:58:26\" status=\"4\" bonusCode=\"08,11,17,21,23,24 | 05\" bonusInfo=\"一等奖 13 6573318 &lt;br/&gt;二等奖 243 126253 &lt;br/&gt;三等奖 1130 3000 &lt;br/&gt;四等奖 66539 200 &lt;br/&gt;五等奖 1319685 10 &lt;br/&gt;六等奖 6699351 5 &lt;br/&gt;\" /></body></msg>&key=FAE157EAFBD25AD9946A78272C7F92D1&partnerid=349032";
		QueryIssueResponseParserStatus status = new QueryIssueResponseParserStatus();
		String oldKey = parser.getConfig().getKey();
		parser.getConfig().setKey("8C55EBA825E3B07E04EA4DADF0D48FCC");
		int code = parser.parse(response, status);
		Assert.assertEquals(101, code);
		Assert.assertTrue(null!=status.getIssueinfos()&&!status.getIssueinfos().isEmpty());
		Assert.assertEquals(status.getIssueinfos().size(), 1);
		parser.getConfig().setKey(oldKey);
	}
	
	/**
	 * 检查有多个issueinfo节点的情况
	 * @throws ParseException
	 */
	@Test
	public void testParseManyIssueinfosBody() throws ParseException {
		String response="transcode=101&msg=<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"101\" partnerid=\"349182\" version=\"1.0\" time=\"20120904093545\" /><body><issueinfos lotteryId=\"JX11X5\" ><issueinfo  issueNumber=\"12090404\" startTime=\"\" stopTime=\"2011/08/21 13:00:00\" closeTime=\"2011/08/21 13:00:00\" status=\"1\" bonusCode=\"01 02 03 04 05\" bonusInfo=\"\"/><issueinfo  issueNumber=\"12090403\" startTime=\"\" stopTime=\"2011/08/21 13:00:00\" closeTime=\"2011/08/21 13:00:00\" status=\"1\" bonusCode=\"01 02 03 04 05\" bonusInfo=\"\"/></issueinfos></body></msg>&key=a470a270d52e2056751988330ae6fb53&partnerid=349182";
		QueryIssueResponseParserStatus status=new QueryIssueResponseParserStatus();
		int code = parser.parse(response, status);
		Assert.assertEquals(101, code);
		Assert.assertTrue(null!=status.getIssueinfos()&&!status.getIssueinfos().isEmpty());
		Assert.assertTrue(status.getIssueinfos().size()==2);
		for(IssueInfo issueinfo:status.getIssueinfos()){
			checkIssueinfo(issueinfo);
		}
	}

	private void checkIssueinfo(IssueInfo issueinfo) {
		System.out.println("issueinfo="+issueinfo);
		Assert.assertTrue(null!=issueinfo);
		Assert.assertTrue(StringUtils.isNotBlank(issueinfo.getLotteryId()));
		Assert.assertTrue(StringUtils.isNotBlank(issueinfo.getIssueNumber()));
		Assert.assertTrue(null==issueinfo.getStartTime());
		Assert.assertTrue(null!=issueinfo.getCloseTime());
		Assert.assertTrue(null!=issueinfo.getStopTime());
	}
}
