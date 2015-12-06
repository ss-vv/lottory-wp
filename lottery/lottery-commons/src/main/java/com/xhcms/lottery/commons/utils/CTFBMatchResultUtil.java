package com.xhcms.lottery.commons.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;

public class CTFBMatchResultUtil {

	/**
	 * 
	 * @param ctfbMatchs
	 * @param playId
	 * @return 赛果列表，每一项代表一场比赛的赛果，如果比赛取消，则该场比赛涉及的投注项用*表示，表示任何投注项都算正确
	 */
	public List<String> makeMatchResultList(List<CTFBMatchPO> ctfbMatchs,
			String playId) {
		if(null!=ctfbMatchs&&!ctfbMatchs.isEmpty()&&StringUtils.isNotBlank(playId)){
			List<String> result=new ArrayList<String>();
			String matchResult=null;
			for(CTFBMatchPO ctfbMatch:ctfbMatchs){
				matchResult=makeMatchResult(ctfbMatch,playId);
				if(null!=matchResult){
					if(-1 != matchResult.indexOf(",")){//兼容半全场,进球数
						String [] aa = matchResult.split(",");
						result.add(aa[0]);
						result.add(aa[1]);
					} else {
						result.add(matchResult);
					}
				}
				else{//一旦有一场比赛无法生成赛果，则马上返回
					return null;
				}
			}
			return result;
		}
		return null;
	}

	/**
	 * 
	 * @param ctfbMatch
	 * @param playId
	 * @return 赛果，如果比赛取消，则该场比赛涉及的投注项用*表示，表示任何投注项都算正确
	 */
	public String makeMatchResult(CTFBMatchPO ctfbMatch, String playId) {
		if (null != ctfbMatch 
				&& StringUtils.isNotBlank(playId)) {
			if(EntityStatus.MATCH_CANCEL == ctfbMatch.getStatus()){
				if(Constants.PLAY_26_ZC_BQ.equals(playId) || Constants.PLAY_27_ZC_JQ.equals(playId)){
					return "*,*";
				} else {
					return "*";
				}
			}
			String score = ctfbMatch.getScore();
			if(StringUtils.isBlank(score)){
				return null;
			}
			String[] scores = score.split(":");
			if(null == scores || scores.length < 2){
				return null;
			}
			int hScore = Integer.valueOf(scores[0]);
			int gScore = Integer.valueOf(scores[1]);
			switch(playId){
				case Constants.PLAY_24_ZC_14:
					return hScore > gScore ? "3" : hScore == gScore ? "1" : "0";
				case Constants.PLAY_25_ZC_R9:
					return hScore > gScore ? "3" : hScore == gScore ? "1" : "0";
				case Constants.PLAY_26_ZC_BQ:
					String halfScore = ctfbMatch.getHalfScore();
					if(StringUtils.isBlank(halfScore)){
						return null;
					}
					String[] halfScores = halfScore.split(":");
					if(null == halfScores || halfScores.length < 2){
						return null;
					}
					int hhScore = Integer.valueOf(halfScores[0]);
					int hgScore = Integer.valueOf(halfScores[1]);
					String a=hhScore > hgScore ? "3" : hhScore == hgScore ? "1" : "0";//半场赛果
					String b=hScore > gScore ? "3" : hScore == gScore ? "1" : "0";//全场赛果
					return a+","+b;
				case Constants.PLAY_27_ZC_JQ:
					String a1 = hScore >= 3 ? "3" : hScore+"";
					String b1 = gScore >= 3 ? "3" : gScore+"";
					return a1+","+b1;
			}
		}
		return null;
	}
}
