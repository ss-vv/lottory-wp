package com.unison.lottery.api.showAndFollow.task;

import java.util.concurrent.Callable;

import com.unison.lottery.api.showAndFollow.model.BetShemeParams;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.ucenter.persistent.service.IUserService;

public class LoadParamsTask implements Callable<BetShemeParams>{

	private BetSchemePO betSchemePO;
	
	private IUserService userService;
	
	private UserScoreService userScoreService;
	
	@Override
	public BetShemeParams call() throws Exception {
		User user = userService.getUser(betSchemePO.getSponsorId());
		if(user != null){
			BetShemeParams betShemeParams = new BetShemeParams();
			UserScore userScore = userScoreService.getUserScoreByUserIdLotteryId(user.getId(), "-1");
			betShemeParams.setBetSchemePO(betSchemePO);
			betShemeParams.setImageUrl(user.getHeadImageURL());
			if(userScore != null){
				betShemeParams.setMilitaryExploits(userScore.getWinAmount());
			}
			return betShemeParams;
		}
		return null;
	}

	public BetSchemePO getBetSchemePO() {
		return betSchemePO;
	}

	public void setBetSchemePO(BetSchemePO betSchemePO) {
		this.betSchemePO = betSchemePO;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public UserScoreService getUserScoreService() {
		return userScoreService;
	}

	public void setUserScoreService(UserScoreService userScoreService) {
		this.userScoreService = userScoreService;
	}

	
}
