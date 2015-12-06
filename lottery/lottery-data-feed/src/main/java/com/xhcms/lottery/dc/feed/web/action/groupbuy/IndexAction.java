/**
 * 
 */
package com.xhcms.lottery.dc.feed.web.action.groupbuy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.service.PlayService;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.commons.persist.service.YesNoAll;
import com.xhcms.lottery.dc.feed.persist.service.GroupBuySchemeService;
import com.xhcms.lottery.dc.feed.web.action.BaseListAction;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.ConvertUserScore;

/**
 * @author Bean.Long
 *
 */
public class IndexAction extends BaseListAction {
	private static final long serialVersionUID = 4453396814346248096L;
	
	private String username;
	private Integer commission;
	private String play;
	
	private String orderBy;
	private Boolean asc;
		
	private String lottery;
	
	@Autowired
	private GroupBuySchemeService groupBuyService;
	@Autowired
	private PlayService playService;
	@Autowired
	private UserScoreService userScoreService;
	
	@Override
	public String execute() throws Exception {
		wrapPaging();
		paging.setCount(true);
		
		if(asc == null)
			asc = false;
		
		Date endTime = Calendar.getInstance().getTime();
		Date startTime = DateUtils.addDays(endTime, -3);
		
		groupBuyService.pagingRecommendGroupBuyShcemes(username, commission, play, orderBy, asc, 
				startTime, endTime,  paging);
		
		loadUserScore(paging);
		loadCommonData();
		
		return SUCCESS;
	}
	
	public String lotteryGroupbuySchemes() throws Exception {
		wrapPaging();
		paging.setCount(true);
		
		if(asc == null)
			asc = false;
		
		if(StringUtils.isEmpty(lottery))
			lottery = Constants.JCZQ;
		
		Date endTime = Calendar.getInstance().getTime();
		Date startTime = DateUtils.addDays(endTime, -3);

		groupBuyService.pagingGroupBuyShcemesByLottery(lottery, username, commission, play, orderBy, asc, 
				startTime, endTime, paging);
		loadUserScore(paging);
		loadCommonData();
		
		return SUCCESS;
	}
	
	private void loadUserScore(Paging paging) {
		List<GroupBuyScheme> results = new ArrayList<GroupBuyScheme>();
		for(Object scheme : paging.getResults()) {
			BetScheme betScheme = (BetScheme)scheme;
			UserScore userScore = userScoreService.getUserScoreByUserIdLotteryId(betScheme.getSponsorId(), lottery==null?Constants.ZCZ:lottery);
			results.add(new GroupBuyScheme(betScheme, userScore));
		}
		paging.setResults(results);
	}
	
	private void loadCommonData() {
		if(StringUtils.isEmpty(lottery)) {
			getValueStack().set("plays", playService.listCustomMadePlays());
		} else {
			getValueStack().set("plays", playService.listPlay(lottery));
		}
		getValueStack().set("yesNoAll", YesNoAll.values());
		
		List<UserScore> scoreTopList = null;
		if(StringUtils.isNotEmpty(lottery)) {
			scoreTopList = userScoreService.topGroupbuyUserScores(lottery, 20);
		} else {
			scoreTopList = userScoreService.topGroupbuyUserScores(Constants.ZCZ, 20);
		}
		
		String groupPic = null;
		for(UserScore userScore:scoreTopList) {
			groupPic = ConvertUserScore.convertScore(userScore.getGroupScore(),
					EntityType.GROUP_SCORE);
			userScore.setShowPic(groupPic);
			
		}
		getValueStack().set("scoreTopList", scoreTopList);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getCommission() {
		return commission;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}

	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
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

	public String getLottery() {
		return lottery;
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}
}
