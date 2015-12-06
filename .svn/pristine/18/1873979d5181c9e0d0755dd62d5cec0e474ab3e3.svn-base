package com.xhcms.lottery.dc.feed.web.action.follow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.FollowWinList;
import com.xhcms.lottery.commons.data.ShowWinList;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.commons.persist.service.WinListService;
import com.xhcms.lottery.dc.feed.web.action.BaseListAction;
import com.xhcms.lottery.lang.EntityType;

/**
 * 晒单跟单中奖榜
 * @author yonglizhu
 *
 */
public class WinListAction extends BaseListAction {
	
	private String lid;
	
	private String ticket; // 单点登录的ticket，需要重定向以便在url中去掉。
	
	private List<UserScore> scoreTopList;
	
	@Autowired
	private WinListService winListService;
	@Autowired
	private UserScoreService userScoreService;

	public String swl() {
		if (StringUtils.isNotBlank(ticket)) {
			return "betView";
		}
		
		wrapPaging();
		paging.setMaxResults(20);
		winListService.findShowWinList(paging, lid);
		loadShowUserScore(paging);
		
		loadUserScoreTop();
		
		return SUCCESS;
	}
	
	public String fwl() {
		if (StringUtils.isNotBlank(ticket)) {
			return "betView";
		}
		
		wrapPaging();
		paging.setMaxResults(20);
		winListService.findFollowWinList(paging, lid);
		loadFollowUserScore(paging);
		
		loadUserScoreTop();
		
		return SUCCESS;
	}

	private void loadShowUserScore(Paging paging) {
		List<FollowScheme> results = new ArrayList<FollowScheme>();
		for(Object winList : paging.getResults()) {
			ShowWinList showWinList = (ShowWinList)winList;
			UserScore userScore = userScoreService.getUserScoreByUserIdLotteryId(showWinList.getSponsorId(), lid);
			results.add(new FollowScheme(showWinList, userScore));
		}
		paging.setResults(results);
	}
	
	private void loadFollowUserScore(Paging paging) {
		List<FollowScheme> results = new ArrayList<FollowScheme>();
		for(Object winList : paging.getResults()) {
			FollowWinList followWinList = (FollowWinList)winList;
			UserScore userScore = userScoreService.getUserScoreByUserIdLotteryId(followWinList.getFollowerId(), lid);
			results.add(new FollowScheme(followWinList, userScore));
		}
		paging.setResults(results);
	}
	
	private void loadUserScoreTop() {
		scoreTopList = userScoreService.listTopUserScoreOfLottery(
				StringUtils.EMPTY, EntityType.TOP_USER_SCORE);
	}
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public List<UserScore> getScoreTopList() {
		return scoreTopList;
	}

	public void setScoreTopList(List<UserScore> scoreTopList) {
		this.scoreTopList = scoreTopList;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}
	
}
