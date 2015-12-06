package com.xhcms.lottery.commons.util.bonus;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.persist.service.BetOptionService;
import com.xhcms.lottery.commons.persist.service.CheckMatchService;
import com.xhcms.lottery.commons.persist.service.MatchService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-maxAndMinBonusUtil-spring.xml"})
public class MaxAndMinBonusUtilTest {
	@Autowired
	private MaxAndMinBonusUtil maxAndMinBonusUtil;
	@Autowired
	private CheckMatchService checkMatchService;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private BetOptionService betOptionService;
	
	@Test
	public void testMaxAndMinBonus(){
		String betContent = "201505155001-50010-false-spf,201505155001-50013-false-brqspf,201505165002-50023-false-brqspf,201505165002-500256-false-jqs,201505165002-50020241-false-bf";
//		betContent = "201505154002-400210202130313240414250515290-false-bf,201505154003-4003310-false-brqspf";
//		betContent = "201505154002-4002310-true-brqspf,201505154002-4002310-true-spf,201505154003-4003013-true-brqspf,201505154003-4003310-true-spf,201505154004-4004013-true-brqspf,201505154004-4004310-true-spf";
		
		String passtype="2@1";
		String playId = "555_ZCFH_2";
		String lotteryId="JCZQ";
		String[] matchArr = betContent.split(",");
		Pattern p = Pattern.compile("-");
		List<BetMatch> matchList =matchService.makeMatchList(matchArr, p);
		betOptionService.combinBetOptions(playId, matchList,false);
		int code = checkMatchService.checkMatch(playId, matchList);
		if(code==0){
			Map<Long, BigDecimal> matchScoreMap = matchService.findDefaultScoreByMatchIdAndLotteryId(matchList,lotteryId);
			MaxAndMinBonusModel result = maxAndMinBonusUtil.maxAndMinBonusForJC(matchList, lotteryId, matchScoreMap,passtype);
			assertTrue(result.getMaxBonus()>0);
			assertTrue(result.getMinBonus()>0);
			assertTrue(result.getDetailExpress().size() > 0);
			printBonusModel(result);
		}
	}
	
	private Set<Long> makeMatchIdSet(List<BetMatch> matchList) {
		Set<Long> result=null;
		if(null!=matchList&&!matchList.isEmpty()){
			result=new HashSet<Long>();
			for(BetMatch match:matchList){
				result.add(match.getMatchId());
			}
		}
		return result;
	}

	@Test
	public void testMaxAndMinBonus4Client(){
		Map<Long, List<List<BetContent>>> map = null;
		String passType = null;
		MaxAndMinBonusModel result = maxAndMinBonusUtil.maxAndMinBonus(map,passType);
		assertTrue(result.getMaxBonus()>0);
		assertTrue(result.getMinBonus()>0);
		assertTrue(result.getDetailExpress().size()==2);
		for(double[] detailExpress:result.getDetailExpress()){
			System.out.println("detailExpress="+ReflectionToStringBuilder.toString(detailExpress, ToStringStyle.MULTI_LINE_STYLE));
			
			
		}
	}
	
	@Test
	public void testComputeMinAndMaxOddsBetMatchListForOneMatchAndOneBetContent(){
		MaxAndMinBonusContext maxAndMinBonusContext = new MaxAndMinBonusContext();
		List<MaxAndMinBonusContextOfMatch> matchContexts=new ArrayList<MaxAndMinBonusContextOfMatch>();
		MaxAndMinBonusContextOfMatch maxAndMinBonusContextOfMatch=new MaxAndMinBonusContextOfMatch();
		long matchId = 201505095005l;
		maxAndMinBonusContextOfMatch.setMatchId(matchId);
		List<List<BetContent>> coexistenceSet=new ArrayList<List<BetContent>>();
		List<BetContent> betContentList=new ArrayList<BetContent>();
		BetContent betContent=new BetContent();
		betContent.setMatchId(matchId);
		betContent.setBetOption("50053");
		betContent.setOdd(new BigDecimal(4.55));
		betContent.setPlayId("01_ZC_2");
		betContentList.add(betContent);
		coexistenceSet.add(betContentList);
		maxAndMinBonusContextOfMatch.setCoexistenceSet(coexistenceSet);
		matchContexts.add(maxAndMinBonusContextOfMatch);
		maxAndMinBonusContext.setMatchContexts(matchContexts);
		maxAndMinBonusUtil.computeMinAndMaxOddsBetContentList(maxAndMinBonusContext);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMaxOddsBetContentList()==betContentList);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMaxOddsBetContentList().get(0)==betContent);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMinOddsBetContentList().size()==1);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMinOddsBetContentList().get(0)==betContent);
	}
	
	@Test
	public void testComputeMaxOddsBetMatchListForManyMatchAndOneContent(){
		MaxAndMinBonusContext maxAndMinBonusContext = new MaxAndMinBonusContext();
		List<MaxAndMinBonusContextOfMatch> matchContexts=new ArrayList<MaxAndMinBonusContextOfMatch>();
		maxAndMinBonusContext.setMatchContexts(matchContexts);
		
		MaxAndMinBonusContextOfMatch maxAndMinBonusContextOfMatch=new MaxAndMinBonusContextOfMatch();
		long matchId = 201505095005l;
		maxAndMinBonusContextOfMatch.setMatchId(matchId);
		List<List<BetContent>> coexistenceSet=new ArrayList<List<BetContent>>();
		List<BetContent> betContentList=new ArrayList<BetContent>();
		BetContent betContent=new BetContent();
		betContent.setMatchId(matchId);
		betContent.setBetOption("50053");
		betContent.setOdd(new BigDecimal(4.55));
		betContent.setPlayId("01_ZC_2");
		betContentList.add(betContent);
		coexistenceSet.add(betContentList);
		maxAndMinBonusContextOfMatch.setCoexistenceSet(coexistenceSet);
		matchContexts.add(maxAndMinBonusContextOfMatch);
		
		
		MaxAndMinBonusContextOfMatch maxAndMinBonusContextOfMatch2=new MaxAndMinBonusContextOfMatch();
		long matchId2 = 201505095004l;
		maxAndMinBonusContextOfMatch2.setMatchId(matchId2);
		List<List<BetContent>> coexistenceSet2=new ArrayList<List<BetContent>>();
		List<BetContent> betContentList2=new ArrayList<BetContent>();
		BetContent betContent2=new BetContent();
		betContent2.setMatchId(matchId2);
		betContent2.setBetOption("50043");
		betContent2.setOdd(new BigDecimal(2.35));
		betContent2.setPlayId("80_ZC_2");
		betContentList2.add(betContent2);
		coexistenceSet2.add(betContentList2);
		maxAndMinBonusContextOfMatch2.setCoexistenceSet(coexistenceSet2);
		matchContexts.add(maxAndMinBonusContextOfMatch2);
		
		//MaxAndMinBonusUtil.computeMaxOddsBetContentList(maxAndMinBonusContext);
		maxAndMinBonusUtil.computeMinAndMaxOddsBetContentList(maxAndMinBonusContext);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMaxOddsBetContentList()==betContentList);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMaxOddsBetContentList().get(0)==betContent);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(1).getMaxOddsBetContentList()==betContentList2);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(1).getMaxOddsBetContentList().get(0)==betContent2);
		
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMinOddsBetContentList().size()==1);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMinOddsBetContentList().get(0)==betContent);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(1).getMinOddsBetContentList().size()==1);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(1).getMinOddsBetContentList().get(0)==betContent2);
	}
	
	@Test
	public void testComputeMaxOddsBetMatchListForManyMatchAndManyContent(){
		MaxAndMinBonusContext maxAndMinBonusContext = new MaxAndMinBonusContext();
		List<MaxAndMinBonusContextOfMatch> matchContexts=new ArrayList<MaxAndMinBonusContextOfMatch>();
		maxAndMinBonusContext.setMatchContexts(matchContexts);
		
		MaxAndMinBonusContextOfMatch maxAndMinBonusContextOfMatch=new MaxAndMinBonusContextOfMatch();
		long matchId = 201505095005l;
		maxAndMinBonusContextOfMatch.setMatchId(matchId);
		List<List<BetContent>> coexistenceSet=new ArrayList<List<BetContent>>();
		List<BetContent> betContentList=new ArrayList<BetContent>();
		BetContent betContent=new BetContent();
		betContent.setMatchId(matchId);
		betContent.setBetOption("50053");
		betContent.setOdd(new BigDecimal(4.55));
		betContent.setPlayId("01_ZC_2");
		
		BetContent betContent2=new BetContent();
		betContent2.setMatchId(matchId);
		betContent2.setBetOption("50051");
		betContent2.setOdd(new BigDecimal(3.55));
		betContent2.setPlayId("80_ZC_2"); 
		
		betContentList.add(betContent);
		betContentList.add(betContent2);
		coexistenceSet.add(betContentList);
		
		List<BetContent> betContentList3=new ArrayList<BetContent>();
		BetContent betContent4=new BetContent();
		betContent4.setMatchId(matchId);
		betContent4.setBetOption("50050");
		betContent4.setOdd(new BigDecimal(9));
		betContent4.setPlayId("01_ZC_2");
		
		
		betContentList3.add(betContent4);
		coexistenceSet.add(betContentList);
		coexistenceSet.add(betContentList3);
		
		maxAndMinBonusContextOfMatch.setCoexistenceSet(coexistenceSet);
		matchContexts.add(maxAndMinBonusContextOfMatch);
		
		
		MaxAndMinBonusContextOfMatch maxAndMinBonusContextOfMatch2=new MaxAndMinBonusContextOfMatch();
		long matchId2 = 201505095004l;
		maxAndMinBonusContextOfMatch2.setMatchId(matchId2);
		List<List<BetContent>> coexistenceSet2=new ArrayList<List<BetContent>>();
		List<BetContent> betContentList2=new ArrayList<BetContent>();
		BetContent betContent3=new BetContent();
		betContent3.setMatchId(matchId2);
		betContent3.setBetOption("50043");
		betContent3.setOdd(new BigDecimal(2.35));
		betContent3.setPlayId("80_ZC_2");
		betContentList2.add(betContent3);
		coexistenceSet2.add(betContentList2);
		maxAndMinBonusContextOfMatch2.setCoexistenceSet(coexistenceSet2);
		matchContexts.add(maxAndMinBonusContextOfMatch2);
		
		//MaxAndMinBonusUtil.computeMaxOddsBetContentList(maxAndMinBonusContext);
		maxAndMinBonusUtil.computeMinAndMaxOddsBetContentList(maxAndMinBonusContext);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMaxOddsBetContentList()==betContentList3);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMaxOddsBetContentList().get(0)==betContent4);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(1).getMaxOddsBetContentList()==betContentList2);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(1).getMaxOddsBetContentList().get(0)==betContent3);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(0).getMinOddsBetContentList().get(0)==betContent2);
		assertTrue(maxAndMinBonusContext.getMatchContexts().get(1).getMinOddsBetContentList().get(0)==betContent3);
	}
	
	@Test
	public void testPrepareCombination(){
		MaxAndMinBonusContext maxAndMinBonusContext = new MaxAndMinBonusContext();
		List<MaxAndMinBonusContextOfMatch> matchContexts=new ArrayList<MaxAndMinBonusContextOfMatch>();
		maxAndMinBonusContext.setMatchContexts(matchContexts);
		
		MaxAndMinBonusContextOfMatch maxAndMinBonusContextOfMatch=new MaxAndMinBonusContextOfMatch();
		long matchId = 201505095005l;
		maxAndMinBonusContextOfMatch.setMatchId(matchId);
		List<List<BetContent>> coexistenceSet=new ArrayList<List<BetContent>>();
		List<BetContent> betContentList=new ArrayList<BetContent>();
		BetContent betContent=new BetContent();
		betContent.setMatchId(matchId);
		betContent.setBetOption("50053");
		betContent.setOdd(new BigDecimal(4.55));
		betContent.setPlayId("01_ZC_2");
		
		BetContent betContent2=new BetContent();
		betContent2.setMatchId(matchId);
		betContent2.setBetOption("50051");
		betContent2.setOdd(new BigDecimal(3.55));
		betContent2.setPlayId("80_ZC_2"); 
		
		betContentList.add(betContent);
		betContentList.add(betContent2);
		coexistenceSet.add(betContentList);
		
		List<BetContent> betContentList3=new ArrayList<BetContent>();
		BetContent betContent4=new BetContent();
		betContent4.setMatchId(matchId);
		betContent4.setBetOption("50050");
		betContent4.setOdd(new BigDecimal(9));
		betContent4.setPlayId("01_ZC_2");
		
		
		betContentList3.add(betContent4);
		coexistenceSet.add(betContentList);
		coexistenceSet.add(betContentList3);
		
		maxAndMinBonusContextOfMatch.setCoexistenceSet(coexistenceSet);
		matchContexts.add(maxAndMinBonusContextOfMatch);
		
		
		MaxAndMinBonusContextOfMatch maxAndMinBonusContextOfMatch2=new MaxAndMinBonusContextOfMatch();
		long matchId2 = 201505095004l;
		maxAndMinBonusContextOfMatch2.setMatchId(matchId2);
		List<List<BetContent>> coexistenceSet2=new ArrayList<List<BetContent>>();
		List<BetContent> betContentList2=new ArrayList<BetContent>();
		BetContent betContent3=new BetContent();
		betContent3.setMatchId(matchId2);
		betContent3.setBetOption("50043");
		betContent3.setOdd(new BigDecimal(2.35));
		betContent3.setPlayId("80_ZC_2");
		betContentList2.add(betContent3);
		coexistenceSet2.add(betContentList2);
		maxAndMinBonusContextOfMatch2.setCoexistenceSet(coexistenceSet2);
		matchContexts.add(maxAndMinBonusContextOfMatch2);
		//MaxAndMinBonusUtil.computeMaxOddsBetContentList(maxAndMinBonusContext);
		maxAndMinBonusUtil.computeMinAndMaxOddsBetContentList(maxAndMinBonusContext);
		maxAndMinBonusUtil.prepareCombination(maxAndMinBonusContext);
		assertTrue(maxAndMinBonusContext.getMaxOddsBetContentListOfAll().size()==2);
		assertTrue(maxAndMinBonusContext.getMaxOddsBetContentListOfAll().get(0)==betContent4);
		assertTrue(maxAndMinBonusContext.getMaxOddsBetContentListOfAll().get(1)==betContent3);
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().size()==2);
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(0).length==1);
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(0)[0].equals(0));
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(1).length==1);
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(1)[0].equals(1));
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMaxOdds().size()==0);
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMinOdds().size()==0);
		assertTrue(maxAndMinBonusContext.getMinOddsBetContentListOfAll().size()==2);
		assertTrue(maxAndMinBonusContext.getMinOddsBetContentListOfAll().get(0)==betContent2);
		assertTrue(maxAndMinBonusContext.getMinOddsBetContentListOfAll().get(1)==betContent3);
	}
	
	@Test
	public void testCalculateMaxAndMinBonus(){
		MaxAndMinBonusContext context = new MaxAndMinBonusContext();
		context.setPassType("2@1,3@1");
		
		List<BetContent> max = new ArrayList<>();
		List<BetContent> min = new ArrayList<>();
		
		BetContent bet = new BetContent();
		bet.setOdd(new BigDecimal(3.55));
		max.add(bet);
		min.add(bet);
		
		BetContent bet2 = new BetContent();
		bet2.setOdd(new BigDecimal(10.00));
		max.add(bet2);

		BetContent bet3 = new BetContent();
		bet3.setOdd(new BigDecimal(3.30));
		max.add(bet3);
		min.add(bet3);
		
		BetContent bet4 = new BetContent();
		bet4.setOdd(new BigDecimal(1.98));
		max.add(bet4);
		min.add(bet4);
		
		context.setMaxOddsBetContentListOfAll(max);
		context.setMinOddsBetContentListOfAll(min);
		ArrayList<Integer[]> arr = new ArrayList<Integer[]>();
		arr.add(new Integer[]{0,1});
		context.setExcludeIndexOfMaxOdds(arr);
		context.setExcludeIndexOfMinOdds(new ArrayList<Integer[]>());
		context.setSeedIndexOfMaxOdds(new ArrayList<Integer[]>());
		context.setSeedIndexOfMinOdds(new ArrayList<Integer[]>());
		MaxAndMinBonusModel maxAndMinBonusModel = maxAndMinBonusUtil.calculateMaxAndMinBonus(context);
		printBonusModel(maxAndMinBonusModel);
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
			a+="2" + "*1倍=" + df.format(((float)sum)*2);
			System.out.println(a);
		}
		System.out.println();
		System.out.println("共"+maxAndMinBonusModel.getNote()+"注，总金额："+maxAndMinBonusModel.getBuyAmount()+"元");
	}
	
	@Test
	public void testPrepareCombinationHaveSeed(){
		MaxAndMinBonusContext maxAndMinBonusContext = new MaxAndMinBonusContext();
		List<MaxAndMinBonusContextOfMatch> matchContexts=new ArrayList<MaxAndMinBonusContextOfMatch>();
		maxAndMinBonusContext.setMatchContexts(matchContexts);
		
		MaxAndMinBonusContextOfMatch maxAndMinBonusContextOfMatch=new MaxAndMinBonusContextOfMatch();
		long matchId = 201505095005l;
		maxAndMinBonusContextOfMatch.setMatchId(matchId);
		maxAndMinBonusContextOfMatch.setSeed(true);
		List<List<BetContent>> coexistenceSet=new ArrayList<List<BetContent>>();
		List<BetContent> betContentList=new ArrayList<BetContent>();
		BetContent betContent=new BetContent();
		betContent.setMatchId(matchId);
		betContent.setBetOption("50053");
		betContent.setOdd(new BigDecimal(4.55));
		betContent.setPlayId("01_ZC_2");
		
		BetContent betContent2=new BetContent();
		betContent2.setMatchId(matchId);
		betContent2.setBetOption("50051");
		betContent2.setOdd(new BigDecimal(3.55));
		betContent2.setPlayId("80_ZC_2"); 
		
		betContentList.add(betContent);
		betContentList.add(betContent2);
		coexistenceSet.add(betContentList);
		
		List<BetContent> betContentList3=new ArrayList<BetContent>();
		BetContent betContent4=new BetContent();
		betContent4.setMatchId(matchId);
		betContent4.setBetOption("50050");
		betContent4.setOdd(new BigDecimal(1.5));
		betContent4.setPlayId("01_ZC_2");
		
		
		betContentList3.add(betContent4);
		coexistenceSet.add(betContentList);
		coexistenceSet.add(betContentList3);
		
		maxAndMinBonusContextOfMatch.setCoexistenceSet(coexistenceSet);
		matchContexts.add(maxAndMinBonusContextOfMatch);
		
		
		MaxAndMinBonusContextOfMatch maxAndMinBonusContextOfMatch2=new MaxAndMinBonusContextOfMatch();
		long matchId2 = 201505095004l;
		maxAndMinBonusContextOfMatch2.setMatchId(matchId2);
		maxAndMinBonusContextOfMatch2.setSeed(true);
		List<List<BetContent>> coexistenceSet2=new ArrayList<List<BetContent>>();
		List<BetContent> betContentList2=new ArrayList<BetContent>();
		BetContent betContent3=new BetContent();
		betContent3.setMatchId(matchId2);
		betContent3.setBetOption("50043");
		betContent3.setOdd(new BigDecimal(2.35));
		betContent3.setPlayId("80_ZC_2");
		
		BetContent betContent5=new BetContent();
		betContent5.setMatchId(matchId2);
		betContent5.setBetOption("50041");
		betContent5.setOdd(new BigDecimal(1.35));
		betContent5.setPlayId("80_ZC_2");
		
		BetContent betContent6=new BetContent();
		betContent6.setMatchId(matchId2);
		betContent6.setBetOption("50040");
		betContent6.setOdd(new BigDecimal(1.35));
		betContent6.setPlayId("01_ZC_2");
		
		
		betContentList2.add(betContent3);
		betContentList2.add(betContent5);
		betContentList2.add(betContent6);
		
		List<BetContent> betContentList4=new ArrayList<BetContent>();
		BetContent betContent7=new BetContent();
		betContent7.setMatchId(matchId2);
		betContent7.setBetOption("50040");
		betContent7.setOdd(new BigDecimal(2.45));
		betContent7.setPlayId("80_ZC_2");
		
		BetContent betContent8=new BetContent();
		betContent8.setMatchId(matchId2);
		betContent8.setBetOption("50041");
		betContent8.setOdd(new BigDecimal(1.35));
		betContent8.setPlayId("01_ZC_2");
		
		BetContent betContent9=new BetContent();
		betContent9.setMatchId(matchId2);
		betContent9.setBetOption("50043");
		betContent9.setOdd(new BigDecimal(1.35));
		betContent9.setPlayId("01_ZC_2");
		
		
		betContentList4.add(betContent7);
		betContentList4.add(betContent8);
		betContentList4.add(betContent9);
		
		
		coexistenceSet2.add(betContentList2);
		coexistenceSet2.add(betContentList4);
		maxAndMinBonusContextOfMatch2.setCoexistenceSet(coexistenceSet2);
		matchContexts.add(maxAndMinBonusContextOfMatch2);
		//MaxAndMinBonusUtil.computeMaxOddsBetContentList(maxAndMinBonusContext);
		maxAndMinBonusUtil.computeMinAndMaxOddsBetContentList(maxAndMinBonusContext);
		maxAndMinBonusUtil.prepareCombination(maxAndMinBonusContext);
		
		assertTrue(maxAndMinBonusContext.getMaxOddsBetContentListOfAll().size()==5);
		assertTrue(maxAndMinBonusContext.getMaxOddsBetContentListOfAll().get(0)==betContent);
		assertTrue(maxAndMinBonusContext.getMaxOddsBetContentListOfAll().get(1)==betContent2);
		assertTrue(maxAndMinBonusContext.getMaxOddsBetContentListOfAll().get(2)==betContent7);
		assertTrue(maxAndMinBonusContext.getMaxOddsBetContentListOfAll().get(3)==betContent8);
		assertTrue(maxAndMinBonusContext.getMaxOddsBetContentListOfAll().get(4)==betContent9);
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().size()==2);
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(0).length==2);
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(0)[0].equals(0));
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(0)[1].equals(1));
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(1).length==3);
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(1)[0].equals(2));
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(1)[1].equals(3));
		assertTrue(maxAndMinBonusContext.getExcludeIndexOfMaxOdds().get(1)[2].equals(4));
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMaxOdds().size()==2);
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMaxOdds().get(0).length==2);
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMaxOdds().get(0)[0].equals(0));
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMaxOdds().get(0)[1].equals(1));
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMaxOdds().get(1).length==3);
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMaxOdds().get(1)[0].equals(2));
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMaxOdds().get(1)[1].equals(3));
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMaxOdds().get(1)[2].equals(4));
		assertTrue(maxAndMinBonusContext.getMinOddsBetContentListOfAll().size()==2);//min
		assertTrue(maxAndMinBonusContext.getMinOddsBetContentListOfAll().get(0)==betContent4);//min
		assertTrue(maxAndMinBonusContext.getMinOddsBetContentListOfAll().get(1)==betContent5);//min
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMinOdds().size()==2);//min
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMinOdds().get(0)[0].equals(0));//min
		assertTrue(maxAndMinBonusContext.getSeedIndexOfMinOdds().get(1)[0].equals(1));//min
	}
	

}
