package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.lang.BetPlayNameAndCode;
import com.xhcms.lottery.commons.lang.OddAndActualOdd;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.service.BetOddsService;
import com.xhcms.lottery.commons.persist.service.BetOptionUtil;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

public class BetOddsServiceImpl implements BetOddsService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public OddAndActualOdd convert(TicketPO ticketPO, BetScheme scheme) {
		log.debug("根据投注内容和玩法转换出出票赔率内容:ticket={}, playId={}, lotteryId={}, sponsorId={}, " +
				"scheme={}, betMatchs={}", new Object[]{
				ticketPO, scheme.getId(), scheme.getLotteryId(), scheme.getSponsorId(), 
				scheme, scheme.getMatchs()
		});
		
		OddAndActualOdd result = new OddAndActualOdd();
		if(null != scheme && StringUtils.isNotBlank(scheme.getPlayId())) {
			String playId = scheme.getPlayId();
			if(playId.startsWith(PlayType.JCZQ_SPF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCZQ_BRQSPF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCZQ_BF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCZQ_ZJQS.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCZQ_BQC.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCLQ_SF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCLQ_SFC.getShortPlayStr())) {
				result.setOdds(splitBetCodeWithJCZQ(playId, ticketPO.getCode(), scheme.getMatchs()));
			} else if(playId.startsWith(PlayType.JCZQ_HH.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCZQ_FH.getShortPlayStr())) {//方案是竞彩足球混合投注
				if(ticketPO.getPlayId().startsWith(PlayType.JCZQ_HH.getShortPlayStr())){//票是竞彩足球混合投注，odds和actualOdds一致
					String oddsStr=splitBetCodeWithJCZQ_HH(ticketPO, scheme.getMatchs());
					result.setOdds(oddsStr);
					result.setActualOdds(oddsStr);
				} else{//其他情况应该是非混合投注玩法的票
					String oddsStr=makeOddsWithJCZQ_HH_4SamePlayId(ticketPO, scheme.getMatchs());
					String actualOdds=makeActualOddsWithJCZQ_HH_4SamePlayId(ticketPO, scheme.getMatchs());
					result.setOdds(oddsStr);
					result.setActualOdds(actualOdds);
				}
			} else if(playId.startsWith(PlayType.JCLQ_RFSF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCLQ_DXF.getShortPlayStr())) {
				result.setOdds(makeOddsWithJCLQ_HH_4SamePlayId(ticketPO, scheme.getMatchs()));
			} else if(playId.startsWith(PlayType.JCLQ_HH.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCLQ_FH.getShortPlayStr())) {
				if(ticketPO.getPlayId().startsWith(PlayType.JCLQ_HH.getShortPlayStr())){//票是竞彩篮球混合投注，odds和actualOdds一致
					String oddsStr=splitBetCodeWithJCLQ_HH(ticketPO, scheme.getMatchs());
					result.setOdds(oddsStr);
					result.setActualOdds(oddsStr);
				} else{//其他情况应该是非混合投注玩法的票
					String oddsStr=makeOddsWithJCLQ_HH_4SamePlayId(ticketPO, scheme.getMatchs());
					String actualOdds=makeActualOddsWithJCLQ_HH_4SamePlayId(ticketPO, scheme.getMatchs());
					result.setOdds(oddsStr);
					result.setActualOdds(actualOdds);
				}
			}
		}
		return result;
	}

	private String makeActualOddsWithJCLQ_HH_4SamePlayId(TicketPO ticketPO,
			List<BetMatch> matchs) {
		return makeActualOddsWith_HH_4SamePlayId(ticketPO, matchs, LotteryId.JCLQ);
	}

	private String makeOddsWithJCLQ_HH_4SamePlayId(TicketPO ticketPO,
			List<BetMatch> matchs) {
		String result=null;
		if(null!=ticketPO&&StringUtils.isNotBlank(ticketPO.getPlayId())){
			String playId=ticketPO.getPlayId();
			if(playId.startsWith(PlayType.JCLQ_SF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCLQ_SFC.getShortPlayStr())) {
				
				result=splitBetCodeWithJCZQ(ticketPO.getPlayId(), ticketPO.getCode(), matchs);
			}
			else if(playId.startsWith(PlayType.JCLQ_RFSF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCLQ_DXF.getShortPlayStr())){
				String betOptionSplit = splitBetCodeWithJCZQ(ticketPO.getPlayId(), ticketPO.getCode(), matchs);
				
				StringBuffer buf = new StringBuffer("@");
				List<String> list = Arrays.asList(ticketPO.getCode().split("-"));
				if(playId.startsWith(PlayType.JCLQ_RFSF.getShortPlayStr())) {
					for(int i=0; i<matchs.size(); i++) {
						BetMatch betMatch = matchs.get(i);
						if(list.contains(betMatch.getCode()) && 
								playId.equals(betMatch.getPlayId())) {
							BigDecimal defaultScore = new BigDecimal(betMatch.getDefaultScore());
							if(defaultScore.doubleValue() > 0) {
								buf.append("+").append(defaultScore);
							} else {
								buf.append(defaultScore);
							}
							if(i != matchs.size() - 1) {
								buf.append("B");
							}
						}
					}
				} else {
					for(int i=0; i<matchs.size(); i++) {
						BetMatch betMatch = matchs.get(i);
						if(null != list && list.size() > 0) {
							if(list.contains(betMatch.getCode()) && 
									playId.equals(betMatch.getPlayId())) {
								buf.append(new BigDecimal(betMatch.getDefaultScore()));
								if(i != matchs.size() - 1) {
									buf.append("B");
								}
							}
						}
					}
					if(buf.toString().endsWith("B")) {
						buf = buf.deleteCharAt(buf.length() - 1);
					}
				}
				result=betOptionSplit + buf.toString();
			}
		}
		return result;
	}

	/**
	 * 生成足球混合投注方案中，单一玩法的票的odds属性
	 * @param ticketPO
	 * @param matchs
	 * @return
	 */
	private String makeOddsWithJCZQ_HH_4SamePlayId(TicketPO ticketPO,
			List<BetMatch> matchs) {
		String result=null;
		if(null!=ticketPO&&StringUtils.isNotBlank(ticketPO.getPlayId())){
			String playId=ticketPO.getPlayId();
			if(playId.startsWith(PlayType.JCZQ_SPF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCZQ_BRQSPF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCZQ_BF.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCZQ_ZJQS.getShortPlayStr()) ||
					playId.startsWith(PlayType.JCZQ_BQC.getShortPlayStr())) {
				
				result=splitBetCodeWithJCZQ(ticketPO.getPlayId(),ticketPO.getCode(), matchs);
			}
		}
		
		return result;
	}

	/**
	 * 生成足球混合投注方案中，单一玩法的票的actualOdds属性
	 * @param ticketPO
	 * @param matchs
	 * @return
	 */
	private String makeActualOddsWithJCZQ_HH_4SamePlayId(TicketPO ticketPO,
			List<BetMatch> matchs) {
		return makeActualOddsWith_HH_4SamePlayId(ticketPO, matchs,LotteryId.JCZQ);
	}

	private String makeActualOddsWith_HH_4SamePlayId(TicketPO ticketPO,
			List<BetMatch> matchs,LotteryId lotteryId) {
		StringBuffer buf = new StringBuffer();
		String[] betContent = ticketPO.getCode().split("-");
		for(int i=0; i<betContent.length; i++) {
			String content = betContent[i];
			String betPlayStr = formatBetOdd4SamePlayId(content,ticketPO.getPlayId());
			String optionResult = convertBetOption4SamePlayId(content, matchs, lotteryId.name(),ticketPO.getPlayId());
			buf.append(betPlayStr).append(optionResult);
			
			if(i != betContent.length - 1) {
				buf.append("/");
			}
		}
		return buf.toString();
	}



	private String convertBetOption4SamePlayId(String content,
			List<BetMatch> matchs, String lotteryName, String playId) {
		String result = "[%s]";
		BetPlayNameAndCode betPlayNameAndCode = makeBetPlayNameAndCode4SamePlayId(content,
				playId);
		if (null != betPlayNameAndCode
				&& StringUtils.isNotBlank(betPlayNameAndCode.getBetPlayName())
				&& StringUtils.isNotBlank(betPlayNameAndCode.getCode())
				&& StringUtils.isNotBlank(betPlayNameAndCode.getPlayId())) {
			String options = betPlayNameAndCode.getCode().substring(4);
			String odds = getOddsByCode(betPlayNameAndCode.getCode(), matchs, betPlayNameAndCode.getPlayId());
			if(StringUtils.isNotBlank(odds)) {
				String[] odd = odds.split(",");
				List<String> list = Arrays.asList(options.split(""));
				list = list.subList(1, list.size());
				String rs = "";
				if(LotteryId.JCZQ.name().equals(lotteryName)) {
					rs = formatJCZQBetOption(list, betPlayNameAndCode.getBetPlayName(), odd);
				} else if(LotteryId.JCLQ.name().equals(lotteryName)) {
					BigDecimal defaultScore = null;
					for(BetMatch betMatch : matchs) {
						if( betPlayNameAndCode.getCode().equals(betMatch.getCode()) &&
								betPlayNameAndCode.getPlayId().equals(betMatch.getPlayId())) {
							defaultScore = new BigDecimal(betMatch.getDefaultScore());
							break;
						}
					}
					
					rs = formatJCLQBetOption(list, betPlayNameAndCode.getBetPlayName(), odd, defaultScore);
				}
				return String.format(result, rs);
			}
		}
		
		return "[]";
	}

	private BetPlayNameAndCode makeBetPlayNameAndCode4SamePlayId(
			String content, String playId) {
		BetPlayNameAndCode betPlayNameAndCode=new BetPlayNameAndCode();
		if(StringUtils.equals(playId, Constants.PLAY_01_ZC_2)){
			betPlayNameAndCode.setBetPlayName("BRQSPF"); 
			betPlayNameAndCode.setCode(content);
		}else if (StringUtils.equals(playId, Constants.PLAY_02_ZC_2)){
			betPlayNameAndCode.setBetPlayName("BF"); 
			betPlayNameAndCode.setCode(content);
		}else if(StringUtils.equals(playId, Constants.PLAY_03_ZC_2)){
			betPlayNameAndCode.setBetPlayName("JQS"); 
			betPlayNameAndCode.setCode(content);
		}else if(StringUtils.equals(playId, Constants.PLAY_04_ZC_2)){
			betPlayNameAndCode.setBetPlayName("BQC"); 
			betPlayNameAndCode.setCode(content);
		}else if(StringUtils.equals(playId, Constants.PLAY_80_ZC_2)){
			betPlayNameAndCode.setBetPlayName("SPF"); 
			betPlayNameAndCode.setCode(content);
	
		}else if(StringUtils.equals(playId, Constants.PLAY_06_LC_2)){
			betPlayNameAndCode.setBetPlayName("RFSF"); 
			betPlayNameAndCode.setCode(content);
	
		}else if(StringUtils.equals(playId, Constants.PLAY_07_LC_2)){
			betPlayNameAndCode.setBetPlayName("SF"); 
			betPlayNameAndCode.setCode(content);
	
		}else if(StringUtils.equals(playId, Constants.PLAY_08_LC_2)){
			betPlayNameAndCode.setBetPlayName("FC"); 
			betPlayNameAndCode.setCode(content);
	
		}else if(StringUtils.equals(playId, Constants.PLAY_09_LC_2)){
			betPlayNameAndCode.setBetPlayName("DXF"); 
			betPlayNameAndCode.setCode(content);
	
		}
		return betPlayNameAndCode;
	}

	private String formatBetOdd4SamePlayId(String content, String playId) {
		String result=null;
	
		if(StringUtils.isNotBlank(content)){
			String tpl = content.substring(0,1) + "%s" + content.substring(1,4) + ":";
			result = String.format(tpl, "-");
		}
		return result;
	}

	/**
	 * 竞彩足球：根据投注内容获取出票成功的赔率内容
	 * @param playId
	 * @param betCode
	 * @param betMatchs
	 * @return
	 */
	protected String splitBetCodeWithJCZQ(String playId, String betCode, List<BetMatch> betMatchs) {
		List<String> list = Arrays.asList(betCode.split("-"));
		StringBuffer result = new StringBuffer();
		for(int i=0; i<list.size(); i++) {
			String code = list.get(i);
			for(BetMatch betMatch : betMatchs) {
				if(code.equals(betMatch.getCode()) && playId.equals(betMatch.getPlayId())) {
					String betOdds = betMatch.getOdds();
					int oddSize = betOdds.split(",").length;
					if(oddSize > 1) {
						betOdds = betOdds.replaceAll(",", "A");
					}
					result.append(betOdds);
				}
			}
			if(i != (list.size() - 1)) {
				result.append("-");
			}
		}
		return result.toString();
	}
	
	private String getOddsByCode(String code, List<BetMatch> betMatchs, String playId) {
		String result = null;
		for(BetMatch betMatch : betMatchs) {
			if(code.equals(betMatch.getCode()) && playId.equals(betMatch.getPlayId())) {
				result = betMatch.getOdds();
				break;
			}
		}
		return result;
	}
	
	private String formatBetOdd(String content, String playId) {
		String result=null;
		BetPlayNameAndCode betPlayNameAndCode = makeBetPlayNameAndCode(content,
				playId);
		if(null!=betPlayNameAndCode&&StringUtils.isNotBlank(betPlayNameAndCode.getBetPlayName())){
			String tpl = betPlayNameAndCode.getBetPlayName() + "%s" + betPlayNameAndCode.getCode().substring(0,1) + "%s" + betPlayNameAndCode.getCode().substring(1,4) + ":";
			result = String.format(tpl, "@", "-");
		}
		return result;
	}

	private BetPlayNameAndCode makeBetPlayNameAndCode(String content,
			String playId) {
		BetPlayNameAndCode betPlayNameAndCode=new BetPlayNameAndCode();
		if(StringUtils.equals(playId,Constants.PLAY_05_ZC_2)||StringUtils.equals(playId,Constants.PLAY_10_LC_2)){//足球或篮球混合投注
			String[] betSplit = content.split(":");
			betPlayNameAndCode.setCode(betSplit[0]);
			betPlayNameAndCode.setBetPlayName(betSplit[1]); 
			if(StringUtils.isNotBlank(betSplit[1])) {
				if("BRQSPF".equals(betSplit[1])) {
					betPlayNameAndCode.setPlayId(PlayType.JCZQ_BRQSPF.getPlayIdWithPass(false));
				} else if("SPF".equals(betSplit[1])) {
					betPlayNameAndCode.setPlayId(PlayType.JCZQ_SPF.getPlayIdWithPass(false));
				} else if("JQS".equals(betSplit[1])) {
					betPlayNameAndCode.setPlayId(PlayType.JCZQ_ZJQS.getPlayIdWithPass(false));
				} else if("BF".equals(betSplit[1])) {
					betPlayNameAndCode.setPlayId(PlayType.JCZQ_BF.getPlayIdWithPass(false));
				} else if("BQC".equals(betSplit[1])) {
					betPlayNameAndCode.setPlayId(PlayType.JCZQ_BQC.getPlayIdWithPass(false));
				} else if("RFSF".equals(betSplit[1])) {
					betPlayNameAndCode.setPlayId(PlayType.JCLQ_RFSF.getPlayIdWithPass(false));
				} else if("SF".equals(betSplit[1])) {
					betPlayNameAndCode.setPlayId(PlayType.JCLQ_SF.getPlayIdWithPass(false));
				} else if("FC".equals(betSplit[1])) {
					betPlayNameAndCode.setPlayId(PlayType.JCLQ_SFC.getPlayIdWithPass(false));
				} else if("DXF".equals(betSplit[1])) {
					betPlayNameAndCode.setPlayId(PlayType.JCLQ_DXF.getPlayIdWithPass(false));
				}
			}
		}
		return betPlayNameAndCode;
	}
	
	private String formatJCZQBetOption(List<String> options, String playIdName, String[] odds) {
		StringBuffer buf = new StringBuffer();
		
		if("BRQSPF".equals(playIdName) || "SPF".equals(playIdName)) {
			for(int i=0; i<options.size(); i++) {
				String opt = options.get(i);
				if("3".equals(opt)) {
					opt = "胜";
				} else if("1".equals(opt)) {
					opt = "平";
				}  else if("0".equals(opt)) {
					opt = "负";
				}
				opt = opt + "=" + odds[i];
				if(i != options.size() - 1) {
					opt = opt + ",";
				}
				buf.append(opt);
			}
		} else if("JQS".equals(playIdName)) {
			for(int i=0; i<options.size(); i++) {
				String opt = options.get(i);
				if("7".equals(opt)) {
					opt = "7+";
				}
				opt = opt + "=" + odds[i];
				if(i != options.size() - 1) {
					opt = opt + ",";
				}
				buf.append(opt);
			}
		} else if("BF".equals(playIdName)) {
			String rs = "";
			for(int i=0; i<options.size(); i++) {
				String opt = "";
				rs = rs + options.get(i);
				if(i > 0 && (i+1)%2 == 0) {
					opt = BetOptionUtil.getJZ_BF_OPTION(rs);
					opt = opt + "=" + odds[(i - 1) / 2];
					if(i != options.size() - 1) {
						opt = opt + ",";
					}
					rs = "";
					buf.append(opt);
				}
			}
		} else if("BQC".equals(playIdName)) {
			String rs = "";
			for(int i=0; i<options.size(); i++) {
				String opt = "";
				rs = rs + options.get(i);
				if(i > 0 && (i+1)%2 == 0) {
					opt = BetOptionUtil.getJZ_BQC_OPTION(rs);
					opt = opt + "=" + odds[(i - 1) / 2];
					if(i != options.size() - 1) {
						opt = opt + ",";
					}
					rs = "";
					buf.append(opt);
				}
			}
		}
		return buf.toString();
	}
	
	
	private String formatJCLQBetOption(List<String> options, String playId, String[] odds, BigDecimal defaultScore) {
		StringBuffer buf = new StringBuffer();
		
		if("RFSF".equals(playId)) {
			for(int i=0; i<options.size(); i++) {
				String opt = options.get(i);
				if("1".equals(opt)) {//主胜=1.750^-0.50
					opt = "主胜";
				} else if("2".equals(opt)) {
					opt = "客胜";
				}
				opt = opt + "=" + odds[i];
				if(i != options.size() - 1) {
					opt = opt + ",";
				}
				
				if(null != defaultScore 
						&& (i == options.size() - 1)) {
					if(defaultScore.compareTo(BigDecimal.ZERO)>=0){
						opt = opt + "^+" + defaultScore;
					}
					else{
						opt = opt + "^" + defaultScore;
					}
					
				}
				
				buf.append(opt);
			}
		} else if("SF".equals(playId)) {
			for(int i=0; i<options.size(); i++) {
				String opt = options.get(i);
				if("1".equals(opt)) {
					opt = "主胜";
				} else if("2".equals(opt)) {
					opt = "客胜";
				}
				opt = opt + "=" + odds[i];
				if(i != options.size() - 1) {
					opt = opt + ",";
				}
				buf.append(opt);
			}
		} else if("FC".equals(playId)) {
			String rs = "";
			for(int i=0; i<options.size(); i++) {
				String opt = "";
				rs = rs + options.get(i);
				if(i > 0 && (i+1)%2 == 0) {
					opt = BetOptionUtil.getJL_SFC_OPTION(rs);
					opt = opt + "=" + odds[(i - 1) / 2];
					if(i != options.size() - 1) {
						opt = opt + ",";
					}
					rs = "";
					buf.append(opt);
				}
			}
		} else if("DXF".equals(playId)) {
			for(int i=0; i<options.size(); i++) {
				String opt = options.get(i);
				if("1".equals(opt)) {
					opt = "大";
				} else if("2".equals(opt)) {
					opt = "小";
				}
				opt = opt + "=" + odds[i];
				
				if(i != options.size() - 1) {
					opt = opt + ",";
				}
				
				if(null != defaultScore 
						&& (i == options.size() - 1)) {
					opt = opt + "^" + defaultScore;
				}
				
				buf.append(opt);
			}
		}
		return buf.toString();
	}
		
	private String convertBetOption(String content, List<BetMatch> betMatchs, String lotteryId, String playId) {
		String result = "[%s]";
		BetPlayNameAndCode betPlayNameAndCode = makeBetPlayNameAndCode(content,
				playId);
		if (null != betPlayNameAndCode
				&& StringUtils.isNotBlank(betPlayNameAndCode.getBetPlayName())
				&& StringUtils.isNotBlank(betPlayNameAndCode.getCode())
				&& StringUtils.isNotBlank(betPlayNameAndCode.getPlayId())) {
			String options = betPlayNameAndCode.getCode().substring(4);
			String odds = getOddsByCode(betPlayNameAndCode.getCode(), betMatchs, betPlayNameAndCode.getPlayId());
			if(StringUtils.isNotBlank(odds)) {
				String[] odd = odds.split(",");
				List<String> list = Arrays.asList(options.split(""));
				list = list.subList(1, list.size());
				String rs = "";
				if(LotteryId.JCZQ.name().equals(lotteryId)) {
					rs = formatJCZQBetOption(list, betPlayNameAndCode.getBetPlayName(), odd);
				} else if(LotteryId.JCLQ.name().equals(lotteryId)) {
					BigDecimal defaultScore = null;
					for(BetMatch betMatch : betMatchs) {
						if( betPlayNameAndCode.getCode().equals(betMatch.getCode()) &&
								betPlayNameAndCode.getPlayId().equals(betMatch.getPlayId())) {
							defaultScore = new BigDecimal(betMatch.getDefaultScore());
							break;
						}
					}
					
					rs = formatJCLQBetOption(list, betPlayNameAndCode.getBetPlayName(), odd, defaultScore);
				}
				return String.format(result, rs);
			}
		}
		
		return "[]";
	}
	
	protected String splitBetCodeWithJCZQ_HH(TicketPO ticketPO, List<BetMatch> betMatchs) {
		StringBuffer buf = new StringBuffer();
		String[] betContent = ticketPO.getCode().split("-");
		for(int i=0; i<betContent.length; i++) {
			String content = betContent[i];
			String betPlayStr = formatBetOdd(content,ticketPO.getPlayId());
			String optionResult = convertBetOption(content, betMatchs, LotteryId.JCZQ.name(),ticketPO.getPlayId());
			buf.append(betPlayStr).append(optionResult);
			
			if(i != betContent.length - 1) {
				buf.append("/");
			}
		}
		return buf.toString();
	}
	
	protected String splitBetCodeWithJCLQ_HH(TicketPO ticketPO, List<BetMatch> betMatchs) {
		StringBuffer buf = new StringBuffer();
		String[] betContent = ticketPO.getCode().split("-");
		for(int i=0; i<betContent.length; i++) {
			String content = betContent[i];
			
			String betPlayStr = formatBetOdd(content,ticketPO.getPlayId());
			String optionResult = convertBetOption(content, betMatchs, LotteryId.JCLQ.name(), ticketPO.getPlayId());
			buf.append(betPlayStr).append(optionResult);
			
			if(i != betContent.length - 1) {
				buf.append("/");
			}
		}
		return buf.toString();
	}
}