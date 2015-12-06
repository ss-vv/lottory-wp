package com.xhcms.lottery.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.bonus.JCLQBetOptGroup;
import com.xhcms.lottery.commons.data.bonus.JCZQBetOptGroup;
import com.xhcms.lottery.commons.persist.service.BonusCalculatorService;
import com.xhcms.lottery.commons.persist.service.OptionGroupByScoreService;
import com.xhcms.lottery.commons.persist.service.impl.BonusCalculatorServiceImpl;
import com.xhcms.lottery.commons.util.bonus.BetContent;
import com.xhcms.lottery.lang.LotteryId;

public class OptionGroupWithScoreTest {

	private BonusCalculatorService bonusCalcService = new BonusCalculatorServiceImpl();
	
	@Test
	public void loadOtpionsJson() {
		Map<List<String>, JCLQBetOptGroup> jclqOptionsMap = OptionGroupByScoreService.getJclqOptionsMap();
		Map<List<String>, JCZQBetOptGroup> jczqOptionsMap = OptionGroupByScoreService.getJczqOptionsMap();
		boolean condition = (jclqOptionsMap != null) && jclqOptionsMap.size() > 0;
		boolean conditionzq = (jczqOptionsMap != null ) && jczqOptionsMap.size() > 0;
		Assert.assertTrue(condition);
		Assert.assertTrue(conditionzq);
	}
	
	@Test
	public void optionGroupWithJCLQTest() {
		String lotteryId = LotteryId.JCLQ.name();
		List<BetMatch> betMatchs = new ArrayList<BetMatch>();
		Map<Long, BigDecimal> matchScoreMap = new HashMap<Long, BigDecimal>();
		
		matchScoreMap.put(201505154301L, new BigDecimal(-1.5));
		matchScoreMap.put(201505154302L, new BigDecimal(7.5));
		
		BetMatch bmWith301 = new BetMatch();
		BetMatch bmWith3010 = new BetMatch();
		BetMatch bmWith3011 = new BetMatch();
		BetMatch bmWith302 = new BetMatch();

		bmWith301.setMatchId(201505154301L);
		bmWith301.setBetOptions("主胜");
		bmWith301.setOdds("1.8");
		
		bmWith3010.setMatchId(201505154301L);
		bmWith3010.setBetOptions("让分主负");
		bmWith3010.setOdds("10.5");
		
		bmWith3011.setMatchId(201505154301L);
		bmWith3011.setBetOptions("主胜6-10分");
		bmWith3011.setOdds("2.3");
		
		bmWith302.setMatchId(201505154302L);
		bmWith302.setBetOptions("主胜");
		bmWith302.setOdds("1.6");
		
		betMatchs.add(bmWith301);
		betMatchs.add(bmWith3010);
		betMatchs.add(bmWith3011);
		betMatchs.add(bmWith302);
		
		Map<Long, List<List<BetContent>>> result = bonusCalcService.groupOptionsByLottery(betMatchs, 
				lotteryId, matchScoreMap);
		System.out.println(result);
		Assert.assertTrue(result != null && result.size() > 0);
	}
	
}
