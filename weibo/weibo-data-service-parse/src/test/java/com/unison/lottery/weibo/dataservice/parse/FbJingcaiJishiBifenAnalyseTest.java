package com.unison.lottery.weibo.dataservice.parse;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.AnalyseQiutanJIshiIBiFen;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;

public class FbJingcaiJishiBifenAnalyseTest {

	private AnalyseQiutanJIshiIBiFen analyseQiuTan;
	@Before
	public void init(){
		analyseQiuTan = new AnalyseQiutanJIshiIBiFen();
	}
	
	@Test
	public void testJishibifenTest(){
		List<QtMatchBaseModel> qtMatchBaseModels = analyseQiuTan.analyseJingcaiJishiBifen("20150402094251$$自由杯^89^1!日职乙^284^1!英甲^39^1!智利甲^415^1$$1113989^1356^-1^20150401163000^^悉尼联盟^莱卡特老虎^2^2^1^0^0^0^3^1^0.5^^^0^5^1^90,2-2;;1,2-2;4-1;1^^0");
		assertNotNull(qtMatchBaseModels);
		System.out.print(qtMatchBaseModels);
	}
}
