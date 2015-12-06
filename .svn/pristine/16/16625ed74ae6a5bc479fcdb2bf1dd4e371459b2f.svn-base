package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.uc.data.TeamPraisedWeiboUserResult;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.BBMatchOverviewService;
import com.unison.lottery.weibo.web.service.MatchTeamPraiseService;
import com.xhcms.commons.lang.Data;

/**
 * @desc 球队的赞
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-20
 * @version 1.0
 */
public class TeamPraiseAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MatchTeamPraiseService teamPraiseService;

	private String matchId;

	private long teamId;

	private String lotteryType;

	private long homeTeamId;

	private long guestTeamId;

	private TeamPraisedWeiboUserResult result;

	@Autowired
	private BBMatchOverviewService bbMatchOverviewService;

	private Map<String, Object> resultMap;

	private Data data = Data.failure(null);

	/**
	 * 球队赞的查询
	 * 
	 * @return
	 */
	public String teamPraiseQuery() {
		try {
			result = teamPraiseService.findTeamPraisedWeiboUser(matchId,
					teamId, lotteryType, 10);
			data = Data.success(null);
		} catch (Exception e) {
			log.error("查询彩种={}, 比赛={}中球队ID={}的支持用户错误.", new Object[] {
					lotteryType, matchId, teamId, e });
		}
		return SUCCESS;
	}

	/**
	 * 支持某只球队
	 * 
	 * @return
	 */
	public String likeTeam() {
		long weiboUserId = getUserLaicaiWeiboId();
		try {
			if(weiboUserId > 0) {
				teamPraiseService.addPraise(matchId, teamId, lotteryType,
						weiboUserId);
				data = Data.success(null);
			} else {
				data.setData("请先登录");
			}
		} catch (Exception e) {
			log.error(
					"微博User={}, 点赞球队teamId={}，所属：matchId={}，lotteryType={}，异常.",
					new Object[] { weiboUserId, teamId, matchId, lotteryType });
		}
		return SUCCESS;
	}
	public String unlikeTeam() {
		long weiboUserId = getUserLaicaiWeiboId();
		try {
			if(weiboUserId > 0) {
				teamPraiseService.delPraise(matchId, teamId, lotteryType,
						weiboUserId);
				data = Data.success(null);
			} else {
				data.setData("请先登录");
			}
		} catch (Exception e) {
			log.error(
					"微博User={}, 取消赞球队teamId={}，所属：matchId={}，lotteryType={}，异常.",
					new Object[] { weiboUserId, teamId, matchId, lotteryType });
		}
		return SUCCESS;
	}

	/**
	 * 赛事概览信息汇总
	 * 
	 * @return
	 */
	public String matchInfoCollect() {
		try {
			resultMap = bbMatchOverviewService.bbMatchInfoCollect(matchId,
					homeTeamId, guestTeamId, lotteryType, 10);
			data = Data.success(null);
		} catch (Exception e) {
			log.error("查询篮球赛事概览数据出现异常.", e);
		}
		return SUCCESS;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public void setTeamId(String teamId) {
		try {
			this.teamId = Long.valueOf(teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public TeamPraisedWeiboUserResult getResult() {
		return result;
	}

	public long getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(long homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public long getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public Data getData() {
		return data;
	}
}