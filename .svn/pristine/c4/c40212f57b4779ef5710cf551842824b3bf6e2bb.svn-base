package com.xhcms.lottery.dc.feed.web.action.follow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.service.ShowFollowQueryCondition;
import com.xhcms.lottery.commons.persist.service.ShowSchemeService;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.dc.feed.web.action.BaseListAction;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityType;

/**
 * 某个用户晒单跟单
 * @author yonglizhu
 *
 */
public class ShowAndFollowSchemeListAction extends BaseListAction {

	private static final long serialVersionUID = 9215329432057185493L;

	private long uid;

	private String orderBy;
	private boolean asc;
	
	private List<UserScore> scoreTopList;

	private UserScore userScore;
	private String userName;

	private String ticket; // 单点登录的ticket，需要重定向以便在url中去掉。

	@Autowired
	private ShowSchemeService showSchemeService;

	@Autowired
	private UserScoreService userScoreService;

	@Autowired
	private UserService userService;

	// 查询在售晒单方案
	public String oss() {
		if (StringUtils.isNotBlank(ticket)) {
			return "betView";
		}
		
		getUserNameAndScore();
		
		ShowFollowQueryCondition condition = composeCondition(true);
		showSchemeService.findShowSchemesByCondition(paging, condition);
		loadUserScore(paging);
		
		return SUCCESS;
	}

	// 查询在售跟单方案
	public String osf() {
		if (StringUtils.isNotBlank(ticket)) {
			return "betView";
		}
		
		getUserNameAndScore();
		
		ShowFollowQueryCondition condition = composeCondition(true);
		showSchemeService.findFollowSchemesByCondition(paging, condition);
		loadUserScore(paging);

		return SUCCESS;
	}

	// 查询中奖晒单方案
	public String ws() {
		if (StringUtils.isNotBlank(ticket)) {
			return "betView";
		}
		
		getUserNameAndScore();
		
		ShowFollowQueryCondition condition = composeCondition(false);
		showSchemeService.findShowSchemesByCondition(paging, condition);

		return SUCCESS;
	}

	// 查询中奖跟单方案
	public String wf() {
		if (StringUtils.isNotBlank(ticket)) {
			return "betView";
		}
		
		getUserNameAndScore();
		
		ShowFollowQueryCondition condition = composeCondition(false);
		showSchemeService.findFollowSchemesByCondition(paging, condition);
		loadUserScore(paging);

		return SUCCESS;
	}

	private ShowFollowQueryCondition composeCondition(boolean isOnSale) {
		wrapPaging();
		this.paging.setMaxResults(10000);

		ShowFollowQueryCondition condition = new ShowFollowQueryCondition();		
		condition.setUserId(uid);
		if (null == orderBy) {
			orderBy = StringUtils.EMPTY;
		}
		condition.setOrderColumn(orderBy);
		condition.setIsAsc(asc);
		if (isOnSale) {
			condition.setCurDate("now");
			condition.setStatus(-1);
		} else {
			condition.setCurDate(StringUtils.EMPTY);
			condition.setStatus(12);
		}
		
		return condition;
	}

	private void getUserNameAndScore() {
		User user = userService.getUser(uid);
		if (user != null) {
			userName = user.getUsername();
		}
		userScore = userScoreService.getUserScoreByUserIdLotteryId(uid,
				StringUtils.EMPTY);
		scoreTopList = userScoreService.listTopUserScoreOfLottery(
				StringUtils.EMPTY, EntityType.TOP_USER_SCORE);
	}
	
	private void loadUserScore(Paging paging) {
		List<FollowScheme> results = new ArrayList<FollowScheme>();
		for(Object scheme : paging.getResults()) {
			BetScheme betScheme = (BetScheme)scheme;
			UserScore userScore = userScoreService.getUserScoreByUserIdLotteryId(betScheme.getSponsorId(), Constants.ZCZ);
			results.add(new FollowScheme(betScheme, userScore));
		}
		paging.setResults(results);
	}


	public List<UserScore> getScoreTopList() {
		return scoreTopList;
	}

	public void setScoreTopList(List<UserScore> scoreTopList) {
		this.scoreTopList = scoreTopList;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public UserScore getUserScore() {
		return userScore;
	}

	public void setUserScore(UserScore userScore) {
		this.userScore = userScore;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
	
}
