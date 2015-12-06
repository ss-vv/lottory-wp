package com.xhcms.lottery.commons.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.xhcms.lottery.commons.client.translate.BetContent;
import com.xhcms.lottery.commons.client.translate.BetContentFactory;
import com.xhcms.lottery.commons.client.translate.MatchResultMap;
import com.xhcms.lottery.commons.client.translate.Odds;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryIdException;
import com.xhcms.lottery.lang.LotteryIdForZM;
import com.xhcms.lottery.lang.PlayType;

/**
 * 大V彩内部数据与接口数据之间的互相转换工具。
 * 
 * @author Yang Bo
 */
public class Translator {

	// 高频彩的 play id 与中民接口中BetType的映射
	private static Map<String,String> lcPlayIdZMBetTypeMap=new HashMap<String, String>();
	
	static {
		initHfPlayIdZMBetTypeMap();
	}
	
	/**
	 * 转换大V彩内部ticket投注内容为接口指定的投注格式。并将结果写入 ticket 的 actualCode 属性。
	 * @param tickets
	 * @throws TranslateException 
	 */
	public static void translateTicketCodeToPFormat(List<Ticket> tickets) throws TranslateException{
		for (Ticket ticket : tickets){
			ticket.setActualCode(translateTicketCodeToPFormat(ticket));
			//转换出多平台投注格式串
			Map<String,String> platformBetCode = BetContentFactory.parseBetToPlatformBetCode(ticket);
			ticket.setPlatformBetCodeMap(platformBetCode);
		}
	}
	
	private static void initHfPlayIdZMBetTypeMap() {
		//SSQ
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_SSQ_DS, Constants.ZM_BETTYPE_SSQ_DS); // 双色球单式投注
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_SSQ_FS, Constants.ZM_BETTYPE_SSQ_FS); // 双色球复式投注
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_SSQ_DT, Constants.ZM_BETTYPE_SSQ_DT); // 双色球胆拖投注
		
		//FC3D
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_FC3D_ZXDS, Constants.ZM_BETTYPE_FC3D_ZXDS); 
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_FC3D_ZXFS, Constants.ZM_BETTYPE_FC3D_ZXFS); 
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_FC3D_ZXHZ, Constants.ZM_BETTYPE_FC3D_ZXHZ); 
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_FC3D_ZX_DS, Constants.ZM_BETTYPE_FC3D_ZX_DS); 
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_FC3D_Z3FS, Constants.ZM_BETTYPE_FC3D_Z3FS); 
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_FC3D_Z3HZ, Constants.ZM_BETTYPE_FC3D_Z3HZ); 
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_FC3D_Z6FS, Constants.ZM_BETTYPE_FC3D_Z6FS); 
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_FC3D_Z6HZ, Constants.ZM_BETTYPE_FC3D_Z6HZ); 
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_FC3D_DXBH, Constants.ZM_BETTYPE_FC3D_DXBH); 
		
		//JX11
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_11_J5_R1, Constants.ZM_BETTYPE_11_RX_1);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_12_J5_R2, Constants.ZM_BETTYPE_11_RX_2);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_13_J5_R3, Constants.ZM_BETTYPE_11_RX_3);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_14_J5_R4, Constants.ZM_BETTYPE_11_RX_4);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_15_J5_R5, Constants.ZM_BETTYPE_11_RX_5);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_16_J5_R6, Constants.ZM_BETTYPE_11_RX_6);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_17_J5_R7, Constants.ZM_BETTYPE_11_RX_7);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_18_J5_R8, Constants.ZM_BETTYPE_11_RX_8);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_19_J5_D2, Constants.ZM_BETTYPE_11_ZHIXQ2);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_20_J5_D3, Constants.ZM_BETTYPE_11_ZHIXQ3);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_21_J5_G2, Constants.ZM_BETTYPE_11_ZUXQ2);
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_22_J5_G3, Constants.ZM_BETTYPE_11_ZUXQ3);

		// 中民新接口 - 重庆时时彩玩法编码
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_DXDS,      Constants.ZM_BETTYPE_CQSS_DXDS);     // 重庆时时彩 - 大小单双
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_1X_DS,     Constants.ZM_BETTYPE_CQSS_1X_DS);    // 重庆时时彩 - 一星直选单式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_2X_DS,     Constants.ZM_BETTYPE_CQSS_2X_DS);    // 重庆时时彩 - 二星直选单式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_2X_FS,     Constants.ZM_BETTYPE_CQSS_2X_FS);    // 重庆时时彩 - 二星直选复式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_2X_ZH,     Constants.ZM_BETTYPE_CQSS_2X_ZH);    // 重庆时时彩 - 二星组合
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_2X_HZ,     Constants.ZM_BETTYPE_CQSS_2X_HZ);    // 重庆时时彩 - 二星直选和值
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_2X_ZX_DS,  Constants.ZM_BETTYPE_CQSS_2X_ZX_DS); // 重庆时时彩 - 二星组选单式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_2X_ZX_ZH,  Constants.ZM_BETTYPE_CQSS_2X_ZX_ZH); // 重庆时时彩 - 二星组选组合
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_2X_ZX_FZ,  Constants.ZM_BETTYPE_CQSS_2X_ZX_FZ); // 重庆时时彩 - 二星组选分组
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_2X_ZX_HZ,  Constants.ZM_BETTYPE_CQSS_2X_ZX_HZ); // 重庆时时彩 - 二星组选和值
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_2X_ZX_BD,  Constants.ZM_BETTYPE_CQSS_2X_ZX_BD); // 重庆时时彩 - 二星组选包胆
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_DS,     Constants.ZM_BETTYPE_CQSS_3X_DS);    // 重庆时时彩 - 三星直选单式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_FS,     Constants.ZM_BETTYPE_CQSS_3X_FS);    // 重庆时时彩 - 三星直选复式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_ZH,     Constants.ZM_BETTYPE_CQSS_3X_ZH);    // 重庆时时彩 - 三星组合
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_ZH_FS,  Constants.ZM_BETTYPE_CQSS_3X_ZH_FS); // 重庆时时彩 - 三星组合复式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_HZ,     Constants.ZM_BETTYPE_CQSS_3X_HZ);    // 重庆时时彩 - 三星直选和值
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_Z3_DS,  Constants.ZM_BETTYPE_CQSS_3X_Z3_DS); // 重庆时时彩 - 三星组三单式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_Z3_FS,  Constants.ZM_BETTYPE_CQSS_3X_Z3_FS); // 重庆时时彩 - 三星组三复式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_Z6_DS,  Constants.ZM_BETTYPE_CQSS_3X_Z6_DS); // 重庆时时彩 - 三星组六单式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_Z6_FS,  Constants.ZM_BETTYPE_CQSS_3X_Z6_FS); // 重庆时时彩 - 三星组六复式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_ZX_HZ,  Constants.ZM_BETTYPE_CQSS_3X_ZX_HZ); // 重庆时时彩 - 三星组选合值
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_3X_ZX_BD,  Constants.ZM_BETTYPE_CQSS_3X_ZX_BD); // 重庆时时彩 - 三星组选包胆
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_5X_TX,     Constants.ZM_BETTYPE_CQSS_5X_TX);    // 重庆时时彩 - 五星通选
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_5X_DS,     Constants.ZM_BETTYPE_CQSS_5X_DS);    // 重庆时时彩 - 五星直选单式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_5X_FS,     Constants.ZM_BETTYPE_CQSS_5X_FS);    // 重庆时时彩 - 五星直选复式
		lcPlayIdZMBetTypeMap.put(Constants.PLAY_CQSS_5X_ZH,     Constants.ZM_BETTYPE_CQSS_5X_ZH);    // 重庆时时彩 - 五星组合
	}

	/**
	 * 转换大V彩内部ticket投注内容为接口指定的投注格式。
	 * @param code 大V彩ticket投注内容。
	 * @return 接口规定格式的投注内容。
	 * @throws TranslateException 
	 */
	public static String translateTicketCodeToPFormat(Ticket ticket) throws TranslateException {
		String code = ticket.getCode();
		try {
			AssertUtils.assertNotBlank(code, "code");
			BetContent betContent = BetContentFactory.parseBetContentInLaiCaiFormat(code, ticket.getPlayId(), ticket.getLotteryPlatformId());
			return betContent.toPlatformBetContent();
		} catch (TranslateException e) {
			throw new TranslateException("can not translate bet content: " + code, e);
		}
	}

	/**
	 * 根据大V彩的playId构造中民接口的lotteryId。 
	 * @throws TranslateException 没有playId对应的lotteryId。
	 */
	public static String lcPlayIdToZMLotteryId(String lcPlayId) throws TranslateException{
		if (StringUtils.isBlank(lcPlayId)){
			throw new IllegalArgumentException("playId can not be blank!");
		}
		PlayType playType = PlayType.valueOfLcPlayId(lcPlayId);
		if(null == playType){
			throw new TranslateException("Unsupported lcPlayId: " + lcPlayId);
		}
		try {
			return LotteryIdForZM.getZMLotteryIdFromLcPlayId(lcPlayId).getName();
		} catch (LotteryIdException e) {
			throw new TranslateException("Can not get ZM LotteryId from lcPlayId=" + lcPlayId, e);
		}
	}

	/**
	 * 转换BetType到接口(platform)格式。利用 passType 字段存放中民接口要求的 betType 值。
	 * @param tickets 要修改的tickets
	 */
	public static void translateBetType(List<Ticket> tickets) {
		for (Ticket ticket : tickets) {
			translateBetType(ticket);
		}
	}

	public static void translateBetType(Ticket ticket) {
		String playId = ticket.getPlayId();
		PlayType playType = PlayType.valueOfLcPlayId(playId);
		String passType = ticket.getPassTypeId();
		switch(playType.getLotteryId()){
		case JCLQ:
		case JCZQ:
		case BJDC:
		case BDSF:
			if(PlayType.JCSJBGJ.getShortPlayStr().equals(playId)) {
				passType = playId;
			} else {
				passType = lcPassTypeToZMPassType(ticket.getPassTypeId());
			}
			break;

		case SSQ:
		case JX11:
		case CQSS:
		case FC3D:
			passType = lcHfPlayIdToZMBetType(ticket.getPlayId(), ticket.getCode());
			break;
			
		case CTZC:
			// 不做修改
			break;
		default:break;
		}
		ticket.setPassTypeId(passType);
	}
	
	/**
	 * 转换高频彩playId到接口(platform)格式。
	 * @param lcPlayId 
	 * @param code
	 * @return
	 */
	static String lcHfPlayIdToZMBetType(String lcPlayId, String code) {
		AssertUtils.assertNotBlank(lcPlayId, "lcPlayId");
		if(lcPlayIdZMBetTypeMap.containsKey(lcPlayId)){
			return lcPlayIdZMBetTypeMap.get(lcPlayId)+decideDanshiOrFushi(lcPlayId,code);
		}
		return null;
	}

	/**
	 * 如果playid是前二或前三组选，则根据投注内容判断是单式还是复式,如果是单式，则返回"_D",如果是复式，则返回"_F"
	 * 单式:投注内容中有分号
	 * 复式：投注内容中无分号
	 * @param code 投注内容
	 * @return
	 */
	private static String decideDanshiOrFushi(String lcPlayId,String code) {
		String result="";
		if(StringUtils.isNotBlank(code)){
			if (lcPlayId.equals(Constants.PLAY_19_J5_D2)
					|| lcPlayId.equals(Constants.PLAY_20_J5_D3)) {
				if(code.contains(";")){
					result="_D";
				}
				else{
					result="_F";
				}
			}
		}
		return result;
	}

	/**
	 * 转换大V彩过关方式到中民接口格式。如果没有过关方式则返回null
	 * @param lcPassTypeId 大V彩的过关方式，形如：1@1
	 * @return 中民的过关方式，形如：P1_1
	 */
	public static String lcPassTypeToZMPassType(String lcPassTypeId) {
		AssertUtils.assertNotBlank(lcPassTypeId, "lcPassTypeId");
		
		String[] parts = lcPassTypeId.split("@");
		if (parts.length!=2){
			throw new RuntimeException("lcPassType format error! " + lcPassTypeId);
		}
		return String.format("P%s_%s", parts[0], parts[1]);
	}

	/**
	 * 把接口的交易结果状态转换为“大V彩”内部状态。
	 * @param platformStatus 接口状态
	 * @return 大V彩交易结果状态
	 * @throws TranslateException 出现不识别的平台状态。
	 */
	public static int translateOrderResultStatusToLCFormat(
			int platformStatus) throws TranslateException {
		// 目前只支持“中民”新接口
		switch(platformStatus){
		case Constants.ZM_TICKET_WAIT:
		case Constants.ZM_TICKET_IN_TRANSACTION:
			return EntityStatus.TICKET_BUYING;
		case Constants.ZM_TICKET_NOT_EXIST:
			return EntityStatus.TICKET_NOT_EXIST;
		case Constants.ZM_TICKET_SUCCESS:
			return EntityStatus.TICKET_BUY_SUCCESS;
		case Constants.ZM_TICKET_FAIL:
			return EntityStatus.TICKET_BUY_FAIL;
		default:
			throw new TranslateException("Unknown Platform Order Result Status: " + platformStatus);
		}
	}
	
	
	
	/**
	 * 把安瑞接口的交易结果状态转换为“大V彩”内部状态。
	 * @param platformStatus 接口状态
	 * @return 大V彩交易结果状态
	 * @throws TranslateException 出现不识别的平台状态。
	 */
	public static int translateAnRuiOrderResultStatusToDavFormat(
			int platformStatus) throws TranslateException {
		switch(platformStatus){
		case 1:
			return EntityStatus.TICKET_BUY_SUCCESS;
		case -1:
			return EntityStatus.TICKET_BUY_FAIL;
		default:
			throw new TranslateException("Unknown Platform Order Result Status: " + platformStatus);
		}
	}

	/**
	 * 把接口格式的赔率翻译为大V彩的格式。对于混合过关方式，采用中民的原始格式。
	 * @param actualOdds 接口赔率
	 * @throws TranslateException 
	 */
	public static String translateOddsToLCFormat(
			String actualOdds, 
			String lcBetContent, 
			String lcPlayId) throws TranslateException {
		
		PlayType pt = PlayType.valueOfLcPlayId(lcPlayId);
		if (pt.isHH()){
			return actualOdds;
		}
		if (PlayType.JCSJBGJ.getShortPlayStr().equals(pt.getShortPlayStr())){
			return actualOdds;
		}
		Odds odds = Odds.parseZMOdds(actualOdds);
		return odds.toLCOdds(lcBetContent, lcPlayId);
	}
	
	/**
	 * 把接口格式的赔率翻译为大V彩的格式。对于混合过关方式，采用中民的原始格式。
	 * @param anRuiReturnOdds 接口赔率
	 * @throws TranslateException 
	 */
	public static String translateAnRuiOddsToDavFormat(
			String anRuiReturnOdds, 
			String lcPlayId) throws TranslateException {
		PlayType pt = PlayType.valueOfLcPlayId(lcPlayId);
		String lotteryId = PlayType.getLotteryIdByPlayId(lcPlayId);
		Pattern pattern = Pattern
				.compile("(\\d+):(\\D+)(\\d+)(\\((\\+|\\-)?\\d+\\)):\\[(.+)\\]");// 安瑞返回赔率的正则表达式
		Pattern jclqPattern = Pattern
				.compile("(\\d+):(\\D+)(\\d+)\\(((\\+|\\-)?\\d+\\.\\d+|\\d)\\):\\[(.+)\\]");// 安瑞返回赔率的正则表达式
							
		StringBuilder davActualOdd = new StringBuilder();
		String[] matches = anRuiReturnOdds.split("/");
		// JCZQ
		if ("JCZQ".equals(lotteryId)) {
			if (pt.isHH()) {
				
				for (int j = 0; j < matches.length; j++) {
					Matcher matcher = pattern.matcher(matches[j]);
					if (matcher.matches()) {
						String matchId = matcher.group(1);// 安瑞赛事ID
						String dayInWeek = matcher.group(2);// 周几
						String matchCode = matcher.group(3);// 赛事编号
						String handicap = matcher.group(4);// 盘口
						// 去掉小括号
						handicap = handicap.substring(1, handicap.length() - 1);
						String option_odd_str_group = matcher.group(6);// 投注项和赔率串
						String davPlayOption = null;
						StringBuilder davPlayOptionStr = new StringBuilder();
						
							String option_odds[] = option_odd_str_group
									.split("\\+");

						for (int i = 0; i < option_odds.length; i++) {

							String[] option_odd_str = option_odds[i].split("@");
							String option = option_odd_str[0];
							String odd = option_odd_str[1];

							if (!handicap.equals("0")) {
								davPlayOption = "SPF";
								
								option = MatchResultMap.getAnRuiJCZQBF2DavMap()
										.get(option);

								davPlayOptionStr.append(option).append("=")
										.append(odd);
								if (i < option_odds.length - 1) {
									davPlayOptionStr.append(",");
								}
								
							} else {
								davPlayOption = MatchResultMap
										.getAnRuiJCZQ2DavPlayOptions().get(
												option);
							}

							if ("BF".equals(davPlayOption)) {
								option = MatchResultMap.getAnRuiJCZQBF2DavMap()
										.get(option);

								davPlayOptionStr.append(option).append("=")
										.append(odd);
								if (i < option_odds.length - 1) {
									davPlayOptionStr.append(",");
								}
							}
							


						}

						if("BF".equals(davPlayOption)|| "SPF".equals(davPlayOption)){
							
							davActualOdd.append(davPlayOption).append("@")
							.append(cnCodeToNumCode(dayInWeek))
							.append("-").append(matchCode).append(":")
							.append("[").append(davPlayOptionStr)
							.append("]");
						} else {
							davActualOdd.append(davPlayOption)
									.append("@")
									.append(cnCodeToNumCode(dayInWeek))
									.append("-")
									.append(matchCode)
									.append(":")
									.append("[")
									.append(option_odd_str_group.replace("@",
											"=").replace("+", ",")).append("]");
						}
						if (j < matches.length - 1) {
							davActualOdd.append("/");
						}
						
					}
				}
			} else {

				for (int i = 0; i < matches.length; i++) {
					Matcher matcher = pattern.matcher(matches[i]);
					if (matcher.matches()) {
						String option_odd_str_group = matcher.group(6);// 投注项和赔率串
						String option_odds[] = option_odd_str_group.split("\\+");
						for (int j = 0; j < option_odds.length; j++) {
							String[] option_odd_str = option_odds[j].split("@");
							String odd = option_odd_str[1];
							davActualOdd.append(odd);
							if (j < option_odds.length - 1) {
								davActualOdd.append("A");
							}
						}
						if (i != matches.length - 1) {
							davActualOdd.append("-");
						}

					}
				}
			}
		// JCLQ
		} else if ("JCLQ".equals(lotteryId)) {

			if (pt.isHH()) {
				for (int i = 0; i < matches.length; i++) {
					Matcher matcher = jclqPattern.matcher(matches[i]);
					if (matcher.matches()) {
						String matchId = matcher.group(1);// 安瑞赛事ID
						String dayInWeek = matcher.group(2);// 周几
						String matchCode = matcher.group(3);// 赛事编号
						String handicap = matcher.group(4);// 盘口
						//去掉小括号
						String option_odd_str_group = matcher.group(6);// 投注项和赔率串

						String davPlayOption = null;
						StringBuilder davPlayOptionStr = new StringBuilder();
						String option_odds[] = option_odd_str_group.split("\\+");

						for (int j = 0; j < option_odds.length; j++) {
							String[] option_odd_str = option_odds[j].split("@");
							String option = option_odd_str[0];
							String odd = option_odd_str[1];
							// 转换“小”为“DXF”等
							davPlayOption = MatchResultMap
									.getAnRuiJCLQ2DavPlayOptions().get(option);
							option = MatchResultMap
									.getAnRuiJCLQ_RFSF_SF_SFC2DavMap().get(
											option);
							if ("DXF".equals(davPlayOption)
									|| "RFSF".equals(davPlayOption)) {
								davPlayOptionStr.append(option)
										.append("=").append(odd).append("^")
										.append(handicap);
							} else if ("SF".equals(davPlayOption)
									|| "FC".equals(davPlayOption)) {
								davPlayOptionStr.append(option)
										.append("=").append(odd);
							} else {
								// do nothing
							}
							if (j < option_odds.length - 1) {
								davPlayOptionStr.append(",");
							} 
//							else if (j == option_odds.length - 1) {
//								davPlayOptionStr.append("]");
//							}

						}
						davActualOdd.append(davPlayOption).append("@")
								.append(cnCodeToNumCode(dayInWeek)).append("-")
								.append(matchCode).append(":").append("[")
								.append(davPlayOptionStr).append("]");
						if (i < matches.length - 1) {
							davActualOdd.append("/");
						}
					}
				}

			} else {//非混合
				StringBuilder davHandicap = new StringBuilder();
				StringBuilder davPlayOddStr = new StringBuilder();
				for (int i = 0; i < matches.length; i++) {
					Matcher matcher = jclqPattern.matcher(matches[i]);
					if (matcher.matches()) {
						String handicap = matcher.group(4);// 盘口
						
						String option_odd_str_group = matcher.group(6);// 投注项和赔率串
						String option_odds[] = option_odd_str_group.split("\\+");

						if (lcPlayId.startsWith(PlayType.JCLQ_SF
								.getShortPlayStr())
								|| lcPlayId.startsWith(PlayType.JCLQ_SFC
										.getShortPlayStr())) {
							for (int j = 0; j < option_odds.length; j++) {
								String[] option_odd_str = option_odds[j]
										.split("@");
								String odd = option_odd_str[1];
								davPlayOddStr.append(odd);
								if (j < option_odds.length - 1) {
									davPlayOddStr.append("A");
								}
							}
						} else {
							if(lcPlayId.startsWith(PlayType.JCLQ_DXF
									.getShortPlayStr())){//竞彩篮球大小分，安瑞的盘口需要把+号去掉
								handicap=handicap.replace("+", "");
							}
							davHandicap.append(handicap);
							for (int j = 0; j < option_odds.length; j++) {
								String[] option_odd_str = option_odds[j]
										.split("@");
								String odd = option_odd_str[1];
								davPlayOddStr.append(odd);
								// 如果复选
								if (j < option_odds.length - 1) {
									davPlayOddStr.append("A");
//									davHandicap.append("B");
								}
							}

						}

					}
					if (i < matches.length - 1) {
						davPlayOddStr.append("-");
						davHandicap.append("B");
					}

				}
				davActualOdd.append(davPlayOddStr);
				if(!davHandicap.toString().isEmpty()){
					davActualOdd.append("@").append(davHandicap);
				}
			}

		}

		
		return davActualOdd.toString();
	}
	
	/**
	 * 把中民的中奖状态转换为大V彩内部中奖状态。
	 * @param statusCode
	 * @return
	 * @throws TranslateException 
	 */
	public static int translatePrizeStatusCode(int statusCode) throws TranslateException {
		switch(statusCode){
		case Constants.ZM_PRIZE_CANCEL:
			return EntityStatus.TICKET_NOT_EXIST;
		case Constants.ZM_PRIZE_WIN:
			return EntityStatus.TICKET_NOT_AWARD;
		case Constants.ZM_PRIZE_NOT_WIN:
			return EntityStatus.TICKET_NOT_WIN;
		case Constants.ZM_PRIZE_NOT_YET:
			return EntityStatus.TICKET_NOT_PRIZE;
		default:
			throw new TranslateException("Unknown zm prize status: " + statusCode);
		}
	}

	public static String getMessageOfLcPrizeStatus(int lcStatusCode) {
		switch(lcStatusCode){
			case EntityStatus.TICKET_NOT_EXIST:
				return "彩票不存在";
			case EntityStatus.TICKET_NOT_AWARD:
				return "彩票已中奖，但未派奖";
			case EntityStatus.TICKET_NOT_WIN:
				return "彩票未中奖";
			case EntityStatus.TICKET_NOT_PRIZE:
				return "彩票还未开奖";
			default:
				return "未知状态";
		}
	}

	/**
	 * 中文形式的“竞彩比赛场次”转换为数字形式的场次编号。
	 * @param cnCode 如：周一301
	 * @return 数字场次编号，如：1301
	 */
	public static String cnCodeToNumCode(String cnCode) {
        String code = null;
        if (cnCode.startsWith("周一")) {
            code = "1";
        } else if (cnCode.startsWith("周二")) {
            code = "2";
        } else if (cnCode.startsWith("周三")) {
            code = "3";
        } else if (cnCode.startsWith("周四")) {
            code = "4";
        } else if (cnCode.startsWith("周五")) {
            code = "5";
        } else if (cnCode.startsWith("周六")) {
            code = "6";
        } else {
            code = "7";
        }
        return code + cnCode.substring(2);
    }
	
	

	
}
