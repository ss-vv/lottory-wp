package com.xhcms.lottery.commons.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;

/**
 * 赛事投注内容解析器
 * @author lei.li@davcai.com
 */
public class BetMatchsResolver {
	
	public static final String mutiPlaySep = ",";
	
	public List<BetMatch> splitOneMatchOnePlay(String matchs, String playId) {
		String[] matchArr = matchs.split(",");
        Pattern p = Pattern.compile("-");
        List<BetMatch> matchList = new ArrayList<BetMatch>(matchArr.length);
        for (int i = 0; i < matchArr.length; i++) {
            String match = matchArr[i];
            String[] m = p.split(match);
            BetMatch bm = new BetMatch();
            bm.setId(Long.parseLong(m[0]));
            bm.setMatchId(Long.parseLong(m[0]));
            bm.setCode(m[1]);
            //加胆码
            bm.setSeed(Boolean.parseBoolean(m[2]));
            if(PlayType.isOnePlayMixBet(playId)) {
            	MixPlayType mp = MixPlayType.valueOfPlayName(m[3]);
            	int index = mp.getPlayId().lastIndexOf("_");
            	String playIdStr = mp.getPlayId().substring(0, index) + "_1";
            	bm.setPlayId(playIdStr);
            } else if(m.length > 3) {
            	MixPlayType mp = MixPlayType.valueOfPlayName(m[3]);
            	bm.setPlayId(mp.getPlayId());
            }
            matchList.add(bm);
        }
        return matchList;
	}
	
	/**
	 * 主要拆分一场赛事包含多玩法投注内容
	 * 支持的玩法：
	 * <pre>1.一场比赛，多个玩法单关</pre>
	 * <pre>2.一场比赛多个玩法与另外一场比赛串关过关</pre>
	 * 解析投注赛事串,格式：
	 * <pre>
	 * 3）混合（一场赛事多种玩法混合）
		单关玩法：
		matchs=201502042001-2001310-false-brqspf,201502042001-2001202110-false-bf
		playId=555_ZCFH_1
		过关玩法：
		matchs=201502042001-20013-false-brqspf,201502042001-20013-false-spf,201502042002-20023-false-brqspf
		playId=555_ZCFH_2
	 * </pre>
	 * @param playId	投注赛事玩法列表ID
	 * @param matchs	用户投注赛事串
	 * @return	投注方案关联的赛事
	 */
	public List<BetMatch> splitOneMatchMutiPlayHH(String playId, String matchs) {
		List<BetMatch> matchList = new ArrayList<BetMatch>();
		
		//混合数据校验
		String[] matchStrList = matchs.split(",");
		for(String match : matchStrList) {
			String[] matchSplitList = match.split("-");
			if(matchSplitList.length != 4) {
				throw new XHRuntimeException("混合投注内容无效.");
			}
		}
		
		List<BetMatch> betMatchList = splitOneMatchOnePlay(matchs, playId);
		matchList.addAll(betMatchList);
		return matchList;
	}
	
	/**
	 * 判断当前投注内容是否为一场赛事单关混合投注
	 * @param playId	玩法ID
	 * @param matchs 投注赛事
	 * @return
	 */
	public boolean isOneMatchSingleHHBet(String playId, String matchs) {
		boolean result = false;
		if(StringUtils.isNotBlank(playId) && PlayType.isOnePlayMixBet(playId)) {
			String[] matchArr = matchs.split(",");
			if(matchArr.length > 1 && PlayType.isOnePlayMixBet(playId)) {
				Pattern p = Pattern.compile("-");
				Set<String> matchIds = new HashSet<String>();
				for (int i = 0; i < matchArr.length; i++) {
					String match = matchArr[i];
					String[] m = p.split(match);
					String matchId = m[0];
					matchIds.add(matchId);
				}
				if(matchIds.size() == 1) {//只选了一场赛事
					result = true;
				}
			}
		}
		return result;
	}
}