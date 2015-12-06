package com.xhcms.lottery.commons.client.translate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 一场比赛的赔率。
 * 赔率目前只有竞彩有。
 * @author Yang Bo
 *
 */
public class MatchOdds {

	private String dayInWeek;		// 星期
	private String sequence;		// 序号
	// key-中民格式的option, value-赔率值
	private Map<String, String> optionOddsMap = new LinkedHashMap<String,String>();
	private String extraInfo;		// 可能是“篮球大小分玩法”的设定分值、“篮球比分玩法”的让分数。

	private MatchOdds(){}
	
	/**
	 * 解析中民新接口的比赛赔率。
	 * @throws TranslateException 如果格式错误。
	 */
	public static MatchOdds parseZMMatchOdds(String matchWithOdds) throws TranslateException {
		AssertUtils.assertNotBlank(matchWithOdds, "matchWithOdds");
		MatchOdds matchOdds = new MatchOdds();
		Pattern pattern = Pattern.compile("(\\d)-(\\d+):\\[(.+)\\]");
		Pattern patternHH = Pattern.compile("(\\w+)@(\\d)-(\\d+):\\[(.+)\\]"); // 混合过关的出票赔率格式
		Matcher matcher = pattern.matcher(matchWithOdds);
		Matcher matcherHH = patternHH.matcher(matchWithOdds);
		String w,s,o;
		if (matcher.matches()){
			w = matcher.group(1); // 星期
			s = matcher.group(2); // 序号
			o = matcher.group(3); // 赔率
		}else if(matcherHH.matches()){
			w = matcherHH.group(2);
			s = matcherHH.group(3);
			o = matcherHH.group(4);
		}else{
			throw new TranslateException("Unknown ZM Odds format: " + matchWithOdds);
		}
		matchOdds.setDayInWeek(w);
		matchOdds.setSequence(s);
		parseZMOptionOdds(o, matchOdds);
		return matchOdds;
	}

	private static void parseZMOptionOdds(String optionOdds, MatchOdds matchOdds) {
		String optionOddsPairs = optionOdds;
		char extraInfoSeperator = '^';
		int indexOfInfo = optionOdds.indexOf(extraInfoSeperator);
		if (indexOfInfo != -1) {
			matchOdds.setExtraInfo(optionOdds.substring(indexOfInfo+1));
			optionOddsPairs = optionOdds.substring(0, indexOfInfo);
		}
		String[] pairs = optionOddsPairs.split(",");
		for (String pair : pairs) {
			String[] optOdds = pair.split("=");
			matchOdds.getOptionOddsMap().put(optOdds[0], optOdds[1]);
		}
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getDayInWeek() {
		return dayInWeek;
	}

	public void setDayInWeek(String dayInWeek) {
		this.dayInWeek = dayInWeek;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public Map<String, String> getOptionOddsMap() {
		return optionOddsMap;
	}

	public void setOptionOddsMap(Map<String, String> optionOddsMap) {
		this.optionOddsMap = optionOddsMap;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	/**
	 * 通过中明投注选项，获取对应的赔率。
	 * @param zmOpt 中民出票选项，如：胜-胜
	 * @param playType 玩法
	 * @return 赔率
	 */
	public String getOddsByBuyOption(String zmOpt, PlayType playType) {
		String zmOddsOpt = Odds.transZMBuyOptToOddsOpt(playType, zmOpt);
		String odds = getOptionOddsMap().get(zmOddsOpt);
		return odds;
	}

	/**
	 * 解析安瑞智赢的比赛赔率
	 * @param matchWithOdds
	 * @param playId 
	 * @return
	 * @throws TranslateException 
	 */
	public static MatchOdds parseAnRuiMatchOdds(String matchWithOdds, String playId) throws TranslateException {
		AssertUtils.assertNotBlank(matchWithOdds, "matchWithOdds");
		MatchOdds matchOdds = new MatchOdds();
		Pattern pattern = Pattern.compile("(\\d+):(\\D+)(\\d+)\\((.+)\\):\\[(.+)\\]");
		
		Matcher matcher = pattern.matcher(matchWithOdds);
		
		String w,s,o,handicap;
		if (matcher.matches()){
			w = matcher.group(2); // 星期
			s = matcher.group(3); // 序号
			handicap = matcher.group(4);//盘口
			o = matcher.group(5); // 赔率
			
		}else{
			throw new TranslateException("Unknown AnRuiZhiYing Odds format: " + matchWithOdds);
		}
		matchOdds.setDayInWeek(Translator.cnCodeToNumCode(w));
		matchOdds.setSequence(s);
		matchOdds.setExtraInfo(StringUtils.equals(handicap, "0")?"":handicap);
		parseAnRuiOptionOdds(o, matchOdds,playId);
		return matchOdds;
	}

	private static void parseAnRuiOptionOdds(String optionOdds, MatchOdds matchOdds, String playId) {
		String optionOddsPairs = optionOdds;
		
		String[] pairs = optionOddsPairs.split("\\+");
		if(null!=pairs&&pairs.length>0){
			for (String pair : pairs) {
				String[] optOdds = pair.split("@");
				if(null!=optOdds&&optOdds.length==2){
					matchOdds.getOptionOddsMap().put(toZMOption(optOdds[0],playId), optOdds[1]);
				}
				
			}
		}
		
		
	}

	private static String toZMOption(String anRuiOption, String playId) {
		String result=null;
		String playType = PlayType.getLotteryIdByPlayId(playId);
		if(LotteryId.valueOf(playType)==LotteryId.JCLQ){
			result=MatchResultMap.getAnRuiJCLQ_RFSF_SF_SFC2DavMap().get(anRuiOption);
		}
		else if(LotteryId.valueOf(playType)==LotteryId.JCZQ){
			
			result=MatchResultMap.getAnRuiJCZQBF2DavMap().get(anRuiOption);
			if(StringUtils.isBlank(result)){//如果取不到，用原始的值
				result=anRuiOption;
			}
		}
		return result;
	}
}
