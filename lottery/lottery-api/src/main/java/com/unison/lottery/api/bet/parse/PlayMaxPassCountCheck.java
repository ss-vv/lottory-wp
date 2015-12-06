package com.unison.lottery.api.bet.parse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 玩法最大串关数检验
 * 竞彩足球：
 * 		2串n,3串n, 4串n 支持的玩法（胜平负;比分;半全场;总进球;不让球胜平负）
 * 		5串n,6串n支持的玩法（胜平负;总进球;不让球胜平负）
 * 		7串n,8串n 支持的玩法（胜平负; 不让球胜平负）
 * 竞彩篮球：
 * 		2串n,3串n , 4串n支持的玩法（大小分;让分胜负;胜分差;胜负）
 * 		5串n,6串n，7串n,8串n 支持的玩法（大小分;让分胜负;胜负）
 * @author lei.li@davcai.com
 */
public class PlayMaxPassCountCheck {

	private static final Logger logger = LoggerFactory.getLogger(PlayMaxPassCountCheck.class);
	
	/**初始化玩法支持的最大串关Map**/
	private static final Map<String,Integer> playTypeMaxPassTypeMap = new HashMap<String, Integer>();
	private static final Map<String,String> playTypeMap = new HashMap<String, String>();
	
	static {
		//竞彩足球各玩法支持的最大串关数
		playTypeMaxPassTypeMap.put(PlayType.JCZQ_BRQSPF.getPlayIdWithPass(false), 8);
		playTypeMaxPassTypeMap.put(PlayType.JCZQ_SPF.getPlayIdWithPass(false), 8);
		playTypeMaxPassTypeMap.put(PlayType.JCZQ_ZJQS.getPlayIdWithPass(false), 6);
		playTypeMaxPassTypeMap.put(PlayType.JCZQ_BF.getPlayIdWithPass(false), 4);
		playTypeMaxPassTypeMap.put(PlayType.JCZQ_BQC.getPlayIdWithPass(false), 4);
		
		//竞彩篮球各玩法支持的最大串关数
		playTypeMaxPassTypeMap.put(PlayType.JCLQ_SF.getPlayIdWithPass(false), 8);
		playTypeMaxPassTypeMap.put(PlayType.JCLQ_RFSF.getPlayIdWithPass(false), 8);
		playTypeMaxPassTypeMap.put(PlayType.JCLQ_DXF.getPlayIdWithPass(false), 8);
		playTypeMaxPassTypeMap.put(PlayType.JCLQ_SFC.getPlayIdWithPass(false), 4);
		
		//竞彩足球
		playTypeMap.put("brqspf", "80_ZC_2");
		playTypeMap.put("spf", "01_ZC_2");
		playTypeMap.put("bqc", "04_ZC_2");
		playTypeMap.put("bf", "02_ZC_2");
		playTypeMap.put("jqs", "03_ZC_2");
		//竞彩篮球
		playTypeMap.put("rfsf", "06_LC_2");
		playTypeMap.put("sf", "07_LC_2");
		playTypeMap.put("fc", "08_LC_2");
		playTypeMap.put("dxf", "09_LC_2");
	}
	
	/**
	 * 检查投注内容中各玩法是否超过最大投注串关数；支持彩种：竞彩足球、竞彩篮球；
	 * 若不超过返回true，否则返回false；注意非竞彩投注直接返回true
	 * @param lotteryId
	 * @param playId
	 * @param passType
	 * @param betContent
	 * @param type 
	 * @return
	 */
	public static boolean check(String lotteryId, String playId, String passType, String betContent, String betType) {
		boolean result = false;
		boolean isJC = lotteryId.equals(LotteryId.JCZQ.name()) || lotteryId.equals(LotteryId.JCLQ.name());
		boolean isFollowOrJoinPartner = (Integer.valueOf(betType) == EntityType.BET_TYPE_FOLLOW ||
				Integer.valueOf(betType) == EntityType.BET_TYPE_JION_PARTNER);
		if(!isJC || isFollowOrJoinPartner) {
			return true;
		}
		PlayType playType = PlayType.valueOfLcPlayId(playId);
		if(null != playType) {
			List<String> betList = Arrays.asList(betContent.split(","));
			Set<String> betSet = new HashSet<String>();
			Set<String> playSet = new HashSet<String>();
			for(String bet : betList) {
				String[] options = bet.split("-");
				betSet.add(options[0]);
				if(options.length == 4) {
					playSet.add(options[3]);
				}
			}
			int betMaxWithPassType = calcMaxMatchWithPassType(passType);
			if(!playType.isHH()) {
				Integer playMaxPass = playTypeMaxPassTypeMap.get(playId);
				if(null != playMaxPass && betMaxWithPassType <= playMaxPass) {
					result = true;
				} else {
					logger.info("投注包含玩法:playId={},max value={}, actual value={}, betContent={}",
							new Object[]{playId, playMaxPass, betMaxWithPassType, betContent});
				}
			} else {
				for(String playStr : playSet) {
					String playIdStr = playTypeMap.get(playStr);
					if(StringUtils.isNotBlank(playIdStr)) {
						Integer playMaxPass = playTypeMaxPassTypeMap.get(playIdStr);
						if(null != playMaxPass && betMaxWithPassType <= playMaxPass) {
							result = true;
						} else {
							result = false;
							logger.info("投注包含玩法:playId={},max value={},actual value={},betContent={}",
									new Object[]{playIdStr, playMaxPass, betMaxWithPassType, betContent});
							break;
						}
					}
				}
			}
		}
		return result;
	}

	private static int calcMaxMatchWithPassType(String passTypes) {
		List<String> passTypeList = Arrays.asList(passTypes.split(","));
		int max = 0;
		for(String mCn : passTypeList) {
			if(StringUtils.isNotBlank(mCn) && mCn.contains("@")) {
				String[] typeSplit = mCn.split("@");
				if(typeSplit.length >= 1) {
					int mCount = Integer.parseInt(typeSplit[0]);
					if(mCount > max) {
						max = mCount;
					}
				}
			}
		}
		return max;
	}
	
}
