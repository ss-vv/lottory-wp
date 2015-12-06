package com.unison.lottery.mc.uni.parser;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.data.OddsBase;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.PlayType;

/**
 * 查询竞彩赔率(包括固定奖金、浮动奖金)的parser。是线程安全的。
 * <p>
 * 2013-05-30 增加竞彩足球不让球胜平负玩法。
 * </p>
 * @author Yang Bo
 */
public class QueryJCOddsParser extends MessageParser {

	private static final long serialVersionUID = 7316617974226533657L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String JCLQGG = "jclqgg";
	private static final String JCLQDG = "jclqdg";
	private static final String JCZQGG = "jczqgg";
	private static final String JCZQDG = "jczqdg";

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
	
	public QueryJCOddsParser(){
    	setExpectedTransCode(113);
    }
	
	/**
	 * 解析赛事信息，结果写入 status 中。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void parseBody(Element body, ParserStatus status) throws ParseException {
		List<Element> spElements = body.element("spInfos").elements("spInfo");
		QueryJCOddsParserStatus oddsParserStatus = (QueryJCOddsParserStatus) status;
		List<OddsBase> odds = oddsParserStatus.getOdds();
		for (Element spInfo : spElements) {
			List<OddsBase> allOddsOfMatch = constructAllOddsOfMatch(spInfo, oddsParserStatus);
			odds.addAll(allOddsOfMatch);
		}
	}

	private List<OddsBase> constructAllOddsOfMatch(Element spInfo,
			QueryJCOddsParserStatus oddsParserStatus) throws ParseException {
		String sp = spInfo.attributeValue("sp");
		String matchId = spInfo.attributeValue("matchId");
		String matchTime = spInfo.attributeValue("matchtime");
		boolean isZC = true;
		boolean isDG = true;
		if (oddsParserStatus.getType().equals(JCZQDG)){
			isZC = true;
			isDG = true;
		}else if (oddsParserStatus.getType().equals(JCZQGG)){
			isZC = true;
			isDG = false;
		}else if (oddsParserStatus.getType().equals(JCLQDG)){
			isZC = false;
			isDG = true;
		}else if (oddsParserStatus.getType().equals(JCLQGG)){
			isZC = false;
			isDG = false;
		}else{
			throw new ParseException("Unknown odds match type: " + oddsParserStatus.getType());
		}
		return jcOdds(matchId, matchTime, sp, isZC, isDG);
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
			if (StringUtils.isBlank(spOfPlay[i])){
				continue;
			}
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
		AssertUtils.assertNotBlank(sp, "sp");
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
		reOrderOptionsAndOdds(odds);
	}

	/**
	 * 按照大V彩js需要的options和odds顺序重新排列
	 * @param odds 被重新排列的赔率对象
	 */
	private void reOrderOptionsAndOdds(OddsBase odds) {
		Map<String,String> optionOddsMap = new HashMap<String,String>();
		String[] options = odds.getOptions().split(",");
		for (int i=0; i<options.length; i++){
			optionOddsMap.put(options[i], odds.getOddsList().get(i));
		}
		PlayType playType = PlayType.valueOfLcPlayId(odds.getPlayId());
		String lcOptionStr = playTypeLCOptionsMap.get(playType);
		String[] lcOptions = lcOptionStr.split(",");
		List<String> lcOdds = new LinkedList<String>();
		for (String lcOption : lcOptions){
			lcOdds.add(optionOddsMap.get(lcOption));
		}
		odds.setOptions(lcOptionStr);
		odds.setOdds(lcOdds);
	}
}
