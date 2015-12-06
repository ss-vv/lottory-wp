package com.unison.lottery.weibo.web.action.pc.league;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.service.store.data.LeagueScoreRank;
import com.unison.lottery.weibo.data.service.store.persist.service.FBLeagueCupRankService;
import com.unison.lottery.weibo.utils.LotteryIdMatchData;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.commons.lang.Data;

/**
 * @desc 获取联赛/杯赛的排行数据
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-20
 * @version 1.0
 */
public class LeagueCupAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(getClass());

	private String matchId;

	private Data data;
	
	private String lotteryType;

	@Autowired
	private FBLeagueCupRankService fbRankService;
	
	public String execute() {
		LeagueScoreRank scoreRank = null;
		try {
			if(LotteryIdMatchData.isZC(lotteryType)) {
				scoreRank = fbRankService.findFBLeagueRankBy(matchId);
			} else if(LotteryIdMatchData.isLC(lotteryType)) {
				
			}
			data = Data.success(scoreRank);
		} catch (Exception e) {
			log.error("查询联赛积分排行错误.", e);
		}
		return SUCCESS;
	}
	
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public Data getData() {
		return data;
	}
	
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
}