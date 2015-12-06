package com.unison.lottery.mc.uni.parser;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Element;

import com.unison.lottery.mc.uni.parser.QueryMatchesParserStatus.Type;
import com.unison.lottery.mc.uni.parser.util.ZMPlayCode;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.dc.data.CGJTeam;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.task.ticket.OpenSaleIntercessor;
import com.xhcms.lottery.lang.Constants;

/**
 * 查询赛事信息，解析响应parser。是线程安全的。
 * @author Yang Bo
 */
public class QueryMatchesParser extends MessageParser {

	private static final long serialVersionUID = -5847174050078276246L;
	
	public QueryMatchesParser(){
    	setExpectedTransCode(116);
    }
	
	/**
	 * 解析赛事信息，结果写入 status 中。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void parseBody(Element body, ParserStatus status) throws ParseException {
		List<Element> games = body.element("jcgames").elements("jcgame");
		QueryMatchesParserStatus matchesParserStatus = (QueryMatchesParserStatus) status;
		List<Match> matches = matchesParserStatus.getMatches();
		for (Element game : games) {
			Match match = null;
			if(matchesParserStatus.getType().equals("jcsjbgj")) {
				match = constructTeamInfo(game, matchesParserStatus);
			} else {
				match = constructMatch(game, matchesParserStatus);
			}
			if(null != match) {
				matches.add(match);
			}
		}
	}
	
	/**
	 * 组装队伍信息
	 * 竞猜世界杯冠军
	 * @param game
	 * @param matchesParserStatus
	 * @return
	 * @throws ParseException
	 */
	protected Match constructTeamInfo(Element game, QueryMatchesParserStatus matchesParserStatus) throws ParseException {
		Match match = new Match();
		String lotteryId = Constants.JCZQ;
		String time = game.attributeValue("matchtime");
		
		parseOfftime(lotteryId, match, time);
		
		String matchId = game.attributeValue("matchID");
		String name = game.attributeValue("name");	//赛事名称
		String teamName = game.attributeValue("team");
		String matchState = game.attributeValue("matchstate");
		String sp = game.attributeValue("sp");
		
		CGJTeam cgjTeam = new CGJTeam();
		cgjTeam.setTeamName(teamName);
		cgjTeam.setName(name);
		if(StringUtils.isNotBlank(matchId)) {
			cgjTeam.setMatchId(Long.parseLong(matchId));
		}
		if(StringUtils.isNotBlank(matchState)) {
			cgjTeam.setStatus(Integer.parseInt(matchState));
		}
		if(StringUtils.isNotBlank(sp)) {
			cgjTeam.setOdds(new BigDecimal(sp));
		}
		cgjTeam.setPlayingTime(match.getPlayingTime());
		cgjTeam.setOfftime(match.getOfftime());
		
		return cgjTeam;
	}
	
	private Match constructMatch(Element game, QueryMatchesParserStatus matchesParserStatus) throws ParseException {
		Match match = new Match();
		String cnCode = game.attributeValue("matchID");
		String time = game.attributeValue("matchtime");
		match.setCode(Translator.cnCodeToNumCode(cnCode));
		match.setCnCode(cnCode);
		match.setLeague(game.attributeValue("name"));
		String home = game.attributeValue("hometeam");
		String guest = game.attributeValue("guestteam");
		match.setHomeTeam(home);
		match.setGuestTeam(guest);
		
		//赛事不支持的玩法
		String privilegesType = game.attributeValue("PrivilegesType");
		
		List<String> playIdList = splitPlayType(privilegesType, matchesParserStatus.getType());
		if(playIdList.size() > 0) {
			match.setPlayIds(playIdList);
		}
		
		if (matchesParserStatus.getEnumType()==Type.jclq){
			match.setName(guest + " VS " + home);
		}else{
			match.setName(home + " VS " + guest);
		}
		String polygoal = game.attributeValue("polygoal"); // JCZQ 让球数
		if (StringUtils.isNotBlank(polygoal)) {
			match.setConcedePoints((int)Float.parseFloat(polygoal));
		}
		String concedeScore = game.attributeValue("goal");	// 让分值, 需要靠获取SP值接口获取更准确数据
		if (StringUtils.isNotBlank(concedeScore)){
			float score = Float.valueOf(concedeScore);
			match.setConcedeScore(score);
			match.setConcedeScorePass(score);
		}
		String presetScore = game.attributeValue("ougoal");	// 大小分预设值, 需要靠获取SP值接口获取更准确数据
		if (StringUtils.isNotBlank(presetScore)){
			float score = Float.valueOf(presetScore);
			match.setGuestScore(score);
			match.setGuestScorePass(score);
		}
		String lotteryId = null;
		if (matchesParserStatus.getType().equals("jclq")){
			lotteryId = Constants.JCLQ;
		}else if(matchesParserStatus.getType().equals("jczq")){
			lotteryId = Constants.JCZQ;
		}else{
			throw new ParseException("Unsupported type: " + matchesParserStatus.getType());
		}
		parseOfftime(lotteryId, match, time);
		
		String selloutTime = game.attributeValue("sellouttime"); // 销售截止时间
		logger.debug("{} entrustDeadLine: {}, selloutTime: {}", new Object[]{
				cnCode, match.getEntrustDeadline(), selloutTime});
		
		return match;
	}

	private List<String> splitPlayType(String privilegesType, String lottery) {
		List<String> playIdList = new ArrayList<String>();
		String[] codes=null;
		if(StringUtils.isNotBlank(privilegesType)) {//有不支持的玩法
			codes = privilegesType.split(",");
			
		}
		
		playIdList = ZMPlayCode.supportPlayType(codes, lottery);//根据不支持的code，求出支持的玩法id
		return playIdList;
	}

	// 开赛时间格式
	protected SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	// 大V彩提前停售时间，分钟
	private int haltSales = -15;
	private OpenSaleIntercessor openSaleIntercessor;
	
	/**
	 * 新接口的比赛开始时间是准确的开始时间所以不用加1分钟得到停赛时间。 
	 */
	protected void parseOfftime(String lotteryId, Match match, String time) throws ParseException {
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
