package com.xhcms.lottery.commons.client.translate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.MapUtils;
import com.xhcms.lottery.lang.PlayType;

/**
 * 赔率格式转换类。
 * 赔率目前只有竞彩有。
 * @author Yang Bo
 */
@SuppressWarnings("unchecked")
public class Odds {

	private List<MatchOdds> matchOdds = new LinkedList<MatchOdds>();
	public List<MatchOdds> getMatchOdds() {
		return matchOdds;
	}

	private static HashMap<String, String> zmOptOddsOptMap = new HashMap<String,String>();
	// key 是中民返回的赔率选项，value 是出票的选项格式
	private static Map<String, String> zmOddsOptToBuyOpt = new HashMap<String, String>();

	static {
		zmOptOddsOptMap.put("胜1-5", "胜1-5分");
		zmOptOddsOptMap.put("胜6-10", "胜6-10分");
		zmOptOddsOptMap.put("胜11-15", "胜11-15分");
		zmOptOddsOptMap.put("胜16-20", "胜16-20分");
		zmOptOddsOptMap.put("胜21-25", "胜21-25分");
		zmOptOddsOptMap.put("胜26+", "胜26分以上");
		zmOptOddsOptMap.put("负1-5", "负1-5分");
		zmOptOddsOptMap.put("负6-10", "负6-10分");
		zmOptOddsOptMap.put("负11-15", "负11-15分");
		zmOptOddsOptMap.put("负16-20", "负16-20分");
		zmOptOddsOptMap.put("负21-25", "负21-25分");
		zmOptOddsOptMap.put("负26+", "负26分以上");
		
		zmOddsOptToBuyOpt = MapUtils.reverse(zmOptOddsOptMap);
	}
	
	private Odds(){
	}
	
	public static Odds parseZMOdds(String zmOdds) throws TranslateException {
		AssertUtils.assertNotBlank(zmOdds, "zmOdds");
		Odds odds = new Odds();
		String [] matches = zmOdds.split("/");
		for (String matchWithOdds : matches){
			odds.matchOdds.add(MatchOdds.parseZMMatchOdds(matchWithOdds));
		}
		return odds;
	}
	
	
	public static Odds praseAnRuiOdds(String anRuiOdds, String playId) throws TranslateException{
		AssertUtils.assertNotBlank(anRuiOdds, "anRuiOdds");
		Odds odds = new Odds();
		String [] matches = anRuiOdds.split("/");
		for (String matchWithOdds : matches){
			odds.matchOdds.add(MatchOdds.parseAnRuiMatchOdds(matchWithOdds,playId));
		}
		return odds;
	}

	/**
	 * 根据“大V彩投注内容”和“大V彩玩法id”构造“大V彩赔率”。
	 * @param lcBetContent
	 * @param lcPlayId
	 * @return 大V彩赔率格式，也就是中民旧接口的赔率格式，形如：1.77A1.77-1.77@181.5B195.5
	 * @throws TranslateException 
	 */
	public String toLCOdds(String lcBetContent, String lcPlayId) throws TranslateException {
		StringBuilder matchOddsBuilder = new StringBuilder();
		StringBuilder extraInfoBuilder = new StringBuilder();
		
		BetContent betContent = BetContentFactory.parseBetContentInLaiCaiFormat(lcBetContent, lcPlayId, LotteryPlatformId.ZUN_AO);
		for (PlatformBetContent matchBet : betContent.getMatchBetContents()){
			buildMatchOdds(matchBet, matchOddsBuilder);
			buildExtraInfo(matchBet, extraInfoBuilder);
		}
		if (extraInfoBuilder.length()>0){
			return matchOddsBuilder.append('@').append(extraInfoBuilder).toString();
		}else{
			return matchOddsBuilder.toString();
		}
	}

	/**
	 * 组织比赛赔率，一场比赛的赔率串形如： 1.77A1.77.
	 * 更新 matchOddsBuilder 的内容。
	 * @throws TranslateException 
	 */
	private void buildMatchOdds(PlatformBetContent matchBet,
			StringBuilder matchOddsBuilder) throws TranslateException {
		List<String> zmOptions = matchBet.splitOptionsInPlatformFormat();
		List<String> zmOddsOptions = zmOptions;//translateBetOptionToOddsOption(matchBet, zmOptions);
		StringBuilder oneMatchOdds = new StringBuilder();
		MatchOdds mOdds = findMatchOddsByMatchBet(matchBet);
		String playId = matchBet.getLcPlayId();
		if (StringUtils.isBlank(playId)){
			playId = matchBet.getTicketPlayId();
		}
		for (String oddsOpt : zmOddsOptions){
			appendSeperator(oneMatchOdds, "A");
			String odds = getOddsWithTolerantOption(playId, mOdds.getOptionOddsMap(), oddsOpt);
			if (StringUtils.isBlank(odds)) {
				throw new TranslateException("No odds for ZM Option: " + oddsOpt +
						", match bet: " + matchBet + ", match odds: " + mOdds);
			}
			oneMatchOdds.append(odds);
		}
		appendSeperator(matchOddsBuilder, "-");
		matchOddsBuilder.append(oneMatchOdds);
	}
	
	/**
	 * 容错的获取选项对应的赔率。
	 * @return 赔率
	 */
	private String getOddsWithTolerantOption(String playId, Map<String,String> optOdds, String opt){
		PlayType pt = PlayType.valueOfLcPlayId(playId);
		String altOpt;
		String o = optOdds.get(opt);
		if (StringUtils.isNotBlank(o)){
			return o;
		}
		switch(pt){
		case JCZQ_BQC:
			altOpt = opt.replace("-", "");
			break;
		case JCLQ_SFC:
			altOpt = zmOddsOptToBuyOpt.get(opt);
			if (StringUtils.isNotBlank(altOpt)){
				altOpt = opt;
			}else{
				altOpt = zmOptOddsOptMap.get(opt);
			}
			break;
		default:
			altOpt = opt;
			break;
		}
		return optOdds.get(altOpt);
	}
	
	private MatchOdds findMatchOddsByMatchBet(
			PlatformBetContent matchBet) throws TranslateException {
		for (MatchOdds mOdds : matchOdds) {
			if (mOdds.getDayInWeek().equals(matchBet.getDayInWeek())
				&& mOdds.getSequence().equals(matchBet.getSequence())) {
				return mOdds;
			}
		}
		throw new TranslateException("Can not find MatchOdds by MatchBetContent: " + matchBet);
	}
	
	private void appendSeperator(StringBuilder builder, String seperator) {
		if (builder.length()>0){
			builder.append(seperator);
		}
	}

	/**
	 * 组织额外信息串，形如：181.5B195.5
	 * @param matchBet
	 * @param extraInfoBuilder
	 * @throws TranslateException 
	 */
	private void buildExtraInfo(PlatformBetContent matchBet,
			StringBuilder extraInfoBuilder) throws TranslateException {
		MatchOdds mOdds = findMatchOddsByMatchBet(matchBet);
		String extraInfo = mOdds.getExtraInfo();
		if (StringUtils.isNotBlank(extraInfo)) {
			appendSeperator(extraInfoBuilder, "B");
			extraInfoBuilder.append(extraInfo);
		}
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * 转换中民的购买选项到出票赔率选项格式。比如：胜-胜 -> 胜胜；胜1-5 -> 胜1-5分
	 * @param playType 玩法
	 * @param zmOpt 购买选项
	 * @return 出票赔率选项
	 */
	public static String transZMBuyOptToOddsOpt(PlayType playType, String zmOpt) {
		String oddsOpt = zmOpt;
		switch(playType){
		case JCZQ_BQC:
			oddsOpt = zmOpt.replace("-", "");
			break;
		case JCLQ_SFC:
			oddsOpt = zmOptOddsOptMap.get(zmOpt);
			break;
		default:
			break;
		}
		return oddsOpt;
	}
}
