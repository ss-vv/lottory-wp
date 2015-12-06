package com.davcai.lottery.weibo.analyse.service.impl;

import java.util.Comparator;

import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;

/**
 * 足球积分排序规则
 * @author baoxing.peng
 *
 */
public class ComparatorFbLeagueScore implements Comparator<LeagueScoreRandPO> {

	@Override
	public int compare(LeagueScoreRandPO o1, LeagueScoreRandPO o2) {
		int leagueScore1 = o1.getLeageScore().intValue();
		int leagueScore2 = o2.getLeageScore().intValue();
		//净胜球
		double jingGoal1 = o1.getGoal()-o1.getLose();
		double jingGoal2 = o2.getGoal()-o2.getLose();
		if(leagueScore1>leagueScore2){
			return -1;
		}else if(leagueScore1==leagueScore2){
			if(jingGoal1>jingGoal2){ //积分相同比较净胜球
				return -1;
			}else if(jingGoal1<jingGoal2){
				return 1;
			}else{
				return 0;
			}
		}else {
			return 1;
		}
	}

}
