package com.xhcms.lottery.commons.util.bonus;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.persist.service.BetOptionService;
import com.xhcms.lottery.commons.persist.service.CheckMatchService;
import com.xhcms.lottery.commons.persist.service.MatchService;

/**
 * 竞彩篮球最大最小奖金集成测试
 * @author lei.li
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-maxAndMinBonusUtil-spring.xml"})
public class JCLQMaxMinBonusTest {
	@Autowired
	private MaxAndMinBonusUtil maxAndMinBonusUtil;
	@Autowired
	private CheckMatchService checkMatchService;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private BetOptionService betOptionService;
	
	/**
	 * 竞彩篮球胜负
	 */
	@Test
	public void testJCLQ_SF(){
		String lotteryId="JCLQ";
		String playId = "07_LC_2";
		String passtype="2@1,3@1";
		String betContent = "201505155301-530121-false-sf,201505165302-530221-false-sf,201505165303-530321-false-sf";
		MaxAndMinBonusModel calcResult = testCommon(betContent, playId, lotteryId, passtype);
		Assert.assertNotNull("not null", calcResult);
		float maxBonus = calcResult.getMaxBonus();
		float minBonus = calcResult.getMinBonus();
		Assert.assertEquals(78.8, maxBonus, 1);
		Assert.assertEquals(2.76, minBonus, 1);
	}
	
	/**
	 * 竞彩篮球让分胜负
	 */
	@Test
	public void testJCLQ_RFSF(){
		String lotteryId="JCLQ";
		String playId = "06_LC_2";
		String passtype="2@1";
		String betContent = "201505155301-530121-false-rfsf,201505165302-530221-false-rfsf,201505165303-530321-false-rfsf";
		MaxAndMinBonusModel calcResult = testCommon(betContent, playId, lotteryId, passtype);
		Assert.assertNotNull("not null", calcResult);
		float maxBonus = calcResult.getMaxBonus();
		float minBonus = calcResult.getMinBonus();
		Assert.assertEquals(19.36, maxBonus, 1);
		Assert.assertEquals(5.28, minBonus, 1);
	}

	/**
	 * 竞彩篮球大小分
	 */
	@Test
	public void testJCLQ_DXF(){
		String lotteryId="JCLQ";
		String playId = "09_LC_2";
		String passtype="3@4";
		String betContent = "201505155301-530112-false,201505165302-530212-false,201505165303-530312-false";
		MaxAndMinBonusModel calcResult = testCommon(betContent, playId, lotteryId, passtype);
		Assert.assertNotNull("not null", calcResult);
		float maxBonus = calcResult.getMaxBonus();
		float minBonus = calcResult.getMinBonus();
		Assert.assertEquals(27.82, maxBonus, 1);
		Assert.assertEquals(5.78, minBonus, 1);
	}
	
	/**
	 * 竞彩篮球胜分差
	 */
	@Test
	public void testJCLQ_SFC(){
		String lotteryId="JCLQ";
		String playId = "08_LC_2";
		String passtype="2@1";
		String betContent = "201505155301-5301111213141516060504030201-false,201505165302-5302111213141516060504030201-false";
		MaxAndMinBonusModel calcResult = testCommon(betContent, playId, lotteryId, passtype);
		Assert.assertNotNull("not null", calcResult);
		float maxBonus = calcResult.getMaxBonus();
		float minBonus = calcResult.getMinBonus();
		Assert.assertEquals(12168, maxBonus, 1);
		Assert.assertEquals(23.8, minBonus, 1);
	}
	
	/**
	 * 竞彩篮球混合2场比赛全选
	 */
	@Test
	public void testJCLQ_HH(){
		String lotteryId="JCLQ";
		String playId = "666_LCFH_2";
		String passtype="3@1";
		String betContent = "201505155301-530121-false-rfsf,201505155301-530121-false-sf,201505155301-5301131411121516010203040506-false-fc,201505155301-530112-false-dxf,201505165302-530221-false-rfsf,201505165302-530221-false-sf,201505165302-5302131411121516010203040506-false-fc,201505165302-530212-false-dxf,201505165303-530321-false-rfsf,201505165303-530321-false-sf,201505165303-5303131411121516010203040506-false-fc,201505165303-530312-false-dxf";
		MaxAndMinBonusModel calcResult = testCommon(betContent, playId, lotteryId, passtype);
		Assert.assertNotNull("not null", calcResult);
		float maxBonus = calcResult.getMaxBonus();
		float minBonus = calcResult.getMinBonus();
		Assert.assertEquals(677044.62, maxBonus, 1);//最大与澳客一样，与500有差别
		Assert.assertEquals(4.42, minBonus, 1);
	}
	
	@Test
	public void testJCLQ_ZH(){
		String lotteryId="JCLQ";
		String playId = "666_LCFH_2";
		String passtype="2@1";
		String betContent = "201505155301-5301131411121516-false-fc,201505165302-53022-false-rfsf";
		MaxAndMinBonusModel calcResult = testCommon(betContent, playId, lotteryId, passtype);
		Assert.assertNotNull("not null", calcResult);
		float maxBonus = calcResult.getMaxBonus();
		float minBonus = calcResult.getMinBonus();
		Assert.assertEquals(282.36, maxBonus, 1);
		Assert.assertEquals(19.92, minBonus, 1);
	}
	
	protected MaxAndMinBonusModel testCommon(String betContent, String playId, String lotteryId, String passtype) {
		String[] matchArr = betContent.split(",");
		Pattern p = Pattern.compile("-");
		List<BetMatch> matchList =matchService.makeMatchList(matchArr, p);
		betOptionService.combinBetOptions(playId, matchList,false);
		int code = checkMatchService.checkMatch(playId, matchList);
		MaxAndMinBonusModel result = null;
		if(code==0){
			Map<Long, BigDecimal> matchScoreMap = matchService.findDefaultScoreByMatchIdAndLotteryId(matchList,lotteryId);
			result = maxAndMinBonusUtil.maxAndMinBonusForJC(matchList, lotteryId, matchScoreMap,passtype);
			assertTrue(result.getMaxBonus()>0);
			assertTrue(result.getMinBonus()>0);
			assertTrue(result.getDetailExpress().size() > 0);
			printBonusModel(result);
		}
		return result;
	}
	
	
	private void printBonusModel(MaxAndMinBonusModel maxAndMinBonusModel) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		System.out.println("最小奖金："+df.format(maxAndMinBonusModel.getMinBonus()));
		System.out.println("最小详细：");
		String a1="";
		double sum1=1;
		for (double d : maxAndMinBonusModel.getMinDetailExpress()) {
			a1+=(d + "*");
			sum1*=d;
		}
		a1+="2" + "*1倍=" + df.format(sum1*2);
		System.out.println(a1);
		System.out.println();
		System.out.println("最大奖金："+df.format(maxAndMinBonusModel.getMaxBonus()));
		System.out.println("最大详细：");
		for (double[] fs : maxAndMinBonusModel.getDetailExpress()) {
			String a="";
			double sum=1;
			for (double d : fs) {
				a+=(d + "*");
				sum*=d;
			}
			a+="2" + "*1倍=" + df.format(sum*2);
			System.out.println(a);
		}
		System.out.println();
		System.out.println("共"+maxAndMinBonusModel.getNote()+"注，总金额："+maxAndMinBonusModel.getBuyAmount()+"元");
	}
}