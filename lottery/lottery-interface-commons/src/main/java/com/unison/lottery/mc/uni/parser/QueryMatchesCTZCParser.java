package com.unison.lottery.mc.uni.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.mc.uni.parser.util.IDMapper;
import com.unison.lottery.mc.uni.parser.util.IDMapperException;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.dc.task.ticket.OpenSaleIntercessor;
import com.xhcms.lottery.lang.PlayType;

/**
 * 查询ticket中奖情况的响应parser。是线程安全的。
 * @author Wang Lei
 */
public class QueryMatchesCTZCParser extends MessageParser {

	private static final long serialVersionUID = -5847174050078276246L;
	
	@Autowired
	private IDMapper idMapper;
	
	public QueryMatchesCTZCParser(){
    	setExpectedTransCode(115);
    }
	
	/**
	 * 解析赛事信息，结果写入 status 中。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void parseBody(Element body, ParserStatus status) throws ParseException {
		List<Element> games = body.element("jcgames").elements("jcgame");
		QueryMatchesCTZCParserStatus matchesParserStatus = (QueryMatchesCTZCParserStatus) status;
		List<CTFBMatch> matches = matchesParserStatus.getMatches();
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
			CTFBMatch match = constructMatch(game, matchesParserStatus);
			matches.add(match);
		}
	}
	
	private CTFBMatch constructMatch(Element game, QueryMatchesCTZCParserStatus matchesParserStatus) throws ParseException {
		CTFBMatch match = new CTFBMatch();
		String lotteryId = parseLotteryId(matchesParserStatus.getType());
		
		String playId = matchesParserStatus.getPlayId();
		String matchId = game.attributeValue("matchID");
		String time = game.attributeValue("matchtime");
		String home = game.attributeValue("hometeam").trim();
		String guest = game.attributeValue("guestteam").trim();
		
		match.setMatchId(Long.parseLong(matchId));
		match.setLeagueName(game.attributeValue("name").trim());
		match.setHomeTeamName(home);
		match.setGuestTeamName(guest);
		match.setId(matchesParserStatus.getIssueNumber()+playId+matchId);
//		match.setLotteryId(lotteryId);
		match.setPlayId(playId);
		match.setIssueNumber(matchesParserStatus.getIssueNumber());
		
		parseOfftime(lotteryId, match, time);
		
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
	protected SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd HH:mm");
	// 大V彩提前停售时间，分钟
	private int haltSales = 0;
	private OpenSaleIntercessor openSaleIntercessor;
	
	/**
	 * 比赛开始时间
	 */
	protected void parseOfftime(String lotteryId, CTFBMatch match, String time) throws ParseException {
        Calendar playTime = Calendar.getInstance();
        try {
			playTime.setTime(formater.parse(time));
		} catch (java.text.ParseException e) {
			throw new ParseException("Can not parse play start time: " + time, e);
		}
        
        // 开赛时间
        match.setPlayingTime(playTime.getTime());

        // 停售时间
        Date offtime = DateUtils.addMinutes(playTime.getTime(), -1);
        match.setOfftime(offtime);
        // 代理停止出票时间
        playTime.add(Calendar.MINUTE, haltSales);
        match.setEntrustDeadline(openSaleIntercessor.parseEntrustDeadline(lotteryId, playTime.getTime()));
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
