package com.unison.lottery.weibo.web.action.pc;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.service.store.data.BBEuropeOddVO;
import com.unison.lottery.weibo.data.service.store.data.BBMatchScore;
import com.unison.lottery.weibo.data.service.store.data.FBEuropeOddVO;
import com.unison.lottery.weibo.data.service.store.data.FBMatchData;
import com.unison.lottery.weibo.data.service.store.data.FBMatchScore;
import com.unison.lottery.weibo.data.service.store.persist.service.BBMatchDataService;
import com.unison.lottery.weibo.data.service.store.persist.service.MatchDataService;
import com.unison.lottery.weibo.utils.LotteryIdMatchData;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.dc.data.Match;

public class MatchAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(getClass());

	private String matchId;
	private String lotteryType;
	private Match match = new Match();
	private FBMatchData fbMatchData;
	private Data data;

	@Autowired
	private MatchDataService matchDataService;

	@Autowired
	private BBMatchDataService bbMatchDataService;
	
	public String execute() {
		try {
			fillMatch(matchId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("跳转赛事概览页面异常",e);
		}
		return SUCCESS;
	}

	public String toRecentMatchPage() {
		try {
			fillMatch(matchId);
		} catch (Exception e) {
			log.error("跳转近期赛事页面异常",e);
		}
		return SUCCESS;
		
	}

	public String toFutureMatchPage() {
		try {
			fillMatch(matchId);
		} catch (Exception e) {
			log.error("跳转未来赛事页面异常",e);
		}
		return SUCCESS;
	}

	public String toFightHistoryPage() {
		try {
			fillMatch(matchId);
		} catch (Exception e) {
			log.error("跳转交战历史赛事页面异常",e);
		}
		return SUCCESS;
	}

	/** 欧赔指数 */
	public String europeOdds() {
		try {
			if(LotteryIdMatchData.isZC(lotteryType)) {
				List<FBEuropeOddVO> europeOddList = matchDataService.fbEuropeOddList(matchId);
				fillFBMatch(matchId);
				data = Data.success(europeOddList);
			} else if(LotteryIdMatchData.isLC(lotteryType)) {
				List<BBEuropeOddVO> bbEuropeOddList = bbMatchDataService.bbEuropeOddList(matchId);
				fillBBMatch(matchId);
				data = Data.success(bbEuropeOddList);
			}
		} catch (Exception e) {
			log.error("查询欧赔指数异常!", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/** 亚洲盘口 */
	public String asianOdds() {
		try {
			if(LotteryIdMatchData.isZC(lotteryType)) {
				fillFBMatch(matchId);
				data = Data.success(matchDataService.fbAsianOddResult(matchId));
			} else if(LotteryIdMatchData.isLC(lotteryType)) {
				fillBBMatch(matchId);
				data = Data.success(bbMatchDataService.bbAsianOddResult(matchId));
			}
		} catch (Exception e) {
			data = Data.failure(null);
			log.error("查询亚洲盘口异常!", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 篮球大小球盘口
	 * @return
	 */
	public String bigSmallOdds() {
		if(LotteryIdMatchData.isLC(lotteryType)) {
			data = Data.success(bbMatchDataService.bbBigSmallOddResult(matchId));
		} else {
			log.info("彩种类型:{},没有‘大小分赔率’", lotteryType);
		}
		return SUCCESS;
	}
	
	private void fillMatch(String matchId) {
		if(LotteryIdMatchData.isZC(lotteryType)) {
			fillFBMatch(matchId);
		} else if(LotteryIdMatchData.isLC(lotteryType)) {
			fillBBMatch(matchId);
		}
	}
	
	/**
	 * 填充足球赛事数据
	 * @param matchId
	 */
	private void fillFBMatch(String matchId) {
		FBMatchScore fbMatch = matchDataService.getFBMatch(matchId);
		match.setHomeTeam(fbMatch.getHomeTeamName());
		match.setGuestTeam(fbMatch.getGuestTeamName());
//		match.setHomeTeamId(fbMatch.getHomeTeamId());
//		match.setGuestTeamId(fbMatch.getGuestTeamId());
	}
	
	/**
	 * 填充篮球赛事数据
	 * @param matchId
	 */
	private void fillBBMatch(String matchId) {
		BBMatchScore bbMatch = bbMatchDataService.getBBMatch(matchId);
		match.setHomeTeam(bbMatch.getHomeTeam());
		match.setGuestTeam(bbMatch.getGuestTeam());
//		match.setHomeTeamId(bbMatch.getHomeTeamId());
//		match.setGuestTeamId(bbMatch.getGuestTeamId());
	}

	public Match getMatch() {
		return match;
	}

	public FBMatchData getFbMatchData() {
		return fbMatchData;
	}

	public void setFbMatchData(FBMatchData fbMatchData) {
		this.fbMatchData = fbMatchData;
	}

	public Data getData() {
		return data;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
}
