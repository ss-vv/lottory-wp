package com.xhcms.lottery.commons.utils.internal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.TicketPresetPO;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.commons.util.MatchResults;
import com.xhcms.lottery.commons.util.OptionUtils;
import com.xhcms.lottery.commons.util.PrizesStrategy;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;

/**
 * 竞彩预兑奖
 * @author Wang Lei
 *
 */
public class JCPrizesStrategy implements PrizesStrategy{
	
	private Logger log = LoggerFactory.getLogger(JCPrizesStrategy.class);
	
	@Autowired
	private BetResolver betResolver;

	@Override
	public boolean match(String playId) {
		return Pattern.matches("\\d{2}_(Z|L)C_\\d", playId);
	}

	@Override
	public boolean prizesByTickets(List<TicketPresetPO> tickets, MatchResults results) {
		for(TicketPresetPO t : tickets){
    		Bet bet = betResolver.resolve(makeSchemeByTicket(t, results));
    		
    		BigDecimal bonus = new BigDecimal(bet.getMaxBonus()).setScale(2, RoundingMode.HALF_UP);
    		BigDecimal afterTaxBonus = null;
    		
    		//单票单倍奖金大于1万，扣除20%所得税
    		BigDecimal oneMutiBonus = bonus.divide(new BigDecimal(t.getMultiple()), RoundingMode.HALF_UP);
    		if(oneMutiBonus.compareTo(new BigDecimal(10000)) >= 0) {
    			afterTaxBonus = bonus.multiply(new BigDecimal(0.8));
    		} else {
    			afterTaxBonus = bonus;
    		}
    		
    		t.setPreTaxBonus(bonus);
    		t.setAfterTaxBonus(afterTaxBonus);
    	}
		return true;
	}
	
	private BetScheme makeSchemeByTicket(TicketPresetPO t, MatchResults results){
		Map<String, Map<String, String>> matchPlayOdd = matchPlayOdd(t);
		BetScheme betScheme = new BetScheme();
		String lottryId = PlayType.getLotteryIdByPlayId(t.getPlayId());
		String playId = t.getPlayId();
		Map<String, String> matchResult = Constants.JCZQ.equals(lottryId) ? results.getfBMatchWinOptMap() : results.getbBMatchWinOptMap();
		List<BetMatch> matchs = new ArrayList<BetMatch>();
		String[] codes = t.getCode().split("-");
		
		for(String code : codes){
			BetMatch match = new BetMatch();
			match.setCode(code);
			if(isCancelMatchCode(code, results)) {
				match.setOdds(cancelMatchTicketOdds(code, playId));
				log.info("票ID={},包含取消赛事ID={},处理兑奖赔率.", new Object[]{t.getId(), code});
			} else {
				match.setOdds(getWinOdd(makeKey(code, playId) , playId, matchPlayOdd, matchResult));
			}
			matchs.add(match);
			match.setPlayId(makePlayId(code, playId));
		}
		betScheme.setLotteryId(lottryId);
		betScheme.setMatchNumber(matchPlayOdd.size());
		betScheme.setMatchs(matchs);
		betScheme.setPlayId(playId);
		List<String> list = new ArrayList<String>();
		list.add(t.getPassTypeId());
		betScheme.setPassTypes(list);
		betScheme.setMultiple(t.getMultiple());
		return betScheme;
	}
	
	/**
	 * 投注code是否是“已取消”状态的比赛
	 * @param code
	 * @param results
	 * @return
	 */
	private boolean isCancelMatchCode(String code, MatchResults results) {
		if(StringUtils.isNotBlank(code) && code.length() > 4) {
			List<String> cancelCodeMatchList = results.getCancelMatchCodeList();
			if(null != cancelCodeMatchList && cancelCodeMatchList.contains(code.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}

	private String cancelMatchTicketOdds(String code, String playId) {
		int odd = 0;
		String betOptions = ""; 
		if(playId.equals(PlayType.JCZQ_HH.getPlayIdWithPass(false)) ||
				playId.equals(PlayType.JCLQ_HH.getPlayIdWithPass(false))) {
			String mixPlayId = code.substring(code.indexOf(":") + 1, code.length());
			playId = getPlayIdByMixPlayId(mixPlayId);
			
			betOptions = code.substring(4, code.indexOf(":"));
		} else {
			betOptions = code.substring(4);
		}
		
		if(Constants.PLAY_01_ZC_2.equals(playId) || 
				Constants.PLAY_80_ZC_2.equals(playId) ||
				Constants.PLAY_03_ZC_2.equals(playId)) {
			odd = betOptions.length();
		} else if(Constants.PLAY_02_ZC_2.equals(playId) ||
				Constants.PLAY_04_ZC_2.equals(playId)) {
			odd = betOptions.length()/2;
		} else if(Constants.PLAY_06_LC_2.equals(playId) ||
				Constants.PLAY_07_LC_2.equals(playId) ||
				Constants.PLAY_09_LC_2.equals(playId)) {
			odd = betOptions.length();
		} else if(Constants.PLAY_08_LC_2.equals(playId)) {
			odd = betOptions.length()/2;
		}
		return String.valueOf(odd);
	}
	
	/**
	 * 计算是否中奖，如中奖则返回出票时的赔率，否则返回0赔率
	 * @param key
	 * @param playId
	 * @param matchPlayOdd
	 * @param matchResult
	 * @return
	 */
	private String getWinOdd(String key,String playId, Map<String, Map<String, String>> matchPlayOdd, Map<String, String> matchResult){
		Map<String, String> matchBet = matchPlayOdd.get(key);	// 通过赛事code+playId 取得票中投注的选项map
		String winOpt = matchResult.get(key);									// 通过赛事code+playId 取得对应的中奖选项
		if(StringUtils.isBlank(winOpt)){
			throw new RuntimeException("票中未匹配到投注选项，key:" + key);
		}
		winOpt = getWinOpt(matchBet.get("playId"), matchBet.get("defaultScore"), winOpt); 	// 如果票有预设分值，则重新计算当前玩法的中奖选项
		String odd = matchBet.get(winOpt);
		return StringUtils.isNotBlank(odd) ? odd : "0";
	}
	
	private String makeKey(String code, String playId){
		if(Constants.PLAY_05_ZC_2.equals(playId) || Constants.PLAY_10_LC_2.equals(playId)){
			String mixPlayId = code.substring(code.indexOf(":") + 1, code.length());
			return code.substring(0, 4) + getPlayIdByMixPlayId(mixPlayId) + mixPlayId;
		}
		return code.substring(0, 4) + playId;
	}
	
	private String getWinOpt(String playId, String defaultScore, String winOpt){
		if(Constants.PLAY_06_LC_2.equals(playId)){
			return OptionUtils.lcWinOption(Constants.PLAY_06, Float.parseFloat(defaultScore), 0, winOpt);
		}else if(Constants.PLAY_09_LC_2.equals(playId)){
			return OptionUtils.lcWinOption(Constants.PLAY_09, 0, Float.parseFloat(defaultScore), winOpt);
		}
		return winOpt;
	}
	
	private Map<String, Map<String, String>> matchPlayOdd(TicketPresetPO t){
		Map<String, Map<String, String>> matchPlayOdd = new HashMap<String, Map<String,String>>();
		String playId = t.getPlayId();
		String[] odd = splitOdds(playId, t.getOdds());									// 赔率数组
		String[] defaultScores = splitDefaultScores(playId, t.getOdds());	// 预设分数组(如：篮球预设让分)
		String[] matchOpts = t.getCode().split("-");										// 投注选项数组

		for(int i=0; i< odd.length ; i++){
			String[] opts = getBetOptByPlayId(playId, matchOpts[i]);	// 每一场赛事的选项数组
			String[] odds = getOdds(odd[i]); 															// 每一场赛事的赔率数组
			Map<String, String> bet = new HashMap<String, String>();
			
			for(int j=0 ; j < opts.length ; j++){
				bet.put(opts[j], odds[j]);
			}
			if(defaultScores.length > 0){
				bet.put("defaultScore", defaultScores[i]);
			}
			bet.put("playId", makePlayId(matchOpts[i], playId));
			matchPlayOdd.put(makeKey(matchOpts[i], playId), bet);
		}
		return matchPlayOdd;
	}
	
	private String makePlayId(String code, String playId){
		 if(playId.equals(Constants.PLAY_05_ZC_2) || playId.equals(Constants.PLAY_10_LC_2)){
			 String mixPlayId = code.substring(code.indexOf(":") + 1, code.length());
			 return getPlayIdByMixPlayId(mixPlayId);
		 }
		 return playId;
	}
	
	private String[] splitOdds(String playId, String odds){
		if(playId.equals(Constants.PLAY_06_LC_2) || playId.equals(Constants.PLAY_09_LC_2)){
			return odds.substring(0, odds.indexOf("@")).split("-");
		}else if(playId.equals(Constants.PLAY_05_ZC_2) || playId.equals(Constants.PLAY_10_LC_2)){
			String[] result = odds.split("/");
			
			for(int i=0 ; i < result.length ; i ++){
				if(result[i].indexOf("^") != -1){ // 有让球或者预设分
					result[i] = result[i].substring(0, result[i].indexOf("^"));
				}
				String[] opts = result[i].split(",");
				result[i] = "";
				for(int j=0; j<opts.length ; j++){
					if(j > 0){
						result[i] += "A";
					}
					String one = opts[j];
					if(one.indexOf(",")  != -1){
						opts[j] = one.substring(one.indexOf("=") + 1,one.indexOf(","));
					}else{
						opts[j] = one.substring(one.indexOf("=") + 1);
					}
					result[i] += opts[j].replace("]", "");
				}
			}
			return result;
		}else {
			return odds.split("-");
		}
	}
	
	private String[] splitDefaultScores(String playId, String odds){
		if(playId.equals(Constants.PLAY_06_LC_2) || playId.equals(Constants.PLAY_09_LC_2)){
			return odds.substring(odds.indexOf("@") +1 , odds.length()).split("B");
		}else if(playId.equals(Constants.PLAY_05_ZC_2) || playId.equals(Constants.PLAY_10_LC_2)){
			String[] result = odds.split("/");
			for(int i = 0; i <result.length ; i ++){
				if(result[i].indexOf("^") == -1){
					result[i] = "";
				}else{
					result[i] = result[i].substring(result[i].indexOf("^") +1 , result[i].length()).replace("]", "");
				}
			}
			return result;
		}else {
			return new String[]{};
		}
	}
	
	private String[] getOdds(String odds){
		String[] odd = odds.split("A");
		String[] result = new String[odd.length];
		for(int i = 0 ; i < odd.length ; i++){
			result[i] = odd[i].toString();
		}
		return result;
	}
	
	private String[] getBetOptByPlayId(String playId, String opts){
		if(Constants.PLAY_05_ZC_2.equals(playId) || Constants.PLAY_10_LC_2.equals(playId)){
			String mixPlayId = opts.substring(opts.indexOf(":") +1, opts.length());
			playId = getPlayIdByMixPlayId(mixPlayId);
			opts = opts.substring(4, opts.indexOf(":"));
		}else{
			opts = opts.substring(4);
		}
		if(StringUtils.isBlank(opts)){
			throw new RuntimeException("分解票中投注内容时出错！投注内容为空！");
		}
		int j = 1;
		if(Constants.PLAY_01_ZC_2.equals(playId) ||
				Constants.PLAY_01_ZC_1.equals(playId)){
		}else if(Constants.PLAY_02_ZC_2.equals(playId) ||
				Constants.PLAY_02_ZC_1.equals(playId)){
			j = 2;
		}else if(Constants.PLAY_03_ZC_2.equals(playId) ||
				Constants.PLAY_03_ZC_1.equals(playId)){
		}else if(Constants.PLAY_04_ZC_2.equals(playId) ||
				Constants.PLAY_04_ZC_1.equals(playId)){
			j = 2;
		}else if(Constants.PLAY_80_ZC_2.equals(playId) || 
				Constants.PLAY_80_ZC_1.equals(playId)){
		}else if(Constants.PLAY_06_LC_2.equals(playId) ||
				Constants.PLAY_06_LC_1.equals(playId)){
		}else if(Constants.PLAY_07_LC_2.equals(playId) ||
				Constants.PLAY_07_LC_1.equals(playId)){
		}else if(Constants.PLAY_08_LC_2.equals(playId) ||
				Constants.PLAY_08_LC_1.equals(playId)){
			j = 2;
		}else if(Constants.PLAY_09_LC_2.equals(playId) ||
				Constants.PLAY_09_LC_1.equals(playId)){
		}else{
			throw new RuntimeException("分解票中投注内容时出错！未知的玩法！" + playId);
		}
		char[] optChars = opts.toCharArray();
		int count = optChars.length / j;
		String[] optArray = new String[count];
		for(int i=0;i<count ; i++){
			optArray[i] = opts.substring(i * j , i * j + j);
		}
		return optArray;
	}
	
	private String getPlayIdByMixPlayId(String mix){
		if(MixPlayType.RFSF.name().equals(mix)){
			return Constants.PLAY_06_LC_2;
		}else if(MixPlayType.SF.name().equals(mix)){
			return Constants.PLAY_07_LC_2;
		}else if(MixPlayType.FC.name().equals(mix)){
			return Constants.PLAY_08_LC_2;
		}else if(MixPlayType.DXF.name().equals(mix)){
			return Constants.PLAY_09_LC_2;
		}else if(MixPlayType.SPF.name().equals(mix)){
			return Constants.PLAY_01_ZC_2;
		}else if(MixPlayType.BRQSPF.name().equals(mix)){
			return Constants.PLAY_80_ZC_2;
		}else if(MixPlayType.BF.name().equals(mix)){
			return Constants.PLAY_02_ZC_2;
		}else if(MixPlayType.JQS.name().equals(mix)){
			return Constants.PLAY_03_ZC_2;
		}else if(MixPlayType.BQC.name().equals(mix)){
			return Constants.PLAY_04_ZC_2;
		}else{
			throw new RuntimeException("从混合玩法转换到普通玩法时出错！未知的玩法！" + mix);
		}
	}
}
