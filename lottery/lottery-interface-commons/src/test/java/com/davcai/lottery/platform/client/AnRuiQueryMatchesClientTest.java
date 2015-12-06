package com.davcai.lottery.platform.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.davcai.lottery.platform.client.anruizhiying.AnRuiQueryMatchesClient;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchModel;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchesResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/spring-interface.xml")
public class AnRuiQueryMatchesClientTest {
	@Autowired
	private ILotteryPlatformQueryMatchesClient anRuiQueryMatchesClient;
	@Before
	public void setUp(){
		AnRuiQueryMatchesClient a = (AnRuiQueryMatchesClient)anRuiQueryMatchesClient;
		a.setInterfaceUrl("http://124.254.63.10:7100/SportteryPv/football_pv.xml");
	}
	@Test
	public void testPostByMatchType(){
		AnRuiZhiYingJCMatchesResponse response=(AnRuiZhiYingJCMatchesResponse)anRuiQueryMatchesClient.postByMatchType("jczq");
		Assert.assertTrue(response.getMatches().size() > 0);
		for (AnRuiZhiYingJCMatchModel m: response.getMatches()) {
			System.out.println(m.toString());
		}
	}
	@Test
	public void testPostByMatchTypeByErrorCode(){
		AnRuiZhiYingJCMatchesResponse response=(AnRuiZhiYingJCMatchesResponse)anRuiQueryMatchesClient.postByMatchType("fajsl");
		Assert.assertTrue(response.getMatches().size() == 0);
		for (AnRuiZhiYingJCMatchModel m: response.getMatches()) {
			System.out.println(m.toString());
		}
	}
}
