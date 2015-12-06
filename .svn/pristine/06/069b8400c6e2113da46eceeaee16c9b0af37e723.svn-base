package com.xhcms.lottery.commons.utils.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.persist.service.BetException;

/**
 * 出票内容格式验证器。确保投注内容格式满足出票平台约束。
 * @author zhangdebin
 */
public class SSQBetCodeValidator extends BetCodeValidator{
	
	private static java.util.Map<PlayType, Pattern> playTypePatterns = new java.util.HashMap<PlayType, Pattern>();
	static {
		String regexUnit_Red  = "(0[1-9]|[1-2][0-9]|3[0-3])";
		String regexUnit_Blue = "(0[1-9]|1[0-6])";

		String pattern_SSQ_DS = "^((" + regexUnit_Red + ",){5}"    + regexUnit_Red + "{1}" + BetOption.BAR_REGEXP + regexUnit_Blue + "{1}(;|;?$))+";
		String pattern_SSQ_FS = "^("  + regexUnit_Red + ",){5,32}" + regexUnit_Red + "{1}" + BetOption.BAR_REGEXP + "(" + regexUnit_Blue + ",){0,15}" + regexUnit_Blue + "{1}$";
		String pattern_SSQ_DT = "^\\((" + regexUnit_Red + ",){0,4}"  + regexUnit_Red + "{1}" + "\\),"  + "(" + regexUnit_Red  + ",){0,15}(" + regexUnit_Red + "){1}" + BetOption.BAR_REGEXP + "(" + regexUnit_Blue + ",){0,15}" + regexUnit_Blue + "{1}$";

		playTypePatterns.put(PlayType.SSQ_DS, Pattern.compile(pattern_SSQ_DS)); // 双色球单式
		playTypePatterns.put(PlayType.SSQ_FS, Pattern.compile(pattern_SSQ_FS)); // 双色球复式
		playTypePatterns.put(PlayType.SSQ_DT, Pattern.compile(pattern_SSQ_DT)); // 双色球胆拖
	}
	
	/**
	 * 对投注的号码进行格式验证。
	 * 
	 * @param playType, 投注玩法
	 * @param betCode, 投注号码
	 * @throws BetException, 投注格式不符合要求。
	 * @return
	 */
	@Override
	public void valid(String betCode, PlayType playType) throws BetException {
		boolean checkResult = false;
		
		Pattern pattern = playTypePatterns.get(playType);
		if (pattern != null) {
			Matcher matcher = pattern.matcher(betCode);
			checkResult = matcher.matches();
			
			if (checkResult) {
				switch (playType) {
					case SSQ_DS: // 双色球单式
						break;
						
					case SSQ_FS: // 双色球复式
						break;
						
					case SSQ_DT: // 双色球胆拖
						break;
				}
			}
		}

		if (!checkResult) {
			throw new BetException("Invalid bet content: " + betCode + " || pattern: " + pattern.pattern());
		}
	}
}
