package com.davcai.lottery.weibo.analyse.service.impl;

import java.util.Comparator;

import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;

/**
 * @author baoxing.peng
 *
 */
public class ComparatorBbLeagueScore implements Comparator<LeagueScoreRandPO> {

	@Override
	public int compare(LeagueScoreRandPO o1, LeagueScoreRandPO o2) {
		double winRate1 = o1.getWinMatches()*1.0/o1.getTotalMatches();;
		double winRate2 = o2.getWinMatches()*1.0/o2.getTotalMatches();
		if(winRate1>winRate2){
			return 1;
		}else if(winRate1==winRate2){
			return 0;
		}else{
			return -1;
		}
	}

}
