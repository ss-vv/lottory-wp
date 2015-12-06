package com.xhcms.lottery.dc.feed.web.action.bet;

import java.util.ArrayList;
import java.util.List;

public class BetLotteryOfSupport {

	private static final List<String> lotteryListOfSupport = new ArrayList<String>();
	
	static {
		lotteryListOfSupport.add("jczq");
		lotteryListOfSupport.add("jclq");
		lotteryListOfSupport.add("ctzc");
		lotteryListOfSupport.add("ssq");
		lotteryListOfSupport.add("shaidan");
		lotteryListOfSupport.add("hemai");
		lotteryListOfSupport.add("bjdc");
	}
	
	public static boolean isSupported(String lottery) {
		return lotteryListOfSupport.contains(lottery);
	}
	
}