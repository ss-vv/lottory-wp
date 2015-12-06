package com.unison.lottery.api.bet.parse;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

/**
 * 主要功能支持客户端m@n的过关方式；将客户端投注的m@1的过关合并成m@n
 * 例如选择三场比赛，过关方式选择2@1,3@1；可以将过关方式转换为3@4；
 * 只针对过关玩法，不支持单关（playId必须是过关玩法）
 * 
 * 支持的彩种有：竞彩足球、竞彩篮球；
 * 支持比赛某种玩法复选投注
 * 不支持玩法：一场比赛多种玩法的投注；
 * 不支持设胆：投注内容含胆；
 * @author lei.li@davcai.com
 */
public class SupportClientPassTypeMN {
	
	private static final Logger logger = LoggerFactory.getLogger(SupportClientPassTypeMN.class);
	private static final Map<String,String> passTypeToMXNMap = new HashMap<String, String>();
	
	static {
		//选3场
		passTypeToMXNMap.put("3-2@1", "3@3");
		passTypeToMXNMap.put("3-2@1,3@1", "3@4");
		//选4场
		passTypeToMXNMap.put("4-3@1", "4@4");
		passTypeToMXNMap.put("4-3@1,4@1", "4@5");
		passTypeToMXNMap.put("4-2@1", "4@6");
		passTypeToMXNMap.put("4-2@1,3@1,4@1", "4@11");
		//选5场
		passTypeToMXNMap.put("5-4@1", "5@5");
		passTypeToMXNMap.put("5-4@1,5@1", "5@6");
		passTypeToMXNMap.put("5-2@1", "5@10");
		passTypeToMXNMap.put("5-3@1,4@1,5@1", "5@16");
		passTypeToMXNMap.put("5-2@1,3@1", "5@20");
		passTypeToMXNMap.put("5-2@1,3@1,4@1,5@1", "5@26");
		//选6场
		passTypeToMXNMap.put("6-5@1", "6@6");
		passTypeToMXNMap.put("6-5@1,6@1", "6@7");
		passTypeToMXNMap.put("6-2@1", "6@15");
		passTypeToMXNMap.put("6-3@1", "6@20");
		passTypeToMXNMap.put("6-4@1,5@1,6@1", "6@22");
		passTypeToMXNMap.put("6-2@1,3@1", "6@35");
		passTypeToMXNMap.put("6-3@1,4@1,5@1,6@1", "6@42");
		passTypeToMXNMap.put("6-2@1,3@1,4@1", "6@50");
		passTypeToMXNMap.put("6-2@1,3@1,4@1,5@1,6@1", "6@57");
		//选7场
		passTypeToMXNMap.put("7-6@1", "7@7");
		passTypeToMXNMap.put("7-6@1,7@1", "7@8");
		passTypeToMXNMap.put("7-5@1", "7@21");
		passTypeToMXNMap.put("7-4@1", "7@35");
		passTypeToMXNMap.put("7-2@1,3@1,4@1,5@1,6@1,7@1", "7@120");
		//选8场
		passTypeToMXNMap.put("8-7@1", "8@8");
		passTypeToMXNMap.put("8-7@1,8@1", "8@9");
		passTypeToMXNMap.put("8-6@1", "8@28");
		passTypeToMXNMap.put("8-5@1", "8@56");
		passTypeToMXNMap.put("8-4@1", "8@70");
		passTypeToMXNMap.put("8-2@1,3@1,4@1,5@1,6@1,7@1,8@1", "8@247");
		
		//提供冗余配置，主要是用在对用户投注的串关方式做时间限制
		passTypeToMXNMap.put("1-1@1", "1@1");
		passTypeToMXNMap.put("2-2@1", "2@1");
		passTypeToMXNMap.put("3-3@1", "3@1");
		passTypeToMXNMap.put("4-4@1", "4@1");
		passTypeToMXNMap.put("5-5@1", "5@1");
		passTypeToMXNMap.put("6-6@1", "6@1");
		passTypeToMXNMap.put("7-7@1", "7@1");
		passTypeToMXNMap.put("8-8@1", "8@1");
	}

	/**
	 * 尝试将客户端投注的m@1的过关合并成m@n;
	 * 如果转换失败则返回原有的串关方式
	 * @param lotteryId 彩种ID
	 * @param playId 玩法ID
	 * @param betContent 投注格式字符串
	 * @param passType	串关类型
	 * @param betType 
	 * @param schemeId
	 * @return
	 */
	public static ParsePassTypeResult translator(String lotteryId, String playId, String betContent, String passType, String betType, String schemeId) {
		logger.info("用户投注参数:lotteryId={}, betType={}, playId={}, passType={}, betContent={}", 
				new Object[]{lotteryId, betType, playId, passType, betContent});
		
		String result = passType;
		ParsePassTypeResult parsePassTypeResult = new ParsePassTypeResult(passType, passType);

		//竞彩篮球、所有单关都不做限制
		if(!lotteryId.equals(LotteryId.JCZQ.name()) || playId.endsWith("_1")) {
			parsePassTypeResult.setConvertSuccess(true);
			if(lotteryId.equals(LotteryId.JCLQ.name())) {
				//convert m@n
				ParsePassTypeResult newParsePassTypeResult = isCanTranslator(lotteryId, playId, betContent, passType,betType);
				if(newParsePassTypeResult.isConvertSuccess() && StringUtils.isNotBlank(betContent)) {
					List<String> betList = Arrays.asList(betContent.split(","));
					result = parsePassType(passType, betList);
					if(StringUtils.isNotBlank(result)) {
						parsePassTypeResult.setConvertPassType(result);
					}
				}
			}
			return parsePassTypeResult;
		}
		
		//如果客户端传过来的是m@n
		if(StringUtils.isNotBlank(passType) && passType.split(",").length == 1) {
			String[] mCn = passType.split("@");
			if(Integer.parseInt(mCn[1]) > 1) {
				parsePassTypeResult.setConvertPassType(passType);
				parsePassTypeResult.setConvertSuccess(true);
				return parsePassTypeResult;
			}
		}
		if(StringUtils.equals(betType, String.valueOf(EntityType.BET_TYPE_JION_PARTNER))){//参与合买，肯定是转换成功的
			parsePassTypeResult.setConvertSuccess(true);
		} else {
			parsePassTypeResult = isCanTranslator(lotteryId, playId, betContent, passType,betType);
			if(parsePassTypeResult.isConvertSuccess() && StringUtils.isNotBlank(betContent)) {
				List<String> betList = Arrays.asList(betContent.split(","));
				result = parsePassType(passType, betList);
				if(StringUtils.isNotBlank(result)) {
					parsePassTypeResult.setConvertPassType(result);
					parsePassTypeResult.setConvertSuccess(true);
				} else {
					parsePassTypeResult.setConvertSuccess(false);
				}
			} else if(parsePassTypeResult.isSkipLimit()) {
				parsePassTypeResult.setConvertPassType(passType);
				parsePassTypeResult.setConvertSuccess(true);
			}
		}
		
		logger.info("投注类型={}, 方案ID={}, MXN转换之后的结果={}", betType, schemeId, parsePassTypeResult);
		return parsePassTypeResult;
	}
	
	/**
	 * 判断是否能将多个m@1合并为一个m@n进行投注
	 * @param lotteryId
	 * @param playId
	 * @param betContent
	 * @param passType
	 * @param betType 
	 * @return
	 */
	private static ParsePassTypeResult isCanTranslator(String lotteryId, String playId, 
			String betContent, String passType, String betType) {
		ParsePassTypeResult passTypeResult = new ParsePassTypeResult(passType, passType);
		boolean result = false;
		if(StringUtils.isNotBlank(lotteryId)) {
			boolean isTypePartner = StringUtils.equals(betType, String.valueOf(EntityType.BET_TYPE_PARTNER));
			boolean isTypeAlone = StringUtils.equals(betType, String.valueOf(EntityType.BET_TYPE_ALONE));
			if(isTypePartner || isTypeAlone) {//代购或发起合买
				boolean isSuppLottery = lotteryId.equals(LotteryId.JCZQ.name())
						|| lotteryId.equals(LotteryId.JCLQ.name());
				boolean isNotNull = StringUtils.isNotBlank(playId)
						&& StringUtils.isNotBlank(betContent);
				boolean isGG = playId.endsWith("_2");
				if (isSuppLottery && isNotNull && isGG) {
					//betContent=201504245001-50013-false-spf,201504245001-50013-false-brqspf
					List<String> betList = Arrays.asList(betContent.split(","));
					if (betList.size() > 1) {
						Set<String> betSet = new HashSet<String>();
						boolean isSeed = false;
						for (String bet : betList) {
							String[] options = bet.split("-");
							betSet.add(options[0]);
							// 不支持设胆串关合并
							if (options.length >= 3 && options[2].equals("true")) {
								isSeed = true;
								break;
							}
						}
						if (!isSeed) {//非胆码投注
							boolean isOneMatchOnePlay = (betList.size() == betSet.size());
							//如果是一场比赛一种玩法
							if(isOneMatchOnePlay) {
								result = true;
							} else if((MXNParser.isOneMatchMultiplePlayHHMX1(betContent, passType))) {
								//对同一场比赛的不同玩法进行混合投注中，只允许使用M串1方式的投注(一场比赛多种玩法的m@1)
								passTypeResult.setSkipLimit(true);
							}
						}
					}
				}
			} else if(StringUtils.equals(betType, String.valueOf(EntityType.BET_TYPE_FOLLOW))){//跟单
				if(MXNParser.isMX1(passType) || MXNParser.isMXN(passType)) {
					result = true;
				} else {
					result = false;
				}
				logger.info("用户参与跟单, 方案串关方式={}, 是否为m@1或m@n={}", 
						new Object[]{passType, result});
			}
		}
		passTypeResult.setConvertSuccess(result);
		return passTypeResult;
	}
	
	/**
	 * 解析多个m@1;如果合并匹配则返回对应m@n,否则返回null
	 * @param passType
	 * @param betList
	 * @return
	 */
	private static String parsePassType(String passType, List<String> betList) {
		String result = null;
		int betMatchCount = betList.size();
		List<String> passTypes = Arrays.asList(passType.split(","));
		boolean canSwitch = false;
		for(String mCn : passTypes) {
			if(StringUtils.isNotBlank(mCn) && mCn.contains("@")) {
				String[] typeSplit = mCn.split("@");
				if(typeSplit.length == 2) {
					//分别解析出m@n的m、n
					int mCount = Integer.parseInt(typeSplit[0]);
					int nCount = Integer.parseInt(typeSplit[1]);
					if(mCount <= betMatchCount && nCount == 1) {
						canSwitch = true;
					} else {
						canSwitch = false;
						break;
					}
				}
			}
		}
		if(canSwitch) {
			String key = betList.size() + "-" + sortPassType(passType);
			String value = passTypeToMXNMap.get(key);
			if(StringUtils.isNotBlank(value)) {
				result = value;
			}
		}
		return result;
	}
	
	private static String sortPassType(String passType) {
		int countMatch = StringUtils.countMatches(passType, "@");
		if(countMatch <= 1) {
			return passType;
		}
		String[] passTypeArr = passType.split(",");
		Arrays.sort(passTypeArr);
		StringBuilder build = new StringBuilder();
		for(int i=0; i<passTypeArr.length; i++) {
			build.append(passTypeArr[i]);
			if(i != passTypeArr.length - 1) {
				build.append(","); 
			}
		}
		logger.info("对客户端传过来的串关字符串做排序,排序前={},排序后={}", 
				passType, build.toString());
		return build.toString();
	}
	
	/**
	 * 根据设定的时间仅支持m串1和m串n过关方式
	 * @param passTypeTimeLimit	对于非m@n串关的投注时间显示
	 * @param schemeOfftime		方案截止时间
	 * */
	public static boolean passTypeTimeLimit(String passTypeTimeLimit, Date schemeOfftime) {
		boolean isSupp = false;
		
		int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		Calendar cal = Calendar.getInstance();
		cal.setTime(schemeOfftime);
		int offTimeDay = cal.get(Calendar.DAY_OF_MONTH);
		if(offTimeDay > dayOfMonth) {
			return true;
		}
		
		if(StringUtils.isNotBlank(passTypeTimeLimit)) {
			String[] time = passTypeTimeLimit.split(":");
			if(time.length == 2) {
				int configHour = Integer.parseInt(time[0]);
				int configMinute = Integer.parseInt(time[1]);
				
				int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				int minute = Calendar.getInstance().get(Calendar.MINUTE);
				if(hourOfDay < configHour) {
					isSupp = true;
				} else if(hourOfDay == configHour) {
					if(minute < configMinute) {
						isSupp = true;
					}
				}
			}
		}
		return isSupp;
	}
	
	/**
	 * 是否在时间限制之前
	 * */
	public static boolean passTypeBeforeTimeLimit(String passTypeTimeLimit, Date schemeOfftime) {
		boolean isSupport = false;
		if(StringUtils.isBlank(passTypeTimeLimit)) {
			return false;
		}
		int timeLimit = Integer.parseInt(passTypeTimeLimit);
		if(timeLimit < 0) {
			isSupport = false;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(schemeOfftime);
			cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - timeLimit);
			if(new Date().getTime() <= cal.getTime().getTime()) {
				isSupport = true;
			}
			logger.info("最晚一场比赛截止时间={}, 提前截止分钟={}, 当前时间={}, 是否允许投注={}", 
					new Object[]{schemeOfftime, timeLimit, new Date(), isSupport});
		}
		return isSupport;
	}
	
}