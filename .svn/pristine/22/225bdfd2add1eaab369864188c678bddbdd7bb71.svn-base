package com.xhcms.lottery.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.service.BetOptionUtil;
import com.xhcms.lottery.lang.PlayType;

public class YuanChengChuoPiaoOdds2OddsUtil {
	private static Logger logger = LoggerFactory.getLogger(YuanChengChuoPiaoOdds2OddsUtil.class);
	public static String convert(Ticket t,String betCodeWithOdds){
		PlayType p = PlayType.valueOfLcPlayId(t.getPlayId());
		//ArrayList<String[]> oldOddList = betCode2Arr(p, t.getActualCode());
		//ArrayList<String[]> newOddList = betCode2Arr(p, betCodeWithOdds);
		
		ArrayList<String[]> oldOddList = betCode2ArrNew(p, t.getActualCode());
		ArrayList<String[]> newOddList = betCode2ArrNewOdds(oldOddList,p, betCodeWithOdds);
		
		StringBuilder newOddsStringBuilder = new StringBuilder();
		for (int i = 0; i < newOddList.size(); i++) {
			String[] olds = oldOddList.get(i);
			String[] news = newOddList.get(i);
			if(olds.length!=news.length){
				logger.error("新的带赔率的投注内容长度与原票中的投注内容长度不一致！old={},new={}",olds,news);
				return "";
			}
			for (int j = 0; j < olds.length-1; j++) {
				if(!olds[i].equals(news[i])){
					logger.error("新的带赔率的投注内容与原票中的投注内容不一致！old={},new={}",olds,news);
					return "";
				}
			}
			newOddsStringBuilder.append(makeOddsString(p,news));
			if(p.isHH()){
				newOddsStringBuilder.append("/");
			} else {
				newOddsStringBuilder.append("-");
			}
		}
		String ret = newOddsStringBuilder.toString();
		return ret.substring(0,ret.length()-1);
	}
	private static String makeOddsString(PlayType p, String[] odds){
		if(p.isHH()){
			return getPlayType(odds[0])+"@"+odds[1]+"-"+odds[2]+":"+getHHOptionAndOdds(odds[0],odds[3]);
		} else {
			return getOptionAndOdds(odds[2]);
		}
	}
	private static String getOptionAndOdds(String optionString) {
		String[] options = optionString.split(",");
		StringBuilder sb = new StringBuilder();
		for (String s : options) {
			int start = s.indexOf("(");
			int end = s.indexOf(")");
			String odd = s.substring(start+1, end);
			sb.append(odd).append("A"); 
		}
		sb.replace(sb.length()-1, sb.length(), "");
		return sb.toString();
	}
	private static String getHHOptionAndOdds(String p, String optionString) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		String[] options = optionString.split(",");
		String[] spfMap = new String[]{"负","平","","胜"};
		for (String s : options) {
			int start = s.indexOf("(");
			int end = s.indexOf(")");
			String opt = s.substring(0,start);
			String odd = s.substring(start+1, end);
			switch (p) {
				case "51":
					sb.append(spfMap[Integer.valueOf(opt)]);
					break;
				case "52":
					sb.append(BetOptionUtil.getJZ_BF_OPTION(opt));
					break;
				case "53":
					if("7".equals(opt)) {
						opt = "7+";
					}
					sb.append(opt);
					break;
				case "54":
					sb.append(BetOptionUtil.getJZ_BQC_OPTION(opt));
					break;
				case "56":
					sb.append(spfMap[Integer.valueOf(opt)]);
					break;
				default:
					break;
			}
			sb.append("=").append(odd).append(",");
		}
		sb.replace(sb.length()-1, sb.length(), "");
		sb.append("]");
		return sb.toString();
	}
	private static String getPlayType(String yuanchengPlayType){
		switch (yuanchengPlayType) {
			case "51":
				return "BRQSPF";
			case "52":
				return "BF";
			case "53":
				return "JQS";
			case "54":
				return "BQC";
			case "56":
				return "SPF";
			default:
				return "";
		}
	}
	private static ArrayList<String[]> betCode2Arr(PlayType p,String betCode){
		String [] contents = betCode.split("\\|");
		int oneLength = 3;
		if(p.isHH()){
			oneLength=4;
		}
		StringBuilder newsb = new StringBuilder(); 
		for (int i = 0; i < contents.length;i++) {
			newsb.append(contents[i]);
			if(i!=0 && (i+1)%oneLength==0){
				newsb.append("|");
			} else {
				newsb.append(":");
			}
		}
		String s = newsb.toString();
		contents = s.substring(0, s.length()-1).split("\\|");
		ArrayList<String[]> odds = new ArrayList<>();
		for (String ss : contents) {
			String[] os = new String[oneLength];
			int i=0;
			for (String string : ss.split(":")) {
				os[i]=string;
				i++;
			}
			odds.add(os);
		}
		return odds;
	}
	/**
	 * actual_Code
	 * @param p
	 * @param betCode
	 * @return
	 */
	private static ArrayList<String[]> betCode2ArrNew(PlayType p,String betCode)
	{
		//7001560^7002510^  70013^70023^
		//7001@1.500|7002@2.500
		//[51, 4, 003, 0,1]
		
		
		ArrayList<String[]> odds = new ArrayList<>();
		String[] betCodeSplit = betCode.split("\\^");
		for(String bet:betCodeSplit)
		{
			String[] oneBet = new String[bet.length()-1];
			if(bet.length()>5)
			{
				oneBet[0] = bet.substring(4, 6);//玩法代码
				oneBet[3] = makeItem(bet.substring(6, bet.length()),oneBet[0]);// ,
				oneBet[3] = oneBet[3].substring(0, oneBet[3].length()-1);
			}
			else
			{
				oneBet[0] = getYuanChengPlayTypeId(p);
				oneBet[3] = makeItem(bet.substring(4, bet.length()),oneBet[0]);// ,
				oneBet[3] = oneBet[3].substring(0, oneBet[3].length()-1);
			}
			oneBet[1] = bet.substring(0, 1);//周代码
			oneBet[2] = bet.substring(1, 4);//比赛代码
					
			odds.add(oneBet);
		}
		removePlayCode(p,odds);
		return odds;
	}
	
	private static void removePlayCode(PlayType p,ArrayList<String[]> list)
	{
		if(!p.isHH())
		{
			for(int i=0;i<list.size();i++)
			{
				String[] bet = list.get(i);// 51 4 001 0,0
				for(int j=0;j<bet.length-1;j++)
				{
					list.get(i)[j] = bet[j+1];
				}
			}
		}
		
	}
	
	private static ArrayList<String[]> betCode2ArrNewOdds(ArrayList<String[]> oldList,PlayType p,String betCode)
	{
		//7001560^7002510^  70013^70023^
		//7001@1.500|7002@2.500
		//[51, 4, 003, 0,1]
		betCode = Pattern.compile("\\([^\\(]*\\)").matcher(betCode).replaceAll("");//去掉让球括号
		String[] beCodes = betCode.split("\\|");
		if(beCodes.length != oldList.size())
		{
			logger.error("返回赔率和场次数数量不符");
			return oldList;
		}
		 @SuppressWarnings("unchecked")
		ArrayList<String[]> newlist = (ArrayList<String[]>) oldList.clone();
		for(int i=0;i<oldList.size();i++)
		{
			String str = oldList.get(i)[3];//0,1
			String bet = beCodes[i].substring(4, beCodes[i].length());//7001@1.500+@1.800   //0(1.500),1(1.800)
			String[] strs = str.split(",");
			String[] bets = bet.split("\\+");
			StringBuilder strBuilder = new StringBuilder();
			for(int j=0;j<strs.length;j++)
			{
				strBuilder.append(strs[j]).append("("+bets[j].replace("@", "")+")").append(",");
			}
			
			newlist.get(i)[3] = strBuilder.toString().substring(0,strBuilder.toString().length()-1);
		}
		removePlayCode(p,newlist);
		return newlist;
	}
	
	
	private static String makeItem(String bet,String douBet)
	{
		StringBuilder betBuilder = new StringBuilder();
		if(douBet.equals("52") || douBet.equals("54"))//竞猜代码为两位
		{
			for(int i=0;i<bet.length()/2;i++)
			{
				betBuilder.append(bet.substring(i*2,i*2+2));
				betBuilder.append(",");
				
			}
		}
		else//竞猜代码为1位
		{
			for(int i=0;i<bet.length();i++)
			{
				betBuilder.append(bet.substring(i,i+1));
				betBuilder.append(",");
			}
		}
		
		
		return betBuilder.toString();
	}
	
	private static String getYuanChengPlayTypeId(PlayType pt){
		switch (pt) {
		//足球
		case JCZQ_BF:
			return "52";
		case JCZQ_BQC:
			return "54";
		case JCZQ_SPF:
			return "56";
		case JCZQ_ZJQS:
			return "53";
		case JCZQ_BRQSPF:
			return "51";
		
		//篮球
			//让分胜负
		case JCLQ_RFSF:
			return "61";
		case JCLQ_SF:
			return "62";
		case JCLQ_SFC:
			return "63";
		case JCLQ_DXF:
			return "64";
		
		default:
			return "";
		}
	}
	
}
