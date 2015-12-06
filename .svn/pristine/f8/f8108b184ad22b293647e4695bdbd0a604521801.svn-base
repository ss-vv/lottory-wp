package com.unison.lottery.mc.uni.parser;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.mc.uni.parser.util.IDMapper;
import com.unison.lottery.mc.uni.parser.util.IDMapperException;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.task.ticket.OpenSaleIntercessor;
import com.xhcms.lottery.lang.PlayType;

/**
 * 查询ticket中奖情况的响应parser。是线程安全的。
 */
public class QueryMatchesBJDCParser extends MessageParser {

	private static final long serialVersionUID = -5847174050078276246L;
	
	@Autowired
	private IDMapper idMapper;
	
	public QueryMatchesBJDCParser(){
    	setExpectedTransCode(106);
    }
	
	/**
	 * 解析赛事信息，结果写入 status 中。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void parseBody(Element body, ParserStatus status) throws ParseException {
		List<Element> games = body.element("games").elements("game");
		QueryMatchesBJDCParserStatus matchesParserStatus = (QueryMatchesBJDCParserStatus) status;
		List<BDMatch> matches = matchesParserStatus.getMatches();
		PlayType playType;
		String playId="";
		try {
			playType = idMapper.getLCPlayTypeFromPlatformLotteryId(matchesParserStatus.getType());
			playId=playType.getPlayId();
		} catch (IDMapperException e) {
			logger.error("彩种转换玩法id失败！lotteryId:{}",matchesParserStatus.getType());
			e.printStackTrace();
		}
		matchesParserStatus.setPlayId(playId);
		for (Element game : games) {
			BDMatch match = constructMatch(game, matchesParserStatus);
			matches.add(match);
		}
	}
	
	private BDMatch constructMatch(Element game, QueryMatchesBJDCParserStatus matchesParserStatus) throws ParseException {
		BDMatch match = new BDMatch();
		String lotteryId = parseLotteryId(matchesParserStatus.getType());
		
		String matchId = game.attributeValue("matchID");
		String offtime = game.attributeValue("sellouttime");
		String playtime = game.attributeValue("matchtime");
		String home = game.attributeValue("hometeam").trim();
		String guest = game.attributeValue("guestteam").trim();
		String polygoal = game.attributeValue("remark"); //  让球数
		String status = game.attributeValue("matchstate").trim();

		match.setStatus(Integer.parseInt(status));
		match.setCode(matchId);
		match.setName(home + " VS " + guest);
		match.setLeague(game.attributeValue("name").trim());
		match.setHomeTeam(home);
		match.setGuestTeam(guest);
		match.setIssueNumber(matchesParserStatus.getIssueNumber());
		if (StringUtils.isNotBlank(polygoal)) {
			BigDecimal concedePoints = new BigDecimal(polygoal);
			match.setConcedePoints(concedePoints);
		}
		parseOfftime(lotteryId, match, offtime, playtime);
		
		logger.debug("{} entrustDeadLine: {}", new Object[]{
				matchId, match.getEntrustDeadline(),});

		return match;
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
	
	// 开赛时间格式
	protected SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmm");
	// 大V彩提前停售时间，分钟
	private int haltSales = 0;
	private OpenSaleIntercessor openSaleIntercessor;
	
	/**
	 * 比赛开始时间
	 */
	protected void parseOfftime(String lotteryId, BDMatch match, String time, String playtime) throws ParseException {
        Calendar playTime = Calendar.getInstance();
        Calendar offTime = Calendar.getInstance();
        try {
			playTime.setTime(formater.parse(playtime));
		} catch (java.text.ParseException e) {
			throw new ParseException("Can not parse play start time: " + playtime, e);
		}
        
        // 开赛时间
        match.setPlayingTime(playTime.getTime());
        // 周几
        match.setCnCode(getCNWeek(playTime)+match.getCode());
        try {
        	offTime.setTime(formater.parse(time));
		} catch (java.text.ParseException e) {
			throw new ParseException("Can not parse play off time: " + time, e);
		}
        // 停售时间
        Date offtime = offTime.getTime();
        match.setOfftime(offtime);
        // 代理停止出票时间
        //playTime.add(Calendar.MINUTE, haltSales);
        match.setEntrustDeadline(openSaleIntercessor.parseEntrustDeadline(lotteryId, playTime.getTime()));
        
        
    }
	
	private String getCNWeek(Calendar calendar){
		String dayOfWeek = "";
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
				
		        case Calendar.SUNDAY:
		            dayOfWeek = "周日";
		            break;
		        case Calendar.MONDAY:
		            dayOfWeek = "周一";
		            break;
		        case Calendar.TUESDAY:
		            dayOfWeek = "周二";
		            break;
		        case Calendar.WEDNESDAY:
		            dayOfWeek = "周三";
		            break;
		        case Calendar.THURSDAY:
		            dayOfWeek = "周四";
		            break;
		        case Calendar.FRIDAY:
		            dayOfWeek = "周五";
		            break;
		        case Calendar.SATURDAY:
		            dayOfWeek = "周六";
		            break;
		        default:
		        	throw new RuntimeException("Can case CNWeek : "  + calendar.get(Calendar.DAY_OF_WEEK));
			}
		return dayOfWeek;
	}
		

	public int getHaltSales() {
		return haltSales;
	}

	public void setHaltSales(int haltSales) {
		this.haltSales = haltSales;
	}

	public OpenSaleIntercessor getOpenSaleIntercessor() {
		return openSaleIntercessor;
	}

	public void setOpenSaleIntercessor(OpenSaleIntercessor openSaleIntercessor) {
		this.openSaleIntercessor = openSaleIntercessor;
	}
}
