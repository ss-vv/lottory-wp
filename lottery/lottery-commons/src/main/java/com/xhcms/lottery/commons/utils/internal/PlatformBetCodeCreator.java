package com.xhcms.lottery.commons.utils.internal;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;

/**
 * 大V彩平台投注内容构造器，与具体出票平台无关
 * @author lei.li@davcai.com
 */
public abstract class PlatformBetCodeCreator {
	
	private Logger logger=LoggerFactory.getLogger(getClass());

	private List<BetMatch> matchs;
	
	/**
	 * 默认直接返回投注code，格式：
	 * 30041，表示周三，004场，投注选项为1
	 * @param betMatch
	 * @return
	 */
	public String getPlatformCode(BetMatch betMatch) {
		return betMatch.getCode();
	}
	
	/**
	 * @param playId	投注玩法ID（对应的是大V彩自定义的玩法ID）
	 * @param cur	比赛数组的下标
	 * @param matchs	投注的比赛信息
	 */
	public Ticket create(String playId, int[] cur, List<BetMatch> matchs) {
		this.matchs = matchs;

		StringBuilder buf;
		PlayType playType = PlayType.valueOfLcPlayId(playId);
		if (playType == PlayType.JCLQ_HH || playType == PlayType.JCZQ_HH 
				|| PlayType.isOneMatchMutiPlayMixBet(playId)) {
			CodeOfHH codeOfHH = createCodeForHH(cur);
			if (codeOfHH.isOnlyOnePlay()) {
				// 将混合过关投注中只有一种玩法的投注内容生产一张票
				Ticket t = new Ticket();
				t.setCode(codeOfHH.getCodeBuf().toString());
				String ticketPlayId = codeOfHH.getPlay().getPlayId();
				t.setPlayId(ticketPlayId);
				computeMinMatchPlayingTime(cur, t);
				return t;
			} else {
				buf = codeOfHH.getCodeBuf();
			}
		} else {
			buf = createCode(cur);
		}
		Ticket t = new Ticket();
		t.setCode(buf.toString());
		computeMinMatchPlayingTime(cur, t);
		
		if(PlayType.isOnePlayMixBet(playId) && null != cur && cur.length == 1) {
			t.setPlayId(matchs.get(cur[0]).getPlayId());
		}
		
		return t;
	}

	private void computeMinMatchPlayingTime(int[] cur, Ticket t) {
		BetMatch match = this.matchs.get(cur[0]);
		Date minMatchPlayingTime=match.getPlayingTime();
		if(null!=minMatchPlayingTime){
			for(int i=0;i<cur.length;i++){
				match = this.matchs.get(cur[i]);
				if(null!=match.getPlayingTime()&&match.getPlayingTime().before(minMatchPlayingTime)){
					minMatchPlayingTime=match.getPlayingTime();
					
				}
			}
			if(null!=minMatchPlayingTime){
				t.setMinMatchPlayingTime(minMatchPlayingTime);
			}
			else{
				logger.warn("ticket:{}的match:{}的minMatchPlayingTime为空",t,match);
			}
			
		}
		else{
			logger.warn("ticket:{}的match:{}的minMatchPlayingTime为空",t,match);
		}
	}

	private StringBuilder createCode(int[] cur) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < cur.length; i++) {
			BetMatch m = matchs.get(cur[i]);
			buf.append(getPlatformCode(m));
			buf.append('-');
		}
		buf.deleteCharAt(buf.length() - 1);
		return buf;
	}

	/**
	 * @param cur
	 *            比赛数组的下标
	 * @return null 如果混合过关方式下，所有比赛的玩法都一样则返回null。
	 */
	private CodeOfHH createCodeForHH(int[] cur) {
		StringBuilder buf = new StringBuilder();
		boolean isAllMixSame = true; // 是否所有比赛都是同一类型
		MixPlayType preMix = null;
		for (int i = 0; i < cur.length; i++) {
			BetMatch m = matchs.get(cur[i]);
			PlayType lcPT = PlayType.valueOfLcPlayId(m.getPlayId());
			MixPlayType mpt = MixPlayType.valueOfPlayType(lcPT);
			String mixPlayType = mpt.getName();
			buf.append(getPlatformCode(m));
			buf.append(":").append(mixPlayType).append('-');
			if (i > 0) {
				if (mpt != preMix) {
					isAllMixSame = false;
					preMix = mpt;
				}
			} else { // i==0 先赋值给 preMix
				preMix = mpt;
			}
		}
		buf.deleteCharAt(buf.length() - 1);
		CodeOfHH codeOfHH = new CodeOfHH();
		codeOfHH.setCodeBuf(buf);
		if (cur.length > 1 && isAllMixSame) {
			codeOfHH.setOnlyOnePlay(true);
			codeOfHH.setPlay(preMix);
			trimPlayTypeFromCode(codeOfHH);
		} else {
			codeOfHH.setOnlyOnePlay(false);
		}
		return codeOfHH;
	}

	private void trimPlayTypeFromCode(CodeOfHH codeOfHH) {
		StringBuilder sb = codeOfHH.getCodeBuf();
		String code = sb.toString();
		String codeTrimed = code.replaceAll(":.+?(?=(-|$))", "");
		codeOfHH.setCodeBuf(new StringBuilder(codeTrimed));
	}
}
