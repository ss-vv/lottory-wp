package com.xhcms.lottery.commons.client.translate;

import java.util.List;
import java.util.Map;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;

public class YuanChengMatchBetContent extends PlatformBetContent {

	public static PlatformBetContent parseMatchBetContentInLaiCaiFormat(
			String matchBet, String lcPlayId) {
		AssertUtils.assertNotBlank(matchBet, "matchBet");
		AssertUtils.assertNotBlank(lcPlayId, "lcPlayId");
		
		PlayType lcPlayType = PlayType.valueOfLcPlayId(lcPlayId);
		YuanChengMatchBetContent matchBetObj = new YuanChengMatchBetContent();
		if (matchBet.length()<=4&&!(LotteryId.BJDC.equals(lcPlayType.getLotteryId())||LotteryId.BDSF.equals(lcPlayType.getLotteryId()))){
			
			throw new IllegalArgumentException("matchBet length < 4 : " + matchBet);
		}else if(matchBet.length()<4&&LotteryId.BJDC.equals(lcPlayType.getLotteryId())){
			
			throw new IllegalArgumentException("matchBet length < 4 : " + matchBet);
		}
		// 是否 lcPlayId 为混合彩，是就解析出各自比赛对应的玩法
		PlayType playType = PlayType.valueOfLcPlayId(lcPlayId);
		String playId = lcPlayId;
		String bet = matchBet;
		if (playType.isHH()){
			String[] sa = matchBet.split(":");
			bet = sa[0];
			playId = MixPlayType.valueOfPlayName(sa[1]).getPlayId();
		}
		matchBetObj.setLcPlayId(playId);
		matchBetObj.setTicketPlayId(lcPlayId);
		//-----------判断玩法 北京单场场次格式 
		if(LotteryId.BJDC.equals(lcPlayType.getLotteryId())||LotteryId.BDSF.equals(lcPlayType.getLotteryId())){
			
		}else{
			matchBetObj.setDayInWeek(bet.substring(0, 1));
			matchBetObj.setSequence(bet.substring(1,4));
			matchBetObj.setExpectedResult(bet.substring(4));
		}
		return matchBetObj;
	}

	@Override
	public Map<String, String>[] getResultMaps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toPlatformMatchBetContent() throws TranslateException {
		PlayType ticPlayType = PlayType.valueOfLcPlayId(this.ticketPlayId);
		PlayType lcPlayType = PlayType.valueOfLcPlayId(this.lcPlayId);
		StringBuilder code = new StringBuilder();
		
		//测试时只能投周日的，部署生产时改回来
		code.append(this.dayInWeek);//code.append("7");
		
		code.append(this.sequence);
		if (ticPlayType.isHH()) {
			code.append(getYuanChengPlayTypeId(lcPlayType));
		}
		
		code.append(makeYuanChengResult(expectedResult, lcPlayType)).append("^");
		return code.toString();
	}
	
	private String makeYuanChengResult(String expectedResult,PlayType p){
		switch (p) {
		//足球
		case JCZQ_BF:
			return makeBFResult(expectedResult);
		case JCZQ_BQC:
			return makeBQCResult(expectedResult);
		case JCZQ_SPF:
			return makeSPFResult(expectedResult);
		case JCZQ_ZJQS:
			return makeJQSResult(expectedResult);
		case JCZQ_BRQSPF:
			return makeSPFResult(expectedResult);
		
		//篮球
		//让分胜负
		case JCLQ_RFSF:
			return makeSPFResult(expectedResult);
		//胜负
		case JCLQ_SF:
			return makeSPFResult(expectedResult);
		//胜分差
		case JCLQ_SFC:
			return makeSPFResult(expectedResult);
		//大小分
		case JCLQ_DXF:
			return makeSPFResult(expectedResult);
		
		default:
			return "";
		}
	}
	
	private String makeBFResult(String result){
		return result;
//		StringBuilder resBuilder = new StringBuilder();
//		String[] results = result.split("");
//		for (int i = 1; i < results.length-2; ) {
//			resBuilder.append(results[i]).append(results[i+1]);
//			i=i+2;
//		}
//		resBuilder.append(results[results.length-2]).append(results[results.length-1]);
//		return resBuilder.toString();
	}
	private String makeBQCResult(String result){
		return result;
//		StringBuilder resBuilder = new StringBuilder();
//		String[] results = result.split("");
//		for (int i = 1; i < results.length-2; ) {
//			resBuilder.append(results[i]).append(results[i+1]);
//			i=i+2;
//		}
//		resBuilder.append(results[results.length-2]).append(results[results.length-1]);
//		return resBuilder.toString();
	}
	private String makeJQSResult(String result){
		return result;
//		StringBuilder resBuilder = new StringBuilder();
//		String[] results = result.split("");
//		for (int i = 1; i < results.length-1; i++) {
//			resBuilder.append(results[i]);
//		}
//		resBuilder.append(results[results.length-1]);
//		return resBuilder.toString();
	}
	private String makeSPFResult(String result){
		return result;
//		StringBuilder resBuilder = new StringBuilder();
//		String[] results = result.split("");
//		for (int i = 1; i < results.length-1; i++) {
//			resBuilder.append(results[i]);
//		}
//		resBuilder.append(results[results.length-1]);
//		return resBuilder.toString();
	}
	
	private String getYuanChengPlayTypeId(PlayType pt){
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
	
	@Override
	public List<String> splitOptionsInPlatformFormat()
			throws TranslateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayType getMatchOrTicketPlayType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String translatePlatformExpectedResult() throws TranslateException {
		// TODO Auto-generated method stub
		return null;
	}
}
