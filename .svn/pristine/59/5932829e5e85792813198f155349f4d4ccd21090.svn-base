package com.xhcms.lottery.dc.feed.web.action.follow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.service.PlayService;
import com.xhcms.lottery.commons.persist.service.ShowSchemeService;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.commons.persist.service.YesNoAll;
import com.xhcms.lottery.dc.feed.web.action.BaseListAction;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.AdminStatusTool;

/**
 * 显示晒单的列表，用户战绩
 * @author yonglizhu
 *
 */
public class ShowingSchemeListAction extends BaseListAction {

	private static final long serialVersionUID = 2608997724703118640L;

	private String username;
	private int followRatio;
	private String playId;

	private String lid;
	private String orderBy;
	private boolean asc;

	@Autowired
	private ShowSchemeService showSchemeService;
	@Autowired
	private PlayService playService;
	@Autowired
	private UserScoreService userScoreService;

	private String ticket; // 单点登录的ticket，需要重定向以便在url中去掉。

	// 查询方案和战绩
	public String execute() {
		if (StringUtils.isNotBlank(ticket)) {
			return "betView";
		}

		wrapPaging();
		paging.setCount(true);
		
		showSchemeService.findOnSaleShowingSchemes(paging, true, lid, username, playId, followRatio, orderBy, asc);
		loadUserScore(paging);
		loadCommonData();

		return SUCCESS;
	}

	/**
	 * 异步请求热门晒单推荐
	 * @return
	 */
	public String ajxHotSchemeRecommend() {
		wrapPaging();
		paging.setMaxResults(maxResults);
		paging.setCount(true);
		showSchemeService.findOnSaleShowingSchemes(paging, false, lid, username, playId, followRatio, orderBy, asc);
		convertSchemePlayName(paging.getResults());
		loadUserScore(paging);
		return SUCCESS;
	}
	
	public String list() {
		if (StringUtils.isNotBlank(ticket)) {
			return "betView";
		}

		wrapPaging();
		paging.setCount(true);
		
		if(StringUtils.isEmpty(lid)) {
			lid = Constants.JCZQ;
		}
		
		showSchemeService.findOnSaleShowingSchemes(paging, false, lid, username, playId, followRatio, orderBy, asc);
		loadUserScore(paging);
		loadCommonData();

		return SUCCESS;
	}

	private void loadUserScore(Paging paging) {
		List<FollowScheme> results = new ArrayList<FollowScheme>();
		for(Object scheme : paging.getResults()) {
			BetScheme betScheme = (BetScheme)scheme;
			UserScore userScore = userScoreService.getUserScoreByUserIdLotteryId(betScheme.getSponsorId(), lid==null?Constants.ZCZ:lid);
			results.add(new FollowScheme(betScheme, userScore));
		}
		paging.setResults(results);
	}
	
	@SuppressWarnings("unchecked")
	private void convertSchemePlayName(List<?> rs) {
		if(null != rs) {
			List<BetScheme> result = (List<BetScheme>)rs;
			for(BetScheme scheme : result) {
				Play play = new Play();
				play.setName(new AdminStatusTool().playName(scheme.getPlayId()));
				scheme.setPlay(play);
			}
		}
	}
	
	private void loadCommonData() {
		//提成
		getValueStack().set("yesNoAll", YesNoAll.values());
		//玩法
		if(StringUtils.isEmpty(lid)) {
			getValueStack().set("playList", playService.listCustomMadePlays());
		} else {
			getValueStack().set("playList", playService.listPlay(lid));
		}
		//晒单战绩榜
		List<UserScore> scoreTopList = userScoreService
				.listTopUserScoreOfLottery(lid, EntityType.TOP_USER_SCORE);
		getValueStack().set("scoreTopList", scoreTopList);
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getFollowRatio() {
		return followRatio;
	}

	public void setFollowRatio(int followRatio) {
		this.followRatio = followRatio;
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

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

}
