package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.service.store.data.BBMatchScore;
import com.unison.lottery.weibo.data.service.store.data.FBMatchData;
import com.unison.lottery.weibo.data.service.store.data.FBMatchScore;
import com.unison.lottery.weibo.data.service.store.persist.service.BBMatchDataService;
import com.unison.lottery.weibo.data.service.store.persist.service.MatchDataService;
import com.unison.lottery.weibo.utils.LotteryIdMatchData;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.commons.lang.Data;

public class MatchScoreAction extends BaseAction {

	private static final long serialVersionUID = -7819873078787379995L;
	private Logger log = LoggerFactory.getLogger(getClass());
	private Data data = Data.success(null);

	private String matchId;

	private String lotteryType;
	
	@Autowired
	private MatchDataService matchDataService;
	
	@Autowired
	private BBMatchDataService bbMatchDataService;

	/**
	 * 赛事比分结果
	 * @return
	 */
	public String matchResult() {
		try {
			if(LotteryIdMatchData.isZC(lotteryType)) {
				FBMatchScore fbMatchScore = matchDataService.getFBMatch(matchId);
				data.setData(fbMatchScore);
			} else if(LotteryIdMatchData.isLC(lotteryType)) {
				BBMatchScore bbMatchScore = bbMatchDataService.getBBMatch(matchId);
				data.setData(bbMatchScore);
			}
		} catch (Exception e) {
			log.error("查询赛事比分结果异常!", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 比分走势
	 * 
	 * @return
	 */
	public String scoreChart() {
		try {
			if(LotteryIdMatchData.isZC(lotteryType)) {
				FBMatchData scoreChartData = matchDataService
						.getFBLatestMatchResult(matchId, 5);
				data.setData(scoreChartData);
			} else if(LotteryIdMatchData.isLC(lotteryType)) {
				
			}
		} catch (Exception e) {
			log.error("查询竞彩比分走势异常!", e);
			e.printStackTrace();
		}
		return SUCCESS;
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

	
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
}