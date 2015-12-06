package com.xhcms.lottery.admin.web.action.groupfollow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetScheme;

public class ListFollowingSchemeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4833258017189345340L;

	@Autowired
	private BetSchemeService betSchemeService;
	
	private Long id;
	private String playId;
	private String lotteryId;

	private List<BetScheme> followingSchemes;
    
	@Override
	public String execute() throws Exception {
		followingSchemes = betSchemeService.listFollowingScheme(id);
		return SUCCESS;
	}

	public Long getId() {
		return id;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<BetScheme> getFollowingSchemes() {
		return followingSchemes;
	}

	public void setFollowingSchemes(List<BetScheme> followingSchemes) {
		this.followingSchemes = followingSchemes;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}
}
