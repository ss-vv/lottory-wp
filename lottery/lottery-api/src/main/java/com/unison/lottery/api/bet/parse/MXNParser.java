package com.unison.lottery.api.bet.parse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public final class MXNParser {

	public static String filter(String passType) {
		if(StringUtils.isNotBlank(passType)) {
			if(passType.startsWith(",")) {
				passType = passType.substring(1);
			}
			if(passType.endsWith(",")) {
				passType = passType.substring(0, passType.length() - 1);
			}
		}
		return passType;
	}
	
	public static boolean isMX1(String passType) {
		boolean result = false;
		passType = filter(passType);
		
		if(StringUtils.isNotBlank(passType) && passType.split(",").length == 1){
			String[] mCn = passType.split("@");
			if(Integer.parseInt(mCn[1]) == 1) {
				result = true;
			}
		}
		return result;
	}
	
	public static boolean isMXN(String passType) {
		boolean result = false;
		passType = filter(passType);
		
		if(StringUtils.isNotBlank(passType) && passType.split(",").length == 1){
			String[] mCn = passType.split("@");
			if(Integer.parseInt(mCn[1]) > 1) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 是否包含一场比赛多种玩法投注
	 * @param betContent
	 * @return
	 */
	public static boolean isOneMatchMultiplePlayHH(String betContent) {
		if(StringUtils.isNotBlank(betContent)) {
			List<String> betList = Arrays.asList(betContent.split(","));
			if (betList.size() > 1) {
				Set<String> betSet = new HashSet<String>();
				for (String bet : betList) {
					String[] options = bet.split("-");
					betSet.add(options[0]);
				}
				if(betList.size() != betSet.size()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param passType
	 * @param betContent
	 * @return
	 */
	private static boolean isOneMatchMulPlayWithPassType(String passType, String betContent) {
		if(StringUtils.isNotBlank(betContent)) {
			List<String> betList = Arrays.asList(betContent.split(","));
			if (betList.size() > 1) {
				Set<String> betSet = new HashSet<String>();
				for (String bet : betList) {
					String[] options = bet.split("-");
					betSet.add(options[0]);
				}
				if(Integer.parseInt(passType.split("@")[0]) == betSet.size()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 是否是一场比赛多种玩法的mxn投注
	 * @param passType
	 * @return
	 */
	public static boolean isOneMatchMultiplePlayHHMXN(String betContent, String passType) {
		boolean isHHMXN = false;
		boolean result = isMXN(passType);
		if(result && isOneMatchMultiplePlayHH(betContent)) {
			isHHMXN = true;
		}
		return isHHMXN;
	}
	
	/**
	 * 是否是一场比赛多种玩法的mx1投注,这里的m@1中的m必须要和所选比赛数相同
	 * 比如3场比赛，有一场比赛多种玩法，只能选择3@1
	 * @param passType
	 * @return
	 */
	public static boolean isOneMatchMultiplePlayHHMX1(String betContent, String passType) {
		boolean isHHMXN = false;
		boolean result = isMX1(passType);
		if(result && isOneMatchMulPlayWithPassType(passType, betContent)) {
			isHHMXN = true;
		}
		return isHHMXN;
	}
	
}