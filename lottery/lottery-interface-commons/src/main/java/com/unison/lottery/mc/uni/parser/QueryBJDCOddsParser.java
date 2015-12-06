package com.unison.lottery.mc.uni.parser;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.mc.uni.parser.util.IDMapper;
import com.unison.lottery.mc.uni.parser.util.ZMInterfaceConstants;
import com.xhcms.lottery.dc.data.BDOdds;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.lang.PlayTypeZMOptions;

/**
 * 查询北京单场赔率的parser。是线程安全的。
 */
public class QueryBJDCOddsParser extends MessageParser {

	private static final long serialVersionUID = 7316617974226533657L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDMapper idMapper;
	
	public QueryBJDCOddsParser(){
    	setExpectedTransCode(108);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void parseBody(Element body, ParserStatus status)
			throws ParseException {
		Element spInfos = body.element("spInfos");
		List<Element> spElements = spInfos.elements("spInfo");
		QueryBJDCOddsParserStatus oddsParserStatus = (QueryBJDCOddsParserStatus) status;
		List<BDOdds> odds = oddsParserStatus.getOdds();
		String issueNumber = spInfos.attributeValue("issueNumber");
		for (Element spInfo : spElements) {
			BDOdds matchOdds = composeMatch(issueNumber, spInfo, oddsParserStatus);
			if(null != matchOdds) {
				odds.add(matchOdds);
			}
		}
	}

	private BDOdds composeMatch(String issueNumber, Element spInfo,
			QueryBJDCOddsParserStatus oddsParserStatus) throws ParseException {
		String type = oddsParserStatus.getType();
		String sp = spInfo.attributeValue("sp");
		String matchId = spInfo.attributeValue("matchId");
		String matchTime = spInfo.attributeValue("matchtime");
		
		BDOdds odds = jcOdds(matchId, matchTime, sp, type);
		if(null != odds) {
			odds.setIssueNumber(issueNumber);
		}
		return odds;
	}
	
	private BDOdds jcOdds(String matchId, String matchTime, String sp, String type) throws ParseException {
		AssertUtils.assertNotBlank(sp, "sp");
		AssertUtils.assertNotBlank(matchTime, "matchTime");
		Date offtime = null;
		try {
			offtime = TimeUtils.parseOfftimeFromMatchStartTime(matchTime, "yyyyMMddHHmm");
		} catch (java.text.ParseException e) {
			logger.error("Can not parse matchtime: " + matchTime);
			throw new ParseException(e);
		}
		PlayType playType = null;
		if(ZMInterfaceConstants.SPF.equals(type)) {
			playType = PlayType.BJDC_SPF;
		} else if(ZMInterfaceConstants.SF.equals(type)) {
			playType = PlayType.BJDC_SF;
		} else if(ZMInterfaceConstants.BQC.equals(type)) {
			playType = PlayType.BJDC_BQC;
		} else if(ZMInterfaceConstants.SXDS.equals(type)) {
			playType = PlayType.BJDC_SXDS;
		} else if(ZMInterfaceConstants.BF.equals(type)) {
			playType = PlayType.BJDC_BF;
		} else if(ZMInterfaceConstants.JQS.equals(type)) {
			playType = PlayType.BJDC_JQS;
		} else {
			return null;
		}
		
		BDOdds oddsOfPlay = createOdds(playType);
		oddsOfPlay.setOfftime(offtime);
		oddsOfPlay.setCode(matchId);
		oddsOfPlay.setOdds(Arrays.asList(sp.split(",")));
		
		return oddsOfPlay;
	}
	
	/**
	 * 根据玩法类型创建一个BDOdds赔率对象。
	 * @param playType
	 * @return
	 */
	private BDOdds createOdds(PlayType playType) {
		BDOdds bdOdds = new BDOdds();
		bdOdds.setPlayId(playType.getShortPlayStr());
		bdOdds.setOptions(PlayTypeZMOptions.playTypeZMOptionsMap.get(playType));
		return bdOdds;
	}
	
	/**
	 * 转换彩种id
	 * @param lotteryId
	 * @throws ParseException
	 */
	protected String parseLotteryId(String lotteryId) throws ParseException{
		String new_lotteryId = idMapper.getLCLotteryIdFromPlatformLotteryId(lotteryId);
		if (StringUtils.isBlank(new_lotteryId)){
			throw new ParseException("Unsupported type: " + lotteryId);
		}
		return new_lotteryId;
	}
}