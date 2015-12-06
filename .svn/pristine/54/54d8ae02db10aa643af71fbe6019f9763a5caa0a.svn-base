package com.xhcms.lottery.dc.platform.parser;

import java.util.ArrayList;
import java.util.Date; 
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchModel;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchesResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCPlayOddsModel;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiLotteryIdToDavcaiLotteryIdUtil;
import com.davcai.lottery.platform.client.qiutan.QiutanJCMatch;
import com.davcai.lottery.platform.client.qiutan.QiutanJCMatchInfo;
import com.davcai.lottery.platform.client.zunao.model.ZunAoJCMatcheOddsResponse;
import com.davcai.lottery.platform.constants.AnRuiZhiYingLotteryPlayType;
import com.unison.lottery.mc.uni.parser.ParseException;
import com.unison.lottery.mc.uni.parser.TimeUtils;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.OddsBase;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.dc.persist.service.MatchSupportPlayService;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.DateUtils;
import com.xhcms.lottery.utils.WeekUtil;

@Service
public class MatchOddsStoreParserImpl implements IMatchOddsStoreParser {
	private static final String ON_SALE = "1";
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MatchSupportPlayService supportPlayService;
	private static PlayType[] playTypesOfZC = new PlayType[]{
		PlayType.JCZQ_SPF,
		PlayType.JCZQ_ZJQS,
		PlayType.JCZQ_BF,
		PlayType.JCZQ_BQC,
		PlayType.JCZQ_BRQSPF
	};
	
	private static PlayType[] playTypesOfLC = new PlayType[]{
		PlayType.JCLQ_SF, 
		PlayType.JCLQ_RFSF,
		PlayType.JCLQ_SFC, 
		PlayType.JCLQ_DXF
	};
	// key为PlayType，值为 options（投注选项）且必须和《中民接口文档》中的赔率顺序一致。
	private static Map<PlayType, String> playTypeZMOptionsMap = new Hashtable<PlayType, String>();
	static{
		// 竞彩足球，让球胜平负
		playTypeZMOptionsMap.put(PlayType.JCZQ_SPF, "3,1,0");
		// 竞彩足球，不让球胜平负
		playTypeZMOptionsMap.put(PlayType.JCZQ_BRQSPF, "3,1,0");
		// 竞彩足球，比分
		playTypeZMOptionsMap.put(PlayType.JCZQ_BF, 
				"10,20,21,30,31,32,40,41,42,50,51,52,90," +
				"00,11,22,33,99," +
				"01,02,12,03,13,23,04,14,24,05,15,25,09");
		// 竞彩足球，总进球数
		playTypeZMOptionsMap.put(PlayType.JCZQ_ZJQS, "0,1,2,3,4,5,6,7");
		// 竞彩足球，半全场
		playTypeZMOptionsMap.put(PlayType.JCZQ_BQC, "33,31,30,13,11,10,03,01,00");
		// 竞彩篮球，让分胜负
		playTypeZMOptionsMap.put(PlayType.JCLQ_RFSF, "1,2");
		// 竞彩篮球，胜负
		playTypeZMOptionsMap.put(PlayType.JCLQ_SF, "1,2");
		// 竞彩篮球，胜分差
		playTypeZMOptionsMap.put(PlayType.JCLQ_SFC, "01,02,03,04,05,06,11,12,13,14,15,16");
		// 竞彩篮球，大小分
		playTypeZMOptionsMap.put(PlayType.JCLQ_DXF, "1,2");
	}
	
	// key为 PlayType，值为 options（投注选项），顺序为大V彩前端js要求的顺序。
	private static Map<PlayType, String> playTypeLCOptionsMap = new Hashtable<PlayType, String>();
	static{
		// 竞彩足球，让球胜平负
		playTypeLCOptionsMap.put(PlayType.JCZQ_SPF, "3,1,0");
		// 竞彩足球，不让球胜平负
		playTypeLCOptionsMap.put(PlayType.JCZQ_BRQSPF, "3,1,0");
		// 竞彩足球，比分
		playTypeLCOptionsMap.put(PlayType.JCZQ_BF, 
				"10,20,21,30,31,32,40,41,42,50,51,52,90," +
				"00,11,22,33,99," +
				"01,02,12,03,13,23,04,14,24,05,15,25,09");
		// 竞彩足球，总进球数
		playTypeLCOptionsMap.put(PlayType.JCZQ_ZJQS, "0,1,2,3,4,5,6,7");
		// 竞彩足球，半全场
		playTypeLCOptionsMap.put(PlayType.JCZQ_BQC, "33,31,30,13,11,10,03,01,00");
		// 竞彩篮球，让分胜负
		playTypeLCOptionsMap.put(PlayType.JCLQ_RFSF, "2,1");
		// 竞彩篮球，胜负
		playTypeLCOptionsMap.put(PlayType.JCLQ_SF, "2,1");
		// 竞彩篮球，胜分差
		playTypeLCOptionsMap.put(PlayType.JCLQ_SFC, "11,01,12,02,13,03,14,04,15,05,16,06");
		// 竞彩篮球，大小分
		playTypeLCOptionsMap.put(PlayType.JCLQ_DXF, "1,2");
	}
	
	@Override
	public List<OddsBase> parseMatcheOdds(LotteryPlatformBaseResponse resp,
			String platformId, LotteryId lotteryId) {
		if(LotteryPlatformId.AN_RUI_ZHI_YING.equals(platformId)){
			return parseByAnRuiResp(resp,lotteryId);
		} else if(LotteryPlatformId.ZUN_AO.equals(platformId)){
			return parseByZhongMinResp(resp,lotteryId);
		} else if(LotteryPlatformId.QIU_TAN.equals(platformId)){
			return parseByQiutanResp(resp,lotteryId);
		}
		return new ArrayList<OddsBase>();
	}

	private List<OddsBase> parseByQiutanResp(LotteryPlatformBaseResponse resp,
			LotteryId lotteryId) {
		List<OddsBase> odds = new ArrayList<OddsBase>();
		boolean isZC = "JCZQ".equals(lotteryId.name());
		if(resp instanceof QiutanJCMatchInfo){
			List<QiutanJCMatch> qiutanJCMatchs = ((QiutanJCMatchInfo)resp).getMatches();
			try{
				for (QiutanJCMatch qiutanJCMatch : qiutanJCMatchs) {
					String matchId = qiutanJCMatch.getMatchid();
					Date date = TimeUtils.parseOfftimeFromMatchStartTime(qiutanJCMatch.getMatchtime(), "yyyyMMddhhmmss");
					String ggSP = makeSPStringFromQiutanMatch(qiutanJCMatch);
					String dgSP = ggSP;
					List<OddsBase> dgmatchOdds = jcOdds(matchId, DateUtils.format(date), dgSP, isZC, true);
					List<OddsBase> ggmatchOdds = jcOdds(matchId, DateUtils.format(date), ggSP, isZC, false);
					odds.addAll(dgmatchOdds);
					odds.addAll(ggmatchOdds);
				}
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		return odds;
	}
	private String makeSPStringFromQiutanMatch(QiutanJCMatch qiutanJCMatch){
		StringBuilder ggSPBuilder = new StringBuilder();
		//顺序：胜平负| 进球数| 比分| 半全场|不让球胜平负，每一个赔率“,”隔开
		ggSPBuilder.append(qiutanJCMatch.getWl3()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getWl1()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getWl0()); ggSPBuilder.append("|");
		//进球数
		ggSPBuilder.append(qiutanJCMatch.getT0()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getT1()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getT2()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getT3()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getT4()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getT5()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getT6()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getT7()); ggSPBuilder.append("|");
		//比分
		ggSPBuilder.append(qiutanJCMatch.getSw10()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw20()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw21()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw30()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw31()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw32()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw40()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw41()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw42()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw50()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw51()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw52()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSw5()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSd00()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSd11()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSd22()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSd33()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSd4()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl01()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl02()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl12()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl03()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl13()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl23()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl04()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl14()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl24()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl05()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl15()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl25()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSl5()); ggSPBuilder.append("|");
		//半全场
		ggSPBuilder.append(qiutanJCMatch.getHt33()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getHt31()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getHt30()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getHt13()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getHt11()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getHt10()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getHt03()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getHt01()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getHt00()); ggSPBuilder.append("|");
		//不让球胜平负
		ggSPBuilder.append(qiutanJCMatch.getSf3()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSf1()); ggSPBuilder.append(",");
		ggSPBuilder.append(qiutanJCMatch.getSf0());
		return ggSPBuilder.toString();
	}
	private List<OddsBase> parseByZhongMinResp(
			LotteryPlatformBaseResponse resp, LotteryId lotteryId) {
		if(resp instanceof ZunAoJCMatcheOddsResponse){
			ZunAoJCMatcheOddsResponse r = (ZunAoJCMatcheOddsResponse)resp;
			return r.getOdds();
		} else {
			return new ArrayList<OddsBase>();
		}
	}

	private List<OddsBase> parseByAnRuiResp(LotteryPlatformBaseResponse resp,
			LotteryId lotteryId) {
		if(resp instanceof AnRuiZhiYingJCMatchesResponse){
			AnRuiZhiYingJCMatchesResponse response = (AnRuiZhiYingJCMatchesResponse)resp;
			List<AnRuiZhiYingJCMatchModel> anRuimatches = response.getMatches();
			try {
				return composeMatcheOddsFromAnRuiResp(anRuimatches,lotteryId);
			} catch (ParseException e) {
				logger.error("",e);
				return new ArrayList<OddsBase>();
			}
		} else {
			return new ArrayList<OddsBase>();
		}
	}

	private List<OddsBase> composeMatcheOddsFromAnRuiResp(
			List<AnRuiZhiYingJCMatchModel> anRuimatches, LotteryId lotteryId) throws ParseException {
		List<OddsBase> odds = new ArrayList<OddsBase>();
		for (AnRuiZhiYingJCMatchModel match : anRuimatches) {
			String matchId = match.getGameNo();
			boolean isZC = "JCZQ".equals(lotteryId.name());
			StringBuilder dgSPStringBuilder = new StringBuilder();
			StringBuilder ggSPStringBuilder = new StringBuilder();
			String [] dgSPArray = new String[5];
			String [] ggSPArray = new String[5];
			if(isZC){//足彩
				//按顺序组织赔率字符串
				//下标0，1，2，3，4，5
				//胜平负| 进球数| 比分| 半全场|不让球胜平负
				initZCSpArray(match, dgSPArray, ggSPArray);
			} else {//篮彩
				//按顺序组织赔率字符串
				//下标0，1，2，3，4
				//篮球胜负| 篮球让分胜负| 篮球胜分差| 篮球大小分
				initLCSpArray(match, dgSPArray, ggSPArray);
			}
			int maxlength = 5;
			if(isZC){
				maxlength = 5;
			} else {
				maxlength = 4;
			}
			for (int i = 0; i < dgSPArray.length && i < maxlength; i++) {
				dgSPStringBuilder.append(dgSPArray[i]+"|");
			}
			for (int i = 0; i < ggSPArray.length && i < maxlength; i++) {
				ggSPStringBuilder.append(ggSPArray[i]+"|");
			}
			String ggSP = ggSPStringBuilder.toString();
			ggSP = StringUtils.removeEnd(ggSP, "|");
			String dgSP =dgSPStringBuilder.toString();
			dgSP = StringUtils.removeEnd(dgSP, "|");
			List<OddsBase> dgmatchOdds = jcOdds(matchId, match.getGameTime(), dgSP, isZC, true);
			List<OddsBase> ggmatchOdds = jcOdds(matchId, match.getGameTime(), ggSP, isZC, false);
			odds.addAll(dgmatchOdds);
			odds.addAll(ggmatchOdds);
		}
		return odds;
	}

	private void initLCSpArray(AnRuiZhiYingJCMatchModel match,
			String[] dgSPArray, String[] ggSPArray) throws ParseException {
		for (AnRuiZhiYingJCPlayOddsModel playOdds: match.getPlayOdds()) {
			String dg = makeDG(playOdds);
			String gg = makeGG(playOdds);
			if(AnRuiZhiYingLotteryPlayType.JCLQ_SFC.equals(playOdds.getLotteryId())){
				if(StringUtils.isNotBlank(gg)){
					
					String[] ggArray = gg.split(",");
					if(ggArray.length<12){
						throw new ParseException("篮球胜分差赔率数量小于12个。出错的playOdds:"+playOdds);
					}
					gg = ggArray[1]+","+ggArray[3]+","+ggArray[5]+","+ggArray[7]+","+ggArray[9]+","+ggArray[11] +"," +
							ggArray[0]+","+ggArray[2]+","+ggArray[4]+","+ggArray[6]+","+ggArray[8]+","+ggArray[10];
				}
//				if(StringUtils.isBlank(gg)){
//					gg="1.00,1.00,1.00,1.00,1.00,1.00,1.00,1.00,1.00,1.00,1.00,1.00";
//				}
				if(StringUtils.isNotBlank(dg)){
					String[] dgArray = dg.split(",");
					if(dgArray.length>=12){
						dg = dgArray[1]+","+dgArray[3]+","+dgArray[5]+","+dgArray[7]+","+dgArray[9]+","+dgArray[11] +"," +
								dgArray[0]+","+dgArray[2]+","+dgArray[4]+","+dgArray[6]+","+dgArray[8]+","+dgArray[10];
					}
				}
				
				dgSPArray[2] = dg;
				ggSPArray[2] = gg;
			} else if(AnRuiZhiYingLotteryPlayType.JCLQ_DXF.equals(playOdds.getLotteryId())){
				if(StringUtils.isNotBlank(gg)){
					 gg= gg + "," + playOdds.getGgRq();
				}
				if(StringUtils.isNotBlank(dg)){
					dg=dg + "," + playOdds.getDgRq();//将默认比分追加到赔率之后，保持和尊傲接口一致
				}
				dgSPArray[3] = dg;
				ggSPArray[3]=gg;
			} else if(AnRuiZhiYingLotteryPlayType.JCLQ_RFSF.equals(playOdds.getLotteryId())){
				if(StringUtils.isNotBlank(gg)){
					String[] ggArray = gg.split(",");
					if(ggArray.length<2){
						throw new ParseException("篮球让分胜负赔率数量小于2个。出错的playOdds:"+playOdds);
					}
					gg = ggArray[1] + "," + ggArray[0];
					gg=gg + "," + playOdds.getGgRq();
				}
				if(StringUtils.isNotBlank(dg)){
					String[] dgArray = dg.split(",");
					if(dgArray.length>=2){
						dg = dgArray[1] + "," + dgArray[0];
					}
					dg=dg + "," + playOdds.getDgRq();//将让分追加到赔率之后，保持和尊傲接口一致
				}
				dgSPArray[1] = dg;
				ggSPArray[1] = gg;
			} else if(AnRuiZhiYingLotteryPlayType.JCLQ_SF.equals(playOdds.getLotteryId())){
				if(StringUtils.isNotBlank(gg)){
					String[] ggArray = gg.split(",");
					if(ggArray.length<2){
						throw new ParseException("篮球胜负赔率数量小于2个。出错的playOdds:"+playOdds);
					}
					gg = ggArray[1] + "," + ggArray[0];
				}
				if(StringUtils.isNotBlank(dg)){
					String[] dgArray = dg.split(",");
					
					
					if(dgArray.length>=2){
						dg = dgArray[1] + "," + dgArray[0];
					}
				}
				dgSPArray[0] = dg;
				ggSPArray[0] = gg;
			}
		}
	}

	private void initZCSpArray(AnRuiZhiYingJCMatchModel match,
			String[] dgSPArray, String[] ggSPArray) {
		for (AnRuiZhiYingJCPlayOddsModel playOdds: match.getPlayOdds()) {
			String dg = makeDG(playOdds);
			String gg = makeGG(playOdds);
			if(AnRuiZhiYingLotteryPlayType.JCZQ_BF.equals(playOdds.getLotteryId())){
				dgSPArray[2] = dg;
				ggSPArray[2] = gg;
			} else if(AnRuiZhiYingLotteryPlayType.JCZQ_BQC.equals(playOdds.getLotteryId())){
				dgSPArray[3] = dg;
				ggSPArray[3] = gg;
			} else if(AnRuiZhiYingLotteryPlayType.JCZQ_JQS.equals(playOdds.getLotteryId())){
				dgSPArray[1] = dg;
				ggSPArray[1] = gg;
			} else if(AnRuiZhiYingLotteryPlayType.JCZQ_RQSPF.equals(playOdds.getLotteryId())){
				dgSPArray[0] = dg;
				ggSPArray[0] = gg;
			} else if(AnRuiZhiYingLotteryPlayType.JCZQ_SPF.equals(playOdds.getLotteryId())){
				dgSPArray[4] = dg;
				ggSPArray[4] = gg;
			}
		}
	}

	private String makeGG(AnRuiZhiYingJCPlayOddsModel playOdds) {
		if(StringUtils.equals(playOdds.getGgSaleStatus(), ON_SALE)){//如果是在售状态，赔率值才有效
			return playOdds.getGgPv().replace("@", ",");
		}
		else{//如果是在售状态，赔率值才有效
			return "";
		}
		
	}

	private String makeDG(AnRuiZhiYingJCPlayOddsModel playOdds) {
		if(StringUtils.equals(playOdds.getDgGdSaleStatus(), ON_SALE)){//如果是在售状态，赔率值才有效
			return playOdds.getDgPv().replace("@", ",");
		}
		else{//如果是在售状态，赔率值才有效
			return "";
		}
		
	}
	
	/**
	 * 生成竞彩篮球的赔率对象。
	 * @param matchId 比赛场次
	 * @param sp 篮球是："篮球胜负|篮球让分胜负|篮球胜分差|篮球大小分"；足球是："胜平负|总进球数|比分|半全场|不让球胜平负"
	 * @param matchTime 比赛开始时间
	 * @param isZC true 竞彩足球；false 竞彩篮球
	 * @param isDG true 单关；false 过关
	 * @return 赔率对象
	 * @throws ParseException 
	 */
	private List<OddsBase> jcOdds(String matchId, String matchTime, String sp, boolean isZC, boolean isDG) throws ParseException {
		AssertUtils.assertNotBlank(sp, "sp");
		AssertUtils.assertNotBlank(matchTime, "matchTime");
		Date offtime = null;
		try {
			offtime = TimeUtils.parseOfftimeFromMatchStartTime(matchTime);
		} catch (java.text.ParseException e) {
			logger.error("Can not parse matchtime: " + matchTime);
			throw new ParseException(e);
		}
		String[] spOfPlay = sp.split("\\|", -1); // -1: 保留末尾的空串
		PlayType[] playTypeOfOdds = isZC ? playTypesOfZC : playTypesOfLC;
		String lotteryType = isZC ? "jczq" : "jclq";
		
		if (spOfPlay.length != playTypeOfOdds.length) {
			throw new ParseException("Format of '" +lotteryType+ "' SP is wrong: (" + 
					sp + ") Correct length is: " + playTypeOfOdds.length);
		}
		List<OddsBase> odds = new LinkedList<OddsBase>();
		for (int i=0; i<spOfPlay.length; i++){
			OddsBase oddsOfPlay = createOdds(playTypeOfOdds[i], isZC, isDG);
			oddsOfPlay.setOfftime(offtime);
			oddsOfPlay.setCode(Translator.cnCodeToNumCode(matchId));
			addOddsTo(oddsOfPlay, spOfPlay[i], oddsOfPlay.getOptions(), isZC);
			odds.add(oddsOfPlay);
		}
		return odds;
	}
	/**
	 * 根据玩法类型和是否单关创建一个 LCOdds 赔率对象。
	 * @param playType
	 * @param isZC
	 * @param isDG
	 * @return
	 */
	private OddsBase createOdds(PlayType playType, boolean isZC, boolean isDG) {
		if (isZC) {
			return new ZCOdds(playType.getPlayIdWithPass(isDG), 
					playTypeZMOptionsMap.get(playType));
		}else{
			return new LCOdds(playType.getPlayIdWithPass(isDG), 
					playTypeZMOptionsMap.get(playType));
		}
	}
	/**
	 *  根据 options 中的元素个数拆分并添加每个选项的赔率到 odds 对象，并添加“让分数”和“预设总分”。
	 *  如果 sp 为空就添加一个空sp值。
	 */
	private void addOddsTo(OddsBase odds, String sp, String options, boolean isZC) throws ParseException {
//		AssertUtils.assertNotBlank(sp, "sp");
		if(StringUtils.isNotBlank(sp)){
			int optionSize = options.split(",", -1).length;
			String[] spOdd = sp.split(",", -1);
			if (optionSize>spOdd.length){
				throw new ParseException("option length > spOdd length. sp: " + sp + "; option: " + options);
			}
			for (int i=0; i<optionSize; i++){
				odds.addOdd(spOdd[i]);
			}
			if (!isZC){
				// 对于篮彩
				if (spOdd.length > optionSize + 1) {
					throw new ParseException("length(odds) > length(options)+1, options="+options + ", sp=" + sp);
				}
				if (spOdd.length == optionSize + 1) {
					LCOdds lcOdds = (LCOdds) odds;
					String presetScore = spOdd[spOdd.length-1]; // 让分值、预设分值
					if (StringUtils.isNotBlank(presetScore)){
						lcOdds.setDefaultScore(Float.parseFloat(presetScore));
					}else{
						logger.error("presetScore is empty! matchId: {}, sp: {}", odds.getCode(), sp);
					}
				}
			}
			odds.setStatus(0);//状态正常
		}
		else{
			odds.setOdds(null);
			odds.setStatus(1);//状态异常
		}
		
		reOrderOptionsAndOdds(odds);
	}
	/**
	 * 按照大V彩js需要的options和odds顺序重新排列
	 * @param odds 被重新排列的赔率对象
	 */
	private void reOrderOptionsAndOdds(OddsBase odds) {
		
		PlayType playType = PlayType.valueOfLcPlayId(odds.getPlayId());
		String lcOptionStr = playTypeLCOptionsMap.get(playType);
		String[] lcOptions = lcOptionStr.split(",");
		if(StringUtils.isNotBlank(odds.getOptions())){
			
			
			if(null!=odds.getOddsList()&&!odds.getOddsList().isEmpty()){
				Map<String,String> optionOddsMap = new HashMap<String,String>();
				String[] options = odds.getOptions().split(",");
				for (int i=0; i<options.length; i++){
					optionOddsMap.put(options[i], odds.getOddsList().get(i));
				}
				
				List<String> lcOdds = new LinkedList<String>();
				for (String lcOption : lcOptions){
					lcOdds.add(optionOddsMap.get(lcOption));
				}
				
				odds.setOdds(lcOdds);
			}
			odds.setOptions(lcOptionStr);
		}
		
		
	}
}
