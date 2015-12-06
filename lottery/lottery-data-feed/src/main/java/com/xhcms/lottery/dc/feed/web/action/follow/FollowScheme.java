/**
 * 
 */
package com.xhcms.lottery.dc.feed.web.action.follow;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.FollowWinList;
import com.xhcms.lottery.commons.data.ShowWinList;
import com.xhcms.lottery.commons.data.UserScore;

/**
 * @author Pink
 *
 */
public class FollowScheme {
	private BetScheme scheme;
	private UserScore userScore;
	private ShowWinList showWinList;
	private FollowWinList followWinList;
	
	public FollowScheme(){}
	
	public FollowScheme(BetScheme scheme, UserScore userScore) {
		this.scheme = scheme;
		this.userScore = userScore;
	}
	
	public FollowScheme(ShowWinList showWinList, UserScore userScore) {
		this.showWinList = showWinList;
		this.userScore = userScore;
	}
	
	public FollowScheme(FollowWinList followWinList, UserScore userScore) {
		this.followWinList = followWinList;
		this.userScore = userScore;
	}
	
	public ShowWinList getShowWinList() {
		return showWinList;
	}

	public void setShowWinList(ShowWinList showWinList) {
		this.showWinList = showWinList;
	}

	public FollowWinList getFollowWinList() {
		return followWinList;
	}

	public void setFollowWinList(FollowWinList followWinList) {
		this.followWinList = followWinList;
	}

	public BetScheme getScheme() {
		return scheme;
	}
	
	public void setScheme(BetScheme scheme) {
		this.scheme = scheme;
	}
	
	public UserScore getUserScore() {
		return userScore;
	}
	
	public void setUserScore(UserScore userScore) {
		this.userScore = userScore;
	}
}
