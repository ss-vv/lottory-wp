package com.xhcms.lottery.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.exception.JXRuntimeException;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 将票code转换成对应实体店可出票Code并返回
 * @author haoxiang.jiang@davcai.com
 * @date 2015年3月19日 下午5:17:07
 */
public class Ticket2ShiTiDianCodeTool {
	
	private final static Logger logger = LoggerFactory.getLogger(Ticket2ShiTiDianCodeTool.class);
	
	/**实体店出票Code前缀*/
	private static Map<String, String> CODE_PREFIX_MAP = new HashMap<String, String>();
	/**实体店出票Code后缀-表示单注价格*/
	private static String CODE_POSTFIX = ",2";
	private static final Map<String, String> PLAY_TO_CODE = new HashMap<String, String>();
	private static final String tab="	";
	static {
		CODE_PREFIX_MAP.put("JCLQ_SF", "62222,竞彩足球胜平负,T,(62)");
		CODE_PREFIX_MAP.put("JCLQ_RFSF", "61111,竞彩足球胜平负,T,(61)");
		CODE_PREFIX_MAP.put("JCLQ_SFC", "63333,竞彩足球胜平负,T,(63)");
		CODE_PREFIX_MAP.put("JCLQ_DXF", "64444,竞彩足球胜平负,T,(64)");
		CODE_PREFIX_MAP.put("JCLQ_HH", "69999,竞彩足球胜平负,T,(69)");
		
		CODE_PREFIX_MAP.put("JCZQ_SPF", "56666,竞彩足球胜平负,T,(56)");//jczq_spf
		CODE_PREFIX_MAP.put("JCZQ_BRQSPF", "51111,竞彩足球胜平负,T,(51)");//jczq_brqspf
		CODE_PREFIX_MAP.put("JCZQ_BF", "52222,竞彩足球胜平负,T,(52)");//jczq_bf
		CODE_PREFIX_MAP.put("JCZQ_ZJQS", "53333,竞彩足球胜平负,T,(53)");
		CODE_PREFIX_MAP.put("JCZQ_BQC", "54444,竞彩足球胜平负,T,(54)");
		CODE_PREFIX_MAP.put("JCZQ_HH", "59999,竞彩足球胜平负,T,(59)");
		
		
		//玩法字符串到出票格式玩法编号
		PLAY_TO_CODE.put("BRQSPF", "51");
		PLAY_TO_CODE.put("BF", "52");
		PLAY_TO_CODE.put("JQS", "53");
		PLAY_TO_CODE.put("BQC", "54");
		PLAY_TO_CODE.put("SPF", "56");
		
		PLAY_TO_CODE.put("RFSF", "61");
		PLAY_TO_CODE.put("SF", "62");
		PLAY_TO_CODE.put("FC", "63");
		PLAY_TO_CODE.put("DXF", "64");
	}
	
	public static String convert(Ticket needConverttiTicket){
		PlayType playType = PlayType.valueOfLcPlayId(needConverttiTicket.getPlayId());
		if(null == playType || null == needConverttiTicket.getCode()){
			return "";//异常票
		}
		if(LotteryId.JCLQ.equals(playType.getLotteryId())){
			if(PlayType.JCLQ_SF.equals(playType) || PlayType.JCLQ_RFSF.equals(playType)
					|| PlayType.JCLQ_SFC.equals(playType) || PlayType.JCLQ_DXF.equals(playType)){
				return convertJCLQ_NOT_HH(playType,needConverttiTicket);
			} else if(PlayType.JCLQ_HH.equals(playType)){
				return convertJCLQ_HH(playType,needConverttiTicket);
			}
		} else if(LotteryId.JCZQ.equals(playType.getLotteryId())){
			if(PlayType.JCZQ_SPF.equals(playType) || PlayType.JCZQ_BRQSPF.equals(playType) 
					|| PlayType.JCZQ_ZJQS.equals(playType) || PlayType.JCZQ_BQC.equals(playType)) {
				return convertJCZQ_Exclude_BF(playType, needConverttiTicket);
			} else if(PlayType.JCZQ_BF.equals(playType)){//比分
				return convertJCZQ_BF(playType,needConverttiTicket);
			} else if(PlayType.JCZQ_HH.equals(playType)) {
				return convertJCZQ_HH(playType, needConverttiTicket);
			}
		}else if(LotteryId.CTZC.equals(playType.getLotteryId())){
			String betCode="";
			if(PlayType.CTZC_14.getShortPlayStr().equals(needConverttiTicket.getPlayId())||
					PlayType.CTZC_BQ.getShortPlayStr().equals(needConverttiTicket.getPlayId())||
					PlayType.CTZC_JQ.getShortPlayStr().equals(needConverttiTicket.getPlayId())){
				if(needConverttiTicket.getPassTypeId().equals("DS")){
					betCode=convertCTZC_DSexcludeCTZC_R9(needConverttiTicket.getPlayId(),needConverttiTicket.getCode());
				}else if(needConverttiTicket.getPassTypeId().equals("FS")){
					betCode=convertCTZC_FSexcludeCTZC_R9(needConverttiTicket.getPlayId(),needConverttiTicket.getCode());
				}
			}else if(PlayType.CTZC_R9.getShortPlayStr().equals(needConverttiTicket.getPlayId())){
				if(needConverttiTicket.getPassTypeId().equals("DS")){
					betCode=convertCTZC_R9_DS(needConverttiTicket.getCode());
				}else if(needConverttiTicket.getPassTypeId().equals("FS")){
					betCode=convertCTZC_R9_FS(needConverttiTicket.getCode());	
				}
			}
			if(StringUtils.isNotBlank(betCode)){
				return betCode+tab+needConverttiTicket.getMultiple()+tab+needConverttiTicket.getMoney();
			}
			if(StringUtils.isNotBlank(betCode)){
				return betCode+tab+needConverttiTicket.getMultiple()+tab+needConverttiTicket.getMoney();
			}
		}
		return "";
	}
	
	private static String convertJCLQ_HH(PlayType playType,Ticket needConverttiTicket) {
		return commonSplitBetCode(playType, needConverttiTicket);
	}
	
	private static String convertJCLQ_NOT_HH(PlayType playType,Ticket needConverttiTicket){
		StringBuilder betContentSB = new StringBuilder();
		String [] betCodeOptions = needConverttiTicket.getCode().split("-");
		for (int i = 0; i < betCodeOptions.length; i++) {
			String matchCode = betCodeOptions[i].substring(0,4);
			String betOptions = betCodeOptions[i].substring(4);
			if(betOptions.length() < 1){
				continue;
			}
			betContentSB.append(composeAMatchBetContent(matchCode,betOptions,i==(betCodeOptions.length-1)?true:false, null));
		}
		betContentSB = composePassTypeAndMulti(needConverttiTicket.getPassTypeId(), needConverttiTicket.getMultiple(), betContentSB);
		return composeReturnVal(playType, betContentSB.toString());
	}
	
	/**
	 * 补充串关方式和倍数
	 * @param passtype 过关方式 eg:"2@1"
	 * @param multi 倍数
	 * @return ","+passtype+","+multi
	 */
	private static StringBuilder composePassTypeAndMulti(String passtype,int multi,StringBuilder betContentSB){
		betContentSB.append(",");
		if("1@1".equals(passtype)) {
			betContentSB.append("单关");
		} else {
			betContentSB.append(passtype.replace("@", "串"));
		}
		betContentSB.append(",");
		betContentSB.append(multi+"");
		return betContentSB;
	}
	
	/**
	 * 
	 * @param matchCode
	 * @param betOptions
	 * @param isLast
	 * @param hhMark 混合投注标识串
	 * @return
	 */
	private static String composeAMatchBetContent(String matchCode,String betOptions,
			boolean isLast, String hhMark){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(matchCode);
		sb.append(">");
		if(StringUtils.isNotBlank(hhMark)) {
			sb.append(hhMark).append("-");
		}
		sb.append(betOptions);
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * 竞彩足球比分
	 * @param playType
	 * @param needConverttiTicket
	 * @return
	 */
	private static String convertJCZQ_BF(PlayType playType,Ticket needConverttiTicket){
		StringBuilder betContentSB = new StringBuilder();
	    String code=needConverttiTicket.getCode();
	    int multiple=needConverttiTicket.getMultiple();
	    String passType=needConverttiTicket.getPassTypeId();
	    if(StringUtils.isNotBlank(code)){
	    	betContentSB.append(convertJZBFCode(playType, code, null));
	    	betContentSB.append(",");
	    	if("1@1".equals(passType)){
	    		betContentSB.append("单关");
	    	}else{
	    		betContentSB.append(passType.replace("@", "串"));
	    	}
	    	betContentSB.append(",");
	    	betContentSB.append(multiple);
	    	betContentSB.append(",");
	    	betContentSB.append(2);
	    }
	    return appendPrintInfo(playType, betContentSB);
	}
	
	private static String convertJZBFCode(PlayType playType, String code, String hhMark) {
		StringBuilder betContentSB = new StringBuilder();
		String codes[]=code.split("-");
    	for(int i=0;i<codes.length;i++){
    		String match=codes[i].substring(0, 4);
    		betContentSB.append("(");
    		betContentSB.append(match);
    		betContentSB.append(">");
    		if(StringUtils.isNotBlank(hhMark)) {
    			betContentSB.append(hhMark).append("-");
    		}
    		String bet=codes[i].substring(4);
    		for(int k=0;k<bet.length();k+=2){
    			String b=bet.substring(k,k+2);
    			if(k==0){
    				betContentSB.append(b);
    			} else if("90".equals(b)||"99".equals(b)||"09".equals(b)) {
    				betContentSB.append(b);
    			} else {
    				betContentSB.append(b.charAt(0));
    				betContentSB.append("-");
    				betContentSB.append(b.charAt(1));
    			}
    		}
    		betContentSB.append(")");
    	}
		return betContentSB.toString();
	}
	
	/**
	 * 除比分之外的竞彩足球投注格式转换
	 * 支持玩法：胜平负，让球胜平负，总进球，半全场
	 * @param playType
	 * @param ticket
	 * @return
	 */
	private static String convertJCZQ_Exclude_BF(PlayType playType,Ticket ticket){
		return commonSplitBetCode(playType, ticket);
	}
	
	private static String convertJCZQ_HH(PlayType playType, Ticket ticket){
		return commonSplitBetCode(playType, ticket);
	}
	
	private static String commonSplitBetCode(PlayType playType, Ticket ticket) {
		StringBuilder betContent = new StringBuilder();
		String code = ticket.getCode();
		if(StringUtils.isBlank(code)) {
			logger.error("ticket code is null!");
			return "";
		}
		if(null == playType) {
			logger.error("ticket playType is null!");
			return "";
		}
		List<String> list = Arrays.asList(code.split("-"));
		if(playType.isHH()) {
			for(int i=0; i<list.size(); i++) {
				String betCode = list.get(i);
				if(StringUtils.isBlank(betCode) || betCode.length() <= 4) {
					throw new JXRuntimeException("ticket code=" + betCode + ", is invalid.");	
				}
				//711230:SPF-71294:JQS-71303:JQS
				int idx = betCode.indexOf(":");
				if(idx >= 5 && betCode.split(":").length == 2) {
					String matchCode = betCode.substring(0, 4);
					String betOptions = betCode.substring(4, idx);
					String playTypeStr = betCode.split(":")[1];
					String hhMark = PLAY_TO_CODE.get(playTypeStr);
					if("BF".equals(playTypeStr)) {
						betContent.append(convertJZBFCode(playType, betCode.split(":")[0], hhMark));
					} else {
						betContent.append(composeAMatchBetContent(matchCode, betOptions, 
								false, hhMark));
					}
				} else {
					logger.error("ticket code={} is invalid!", betCode);
				}
			}
		} else {
			for(int i=0; i<list.size(); i++) {
				String betCode = list.get(i);
				if(StringUtils.isBlank(betCode) || betCode.length() <= 4) {
					logger.error("ticket code={} is invalid!", betCode);
				}
				String matchCode = betCode.substring(0, 4);
				String betOptions = betCode.substring(4);
				betContent.append(composeAMatchBetContent(matchCode, betOptions,false, null));
			}
		}
		String passTypeId = ticket.getPassTypeId();
		String passType = "";
		if("1@1".equals(passTypeId)) {
			passType = "单关";
		} else {
			passType = ticket.getPassTypeId().replace("@", "串");
		}
		betContent.append(",").append(passType);
		betContent.append(",").append(ticket.getMultiple());
		return composeReturnVal(playType, betContent.toString());
	}
	
	/**
	 * 根据投注内容和玩法，组成最终的返回值（CODE_PREFIX+betContent+CODE_POSTFIX）
	 * @param playType
	 * @param betContent
	 * @return
	 */
	private static String composeReturnVal(PlayType playType,String betContent){
		StringBuilder sb = new StringBuilder();
		sb.append(CODE_PREFIX_MAP.get(playType.name()));
		sb.append(betContent);
		sb.append(CODE_POSTFIX);
		return sb.toString();
	}
	private static String appendPrintInfo(PlayType playType,StringBuilder sb){
		 sb.insert(0,CODE_PREFIX_MAP.get(playType.name()));
		 return sb.toString();
	}
	private static String convertCTZC_DSexcludeCTZC_R9(String playType,String code){
		if(StringUtils.isNotBlank(playType) && StringUtils.isNotBlank(code)){
			return code.replaceAll(",", "");
		}
		return "";
	}
	private static String convertCTZC_FSexcludeCTZC_R9(String playType,String code){
		if(StringUtils.isNotBlank(playType) && StringUtils.isNotBlank(code)){
			return code.replaceAll(",","-");
		}
		return "";
	}
	private static String convertCTZC_R9_DS(String code){
		if(StringUtils.isNotBlank(code)){
			return code.replaceAll("-", "*").replaceAll(",", "");
		}
		return null;
	}
	private static String convertCTZC_R9_FS(String code){
		if(StringUtils.isNotBlank(code)){
			return code.replaceAll("-", "*").replaceAll(",", "-");
		}
		return null;
	}

	/**
	 * 升级旧的投注内容
	 * 只针对竞彩篮球和竞彩足球的混合玩法做升级
	 * 原版本：.输入“59”进入混合，输入“3”选周三的比赛，输入“001”选择赛事，输入“51”选择足彩胜平负，输入“0”选择负，按F1确认；
 	 * 新版本：输入“59”进入混合，输入“3”选周三的比赛，输入“001”选择赛事，输入“1”选择足彩胜平负，输入0选择负，按F1确认。
	 * 对比：在混合过关中的51.52.53.54.56分别改成了1.2.3.4.6，其他玩法一样。
	 * 篮球亦是如此。
	 * 
	 * 原版：单倍多倍都是用“竞彩足球胜平负”
	 * 新版：单倍使用“竞彩足球比分”，多倍使用“竞彩足球胜平负”
	 * @param orignBetContent
	 * @return
	 */
	public static String upgradeBetContent(String orignBetContent) {
		if(StringUtils.isNotBlank(orignBetContent)){
			String newBetContent=orignBetContent;
			if(isJCHH(orignBetContent)){
				char headLetter=orignBetContent.charAt(0);
				
				if(headLetter=='5'){
					newBetContent=orignBetContent.replace(">51", ">1").replace(">52", ">2")
					.replace(">53", ">3").replace(">54", ">4")
					.replace(">56", ">6");
				}
				else if(headLetter=='6'){
					newBetContent=orignBetContent.replace(">61", ">1").replace(">62", ">2")
							.replace(">63", ">3").replace(">64", ">4");
				}
				
				
			}
			if(newBetContent.contains(",")){
				//升级玩法
				String[] os = newBetContent.split(",");
				if(null!=os&&os.length>=6){
					String multi = os[5];
					if(Integer.valueOf(multi)==1){//单倍
						newBetContent=newBetContent.replace("竞彩足球胜平负", "竞彩足球比分");
					}
				}
				
			}
			
			return newBetContent;
		}
		return null;
	}

	/**
	 * 是否竞彩篮球和竞彩足球的混合玩法
	 * @param orignBetContent
	 * @return
	 */
	private static boolean isJCHH(String orignBetContent) {
		boolean result=false;
		if(StringUtils.startsWith(orignBetContent, CODE_PREFIX_MAP.get("JCLQ_HH").split(",")[0])
				||StringUtils.startsWith(orignBetContent, CODE_PREFIX_MAP.get("JCZQ_HH").split(",")[0])){//以69999打头或者以59999打头
			result=true;
		}
		return result;
	}

	/**
	 * 尝试降级
	 * @param printBetContent
	 * @return
	 */
	public static String downgradeBetContent(String orignBetContent) {
		if(StringUtils.isNotBlank(orignBetContent)){
			String newBetContent=orignBetContent;
			if(isJCHH(orignBetContent)){
				char headLetter=orignBetContent.charAt(0);
				
				if(headLetter=='5'){
					newBetContent=orignBetContent.replace(">1", ">51").replace(">2", ">52")
					.replace(">3", ">53").replace(">4", ">54")
					.replace(">6", ">56");
				}
				else if(headLetter=='6'){
					newBetContent=orignBetContent.replace(">1", ">61").replace(">2", ">62")
							.replace(">3", ">63").replace(">4", ">64");
				}
				
			}
			if(newBetContent.contains(",")){
				//降级玩法
				String[] os = newBetContent.split(",");
				if(null!=os&&os.length>=6){
					String multi = os[5];
					if(Integer.valueOf(multi)==1){//单倍
						newBetContent=newBetContent.replace("竞彩足球比分", "竞彩足球胜平负");
					}
				}
				
			}
			
			return newBetContent;
		}
		return null;
	}

}
