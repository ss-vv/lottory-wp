package com.xhcms.lottery.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.lang.PlayType;

public class CombineBetMatchUtil {
	public static BetScheme combine(BetScheme betScheme){
		List<BetMatch> newBetMatchs = new ArrayList<BetMatch>();
		Map<String,BetMatch> matchOptionsMap = new HashMap<String, BetMatch>();
		List<BetMatch> oldBetMatchs = betScheme.getMatchs();
		if(null == oldBetMatchs){//方案没有比赛直接返回
			return betScheme;
		}
		for (BetMatch  match : oldBetMatchs) {
			String matchId = match.getMatchId().toString();
			PlayMatch playMatch = new PlayMatch();
			if(match instanceof PlayMatch){
				playMatch = (PlayMatch)match;
			}
			if(null == matchOptionsMap.get(matchId)){//betMatch第一次出现，放入map
				matchOptionsMap.put(matchId, match);
				String playId = match.getPlayId();
				int oddsCount = 0;
				if(StringUtils.isNotBlank(match.getOdds())){
					oddsCount = match.getOdds().split(",").length;
				} else {//推荐情况下没有赔率，使用投注选项计算
					oddsCount = match.getBetOptions().split(",").length;
				}
				match.setPlayId("");//兼容else调用updatePlay()
				match.setPlayName("");
				updatePlay(match,playId,oddsCount,playMatch.getConcedePoints());
				match.setBetOptions(match.getBetOptions()+",");
				match.setOdds(match.getOdds()+",");
			} else {
				BetMatch newBetMatch = matchOptionsMap.get(matchId);
				int oddsCount = 0;
				if(StringUtils.isNotBlank(match.getOdds())){
					oddsCount = match.getOdds().split(",").length;
				} else {//推荐情况下没有赔率，使用投注选项计算
					oddsCount = match.getBetOptions().split(",").length;
				}
				updatePlay(newBetMatch,match.getPlayId(),oddsCount,playMatch.getConcedePoints());
				newBetMatch.setBetOptions(newBetMatch.getBetOptions() + match.getBetOptions()+",");
				newBetMatch.setOdds(newBetMatch.getOdds() + match.getOdds() + ",");
			}
		}
		for (String key : matchOptionsMap.keySet()) {
			BetMatch  match = matchOptionsMap.get(key);
			//去掉最后一个逗号分隔符
			if(StringUtils.isNotBlank(match.getBetOptions())){
				match.setBetOptions(match.getBetOptions().substring(0,match.getBetOptions().length()-1));
			}
			if(StringUtils.isNotBlank(match.getOdds())){
				match.setOdds(match.getOdds().substring(0,match.getOdds().length()-1));
			}
			match.setPlayId(match.getPlayId().substring(0,match.getPlayId().length()-1));
			match.setPlayName(match.getPlayName().substring(0,match.getPlayName().length()-1));
			newBetMatchs.add(match);
		}
		Collections.sort(newBetMatchs, new Comparator<BetMatch>() {
			@Override
			public int compare(BetMatch o1, BetMatch o2) {
				if(null == o1 || null == o2){
					return 0;
				}
				if(null == o1.getCode() || null == o2.getCode()){
					return 0;
				}
				return o1.getCode().compareTo(o2.getCode());
			}
		});
		betScheme.setMatchs(newBetMatchs);
		return betScheme;
	}
	
	private static void updatePlay(BetMatch match,String playId,int oddsCount,String defaultScore){
		String playName = PlayType.getPlayNameByPlayId(playId);
		StringBuilder playIdBuilder = new StringBuilder();
		StringBuilder playNameBuilder = new StringBuilder();
		for (int i = 0; i < oddsCount; i++) {
			playIdBuilder.append(playId);
			playIdBuilder.append(",");
			playNameBuilder.append(playName);
			playNameBuilder.append(",");
		}
		match.setPlayId(match.getPlayId() + playIdBuilder.toString());
		match.setPlayName(match.getPlayName() + playNameBuilder.toString());
		String lqrfsf1 = PlayType.JCLQ_RFSF.getShortPlayStr()+"_1";
		String lqrfsf2 = PlayType.JCLQ_RFSF.getShortPlayStr()+"_2";
		if(lqrfsf1.equals(playId) || lqrfsf2.equals(playId)){//是篮球让分玩法，把让分放进defaultscore
			if(StringUtils.isNotBlank(defaultScore)){
				match.setDefaultScore(Float.parseFloat(defaultScore));
			}
		}
	}
}
