package com.unison.lottery.mc.uni.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.dc.data.BDResult;
import com.xhcms.lottery.dc.data.LCResult;
import com.xhcms.lottery.dc.data.ZCResult;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.utils.NumberUtils;

/**
 * 查询赛果parser。是线程安全的。
 * @author Yang Bo
 */
public class QueryMatchResultsParser extends MessageParser {

	private static final String TOTAL_SCORE = "total_score";

	private static final String HALF_SCORE = "half_score";

	private static final long serialVersionUID = 1111092602761339238L;

	
	public QueryMatchResultsParser(){
    	setExpectedTransCode(109);
    }
	
	/**
	 * 解析赛事信息，结果写入 status 中。
	 */
	@Override
	public void parseBody(Element body, ParserStatus status) throws ParseException {
		QueryMatchResultsParserStatus matchResultsParserStatus = (QueryMatchResultsParserStatus) status;
		Element results = body.element("results");
		parseResult(results, matchResultsParserStatus);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void parseResult(Element results,
			QueryMatchResultsParserStatus matchResultsParserStatus) throws ParseException {
		String lotteryId = results.attributeValue("lotteryId");
		String lotteryType = matchResultsParserStatus.getType();
		String issueNumber=results.attributeValue("issueNumber");//北京单场需要期号
		if (lotteryId==null || !lotteryId.equals(lotteryType)) {
			throw new ParseException("lotteryId attribute of <results> error: " + lotteryId);
		}
		List resultList = results.elements("result");
		Iterator iter = resultList.iterator();
		while(iter.hasNext()) {
			Element result = (Element) iter.next();
			Object matchResult = composeResult(result, lotteryType,issueNumber);
			if (matchResult != null){
				matchResultsParserStatus.getMatchResults().add(matchResult);
			}else{
				logger.info("Match may be canceled: {}", result.toString());
			}
		}
	}
	
	private Object composeResult(Element result, String lotteryType,String issueNumber) throws ParseException {
		if (lotteryType.equals("jclq")){
			return composeLCResult(result);
		}else if (lotteryType.equals("jczq")){
			return composeZCResult(result);
		}else if (lotteryType.equals("SPF")){//北京单场
			return composeBDResult(result,issueNumber);
		}else if (lotteryType.equals("BF")){//北京单场
			return composeBDResult(result,issueNumber);
		}else if (lotteryType.equals("SXDS")){//北京单场
			return composeBDResult(result,issueNumber);
		}else if (lotteryType.equals("JQS")){//北京单场
			return composeBDResult(result,issueNumber);
		}else if (lotteryType.equals("BQC")){//北京单场
			return composeBDResult(result,issueNumber);
		}else if (lotteryType.equals("SF")){//北京单场胜负
			return composeBDSFResult(result,issueNumber);
		}else{
			throw new ParseException("Unsupported lotteryType: " + lotteryType);
		}
	}

	/**
	 * 构造篮球赛事结果对象
	 * @param result
	 * @return null 如果比赛取消
	 * @throws ParseException
	 */
	private LCResult composeLCResult(Element result) throws ParseException {
		LCResult lcResult = new LCResult();
		String cnCode = result.attributeValue("matchId");
		lcResult.setCode(Translator.cnCodeToNumCode(cnCode));
		String matchTime = result.attributeValue("matchtime");
		try {
			lcResult.setOfftime(TimeUtils.parseOfftimeFromMatchStartTime(matchTime));
		} catch (java.text.ParseException e) {
			throw new ParseException("Can not parse match time: " + matchTime);
		}
		String lcValue = result.attributeValue("value");
		if (StringUtils.isEmpty(lcValue)){
			return null;
		}
		lcResult.setScore(composeBBScore(lcValue));
		
		String goalStr = result.attributeValue("goal");
		String ougoalStr = result.attributeValue("ougoal");
		float goal = 0.0f, ougoal = 0.0f;
		
		if (!StringUtils.isEmpty(goalStr)){
			goal = Float.parseFloat(goalStr);
		}
		if (!StringUtils.isEmpty(ougoalStr)){
			ougoal = Float.parseFloat(ougoalStr);
		}
		lcResult.setConcedeScore(goal); // 让分盘口, 篮球让分胜负的预设分值
		lcResult.setGuessScore(ougoal); // 总分盘口，篮球大小分的预设分值
		// 取SP值，目前只取单关的
//		String dgSP = result.attributeValue("dsp");
//		if (StringUtils.isBlank(dgSP)){
//			throw new ParseException("jclq DGSP is blank! matchId: " + cnCode);
//		}
		// 竞彩篮球：胜负，让分胜负，胜分差，大小分 的开奖sp
//		String[] sp = dgSP.split(",", -1);
//		if (sp.length != 4){
//			throw new ParseException("jclq DGSP format error: " + dgSP);
//		}
//		lcResult.setSfSp(NumberUtils.safeParseDouble(sp[0]));
//		lcResult.setYfsfSp(NumberUtils.safeParseDouble(sp[1]));
//		lcResult.setSfcSp(NumberUtils.safeParseDouble(sp[2]));
//		lcResult.setDxfSp(NumberUtils.safeParseDouble(sp[3]));
		
		return lcResult;
	}
	
	/**
	 * @param lcValue 格式：主队得分,客队得分
	 * @return 客队得分:主队得分
	 */
	private String composeBBScore(String lcValue) {
		String[] scores = lcValue.split(",");
		StringBuilder builder = new StringBuilder();
		return builder.append(scores[1]).append(":").append(scores[0]).toString();
	}

	/**
	 * 构造足球比赛结果对象
	 * @param result
	 * @return null 如果该场赛事取消
	 * @throws ParseException
	 */
	private ZCResult composeZCResult(Element result) throws ParseException {
		ZCResult zcResult = new ZCResult();
		String cnCode = result.attributeValue("matchId");
		zcResult.setCode(Translator.cnCodeToNumCode(cnCode));
		String matchTime = result.attributeValue("matchtime");
		try {
			zcResult.setOfftime(TimeUtils.parseOfftimeFromMatchStartTime(matchTime));
		} catch (java.text.ParseException e) {
			throw new ParseException("Can not parse match time: " + matchTime);
		}
		// value: 该字符串包括：“半场主队进球 数, 半场客队进球数 ,全场主队进球数，全场客队进球数”
		String fbValue = result.attributeValue("value");
		if (StringUtils.isEmpty(fbValue)){
			return null;	// 赛事取消
		}
		Map<String,String> scoreAndHalfScore = getScoreFromValue(fbValue);
		zcResult.setScore(scoreAndHalfScore.get(TOTAL_SCORE));
		zcResult.setHalfScore(scoreAndHalfScore.get(HALF_SCORE));
		String polygoal = result.attributeValue("polygoal");
		if (StringUtils.isNotBlank(polygoal)){
			zcResult.setConcedePoints(Integer.parseInt(polygoal));
		}
		// 取SP值，目前只取单关的
//		String dgSP = result.attributeValue("dsp");
//		if (StringUtils.isBlank(dgSP)){
//			throw new ParseException("jczq DGSP is blank! matchId: " + cnCode);
//		}
		// 竞彩足球：胜平负开奖sp,进球数开奖sp,比分开奖sp,半全场开奖sp,不让球胜平负sp
//		String[] sp = dgSP.split(",", -1);
//		if (sp.length != 5){
//			throw new ParseException("jczq DGSP format error, length not 5: " + dgSP);
//		}
//		zcResult.setSfpSp(NumberUtils.safeParseDouble(sp[0]));
//		zcResult.setZjqSp(NumberUtils.safeParseDouble(sp[1]));
//		zcResult.setBfSp(NumberUtils.safeParseDouble(sp[2]));
//		zcResult.setBqcSp(NumberUtils.safeParseDouble(sp[3]));
//		zcResult.setBrqspfSp(NumberUtils.safeParseDouble(sp[4]));
		return zcResult;
	}
	/**
	 * 北京单场
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	private BDResult composeBDResult(Element result,String issueNumber) throws ParseException{
		BDResult bd=new BDResult();
		bd.setIssueNumber(issueNumber);
		if(result.attributeValue("matchId")!=null){
			
			bd.setMatchId(result.attributeValue("matchId"));
		}else {
			//无赛果
			return null;
		}
		
		// value: 该字符串包括：“半场主队进球 数, 半场客队进球数 ,全场主队进球数，全场客队进球数”
		String fbValue = result.attributeValue("value");
		if (StringUtils.isEmpty(fbValue)){
			return null;	// 赛事取消
		}
		Map<String,String> scoreAndHalfScore = getScoreFromValue(fbValue);
		bd.setScore(scoreAndHalfScore.get(TOTAL_SCORE));
		bd.setHalfScore(scoreAndHalfScore.get(HALF_SCORE));
		
		String sp = result.attributeValue("sp");
		if (StringUtils.isBlank(sp)){
			throw new ParseException("bjdc SP is blank! matchId: " + bd.getMatchId());
		}
		String[] sps = sp.split(",", -1);
		if (sps.length != 5){
			throw new ParseException("bjdc sp format error, length not 5: " + sps);
		}
		bd.setSfpSp(NumberUtils.safeParseDouble(sps[0]));
		bd.setJqsSp(NumberUtils.safeParseDouble(sps[1]));
		bd.setSxdsSp(NumberUtils.safeParseDouble(sps[2]));
		bd.setBfSp(NumberUtils.safeParseDouble(sps[3]));
		bd.setBqcSp(NumberUtils.safeParseDouble(sps[4]));
		return bd;
	}
	/**
	 * 北单胜负
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	private BDResult composeBDSFResult(Element result,String issueNumber) throws ParseException{
		BDResult bd=new BDResult();
		bd.setIssueNumber(issueNumber);
        if(result.attributeValue("matchId")!=null){
			
			bd.setMatchId(result.attributeValue("matchId"));
		}else {
			//无赛果
			return null;
		}
		// value: 该字符串包括：“半场主队进球 数, 半场客队进球数 ,全场主队进球数，全场客队进球数”
		String fbValue = result.attributeValue("value");
		if (StringUtils.isEmpty(fbValue)){
			return null;	// 赛事取消
		}
		bd.setResult(fbValue);
		String sp = result.attributeValue("sp");
		///if (StringUtils.isBlank(sp)){
			//throw new ParseException("bdsf SP is blank! matchId: " + bd.getMatchId());
		//}
		//北京单场胜负过关赔率有可能为空（赛事结束后有赛果但是没有最终赔率）
        if(!StringUtils.isBlank(sp)){
			
			bd.setSfSp(NumberUtils.safeParseDouble(sp));
		}
		bd.setSfSp(NumberUtils.safeParseDouble(sp));
		return bd;
	}
	

	/**
	 * 拆分竞彩足球的value字段。
	 * @param fbValue 该字符串格式：“半场主队进球 数,半场客队进球数 ,全场主队进球数,全场客队进球数”
	 * @return {'score':'全场比分', 'half_score':'半场比分'}
	 * @throws ParseException 
	 */
	private Map<String, String> getScoreFromValue(String fbValue) throws ParseException {
		AssertUtils.assertNotBlank(fbValue, "fbValue");
		String[] scores = fbValue.split(",", -1);
		if (scores.length!=4){
			throw new ParseException("football value format error: " + fbValue);
		}
		Map<String,String> result = new HashMap<String, String>();
		StringBuilder score = new StringBuilder();
		StringBuilder halfScore = new StringBuilder();
		result.put(HALF_SCORE, score.append(scores[0]).append(":").append(scores[1]).toString());
		result.put(TOTAL_SCORE, halfScore.append(scores[2]).append(":").append(scores[3]).toString());
		return result;
	}
	
}
