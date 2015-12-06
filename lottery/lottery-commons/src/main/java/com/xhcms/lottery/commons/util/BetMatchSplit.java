package com.xhcms.lottery.commons.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.util.bonus.BetContent;

public final class BetMatchSplit {
	
	public static Pattern PATTERN = Pattern.compile(",");

	public static List<BetContent> split(BetMatch bm) {
		List<BetContent> bsList = null;
		String betOptions = bm.getBetOptions();
		String[] optArr = PATTERN.split(betOptions);
		if(optArr.length > 1) {
			bsList = convert(bm, true);
		} else {
			bsList = convert(bm, false);
		}
		return bsList;
	}
	
	public static List<BetContent> convert(BetMatch bm, boolean mutipleOptions) {
		String betOptions = bm.getBetOptions();
		String odds = bm.getOdds();
		String[] optArr = PATTERN.split(betOptions);
		String[] oddArr = PATTERN.split(odds);
		List<BetContent> bsList = new ArrayList<BetContent>();
		
		if(mutipleOptions) {
			for(int i=0; i<optArr.length; i++) {
				BetContent bc = makeBetContent(bm, optArr[i], oddArr[i]);
				bsList.add(bc);
			}
		} else {
			BetContent bc = makeBetContent(bm, null, null);
			bsList.add(bc);
		}
		return bsList;
	}
	
	private static BetContent makeBetContent(BetMatch bm, String betOption, String odd) {
		BetContent bc = new BetContent();
		bc.setMatchId(bm.getMatchId());
		bc.setCode(bm.getCode());
		bc.setCnCode(bm.getCnCode());
		bc.setBetOption(StringUtils.isBlank(betOption) ? 
				bm.getBetOptions() : betOption);
		
		bc.setSeed(bm.isSeed());
		bc.setDefaultScore(bm.getDefaultScore());
		
		bc.setOdd(StringUtils.isBlank(odd) ? 
				new BigDecimal(bm.getOdds()) : new BigDecimal(odd));
		
		bc.setPlayId(bm.getPlayId());
		return bc;
	}
	
}
